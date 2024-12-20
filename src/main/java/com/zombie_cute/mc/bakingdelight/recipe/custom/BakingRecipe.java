package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.recipeInput.MultiStackRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class BakingRecipe implements Recipe<MultiStackRecipeInput> {
    private final ItemStack output;
    private final List<Ingredient> recipeItems;
    public BakingRecipe(List<Ingredient> ingredients, ItemStack itemStack){
        this.output = itemStack;
        this.recipeItems = ingredients;
    }

    @Override
    public boolean matches(MultiStackRecipeInput inventory, World world) {
        return recipeItems.get(0).test(inventory.getStackInSlot(0)) &&
                recipeItems.get(1).test(inventory.getStackInSlot(1)) &&
                recipeItems.get(2).test(inventory.getStackInSlot(2)) &&
                recipeItems.get(3).test(inventory.getStackInSlot(3));
    }

    @Override
    public ItemStack craft(MultiStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output.copy();
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }

    @Override
    public ItemStack createIcon() {
        return ModBlocks.OVEN.asItem().getDefaultStack();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<BakingRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID  = "oven_baking";
    }
    public static class Serializer implements RecipeSerializer<BakingRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "oven_baking";
        public static final MapCodec<BakingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                                .flatXmap(ingredients ->{
                                    Ingredient[] ingredients1 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients1.length == 0){
                                        return DataResult.error(()->"No ingredients");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY,ingredients1));
                                },DataResult::success).forGetter(BakingRecipe::getIngredients)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("output")).forGetter(recipe -> recipe.output)
                ).apply(instance, BakingRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, BakingRecipe> PACKET_CODEC = PacketCodec.ofStatic(BakingRecipe.Serializer::write, BakingRecipe.Serializer::read);

        private static BakingRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            return new BakingRecipe(inputs,output);
        }

        private static void write(RegistryByteBuf buf, BakingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf,ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf,recipe.getResult(null));
        }

        @Override
        public MapCodec<BakingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, BakingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
