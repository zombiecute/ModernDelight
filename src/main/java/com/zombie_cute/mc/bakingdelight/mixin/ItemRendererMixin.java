package com.zombie_cute.mc.bakingdelight.mixin;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
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
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Unique
    private static final Identifier UP = new Identifier(ModernDelightMain.MOD_ID, "item/holder_up.png");
    @Unique
    private static final Identifier DOWN = new Identifier(ModernDelightMain.MOD_ID, "item/holder_down.png");
    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;" +
                    "Lnet/minecraft/client/render/model/json/ModelTransformationMode;" +
                    "ZLnet/minecraft/client/util/math/MatrixStack;" +
                    "Lnet/minecraft/client/render/VertexConsumerProvider;" +
                    "IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("RETURN") // 在 renderItem 方法执行完成后注入
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
            // 获取内部物品的 ItemStack
            ItemStack insideStack = StoneMortarItem.getInsideStack(stack);
            if (!insideStack.isEmpty()) {
                // 调整矩阵变换（偏移到上方，缩小75%）
                matrices.push();
                matrices.translate(-0.2, 0.25, 0.015);
                matrices.scale(0.75f, 0.75f, 0.75f);
                // 渲染内部物品的贴图
                BakedModel insideModel = itemRenderer.getModel(insideStack, null, null, 0);
                itemRenderer.renderItem(insideStack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, insideModel);
                matrices.pop();
            }
        }
        if (stack.getItem() == ModItems.HOLDER) {
            // 渲染内部物品（中层，左上角，75%大小）
            ItemStack insideStack = HolderItem.getHoldingStack(stack);
            if (!insideStack.isEmpty()) {
                matrices.push();
                // 调整位置和缩放
                matrices.translate(-0.2, 0.2, 0.015); // 左上角偏移（根据效果调整数值）
                matrices.scale(0.75f, 0.75f, 0.75f); // 缩小到75%
                // 渲染内部物品
                BakedModel insideModel = itemRenderer.getModel(insideStack, null, null, 0);
                itemRenderer.renderItem(insideStack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, insideModel);
                matrices.pop();
            }
            // 渲染顶层（顶层，原始大小）
            ItemStack upLayer = ModItems.HOLDER_UP.getDefaultStack();
            matrices.push();
            // 调整位置
            matrices.translate(0, 0, 0.03); // 轻微Z轴偏移确保顶层覆盖
            // 渲染顶层
            BakedModel upLayerModel = itemRenderer.getModel(upLayer, null, null, 0);
            itemRenderer.renderItem(upLayer, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, upLayerModel);
            matrices.pop();
        }
    }
}
