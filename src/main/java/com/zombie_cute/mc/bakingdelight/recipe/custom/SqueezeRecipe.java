package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
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

public class SqueezeRecipe implements Recipe<SingleStackRecipeInput> {
    private final ItemStack output_item;
    private final List<Ingredient> ingredients;
    private final FluidStack output_fluid;
    private final String fluid_name;
    private final int fluid_amount;
    private final boolean isDanger;
    private final boolean doCreateFire;
    public SqueezeRecipe(List<Ingredient> ingredients, ItemStack output_item, String fluid_name,int fluid_amount, boolean isDanger, boolean doCreateFire){
        this.fluid_name = fluid_name;
        this.fluid_amount = fluid_amount;
        FluidStack output_fluid = FluidStack.getFluidStack(fluid_name, fluid_amount);
        this.output_item = output_item;
        this.ingredients = ingredients;
        this.output_fluid = output_fluid;
        this.isDanger = isDanger;
        this.doCreateFire = doCreateFire;
    }

    @Override
    public boolean matches(SingleStackRecipeInput inventory, World world) {
        return ingredients.getFirst().test(inventory.getStackInSlot(0));

    }

    public boolean isDanger() {
        return isDanger;
    }

    public boolean doCreateFire() {
        return doCreateFire;
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output_item;
    }

    public FluidStack getOutputFluid() {
        return output_fluid;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output_item;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.ingredients.size());
        list.addAll(ingredients);
        return list;
    }

    @Override
    public ItemStack createIcon() {
        return ModBlocks.WOODEN_BASIN.asItem().getDefaultStack();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SqueezeRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return SqueezeRecipe.Type.INSTANCE;
    }
    public static class Type implements RecipeType<SqueezeRecipe>{
        private Type() {}
        public static final SqueezeRecipe.Type INSTANCE = new SqueezeRecipe.Type();
        public static final String ID  = "squeeze";
    }
    public static class Serializer implements RecipeSerializer<SqueezeRecipe> {

        public static final SqueezeRecipe.Serializer INSTANCE = new SqueezeRecipe.Serializer();
        public static final String ID = "squeeze";
        public static final MapCodec<SqueezeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients")
                                .flatXmap(ingredients ->{
                                    Ingredient[] ingredients1 = ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                                    if (ingredients1.length == 0){
                                        return DataResult.error(()->"No ingredients");
                                    }
                                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY,ingredients1));
                                },DataResult::success).forGetter(SqueezeRecipe::getIngredients)
                        ,(ItemStack.VALIDATED_CODEC.fieldOf("output")).forGetter(recipe -> recipe.output_item)
                        ,Codec.STRING.fieldOf("fluid").forGetter(recipe -> recipe.fluid_name)
                        ,Codec.INT.fieldOf("amount").forGetter(recipe -> recipe.fluid_amount)
                        ,Codec.BOOL.optionalFieldOf("is_danger",false).forGetter(recipe -> recipe.isDanger)
                        ,Codec.BOOL.optionalFieldOf("do_create_fire",false).forGetter(recipe -> recipe.doCreateFire)
                ).apply(instance, SqueezeRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, SqueezeRecipe> PACKET_CODEC = PacketCodec.ofStatic(SqueezeRecipe.Serializer::write, SqueezeRecipe.Serializer::read);

        private static SqueezeRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1,Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            String fluid_name = buf.readString();
            int fluid_amount = buf.readInt();
            boolean is_danger = buf.readBoolean();
            boolean do_create_fire = buf.readBoolean();
            return new SqueezeRecipe(inputs,output,fluid_name, fluid_amount, is_danger, do_create_fire);
        }

        private static void write(RegistryByteBuf buf, SqueezeRecipe recipe) {
            for (Ingredient ingredient : recipe.getIngredients()){
                Ingredient.PACKET_CODEC.encode(buf,ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf,recipe.getResult(null));
            buf.writeString(recipe.fluid_name);
            buf.writeInt(recipe.fluid_amount);
            buf.writeBoolean(recipe.isDanger);
            buf.writeBoolean(recipe.doCreateFire);
        }

        @Override
        public MapCodec<SqueezeRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, SqueezeRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
