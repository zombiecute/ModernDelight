package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.ChargingPostBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.ChargingPostBlockEntity;
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
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

public class ChargingPostBlockEntityRenderer implements BlockEntityRenderer<ChargingPostBlockEntity> {
    public ChargingPostBlockEntityRenderer(BlockEntityRendererFactory.Context context){

    }

    @Override
    public void render(ChargingPostBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getRendererStack();
        Direction dir = entity.getCachedState().get(ChargingPostBlock.FACING);
        if (stack.isOf(ModBlocks.CHARGING_POST.asItem())){
            matrices.push();
            switch (dir){
                case NORTH -> matrices.translate(0.5f, 0.25f,0.25f);
                case EAST -> matrices.translate(0.75f, 0.25f,0.5f);
                case WEST -> matrices.translate(0.25f, 0.25f,0.5f);
                case SOUTH -> matrices.translate(0.5f, 0.25f,0.75f);
            }
            matrices.scale(0.5f,0.5f,0.5f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
            itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();
        } else {
            matrices.push();
            switch (dir){
                case NORTH -> matrices.translate(0.5f, 0.25f,0.25f);
                case EAST -> matrices.translate(0.75f, 0.25f,0.5f);
                case WEST -> matrices.translate(0.25f, 0.25f,0.5f);
                case SOUTH -> matrices.translate(0.5f, 0.25f,0.75f);
            }
            matrices.scale(0.3f,0.3f,0.3f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
            itemRenderer.renderItem(stack, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();
        }

    }

    private int getLightLevel(World world, BlockPos pos){
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight,skyLight);
    }
}
