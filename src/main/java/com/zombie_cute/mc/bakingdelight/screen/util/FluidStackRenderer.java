package com.zombie_cute.mc.bakingdelight.screen.util;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Originally by Flandre923
 **/
public class FluidStackRenderer {
    // 容积
    public final long capacityDrop;
    private final int width;
    private final int height;

    public FluidStackRenderer(){
        this(FluidStack.convertDropletsToMb(FluidConstants.BUCKET),16,16);
    }

    public FluidStackRenderer(long capacityDrop, int width, int height){
        Preconditions.checkArgument(capacityDrop >0,"capacity must be > 0");
        Preconditions.checkArgument(width>0,"width must be > 0");
        Preconditions.checkArgument(height>0,"height must be > 0");
        this.capacityDrop = capacityDrop;
        this.width = width;
        this.height = height;
    }
    // 绘制流体
    public void drawFluid(DrawContext context, FluidStack fluidStack, int x, int y, int width, int height){
        if(fluidStack.getFluidVariant().getFluid() == Fluids.EMPTY){
            return;
        }
        y+=height;
        final Sprite sprite = FluidVariantRendering.getSprite(fluidStack.getFluidVariant());
        int color = FluidVariantRendering.getColor(fluidStack.getFluidVariant());

        final int drawHeight = (int)(fluidStack.getAmount()/(capacityDrop*1f)*height);
        if (sprite != null) {
            final int iconHeight = sprite.getContents().getHeight();
            int offsetHeight = drawHeight;
            RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, 1F);
            int iteration = 0;
            while(offsetHeight != 0){
                final int curHeight = Math.min(offsetHeight, iconHeight);

                context.drawSprite(x,y-offsetHeight,0,width,curHeight,sprite);
                offsetHeight -= curHeight;
                iteration ++;
                if (iteration>50){
                    break;
                }
            }
            context.setShaderColor(1f,1f,1f,1f);
            RenderSystem.setShaderTexture(0, FluidRenderHandlerRegistry.INSTANCE.get(fluidStack.getFluidVariant().getFluid())
                    .getFluidSprites(MinecraftClient.getInstance().world, null, fluidStack.getFluidVariant().getFluid().getDefaultState())
                    [0].getAtlasId());
        }
    }

    public List<Text> getTooltip(FluidStack fluidStack) {
        // 返回的提示信息
        List<Text> tooltip = new ArrayList<>();
        FluidVariant fluidVariant = fluidStack.getFluidVariant();
        if(fluidVariant == null){
            return tooltip;
        }
        // 构建第一个信息是显示的流体的类型是什么
        MutableText displayName = Text.translatable(fluidVariant.getFluid().getDefaultState().getBlockState().getBlock().getTranslationKey());
        tooltip.add(displayName);

        long amount = fluidStack.getAmount();
        // 显示流体的数据
        MutableText amountString = Text.literal(FluidStack.convertDropletsToMb(amount)+" mB");
        tooltip.add(amountString.fillStyle(Style.EMPTY.withColor(Formatting.GRAY)));
        return tooltip;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
