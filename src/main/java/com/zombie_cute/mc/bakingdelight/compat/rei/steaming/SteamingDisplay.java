package com.zombie_cute.mc.bakingdelight.compat.rei.steaming;

import com.google.common.collect.ImmutableList;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.recipe.custom.SteamingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SteamingDisplay extends BasicDisplay {
    public SteamingDisplay(SteamingRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getResult(null).getItem(),recipe.getResult(null).getCount())),
                Optional.of(Identifier.of(Bakingdelight.MOD_ID,recipe.getResult(null).getTranslationKey()+recipe.hashCode())));
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> inputEntryList = new ArrayList<>(super.getInputEntries());
        inputEntryList.add(EntryIngredients.of(Fluids.WATER, 27000L));
        return ImmutableList.copyOf(inputEntryList);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SteamingCategory.STEAMING;
    }
}
