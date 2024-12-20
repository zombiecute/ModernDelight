package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.mojang.serialization.Codec;
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

public class AssemblyRecipe implements Recipe<MultiStackRecipeInput> {
    protected final ItemStack output;
    protected final List<Ingredient> recipeItems;
    protected final int mini_game_type;
    protected final int goal;
    public AssemblyRecipe(List<Ingredient> ingredients, ItemStack output, int mini_game_type, int goal){
        this.output = output;
        this.recipeItems = ingredients;
        this.mini_game_type = mini_game_type;
        this.goal = goal;
    }
    @Override
    public boolean matches(MultiStackRecipeInput input, World world) {
        return recipeItems.get(0).test(input.getStackInSlot(0)) &&
                recipeItems.get(1).test(input.getStackInSlot(1)) &&
                recipeItems.get(2).test(input.getStackInSlot(2)) &&
                recipeItems.get(3).test(input.getStackInSlot(3)) &&
                recipeItems.get(4).test(input.getStackInSlot(4)) &&
                recipeItems.get(5).test(input.getStackInSlot(5));
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

    public int getMiniGameType() {
        return mini_game_type;
    }
    public int getGoal() {
        return goal;
    }
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(this.recipeItems);
        return list;
    }

    @Override
    public ItemStack createIcon() {
        return ModBlocks.ELECTRICIANS_DESK.asItem().getDefaultStack();
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

        public static final MapCodec<AssemblyRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                                .flatXmap(ingredients ->{
                                    Ingredient[] ingredients1 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients1.length == 0){
                                        return DataResult.error(()->"No ingredients");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY,ingredients1));
                                },DataResult::success).forGetter(AssemblyRecipe::getIngredients)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("output")).forGetter(recipe -> recipe.output)
                        ,Codec.INT.fieldOf("mini_game_type").forGetter(recipe -> recipe.mini_game_type)
                        ,Codec.INT.fieldOf("goal").forGetter(recipe -> recipe.goal)
                ).apply(instance, AssemblyRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, AssemblyRecipe> PACKET_CODEC = PacketCodec.ofStatic(AssemblyRecipe.Serializer::write, AssemblyRecipe.Serializer::read);

        private static AssemblyRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(6,Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            int mini_game_type = buf.readShort();
            int goal = buf.readInt();
            return new AssemblyRecipe(inputs,output,mini_game_type,goal);
        }

        private static void write(RegistryByteBuf buf, AssemblyRecipe recipe) {
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf,ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf,recipe.getResult(null));
            buf.writeShort(recipe.mini_game_type);
            buf.writeInt(recipe.goal);
        }

        @Override
        public MapCodec<AssemblyRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, AssemblyRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
