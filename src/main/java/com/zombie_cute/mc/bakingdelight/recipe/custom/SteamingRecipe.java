package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SteamingRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;
    private final int maxProgress;
    public SteamingRecipe(Identifier id, DefaultedList<Ingredient> ingredients, ItemStack itemStack, int maxProgress){
        this.id = id;
        this.output = itemStack;
        this.recipeItems = ingredients;
        this.maxProgress = maxProgress;
    }

    @Override
    public ItemStack createIcon() {
        return ModBlocks.BAMBOO_GRATE.asItem().getDefaultStack();
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient){
            return false;
        }
        return recipeItems.get(0).test(inventory.getStack(0));
    }
    public int getMaxProgress(){
        return this.maxProgress;
    }
    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<SteamingRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID  = "steaming";
    }
    public static class Serializer implements RecipeSerializer<SteamingRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "steaming";

        @Override
        public SteamingRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));

            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1, Ingredient.EMPTY);
            int maxProgress = JsonHelper.getInt(json,"maxProgress",10);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new SteamingRecipe(id, inputs, output, maxProgress);
        }

        @Override
        public SteamingRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
            ItemStack output = buf.readItemStack();
            int[] maxProgress = buf.readIntArray();
            return new SteamingRecipe(id, inputs, output,maxProgress[0]);
        }

        @Override
        public void write(PacketByteBuf buf, SteamingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.output);
            int[] maxProgress = new int[1];
            maxProgress[0] = recipe.getMaxProgress();
            buf.writeIntArray(maxProgress);
        }
    }
}
