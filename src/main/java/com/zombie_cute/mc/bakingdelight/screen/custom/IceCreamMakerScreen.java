package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.entities.IceCreamMakerBlockEntity;
import com.zombie_cute.mc.bakingdelight.util.Flavor;
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

public class IceCreamMakerScreen extends HandledScreen<IceCreamMakerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID,
            "textures/gui/ice_cream_maker_gui.png");
    public IceCreamMakerScreen(IceCreamMakerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    private IceCreamMakerBlockEntity.IceCream iceCream1;
    private IceCreamMakerBlockEntity.IceCream iceCream2;
    private IceCreamMakerBlockEntity.IceCream iceCream3;

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

        iceCream1 = handler.getIceCream1();
        iceCream2 = handler.getIceCream2();
        iceCream3 = handler.getIceCream3();

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth,backgroundHeight);
        renderPower(context,x,y);
        renderArrow(context,x,y);
        renderIceCream1(context,x,y,mouseX,mouseY);
        renderIceCream2(context,x,y,mouseX,mouseY);
        renderIceCream3(context,x,y,mouseX,mouseY);
        if (mouseX >= x + 87 && mouseX <= x + 107 && mouseY >= y + 17 && mouseY <= y + 62){
            context.drawTexture(TEXTURE,mouseX+6,mouseY-16,176,9,50,13);
            context.drawText(textRenderer,String.valueOf(iceCream1.getAmount()),
                    mouseX+9,mouseY-13,iceCream1.getFlavor().getColor(),false);
        }
        if (mouseX >= x + 114 && mouseX <= x + 134 && mouseY >= y + 17 && mouseY <= y + 62){
            context.drawTexture(TEXTURE,mouseX+6,mouseY-16,176,9,50,13);
            context.drawText(textRenderer,String.valueOf(iceCream2.getAmount()),
                    mouseX+9,mouseY-13,iceCream2.getFlavor().getColor(),false);
        }
        if (mouseX >= x + 141 && mouseX <= x + 161 && mouseY >= y + 17 && mouseY <= y + 62) {
            context.drawTexture(TEXTURE,mouseX+6,mouseY-16,176,9,50,13);
            context.drawText(textRenderer,String.valueOf(iceCream3.getAmount()),
                    mouseX+9,mouseY-13,iceCream3.getFlavor().getColor(),false);
        }
    }
    private void renderPower(DrawContext context,int x,int y){
        if (handler.isPowered()){
            context.drawTexture(TEXTURE,x + 9,y + 30,176,25,12,25);
        }
    }
    private void renderArrow(DrawContext context,int x,int y){
        context.drawTexture(TEXTURE,x + 63,y + 22,176,50,handler.getScaledProgress(),42);
    }
    private void renderIceCream1(DrawContext context,int x,int y,int mouseX,int mouseY){
        int height = iceCream1.getAmount() * 46 / 1000;
        int fix = 46 - height;
        boolean area = mouseX >= x + 93 && mouseY >= y + 65 && mouseX <= x + 101 && mouseY <= y + 73;
        if (iceCream1.getFlavor() != Flavor.NULL){
            context.drawTexture(TEXTURE,x + 87,y + 17 + fix,21 * iceCream1.getFlavor().getId(),166,21,height);
        }
        if (iceCream1.isSelected()){
            context.drawTexture(TEXTURE,x+93,y+65,185,0,9,9);
            if (area){
                context.drawTexture(TEXTURE,x+93,y+65,194,0,9,9);
            }
        } else {
            if (area){
                context.drawTexture(TEXTURE,x+93,y+65,176,0,9,9);
            }
        }
    }
    private void renderIceCream2(DrawContext context,int x,int y,int mouseX,int mouseY){
        int height = iceCream2.getAmount() * 46 / 1000;
        int fix = 46 - height;
        boolean area = mouseX >= x + 120 && mouseY >= y + 65 && mouseX <= x + 128 && mouseY <= y + 73;
        if (iceCream2.getFlavor() != Flavor.NULL){
            context.drawTexture(TEXTURE,x + 114,y + 17 + fix,21 * iceCream2.getFlavor().getId(),166,21,height);
        }
        if (iceCream2.isSelected()){
            context.drawTexture(TEXTURE,x+120,y+65,185,0,9,9);
            if (area){
                context.drawTexture(TEXTURE,x+120,y+65,194,0,9,9);
            }
        } else {
            if (area){
                context.drawTexture(TEXTURE,x+120,y+65,176,0,9,9);
            }
        }
    }
    private void renderIceCream3(DrawContext context,int x,int y,int mouseX,int mouseY){
        int height = iceCream3.getAmount() * 46 / 1000;
        int fix = 46 - height;
        boolean area = mouseX >= x + 147 && mouseY >= y + 65 && mouseX <= x + 155 && mouseY <= y + 73;
        if (iceCream3.getFlavor() != Flavor.NULL){
            context.drawTexture(TEXTURE,x + 141,y + 17 + fix,21 * iceCream3.getFlavor().getId(),166,21,height);
        }
        if (iceCream3.isSelected()){
            context.drawTexture(TEXTURE,x+147,y+65,185,0,9,9);
            if (area){
                context.drawTexture(TEXTURE,x+147,y+65,194,0,9,9);
            }
        } else {
            if (area){
                context.drawTexture(TEXTURE,x+147,y+65,176,0,9,9);
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
        boolean area1 = mouseX >= x + 93 && mouseY >= y + 65 && mouseX <= x + 101 && mouseY <= y + 73;
        boolean area2 = mouseX >= x + 120 && mouseY >= y + 65 && mouseX <= x + 128 && mouseY <= y + 73;
        boolean area3 = mouseX >= x + 147 && mouseY >= y + 65 && mouseX <= x + 155 && mouseY <= y + 73;
        int[] array = new int[1];
        if (area1){
            array[0] = 1;
            MinecraftClient.getInstance().getSoundManager()
                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            NetworkHandler.sendChangeBlockEntityDataPacket(handler.blockEntity.getPos(),array);
            return true;
        }
        if (area2){
            array[0] = 2;
            MinecraftClient.getInstance().getSoundManager()
                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            NetworkHandler.sendChangeBlockEntityDataPacket(handler.blockEntity.getPos(),array);
            return true;
        }
        if (area3){
            array[0] = 3;
            MinecraftClient.getInstance().getSoundManager()
                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            NetworkHandler.sendChangeBlockEntityDataPacket(handler.blockEntity.getPos(),array);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
