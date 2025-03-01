package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.networking.packet.ChangeBlockEntityDataC2SPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class ACDCConverterScreen extends HandledScreen<ACDCConverterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID,
            "textures/gui/acdcc_gui.png");
    public ACDCConverterScreen(ACDCConverterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    public static final String SPEED_TIP = "tooltips.bakingdelight.acdcc.speed_tip";
    public static final String PUT_BATTERY_TIP = "tooltips.bakingdelight.acdcc.put_battery_tip";
    public static final String AC2DC = "tooltips.bakingdelight.acdcc.ac2dc";
    public static final String DC2AC = "tooltips.bakingdelight.acdcc.dc2ac";

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
        context.drawTexture(TEXTURE, x + 53, y + 27, 176, 75, handler.getScaledWorkSpeed(), 9);
        renderSwitchButton(context, mouseX, mouseY, x, y);
        renderPageButton(context, mouseX, mouseY, x, y);
        renderPowerLevel(context,x,y);

        if (mouseX >= x + 17 && mouseX <= x + 32 && mouseY >= y + 15 && mouseY <= y + 67){
            context.drawTooltip(this.textRenderer,Text.literal(handler.getPower()+" EP").formatted(Formatting.WHITE),mouseX,mouseY);
        }
        if (mouseX >= x + 151 && mouseX <= x + 51 && mouseY >= y + 168 && mouseY <= y + 68 && !handler.hasBattery()){
            context.drawTooltip(this.textRenderer,Text.translatable(PUT_BATTERY_TIP).formatted(Formatting.RED),mouseX,mouseY);
        }
        if (mouseX >= x + 53 && mouseX <= x + 26 && mouseY >= y + 122 && mouseY <= y + 36){
            MutableText text = Text.translatable(SPEED_TIP);
            text.append(Text.literal(handler.getWorkSpeed() + "/" + handler.getMaxWorkSpeed()));
            context.drawTooltip(this.textRenderer,text.formatted(Formatting.WHITE),mouseX,mouseY);
        }
        if (mouseX >= x + 58 && mouseX <= x + 54 && mouseY >= y + 94 && mouseY <= y + 69){
            if (handler.isDC2ACMode()){
                context.drawTooltip(this.textRenderer,Text.translatable(DC2AC).formatted(Formatting.WHITE),mouseX,mouseY);
            } else {
                context.drawTooltip(this.textRenderer,Text.translatable(AC2DC).formatted(Formatting.WHITE),mouseX,mouseY);
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
        boolean b = mouseX >= x + 42 && mouseY >= y + 56 && mouseX <= x + 56 && mouseY <= y + 66;
        int[] array = new int[2];
        if (handler.isDC2ACMode()){
            if (b && button == 0){
                array[1] = 1;
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                ChangeBlockEntityDataC2SPacket.send(handler.blockEntity.getPos(),array);
                return true;
            }
        } else {
            if (b && button == 0){
                array[1] = 2;
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                ChangeBlockEntityDataC2SPacket.send(handler.blockEntity.getPos(),array);
                return true;
            }
        }
        if (handler.getWorkSpeed() != 0){
            if (hasShiftDown()){
                array[0] = 4;
            } else {
                array[0] = 2;
            }
            if (mouseX >= x + 42 && mouseY >= y + 26 && mouseX <= x + 52 && mouseY <= y + 36){
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                ChangeBlockEntityDataC2SPacket.send(handler.blockEntity.getPos(),array);
                return true;
            }
        }
        if (handler.getWorkSpeed() != handler.getMaxWorkSpeed()){
            if (hasShiftDown()){
                array[0] = 3;
            } else {
                array[0] = 1;
            }
            if (mouseX >= x + 123 && mouseY >= y + 26 && mouseX <= x + 133 && mouseY <= y + 36){
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                ChangeBlockEntityDataC2SPacket.send(handler.blockEntity.getPos(),array);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    private void renderPageButton(DrawContext context, int mouseX, int mouseY, int x, int y) {
        if (handler.getWorkSpeed() != 0){
            context.drawTexture(TEXTURE, x + 42, y + 26,176,53,11,11);
            if (mouseX >=x + 42 && mouseY >= y + 26 && mouseX <= x + 52 && mouseY <= y + 36){
                context.drawTexture(TEXTURE, x + 42, y + 26,176,64,11,11);
            }
        }
        if (handler.getWorkSpeed() != handler.getMaxWorkSpeed()){
            context.drawTexture(TEXTURE, x + 123, y + 26,186,53,11,11);
            if (mouseX >= x + 123 && mouseY >= y + 26 && mouseX <= x + 133 && mouseY <= y + 36){
                context.drawTexture(TEXTURE, x + 123, y + 26,186,64,11,11);
            }
        }
    }

    private void renderSwitchButton(DrawContext context, int mouseX, int mouseY, int x, int y) {
        boolean b = mouseX >= x + 42 && mouseY >= y + 56 && mouseX <= x + 56 && mouseY <= y + 66;
        if (handler.isDC2ACMode()){
            context.drawTexture(TEXTURE, x + 42, y + 56,176,84,15,11);
            context.drawTexture(TEXTURE, x + 60, y + 56,192,0,33,12);
            context.drawText(textRenderer,handler.getEfficiency() + "EP/S",x + 45,y + 41,0xffffff,true);
            if (b){
                context.drawTexture(TEXTURE, x + 42, y + 56,176,95,15,11);
            }
        } else {
            context.drawText(textRenderer,"---",x + 45,y + 41,0xffffff,true);
            if (b){
                context.drawTexture(TEXTURE, x + 42, y + 56,191,95,15,11);
            }
        }
    }

    private void renderPowerLevel(DrawContext context, int x, int y) {
        if (handler.getPower() != 0){
            int offset = 53 - handler.getScaledProgress();
            context.drawTexture(TEXTURE, x + 17, y + 15 + offset, 176, offset,16, handler.getScaledProgress());
        }
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
