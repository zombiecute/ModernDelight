package com.zombie_cute.mc.moderndelight.compat.rei.grinding;

import com.google.common.collect.ImmutableList;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import com.zombie_cute.mc.moderndelight.recipe.custom.GrindingRecipe;
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
    private final float chance;
    public GrindingDisplay(GrindingRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                getOutputs(recipe),
                Optional.ofNullable(recipe.getId()));
        this.chance = recipe.getChance();
    }
    private static List<EntryIngredient> getOutputs(GrindingRecipe recipe){
        List<EntryIngredient> outputs = new ArrayList<>();
        outputs.add(EntryIngredient.of(EntryStacks.of(recipe.getOutput(null))));
        outputs.add(EntryIngredient.of(EntryStacks.of(recipe.getChancedOutput())));
        return outputs;
    }

    public float getChance() {
        return chance;
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
