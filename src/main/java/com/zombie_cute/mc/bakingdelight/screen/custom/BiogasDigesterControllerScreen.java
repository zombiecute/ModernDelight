package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BiogasDigesterControllerScreen extends HandledScreen<BiogasDigesterControllerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID,
            "textures/gui/biogas_digester_controller_gui.png");
    public static final String UNAVAILABLE = "tooltips.bakingdelight.biogas_digester_controller.unavailable";
    public static final String SIZE = "tooltips.bakingdelight.biogas_digester_controller.size";
    public static final String GAS_VALUE = "tooltips.bakingdelight.biogas_digester_controller.gas_value";
    public static final String MAX_GAS_VALUE = "tooltips.bakingdelight.biogas_digester_controller.max_gas_value";

    public BiogasDigesterControllerScreen(BiogasDigesterControllerScreenHandler handler, PlayerInventory inventory, Text title) {
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

        context.drawText(textRenderer,String.valueOf(handler.getSize()),x+70,y+22,0xffffff,true);
        context.drawText(textRenderer,String.valueOf(handler.getGasValue()),x+70,y+38,0x00ff00,true);
        context.drawText(textRenderer,String.valueOf(handler.getSize() * 1000),x+70,y+54,0xff0000,true);
        if (handler.getChecked()==1){
            context.drawTexture(TEXTURE,x+9,y+58,176,0,25,12);
        }
        if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
            context.drawTexture(TEXTURE,x+161,y+5,194,13,11,11);
        }
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
