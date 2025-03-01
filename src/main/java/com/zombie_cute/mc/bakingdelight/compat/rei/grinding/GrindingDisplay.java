package com.zombie_cute.mc.bakingdelight.compat.rei.grinding;

import com.google.common.collect.ImmutableList;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.recipe.custom.GrindingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrindingDisplay extends BasicDisplay {
    public GrindingDisplay(GrindingRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                getOutputs(recipe),
                Optional.ofNullable(recipe.getId()));
    }
    private static List<EntryIngredient> getOutputs(GrindingRecipe recipe){
        List<EntryIngredient> outputs = new ArrayList<>();
        for (ItemStack item : recipe.getOutputs()){
            outputs.add(EntryIngredient.of(EntryStacks.of(item)));
        }
        return outputs;
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> inputEntryList = new ArrayList<>(super.getInputEntries());
        inputEntryList.add(EntryIngredient.of(EntryStacks.of(ModItems.STONE_MORTAR)));
        return ImmutableList.copyOf(inputEntryList);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return GrindingCategory.GRINDING;
    }
}
