package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GasCanisterScreen extends HandledScreen<GasCanisterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID,
            "textures/gui/gas_canister_gui.png");
    public GasCanisterScreen(GasCanisterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title))*2 / 3;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth,backgroundHeight);

        int gasValue = handler.getGasValue();
        if (gasValue>0 && gasValue<333){
            context.drawTexture(TEXTURE,x+81,y+24,176,13,51,28);
        } else if (gasValue >= 333 && gasValue < 666) {
            context.drawTexture(TEXTURE,x+81,y+24,176,41,51,28);
        } else if (gasValue >= 666 && gasValue < 1000) {
            context.drawTexture(TEXTURE,x+81,y+24,176,69,51,28);
        } else if (gasValue >= 1000 && gasValue < 1333) {
            context.drawTexture(TEXTURE,x+81,y+24,176,97,51,28);
        } else if (gasValue >= 1333 && gasValue < 1666) {
            context.drawTexture(TEXTURE,x+81,y+24,176,125,51,28);
        } else if (gasValue >= 1666 && gasValue < 2000) {
            context.drawTexture(TEXTURE,x+81,y+24,0,166,51,28);
        } else if (gasValue >= 2000 && gasValue < 2333) {
            context.drawTexture(TEXTURE,x+81,y+24,51,166,51,28);
        } else if (gasValue >= 2333 && gasValue < 2666) {
            context.drawTexture(TEXTURE,x+81,y+24,102,166,51,28);
        } else if (gasValue >= 2666 && gasValue < 3000) {
            context.drawTexture(TEXTURE,x+81,y+24,153,166,51,28);
        } else if (gasValue >= 3000 && gasValue < 3333) {
            context.drawTexture(TEXTURE,x+81,y+24,204,166,51,28);
        } else if (gasValue >= 3333 && gasValue < 3666) {
            context.drawTexture(TEXTURE,x+81,y+24,0,194,51,28);
        } else if (gasValue >= 3666 && gasValue < 4000) {
            context.drawTexture(TEXTURE,x+81,y+24,51,194,51,28);
        } else if (gasValue >= 4000 && gasValue < 4333) {
            context.drawTexture(TEXTURE,x+81,y+24,102,194,51,28);
        } else if (gasValue >= 4333 && gasValue < 4666) {
            context.drawTexture(TEXTURE,x+81,y+24,153,194,51,28);
        } else if (gasValue >= 4666 && gasValue < 5000) {
            context.drawTexture(TEXTURE,x+81,y+24,204,194,51,28);
        } else if (gasValue >= 5000 && gasValue < 5333) {
            context.drawTexture(TEXTURE,x+81,y+24,0,222,51,28);
        } else if (gasValue >= 5333){
            int cycle = handler.getCycleInt();
            switch (cycle){
                case 0: context.drawTexture(TEXTURE,x+81,y+24,51,222,51,28);break;
                case 1: context.drawTexture(TEXTURE,x+81,y+24,102,222,51,28);break;
                case 2: context.drawTexture(TEXTURE,x+81,y+24,153,222,51,28);break;
                case 3: context.drawTexture(TEXTURE,x+81,y+24,204,222,51,28);
            }
        }

        if (mouseX >= x + 82 && mouseX <= x + 130 && mouseY >= y + 25 && mouseY <= y + 50){
            context.drawTexture(TEXTURE,mouseX+6,mouseY-16,194,0,36,13);
            if (gasValue<1000){
                context.drawText(textRenderer,String.valueOf(gasValue),
                        mouseX+9,mouseY-13, 5635925,false);
            } else if (gasValue<3000) {
                context.drawText(textRenderer,String.valueOf(gasValue),
                        mouseX+9,mouseY-13, 16777045,false);
            } else if (gasValue<5000) {
                context.drawText(textRenderer,String.valueOf(gasValue),
                        mouseX+9,mouseY-13, 16755200,false);
            } else {
                context.drawText(textRenderer,String.valueOf(gasValue),
                        mouseX+9,mouseY-13, 16733525,false);
            }
        }
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
