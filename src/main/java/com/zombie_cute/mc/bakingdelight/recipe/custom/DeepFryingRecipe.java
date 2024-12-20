package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class DeepFryingRecipe implements Recipe<SingleStackRecipeInput> {
    private final ItemStack output;
    private final List<Ingredient> recipeItem;
    public DeepFryingRecipe(List<Ingredient> ingredient, ItemStack itemStack){
        this.output = itemStack;
        this.recipeItem = ingredient;
    }

    @Override
    public ItemStack createIcon() {
        return ModBlocks.DEEP_FRYER.asItem().getDefaultStack();
    }

    @Override
    public boolean matches(SingleStackRecipeInput inventory, World world) {
        return recipeItem.getFirst().test(inventory.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output;
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
        DefaultedList<Ingredient> list = DefaultedList.ofSize(recipeItem.size());
        list.addAll(recipeItem);
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
    public static class Type implements RecipeType<DeepFryingRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID  = "deep_frying";
    }
    public static class Serializer implements RecipeSerializer<DeepFryingRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "deep_frying";

        public static final MapCodec<DeepFryingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                                .flatXmap(ingredients ->{
                                    Ingredient[] ingredients1 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients1.length == 0){
                                        return DataResult.error(()->"No ingredients");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY,ingredients1));
                                },DataResult::success).forGetter(DeepFryingRecipe::getIngredients)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("output")).forGetter(recipe -> recipe.output)
                ).apply(instance, DeepFryingRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, DeepFryingRecipe> PACKET_CODEC = PacketCodec.ofStatic(DeepFryingRecipe.Serializer::write, DeepFryingRecipe.Serializer::read);

        private static DeepFryingRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            return new DeepFryingRecipe(inputs,output);
        }

        private static void write(RegistryByteBuf buf, DeepFryingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf,ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf,recipe.getResult(null));
        }

        @Override
        public MapCodec<DeepFryingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, DeepFryingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
