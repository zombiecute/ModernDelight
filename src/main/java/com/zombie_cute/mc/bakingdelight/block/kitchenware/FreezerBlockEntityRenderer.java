package com.zombie_cute.mc.bakingdelight.block.kitchenware;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

public class FreezerBlockEntityRenderer implements BlockEntityRenderer<FreezerBlockEntity> {
    public FreezerBlockEntityRenderer(BlockEntityRendererFactory.Context context){
    }

    @Override
    public void render(FreezerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack ice_slot = entity.getIceSlot();
        ItemStack craft_slot = entity.getCraftSlot();
        ItemStack slot_1 = entity.getSlot1();
        ItemStack slot_2 = entity.getSlot2();

        matrices.push();
        matrices.translate(0.7f, 0.25f,0.3f);
        matrices.scale(0.4f,0.4f,0.4f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
        itemRenderer.renderItem(ice_slot,
                ModelTransformationMode.GUI,
                getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,entity.getWorld(),
                1);
        matrices.pop();

        matrices.push();
        matrices.translate(0.3f, 0.25f,0.7f);
        matrices.scale(0.4f,0.4f,0.4f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
        itemRenderer.renderItem(craft_slot,
                ModelTransformationMode.GUI,
                getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,entity.getWorld(),
                1);
        matrices.pop();

        matrices.push();
        matrices.translate(0.3f, 0.7f,0.3f);
        matrices.scale(0.4f,0.4f,0.4f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
        itemRenderer.renderItem(slot_1,
                ModelTransformationMode.GUI,
                getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,entity.getWorld(),
                1);
        matrices.pop();

        matrices.push();
        matrices.translate(0.7f, 0.7f,0.7f);
        matrices.scale(0.4f,0.4f,0.4f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45));
        itemRenderer.renderItem(slot_2,
                ModelTransformationMode.GUI,
                getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,entity.getWorld(),
                1);
        matrices.pop();
    }
    private int getLightLevel(World world, BlockPos pos){
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight,skyLight);
    }
}
