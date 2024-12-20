package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.util.NetworkHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TeslaCoilScreen extends HandledScreen<TeslaCoilScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/tesla_coil_gui.png");

    public TeslaCoilScreen(TeslaCoilScreenHandler handler, PlayerInventory inventory, Text title) {
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
        if (handler.getEfficiency() > 0){
            context.drawTexture(TEXTURE,x+9,y+58,176,0,25,12);
        }
        if (handler.getEfficiency() > 0){
            context.drawText(textRenderer,handler.getEfficiency() + "EP/S",x + 70,y + 38,0xffffff,true);
        } else {
            context.drawText(textRenderer,handler.getEfficiency() + "EP/S",x + 70,y + 38,0xff0000,true);
        }
        renderSwitchButton(context,mouseX,mouseY,x,y);
    }
    private void renderSwitchButton(DrawContext context, int mouseX, int mouseY, int x, int y) {
        boolean b = mouseX >= x + 152 && mouseY >= y + 58 && mouseX <= x + 166 && mouseY <= y + 68;
        if (handler.getShowParticle()){
            context.drawTexture(TEXTURE, x + 152, y + 58,176,84,15,11);
            if (b){
                context.drawTexture(TEXTURE, x + 152, y + 58,176,95,15,11);
            }
        } else {
            if (b){
                context.drawTexture(TEXTURE, x + 152, y + 58,191,95,15,11);
            }
        }
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.isSpectator()) {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        boolean b = mouseX >= x + 152 && mouseY >= y + 58 && mouseX <= x + 166 && mouseY <= y + 68;
        byte[] array = new byte[2];
        if (handler.getShowParticle()){
            if (b && button == 0){
                array[0] = 1;
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                NetworkHandler.sendChangeBlockEntityDataPacket(handler.blockEntity.getPos(),array);
                return true;
            }
        } else {
            if (b && button == 0){
                array[0] = 2;
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                NetworkHandler.sendChangeBlockEntityDataPacket(handler.blockEntity.getPos(),array);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
