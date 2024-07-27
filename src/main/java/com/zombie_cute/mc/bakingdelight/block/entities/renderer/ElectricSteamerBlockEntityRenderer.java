package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.block.entities.ElectricSteamerBlockEntity;
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
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

public class ElectricSteamerBlockEntityRenderer implements BlockEntityRenderer<ElectricSteamerBlockEntity> {
    public ElectricSteamerBlockEntityRenderer(BlockEntityRendererFactory.Context context){

    }

    @Override
    public void render(ElectricSteamerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        DefaultedList<ItemStack> inv = entity.getItems();
        for (int i = 0; i < inv.size(); i++) {
            matrices.push();
            float y = (i >= 4) ? 0.625f : 0.8125f;
            float x = (i % 4 < 2) ? 0.4375f : 0.625f;
            float z = (i % 2 == 0) ? 0.625f : 0.4375f;

            matrices.translate(x, y, z);
            matrices.scale(0.1f, 0.1f, 0.1f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

            itemRenderer.renderItem(
                    inv.get(i),
                    ModelTransformationMode.GUI,
                    getLightLevel(Objects.requireNonNull(entity.getWorld()), entity.getPos()),
                    OverlayTexture.DEFAULT_UV,
                    matrices,
                    vertexConsumers,
                    entity.getWorld(),
                    1
            );
            matrices.pop();
        }
    }

    private int getLightLevel(World world, BlockPos pos){
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight,skyLight);
    }
}
