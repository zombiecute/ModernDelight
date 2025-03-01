package com.zombie_cute.mc.bakingdelight.compat.rei.instant_noodles;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class InstantNoodlesMakingDisplay implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> stacks = new ArrayList<>();
        stacks.add(EntryIngredients.of(ModItems.MULTIFUNCTIONAL_WRAPPING_PAPER));
        stacks.add(EntryIngredients.of(ModItems.FRIED_NOODLES));
        stacks.add(EntryIngredients.of(ModItems.PORTABLE_POT));
        stacks.add(EntryIngredients.of(ModItems.PACKAGED_INSTANT_NOODLES));
        for (SpecialIngredient specialIngredient : SpecialIngredient.values()){
            for(Ingredient ingredient : specialIngredient.getIngredients()){
                for(ItemStack stack : ingredient.getMatchingStacks()){
                    stacks.add(EntryIngredients.of(stack));
                }
            }
        }
        return stacks;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(ModItems.COOKED_PORTABLE_POT.getDefaultStack());
        stacks.add(ModItems.PACKAGED_INSTANT_NOODLES.getDefaultStack());
        return List.of(EntryIngredients.ofItemStacks(stacks));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return InstantNoodlesMakingCategory.INSTANT_NOODLES_MAKING;
    }

}
