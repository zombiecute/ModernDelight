package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DeepFryerScreen extends HandledScreen<DeepFryerScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/deep_fryer_gui.png");
    public DeepFryerScreen(DeepFryerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow1(context, x, y);
        renderProgressArrow2(context, x, y);
        renderProgressArrow3(context, x, y);
        renderProgressArrow4(context, x, y);
        renderOilLevel(context,x,y);
        if (handler.isBurning()){
            context.drawTexture(TEXTURE, x + 80, y + 48, 0, 167, 18, 11);
        }
        if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
            context.drawTexture(TEXTURE,x+161,y+5,194,13,11,11);
        }
    }
    private void renderOilLevel(DrawContext context, int x, int y) {
        if (handler.hasOil()){
            int offset = 24 - handler.getScaledOilLevel();
            context.drawTexture(TEXTURE, x + 127, y + 21 + offset,
                    176, offset, 4, handler.getScaledOilLevel());
            context.drawTexture(TEXTURE, x + 47, y + 21 + offset,
                    176, offset, 4, handler.getScaledOilLevel());
        }
    }
    private void renderProgressArrow1(DrawContext context, int x, int y) {
        context.drawTexture(TEXTURE, x + 54, y + 41, 0, 178, handler.getScaledProgress(0), 4);
    }
    private void renderProgressArrow2(DrawContext context, int x, int y) {
        context.drawTexture(TEXTURE, x + 72, y + 41, 0, 178, handler.getScaledProgress(1), 4);
    }
    private void renderProgressArrow3(DrawContext context, int x, int y) {
        context.drawTexture(TEXTURE, x + 90, y + 41, 0, 178, handler.getScaledProgress(2), 4);
    }
    private void renderProgressArrow4(DrawContext context, int x, int y) {
        context.drawTexture(TEXTURE, x + 108, y + 41, 0, 178, handler.getScaledProgress(3), 4);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
