package com.zombie_cute.mc.bakingdelight.recipe.recipeInput;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public record MultiStackRecipeInput(List<ItemStack> items, int size) implements RecipeInput {
    public MultiStackRecipeInput(List<ItemStack> items,int size) {
        this.items = items;
        this.size = size;
    }
    @Override
    public ItemStack getStackInSlot(int slot) {
        return items.get(slot);
    }
    @Override
    public int getSize() {
        return size;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
