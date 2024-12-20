package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.PhotovoltaicGeneratorBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class PhotovoltaicGeneratorScreen extends HandledScreen<PhotovoltaicGeneratorScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/photovoltaic_generator_gui.png");
    public PhotovoltaicGeneratorScreen(PhotovoltaicGeneratorScreenHandler handler, PlayerInventory inventory, Text title) {
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
        renderYText(context, x, y);
        renderPowerLevel(context,x,y);
        renderDayNightIcon(context, x, y);
        if (handler.isWorking()){
            context.drawTexture(TEXTURE, x + 148,y + 14,192,0,12,12);
        }
        if (handler.isInSlowMode()){
            context.drawTexture(TEXTURE, x + 135,y + 14,192,12,12,12);
        }
        if (mouseX >= x + 17 && mouseX <= x + 32 && mouseY >= y + 15 && mouseY <= y + 67){
            context.drawTexture(TEXTURE,mouseX+6,mouseY-16,192,24,45,13);
            context.drawText(textRenderer,String.valueOf(handler.getPower()),
                    mouseX+9,mouseY-13,0xffffff,false);
        }
        if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
            context.drawTexture(TEXTURE,x+161,y+5,192,37,11,11);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15 && button == 0){
            MinecraftClient.getInstance().setScreen(new TipScreen(ModBlocks.ELECTRICIANS_DESK.getName(),
                    MinecraftClient.getInstance().currentScreen,handler.blockEntity));
            MinecraftClient.getInstance().getSoundManager()
                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void renderYText(DrawContext context, int x, int y) {
        if (handler.getYValue() > 0){
            context.drawText(textRenderer,String.valueOf(handler.getYValue()), x + 70, y + 57,0xffffff,true);
        } else {
            context.drawText(textRenderer,String.valueOf(handler.getYValue()), x + 70, y + 57,0xff0000,true);
        }
    }

    private void renderDayNightIcon(DrawContext context, int x, int y) {
        if (!Objects.requireNonNull(handler.blockEntity.getWorld()).getDimension().hasFixedTime()){
            if (PhotovoltaicGeneratorBlockEntity.isEarlyMorningOrTwilight(Objects.requireNonNull(handler.blockEntity.getWorld())) &&
                    handler.blockEntity.getWorld().isThundering()){
                context.drawTexture(TEXTURE, x + 38, y + 54,204,67,14,14);
            } else if (PhotovoltaicGeneratorBlockEntity.isEarlyMorningOrTwilight(Objects.requireNonNull(handler.blockEntity.getWorld())) &&
                    handler.blockEntity.getWorld().isRaining()) {
                context.drawTexture(TEXTURE, x + 38, y + 54,190,67,14,14);
            } else if (PhotovoltaicGeneratorBlockEntity.isEarlyMorningOrTwilight(Objects.requireNonNull(handler.blockEntity.getWorld()))) {
                context.drawTexture(TEXTURE, x + 38, y + 54,176,67,14,14);
            } else if ((PhotovoltaicGeneratorBlockEntity.isMorningOrAfternoon(Objects.requireNonNull(handler.blockEntity.getWorld())) ||
                    PhotovoltaicGeneratorBlockEntity.isNoon(Objects.requireNonNull(handler.blockEntity.getWorld())))
                    && handler.blockEntity.getWorld().isThundering()){
                context.drawTexture(TEXTURE, x + 38, y + 54,204,53,14,14);
            } else if ((PhotovoltaicGeneratorBlockEntity.isMorningOrAfternoon(Objects.requireNonNull(handler.blockEntity.getWorld())) ||
                    PhotovoltaicGeneratorBlockEntity.isNoon(Objects.requireNonNull(handler.blockEntity.getWorld())))
                    && handler.blockEntity.getWorld().isRaining()){
                context.drawTexture(TEXTURE, x + 38, y + 54,190,53,14,14);
            } else if ((PhotovoltaicGeneratorBlockEntity.isMorningOrAfternoon(Objects.requireNonNull(handler.blockEntity.getWorld())) ||
                    PhotovoltaicGeneratorBlockEntity.isNoon(Objects.requireNonNull(handler.blockEntity.getWorld())))){
                context.drawTexture(TEXTURE, x + 38, y + 54,176,53,14,14);
            } else if (handler.blockEntity.getWorld().isThundering()) {
                context.drawTexture(TEXTURE, x + 38, y + 54,204,81,14,14);
            } else if (handler.blockEntity.getWorld().isRaining()) {
                context.drawTexture(TEXTURE, x + 38, y + 54,190,81,14,14);
            } else {
                context.drawTexture(TEXTURE, x + 38, y + 54,176,81,14,14);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private void renderPowerLevel(DrawContext context, int x, int y) {
        if (handler.getPower() != 0){
            int offset = 53 - handler.getScaledProgress();
            context.drawTexture(TEXTURE, x + 17, y + 15 + offset, 176, offset,16, handler.getScaledProgress());
        }
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
    public static class TipScreen extends Screen{
        private final Screen parent;
        private final BlockEntity blockEntity;
        protected TipScreen(Text title, Screen parent, BlockEntity blockEntity) {
            super(title);
            this.parent = parent;
            this.blockEntity = blockEntity;
        }
        private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
                "textures/gui/photovoltaic_generator_gui.png");
        private final int backgroundWidth = 176;
        private final int backgroundHeight = 77;
        private int x;
        private int y;
        public static final String GREEN_TIP_1 = "bakingdelight.tooltips.green_tip_1";
        public static final String GREEN_TIP_2 = "bakingdelight.tooltips.green_tip_2";
        public static final String GREEN_TIP_3 = "bakingdelight.tooltips.green_tip_3";
        public static final String YELLOW_TIP_1 = "bakingdelight.tooltips.yellow_tip_1";
        public static final String YELLOW_TIP_2 = "bakingdelight.tooltips.yellow_tip_2";
        public static final String YELLOW_TIP_3 = "bakingdelight.tooltips.yellow_tip_3";

        @Override
        protected void init() {
            this.x = (this.width - this.backgroundWidth) / 2;
            this.y = (this.height - this.backgroundHeight) / 2;
        }
        @Override
        public boolean shouldPause() {
            return false;
        }
        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            super.render(context, mouseX, mouseY, delta);
            context.drawTexture(TEXTURE, x, y, 0, 166, backgroundWidth,backgroundHeight);
            if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
                context.drawTexture(TEXTURE,x+161,y+5,176,171,11,11);
            }
            context.drawText(textRenderer,Text.translatable(GREEN_TIP_1),
                    x+19,y+6,0xffffff,true);
            context.drawText(textRenderer,Text.translatable(GREEN_TIP_2),
                    x+19,y+16,0xffffff,true);
            context.drawText(textRenderer,Text.translatable(GREEN_TIP_3),
                    x+19,y+26,0xffffff,true);
            context.drawText(textRenderer,Text.translatable(YELLOW_TIP_1),
                    x+19,y+38,0xffffff,true);
            context.drawText(textRenderer,Text.translatable(YELLOW_TIP_2),
                    x+19,y+48,0xffffff,true);
            context.drawText(textRenderer,Text.translatable(YELLOW_TIP_3),
                    x+19,y+58,0xffffff,true);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15 && button == 0){
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                this.close();
                return true;
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }
        @Override
        public final void tick() {
            if (client != null && client.player != null){
                if (!this.client.player.isAlive() && this.client.player.isRemoved() && canUse()) {
                    this.client.setScreen(null);
                }
            }
        }
        private boolean canUse(){
            if (client != null && client.player != null){
                BlockPos pos1 = client.player.getBlockPos();
                BlockPos pos2 = blockEntity.getPos();
                double distance = Math.sqrt(Math.pow(pos2.getX()-pos1.getX(),2)+Math.pow(pos2.getY()-pos1.getY(),2)+Math.pow(pos2.getZ()-pos1.getZ(),2));
                return distance < 7 && !blockEntity.isRemoved();
            } else return false;
        }
        @Override
        public void close() {
            Objects.requireNonNull(client).setScreen(parent);
        }
    }
}