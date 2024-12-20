package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BiogasDigesterIOScreen extends HandledScreen<BiogasDigesterIOScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/biogas_digester_io_gui.png");
    public static final String UNAVAILABLE = "tooltips.bakingdelight.biogas_digester_io.unavailable";
    public BiogasDigesterIOScreen(BiogasDigesterIOScreenHandler handler, PlayerInventory inventory, Text title) {
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

        renderProgressArrow(context, x, y);
        if (handler.isChecked()){
            context.drawTexture(TEXTURE,x+123,y+60,176,0,25,12);
        }
        if (mouseX >= x + 108 && mouseX <= x + 121 && mouseY >= y + 60 && mouseY <= y + 71){
            context.drawTexture(TEXTURE,mouseX+6,mouseY-16,176,12,50,13);
            context.drawText(textRenderer,String.valueOf(handler.getGasValue()),
                    mouseX+9,mouseY-13, 0xffffff,false);
        }
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()){
            context.drawTexture(TEXTURE, x + 64, y + 36, 0, 166, handler.getScaledProgress(), 18);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
