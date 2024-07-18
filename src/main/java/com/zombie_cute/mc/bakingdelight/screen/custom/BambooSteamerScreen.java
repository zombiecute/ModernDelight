package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BambooSteamerScreen extends HandledScreen<BambooSteamerScreenHandler> {
    private static final Identifier TEXTURE_1 = new Identifier(Bakingdelight.MOD_ID,
            "textures/gui/bamboo_steamer_gui.png");
    private static final Identifier TEXTURE_2 = new Identifier(Bakingdelight.MOD_ID,
            "textures/gui/bamboo_steamer2_gui.png");
    private static final Identifier TEXTURE_3 = new Identifier(Bakingdelight.MOD_ID,
            "textures/gui/bamboo_steamer3_gui.png");
    private static final Identifier TEXTURE_4 = new Identifier(Bakingdelight.MOD_ID,
            "textures/gui/bamboo_steamer4_gui.png");
    public BambooSteamerScreen(BambooSteamerScreenHandler handler, PlayerInventory inventory, Text title) {
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
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        switch (handler.getCurrentLayer()){
            case 1 -> {
                RenderSystem.setShaderTexture(0,TEXTURE_1);
                context.drawTexture(TEXTURE_1, x, y, 0, 0, backgroundWidth,backgroundHeight);
                if (handler.isCovered()){
                    context.drawTexture(TEXTURE_1, x + 151, y + 57, 176, 11, 18,2);
                }
            }
            case 2 -> {
                RenderSystem.setShaderTexture(0,TEXTURE_2);
                context.drawTexture(TEXTURE_2, x, y, 0, 0, backgroundWidth,backgroundHeight);
                if (handler.isCovered()){
                    context.drawTexture(TEXTURE_2, x + 151, y + 53, 176, 11, 18,2);
                }

            }
            case 3 -> {
                RenderSystem.setShaderTexture(0,TEXTURE_3);
                context.drawTexture(TEXTURE_3, x, y, 0, 0, backgroundWidth,backgroundHeight);
                if (handler.isCovered()){
                    context.drawTexture(TEXTURE_3, x + 151, y + 49, 176, 11, 18,2);
                }

            }
            case 4 -> {
                RenderSystem.setShaderTexture(0,TEXTURE_4);
                context.drawTexture(TEXTURE_4, x, y, 0, 0, backgroundWidth,backgroundHeight);
                if (handler.isCovered()){
                    context.drawTexture(TEXTURE_4, x + 151, y + 45, 176, 11, 18,2);
                }

            }
        }
        if (handler.isHeated()){
            context.drawTexture(TEXTURE_1, x + 151, y + 65, 176, 0, 18,11);
        }
        if (handler.getLayer() != 0){
            context.drawText(textRenderer,String.valueOf(handler.getLayer()),x + 157, y + 10,0xffffff,true);
        } else {
            context.drawText(textRenderer,String.valueOf(0),x + 157, y + 10,0xff0000,true);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
