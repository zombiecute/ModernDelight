package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GrindingRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final ItemStack chancedOutput;
    private final float chance;
    private final DefaultedList<Ingredient> recipeItems;
    public GrindingRecipe(Identifier id, DefaultedList<Ingredient> ingredients, ItemStack output, ItemStack chancedOutput, float chance){
        this.id = id;
        this.output = output;
        this.recipeItems = ingredients;
        this.chancedOutput = chancedOutput;
        this.chance = chance;
    }

    public float getChance() {
        return chance;
    }

    public ItemStack getChancedOutput() {
        return chancedOutput.copy();
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return recipeItems.get(0).test(inventory.getStack(0));
    }

    @Override
    public ItemStack createIcon() {
        return ModItems.STONE_MORTAR.getDefaultStack();
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
    public List<ItemStack> getOutputs(){
        List<ItemStack> list = new ArrayList<>(2);
        list.add(output.copy());
        list.add(chancedOutput.copy());
        return list;
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
    public static class Type implements RecipeType<GrindingRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID  = "grinding";
    }
    public static class Serializer implements RecipeSerializer<GrindingRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "grinding";

        @Override
        public GrindingRecipe read(Identifier id, JsonObject json) {
            ItemStack output = outputFromJson(JsonHelper.getObject(json,"output"));
            ItemStack chancedOutput = outputFromJson(JsonHelper.getObject(json,"extra"));
            float chance = JsonHelper.getFloat(json,"chance");
            JsonArray ingredients = JsonHelper.getArray(json,"ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1,Ingredient.EMPTY);

            for(int i=0;i<inputs.size();i++){
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }

            return new GrindingRecipe(id, inputs, output,chancedOutput,chance);
        }
        public static ItemStack outputFromJson(JsonObject json) {
            Item item = getItem(json);
            if (json.has("data")) {
                throw new JsonParseException("Disallowed data tag found");
            } else {
                int i = JsonHelper.getInt(json, "count", 1);
                if (i < 1) {
                    throw new JsonSyntaxException("Invalid output count: " + i);
                } else {
                    if (item == Items.AIR){
                        return ItemStack.EMPTY;
                    } else return new ItemStack(item, i);
                }
            }
        }

        public static Item getItem(JsonObject json) {
            String string = JsonHelper.getString(json, "item");
            return Registries.ITEM.getOrEmpty(Identifier.tryParse(string)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + string + "'"));
        }

        @Override
        public GrindingRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            ItemStack chancedOutput = buf.readItemStack();
            float chance = buf.readFloat();
            return new GrindingRecipe(id, inputs, output, chancedOutput, chance);
        }

        @Override
        public void write(PacketByteBuf buf, GrindingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()){
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.output);
            buf.writeItemStack(recipe.chancedOutput);
            buf.writeFloat(recipe.chance);
        }
    }
}
