package com.zombie_cute.mc.bakingdelight.compat.rei.assembly;

import com.google.common.collect.ImmutableList;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.AssemblyRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AssemblyDisplay extends BasicDisplay {
    public AssemblyDisplay(AssemblyRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getOutput(null))),
                Optional.ofNullable(recipe.getId()));
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> inputEntryList = new ArrayList<>(super.getInputEntries());
        inputEntryList.add(EntryIngredients.of(ModBlocks.BAMBOO_COVER));
        inputEntryList.add(EntryIngredients.of(Items.INK_SAC));
        inputEntryList.add(EntryIngredients.of(Items.GLOW_INK_SAC));
        inputEntryList.add(EntryIngredients.of(Items.BLACK_DYE));
        return ImmutableList.copyOf(inputEntryList);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return AssemblyCategory.ASSEMBLY;
    }
}
