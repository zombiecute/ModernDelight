package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
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

import java.util.ArrayList;
import java.util.List;

public class GrindingRecipe implements Recipe<SingleStackRecipeInput> {
    private final ItemStack output;
    private final ItemStack chancedOutput;
    private final float chance;
    private final DefaultedList<Ingredient> recipeItems;
    public GrindingRecipe(DefaultedList<Ingredient> ingredients, ItemStack output, ItemStack chancedOutput, float chance){
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
    public boolean matches(SingleStackRecipeInput inventory, World world) {
        return recipeItems.get(0).test(inventory.getStackInSlot(0));
    }

    @Override
    public ItemStack createIcon() {
        return ModItems.STONE_MORTAR.getDefaultStack();
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
        public static final MapCodec<GrindingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                                .flatXmap(ingredients ->{
                                    Ingredient[] ingredients1 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients1.length == 0){
                                        return DataResult.error(()->"No ingredients");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY,ingredients1));
                                },DataResult::success).forGetter(GrindingRecipe::getIngredients)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("output")).forGetter(recipe -> recipe.output)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("extra")).forGetter(recipe -> recipe.chancedOutput)
                        , Codec.FLOAT.fieldOf("chance").forGetter(recipe -> recipe.chance)
                ).apply(instance, GrindingRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, GrindingRecipe> PACKET_CODEC = PacketCodec.ofStatic(GrindingRecipe.Serializer::write, GrindingRecipe.Serializer::read);

        private static GrindingRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            ItemStack chancedOutput = ItemStack.PACKET_CODEC.decode(buf);
            float chance = buf.readFloat();
            return new GrindingRecipe(inputs,output,chancedOutput,chance);
        }

        private static void write(RegistryByteBuf buf, GrindingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf,ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf,recipe.getResult(null));
            ItemStack.PACKET_CODEC.encode(buf,recipe.chancedOutput);
            buf.writeFloat(recipe.getChance());
        }
        @Override
        public MapCodec<GrindingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, GrindingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
