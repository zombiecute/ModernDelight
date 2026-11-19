package com.zombie_cute.mc.moderndelight.block.multiblock;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class LargeMachineOutlineRenderer {
    // World-dependent shapes may be temporary; do not retain them after their blocks disappear.
    private static final Map<VoxelShape, List<Edge>> EDGES = new WeakHashMap<>();

    private LargeMachineOutlineRenderer() {}

    public static void register() {
        WorldRenderEvents.BLOCK_OUTLINE.register((context, outline) -> {
            BlockState state = outline.blockState();
            BlockPos pos = outline.blockPos();
            if (state.getBlock() instanceof LargeMachinePartBlock part) {
                pos = part.getMachinePos(state, pos);
                state = context.world().getBlockState(pos);
            }
            if (!Registries.BLOCK.getId(state.getBlock()).getNamespace().equals("moderndelight")) return true;
            VoxelShape shape = state.getBlock() instanceof LargeMachineBlock machine
                    ? machine.getMachineShape(state) : state.getOutlineShape(context.world(), pos);
            MatrixStack.Entry matrices = context.matrixStack().peek();
            VertexConsumer vertices = outline.vertexConsumer();
            float x = (float)(pos.getX() - outline.cameraX());
            float y = (float)(pos.getY() - outline.cameraY());
            float z = (float)(pos.getZ() - outline.cameraZ());
            // Traverse the union once per cached shape. drawShapeOutline outlines every cuboid separately.
            for (Edge edge : EDGES.computeIfAbsent(shape, LargeMachineOutlineRenderer::edges)) {
                vertices.vertex(matrices.getPositionMatrix(), x + edge.x1, y + edge.y1, z + edge.z1)
                        .color(0f, 0f, 0f, .4f).normal(matrices.getNormalMatrix(), edge.nx, edge.ny, edge.nz).next();
                vertices.vertex(matrices.getPositionMatrix(), x + edge.x2, y + edge.y2, z + edge.z2)
                        .color(0f, 0f, 0f, .4f).normal(matrices.getNormalMatrix(), edge.nx, edge.ny, edge.nz).next();
            }
            return false;
        });
    }

    private static List<Edge> edges(VoxelShape shape) {
        List<Edge> result = new ArrayList<>();
        shape.forEachEdge((x1, y1, z1, x2, y2, z2) -> {
            float dx = (float)(x2-x1), dy = (float)(y2-y1), dz = (float)(z2-z1);
            float length = (float)Math.sqrt(dx*dx + dy*dy + dz*dz);
            if (length > 0) result.add(new Edge((float)x1, (float)y1, (float)z1, (float)x2, (float)y2, (float)z2,
                    dx/length, dy/length, dz/length));
        });
        return List.copyOf(result);
    }

    private record Edge(float x1, float y1, float z1, float x2, float y2, float z2, float nx, float ny, float nz) {}
}
