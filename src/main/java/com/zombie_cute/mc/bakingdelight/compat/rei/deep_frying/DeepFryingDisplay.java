package com.zombie_cute.mc.bakingdelight.compat.rei.deep_frying;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.recipe.custom.DeepFryingRecipe;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DeepFryingDisplay extends BasicDisplay {
    public DeepFryingDisplay(DeepFryingRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getOutput(null))),
                Optional.ofNullable(recipe.getId()));
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> ingredients = new ArrayList<>(super.getInputEntries());
        ingredients.add(EntryIngredients.ofFluidTag(TagKeys.OIL));
        ingredients.add(EntryIngredients.of(ModBlocks.GAS_CANISTER));
        ingredients.add(EntryIngredients.of(ModItems.HOLDER));
        return ingredients;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return DeepFryingCategory.DEEP_FRYING;
    }
}
