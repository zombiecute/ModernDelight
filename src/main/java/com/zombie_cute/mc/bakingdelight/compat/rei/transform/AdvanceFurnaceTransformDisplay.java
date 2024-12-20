package com.zombie_cute.mc.bakingdelight.compat.rei.transform;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdvanceFurnaceTransformDisplay implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        Collection<ItemStack> stacks = new ArrayList<>();
        stacks.add(ModBlocks.ADVANCE_FURNACE.asItem().getDefaultStack());
        stacks.add(ModBlocks.BAKING_TRAY.asItem().getDefaultStack());
        return List.of(EntryIngredients.ofItemStacks(stacks));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(ModBlocks.OVEN.asItem(),1));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return AdvanceFurnaceTransformCategory.ADVANCE_FURNACE_TRANSFORMING;
    }

}
