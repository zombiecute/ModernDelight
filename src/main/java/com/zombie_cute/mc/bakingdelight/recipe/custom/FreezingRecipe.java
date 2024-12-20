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

public class FreezingRecipe implements Recipe<MultiStackRecipeInput> {
    private final ItemStack output;
    private final List<Ingredient> ingredients;
    public FreezingRecipe(List<Ingredient> ingredients, ItemStack itemStack){
        this.output = itemStack;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(MultiStackRecipeInput inventory, World world) {
        return ingredients.get(0).test(inventory.getStackInSlot(0))&&
                ingredients.get(1).test(inventory.getStackInSlot(1))&&
                ingredients.get(2).test(inventory.getStackInSlot(2));
    }

    @Override
    public ItemStack craft(MultiStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output;
    }

    @Override
    public ItemStack createIcon() {
        return ModBlocks.FREEZER.asItem().getDefaultStack();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.ingredients.size());
        list.addAll(ingredients);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<FreezingRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID  = "freezing";
    }
    public static class Serializer implements RecipeSerializer<FreezingRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "freezing";

        public static final MapCodec<FreezingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                                .flatXmap(ingredients ->{
                                    Ingredient[] ingredients1 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients1.length == 0){
                                        return DataResult.error(()->"No ingredients");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY,ingredients1));
                                },DataResult::success).forGetter(FreezingRecipe::getIngredients)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("output")).forGetter(recipe -> recipe.output)
                ).apply(instance, FreezingRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, FreezingRecipe> PACKET_CODEC = PacketCodec.ofStatic(FreezingRecipe.Serializer::write, FreezingRecipe.Serializer::read);

        private static FreezingRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            return new FreezingRecipe(inputs,output);
        }

        private static void write(RegistryByteBuf buf, FreezingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf,ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf,recipe.getResult(null));
        }

        @Override
        public MapCodec<FreezingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FreezingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
