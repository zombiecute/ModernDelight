package com.zombie_cute.mc.moderndelight.compat.rei.cuisine;

import com.zombie_cute.mc.moderndelight.recipe.custom.CuisineRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CuisineDisplay extends BasicDisplay {
    private final List<Float> chances;

    public CuisineDisplay(CuisineRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                recipe.getOutputs().stream().map(result -> EntryIngredients.of(result.stack())).toList(),
                Optional.ofNullable(recipe.getId()));
        this.chances = recipe.getOutputs().stream().map(CuisineRecipe.Output::chance).toList();
    }

    public float getChance(int index) {
        return chances.get(index);
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
