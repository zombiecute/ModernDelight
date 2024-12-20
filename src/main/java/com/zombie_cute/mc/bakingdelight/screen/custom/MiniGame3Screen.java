package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.entities.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.bakingdelight.util.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class MiniGame3Screen extends Screen {
    private final Screen parent;
    private final ElectriciansDeskBlockEntity blockEntity;
    public MiniGame3Screen(Text title, Screen parent, ElectriciansDeskBlockEntity blockEntity, int goal) {
        super(title);
        this.parent = parent;
        this.blockEntity = blockEntity;
        if (goal >= 6){
            this.goal = 6;
        } else if (goal <= 0){
            this.goal = 1;
        } else this.goal = goal;
        for (int i = 0; i < goal;i++){
            Lines[0][i] = goal - i;
        }
    }
    private final int backgroundWidth = 176;
    private final int backgroundHeight = 166;
    private int x;
    private int y;
    private final int goal;
    private boolean isGameEnd = false;
    private final int[][] Lines = new int[3][6];
    private final boolean[] mouseLine = new boolean[3];
    private boolean isSelectedMode = false;
    private final boolean[] selectedLine = {false,false,false};
    private int selectedNum = 0;
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/mini_game_3_gui.png");
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

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth,backgroundHeight);
        renderDoneButton(context,mouseX,mouseY);
        renderBlocks(context,mouseX,mouseY);
        renderSelectedNum(context,mouseX,mouseY);



        RenderSystem.disableDepthTest();
        context.getMatrices().push();
        context.getMatrices().translate(x, y, 0.0F);
    }
    private void renderDoneButton(DrawContext context, int mouseX, int mouseY){
        if (isGameEnd){
            boolean b = mouseX >= x + 149 && mouseY >= y + 133 && mouseX <= x + 159 && mouseY <= y + 143;
            context.drawTexture(TEXTURE, x + 149, y + 133, 0, 166, 11, 11);
            if (b){
                context.drawTexture(TEXTURE, x + 149, y + 133, 11, 166, 11,11);
            }
        }
    }
    private void renderBlocks(DrawContext context,int mouseX,int mouseY){
        mouseLine[0] = mouseX >= x + 14 && mouseY >= y + 48 && mouseX <= x + 49 && mouseY <= y + 122;
        mouseLine[1] = mouseX >= x + 70 && mouseY >= y + 48 && mouseX <= x + 105 && mouseY <= y + 122;
        mouseLine[2] = mouseX >= x + 126 && mouseY >= y + 48 && mouseX <= x + 161 && mouseY <= y + 122;
        for (int k = 0;k < 3; k++){
            if (k==2){
                boolean b = true;
                for (int i = 0; i < goal;i++){
                    if (Lines[k][i] != goal - i){
                        b = false;
                        break;
                    }
                }
                isGameEnd = b;
            }
            int min = goal;
            int counter = -1;
            for (int i = 0; i < 6; i++){
                if (Lines[k][i] != 0){
                    counter ++;
                    context.drawTexture(TEXTURE, x + 14 + 56 * k,y + 118 - i * 5, 22, 166 + (6 - Lines[k][i]) * 5, 36,5);
                    if (Lines[k][i] < min){
                        min = Lines[k][i];
                    }
                }
            }
            if (isSelectedMode){
                if (selectedLine[k]){
                    context.drawTexture(TEXTURE, x + 24 + 56 * k,y + 48, 176, 48, 16,75 - (counter + 1) * 5);
                    context.drawTexture(TEXTURE, x + 14 + 56 * k,y + 118 - counter * 5, 63, 166 + (6 - min) * 5, 36,5);
                } else {
                    if (mouseLine[k]){
                        if (selectedNum < min) {
                            context.drawTexture(TEXTURE, x + 24 + 56 * k,y + 48, 176, 48, 16,75 - (counter + 1) * 5);
                        } else {
                            context.drawTexture(TEXTURE, x + 24 + 56 * k,y + 48, 192, 48, 16,75 - (counter + 1) * 5);
                        }
                    }
                }
            } else {
                if (mouseLine[k]){
                    context.drawTexture(TEXTURE, x + 24 + 56 * k,y + 48, 176, 48, 16,75 - (counter + 1) * 5);
                    if (counter != -1){
                        context.drawTexture(TEXTURE, x + 14 + 56 * k,y + 118 - counter * 5, 63, 166 + (6 - min) * 5, 36,5);
                    }
                }
            }
        }
    }
    private void renderSelectedNum(DrawContext context,int mouseX,int mouseY){
        if (isSelectedMode){
            context.drawTexture(TEXTURE, mouseX - 18,mouseY - 3, 22, 166 + (6 - selectedNum) * 5, 36,5);
        }
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean gameEndButton = mouseX >= x + 149 && mouseY >= y + 133 && mouseX <= x + 159 && mouseY <= y + 143;
        if (isGameEnd){
            if (gameEndButton){
                byte[] array = new byte[1];
                array[0] = 1;
                NetworkHandler.sendChangeBlockEntityDataPacket(blockEntity.getPos(),array);
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                this.close();
                return true;
            }
        }
        for (int i = 0; i < 3; i++){
            if (mouseLine[i]){
                if (isSelectedMode){
                    if (selectedLine[i]){
                        selectedNum = 0;
                        isSelectedMode = false;
                        selectedLine[i] = false;
                        MinecraftClient.getInstance().getSoundManager()
                                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                        return true;
                    } else {
                        int min = goal;
                        int counter = 0;
                        for (int j = 0;j < 6;j++){
                            if (Lines[i][j] != 0){
                                counter++;
                                if (Lines[i][j] < min){
                                    min = Lines[i][j];
                                }
                            }
                        }
                        if (counter == 0){
                            Lines[i][0] = selectedNum;
                            for (int k = 0;k < 3;k++){
                                if (selectedLine[k]){
                                    int counter2 = 0;
                                    for (int j = 0;j < 6;j++){
                                        if (Lines[k][j] != 0){
                                            counter2 ++;
                                        }
                                    }
                                    Lines[k][counter2-1] = 0;
                                    selectedLine[k] = false;
                                }
                            }
                            selectedNum = 0;
                            isSelectedMode = false;
                            MinecraftClient.getInstance().getSoundManager()
                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                            return true;
                        } else {
                            if (min > selectedNum){
                                Lines[i][counter] = selectedNum;
                                for (int k = 0;k < 3;k++){
                                    if (selectedLine[k]){
                                        int counter2 = 0;
                                        for (int j = 0;j < 6;j++){
                                            if (Lines[k][j] != 0){
                                                counter2 ++;
                                            }
                                        }
                                        Lines[k][counter2-1] = 0;
                                        selectedLine[k] = false;
                                    }
                                }
                                selectedNum = 0;
                                isSelectedMode = false;
                                MinecraftClient.getInstance().getSoundManager()
                                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                return true;
                            }
                        }
                    }
                } else {
                    int min = goal;
                    int counter = 0;
                    for (int j = 0;j < 6;j++){
                        if (Lines[i][j] != 0){
                            counter++;
                            if (Lines[i][j] < min){
                                min = Lines[i][j];
                            }
                        }
                    }
                    if (counter != 0){
                        selectedNum = min;
                        isSelectedMode = true;
                        selectedLine[i] = true;
                        MinecraftClient.getInstance().getSoundManager()
                                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                        return true;
                    }
                }
            }
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
            if (isGameEnd){
                if (keyCode == InputUtil.GLFW_KEY_ENTER || keyCode == InputUtil.GLFW_KEY_KP_ENTER){
                    byte[] array = new byte[1];
                    array[0] = 1;
                    NetworkHandler.sendChangeBlockEntityDataPacket(blockEntity.getPos(),array);
                    MinecraftClient.getInstance().getSoundManager()
                            .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    this.close();
                    return true;
                }
            }
            if (this.client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
                this.close();
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
        byte[] array = new byte[1];
        array[0] = 4;
        NetworkHandler.sendChangeBlockEntityDataPacket(blockEntity.getPos(),array);
        Objects.requireNonNull(client).setScreen(parent);
    }
}
