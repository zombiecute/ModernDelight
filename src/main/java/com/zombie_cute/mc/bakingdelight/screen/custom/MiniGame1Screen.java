package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.entities.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.bakingdelight.util.NetworkHandler;
import com.zombie_cute.mc.bakingdelight.util.Pos2;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Environment(EnvType.CLIENT)
public class MiniGame1Screen extends Screen {
    private final Screen parent;
    private final ElectriciansDeskBlockEntity blockEntity;
    public MiniGame1Screen(Text title, Screen parent, ElectriciansDeskBlockEntity blockEntity,int goal) {
        super(title);
        this.parent = parent;
        this.blockEntity = blockEntity;
        this.goal = goal;
        spawnARandomNumber();
        spawnARandomNumber();
    }
    private final int backgroundWidth = 176;
    private final int backgroundHeight = 166;
    private int x;
    private int y;
    private final int[][] game = {
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0}
    };
    private boolean advanceMode = false;
    private final int[] spawnNumberPool = {2,4};
    private final int[] advanceSpawnNumberPool = {2,4,8};
    private final int goal;
    private boolean isGameEnd = false;
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/mini_game_1_gui.png");
    private Pos2 getARandomPos(){
        List<Pos2> availablePos = new ArrayList<>();
        for (int i = 0;i < 4;i++){
            for (int j = 0;j < 4;j++){
                if (game[i][j] == 0){
                    Pos2 temp = new Pos2(j,i);
                    availablePos.add(temp);
                }
            }
        }
        if (availablePos.isEmpty()){
            return null;
        } else {
            int k = (int)(Math.random() * availablePos.size());
            return availablePos.get(k);
        }
    }
    private void spawnARandomNumber(){
        int num;
        if (advanceMode){
            num = advanceSpawnNumberPool[(int) (Math.random() * advanceSpawnNumberPool.length)];
        } else {
            num = spawnNumberPool[(int) (Math.random() * spawnNumberPool.length)];
        }
        Pos2 tem = getARandomPos();
        if (tem != null){
            game[tem.getY()][tem.getX()] = num;
        }
    }
    private void spawnRandomNumbers() {
        if (advanceMode){
            if (Math.random() < 0.7)
                spawnARandomNumber();
        } else {
            if (Math.random() < 0.3){
                spawnARandomNumber();
                spawnARandomNumber();
            } else {
                spawnARandomNumber();
            }
        }
    }
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
        renderNumbers(context);
        renderGoal(context);
        renderDoneButton(context,mouseX,mouseY);
        renderControlButton(context,mouseX,mouseY);

        RenderSystem.disableDepthTest();
        context.getMatrices().push();
        context.getMatrices().translate(x, y, 0.0F);
    }
    private void renderNumbers(DrawContext context){
        for (int i = 0;i < 4;i++){
            for (int j = 0;j < 4;j++){
                if (game[i][j] >= goal){
                    isGameEnd = true;
                }
                switch (game[i][j]){
                    case 0 -> {}
                    case 2 -> context.drawTexture(TEXTURE,
                            x + 24 + j * 32, y + 11 + i * 32, 0, 188, 32,32);
                    case 4 -> context.drawTexture(TEXTURE,
                            x + 24 + j * 32, y + 11 + i * 32, 32, 188, 32,32);
                    case 8 -> context.drawTexture(TEXTURE,
                            x + 24 + j * 32, y + 11 + i * 32, 64, 188, 32,32);
                    case 16 -> context.drawTexture(TEXTURE,
                            x + 24 + j * 32, y + 11 + i * 32, 96, 188, 32,32);
                    case 32 -> context.drawTexture(TEXTURE,
                            x + 24 + j * 32, y + 11 + i * 32, 128, 188, 32,32);
                    case 64 -> context.drawTexture(TEXTURE,
                            x + 24 + j * 32, y + 11 + i * 32, 160, 188, 32,32);
                    case 128 -> {
                        context.drawTexture(TEXTURE,
                                x + 24 + j * 32, y + 11 + i * 32, 192, 188, 32, 32);
                        advanceMode = true;
                    }
                    case 256 -> context.drawTexture(TEXTURE,
                            x + 24 + j * 32, y + 11 + i * 32, 224, 188, 32,32);
                    case 512 -> context.drawTexture(TEXTURE,
                            x + 24 + j * 32, y + 11 + i * 32, 0, 220, 32,32);
                    case 1024 -> context.drawTexture(TEXTURE,
                            x + 24 + j * 32, y + 11 + i * 32, 32, 220, 32,32);
                    case 2048 -> context.drawTexture(TEXTURE,
                            x + 24 + j * 32, y + 11 + i * 32, 64, 220, 32,32);
                    default -> {
                        context.drawTexture(TEXTURE,
                                x + 24 + j * 32, y + 11 + i * 32, 96, 220, 32,32);
                        context.drawText(textRenderer,String.valueOf(game[i][j]),x + 28 + j * 32, y + 23 + i * 32,0xffffff,true);
                    }
                }
            }
        }
    }
    private void renderDoneButton(DrawContext context, int mouseX, int mouseY){
        if (isGameEnd){
            boolean b = mouseX >= x + 149 && mouseY >= y + 145 && mouseX <= x + 159 && mouseY <= y + 155;
            context.drawTexture(TEXTURE, x + 149, y + 145, 0, 166, 11, 11);
            if (b){
                context.drawTexture(TEXTURE, x + 149, y + 145, 11, 166, 11,11);
            }
        }
    }
    private void renderControlButton(DrawContext context, int mouseX, int mouseY){
        boolean bU = mouseX >= x + 40 && mouseY >= y + 145 && mouseX <= x + 50 && mouseY <= y + 155;
        boolean bD = mouseX >= x + 52 && mouseY >= y + 145 && mouseX <= x + 62 && mouseY <= y + 155;
        boolean bL = mouseX >= x + 16 && mouseY >= y + 145 && mouseX <= x + 26 && mouseY <= y + 155;
        boolean bR = mouseX >= x + 28 && mouseY >= y + 145 && mouseX <= x + 38 && mouseY <= y + 155;
        if (bU){
            context.drawTexture(TEXTURE, x + 40, y + 145, 22, 177, 11,11);
        } else if (bD) {
            context.drawTexture(TEXTURE, x + 52, y + 145, 33, 177, 11,11);
        } else if (bL) {
            context.drawTexture(TEXTURE, x + 16, y + 145, 0, 177, 11,11);
        } else if (bR) {
            context.drawTexture(TEXTURE, x + 28, y + 145, 11, 177, 11,11);
        }
    }
    private void renderGoal(DrawContext context){
        if (isGameEnd){
            context.drawText(textRenderer,String.valueOf(goal),x + 68,y + 146, 0x00ff00,true);
        } else {
            context.drawText(textRenderer,String.valueOf(goal),x + 68,y + 146, 0xff0000,true);
        }
    }

    private void handleUpButton(){
        MinecraftClient.getInstance().getSoundManager()
                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        for (int i = 0; i < 4; i++){
            int count = 1;
            for (int j = 3; j > 0; j--){
                if (j == 3){
                    if (game[3][i] == game[2][i] && game[1][i] == game[0][i]
                            && game[3][i] != 0 && game[1][i] != 0){
                        int tem1 = game[1][i] + game[0][i];
                        int tem2 = game[3][i] + game[2][i];
                        game[0][i] = tem1;
                        game[1][i] = tem2;
                        game[2][i] = 0;
                        game[3][i] = 0;
                        break;
                    }
                }
                if (game[j][i] != 0 && game[j-1][i] == 0){
                    game[j-1][i] = game[j][i];
                    game[j][i] = 0;
                    continue;
                }
                if (game[j][i] != 0 && game[j-1][i] != 0) {
                    if (game[j][i] == game[j-1][i] && count != 0){
                        int temp = game[j][i] + game[j-1][i];
                        game[j-1][i] = temp;
                        game[j][i] = 0;
                        count = 0;
                    }
                }
            }
        }
        for (int k = 0; k < 2; k++){
            for (int i = 0; i < 4; i++){
                for (int j = 3; j > 0; j--){
                    if (game[j][i] != 0 && game[j-1][i] == 0){
                        game[j-1][i] = game[j][i];
                        game[j][i] = 0;
                    }
                }
            }
        }
        spawnRandomNumbers();
    }


    private void handleDownButton(){
        MinecraftClient.getInstance().getSoundManager()
                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        for (int i = 0; i < 4; i++){
            int count = 1;
            for (int j = 0; j < 3; j++){
                if (j == 0){
                    if (game[3][i] == game[2][i] && game[1][i] == game[0][i]
                            && game[3][i] != 0 && game[1][i] != 0){
                        int tem1 = game[1][i] + game[0][i];
                        int tem2 = game[3][i] + game[2][i];
                        game[0][i] = 0;
                        game[1][i] = 0;
                        game[2][i] = tem1;
                        game[3][i] = tem2;
                        break;
                    }
                }
                if (game[j][i] != 0 && game[j+1][i] == 0){
                    game[j+1][i] = game[j][i];
                    game[j][i] = 0;
                    continue;
                }
                if (game[j][i] != 0 && game[j+1][i] != 0) {
                    if (game[j][i] == game[j+1][i] && count != 0){
                        int temp = game[j][i] + game[j+1][i];
                        game[j+1][i] = temp;
                        game[j][i] = 0;
                        count = 0;
                    }
                }
            }
        }
        for (int k = 0; k < 2; k++){
            for (int i = 0; i < 4; i++){
                for (int j = 0; j < 3; j++){
                    if (game[j][i] != 0 && game[j+1][i] == 0){
                        game[j+1][i] = game[j][i];
                        game[j][i] = 0;
                    }
                }
            }
        }
        spawnRandomNumbers();
    }
    private void handleLeftButton(){
        MinecraftClient.getInstance().getSoundManager()
                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        for (int i = 0; i < 4; i++){
            int count = 1;
            for (int j = 3; j > 0; j--){
                if (j == 3){
                    if (game[i][3] == game[i][2] && game[i][1] == game[i][0]
                            && game[i][3] != 0 && game[i][1] != 0){
                        int tem1 = game[i][1] + game[i][0];
                        int tem2 = game[i][3] + game[i][2];
                        game[i][0] = tem1;
                        game[i][1] = tem2;
                        game[i][2] = 0;
                        game[i][3] = 0;
                        break;
                    }
                }
                if (game[i][j] != 0 && game[i][j-1] == 0){
                    game[i][j-1] = game[i][j];
                    game[i][j] = 0;
                    continue;
                }
                if (game[i][j] != 0 && game[i][j-1] != 0) {
                    if (game[i][j] == game[i][j-1] && count != 0){
                        int temp = game[i][j] + game[i][j-1];
                        game[i][j-1] = temp;
                        game[i][j] = 0;
                        count = 0;
                    }
                }
            }
        }
        for (int k = 0; k < 2; k++){
            for (int i = 0; i < 4; i++){
                for (int j = 3; j > 0; j--){
                    if (game[i][j] != 0 && game[i][j-1] == 0){
                        game[i][j-1] = game[i][j];
                        game[i][j] = 0;
                    }
                }
            }
        }
        spawnRandomNumbers();
    }
    private void handleRightButton(){
        MinecraftClient.getInstance().getSoundManager()
                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        for (int i = 0; i < 4; i++){
            int count = 1;
            for (int j = 0; j < 3; j++){
                if (j == 0){
                    if (game[i][3] == game[i][2] && game[i][1] == game[i][0]
                            && game[i][3] != 0 && game[i][1] != 0){
                        int tem1 = game[i][1] + game[i][0];
                        int tem2 = game[i][3] + game[i][2];
                        game[i][0] = 0;
                        game[i][1] = 0;
                        game[i][2] = tem1;
                        game[i][3] = tem2;
                        break;
                    }
                }
                if (game[i][j] != 0 && game[i][j+1] == 0){
                    game[i][j+1] = game[i][j];
                    game[i][j] = 0;
                    continue;
                }
                if (game[i][j] != 0 && game[i][j+1] != 0) {
                    if (game[i][j] == game[i][j+1] && count != 0){
                        int temp = game[i][j] + game[i][j+1];
                        game[i][j+1] = temp;
                        game[i][j] = 0;
                        count = 0;
                    }
                }
            }
        }
        for (int k = 0; k < 2; k++){
            for (int i = 0; i < 4; i++){
                for (int j = 0; j < 3; j++){
                    if (game[i][j] != 0 && game[i][j+1] == 0){
                        game[i][j+1] = game[i][j];
                        game[i][j] = 0;
                    }
                }
            }
        }
        spawnRandomNumbers();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean bU = mouseX >= x + 40 && mouseY >= y + 145 && mouseX <= x + 50 && mouseY <= y + 155;
        boolean bD = mouseX >= x + 52 && mouseY >= y + 145 && mouseX <= x + 62 && mouseY <= y + 155;
        boolean bL = mouseX >= x + 16 && mouseY >= y + 145 && mouseX <= x + 26 && mouseY <= y + 155;
        boolean bR = mouseX >= x + 28 && mouseY >= y + 145 && mouseX <= x + 38 && mouseY <= y + 155;
        boolean b = mouseX >= x + 149 && mouseY >= y + 145 && mouseX <= x + 159 && mouseY <= y + 155;
        if (bU){
            handleUpButton();
            return true;
        }
        if (bD){
            handleDownButton();
            return true;
        }
        if (bL){
            handleLeftButton();
            return true;
        }
        if (bR){
            handleRightButton();
            return true;
        }
        if (isGameEnd){
            if (b){
                byte[] array = new byte[1];
                array[0] = 1;
                NetworkHandler.sendChangeBlockEntityDataPacket(blockEntity.getPos(),array);
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                this.close();
                return true;
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
            if (this.client.options.forwardKey.matchesKey(keyCode, scanCode) || keyCode == InputUtil.GLFW_KEY_UP){
                this.handleUpButton();
                return true;
            }
            if (this.client.options.backKey.matchesKey(keyCode, scanCode) || keyCode == InputUtil.GLFW_KEY_DOWN){
                this.handleDownButton();
                return true;
            }
            if (this.client.options.leftKey.matchesKey(keyCode, scanCode) || keyCode == InputUtil.GLFW_KEY_LEFT){
                this.handleLeftButton();
                return true;
            }
            if (this.client.options.rightKey.matchesKey(keyCode, scanCode) || keyCode == InputUtil.GLFW_KEY_RIGHT){
                this.handleRightButton();
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
