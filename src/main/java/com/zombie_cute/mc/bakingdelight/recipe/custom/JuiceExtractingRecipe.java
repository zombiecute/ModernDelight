package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class JuiceExtractingRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    final int progress;
    final ItemStack output;
    final DefaultedList<Ingredient> input;
    final Item container;
    public JuiceExtractingRecipe(Identifier id, DefaultedList<Ingredient> ingredients, ItemStack itemStack, int progress, Item container){
        this.id = id;
        this.output = itemStack;
        this.input = ingredients;
        this.progress = progress;
        this.container = container;
    }
    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
        for(int j = 0; j < inventory.size(); ++j) {
            ItemStack itemStack = inventory.getStack(j);
            if (!itemStack.isEmpty()) {
                ++i;
                recipeMatcher.addInput(itemStack, 1);
            }
        }
        return i == this.input.size() && recipeMatcher.match(this, null);
    }
    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public ItemStack createIcon() {
        return ModBlocks.JUICE_EXTRACTOR.asItem().getDefaultStack();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }
    public int getProgress(){
        return progress;
    }

    public Item getContainer() {
        return container;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.input.size());
        list.addAll(input);
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
    public static class Type implements RecipeType<JuiceExtractingRecipe>{
        private Type() {}
        public static final JuiceExtractingRecipe.Type INSTANCE = new JuiceExtractingRecipe.Type();
        public static final String ID  = "juice_extracting";
    }
    public static class Serializer implements RecipeSerializer<JuiceExtractingRecipe> {

        public static final JuiceExtractingRecipe.Serializer INSTANCE = new JuiceExtractingRecipe.Serializer();
        public static final String ID = "juice_extracting";

        @Override
        public JuiceExtractingRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json,"output"));
            JsonArray ingredients = JsonHelper.getArray(json,"ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(4,Ingredient.EMPTY);
            int progress = JsonHelper.getInt(json,"progress",200);
            for(int i=0;i<inputs.size();i++){
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }
            String tempContainer = JsonHelper.getString(json,"container");
            Item container = getItemFromString(tempContainer);
            return new JuiceExtractingRecipe(id, inputs, output, progress, container);
        }

        public static Item getItemFromString(String string) {
            Item item = Items.GLASS_BOTTLE;
            try {
                item = Registries.ITEM.get(new Identifier(string));
            } catch (Exception ignored){
                ModernDelightMain.LOGGER.error("Unknown item '{}'", string);
            }
            return item;
        }

        @Override
        public JuiceExtractingRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
            ItemStack output = buf.readItemStack();
            int progress = (int)buf.readLong();
            Item container = getItemFromString(buf.readString());
            return new JuiceExtractingRecipe(id, inputs, output, progress, container);
        }

        @Override
        public void write(PacketByteBuf buf, JuiceExtractingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()){
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.output);
            buf.writeLong(recipe.progress);
            buf.writeString(Registries.ITEM.getId(recipe.container).toString());
        }
    }
}
