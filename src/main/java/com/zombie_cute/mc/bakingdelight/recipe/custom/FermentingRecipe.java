package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.BacteriaType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class FermentingRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output_item;
    private final DefaultedList<Ingredient> ingredients;
    private final Fluid output_fluid;
    private final long min_fluid_amount;
    private final Fluid input_fluid;
    private final int temperature;
    private final int luminosity;
    private final int time;
    private final String bacteria_input;
    private final String bacteria_output;
    private final int bacteria_output_amount;
    public FermentingRecipe(Identifier id, DefaultedList<Ingredient> ingredients, Fluid input_fluid, long min_fluid_amount,
                            ItemStack output_item, Fluid output_fluid,
                            int temperature, int luminosity, int time,
                            String bacteria_input, String bacteria_output, int bacteria_output_amount) {
        this.id = id;
        this.output_item = output_item;
        this.min_fluid_amount = min_fluid_amount;
        this.ingredients = ingredients;
        this.output_fluid = output_fluid;
        this.input_fluid = input_fluid;
        this.temperature = temperature;
        this.luminosity = luminosity;
        this.time = time;
        this.bacteria_input = bacteria_input;
        this.bacteria_output = bacteria_output;
        this.bacteria_output_amount = bacteria_output_amount;
    }
    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return ingredients.get(0).test(inventory.getStack(0)) &&
                ingredients.get(1).test(inventory.getStack(1)) &&
                ingredients.get(2).test(inventory.getStack(2)) &&
                ingredients.get(3).test(inventory.getStack(3));
    }

    public int getLuminosity() {
        return luminosity;
    }

    public int getTemperature() {
        return temperature;
    }

    public BacteriaType getBacteria_input() {
        return BacteriaType.getBacteriaFromName(bacteria_input);
    }
    public BacteriaType getBacteria_output() {
        return BacteriaType.getBacteriaFromName(bacteria_output);
    }
    public int getBacteria_output_amount() {
        return bacteria_output_amount;
    }
    public int getTime() {
        return time;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output_item;
    }

    public Fluid getInputFluid() {
        return input_fluid;
    }
    public long getMinFluidAmount() {
        return min_fluid_amount;
    }
    public Fluid getOutputFluid() {
        return output_fluid;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output_item;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.ingredients.size());
        list.addAll(ingredients);
        return list;
    }
    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }
    @Override
    public ItemStack createIcon() {
        return ModBlocks.INDUSTRIAL_FERMENTER.asItem().getDefaultStack();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FermentingRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return FermentingRecipe.Type.INSTANCE;
    }
    public static class Type implements RecipeType<FermentingRecipe>{
        private Type() {}
        public static final FermentingRecipe.Type INSTANCE = new FermentingRecipe.Type();
        public static final String ID  = "fermenting";
    }
    public static class Serializer implements RecipeSerializer<FermentingRecipe> {

        public static final FermentingRecipe.Serializer INSTANCE = new FermentingRecipe.Serializer();
        public static final String ID = "fermenting";
        @Override
        public FermentingRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json,"output"));
            JsonArray ingredients = JsonHelper.getArray(json,"ingredients");
            String fluid_output = JsonHelper.getString(json,"fluid_output","empty");
            String fluid_input = JsonHelper.getString(json,"fluid_input","empty");
            long min_fluid_amount = JsonHelper.getLong(json,"min_fluid_amount",0);
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(4,Ingredient.EMPTY);
            for(int i=0;i<inputs.size();i++){
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }
            int temperature = JsonHelper.getInt(json,"temperature", 25);
            int luminosity = JsonHelper.getInt(json,"luminosity", 7);
            int time = JsonHelper.getInt(json,"time", 60);
            String bacteria_input = JsonHelper.getString(json,"bacteria_input","null");
            String bacteria_output = JsonHelper.getString(json,"bacteria_output","null");
            int bacteria_output_amount = JsonHelper.getInt(json,"bacteria_output_amount", 300);
            return new FermentingRecipe(id, inputs, MiscUtil.getFluidFromString(fluid_input), min_fluid_amount,
                    output, MiscUtil.getFluidFromString(fluid_output),
                    temperature, luminosity, time,
                    bacteria_input, bacteria_output,bacteria_output_amount);
        }

        @Override
        public FermentingRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
            ItemStack output = buf.readItemStack();
            String input_fluid = buf.readString();
            long min_fluid_amount = buf.readLong();
            String output_fluid = buf.readString();
            int temperature = buf.readInt();
            int luminosity = buf.readInt();
            int time = buf.readInt();
            String bacteria_input = buf.readString();
            String bacteria_output = buf.readString();
            int bacteria_output_amount = buf.readInt();
            return new FermentingRecipe(id, inputs, MiscUtil.getFluidFromString(input_fluid), min_fluid_amount,
                    output, MiscUtil.getFluidFromString(output_fluid),
                    temperature, luminosity, time,
                    bacteria_input, bacteria_output,bacteria_output_amount);
        }

        @Override
        public void write(PacketByteBuf buf, FermentingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()){
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.output_item);
            Fluid input_fluid = recipe.getInputFluid();
            String string_input = Registries.FLUID.getId(input_fluid).toString();
            buf.writeString(string_input);
            buf.writeLong(recipe.getMinFluidAmount());
            Fluid output_fluid = recipe.getOutputFluid();
            String string_output = Registries.FLUID.getId(output_fluid).toString();
            buf.writeString(string_output);
            buf.writeInt(recipe.getTemperature());
            buf.writeInt(recipe.getLuminosity());
            buf.writeInt(recipe.getTime());
            buf.writeString(recipe.bacteria_input);
            buf.writeString(recipe.bacteria_output);
            buf.writeInt(recipe.getBacteria_output_amount());
        }
    }
}
