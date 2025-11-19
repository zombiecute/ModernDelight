package com.zombie_cute.mc.bakingdelight.mixin;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.tools.HolderItem;
import com.zombie_cute.mc.bakingdelight.item.tools.StoneMortarItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;" +
                    "Lnet/minecraft/client/render/model/json/ModelTransformationMode;" +
                    "ZLnet/minecraft/client/util/math/MatrixStack;" +
                    "Lnet/minecraft/client/render/VertexConsumerProvider;" +
                    "IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("RETURN")
    )
    public void onRenderItem(
            ItemStack stack,
            ModelTransformationMode renderMode,
            boolean leftHanded,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            BakedModel model,
            CallbackInfo ci
    ) {
        ItemRenderer itemRenderer = (ItemRenderer) (Object) this;
        if (stack.getItem() == ModItems.STONE_MORTAR) {
            ItemStack insideStack = StoneMortarItem.getInsideStack(stack);
            if (!insideStack.isEmpty()) {
                matrices.push();
                matrices.translate(-0.2, 0.25, 0.015);
                matrices.scale(0.75f, 0.75f, 0.75f);
                BakedModel insideModel = itemRenderer.getModel(insideStack, null, null, 0);
                itemRenderer.renderItem(insideStack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, insideModel);
                matrices.pop();
            }
        }
        if (stack.getItem() == ModItems.HOLDER) {
            ItemStack insideStack = HolderItem.getHoldingStack(stack);
            if (!insideStack.isEmpty()) {
                matrices.push();
                matrices.translate(-0.2, 0.2, 0.015);
                matrices.scale(0.75f, 0.75f, 0.75f);
                BakedModel insideModel = itemRenderer.getModel(insideStack, null, null, 0);
                itemRenderer.renderItem(insideStack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, insideModel);
                matrices.pop();
            }
            ItemStack upLayer = ModItems.HOLDER_UP.getDefaultStack();
            matrices.push();
            matrices.translate(0, 0, 0.03);
            BakedModel upLayerModel = itemRenderer.getModel(upLayer, null, null, 0);
            itemRenderer.renderItem(upLayer, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, upLayerModel);
            matrices.pop();
        }
    }
}
