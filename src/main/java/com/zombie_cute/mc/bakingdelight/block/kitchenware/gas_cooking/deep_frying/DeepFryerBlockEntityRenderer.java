package com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
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
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

public class DeepFryerBlockEntityRenderer implements BlockEntityRenderer<DeepFryerBlockEntity>{
    public DeepFryerBlockEntityRenderer(BlockEntityRendererFactory.Context context){
    }
    @Override
    public void render(DeepFryerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = ModBlocks.DEEP_FRY_BASKET.asItem().getDefaultStack();
        DefaultedList<ItemStack> items = entity.getItems();
        Direction direction = entity.getCachedState().get(DeepFryerBlock.FACING);

        matrices.push();
        matrices.translate(0.5f, 0.4f,0.5f);
        matrices.scale(2,2,2);
        switch (direction){
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
        }
        itemRenderer.renderItem(stack, ModelTransformationMode.GROUND,
                getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
        matrices.pop();

        matrices.push();
        matrices.translate(0.35f, 0.6f,0.65f);
        matrices.scale(0.3f,0.3f,0.3f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(200));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(220));
        itemRenderer.renderItem(items.get(0), ModelTransformationMode.GUI,
                getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
        matrices.pop();

        matrices.push();
        matrices.translate(0.35f, 0.55f,0.35f);
        matrices.scale(0.3f,0.3f,0.3f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(120));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(60));
        itemRenderer.renderItem(items.get(1), ModelTransformationMode.GUI,
                getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
        matrices.pop();

        matrices.push();
        matrices.translate(0.65f, 0.65f,0.35f);
        matrices.scale(0.3f,0.3f,0.3f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(20));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(230));
        itemRenderer.renderItem(items.get(2), ModelTransformationMode.GUI,
                getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
        matrices.pop();

        matrices.push();
        matrices.translate(0.65f, 0.6f,0.65f);
        matrices.scale(0.3f,0.3f,0.3f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(250));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30));
        itemRenderer.renderItem(items.get(3), ModelTransformationMode.GUI,
                getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
        matrices.pop();
    }
    private int getLightLevel(World world, BlockPos pos){
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight,skyLight);
    }
}
