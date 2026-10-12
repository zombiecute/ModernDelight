package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.fermenter.IndustrialFermenterBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.packet.ChangeBlockEntityDataC2SPacket;
import com.zombie_cute.mc.bakingdelight.screen.util.FluidStackRenderer;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.BacteriaType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;
import java.util.Optional;

public class IndustrialFermenterScreen extends HandledScreen<IndustrialFermenterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID,
            "textures/gui/industrial_fermenter_gui.png");
    public IndustrialFermenterScreen(IndustrialFermenterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    private FluidStackRenderer fluidStackRenderer;
    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        assignFluidStackRenderer();
    }
    boolean doRenderFluidTooltip;
    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth,backgroundHeight);

        renderFluid(context, x, y);
        doRenderFluidTooltip = mouseX >= x + 118 && mouseX <= x + 137 && mouseY >= y + 21 && mouseY <= y + 69;
        if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
            context.drawTexture(TEXTURE,x+161,y+5,245,0,11,11);
        } else if (mouseX >= x + 150 && mouseX <= x + 160 && mouseY >= y + 5 && mouseY <= y + 15){
            context.drawTexture(TEXTURE,x+150,y+5,234,0,11,11);
        }
        context.drawText(textRenderer, String.valueOf(handler.getTemperature()),x+27,y+44,0xffffff,true);
        context.drawText(textRenderer, handler.getEfficiency() + "%",x+27,y+28,0xffffff,true);
        if (handler.blockEntity.getWorld() != null) {
            context.drawText(textRenderer, String.valueOf(handler.getLightLevel()),x+100,y+40,0xffffff,true);
        }
        renderArrow(context,x,y);
        boolean isOnReduceButton = mouseX >= x + 29 && mouseX <= x + 39 && mouseY >= y + 56 && mouseY <= y + 66;
        boolean isOnAddButton = mouseX >= x + 40 && mouseX <= x + 50 && mouseY >= y + 56 && mouseY <= y + 66;
        if (handler.isPowered()){
            context.drawTexture(TEXTURE,x + 4,y + 4,176,0,25,12);
        }
        if (handler.getTemperatureFix() > -30 && handler.isPowered()) {
            if (isOnReduceButton){
                context.drawTexture(TEXTURE,x + 29,y + 56,0,177,11,11);
            } else {
                context.drawTexture(TEXTURE,x + 29,y + 56,0,166,11,11);
            }
        }
        if (handler.getTemperatureFix() < 30 && handler.isPowered()) {
            if (isOnAddButton){
                context.drawTexture(TEXTURE,x + 40,y + 56,10,177,11,11);
            } else {
                context.drawTexture(TEXTURE,x + 40,y + 56,10,166,11,11);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        boolean isOnReduceButton = mouseX >= x + 29 && mouseX <= x + 39 && mouseY >= y + 56 && mouseY <= y + 66;
        boolean isOnAddButton = mouseX >= x + 40 && mouseX <= x + 50 && mouseY >= y + 56 && mouseY <= y + 66;
        boolean isOnCFUButton = mouseX >= x + 150 && mouseX <= x + 160 && mouseY >= y + 5 && mouseY <= y + 15;
        if (button == 0) {
            int[] array = new int[1];
            if (handler.getTemperatureFix() > -30 && handler.isPowered() && isOnReduceButton) {
                array[0] = 2;
                ChangeBlockEntityDataC2SPacket.send(handler.blockEntity.getPos(),array);
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            } else if (handler.getTemperatureFix() < 30 && handler.isPowered() && isOnAddButton) {
                array[0] = 3;
                ChangeBlockEntityDataC2SPacket.send(handler.blockEntity.getPos(),array);
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            } else if (isOnCFUButton) {
                MinecraftClient.getInstance().setScreen(new CFUScreen(ModBlocks.INDUSTRIAL_FERMENTER.getName(),
                        MinecraftClient.getInstance().currentScreen,handler.blockEntity));
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
        }
        if (button == 1 && doRenderFluidTooltip && hasShiftDown()) {
            int[] array = new int[1];
            array[0] = 4;
            ChangeBlockEntityDataC2SPacket.send(handler.blockEntity.getPos(),array);
            MinecraftClient.getInstance().getSoundManager()
                    .play(PositionedSoundInstance.master(SoundEvents.ITEM_BUCKET_EMPTY, 1.0F));
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        if (doRenderFluidTooltip){
            if (hasShiftDown()){
                context.drawTooltip(textRenderer,Text.translatable(TextUtil.FERMENTATION_SHIFT).formatted(Formatting.RED),x,y);
            } else {
                context.drawTooltip(textRenderer,fluidStackRenderer.getTooltip(handler.blockEntity.getFluidStackCopy()),
                        Optional.empty(),x,y);
            }
        }
        super.drawMouseoverTooltip(context, x, y);
    }
    private void assignFluidStackRenderer(){
        fluidStackRenderer = new FluidStackRenderer(IndustrialFermenterBlockEntity.MAX_FLUID_LEVEL,18,47);
    }
    private void renderFluid(DrawContext context, int x, int y) {
        fluidStackRenderer.drawFluid(context,handler.blockEntity.getFluidStackCopy(),
                x+119,y+22,18,47);
    }
    private void renderArrow(DrawContext context,int x,int y){
        context.drawTexture(TEXTURE,x + 61,y + 62,176,12,handler.getScaledProgress(),5);
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    public void close() {
        int[] array = new int[1];
        array[0] = 1;
        ChangeBlockEntityDataC2SPacket.send(handler.blockEntity.getPos(),array);
        super.close();
    }
    public class CFUScreen extends Screen {
        private final Screen parent;
        private final BlockEntity blockEntity;
        protected CFUScreen(Text title, Screen parent, BlockEntity blockEntity) {
            super(title);
            this.parent = parent;
            this.blockEntity = blockEntity;
        }
        private static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID,
                "textures/gui/industrial_fermenter_gui.png");
        private final int backgroundWidth = 176;
        private final int backgroundHeight = 90;
        private int x;
        private int y;

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
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1f,1f,1f,1f);
            RenderSystem.setShaderTexture(0,TEXTURE);
            context.drawTexture(TEXTURE, x, y, 63, 166, backgroundWidth,backgroundHeight);
            if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
                context.drawTexture(TEXTURE,x+161,y+5,239,171,11,11);
            }
            for (BacteriaType bacteriaType : BacteriaType.values()){
                context.drawText(textRenderer, Text.translatable(bacteriaType.getTranslationKey()).append(Text.literal(": " + handler.getCFUs(bacteriaType) + " CFU"))
                        ,x+9,y+9+(bacteriaType.getRawID()*16),0xffffff,true);
            }
            super.render(context, mouseX, mouseY, delta);
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
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (client != null){
                if (this.client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
                    this.close();
                    return true;
                }
            }
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
        @Override
        public void close() {
            Objects.requireNonNull(client).setScreen(parent);
        }
    }
}
