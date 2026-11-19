package com.zombie_cute.mc.moderndelight.util.block_util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.joml.Matrix4f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/** A reusable collision recipe. Declare one static profile alongside the block that uses it. */
public final class ShapeProfile {
    private final Function<BlockState, VoxelShape> bake;
    private final Map<BlockState, VoxelShape> states = new ConcurrentHashMap<>();

    private ShapeProfile(Function<BlockState, VoxelShape> bake) {
        this.bake = bake;
    }

    VoxelShape get(BlockState state) {
        return states.computeIfAbsent(state, bake);
    }

    public static ShapeProfile fixed(VoxelShape shape) {
        return byState(state -> shape);
    }

    public static ShapeProfile byState(Function<BlockState, VoxelShape> shape) {
        return new ShapeProfile(shape);
    }

    /** A hand-built shape authored facing north, rotated to the state's horizontal facing. */
    public static ShapeProfile horizontal(VoxelShape northShape) {
        var models = new ModelShapes.ModelCache();
        return byState(state -> models.orient(northShape, state));
    }

    public static VanillaBuilder vanilla() { return new VanillaBuilder(); }

    public static GeoBuilder geo() { return new GeoBuilder(); }

    public record Element(float x, float y, float z, float maxX, float maxY, float maxZ) {
        public float width() { return maxX - x; }
        public float height() { return maxY - y; }
        public float depth() { return maxZ - z; }
    }

    record VanillaRules(VoxelShape replacement, Predicate<Element> keepElement,
                        Function<List<Element>, VoxelShape> buildShape) {}

    public static final class VanillaBuilder {
        private VoxelShape replacement;
        private Predicate<Element> keepElement = element -> true;
        private Function<List<Element>, VoxelShape> buildShape;

        private VanillaBuilder() {}

        /** Use a hand-built shape with the rotations from the blockstate variants. */
        public VanillaBuilder replaceModel(VoxelShape shape) {
            replacement = shape;
            return this;
        }

        public VanillaBuilder filterElements(Predicate<Element> filter) {
            keepElement = keepElement.and(filter);
            return this;
        }

        /** Build a simplified shape from selected, untransformed model elements. */
        public VanillaBuilder simplifyElements(Function<List<Element>, VoxelShape> simplifier) {
            buildShape = simplifier;
            return this;
        }

        public ShapeProfile build() {
            var rules = new VanillaRules(replacement, keepElement, buildShape);
            var models = new ModelShapes.ModelCache();
            return byState(state -> ModelShapes.bakeVanillaState(state, rules, models));
        }
    }

    @FunctionalInterface
    public interface GeoPose {
        /** Adjust a model-space transform after its bone hierarchy has been composed. */
        Matrix4f transform(String bone, Vector3fc pivot, Matrix4f transform);
    }

    static final GeoPose REST = (bone, pivot, transform) -> transform;
    record PoseRule(Predicate<BlockState> when, GeoPose pose) {}
    record Sweep(String animationFile, String clip, float duration, int intervals, Predicate<String> bones) {}
    enum Detail { CUBES, CUBE_BOUNDS, MODEL_BOUNDS }
    record GeoRules(BiPredicate<String, Vector3fc> keepCube, List<PoseRule> poses,
                    Sweep sweep, Detail detail, List<Element> extraBoxes) {
        GeoPose poseFor(BlockState state) {
            for (var rule : poses) {
                if (rule.when().test(state)) return rule.pose();
            }
            return REST;
        }
    }

    public static final class GeoBuilder {
        private BiPredicate<String, Vector3fc> keepCube = (bone, origin) -> true;
        private final List<PoseRule> poses = new ArrayList<>();
        private Sweep sweep;
        private Detail detail = Detail.CUBES;
        private final List<Element> extraBoxes = new ArrayList<>();

        private GeoBuilder() {}

        /** Filter by bone and the cube's original GEO origin, before mirroring or rotation. */
        public GeoBuilder filterCubes(BiPredicate<String, Vector3fc> filter) {
            keepCube = keepCube.and(filter);
            return this;
        }

        /** The first matching rule supplies a stable pose; the default is the model's rest pose. */
        public GeoBuilder poseWhen(Predicate<BlockState> when, GeoPose pose) {
            poses.add(new PoseRule(when, pose));
            return this;
        }

        /** Replace each selected animated linkage with the bounds of its sampled motion. */
        public GeoBuilder sweepAnimation(String animationFile, String clip, float duration, int intervals,
                                         Predicate<String> bones) {
            if (intervals < 1) throw new IllegalArgumentException("Animation sweep needs at least one interval");
            sweep = new Sweep(animationFile, clip, duration, intervals, bones);
            return this;
        }

        public GeoBuilder useCubeBounds() {
            detail = Detail.CUBE_BOUNDS;
            return this;
        }

        public GeoBuilder useModelBounds() {
            detail = Detail.MODEL_BOUNDS;
            return this;
        }

        /** Add geometry in block model pixels, in the north-facing coordinate system. */
        public GeoBuilder addBox(float x, float y, float z, float maxX, float maxY, float maxZ) {
            extraBoxes.add(new Element(x, y, z, maxX, maxY, maxZ));
            return this;
        }

        public GeoBuilder addDiskInXY(float centerX, float centerY, float radius, float minZ, float maxZ) {
            ModelShapes.addDisk(extraBoxes, Direction.Axis.Z, centerX, centerY, radius, minZ, maxZ);
            return this;
        }

        public GeoBuilder addDiskInYZ(float centerZ, float centerY, float radius, float minX, float maxX) {
            ModelShapes.addDisk(extraBoxes, Direction.Axis.X, centerZ, centerY, radius, minX, maxX);
            return this;
        }

        public ShapeProfile build() {
            var rules = new GeoRules(keepCube, List.copyOf(poses), sweep, detail, List.copyOf(extraBoxes));
            var models = new ModelShapes.ModelCache();
            return byState(state -> ModelShapes.bakeGeoState(state, rules, models));
        }
    }
}
