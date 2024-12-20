package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.util.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Objects;
@Environment(EnvType.CLIENT)
public class AdvanceFurnaceScreen extends HandledScreen<AdvanceFurnaceScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/advance_furnace_gui.png");
    public static final String TOOLTIP = "tooltips.bakingdelight.advance_furnace_exp_tooltip";
    public AdvanceFurnaceScreen(AdvanceFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY -= 3;
        playerInventoryTitleY += 6;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - 172) / 2;
        int minX = x + 7;
        int minY = y + 51;
        int maxX = x + 41;
        int maxY = y + 62;
        if (mouseX >= minX && mouseX <= maxX &&
        mouseY >= minY && mouseY <= maxY && button == 0){
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player != null && !mc.player.isSpectator()) {
                if (handler.getExperiences()/10 != 0){
                    mc.player.playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON,1.0f,1.0f);
                    NetworkHandler.sendSpawnXPPacket(handler.blockEntity.getPos());
                } else {
                    mc.player.playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,1.0f,1.6f);
                }
            }
            return true;
        } else {
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - 172) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth,175);
        renderProgressArrow1(context, x, y);
        renderProgressArrow2(context, x, y);
        renderProgressArrow3(context, x, y);
        renderProgressArrow4(context, x, y);
        renderBurnTime(context, x, y);
        if (mouseX >= x + 7 && mouseX <= x + 41 && mouseY >= y + 51 && mouseY <= y + 62){
            context.drawTexture(TEXTURE, x + 6, y + 50, 176, 38, 36,13);
            if (handler.getExperiences()/10 != 0){
                context.drawTooltip(Objects.requireNonNull(client).textRenderer,Text.translatable(TOOLTIP).formatted(Formatting.WHITE),mouseX,mouseY);
            }
        }
        if (handler.getExperiences()/10 <= 9999){
            context.drawText(textRenderer,String.valueOf(handler.getExperiences()/10),x+8,y+52, 0x82fd64,true);
        } else {
            context.drawText(textRenderer,"9999+",x+8,y+52, 0x82fd64,true);
        }
    }

    private void renderBurnTime(DrawContext context, int x, int y) {
        if (handler.isBurning()){
            int offset = 14 - handler.getScaledBurnTime();
            context.drawTexture(TEXTURE, x + 8, y + 15 + offset, 176, 24 + offset,
                    16 ,handler.getScaledBurnTime());
        }
    }

    private void renderProgressArrow1(DrawContext context, int x, int y) {
        if (handler.isCrafting(0)){
            context.drawTexture(TEXTURE, x + 43, y + 37, 176, 0,18, handler.getScaledProgress(0));
        }
    }
    private void renderProgressArrow2(DrawContext context, int x, int y) {
        if (handler.isCrafting(1)){
            context.drawTexture(TEXTURE, x + 79, y + 37, 176, 0,18 , handler.getScaledProgress(1));
        }
    }
    private void renderProgressArrow3(DrawContext context, int x, int y) {
        if (handler.isCrafting(2)){
            context.drawTexture(TEXTURE, x + 115, y + 37, 176, 0,18 ,handler.getScaledProgress(2) );
        }
    }
    private void renderProgressArrow4(DrawContext context, int x, int y) {
        if (handler.isCrafting(3)){
            context.drawTexture(TEXTURE, x + 151, y + 37, 176, 0, 18, handler.getScaledProgress(3));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
