package com.zombie_cute.mc.bakingdelight.compat.rei.ice_cream_making;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.ForgeTagKeys;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IceCreamMakingDisplay implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        Collection<ItemStack> stacks = new ArrayList<>();
        stacks.add(ModItems.ICE_CREAM_CONE.getDefaultStack());
        stacks.add(Items.EGG.getDefaultStack());
        stacks.add(Items.SUGAR.getDefaultStack());
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(ForgeTagKeys.CREAMS)) {
            stacks.add(registryEntry.value().getDefaultStack());
        }
        return List.of(EntryIngredients.ofItemStacks(stacks));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.ofIngredient(Ingredient.ofItems(ModItems.ICE_CREAM)));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return IceCreamMakingCategory.ICE_CREAM_MAKING;
    }

}
