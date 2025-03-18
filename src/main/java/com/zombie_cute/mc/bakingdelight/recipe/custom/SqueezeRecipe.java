package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
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

public class SqueezeRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output_item;
    private final DefaultedList<Ingredient> ingredients;
    private final FluidStack output_fluid;
    private final boolean isDanger;
    private final boolean doCreateFire;
    public SqueezeRecipe(Identifier id, DefaultedList<Ingredient> ingredients, ItemStack output_item, FluidStack output_fluid, boolean isDanger, boolean doCreateFire){
        this.id = id;
        this.output_item = output_item;
        this.ingredients = ingredients;
        this.output_fluid = output_fluid;
        this.isDanger = isDanger;
        this.doCreateFire = doCreateFire;
    }
    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return ingredients.get(0).test(inventory.getStack(0));
    }

    public boolean isDanger() {
        return isDanger;
    }

    public boolean doCreateFire() {
        return doCreateFire;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
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
    public ItemStack createIcon() {
        return ModBlocks.WOODEN_BASIN.asItem().getDefaultStack();
    }

    @Override
    public Identifier getId() {
        return id;
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

        public static FluidStack getFluidFromJson(JsonObject json){
            String string = JsonHelper.getString(json, "fluid");
            Fluid fluid = Registries.FLUID.getOrEmpty(Identifier.tryParse(string)).orElseThrow(() -> new JsonSyntaxException("Unknown fluid '" + string + "'"));
            int i = JsonHelper.getInt(json, "amount", 9000);
            if (i < 1) {
                throw new JsonSyntaxException("Invalid fluid amount count: " + i);
            } else {
                return new FluidStack(FluidVariant.of(fluid),i);
            }
        }
        @Override
        public SqueezeRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json,"output"));
            JsonArray ingredients = JsonHelper.getArray(json,"ingredients");
            FluidStack fluid = getFluidFromJson(JsonHelper.getObject(json,"fluid_output"));

            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1,Ingredient.EMPTY);

            for(int i=0;i<inputs.size();i++){
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }
            boolean isDanger = JsonHelper.getBoolean(json,"is_danger",false);
            boolean doCreateFire = JsonHelper.getBoolean(json,"do_create_fire",false);
            return new SqueezeRecipe(id, inputs, output,fluid,isDanger,doCreateFire);
        }

        @Override
        public SqueezeRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            String string = buf.readString();
            Fluid fluid = Registries.FLUID.getOrEmpty(Identifier.tryParse(string)).orElseThrow(() -> new JsonSyntaxException("Unknown fluid '" + string + "'"));
            int amount = buf.readInt();
            boolean isDanger = buf.readBoolean();
            boolean doCreateFire = buf.readBoolean();
            return new SqueezeRecipe(id, inputs, output, new FluidStack(FluidVariant.of(fluid),amount),isDanger,doCreateFire);
        }

        @Override
        public void write(PacketByteBuf buf, SqueezeRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()){
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.output_item);
            Fluid fluid = recipe.getOutputFluid().getFluidVariant().getFluid();
            String string = Registries.FLUID.getId(fluid).toString();
            buf.writeString(string);
            int amount = (int)recipe.getOutputFluid().getAmount();
            buf.writeInt(amount);
            buf.writeBoolean(recipe.isDanger);
            buf.writeBoolean(recipe.doCreateFire);
        }
    }
}
