package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WindTurbineControllerScreen extends HandledScreen<WindTurbineControllerScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/wind_turbine_controller_gui.png");

    public WindTurbineControllerScreen(WindTurbineControllerScreenHandler handler, PlayerInventory inventory, Text title) {
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
        if (handler.getYLevel() >= 200){
            context.drawText(textRenderer,String.valueOf(handler.getYLevel()),x + 81,y + 22,0x1fff00,true);
        } else if (handler.getYLevel() <= 0) {
            context.drawText(textRenderer,String.valueOf(handler.getYLevel()),x + 81,y + 22,0xe50000,true);
        } else {
            context.drawText(textRenderer,String.valueOf(handler.getYLevel()),x + 81,y + 22,0xffffff,true);
        }
        context.drawText(textRenderer,handler.getEfficiency() + "EP/S",x + 70,y + 38,0xffffff,true);
        if (handler.getChecked()==1){
            context.drawTexture(TEXTURE,x+9,y+58,176,0,25,12);
        }
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
