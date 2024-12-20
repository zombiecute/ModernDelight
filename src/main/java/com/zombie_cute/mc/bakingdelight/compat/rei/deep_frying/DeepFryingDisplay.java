package com.zombie_cute.mc.bakingdelight.compat.rei.deep_frying;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.DeepFryingRecipe;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DeepFryingDisplay extends BasicDisplay {
    public DeepFryingDisplay(DeepFryingRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getResult(null).getItem(),recipe.getResult(null).getCount())),
                Optional.of(Identifier.of(Bakingdelight.MOD_ID,recipe.getResult(null).getTranslationKey()+recipe.hashCode())));
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> ingredients = new ArrayList<>(super.getInputEntries());
        ingredients.add(EntryIngredients.ofFluidTag(TagKeys.OIL));
        ingredients.add(EntryIngredients.of(ModBlocks.GAS_CANISTER,1));
        ingredients.add(EntryIngredients.of(ModBlocks.DEEP_FRY_BASKET,1));
        return ingredients;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return DeepFryingCategory.DEEP_FRYING;
    }
}
