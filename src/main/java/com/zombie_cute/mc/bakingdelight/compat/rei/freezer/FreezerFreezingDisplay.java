package com.zombie_cute.mc.bakingdelight.compat.rei.freezer;

import com.google.common.collect.ImmutableList;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.entities.FreezerBlockEntity;
import com.zombie_cute.mc.bakingdelight.recipe.custom.FreezingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.*;

public class FreezerFreezingDisplay extends BasicDisplay {
    public FreezerFreezingDisplay(FreezingRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                Collections.singletonList(EntryIngredients.of(recipe.getResult(null).getItem(),recipe.getResult(null).getCount())),
                Optional.of(Identifier.of(Bakingdelight.MOD_ID,recipe.getResult(null).getTranslationKey()+recipe.hashCode())));
    }
    public static Collection<ItemStack> getCoolItems(){
        Collection<ItemStack> itemStacks = new ArrayList<>();
        for (Item item : FreezerBlockEntity.createCoolTimeMap().keySet()){
            itemStacks.add(item.getDefaultStack());
        }
        return itemStacks;
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> inputEntryList = new ArrayList<>(super.getInputEntries());
        EntryIngredient entryStacks = EntryIngredients.ofItemStacks(getCoolItems());
        inputEntryList.add(entryStacks);
        return ImmutableList.copyOf(inputEntryList);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return FreezerFreezingCategory.FREEZING;
    }
}
