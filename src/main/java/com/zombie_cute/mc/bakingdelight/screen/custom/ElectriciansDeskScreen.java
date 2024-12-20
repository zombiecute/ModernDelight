package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.AssemblyRecipe;
import com.zombie_cute.mc.bakingdelight.recipe.recipeInput.MultiStackRecipeInput;
import com.zombie_cute.mc.bakingdelight.util.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ElectriciansDeskScreen extends HandledScreen<ElectriciansDeskScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/electricians_desk_gui.png");
    public ElectriciansDeskScreen(ElectriciansDeskScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    private ItemStack outputItem;
    private int miniGmeType = 0;
    private int goal = 0;
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
        renderButton(context,x,y,mouseX,mouseY);
        if (this.hasRecipe()){
            context.drawItem(this.outputItem,x + 114,y + 26);
        }
    }
    public boolean hasRecipe(){
        List<ItemStack> tempINV = new ArrayList<>(6);
        for (int i=0;i<6;i++){
            tempINV.add(handler.blockEntity.getStack(i));
        }
        Optional<RecipeEntry<AssemblyRecipe>> match = Objects.requireNonNull(handler.blockEntity.getWorld())
                .getRecipeManager().getFirstMatch(AssemblyRecipe.Type.INSTANCE, new MultiStackRecipeInput(tempINV,tempINV.size()),handler.blockEntity.getWorld());
        if (match.isPresent()){
            this.outputItem = new ItemStack(match.get().value().getResult(null).getItem(),
                    match.get().value().getResult(null).getCount());
            this.miniGmeType = match.get().value().getMiniGameType();
            this.goal = match.get().value().getGoal();
            return true;
        } else {
            this.outputItem = ItemStack.EMPTY;
            this.miniGmeType = 0;
            this.goal = 0;
            return false;
        }
    }
    private void renderButton(DrawContext context, int x, int y, int mouseX, int mouseY) {
        boolean b = mouseX >= x + 112 && mouseY >= y + 24 && mouseX <= x + 131 && mouseY <= y + 43;
        if (handler.isOccupied()){
            context.drawTexture(TEXTURE, x + 112, y + 24, 176, 40, 20,20);
        } else {
            if (handler.canCraft()){
                context.drawTexture(TEXTURE, x + 112, y + 24, 176, 0, 20 ,20);
                if (b){
                    context.drawTexture(TEXTURE,x + 112,y + 24, 196, 0, 20, 20);
                }
            } else {
                if (this.hasPaperAndInk() && this.hasRecipe() && handler.blockEntity.getStack(8).isEmpty()){
                    context.drawTexture(TEXTURE,x + 112,y + 24, 176, 20, 20, 20);
                    if (b){
                        context.drawTexture(TEXTURE,x + 112,y + 24, 196, 20, 20, 20);
                    }
                }
            }
        }
    }
    private boolean hasPaperAndInk(){
        return handler.blockEntity.getStack(6).getItem().equals(Items.PAPER) &&
                (handler.blockEntity.getStack(7).getItem().equals(Items.INK_SAC) ||
                        handler.blockEntity.getStack(7).getItem().equals(Items.GLOW_INK_SAC) ||
                        handler.blockEntity.getStack(7).getItem().equals(Items.BLACK_DYE));
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        boolean b = mouseX >= x + 112 && mouseY >= y + 24 && mouseX <= x + 131 && mouseY <= y + 43;
        if (b){
            if (handler.canCraft()){
                byte[] array = new byte[1];
                array[0] = 2;
                if (this.outputItem == ItemStack.EMPTY){
                    NetworkHandler.sendUpdateInventoryPacket(handler.blockEntity.getPos(),Items.AIR.toString());
                } else {
                    NetworkHandler.sendUpdateInventoryPacket(handler.blockEntity.getPos(),this.outputItem.getItem().toString(),this.outputItem.getCount());
                }
                NetworkHandler.sendChangeBlockEntityDataPacket(handler.blockEntity.getPos(),array);
                MinecraftClient.getInstance().getSoundManager()
                        .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            } else {
                if (this.hasRecipe() && this.hasPaperAndInk() && handler.blockEntity.getStack(8).isEmpty() && !handler.isOccupied()){
                    MinecraftClient.getInstance().getSoundManager()
                            .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    Screen currentScreen = MinecraftClient.getInstance().currentScreen;
                    byte [] array = new byte[1];
                    array[0] = 3;
                    NetworkHandler.sendChangeBlockEntityDataPacket(handler.blockEntity.getPos(),array);
                    switch (this.miniGmeType){
                        case 1 -> MinecraftClient.getInstance().setScreen(
                                new MiniGame1Screen(ModBlocks.ELECTRICIANS_DESK.getName(),
                                currentScreen,handler.blockEntity,goal));
                        case 2 -> MinecraftClient.getInstance().setScreen(
                                new MiniGame2Screen(ModBlocks.ELECTRICIANS_DESK.getName(),
                                        currentScreen,handler.blockEntity,goal));
                        case 3 -> MinecraftClient.getInstance().setScreen(
                                new MiniGame3Screen(ModBlocks.ELECTRICIANS_DESK.getName(),
                                        currentScreen,handler.blockEntity,goal));
                    }
                    return true;
                }
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
