package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.mojang.serialization.Codec;
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

public class SteamingRecipe implements Recipe<SingleStackRecipeInput> {
    private final ItemStack output;
    private final List<Ingredient> ingredients;
    private final int maxProgress;
    public SteamingRecipe(List<Ingredient> ingredients, ItemStack itemStack, int maxProgress){
        this.output = itemStack;
        this.ingredients = ingredients;
        this.maxProgress = maxProgress;
    }

    @Override
    public ItemStack createIcon() {
        return ModBlocks.BAMBOO_GRATE.asItem().getDefaultStack();
    }

    @Override
    public boolean matches(SingleStackRecipeInput inventory, World world) {
        return ingredients.getFirst().test(inventory.getStackInSlot(0));
    }
    public int getMaxProgress(){
        return this.maxProgress;
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
        DefaultedList<Ingredient> list = DefaultedList.ofSize(ingredients.size());
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
    public static class Type implements RecipeType<SteamingRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID  = "steaming";
    }
    public static class Serializer implements RecipeSerializer<SteamingRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "steaming";

        public static final MapCodec<SteamingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                                .flatXmap(ingredients ->{
                                    Ingredient[] ingredients1 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients1.length == 0){
                                        return DataResult.error(()->"No ingredients");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY,ingredients1));
                                },DataResult::success).forGetter(SteamingRecipe::getIngredients)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("output")).forGetter(recipe -> recipe.output)
                        ,Codec.INT.fieldOf("maxProgress").forGetter(recipe -> recipe.maxProgress)
                ).apply(instance, SteamingRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, SteamingRecipe> PACKET_CODEC = PacketCodec.ofStatic(SteamingRecipe.Serializer::write, SteamingRecipe.Serializer::read);

        private static SteamingRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1,Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            int maxProgress = buf.readInt();
            return new SteamingRecipe(inputs,output,maxProgress);
        }

        private static void write(RegistryByteBuf buf, SteamingRecipe recipe) {
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf,ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf,recipe.getResult(null));
            buf.writeInt(recipe.maxProgress);
        }

        @Override
        public MapCodec<SteamingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, SteamingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
