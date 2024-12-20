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
public class FreezerScreen extends HandledScreen<FreezerScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/freezer_gui.png");
    public FreezerScreen(FreezerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        int minX = x + 116;
        int minY = y + 35;
        int maxX = x + 150;
        int maxY = y + 46;
        if (mouseX >= minX && mouseX <= maxX &&
                mouseY >= minY && mouseY <= maxY && button == 0){
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player != null && !mc.player.isSpectator()) {
                if (handler.getExperiences() != 0){
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
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth,backgroundHeight);

        renderProgressArrow(context, x, y);
        renderCoolTime(context, x, y);
        if (mouseX >= x + 116 && mouseX <= x + 150 && mouseY >= y + 35 && mouseY <= y + 46){
            context.drawTexture(TEXTURE, x + 115, y + 34, 191, 0, 36,13);
            if (handler.getExperiences() != 0){
                context.drawTooltip(Objects.requireNonNull(client).textRenderer,Text.translatable(AdvanceFurnaceScreen.TOOLTIP).formatted(Formatting.WHITE),mouseX,mouseY);
            }
        }
        if (handler.getExperiences() <= 9999){
            context.drawText(textRenderer,String.valueOf(handler.getExperiences()),x+117,y+36, 0x82fd64,true);
        } else {
            context.drawText(textRenderer,"9999+",x+117,y+36, 0x82fd64,true);
        }
    }

    private void renderCoolTime(DrawContext context, int x, int y) {
        if (handler.isCooling()){
            int offset = 16 - handler.getScaledCoolTime();
            context.drawTexture(TEXTURE, x + 136, y + 52 + offset, 188, offset, 3, handler.getScaledCoolTime());
        }
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()){
            context.drawTexture(TEXTURE, x + 154, y + 34, 176, 0 , 12, handler.getScaledProgress());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
