package com.zombie_cute.mc.bakingdelight.compat.rei.oven;

import com.google.common.collect.ImmutableList;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.recipe.custom.BakingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OvenBakingDisplay extends BasicDisplay {
    public OvenBakingDisplay(BakingRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getResult(null).getItem(),recipe.getResult(null).getCount())),
                Optional.of(Identifier.of(Bakingdelight.MOD_ID,recipe.getResult(null).getTranslationKey()+recipe.hashCode())));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> inputEntryList = new ArrayList<>(super.getInputEntries());

        return ImmutableList.copyOf(inputEntryList);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return OvenBakingCategory.OVEN_BAKING;
    }
}
