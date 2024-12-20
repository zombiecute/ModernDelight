package com.zombie_cute.mc.bakingdelight.compat.rei.wooden_basin;

import com.zombie_cute.mc.bakingdelight.block.entities.WoodenBasinBlockEntity;
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

public class WoodenBasinDisplay implements Display {

    @Override
    public List<EntryIngredient> getInputEntries() {
        Collection<ItemStack> stacks = new ArrayList<>();
        for (Item item:WoodenBasinBlockEntity.createOilMap().keySet()){
            stacks.add(item.getDefaultStack());
        }
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.FILTERS)) {
            stacks.add(registryEntry.value().getDefaultStack());
        }
        return List.of(EntryIngredients.ofItemStacks(stacks));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        List<EntryIngredient> ingredients = new ArrayList<>();
        ingredients.add(EntryIngredients.ofFluidTag(TagKeys.OIL));
        ingredients.add(EntryIngredients.of(ModItems.OIL_IMPURITY,1));
        return ingredients;
    }


    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return WoodenBasinCategory.WOODEN_BASIN;
    }

}
