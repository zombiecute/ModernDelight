package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.input.MultiStackRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class JuiceExtractingRecipe implements Recipe<MultiStackRecipeInput> {
    final int progress;
    final ItemStack output;
    final List<Ingredient> input;
    final ItemStack container;
    public JuiceExtractingRecipe(List<Ingredient> ingredients, ItemStack itemStack, int progress, ItemStack container){
        this.output = itemStack;
        this.input = ingredients;
        this.progress = progress;
        this.container = container;
    }

    @Override
    public boolean matches(MultiStackRecipeInput inventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
        for(int j = 0; j < inventory.size(); ++j) {
            ItemStack itemStack = inventory.getStackInSlot(j);
            if (!itemStack.isEmpty()) {
                ++i;
                recipeMatcher.addInput(itemStack, 1);
            }
        }
        return i == this.input.size() && recipeMatcher.match(this, null);
    }

    @Override
    public ItemStack craft(MultiStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
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

    public ItemStack getContainer() {
        return container;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.input.size());
        list.addAll(input);
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
    public static class Type implements RecipeType<JuiceExtractingRecipe>{
        private Type() {}
        public static final JuiceExtractingRecipe.Type INSTANCE = new JuiceExtractingRecipe.Type();
        public static final String ID  = "juice_extracting";
    }
    public static class Serializer implements RecipeSerializer<JuiceExtractingRecipe> {

        public static final JuiceExtractingRecipe.Serializer INSTANCE = new JuiceExtractingRecipe.Serializer();
        public static final String ID = "juice_extracting";
        public static final MapCodec<JuiceExtractingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                                .flatXmap(ingredients ->{
                                    Ingredient[] ingredients1 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients1.length == 0){
                                        return DataResult.error(()->"No ingredients");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY,ingredients1));
                                },DataResult::success).forGetter(JuiceExtractingRecipe::getIngredients)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("output")).forGetter(recipe -> recipe.output)
                        , Codec.INT.fieldOf("progress").forGetter(recipe -> recipe.progress)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("container")).forGetter(recipe -> recipe.container)
                ).apply(instance, JuiceExtractingRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, JuiceExtractingRecipe> PACKET_CODEC = PacketCodec.ofStatic(JuiceExtractingRecipe.Serializer::write, JuiceExtractingRecipe.Serializer::read);

        private static JuiceExtractingRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            int progress = buf.readInt();
            ItemStack container = ItemStack.PACKET_CODEC.decode(buf);
            return new JuiceExtractingRecipe(inputs,output,progress,container);
        }

        private static void write(RegistryByteBuf buf, JuiceExtractingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf,ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf,recipe.getResult(null));
            buf.writeInt(recipe.getProgress());
            ItemStack.PACKET_CODEC.encode(buf,recipe.getContainer());
        }

        @Override
        public MapCodec<JuiceExtractingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, JuiceExtractingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
