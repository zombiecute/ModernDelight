package com.zombie_cute.mc.bakingdelight.block.kitchenware.juice_extractor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.Objects;

public class JuiceExtractorBlockEntityRender extends GeoBlockRenderer<JuiceExtractorBlockEntity> {
    public JuiceExtractorBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new JuiceExtractorBlockEntityModel());
    }

    @Override
    public void defaultRender(
            MatrixStack matrices,
            JuiceExtractorBlockEntity entity,
            VertexConsumerProvider vertexConsumers,
            RenderLayer renderType,
            VertexConsumer buffer,
            float yaw,
            float partialTick,
            int packedLight
    ) {
        super.defaultRender(matrices, entity, vertexConsumers, renderType, buffer, yaw, partialTick, packedLight);
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        DefaultedList<ItemStack> items = entity.getItems();
        for (int i = 0; i < 4;i++){
            matrices.push();
            switch (i){
                case 0 -> {
                    matrices.translate(0.4f, 0.45f, 0.4f);
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(280));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(130));
                }
                case 1 -> {
                    matrices.translate(0.4f, 0.5f, 0.6f);
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(190));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(160));
                }
                case 2 -> {
                    matrices.translate(0.6f, 0.45f,0.4f);
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(260));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(200));
                }
                case 3 -> {
                    matrices.translate(0.6f, 0.5f,0.6f);
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(185));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(60));
                }
            }
            matrices.scale(0.4f,0.4f,0.4f);
            itemRenderer.renderItem(items.get(i), ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();
        }
    }
    private int getLightLevel(World world, BlockPos pos){
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight,skyLight);
    }
}
