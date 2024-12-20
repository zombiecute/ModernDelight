package com.zombie_cute.mc.bakingdelight.compat.rei.baking_tray;

import com.google.common.collect.ImmutableList;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BakingTrayDisplay extends BasicDisplay {
    public BakingTrayDisplay(CampfireCookingRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getResult(null).getItem(),recipe.getResult(null).getCount())),
                Optional.of(Identifier.of(Bakingdelight.MOD_ID,"stir_frying_"+recipe.getResult(null).getItem().toString().replace(":","_"))));
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> inputEntryList = new ArrayList<>(super.getInputEntries());
        return ImmutableList.copyOf(inputEntryList);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return BakingTrayCategory.STIR_FRYING;
    }
}
