package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ElectricSteamerScreen extends HandledScreen<ElectricSteamerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID,
            "textures/gui/electric_steamer_gui.png");

    public ElectricSteamerScreen(ElectricSteamerScreenHandler handler, PlayerInventory inventory, Text title) {
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

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth,backgroundHeight);

        if (handler.isWorking()){
            context.drawTexture(TEXTURE,x+4,y+4,176,0,25,12);
        }
        if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
            context.drawTexture(TEXTURE,x+161,y+5,194,13,11,11);
        }
        renderProgressArrow(context,x + 54,y + 8,0);
        renderProgressArrow(context,x + 72,y + 8,1);
        renderProgressArrow(context,x + 36,y + 26,2);
        renderProgressArrow(context,x + 54,y + 26,3);
        renderProgressArrow(context,x + 72,y + 26,4);
        renderProgressArrow(context,x + 90,y + 26,5);
        renderProgressArrow(context,x + 36,y + 44,6);
        renderProgressArrow(context,x + 54,y + 44,7);
        renderProgressArrow(context,x + 72,y + 44,8);
        renderProgressArrow(context,x + 90,y + 44,9);
        renderProgressArrow(context,x + 54,y + 62,10);
        renderProgressArrow(context,x + 72,y + 62,11);

        renderSteamProgressArrow(context,x,y);
        renderSteamArrow(context,x,y);
        renderWaterArrow(context,x,y);
        if (mouseX >= x + 152 && mouseY >= y + 18 && mouseX <= x + 167 && mouseY <= y + 58){
            context.drawTooltip(this.textRenderer,Text.literal(handler.getWaterAmount()+" mB"),mouseX,mouseY);
        }
    }
    private void renderProgressArrow(DrawContext context, int x, int y, int slot) {
        if (handler.getScaledProgress(slot) != 0){
            context.drawTexture(TEXTURE, x, y, 176, 123,handler.getScaledProgress(slot),16);
        }
    }
    private void renderSteamProgressArrow(DrawContext context, int x, int y){
        if (handler.getScaledSteamProgress() != 0){
            int offset = 24 - handler.getScaledSteamProgress();
            context.drawTexture(TEXTURE,x+126+offset,y+36,194+offset,24,handler.getScaledSteamProgress(),9);
        }
    }
    private void renderSteamArrow(DrawContext context, int x, int y){
        if (handler.getScaledSteam() != 0){
            int offset = 70 - handler.getScaledSteam();
            context.drawTexture(TEXTURE,x+111,y+9+offset,176,53+offset,14,handler.getScaledSteam());
        }
    }
    private void renderWaterArrow(DrawContext context, int x, int y){
        if (handler.getScaledWater() != 0){
            int offset = 41 - handler.getScaledWater();
            context.drawTexture(TEXTURE,x+152,y+18+offset,176,12+offset,16,handler.getScaledWater());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
