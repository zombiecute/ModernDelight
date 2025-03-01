package com.zombie_cute.mc.bakingdelight.compat.rei.transform;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OvenTransformDisplay implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        Collection<ItemStack> stacks = new ArrayList<>();
        stacks.add(ModBlocks.OVEN.asItem().getDefaultStack());
        for (RegistryEntry<Item> registryKey : Registries.ITEM.iterateEntries(TagKeys.CROWBARS)){
            stacks.add(registryKey.value().getDefaultStack());
        }
        return List.of(EntryIngredients.ofItemStacks(stacks));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        Collection<ItemStack> stacks = new ArrayList<>();
        stacks.add(ModBlocks.BAKING_TRAY.asItem().getDefaultStack());
        stacks.add(ModBlocks.ADVANCE_FURNACE.asItem().getDefaultStack());
        return List.of(EntryIngredients.ofItemStacks(stacks));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return OvenTransformCategory.OVEN_TRANSFORMING;
    }

}
