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

public class WoodenBasinScreen extends HandledScreen<WoodenBasinScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID,
            "textures/gui/wooden_basin_gui.png");
    public WoodenBasinScreen(WoodenBasinScreenHandler handler, PlayerInventory inventory, Text title) {
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

        renderFluid(context, x, y);
        if (mouseX >= x + 46 && mouseX <= x + 65 && mouseY >= y + 20 && mouseY <= y + 68){
            context.drawTexture(TEXTURE,mouseX+6,mouseY-16,194,0,45,13);
            context.drawText(textRenderer,String.valueOf(handler.getFluidLevel()),
                    mouseX+9,mouseY-13,0xffffff,false);
        }
        if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
            context.drawTexture(TEXTURE,x+161,y+5,194,13,11,11);
        }
    }

    @Environment(EnvType.CLIENT)
    private void renderFluid(DrawContext context, int x, int y) {
        if (handler.getFluidLevel()!=0){
            int offset = 47 - handler.getScaledProgress();
            context.drawTexture(TEXTURE, x + 47, y + 21 + offset, 176, offset,18, handler.getScaledProgress());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
