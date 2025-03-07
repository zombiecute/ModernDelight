package com.zombie_cute.mc.bakingdelight.block.kitchenware.decor;

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

public class WoodenPlateBlockEntityRenderer implements BlockEntityRenderer<WoodenPlateBlockEntity> {
    public WoodenPlateBlockEntityRenderer(BlockEntityRendererFactory.Context context){
    }

    @Override
    public void render(WoodenPlateBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack renderStack = entity.getRenderStack();
        matrices.push();
        matrices.translate(0.5f, 0.1f,0.5f);
        matrices.scale(0.8f,0.8f,0.8f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
        itemRenderer.renderItem(renderStack, ModelTransformationMode.FIXED, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos){
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight,skyLight);
    }
}
