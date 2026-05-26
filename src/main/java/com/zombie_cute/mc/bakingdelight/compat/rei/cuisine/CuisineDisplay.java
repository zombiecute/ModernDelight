package com.zombie_cute.mc.bakingdelight.compat.rei.cuisine;

import com.zombie_cute.mc.bakingdelight.recipe.custom.CuisineRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CuisineDisplay extends BasicDisplay {
    public CuisineDisplay(CuisineRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getOutput(null))),
                Optional.ofNullable(recipe.getId()));
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        return new ArrayList<>(super.getInputEntries());
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CuisineCategory.CUISINE;
    }
}
