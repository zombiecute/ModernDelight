package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.recipe.custom.CuisineRecipe;
import com.zombie_cute.mc.bakingdelight.recipe.recipeInput.MultiStackRecipeInput;
import com.zombie_cute.mc.bakingdelight.util.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class CuisineTableScreen extends HandledScreen<CuisineTableScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID,
            "textures/gui/cuisine_table_gui.png");
    private int selectedRecipe;
    private List<RecipeEntry<CuisineRecipe>> availableRecipes;
    private int index;
    private int totalPage;
    private boolean prePageBtn;
    private boolean nextPageBtn;
    public CuisineTableScreen(CuisineTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.selectedRecipe = -1;
        this.index = 0;
        this.totalPage = 0;
        this.prePageBtn = false;
        this.nextPageBtn = false;
        this.availableRecipes = Lists.newArrayList();
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
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        if (this.hasRecipe()){
            renderRecipeSlot(context,x,y);
            renderMouseSelectSlot(context,x,y,mouseX,mouseY);
            renderSelectedRecipeSlot(context,x,y);
            renderRecipeItem(context,x,y);
            popRes();
        } else this.resetAll();
        renderFlipButton(context,x,y);
        renderMouseHoverFlipButton(context,x,y,mouseX,mouseY);
        if (mouseX >= x + 161 && mouseX <= x + 171 && mouseY >= y + 5 && mouseY <= y + 15){
            context.drawTexture(TEXTURE,x+161,y+5,194,13,11,11);
        }
    }
    public boolean hasRecipe(){
        List<ItemStack> tempINV = new ArrayList<>();
        for (int i=0;i<2;i++){
            tempINV.add(i,handler.blockEntity.getStack(i));
        }
        Optional<RecipeEntry<CuisineRecipe>> match = Objects.requireNonNull(handler.blockEntity.getWorld()).getRecipeManager()
                .getFirstMatch(CuisineRecipe.Type.INSTANCE, new MultiStackRecipeInput(tempINV,tempINV.size()), handler.blockEntity.getWorld());
        if (match.isPresent()){
            this.availableRecipes = Objects.requireNonNull(handler.blockEntity.getWorld())
                    .getRecipeManager().getAllMatches(
                            CuisineRecipe.Type.INSTANCE, new MultiStackRecipeInput(tempINV,tempINV.size()), handler.blockEntity.getWorld());
            return true;
        } else return false;
    }
    private void renderMouseSelectSlot(DrawContext context, int x, int y, int mouseX,int mouseY) {
        int count = this.getAvailableRecipeCounts() - 8 * index;
        for (int i = 0; i < Math.min(count, 8); i++) {
            int offsetX = (i % 4) * 18;
            int offsetY = (i / 4) * 18;
            int slotX = x + 48 + offsetX;
            int slotY = y + 16 + offsetY;
            boolean slot = mouseX >= slotX && mouseX <= slotX + 17 && mouseY >= slotY && mouseY <= slotY + 17;
            if (slot) {
                context.drawTexture(TEXTURE, slotX, slotY, 36, 166, 18, 18);
            }
        }
    }
    private void renderRecipeSlot(DrawContext context, int x, int y) {
        int count = this.getAvailableRecipeCounts() - 8 * index;
        for (int i = 0; i < Math.min(count, 8); i++) {
            int offsetX = (i % 4) * 18;
            int offsetY = (i / 4) * 18;
            context.drawTexture(TEXTURE, x + 48 + offsetX, y + 16 + offsetY, 18, 166, 18, 18);
        }
    }
    public int getSelectedRecipe(){
        return this.selectedRecipe;
    }
    public List<RecipeEntry<CuisineRecipe>> getAvailableRecipes(){
        return availableRecipes;
    }
    public int getAvailableRecipeCounts(){
        return availableRecipes.size();
    }
    public void resetAll() {
        this.availableRecipes.clear();
        this.selectedRecipe = -1;
        this.nextPageBtn = false;
        this.prePageBtn = false;
        this.index = 0;
        this.totalPage = 0;
        handler.populateResult(ItemStack.EMPTY);
    }
    private void renderRecipeItem(DrawContext context,int x,int y){
        int count = this.getAvailableRecipeCounts() - 8 * index;
        for (int i = 0; i < Math.min(count, 8); i++) {
            int offsetX = (i % 4) * 18;
            int offsetY = (i / 4) * 18;
            if (isInBounds(i + index * 8)){
                context.drawItem(this.getAvailableRecipes().get(i + index * 8).value().getResult(Objects.requireNonNull(Objects.requireNonNull(this.client).world).getRegistryManager()).copy(),
                        x + 49 + offsetX, y + 17 + offsetY);
            } else {
                resetAll();
            }
        }
    }
    private void renderSelectedRecipeSlot(DrawContext context, int x, int y) {
        switch (this.getSelectedRecipe()){
            case 0 -> context.drawTexture(TEXTURE, x + 48, y + 16, 0, 166, 18, 18);
            case 1 -> context.drawTexture(TEXTURE, x + 66, y + 16, 0, 166, 18, 18);
            case 2 -> context.drawTexture(TEXTURE, x + 84, y + 16, 0, 166, 18, 18);
            case 3 -> context.drawTexture(TEXTURE, x + 102, y + 16, 0, 166, 18, 18);
            case 4 -> context.drawTexture(TEXTURE, x + 48, y + 34, 0, 166, 18, 18);
            case 5 -> context.drawTexture(TEXTURE, x + 66, y + 34, 0, 166, 18, 18);
            case 6 -> context.drawTexture(TEXTURE, x + 84, y + 34, 0, 166, 18, 18);
            case 7 -> context.drawTexture(TEXTURE, x + 102, y + 34, 0, 166, 18, 18);
        }
    }
    private boolean isInBounds(int id){
        return id < this.getAvailableRecipes().size() && id >= 0;
    }
    private void popRes(){
        if (this.getSelectedRecipe() >= 0){
            if (isInBounds(this.getSelectedRecipe() + 8 * index)){
                handler.populateResult(this.getAvailableRecipes().get(this.getSelectedRecipe() + 8 * index).value().getResult(null).copy());
            } else {
                resetAll();
            }
        } else {
            handler.populateResult(ItemStack.EMPTY);
        }
    }
    private void renderMouseHoverFlipButton(DrawContext context, int x,int y, int mouseX, int mouseY){
        if (prePageBtn && mouseX >= x + 122 && mouseY >= y + 15 && mouseX <= x + 131 && mouseY <= y + 25){
            context.drawTexture(TEXTURE, x + 122, y + 15, 54 ,177, 10 ,11);
        }
        if (nextPageBtn && mouseX >= x + 133 && mouseY >= y + 15 && mouseX <= x + 142 && mouseY <= y + 25){
            context.drawTexture(TEXTURE, x + 133, y + 15, 65 ,177, 10 ,11);
        }
    }
    private void renderFlipButton(DrawContext context, int x, int y) {
        if (this.getAvailableRecipeCounts()>8){
            this.totalPage = this.getAvailableRecipeCounts() / 8;
            if (index == 0) {
                context.drawTexture(TEXTURE,x + 122,y + 15,96,166,21,11);
                this.prePageBtn = false;
                this.nextPageBtn = true;
            } else if (index < totalPage) {
                context.drawTexture(TEXTURE,x + 122,y + 15,54,166,21,11);
                this.prePageBtn = true;
                this.nextPageBtn = true;
            } else if (index == totalPage){
                context.drawTexture(TEXTURE,x + 122,y + 15,75,166,21,11);
                this.prePageBtn = true;
                this.nextPageBtn = false;
            }
        } else {
            this.prePageBtn = false;
            this.nextPageBtn = false;
            this.index = 0;
            this.totalPage = 0;
        }
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.hasRecipe()){
            int count = this.getAvailableRecipeCounts() - 8 * this.index;
            for (int i = 0; i < Math.min(count, 8); i++) {
                int offsetX = (i % 4) * 18;
                int offsetY = (i / 4) * 18;
                int slotX = x + 48 + offsetX;
                int slotY = y + 16 + offsetY;
                boolean slot = mouseX >= slotX && mouseX <= slotX + 17 && mouseY >= slotY && mouseY <= slotY + 17;
                if (slot && button == 0) {
                    MinecraftClient.getInstance().getSoundManager()
                            .play(PositionedSoundInstance.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.selectedRecipe = i;
                    if (isInBounds(i + 8 * index)){
                        handler.populateResult(this.getAvailableRecipes().get(i + 8 * index).value().getResult(null).copy());
                    } else resetAll();
                    return true;
                }
            }
            if (mouseX >= x + 122 && mouseY >= y + 15 && mouseX <= x + 131 && mouseY <= y + 25 && button == 0){
                if (this.prePageBtn){
                    this.index--;
                    this.selectedRecipe = -1;
                    MinecraftClient.getInstance().getSoundManager()
                            .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    return true;
                }
            }
            if (mouseX >= x + 133 && mouseY >= y + 15 && mouseX <= x + 142 && mouseY <= y + 25 && button == 0){
                if (this.nextPageBtn){
                    this.index++;
                    this.selectedRecipe = -1;
                    MinecraftClient.getInstance().getSoundManager()
                            .play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
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

    @Override
    public void close() {
        byte[] array = new byte[1];
        array[0] = 1;
        NetworkHandler.sendChangeBlockEntityDataPacket(handler.blockEntity.getPos(), array);
        super.close();
    }
}
