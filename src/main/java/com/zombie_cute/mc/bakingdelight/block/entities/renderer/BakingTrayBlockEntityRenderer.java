package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.block.entities.BakingTrayBlockEntity;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BakingTrayBlockEntityRenderer implements BlockEntityRenderer<BakingTrayBlockEntity> {
    public BakingTrayBlockEntityRenderer(BlockEntityRendererFactory.Context context){

    }

    @Override
    public void render(BakingTrayBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack1 = entity.getRendererStack1();
        ItemStack stack2 = entity.getRendererStack2();
        ItemStack stack3 = entity.getRendererStack3();
        ItemStack stack4 = entity.getRendererStack4();

        if (!isFlat(stack1.getItem())){
            matrices.push();
            matrices.translate(0.25f, 0.15f,0.25f);
            matrices.scale(0.35f,0.35f,0.35f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(stack1, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();

            matrices.push();
            matrices.translate(0.25f, 0.15f,0.75f);
            matrices.scale(0.35f,0.35f,0.35f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(stack2, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();

            matrices.push();
            matrices.translate(0.75f, 0.15f,0.25f);
            matrices.scale(0.35f,0.35f,0.35f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(stack3, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();

            matrices.push();
            matrices.translate(0.75f, 0.15f,0.75f);
            matrices.scale(0.35f,0.35f,0.35f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(stack4, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();
        } else {
            matrices.push();
            matrices.translate(0.5f, 0.25f,0.5f);
            matrices.scale(1.0f,1.0f,1.0f);
            itemRenderer.renderItem(stack1, ModelTransformationMode.FIXED, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();

            matrices.push();
            matrices.translate(0.25f, 0.3f,0.5f);
            matrices.scale(0.35f,0.35f,0.35f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(stack2, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();

            matrices.push();
            matrices.translate(0.75f, 0.3f,0.25f);
            matrices.scale(0.35f,0.35f,0.35f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(stack3, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();

            matrices.push();
            matrices.translate(0.75f, 0.3f,0.75f);
            matrices.scale(0.35f,0.35f,0.35f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));
            itemRenderer.renderItem(stack4, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()),entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers,entity.getWorld(),1);
            matrices.pop();
        }

    }

    private boolean isFlat(Item item) {
        List<Item> items = new ArrayList<>();
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.FLAT_ON_BAKING_TRAY)){
            items.add(registryEntry.value());
        }
        return items.contains(item);
    }

    private int getLightLevel(World world, BlockPos pos){
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight,skyLight);
    }
}
