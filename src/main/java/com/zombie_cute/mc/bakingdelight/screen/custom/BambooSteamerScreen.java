package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
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
    private static final Identifier TEXTURE_1 = new Identifier(ModernDelightMain.MOD_ID,
            "textures/gui/bamboo_steamer_gui.png");
    private static final Identifier TEXTURE_2 = new Identifier(ModernDelightMain.MOD_ID,
            "textures/gui/bamboo_steamer2_gui.png");
    private static final Identifier TEXTURE_3 = new Identifier(ModernDelightMain.MOD_ID,
            "textures/gui/bamboo_steamer3_gui.png");
    private static final Identifier TEXTURE_4 = new Identifier(ModernDelightMain.MOD_ID,
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
        int layer = handler.getCurrentLayer();
        switch (layer){
            case 1 -> {
                RenderSystem.setShaderTexture(0,getTexture(layer));
                context.drawTexture(getTexture(layer), x, y, 0, 0, backgroundWidth,backgroundHeight);
                if (handler.isCovered()){
                    context.drawTexture(getTexture(layer), x + 151, y + 57, 176, 11, 18,2);
                }
                renderProgressArrow(context,x+71,y+25,0,layer);
                renderProgressArrow(context,x+89,y+25,1,layer);
                renderProgressArrow(context,x+71,y+43,2,layer);
                renderProgressArrow(context,x+89,y+43,3,layer);
            }
            case 2 -> {
                RenderSystem.setShaderTexture(0,getTexture(layer));
                context.drawTexture(getTexture(layer), x, y, 0, 0, backgroundWidth,backgroundHeight);
                if (handler.isCovered()){
                    context.drawTexture(getTexture(layer), x + 151, y + 53, 176, 11, 18,2);
                }
                renderProgressArrow(context,x+52,y+25,0,layer);
                renderProgressArrow(context,x+70,y+25,1,layer);
                renderProgressArrow(context,x+52,y+43,2,layer);
                renderProgressArrow(context,x+70,y+43,3,layer);
                renderProgressArrow(context,x+91,y+25,4,layer);
                renderProgressArrow(context,x+109,y+25,5,layer);
                renderProgressArrow(context,x+91,y+43,6,layer);
                renderProgressArrow(context,x+109,y+43,7,layer);
            }
            case 3 -> {
                RenderSystem.setShaderTexture(0,getTexture(layer));
                context.drawTexture(getTexture(layer), x, y, 0, 0, backgroundWidth,backgroundHeight);
                if (handler.isCovered()){
                    context.drawTexture(getTexture(layer), x + 151, y + 49, 176, 11, 18,2);
                }
                renderProgressArrow(context,x+33,y+25,0,layer);
                renderProgressArrow(context,x+51,y+25,1,layer);
                renderProgressArrow(context,x+33,y+43,2,layer);
                renderProgressArrow(context,x+51,y+43,3,layer);
                renderProgressArrow(context,x+72,y+25,4,layer);
                renderProgressArrow(context,x+90,y+25,5,layer);
                renderProgressArrow(context,x+72,y+43,6,layer);
                renderProgressArrow(context,x+90,y+43,7,layer);
                renderProgressArrow(context,x+111,y+25,8,layer);
                renderProgressArrow(context,x+129,y+25,9,layer);
                renderProgressArrow(context,x+111,y+43,10,layer);
                renderProgressArrow(context,x+129,y+43,11,layer);
            }
            case 4 -> {
                RenderSystem.setShaderTexture(0,getTexture(layer));
                context.drawTexture(getTexture(layer), x, y, 0, 0, backgroundWidth,backgroundHeight);
                if (handler.isCovered()){
                    context.drawTexture(getTexture(layer), x + 151, y + 45, 176, 11, 18,2);
                }
                renderProgressArrow(context,x+7,y+25,0,layer);
                renderProgressArrow(context,x+25,y+25,1,layer);
                renderProgressArrow(context,x+7,y+43,2,layer);
                renderProgressArrow(context,x+25,y+43,3,layer);
                renderProgressArrow(context,x+43,y+25,4,layer);
                renderProgressArrow(context,x+61,y+25,5,layer);
                renderProgressArrow(context,x+43,y+43,6,layer);
                renderProgressArrow(context,x+61,y+43,7,layer);
                renderProgressArrow(context,x+79,y+25,8,layer);
                renderProgressArrow(context,x+97,y+25,9,layer);
                renderProgressArrow(context,x+79,y+43,10,layer);
                renderProgressArrow(context,x+97,y+43,11,layer);
                renderProgressArrow(context,x+115,y+25,12,layer);
                renderProgressArrow(context,x+133,y+25,13,layer);
                renderProgressArrow(context,x+115,y+43,14,layer);
                renderProgressArrow(context,x+133,y+43,15,layer);
            }
        }
        if (handler.isHeated()){
            context.drawTexture(getTexture(layer), x + 151, y + 65, 176, 0, 18,11);
        }
        if (handler.getLayer() != 0){
            context.drawText(textRenderer,String.valueOf(handler.getLayer()),x + 157, y + 10,0xffffff,true);
        } else {
            context.drawText(textRenderer,String.valueOf(0),x + 157, y + 10,0xff0000,true);
        }
    }
    private void renderProgressArrow(DrawContext context, int x, int y, int slot, int currentLayer) {
        if (handler.getScaledProgress(slot) != 0){
            context.drawTexture(getTexture(currentLayer), x, y, 176, 123,handler.getScaledProgress(slot),16);
        }
    }
    private Identifier getTexture(int currentLayer){
        return switch (currentLayer){
            case 2 -> TEXTURE_2;
            case 3 -> TEXTURE_3;
            case 4 -> TEXTURE_4;
            default -> TEXTURE_1;
        };
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
