package com.zombie_cute.mc.bakingdelight.compat.rei.biogas_fermentation;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.biogas.BiogasDigesterIOBlockEntity;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BiogasFermentationDisplay implements Display {

    @Override
    public List<EntryIngredient> getInputEntries() {
        Collection<ItemStack> stacks = new ArrayList<>();
        stacks.add(ModBlocks.BIOGAS_DIGESTER_IO.asItem().getDefaultStack());
        stacks.add(ModBlocks.BIOGAS_DIGESTER_CONTROLLER.asItem().getDefaultStack());
        return List.of(EntryIngredients.ofItemStacks(stacks));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        List<EntryIngredient> inputEntryList = new ArrayList<>();
        inputEntryList.add(EntryIngredients.of(ModBlocks.GAS_CANISTER));
        inputEntryList.add(EntryIngredients.of(BiogasDigesterIOBlockEntity.getDigestate()));
        inputEntryList.add(EntryIngredients.of(ModBlocks.LIQUEFIED_BIOGAS_FLUID_BLOCK));
        inputEntryList.add(EntryIngredients.of(ModFluid.STILL_LIQUEFIED_BIOGAS));
        return inputEntryList;
    }


    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return BiogasFermentationCategory.BIOGAS_FERMENTATION;
    }

}
