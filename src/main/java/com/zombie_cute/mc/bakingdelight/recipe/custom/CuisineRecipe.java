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

public class CuisineRecipe implements Recipe<MultiStackRecipeInput> {
    private final ItemStack output;
    private final List<Ingredient> recipeItem;
    public CuisineRecipe(List<Ingredient> recipeItem,ItemStack output){
        this.output = output;
        this.recipeItem = recipeItem;
    }

    @Override
    public boolean matches(MultiStackRecipeInput inventory, World world) {
        return recipeItem.get(0).test(inventory.getStackInSlot(0)) &&
                recipeItem.get(1).test(inventory.getStackInSlot(1));
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
        DefaultedList<Ingredient> list = DefaultedList.ofSize(recipeItem.size());
        list.addAll(recipeItem);
        return list;
    }

    @Override
    public ItemStack createIcon() {
        return ModBlocks.CUISINE_TABLE.asItem().getDefaultStack();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<CuisineRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID  = "cuisine";
    }
    public static class Serializer implements RecipeSerializer<CuisineRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "cuisine";

        public static final MapCodec<CuisineRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                                .flatXmap(ingredients ->{
                                    Ingredient[] ingredients1 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients1.length == 0){
                                        return DataResult.error(()->"No ingredients");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY,ingredients1));
                                },DataResult::success).forGetter(CuisineRecipe::getIngredients)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("output")).forGetter(recipe -> recipe.output)
                ).apply(instance, CuisineRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, CuisineRecipe> PACKET_CODEC = PacketCodec.ofStatic(CuisineRecipe.Serializer::write, CuisineRecipe.Serializer::read);

        private static CuisineRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            return new CuisineRecipe(inputs,output);
        }

        private static void write(RegistryByteBuf buf, CuisineRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf,ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf,recipe.getResult(null));
        }

        @Override
        public MapCodec<CuisineRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, CuisineRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
