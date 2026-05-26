package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.WoodenBasinBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.util.FluidStackRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class WoodenBasinScreen extends HandledScreen<WoodenBasinScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(ModernDelightMain.MOD_ID,
            "textures/gui/wooden_basin_gui.png");
    public WoodenBasinScreen(WoodenBasinScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    private FluidStackRenderer fluidStackRenderer;
    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        assignFluidStackRenderer();
    }
    boolean b;
    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth,backgroundHeight);

        renderFluid(context, x, y);
        b = mouseX >= x + 46 && mouseX <= x + 65 && mouseY >= y + 20 && mouseY <= y + 68;
        if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
            context.drawTexture(TEXTURE,x+161,y+5,194,13,11,11);
        }
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        if (b){
            context.drawTooltip(textRenderer,fluidStackRenderer.getTooltip(handler.blockEntity.getFluidStackCopy()),
                    Optional.empty(),x,y);
        }
        super.drawMouseoverTooltip(context, x, y);
    }
    private void assignFluidStackRenderer(){
        fluidStackRenderer = new FluidStackRenderer(WoodenBasinBlockEntity.MAX_FLUID_LEVEL,18,47);
    }
    private void renderFluid(DrawContext context, int x, int y) {
        fluidStackRenderer.drawFluid(context,handler.blockEntity.getFluidStackCopy(),
                x+47,y+21,18,47);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
