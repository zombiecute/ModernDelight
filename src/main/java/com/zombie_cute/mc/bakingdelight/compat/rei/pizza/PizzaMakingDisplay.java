package com.zombie_cute.mc.bakingdelight.compat.rei.pizza;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
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

public class PizzaMakingDisplay implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        Collection<ItemStack> stacks = new ArrayList<>();
        stacks.add(ModItems.CHEESE.getDefaultStack());
        stacks.add(ModBlocks.PIZZA_WIP.asItem().getDefaultStack());
        stacks.add(ModBlocks.WHEAT_DOUGH.asItem().getDefaultStack());
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.ROLLING_PINS)) {
            stacks.add(registryEntry.value().getDefaultStack());
        }
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.PIZZA_INGREDIENTS)) {
            stacks.add(registryEntry.value().getDefaultStack());
        }
        return List.of(EntryIngredients.ofItemStacks(stacks));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        Collection<ItemStack> stacks = new ArrayList<>();
        stacks.add(ModBlocks.RAW_PIZZA.asItem().getDefaultStack());
        stacks.add(ModBlocks.PIZZA_WIP.asItem().getDefaultStack());
        return List.of(EntryIngredients.ofItemStacks(stacks));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PizzaMakingCategory.PIZZA_MAKING;
    }

}
