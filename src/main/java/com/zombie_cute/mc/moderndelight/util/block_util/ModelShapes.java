package com.zombie_cute.mc.moderndelight.util.block_util;

import com.google.gson.*;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import static com.zombie_cute.mc.moderndelight.util.block_util.ShapeProfile.*;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/** Reads built-in model geometry on either side and converts it into collision shapes. */
public final class ModelShapes {
    public static final ShapeProfile VANILLA = ShapeProfile.vanilla().build();

    private static final float STEP = 0.5f;
    private static final Map<String, JsonObject> JSON = new ConcurrentHashMap<>();

    private ModelShapes() {}

    public static VoxelShape get(BlockState state) {
        return get(state, VANILLA);
    }

    public static VoxelShape get(BlockState state, ShapeProfile profile) {
        return profile.get(state);
    }

    static VoxelShape bakeVanillaState(BlockState state, VanillaRules rules, ModelCache cache) {
        String name = Registries.BLOCK.getId(state.getBlock()).getPath();
        JsonObject blockstate = json("blockstates/" + name + ".json");
        if (blockstate.has("variants")) {
            for (var entry : blockstate.getAsJsonObject("variants").entrySet()) {
                if (!matches(state, entry.getKey())) continue;
                JsonElement variant = entry.getValue();
                JsonObject model = variant.isJsonArray() ? variant.getAsJsonArray().get(0).getAsJsonObject() : variant.getAsJsonObject();
                String path = model.get("model").getAsString().replace("moderndelight:", "");
                VoxelShape shape = cache.vanilla(path, rules);
                return cache.rotate(shape, number(model, "x", 0), number(model, "y", 0));
            }
        }
        return cache.vanilla("block/" + name, rules);
    }

    static VoxelShape bakeGeoState(BlockState state, GeoRules rules, ModelCache cache) {
        String name = Registries.BLOCK.getId(state.getBlock()).getPath();
        GeoPose pose = rules.poseFor(state);
        return cache.orient(cache.geo(name, pose, rules), state);
    }

    /** Model and rotation caches belong to one profile; different rules may read the same resource. */
    static final class ModelCache {
        private record GeoModel(String name, GeoPose pose) {}
        private record Rotation(VoxelShape shape, float x, float y) {}

        private final Map<String, VoxelShape> vanilla = new ConcurrentHashMap<>();
        private final Map<GeoModel, VoxelShape> geo = new ConcurrentHashMap<>();
        private final Map<Rotation, VoxelShape> rotated = new ConcurrentHashMap<>();

        VoxelShape vanilla(String path, VanillaRules rules) {
            if (rules.replacement() != null) return rules.replacement();
            return vanilla.computeIfAbsent(path, key -> bakeVanilla(key, rules));
        }

        VoxelShape geo(String name, GeoPose pose, GeoRules rules) {
            return geo.computeIfAbsent(new GeoModel(name, pose), key -> bakeGeo(key.name(), key.pose(), rules));
        }

        VoxelShape orient(VoxelShape shape, BlockState state) {
            Direction facing = state.contains(Properties.HORIZONTAL_FACING)
                    ? state.get(Properties.HORIZONTAL_FACING) : Direction.NORTH;
            return rotate(shape, 0, switch (facing) {
                case EAST -> 90; case SOUTH -> 180; case WEST -> 270; default -> 0;
            });
        }

        VoxelShape rotate(VoxelShape shape, float x, float y) {
            if (x == 0 && y == 0) return shape;
            return rotated.computeIfAbsent(new Rotation(shape, x, y), key -> ModelShapes.rotate(key.shape(), key.x(), key.y()));
        }
    }

    private static boolean matches(BlockState state, String key) {
        if (key.isEmpty()) return true;
        for (String term : key.split(",")) {
            String[] pair = term.split("=");
            if (!has(state, pair[0], pair[1])) return false;
        }
        return true;
    }

    private static boolean has(BlockState state, String key, String value) {
        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals(key)) return propertyValue(state, property).equals(value);
        }
        return false;
    }

    private static <T extends Comparable<T>> String propertyValue(BlockState state, Property<T> property) {
        return property.name(state.get(property));
    }

    private static JsonObject json(String path) {
        return JSON.computeIfAbsent(path, resource -> {
            try (var stream = ModelShapes.class.getResourceAsStream("/assets/moderndelight/" + resource)) {
                if (stream == null) return new JsonObject();
                return JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).getAsJsonObject();
            } catch (Exception error) {
                throw new IllegalStateException("Cannot load collision model " + resource, error);
            }
        });
    }

    private static VoxelShape bakeVanilla(String path, VanillaRules rules) {
        JsonObject model = json("models/" + path + ".json");
        if (!model.has("elements")) {
            if (model.has("parent") && model.get("parent").getAsString().startsWith("moderndelight:"))
                return bakeVanilla(model.get("parent").getAsString().substring("moderndelight:".length()), rules);
            throw new IllegalStateException("Collision model has no elements: " + path);
        }

        List<Cube> cubes = new ArrayList<>();
        List<Element> selected = new ArrayList<>();
        for (JsonElement entry : model.getAsJsonArray("elements")) {
            JsonObject cube = entry.getAsJsonObject();
            Vector3f from = vector(cube, "from", 0), to = vector(cube, "to", 0);
            Element element = new Element(from.x, from.y, from.z, to.x, to.y, to.z);
            if (!rules.keepElement().test(element)) continue;
            if (rules.buildShape() != null) {
                selected.add(element);
                continue;
            }
            Matrix4f matrix = new Matrix4f();
            if (cube.has("rotation")) {
                JsonObject rotation = cube.getAsJsonObject("rotation");
                Vector3f pivot = vector(rotation, "origin", 0);
                float angle = (float)Math.toRadians(number(rotation, "angle", 0));
                String axis = rotation.get("axis").getAsString();
                matrix.translate(pivot).rotate(angle, axis.equals("x") ? 1 : 0, axis.equals("y") ? 1 : 0, axis.equals("z") ? 1 : 0);
                if (rotation.has("rescale") && rotation.get("rescale").getAsBoolean()) {
                    float scale = 1 / (float)Math.cos(angle);
                    matrix.scale(axis.equals("x") ? 1 : scale, axis.equals("y") ? 1 : scale, axis.equals("z") ? 1 : scale);
                }
                matrix.translate(new Vector3f(pivot).negate());
            }
            cubes.add(new Cube(from, to, matrix));
        }
        return rules.buildShape() == null ? rasterize(cubes) : rules.buildShape().apply(selected);
    }

    private static VoxelShape bakeGeo(String name, GeoPose pose, GeoRules rules) {
        JsonArray bones = json("geo/" + name + ".geo.json").getAsJsonArray("minecraft:geometry")
                .get(0).getAsJsonObject().getAsJsonArray("bones");
        Map<String, JsonObject> byName = new LinkedHashMap<>();
        for (var entry : bones) {
            JsonObject bone = entry.getAsJsonObject();
            byName.put(bone.get("name").getAsString(), bone);
        }

        Sweep sweep = rules.sweep();
        JsonObject animatedBones = sweep == null ? new JsonObject()
                : json("animations/" + sweep.animationFile() + ".animation.json")
                        .getAsJsonObject("animations").getAsJsonObject(sweep.clip()).getAsJsonObject("bones");
        List<JsonObject> frames = new ArrayList<>();
        if (sweep != null) {
            for (int frame = 0; frame <= sweep.intervals(); frame++) {
                frames.add(sample(animatedBones, frame * sweep.duration() / sweep.intervals()));
            }
        }

        List<Cube> cubes = new ArrayList<>();
        JsonObject rest = new JsonObject();
        for (var entry : byName.entrySet()) {
            String boneName = entry.getKey();
            JsonObject bone = entry.getValue();
            if (!bone.has("cubes")) continue;

            Vector3f pivot = vector(bone, "pivot", 0);
            Matrix4f transform = pose.transform(boneName, pivot, boneTransform(bone, byName, rest));
            boolean moving = sweep != null && animatedBones.has(boneName) && sweep.bones().test(boneName);
            List<Matrix4f> transforms = new ArrayList<>();
            transforms.add(transform);
            if (moving) {
                for (JsonObject frame : frames) {
                    transforms.add(pose.transform(boneName, pivot, boneTransform(bone, byName, frame)));
                }
            }

            List<Cube> boneCubes = new ArrayList<>();
            for (var element : bone.getAsJsonArray("cubes")) {
                JsonObject cube = element.getAsJsonObject();
                Vector3f origin = vector(cube, "origin", 0), size = vector(cube, "size", 0);
                if (!rules.keepCube().test(boneName, origin)) continue;
                float inflate = number(cube, "inflate", number(bone, "inflate", 0));
                Vector3f min = new Vector3f(-origin.x - size.x - inflate, origin.y - inflate, origin.z - inflate);
                Vector3f max = new Vector3f(-origin.x + inflate, origin.y + size.y + inflate, origin.z + size.z + inflate);
                Matrix4f cubeRotation = geoRotation(cube);
                for (Matrix4f bonePose : transforms) {
                    Matrix4f matrix = new Matrix4f().translation(8, 0, 8).mul(bonePose).mul(cubeRotation);
                    boneCubes.add(new Cube(min, max, matrix));
                }
            }
            if (moving && !boneCubes.isEmpty()) cubes.add(bounds(boneCubes));
            else cubes.addAll(boneCubes);
        }
        for (Element box : rules.extraBoxes()) {
            cubes.add(new Cube(new Vector3f(box.x(), box.y(), box.z()),
                    new Vector3f(box.maxX(), box.maxY(), box.maxZ()), new Matrix4f()));
        }
        if (rules.detail() == Detail.CUBE_BOUNDS) cubes.replaceAll(Cube::axisAligned);
        if (rules.detail() == Detail.MODEL_BOUNDS && !cubes.isEmpty()) cubes = List.of(bounds(cubes));
        return rasterize(cubes);
    }

    private static Matrix4f boneTransform(JsonObject bone, Map<String, JsonObject> bones, JsonObject poses) {
        Matrix4f parent = bone.has("parent") ? boneTransform(bones.get(bone.get("parent").getAsString()), bones, poses) : new Matrix4f();
        String name = bone.get("name").getAsString();
        if (!poses.has(name)) return parent.mul(geoRotation(bone));
        JsonObject pose = poses.getAsJsonObject(name), posed = bone.deepCopy();
        Vector3f position = poseVector(pose, "position", 0), scale = poseVector(pose, "scale", 1);
        Vector3f rotation = vector(bone, "rotation", 0).add(poseVector(pose, "rotation", 0));
        JsonArray angles = new JsonArray(); angles.add(rotation.x); angles.add(rotation.y); angles.add(rotation.z);
        posed.add("rotation", angles);
        Vector3f pivot = vector(bone, "pivot", 0); pivot.x = -pivot.x;
        return parent.translate(-position.x, position.y, position.z).mul(geoRotation(posed))
                .translate(pivot).scale(scale).translate(new Vector3f(pivot).negate());
    }

    private static Vector3f poseVector(JsonObject pose, String property, float fallback) {
        if (!pose.has(property)) return new Vector3f(fallback);
        JsonElement value = pose.get(property);
        if (value.isJsonArray()) {
            JsonObject wrapper = new JsonObject(); wrapper.add("vector", value);
            return vector(wrapper, "vector", fallback);
        }
        return vector(value.getAsJsonObject(), "vector", fallback);
    }

    /** Sample supported animation tracks during baking so collision stays independent of client ticks. */
    private static JsonObject sample(JsonObject animation, float time) {
        JsonObject result = new JsonObject();
        for (var bone : animation.entrySet()) {
            JsonObject pose = new JsonObject();
            for (var property : bone.getValue().getAsJsonObject().entrySet()) {
                JsonObject track = property.getValue().getAsJsonObject();
                if (track.has("vector")) { pose.add(property.getKey(), track); continue; }
                TreeMap<Float, JsonObject> keys = new TreeMap<>();
                track.entrySet().forEach(key -> keys.put(Float.parseFloat(key.getKey()), key.getValue().getAsJsonObject()));
                var before = keys.floorEntry(time); var after = keys.ceilingEntry(time);
                if (before == null) before = keys.firstEntry();
                if (after == null) after = keys.lastEntry();
                Vector3f value = vector(before.getValue(), "vector", 0);
                if (!before.getKey().equals(after.getKey())) value.lerp(vector(after.getValue(), "vector", 0),
                        (time - before.getKey()) / (after.getKey() - before.getKey()));
                JsonObject frame = new JsonObject(); JsonArray coordinates = new JsonArray();
                coordinates.add(value.x); coordinates.add(value.y); coordinates.add(value.z);
                frame.add("vector", coordinates); pose.add(property.getKey(), frame);
            }
            result.add(bone.getKey(), pose);
        }
        return result;
    }

    private static Matrix4f geoRotation(JsonObject object) {
        Vector3f angles = vector(object, "rotation", 0);
        return geoRotation(vector(object, "pivot", 0), angles.x, angles.y, angles.z);
    }

    /** Convert GEO Euler angles (degrees) around the given model-space pivot. */
    public static Matrix4f geoRotation(Vector3fc pivot, float x, float y, float z) {
        Vector3f mirroredPivot = new Vector3f(-pivot.x(), pivot.y(), pivot.z());
        float radians = (float)Math.PI / 180;
        return new Matrix4f().translate(mirroredPivot).rotateZ(z * radians).rotateY(-y * radians)
                .rotateX(-x * radians).translate(mirroredPivot.negate());
    }

    static void addDisk(List<Element> boxes, Direction.Axis depthAxis, float center, float centerY,
                        float radius, float minDepth, float maxDepth) {
        float band = 4; // Broad bands keep the outline and collision cheap.
        for (float y = centerY - radius; y < centerY + radius; y += band) {
            float top = Math.min(centerY + radius, y + band);
            float dy = Math.max(0, Math.abs((y + top) / 2 - centerY) - (top - y) / 2);
            float width = (float)Math.sqrt(radius * radius - dy * dy);
            boxes.add(depthAxis == Direction.Axis.X
                    ? new Element(minDepth, y, center - width, maxDepth, top, center + width)
                    : new Element(center - width, y, minDepth, center + width, top, maxDepth));
        }
    }

    private static Cube bounds(List<Cube> cubes) {
        Vector3f min = new Vector3f(Float.POSITIVE_INFINITY), max = new Vector3f(Float.NEGATIVE_INFINITY);
        for (Cube cube : cubes) { min.min(cube.lower); max.max(cube.upper); }
        return new Cube(min, max, new Matrix4f());
    }

    private static VoxelShape rotate(VoxelShape shape, float x, float y) {
        if (x == 0 && y == 0) return shape;
        Matrix4f matrix = new Matrix4f().translation(8, 8, 8).rotateY((float)Math.toRadians(-y))
                .rotateX((float)Math.toRadians(-x)).translate(-8, -8, -8);
        List<Cube> cubes = new ArrayList<>();
        shape.forEachBox((a,b,c,d,e,f) -> cubes.add(new Cube(new Vector3f((float)a*16,(float)b*16,(float)c*16),
                new Vector3f((float)d*16,(float)e*16,(float)f*16), matrix)));
        return rasterize(cubes);
    }

    private static VoxelShape rasterize(List<Cube> cubes) {
        if (cubes.isEmpty()) return VoxelShapes.empty();
        Vector3f lower = new Vector3f(Float.POSITIVE_INFINITY), upper = new Vector3f(Float.NEGATIVE_INFINITY);
        for (Cube cube : cubes) { lower.min(cube.lower); upper.max(cube.upper); }
        int ox = floor(lower.x), oy = floor(lower.y), oz = floor(lower.z);
        int nx = ceil(upper.x)-ox, ny = ceil(upper.y)-oy, nz = ceil(upper.z)-oz;
        BitSetVoxelSet voxels = new BitSetVoxelSet(nx, ny, nz);
        Vector3f point = new Vector3f();
        for (Cube cube : cubes) {
            if (cube.max.x <= cube.min.x || cube.max.y <= cube.min.y || cube.max.z <= cube.min.z) continue;
            Matrix4f inverse = new Matrix4f(cube.matrix).invert();
            Vector3f margin = new Vector3f(Math.abs(inverse.m00())+Math.abs(inverse.m10())+Math.abs(inverse.m20()),
                    Math.abs(inverse.m01())+Math.abs(inverse.m11())+Math.abs(inverse.m21()),
                    Math.abs(inverse.m02())+Math.abs(inverse.m12())+Math.abs(inverse.m22())).mul(STEP/2-0.0001f);
            for (int x = floor(cube.lower.x); x < ceil(cube.upper.x); x++)
                for (int y = floor(cube.lower.y); y < ceil(cube.upper.y); y++)
                    for (int z = floor(cube.lower.z); z < ceil(cube.upper.z); z++) {
                        inverse.transformPosition(point.set((x+.5f)*STEP,(y+.5f)*STEP,(z+.5f)*STEP));
                        if (point.x > cube.min.x-margin.x && point.x < cube.max.x+margin.x
                                && point.y > cube.min.y-margin.y && point.y < cube.max.y+margin.y
                                && point.z > cube.min.z-margin.z && point.z < cube.max.z+margin.z)
                            voxels.set(x-ox,y-oy,z-oz);
                    }
        }
        return new GridShape(voxels, coordinates(ox,nx), coordinates(oy,ny), coordinates(oz,nz)).simplify();
    }

    private static int floor(float value) { return (int)Math.floor(value/STEP+0.0001); }
    private static int ceil(float value) { return (int)Math.ceil(value/STEP-0.0001); }
    private static double[] coordinates(int offset, int size) {
        double[] result = new double[size+1];
        for (int i=0;i<=size;i++) result[i]=(offset+i)*STEP/16;
        return result;
    }
    private static float number(JsonObject object, String key, float fallback) {
        return object.has(key) ? object.get(key).getAsFloat() : fallback;
    }
    private static Vector3f vector(JsonObject object, String key, float fallback) {
        if (!object.has(key)) return new Vector3f(fallback);
        JsonArray array = object.getAsJsonArray(key);
        return new Vector3f(array.get(0).getAsFloat(),array.get(1).getAsFloat(),array.get(2).getAsFloat());
    }
    private static final class GridShape extends ArrayVoxelShape {
        private GridShape(VoxelSet voxels, double[] x, double[] y, double[] z) { super(voxels,x,y,z); }
    }
    private static final class Cube {
        private final Vector3f min, max, lower = new Vector3f(Float.POSITIVE_INFINITY), upper = new Vector3f(Float.NEGATIVE_INFINITY);
        private final Matrix4f matrix;

        private Cube axisAligned() {
            return new Cube(new Vector3f(lower), new Vector3f(upper), new Matrix4f());
        }

        private Cube(Vector3f min, Vector3f max, Matrix4f matrix) {
            this.min=min; this.max=max; this.matrix=matrix;
            for (int i=0;i<8;i++) {
                Vector3f corner=matrix.transformPosition(new Vector3f((i&1)==0?min.x:max.x,(i&2)==0?min.y:max.y,(i&4)==0?min.z:max.z));
                lower.min(corner); upper.max(corner);
            }
        }
    }
}
