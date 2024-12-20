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

public class MixWithWaterRecipe implements Recipe<SingleStackRecipeInput> {
    private final ItemStack output;
    private final List<Ingredient> recipeItem;
    public MixWithWaterRecipe(List<Ingredient> ingredients, ItemStack itemStack){
        this.output = itemStack;
        this.recipeItem = ingredients;
    }

    @Override
    public ItemStack createIcon() {
        return ModBlocks.GLASS_BOWL.asItem().getDefaultStack();
    }

    @Override
    public boolean matches(SingleStackRecipeInput inventory, World world) {
        return recipeItem.getFirst().test(inventory.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
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
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItem.size());
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
    public static class Type implements RecipeType<MixWithWaterRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID  = "mix_with_water";
    }
    public static class Serializer implements RecipeSerializer<MixWithWaterRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "mix_with_water";

        public static final MapCodec<MixWithWaterRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("recipeItem")
                                .flatXmap(ingredients ->{
                                    Ingredient[] ingredients1 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients1.length == 0){
                                        return DataResult.error(()->"No ingredients");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY,ingredients1));
                                },DataResult::success).forGetter(MixWithWaterRecipe::getIngredients)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("output")).forGetter(recipe -> recipe.output)
                ).apply(instance, MixWithWaterRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, MixWithWaterRecipe> PACKET_CODEC = PacketCodec.ofStatic(MixWithWaterRecipe.Serializer::write, MixWithWaterRecipe.Serializer::read);

        private static MixWithWaterRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            return new MixWithWaterRecipe(inputs,output);
        }

        private static void write(RegistryByteBuf buf, MixWithWaterRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf,ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf,recipe.getResult(null));
        }

        @Override
        public MapCodec<MixWithWaterRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, MixWithWaterRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
