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
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

public class KitchenUtensilHolderBlockEntityRender implements BlockEntityRenderer<KitchenUtensilHolderBlockEntity> {
    public KitchenUtensilHolderBlockEntityRender(BlockEntityRendererFactory.Context context){

    }

    @Override
    public void render(KitchenUtensilHolderBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        DefaultedList<ItemStack> items = entity.getItems();

        Direction dir = entity.getCachedState().get(Properties.HORIZONTAL_FACING);

        switch(dir) {
            case NORTH:
                //1
                int i = 0;
                while(i < 4) {
                    matrices.push();
                    matrices.translate(0.8f - i * 0.2f, 0.65f, 0.9f);
                    matrices.scale(0.35f, 0.38f, 0.35f);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(30));
                    BlockPos pos = new BlockPos(entity.getPos().getX(), entity.getPos().getY() + 1, entity.getPos().getZ());
                    itemRenderer.renderItem(items.get(i), ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()), pos), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    matrices.pop();
                    i++;
                }
                break;
            case SOUTH:
                // 2
                int j = 0;
                while(j < 4) {
                    matrices.push();
                    matrices.translate(0.2f + j * 0.2f, 0.65f, 0.1f);
                    matrices.scale(0.35f, 0.4f, 0.35f);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(60));
                    BlockPos pos = new BlockPos(entity.getPos().getX(), entity.getPos().getY() + 1, entity.getPos().getZ());
                    itemRenderer.renderItem(items.get(j), ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()), pos), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    matrices.pop();
                    j++;
                }
                break;
            case EAST:
                //3
                int u = 0;
                while(u < 4) {
                    matrices.push();
                    matrices.translate(0.1, 0.65f, 0.8f - u * 0.2f);
                    matrices.scale(0.35f, 0.4f, 0.35f);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(115));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
                    BlockPos pos = new BlockPos(entity.getPos().getX(), entity.getPos().getY() + 1, entity.getPos().getZ());
                    itemRenderer.renderItem(items.get(u), ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()), pos), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    matrices.pop();
                    u++;
                }
                break;
            case WEST:
                //4
                int k = 0;
                while(k < 4) {
                    matrices.push();
                    matrices.translate(0.9f, 0.65f, 0.2f + k * 0.2f);
                    matrices.scale(0.35f, 0.4f, 0.35f);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(145));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
                    BlockPos pos = new BlockPos(entity.getPos().getX(), entity.getPos().getY() + 1, entity.getPos().getZ());
                    itemRenderer.renderItem(items.get(k), ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()), pos), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
                    matrices.pop();
                    k++;
                }
                break;
        }
    }
    private int getLightLevel(World world, BlockPos pos){
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight,skyLight);
    }

}
