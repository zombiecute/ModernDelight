package com.zombie_cute.mc.bakingdelight.compat.rei.fermenting;

import com.google.common.collect.ImmutableList;
import com.zombie_cute.mc.bakingdelight.recipe.custom.FermentingRecipe;
import com.zombie_cute.mc.bakingdelight.util.enums.BacteriaType;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FermentingDisplay extends BasicDisplay {
    FermentingRecipe recipe;
    public FermentingDisplay(FermentingRecipe recipe){
        super(getInputs(recipe), getOutputs(recipe), Optional.ofNullable(recipe.getId()));
        this.recipe = recipe;
    }
    private static List<EntryIngredient> getInputs(FermentingRecipe recipe){
        // 0-3 -> Input Item, 4 -> Input Fluid
        DefaultedList<Ingredient> inputs = DefaultedList.of();
        for (Ingredient ingredient : recipe.getIngredients()){
            if (!ingredient.test(Items.BARRIER.getDefaultStack())) {
                inputs.add(ingredient);
            }
        }
        List<EntryIngredient> ingredients = new ArrayList<>(EntryIngredients.ofIngredients(inputs));
        Fluid fluid = recipe.getInputFluid();
        if (fluid != Fluids.EMPTY) {
            long amount = recipe.getMinFluidAmount();
            if (amount > 0) {
                ingredients.add(EntryIngredients.of(FluidStack.create(fluid, amount)));
            } else {
                ingredients.add(EntryIngredients.of(FluidStack.create(fluid, 81L)));
            }
        }
        return ingredients;
    }
    private static List<EntryIngredient> getOutputs(FermentingRecipe recipe){
        // 0 -> Output Item, 1 -> Output Fluid
        List<EntryIngredient> ingredients = new ArrayList<>();
        ItemStack outputItem = recipe.getOutput(null);
        if (outputItem.getItem() != Items.BARRIER) {
            ingredients.add(EntryIngredients.of(outputItem));
        }
        Fluid fluid = recipe.getOutputFluid();
        if(fluid != Fluids.EMPTY){
            ingredients.add(EntryIngredients.of(FluidStack.create(fluid, 81L)));
        }
        return ingredients;
    }
    public int getTime(){
        return recipe.getTime();
    }
    public int getLuminosity(){
        return recipe.getLuminosity();
    }
    public int getTemperature(){
        return recipe.getTemperature();
    }
    public BacteriaType getBacteriaInput(){
        return recipe.getBacteria_input();
    }
    public BacteriaType getBacteriaOutput(){
        return recipe.getBacteria_output();
    }
    public int getBacteriaOutputCFU(){
        return recipe.getBacteria_output_amount();
    }
    public List<EntryIngredient> getInputItems(){
        List<EntryIngredient> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()){
            if (!ingredient.test(Items.BARRIER.getDefaultStack())) {
                inputs.add(EntryIngredients.ofIngredient(ingredient));
            } else inputs.add(EntryIngredients.of(ItemStack.EMPTY));
        }
        return inputs;
    }
    public EntryIngredient getOutputItem(){
        ItemStack outputItem = recipe.getOutput(null);
        if (outputItem.getItem() != Items.BARRIER) {
            return EntryIngredients.of(outputItem);
        } else {
            return EntryIngredients.of(ItemStack.EMPTY);
        }
    }
    public EntryIngredient getInputFluid(){
        Fluid fluid = recipe.getInputFluid();
        if(fluid != Fluids.EMPTY){
            return EntryIngredients.of(FluidStack.create(fluid, recipe.getMinFluidAmount() == 0 ? 81 : recipe.getMinFluidAmount()));
        } else {
            return EntryIngredients.of(ItemStack.EMPTY);
        }
    }
    public EntryIngredient getOutputFluid(){
        Fluid fluid = recipe.getOutputFluid();
        if(fluid != Fluids.EMPTY){
            return EntryIngredients.of(FluidStack.create(fluid, 81L));
        } else {
            return EntryIngredients.of(ItemStack.EMPTY);
        }
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return FermentingCategory.FERMENTING;
    }
}
