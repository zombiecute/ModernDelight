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

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class MiniGame2Screen extends Screen {
    private final Screen parent;
    private final ElectriciansDeskBlockEntity blockEntity;
    public MiniGame2Screen(Text title, Screen parent, ElectriciansDeskBlockEntity blockEntity, int goal) {
        super(title);
        this.parent = parent;
        this.blockEntity = blockEntity;
        this.goal = goal;
        reset(false);
    }
    private final int backgroundWidth = 176;
    private final int backgroundHeight = 166;
    private int x;
    private int y;
    private final int goal;
    private int currentGoal = 0;
    private boolean isGameEnd = false;
    private boolean isMoveMode = false;
    private boolean isMoving = false;
    private boolean isRemoving = false;
    private boolean isRenderingBluePrint = false;
    private final Pos2[][] stage0 = new Pos2[4][4];
    private final Pos2[][] answerStage0 = new Pos2[4][4];
    private final Pos2[][] stage1 = new Pos2[4][8];
    private final Pos2[][] answerStage1 = new Pos2[4][8];
    private final Pos2[][] stage2 = new Pos2[8][8];
    private final Pos2[][] answerStage2 = new Pos2[8][8];
    private final boolean[][] mouseStage0 = new boolean[4][4];
    private final boolean[][] mouseStage1 = new boolean[4][8];
    private final boolean[][] mouseStage2 = new boolean[8][8];
    private int stage = 0;
    private Pos2 selectedPos2;
    private Pos2 tempPos2;
    boolean canNextChange = true;
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/mini_game_2_gui.png");
    private void randomStage(int type){
        switch (type){
            case 0 -> {
                for (int i = 0;i < 4;i++){
                    for (int j = 0;j < 4;j++){
                        answerStage0[i][j] = new Pos2(0,0);
                    }
                }
                for (int i = 0;i < 4;i++){
                    for (int j = 0;j < 4;j++){
                        if (Math.random() < 0.2){
                            answerStage0[i][j] = Pos2.random(3,4);
                        }
                    }
                }
            }
            case 1 -> {
                for (int i = 0;i < 4;i++){
                    for (int j = 0;j < 8;j++){
                        answerStage1[i][j] = new Pos2(0,0);
                    }
                }
                for (int i = 0;i < 4;i++){
                    for (int j = 0;j < 8;j++){
                        if (Math.random() < 0.2){
                            answerStage1[i][j] = Pos2.random(3,4);
                        }
                    }
                }
            }
            case 2 -> {
                for (int i = 0;i < 8;i++){
                    for (int j = 0;j < 8;j++){
                        answerStage2[i][j] = new Pos2(0,0);
                    }
                }
                for (int i = 0;i < 8;i++){
                    for (int j = 0;j < 8;j++){
                        if (Math.random() < 0.2){
                            answerStage2[i][j] = Pos2.random(3,4);
                        }
                    }
                }
            }
        }
        switch (type){
            case 0 -> {
                for (int i = 0;i < 4;i++){
                    for (int j = 0;j < 4;j++){
                        stage0[i][j] = Pos2.random(3,4);
                    }
                }
            }
            case 1 -> {
                for (int i = 0;i < 4;i++){
                    for (int j = 0;j < 8;j++){
                        stage1[i][j] = Pos2.random(3,4);
                    }
                }
            }
            case 2 -> {
                for (int i = 0;i < 8;i++){
                    for (int j = 0;j < 8;j++){
                        stage2[i][j] = Pos2.random(3,4);
                    }
                }
            }
        }
        switch (type){
            case 0 -> {
                int type1 = 0;
                int type1_a = 0;
                int type2 = 0;
                int type2_a = 0;
                int type3 = 0;
                int type3_a = 0;
                int type4 = 0;
                int type4_a = 0;
                for (int i = 0;i < 4;i ++){
                    for (int j = 0;j < 4;j++){
                        Pos2 temp = stage0[i][j];
                        Pos2 temp_a = answerStage0[i][j];
                        if (temp.getX() == 0 && temp.getY() >=1 && temp.getY() <=2){
                            type1++;
                        }
                        if (temp_a.getX() == 0 && temp_a.getY() >=1 && temp_a.getY() <=2){
                            type1_a++;
                        }
                        if (temp.getX() == 0 && temp.getY() ==3){
                            type4++;
                        }
                        if (temp_a.getX() == 0 && temp_a.getY() ==3){
                            type4_a++;
                        }
                        if (temp.getX() == 1){
                            type2++;
                        }
                        if (temp_a.getX() == 1){
                            type2_a++;
                        }
                        if (temp.getX() == 2){
                            type3++;
                        }
                        if (temp_a.getX() == 2){
                            type3_a++;
                        }
                    }
                }
                if (type1 < type1_a || type2 < type2_a || type3 < type3_a || type4 < type4_a){
                    randomStage(type);
                }
            }
            case 1 -> {
                int type1 = 0;
                int type1_a = 0;
                int type2 = 0;
                int type2_a = 0;
                int type3 = 0;
                int type3_a = 0;
                int type4 = 0;
                int type4_a = 0;
                for (int i = 0;i < 4;i ++){
                    for (int j = 0;j < 8;j++){
                        Pos2 temp = stage1[i][j];
                        Pos2 temp_a = answerStage1[i][j];
                        if (temp.getX() == 0 && temp.getY() >=1 && temp.getY() <=2){
                            type1++;
                        }
                        if (temp_a.getX() == 0 && temp_a.getY() >=1 && temp_a.getY() <=2){
                            type1_a++;
                        }
                        if (temp.getX() == 0 && temp.getY() ==3){
                            type4++;
                        }
                        if (temp_a.getX() == 0 && temp_a.getY() ==3){
                            type4_a++;
                        }
                        if (temp.getX() == 1){
                            type2++;
                        }
                        if (temp_a.getX() == 1){
                            type2_a++;
                        }
                        if (temp.getX() == 2){
                            type3++;
                        }
                        if (temp_a.getX() == 2){
                            type3_a++;
                        }
                    }
                }
                if (type1 < type1_a || type2 < type2_a || type3 < type3_a || type4 < type4_a){
                    randomStage(type);
                }
            }
            case 2 -> {
                int type1 = 0;
                int type1_a = 0;
                int type2 = 0;
                int type2_a = 0;
                int type3 = 0;
                int type3_a = 0;
                int type4 = 0;
                int type4_a = 0;
                for (int i = 0;i < 8;i ++){
                    for (int j = 0;j < 8;j++){
                        Pos2 temp = stage2[i][j];
                        Pos2 temp_a = answerStage2[i][j];
                        if (temp.getX() == 0 && temp.getY() >=1 && temp.getY() <=2){
                            type1++;
                        }
                        if (temp_a.getX() == 0 && temp_a.getY() >=1 && temp_a.getY() <=2){
                            type1_a++;
                        }
                        if (temp.getX() == 0 && temp.getY() ==3){
                            type4++;
                        }
                        if (temp_a.getX() == 0 && temp_a.getY() ==3){
                            type4_a++;
                        }
                        if (temp.getX() == 1){
                            type2++;
                        }
                        if (temp_a.getX() == 1){
                            type2_a++;
                        }
                        if (temp.getX() == 2){
                            type3++;
                        }
                        if (temp_a.getX() == 2){
                            type3_a++;
                        }
                    }
                }
                if (type1 < type1_a || type2 < type2_a || type3 < type3_a || type4 < type4_a){
                    randomStage(type);
                }
            }
        }
    }
    private void reset(boolean reduceGoal){
        this.stage = (int)(Math.random() * 3);
        selectedPos2 = null;
        tempPos2 = null;
        isMoving = false;
        isRemoving = false;
        if (reduceGoal){
            if (currentGoal != 0){
                currentGoal--;
            }
        }
        randomStage(stage);
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

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth,backgroundHeight,512,512);
        renderDoneButton(context,mouseX,mouseY);
        renderResetButton(context,mouseX,mouseY);
        renderNextChallengeButton(context,mouseX,mouseY);
        renderMoveModeButton(context,mouseX,mouseY);
        renderGoalText(context);
        renderBackGround(context);
        renderElements(context,mouseX,mouseY);
        renderRemovingTool(context,mouseX,mouseY);
        renderSelectedPos2(context,mouseX,mouseY);

        renderBluePrint(context);
        renderBluePrintButton(context,mouseX,mouseY);

        RenderSystem.disableDepthTest();
        context.getMatrices().push();
        context.getMatrices().translate(x, y, 0.0F);
    }
    private void renderRemovingTool(DrawContext context,int mouseX, int mouseY){
        boolean b = mouseX >= x + 8 && mouseY >= y + 142 && mouseX <= x + 23 && mouseY <= y + 157 && !isRenderingBluePrint;
        if (isRemoving){
            if (b){
                context.drawTexture(TEXTURE,x + 8,y + 142,120,166,16,16,512,512);
            }
            context.drawTexture(TEXTURE,mouseX - 8,mouseY - 8,88,166,16,16,512,512);
        } else {
            context.drawTexture(TEXTURE,x + 8,y + 142,88,166,16,16,512,512);
            if (b){
                context.drawTexture(TEXTURE,x + 8,y + 142,104,166,16,16,512,512);
            }
        }
    }
    private void renderGoalText(DrawContext context) {
        if (currentGoal<goal && !isGameEnd){
            context.drawText(textRenderer,currentGoal+"/"+goal,x + 74,y + 145,0xff0000,true);
        } else {
            context.drawText(textRenderer,currentGoal+"/"+goal,x + 74,y + 145,0x00ff00,true);
            isGameEnd = true;
        }
    }
    private void renderBackGround(DrawContext context){
        switch (stage){
            case 0 -> context.drawTexture(TEXTURE, x + 42, y + 45, 0, 166, 88,66,512,512);
            case 1 -> context.drawTexture(TEXTURE, x + 10, y + 45, 0, 232, 152,66,512,512);
            case 2 -> context.drawTexture(TEXTURE, x + 10, y + 10, 0, 298, 152,130,512,512);
        }
    }
    private void renderDoneButton(DrawContext context,int mouseX,int mouseY){
        boolean b = mouseX >= x + 142 && mouseY >= y + 144 && mouseX <= x + 152 && mouseY <= y + 154 && !isRenderingBluePrint;
        if (isGameEnd){
            context.drawTexture(TEXTURE, x + 142, y + 144, 176, 133, 11,11,512,512);
            if (b){
                context.drawTexture(TEXTURE, x + 142, y + 144, 187, 133, 11,11,512,512);
            }
        }
    }
    private void renderResetButton(DrawContext context,int mouseX, int mouseY){
        if (mouseX >= x + 130 && mouseY >= y + 144 && mouseX <= x + 140 && mouseY <= y + 154 && !isRenderingBluePrint){
            context.drawTexture(TEXTURE,x + 130,y + 144,198,133,11,11,512,512);
        }
    }
    private void renderNextChallengeButton(DrawContext context,int mouseX, int mouseY){
        boolean b = true;
        switch (stage){
            case 0 -> {
                for (int i = 0;i < 4;i++){
                    for (int j = 0;j < 4;j++){
                        if (!stage0[i][j].equals(answerStage0[i][j])){
                            b = false;
                            break;
                        }
                    }
                }
            }
            case 1 -> {
                for (int i = 0;i < 4;i++){
                    for (int j = 0;j < 8;j++){
                        if (!stage1[i][j].equals(answerStage1[i][j])){
                            b = false;
                            break;
                        }
                    }
                }
            }
            case 2 -> {
                for (int i = 0;i < 8;i++){
                    for (int j = 0;j < 8;j++){
                        if (!stage2[i][j].equals(answerStage2[i][j])){
                            b = false;
                            break;
                        }
                    }
                }
            }
        }
        canNextChange = b;
        if (canNextChange){
            context.drawTexture(TEXTURE,x + 118,y + 144,176,122,11,11,512,512);
            if (mouseX >= x + 118 && mouseY >= y + 144 && mouseX <= x + 128 && mouseY <= y + 154 && !isRenderingBluePrint){
                context.drawTexture(TEXTURE,x + 118,y + 144,187,122,11,11,512,512);
            }
        }
    }
    private void renderMoveModeButton(DrawContext context,int mouseX,int mouseY){
        boolean b = mouseX >= x + 39 && mouseY >= y + 141 && mouseX <= x + 69 && mouseY <= y + 158 && !isRenderingBluePrint;
        if (isMoveMode){
            context.drawTexture(TEXTURE,x + 39,y + 141,176,86,31,18,512,512);
            if (b){
                context.drawTexture(TEXTURE,x + 39,y + 141,176,104,31,18,512,512);
            }
        } else {
            if (b){
                context.drawTexture(TEXTURE,x + 39,y + 141,207,86,31,18,512,512);
            }
        }
    }
    private void renderElements(DrawContext context,int mouseX,int mouseY){
        if (!isRenderingBluePrint){
            switch (stage){
                case 0 -> {
                    for (int i = 0; i < 4; i++){
                        for (int j = 0; j < 4; j++){
                            int x = stage0[i][j].getX();
                            int y = stage0[i][j].getY();
                            if (mouseX >= this.x + 56 + 16 * j
                                    && mouseY >= this.y + 46 + 16 * i
                                    && mouseX <= this.x + 71 + 16 * j
                                    && mouseY <= this.y + 61 + 16 * i){
                                mouseStage0[i][j] = true;
                                if (isRemoving){
                                    if (!(x==0 && y==0)){
                                        context.drawTexture(TEXTURE,this.x + 56 + 16 * j,
                                                this.y + 46 + 16 * i,
                                                224 + 16 * x,
                                                176 + 16 * y,
                                                16,16,512,512);
                                    }
                                } else if (isMoveMode){
                                    if (isMoving){
                                        if (x==0 && y==0){
                                            context.drawTexture(TEXTURE,this.x + 56 + 16 * j,
                                                    this.y + 46 + 16 * i,
                                                    224,
                                                    176,
                                                    16,16,512,512);
                                        }
                                    } else {
                                        if (!( x==0 && y==0 )){
                                            context.drawTexture(TEXTURE,this.x + 56 + 16 * j,
                                                    this.y + 46 + 16 * i,
                                                    224 + 16 * x,
                                                    176 + 16 * y,
                                                    16,16,512,512);
                                        }
                                    }
                                } else {
                                    if (! (x==0 && y==0)){
                                        context.drawTexture(TEXTURE,this.x + 56 + 16 * j,
                                                this.y + 46 + 16 * i,
                                                224 + 16 * x,
                                                176 + 16 * y,
                                                16,16,512,512);
                                    }
                                }
                            } else {
                                mouseStage0[i][j] = false;
                                context.drawTexture(TEXTURE,this.x + 56 + 16 * j,
                                        this.y + 46 + 16 * i,
                                        160 + 16 * x,
                                        176 + 16 * y,
                                        16,16,512,512);
                            }
                        }
                    }
                }
                case 1 -> {
                    for (int i = 0; i < 4; i++){
                        for (int j = 0; j < 8; j++){
                            int x = stage1[i][j].getX();
                            int y = stage1[i][j].getY();
                            if (mouseX >= this.x + 24 + 16 * j
                                    && mouseY >= this.y + 46 + 16 * i
                                    && mouseX <= this.x + 39 + 16 * j
                                    && mouseY <= this.y + 61 + 16 * i){
                                mouseStage1[i][j] = true;
                                if (isRemoving){
                                    if (!(x==0 && y==0)){
                                        context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                                this.y + 46 + 16 * i,
                                                224 + 16 * x,
                                                176 + 16 * y,
                                                16,16,512,512);
                                    }
                                } else if (isMoveMode){
                                    if (isMoving){
                                        if (x==0 && y==0){
                                            context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                                    this.y + 46 + 16 * i,
                                                    224,
                                                    176,
                                                    16,16,512,512);
                                        }
                                    } else {
                                        if (!( (x==0 && y==0) )){
                                            context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                                    this.y + 46 + 16 * i,
                                                    224 + 16 * x,
                                                    176 + 16 * y,
                                                    16,16,512,512);
                                        }
                                    }
                                } else {
                                    if (!( x==0 && y==0 )){
                                        context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                                this.y + 46 + 16 * i,
                                                224 + 16 * x,
                                                176 + 16 * y,
                                                16,16,512,512);
                                    }
                                }
                            } else {
                                mouseStage1[i][j] = false;
                                context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                        this.y + 46 + 16 * i,
                                        160 + 16 * x,
                                        176 + 16 * y,
                                        16,16,512,512);
                            }
                        }
                    }
                }
                case 2 -> {
                    for (int i = 0; i < 8; i++){
                        for (int j = 0; j < 8; j++){
                            int x = stage2[i][j].getX();
                            int y = stage2[i][j].getY();
                            if (mouseX >= this.x + 24 + 16 * j
                                    && mouseY >= this.y + 11 + 16 * i
                                    && mouseX <= this.x + 39 + 16 * j
                                    && mouseY <= this.y + 26 + 16 * i){
                                mouseStage2[i][j] = true;
                                if (isRemoving){
                                    if (!(x==0 && y==0)){
                                        context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                                this.y + 11 + 16 * i,
                                                224 + 16 * x,
                                                176 + 16 * y,
                                                16,16,512,512);
                                    }
                                } else if (isMoveMode){
                                    if (isMoving){
                                        if (x==0 && y==0){
                                            context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                                    this.y + 11 + 16 * i,
                                                    224,
                                                    176,
                                                    16,16,512,512);
                                        }
                                    } else {
                                        if (!( (x==0 && y==0) )){
                                            context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                                    this.y + 11 + 16 * i,
                                                    224 + 16 * x,
                                                    176 + 16 * y,
                                                    16,16,512,512);
                                        }
                                    }
                                } else {
                                    if (!( x==0 && y==0)){
                                        context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                                this.y + 11 + 16 * i,
                                                224 + 16 * x,
                                                176 + 16 * y,
                                                16,16,512,512);
                                    }
                                }
                            } else {
                                mouseStage2[i][j] = false;
                                context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                        this.y + 11 + 16 * i,
                                        160 + 16 * x,
                                        176 + 16 * y,
                                        16,16,512,512);
                            }
                        }
                    }
                }
            }
        }
    }
    private void renderSelectedPos2(DrawContext context,int mouseX,int mouseY){
        if (selectedPos2 != null && tempPos2 != null && isMoving){
            int x = selectedPos2.getX();
            int y = selectedPos2.getY();
            switch (stage){
                case 0 -> {
                    context.drawTexture(TEXTURE,this.x + 56 + 16 * tempPos2.getX(),
                            this.y + 46 + 16 * tempPos2.getY(),
                            224 + 16 * x,
                            176 + 16 * y,
                            16,16,512,512);
                    context.drawTexture(TEXTURE,mouseX - 8,
                            mouseY - 8,
                            160 + 16 * x,
                            176 + 16 * y,
                            16,16,512,512);
                }
                case 1 -> {
                    context.drawTexture(TEXTURE,this.x + 24 + 16 * tempPos2.getX(),
                            this.y + 46 + 16 * tempPos2.getY(),
                            224 + 16 * x,
                            176 + 16 * y,
                            16,16,512,512);
                    context.drawTexture(TEXTURE,mouseX - 8,
                            mouseY - 8,
                            160 + 16 * x,
                            176 + 16 * y,
                            16,16,512,512);
                }
                case 2 -> {
                    context.drawTexture(TEXTURE,this.x + 24 + 16 * tempPos2.getX(),
                            this.y + 11 + 16 * tempPos2.getY(),
                            224 + 16 * x,
                            176 + 16 * y,
                            16,16,512,512);
                    context.drawTexture(TEXTURE,mouseX - 8,
                            mouseY - 8,
                            160 + 16 * x,
                            176 + 16 * y,
                            16,16,512,512);
                }
            }
        }
    }
    private void renderBluePrintButton(DrawContext context,int mouseX,int mouseY){
        boolean openButton = mouseX >= x + 106 && mouseY >= y + 144 && mouseX <= x + 116 && mouseY <= y + 154;
        if (isRenderingBluePrint){
            boolean closeButton = false;
            switch (stage){
                case 0 -> closeButton = mouseX >= x + 122 && mouseY >= y + 44 && mouseX <= x + 132 && mouseY <= y + 54;
                case 1 -> closeButton = mouseX >= x + 154 && mouseY >= y + 44 && mouseX <= x + 164 && mouseY <= y + 54;
                case 2 -> closeButton = mouseX >= x + 154 && mouseY >= y + 9 && mouseX <= x + 164 && mouseY <= y + 19;
            }
            if (closeButton){
                switch (stage){
                    case 0 -> context.drawTexture(TEXTURE,x + 122,y + 44,187,0,11,11,512,512);
                    case 1 -> context.drawTexture(TEXTURE,x + 154,y + 44,187,0,11,11,512,512);
                    case 2 -> context.drawTexture(TEXTURE,x + 154,y + 9,187,0,11,11,512,512);
                }
            }
        } else {
            if (openButton){
                context.drawTexture(TEXTURE,x + 106,y + 144,176,144,11,11,512,512);
            }
        }
    }
    private void renderBluePrint(DrawContext context){
        if (isRenderingBluePrint){
            switch (stage){
                case 0 -> {
                    context.drawTexture(TEXTURE,x + 40,y + 41,176,11,96,74,512,512);
                    for (int i = 0; i < 4; i++){
                        for (int j = 0; j < 4; j++){
                            int x = answerStage0[i][j].getX();
                            int y = answerStage0[i][j].getY();
                            context.drawTexture(TEXTURE,this.x + 56 + 16 * j,
                                    this.y + 46 + 16 * i,
                                    288 + 16 * x,
                                    176 + 16 * y,
                                    16,16,512,512);
                        }
                    }
                }
                case 1 -> {
                    context.drawTexture(TEXTURE,x + 8,y + 41,272,11,160,74,512,512);
                    for (int i = 0; i < 4; i++){
                        for (int j = 0; j < 8; j++){
                            int x = answerStage1[i][j].getX();
                            int y = answerStage1[i][j].getY();
                            context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                    this.y + 46 + 16 * i,
                                    288 + 16 * x,
                                    176 + 16 * y,
                                    16,16,512,512);
                        }
                    }
                }
                case 2 -> {
                    context.drawTexture(TEXTURE,x + 8,y + 6,352,85,160,138,512,512);
                    for (int i = 0; i < 8; i++){
                        for (int j = 0; j < 8; j++){
                            int x = answerStage2[i][j].getX();
                            int y = answerStage2[i][j].getY();
                            context.drawTexture(TEXTURE,this.x + 24 + 16 * j,
                                    this.y + 11 + 16 * i,
                                    288 + 16 * x,
                                    176 + 16 * y,
                                    16,16,512,512);
                        }
                    }
                }
            }
        }
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isRenderingBluePrint){
            boolean closeButton = false;
            switch (stage){
                case 0 -> closeButton = mouseX >= x + 122 && mouseY >= y + 44 && mouseX <= x + 132 && mouseY <= y + 54;
                case 1 -> closeButton = mouseX >= x + 154 && mouseY >= y + 44 && mouseX <= x + 164 && mouseY <= y + 54;
                case 2 -> closeButton = mouseX >= x + 154 && mouseY >= y + 9 && mouseX <= x + 164 && mouseY <= y + 19;
            }
            if (closeButton){
                isRenderingBluePrint = false;
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
        } else {
            boolean openButton = mouseX >= x + 106 && mouseY >= y + 144 && mouseX <= x + 116 && mouseY <= y + 154;
            boolean doneButton = mouseX >= x + 142 && mouseY >= y + 144 && mouseX <= x + 152 && mouseY <= y + 154;
            boolean nextChallenge = mouseX >= x + 118 && mouseY >= y + 144 && mouseX <= x + 128 && mouseY <= y + 154;
            boolean resetButton = mouseX >= x + 130 && mouseY >= y + 144 && mouseX <= x + 140 && mouseY <= y + 154;
            boolean moveModeButton = mouseX >= x + 39 && mouseY >= y + 141 && mouseX <= x + 69 && mouseY <= y + 158;
            boolean removingTool = mouseX >= x + 8 && mouseY >= y + 142 && mouseX <= x + 23 && mouseY <= y + 157;
            if (isGameEnd){
                if (doneButton){
                    handleEndingGame();
                    return true;
                }
            } else if (nextChallenge && canNextChange){
                handleNextChallenge();
                return true;
            }
            if (openButton){
                selectedPos2 = null;
                tempPos2 = null;
                isMoving = false;
                isRemoving = false;
                isRenderingBluePrint = true;
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
            if (resetButton){
                handleReset();
                return true;
            }
            if (moveModeButton){
                handleMoveMode();
                return true;
            }
            if (removingTool){
                isRemoving = !isRemoving;
                selectedPos2 = null;
                tempPos2 = null;
                isMoving = false;
                return true;
            }
            if (isRemoving){
                switch (stage){
                    case 0 -> {
                        for (int i = 0;i < 4;i++){
                            for (int j = 0;j < 4;j++){
                                if (mouseStage0[i][j]){
                                    int x = stage0[i][j].getX();
                                    int y = stage0[i][j].getY();
                                    if (!(x==0 && y==0)){
                                        stage0[i][j].setX(0);
                                        stage0[i][j].setY(0);
                                        MinecraftClient.getInstance().getSoundManager()
                                                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    case 1 -> {
                        for (int i = 0;i < 4;i++){
                            for (int j = 0;j < 8;j++){
                                if (mouseStage1[i][j]){
                                    int x = stage1[i][j].getX();
                                    int y = stage1[i][j].getY();
                                    if (!(x==0 && y==0)){
                                        stage1[i][j].setX(0);
                                        stage1[i][j].setY(0);
                                        MinecraftClient.getInstance().getSoundManager()
                                                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    case 2 -> {
                        for (int i = 0;i < 8;i++){
                            for (int j = 0;j < 8;j++){
                                if (mouseStage2[i][j]){
                                    int x = stage2[i][j].getX();
                                    int y = stage2[i][j].getY();
                                    if (!(x==0 && y==0)){
                                        stage2[i][j].setX(0);
                                        stage2[i][j].setY(0);
                                        MinecraftClient.getInstance().getSoundManager()
                                                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (isMoveMode){
                if (isMoving){
                    switch (stage){
                        case 0 -> {
                            for (int i = 0;i < 4;i++){
                                for (int j = 0;j < 4;j++){
                                    if (mouseStage0[i][j]){
                                        int x = stage0[i][j].getX();
                                        int y = stage0[i][j].getY();
                                        if (x==0 && y==0){
                                            stage0[i][j].setX(selectedPos2.getX());
                                            stage0[i][j].setY(selectedPos2.getY());
                                            stage0[tempPos2.getY()][tempPos2.getX()].setX(0);
                                            stage0[tempPos2.getY()][tempPos2.getX()].setY(0);
                                            selectedPos2 = null;
                                            isMoving = false;
                                            tempPos2 = null;
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        } else if (j == tempPos2.getX() && i == tempPos2.getY()) {
                                            selectedPos2 = null;
                                            isMoving = false;
                                            tempPos2 = null;
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        case 1 -> {
                            for (int i = 0;i < 4;i++){
                                for (int j = 0;j < 8;j++){
                                    if (mouseStage1[i][j]){
                                        int x = stage1[i][j].getX();
                                        int y = stage1[i][j].getY();
                                        if (x==0 && y==0){
                                            stage1[i][j].setX(selectedPos2.getX());
                                            stage1[i][j].setY(selectedPos2.getY());
                                            stage1[tempPos2.getY()][tempPos2.getX()].setX(0);
                                            stage1[tempPos2.getY()][tempPos2.getX()].setY(0);
                                            selectedPos2 = null;
                                            isMoving = false;
                                            tempPos2 = null;
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        } else if (j == tempPos2.getX() && i == tempPos2.getY()) {
                                            selectedPos2 = null;
                                            isMoving = false;
                                            tempPos2 = null;
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        case 2 -> {
                            for (int i = 0;i < 8;i++){
                                for (int j = 0;j < 8;j++){
                                    if (mouseStage2[i][j]){
                                        int x = stage2[i][j].getX();
                                        int y = stage2[i][j].getY();
                                        if (x==0 && y==0){
                                            stage2[i][j].setX(selectedPos2.getX());
                                            stage2[i][j].setY(selectedPos2.getY());
                                            stage2[tempPos2.getY()][tempPos2.getX()].setX(0);
                                            stage2[tempPos2.getY()][tempPos2.getX()].setY(0);
                                            selectedPos2 = null;
                                            isMoving = false;
                                            tempPos2 = null;
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        } else if (j == tempPos2.getX() && i == tempPos2.getY()) {
                                            selectedPos2 = null;
                                            isMoving = false;
                                            tempPos2 = null;
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    switch (stage){
                        case 0 -> {
                            for (int i = 0;i < 4;i++){
                                for (int j = 0;j < 4;j++){
                                    if (mouseStage0[i][j]){
                                        if (stage0[i][j].getY() != 0 || stage0[i][j].getX() != 0){
                                            selectedPos2 = stage0[i][j].copy();
                                            tempPos2 = new Pos2(j,i);
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            isMoving = true;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        case 1 -> {
                            for (int i = 0;i < 4;i++){
                                for (int j = 0;j < 8;j++){
                                    if (mouseStage1[i][j]){
                                        if (stage1[i][j].getY() != 0 || stage1[i][j].getX() != 0){
                                            selectedPos2 = stage1[i][j].copy();
                                            tempPos2 = new Pos2(j,i);
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            isMoving = true;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        case 2 -> {
                            for (int i = 0;i < 8;i++){
                                for (int j = 0;j < 8;j++){
                                    if (mouseStage2[i][j]){
                                        if (stage2[i][j].getY() != 0 || stage2[i][j].getX() != 0){
                                            selectedPos2 = stage2[i][j].copy();
                                            tempPos2 = new Pos2(j,i);
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            isMoving = true;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                switch (stage){
                    case 0 ->{
                        for (int i = 0;i < 4;i++){
                            for (int j = 0;j < 4;j++){
                                if (mouseStage0[i][j]){
                                    if (stage0[i][j].getX() > 0){
                                        if (stage0[i][j].getY() < 3){
                                            stage0[i][j].setY(stage0[i][j].getY() + 1);
                                        } else stage0[i][j].setY(0);
                                        MinecraftClient.getInstance().getSoundManager()
                                                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                        return true;
                                    } else {
                                        if (stage0[i][j].getY() == 1){
                                            stage0[i][j].setY(2);
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        } else if (stage0[i][j].getY() == 2) {
                                            stage0[i][j].setY(1);
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    case 1 ->{
                        for (int i = 0;i < 4;i++){
                            for (int j = 0;j < 8;j++){
                                if (mouseStage1[i][j]){
                                    if (stage1[i][j].getX() > 0){
                                        if (stage1[i][j].getY() < 3){
                                            stage1[i][j].setY(stage1[i][j].getY() + 1);
                                        } else stage1[i][j].setY(0);
                                        MinecraftClient.getInstance().getSoundManager()
                                                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                        return true;
                                    } else {
                                        if (stage1[i][j].getY() == 1){
                                            stage1[i][j].setY(2);
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        } else if (stage1[i][j].getY() == 2) {
                                            stage1[i][j].setY(1);
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        return true;
                    }
                    case 2 ->{
                        for (int i = 0;i < 8;i++){
                            for (int j = 0;j < 8;j++){
                                if (mouseStage2[i][j]){
                                    if (stage2[i][j].getX() > 0){
                                        if (stage2[i][j].getY() < 3){
                                            stage2[i][j].setY(stage2[i][j].getY() + 1);
                                        } else stage2[i][j].setY(0);
                                        MinecraftClient.getInstance().getSoundManager()
                                                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                        return true;
                                    } else {
                                        if (stage2[i][j].getY() == 1){
                                            stage2[i][j].setY(2);
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        } else if (stage2[i][j].getY() == 2) {
                                            stage2[i][j].setY(1);
                                            MinecraftClient.getInstance().getSoundManager()
                                                    .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (client != null){
            if (isRenderingBluePrint){
                if (this.client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
                    isRenderingBluePrint = false;
                    MinecraftClient.getInstance().getSoundManager()
                            .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    return true;
                }
            } else {
                if (isGameEnd){
                    if (keyCode == InputUtil.GLFW_KEY_ENTER || keyCode == InputUtil.GLFW_KEY_KP_ENTER){
                        handleEndingGame();
                        return true;
                    }
                } else if (keyCode == InputUtil.GLFW_KEY_ENTER || keyCode == InputUtil.GLFW_KEY_KP_ENTER){
                    if (canNextChange){
                        handleNextChallenge();
                        return true;
                    }
                }
                if (this.client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
                    selectedPos2 = null;
                    tempPos2 = null;
                    isMoving = false;
                    isRemoving = false;
                    isRenderingBluePrint = true;
                    MinecraftClient.getInstance().getSoundManager()
                            .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    return true;
                }
                if (keyCode == InputUtil.GLFW_KEY_R){
                    handleReset();
                    return true;
                }
                if (keyCode == InputUtil.GLFW_KEY_SPACE){
                    handleMoveMode();
                    return true;
                }
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    private void handleReset() {
        reset(true);
        MinecraftClient.getInstance().getSoundManager()
                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
    private void handleEndingGame() {
        byte[] array = new byte[1];
        array[0] = 1;
        NetworkHandler.sendChangeBlockEntityDataPacket(blockEntity.getPos(),array);
        MinecraftClient.getInstance().getSoundManager()
                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        this.close();
    }
    private void handleNextChallenge(){
        currentGoal++;
        reset(false);
        canNextChange = false;
        MinecraftClient.getInstance().getSoundManager()
                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
    private void handleMoveMode(){
        if (isMoveMode){
            selectedPos2 = null;
            tempPos2 = null;
            isMoveMode = false;
            isMoving = false;
        } else {
            isMoveMode = true;
        }
        MinecraftClient.getInstance().getSoundManager()
                .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
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
        byte[] array = new byte[1];
        array[0] = 4;
        NetworkHandler.sendChangeBlockEntityDataPacket(blockEntity.getPos(),array);
        Objects.requireNonNull(client).setScreen(parent);
    }
}