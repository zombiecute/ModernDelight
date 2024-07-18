package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.BambooGrateBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.BambooGrateBlockEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BambooGrateBlockEntityRenderer implements BlockEntityRenderer<BambooGrateBlockEntity> {
    public BambooGrateBlockEntityRenderer(BlockEntityRendererFactory.Context context){
    }
    @Override
    public void render(BambooGrateBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = ModBlocks.BAMBOO_COVER.asItem().getDefaultStack();
        int layer = entity.getCachedState().get(BambooGrateBlock.LAYER);
        boolean covered = entity.getCachedState().get(BambooGrateBlock.COVERED);
        if (covered){
            matrices.push();
            switch (layer){
                case 1 -> matrices.translate(0.5f, 0.5f,0.5f);
                case 2 -> matrices.translate(0.5f, 0.75f,0.5f);
                case 3 -> matrices.translate(0.5f, 1.0f,0.5f);
                case 4 -> matrices.translate(0.5f, 1.25f,0.5f);
            }
            matrices.scale(2,2,2);
            itemRenderer.renderItem(stack, ModelTransformationMode.GROUND,
                    getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();
        } else {
            if (entity.getWorld()!=null){
                if (layer == 4 && !entity.getWorld().isAir(entity.getPos().up())) return;
            } else return;
            List<ItemStack> renderStacks = new ArrayList<>();
            switch (layer){
                case 1 -> {
                    for (int i=0;i<4;i++){
                        renderStacks.add(entity.getStack(i));
                    }
                }
                case 2 -> {
                    for (int i=4;i<8;i++){
                        renderStacks.add(entity.getStack(i));
                    }
                }
                case 3 -> {
                    for (int i=8;i<12;i++){
                        renderStacks.add(entity.getStack(i));
                    }
                }
                case 4 -> {
                    for (int i=12;i<16;i++){
                        renderStacks.add(entity.getStack(i));
                    }
                }
            }
            float y = (float) (0.15 + 0.25 * (layer - 1));

            matrices.push();
            matrices.translate(0.3f, y,0.3f);
            matrices.scale(0.3f,0.3f,0.3f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(renderStacks.get(0), ModelTransformationMode.GUI,
                    getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();

            matrices.push();
            matrices.translate(0.3f, y,0.7f);
            matrices.scale(0.3f,0.3f,0.3f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(renderStacks.get(1), ModelTransformationMode.GUI,
                    getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();

            matrices.push();
            matrices.translate(0.7f, y,0.3f);
            matrices.scale(0.3f,0.3f,0.3f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(renderStacks.get(2), ModelTransformationMode.GUI,
                    getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();

            matrices.push();
            matrices.translate(0.7f, y,0.7f);
            matrices.scale(0.3f,0.3f,0.3f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(renderStacks.get(3), ModelTransformationMode.GUI,
                    getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();
            renderStacks.clear();
        }
    }
    private int getLightLevel(World world, BlockPos pos){
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight,skyLight);
    }
}
