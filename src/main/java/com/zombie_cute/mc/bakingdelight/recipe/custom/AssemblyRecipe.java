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

public class AssemblyRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;
    private final int mini_game_type;
    private final int goal;
    public AssemblyRecipe(Identifier id, DefaultedList<Ingredient> ingredients, ItemStack output, int mini_game_type, int goal){
        this.id = id;
        this.output = output;
        this.recipeItems = ingredients;
        this.mini_game_type = mini_game_type;
        this.goal = goal;
    }
    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return recipeItems.get(0).test(inventory.getStack(0)) &&
                recipeItems.get(1).test(inventory.getStack(1)) &&
                recipeItems.get(2).test(inventory.getStack(2)) &&
                recipeItems.get(3).test(inventory.getStack(3)) &&
                recipeItems.get(4).test(inventory.getStack(4)) &&
                recipeItems.get(5).test(inventory.getStack(5));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }
    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    public int getMiniGameType() {
        return mini_game_type;
    }
    public int getGoal() {
        return goal;
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
    public ItemStack createIcon() {
        return ModBlocks.ELECTRICIANS_DESK.asItem().getDefaultStack();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AssemblyRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return AssemblyRecipe.Type.INSTANCE;
    }
    public static class Type implements RecipeType<AssemblyRecipe>{
        private Type() {}
        public static final AssemblyRecipe.Type INSTANCE = new AssemblyRecipe.Type();
        public static final String ID  = "assembly";
    }
    public static class Serializer implements RecipeSerializer<AssemblyRecipe> {

        public static final AssemblyRecipe.Serializer INSTANCE = new AssemblyRecipe.Serializer();
        public static final String ID = "assembly";

        @Override
        public AssemblyRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json,"output"));
            JsonArray ingredients = JsonHelper.getArray(json,"ingredients");
            int type = JsonHelper.getInt(json,"mini_game_type",0);
            int goal = JsonHelper.getInt(json,"goal",0);
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(6,Ingredient.EMPTY);

            for(int i=0;i<inputs.size();i++){
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }

            return new AssemblyRecipe(id, inputs, output,type, goal);
        }

        @Override
        public AssemblyRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            int[] type = buf.readIntArray();
            return new AssemblyRecipe(id, inputs, output,type[0], type[1]);
        }

        @Override
        public void write(PacketByteBuf buf, AssemblyRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()){
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.output);
            int[] type = new int[2];
            type[0] = recipe.mini_game_type;
            type[1] = recipe.goal;
            buf.writeIntArray(type);
        }
    }
}
