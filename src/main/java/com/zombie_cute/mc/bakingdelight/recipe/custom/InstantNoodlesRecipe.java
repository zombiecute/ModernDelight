//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.zombie_cute.mc.bakingdelight.recipe.custom;

import com.zombie_cute.mc.bakingdelight.components.ModComponents;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class InstantNoodlesRecipe extends SpecialCraftingRecipe {
    private static final Ingredient FRIED_NOODLES = Ingredient.ofItems(ModItems.FRIED_NOODLES);
    private static final Ingredient PACKAGE = Ingredient.ofItems(ModItems.MULTIFUNCTIONAL_WRAPPING_PAPER);
    public InstantNoodlesRecipe(CraftingRecipeCategory craftingRecipeCategory) {
        super(craftingRecipeCategory);
    }

    @Override
    public boolean matches(CraftingRecipeInput recipeInputInventory, World world) {
        boolean b1 = false;
        boolean b2 = false;
        for(int i = 0; i < recipeInputInventory.getSize(); ++i) {
            ItemStack itemStack = recipeInputInventory.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                if (PACKAGE.test(itemStack)) {
                    b1 = true;
                } else if (FRIED_NOODLES.test(itemStack)) {
                    b2 = true;
                }
            }
        }
        return b1 && b2;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput recipeInputInventory, RegistryWrapper.WrapperLookup lookup) {
        ItemStack itemStack = new ItemStack(ModItems.PACKAGED_INSTANT_NOODLES);
        boolean b1 = false;
        boolean b2 = false;
        List<String> nbt = itemStack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS, new ArrayList<String>());
        for(int i = 0; i < recipeInputInventory.getSize();++i){
            ItemStack item = recipeInputInventory.getStackInSlot(i);
            if (!item.isEmpty()) {
                if (PACKAGE.test(item) && !b1) {
                    b1 = true;
                } else if (FRIED_NOODLES.test(item)&& !b2) {
                    b2 = true;
                } else {
                    String name = Registries.ITEM.getId(item.getItem()).toString();
                    nbt.add(name);
                }
            }
        }
        itemStack.set(ModComponents.INSTANT_NOODLES_INGREDIENTS, nbt);
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return new ItemStack(ModItems.PACKAGED_INSTANT_NOODLES);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }
    public static class Serializer extends SpecialRecipeSerializer<InstantNoodlesRecipe> implements RecipeSerializer<InstantNoodlesRecipe> {

        public static final InstantNoodlesRecipe.Serializer INSTANCE = new InstantNoodlesRecipe.Serializer();
        public static final String ID = "crafting_special_packaged_instant_noodles";

        public Serializer() {
            super(InstantNoodlesRecipe::new);
        }
    }
}
