package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class BiogasDigesterControllerScreen extends HandledScreen<BiogasDigesterControllerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID,
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
    boolean b1;
    boolean b2;
    boolean b3;
    boolean b4;
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
        b1 = mouseX >= x + 52 && mouseY >= y + 19 && mouseX <= x + 65 && mouseY <= y + 33;
        b2 = mouseX >= x + 52 && mouseY >= y + 35 && mouseX <= x + 65 && mouseY <= y + 49;
        b3 = mouseX >= x + 52 && mouseY >= y + 51 && mouseX <= x + 65 && mouseY <= y + 65;

        if (handler.getChecked()==1){
            context.drawTexture(TEXTURE,x+9,y+58,176,0,25,12);
            b4 = false;
        } else {
            b4 = mouseX >= x + 9 && mouseY >= y + 58 && mouseX <= x + 33 && mouseY <= y + 69;
        }
        if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
            context.drawTexture(TEXTURE,x+161,y+5,194,13,11,11);
        }
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int mouseX, int mouseY) {
        if (b1){
            context.drawTooltip(textRenderer,Text.translatable(SIZE).formatted(Formatting.WHITE),mouseX,mouseY);
        }
        if (b2){
            context.drawTooltip(textRenderer,Text.translatable(GAS_VALUE).formatted(Formatting.GREEN),mouseX,mouseY);
        }
        if (b3){
            context.drawTooltip(textRenderer,Text.translatable(MAX_GAS_VALUE).formatted(Formatting.RED),mouseX,mouseY);
        }
        if (b4){
            context.drawTooltip(textRenderer,Text.translatable(UNAVAILABLE).formatted(Formatting.RED),mouseX,mouseY);
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
