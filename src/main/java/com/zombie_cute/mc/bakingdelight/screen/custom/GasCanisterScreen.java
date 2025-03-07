package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.biogas.GasCanisterBlockEntity;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class GasCanisterScreen extends HandledScreen<GasCanisterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID,
            "textures/gui/gas_canister_gui.png");
    public GasCanisterScreen(GasCanisterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) * 4 / 7;
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

        int gasValue = handler.getGasValue();
        int maxGasValue = GasCanisterBlockEntity.getMaxCapacity();
        if (gasValue>0 && gasValue<maxGasValue/18){
            context.drawTexture(TEXTURE,x+81,y+24,176,13,51,28);
        } else if (gasValue >= maxGasValue/18 && gasValue < maxGasValue*2/18) {
            context.drawTexture(TEXTURE,x+81,y+24,176,41,51,28);
        } else if (gasValue >= maxGasValue*2/18 && gasValue < maxGasValue*3/18) {
            context.drawTexture(TEXTURE,x+81,y+24,176,69,51,28);
        } else if (gasValue >= maxGasValue*3/18 && gasValue < maxGasValue*4/18) {
            context.drawTexture(TEXTURE,x+81,y+24,176,97,51,28);
        } else if (gasValue >= maxGasValue*4/18 && gasValue < maxGasValue*5/18) {
            context.drawTexture(TEXTURE,x+81,y+24,176,125,51,28);
        } else if (gasValue >= maxGasValue*5/18 && gasValue < maxGasValue*6/18) {
            context.drawTexture(TEXTURE,x+81,y+24,0,166,51,28);
        } else if (gasValue >= maxGasValue*6/18 && gasValue < maxGasValue*7/18) {
            context.drawTexture(TEXTURE,x+81,y+24,51,166,51,28);
        } else if (gasValue >= maxGasValue*7/18 && gasValue < maxGasValue*8/18) {
            context.drawTexture(TEXTURE,x+81,y+24,102,166,51,28);
        } else if (gasValue >= maxGasValue*8/18 && gasValue < maxGasValue*9/18) {
            context.drawTexture(TEXTURE,x+81,y+24,153,166,51,28);
        } else if (gasValue >= maxGasValue*9/18 && gasValue < maxGasValue*10/18) {
            context.drawTexture(TEXTURE,x+81,y+24,204,166,51,28);
        } else if (gasValue >= maxGasValue*10/18 && gasValue < maxGasValue*11/18) {
            context.drawTexture(TEXTURE,x+81,y+24,0,194,51,28);
        } else if (gasValue >= maxGasValue*11/18 && gasValue < maxGasValue*12/18) {
            context.drawTexture(TEXTURE,x+81,y+24,51,194,51,28);
        } else if (gasValue >= maxGasValue*12/18 && gasValue < maxGasValue*13/18) {
            context.drawTexture(TEXTURE,x+81,y+24,102,194,51,28);
        } else if (gasValue >= maxGasValue*13/18 && gasValue < maxGasValue*14/18) {
            context.drawTexture(TEXTURE,x+81,y+24,153,194,51,28);
        } else if (gasValue >= maxGasValue*14/18 && gasValue < maxGasValue*15/18) {
            context.drawTexture(TEXTURE,x+81,y+24,204,194,51,28);
        } else if (gasValue >= maxGasValue*15/18 && gasValue < maxGasValue*16/18) {
            context.drawTexture(TEXTURE,x+81,y+24,0,222,51,28);
        } else if (gasValue >= maxGasValue*16/18){
            int cycle = handler.getCycleInt();
            switch (cycle){
                case 0: context.drawTexture(TEXTURE,x+81,y+24,51,222,51,28);break;
                case 1: context.drawTexture(TEXTURE,x+81,y+24,102,222,51,28);break;
                case 2: context.drawTexture(TEXTURE,x+81,y+24,153,222,51,28);break;
                case 3: context.drawTexture(TEXTURE,x+81,y+24,204,222,51,28);
            }
        }
        b = mouseX >= x + 82 && mouseX <= x + 130 && mouseY >= y + 25 && mouseY <= y + 50;
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int mouseX, int mouseY) {
        if (b){
            int gasValue = handler.getGasValue();
            int maxGasValue = GasCanisterBlockEntity.getMaxCapacity();
            if (gasValue<maxGasValue/6){
                context.drawTooltip(textRenderer,Text.literal(gasValue+" mB").formatted(Formatting.GREEN),
                        mouseX,mouseY);
            } else if (gasValue<maxGasValue/2) {
                context.drawTooltip(textRenderer,Text.literal(gasValue+" mB").formatted(Formatting.YELLOW),
                        mouseX,mouseY);
            } else if (gasValue<maxGasValue*5/6) {
                context.drawTooltip(textRenderer,Text.literal(gasValue+" mB").formatted(Formatting.GOLD),
                        mouseX,mouseY);
            } else {
                context.drawTooltip(textRenderer,Text.literal(gasValue+" mB").formatted(Formatting.RED),
                        mouseX,mouseY);
            }
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
