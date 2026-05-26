package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ChargingPostScreen extends HandledScreen<ChargingPostScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID,
            "textures/gui/charging_post_gui.png");
    public ChargingPostScreen(ChargingPostScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
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
        renderProgressArrow(context, x, y);
        renderPowerLevel(context,x,y);
        b = mouseX >= x + 7 && mouseY >= y + 16 && mouseX <= x + 24 && mouseY <= y + 70;
    }
    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isWorking()){
            context.drawTexture(TEXTURE, x + 62, y + 40, 0, 166, handler.getScaledProgress(), 15);
        }
    }
    private void renderPowerLevel(DrawContext context, int x, int y) {
        if (handler.getPower() != 0){
            int offset = 53 - handler.getScaledPower();
            context.drawTexture(TEXTURE, x + 8, y + 17 + offset, 176, offset,16, handler.getScaledPower());
        }
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        if (b){
            context.drawTooltip(textRenderer,Text.literal(handler.getPower() + " EP"),x,y);
        }
        super.drawMouseoverTooltip(context, x, y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
