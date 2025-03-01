package com.zombie_cute.mc.bakingdelight.util.registry_util;

import com.zombie_cute.mc.bakingdelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;

public class ModBrewingRecipe {
    public static void registerModBrewingRecipe(){
        BrewingRecipeRegistry.registerPotionRecipe(Potions.STRONG_SLOWNESS, ModItems.BUTTER, ModEffectsAndPotions.STICKY_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(ModEffectsAndPotions.STICKY_POTION, Items.REDSTONE, ModEffectsAndPotions.STICKY_LONG_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, ModItems.CUTTLEBONE, ModEffectsAndPotions.SQUID_POWER_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(ModEffectsAndPotions.SQUID_POWER_POTION, Items.REDSTONE, ModEffectsAndPotions.SQUID_POWER_LONG_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(ModEffectsAndPotions.SQUID_POWER_POTION, Items.GLOWSTONE_DUST, ModEffectsAndPotions.SQUID_POWER_STRONG_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, ModItems.GLOW_CUTTLEBONE, ModEffectsAndPotions.GLOW_SQUID_POWER_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(ModEffectsAndPotions.GLOW_SQUID_POWER_POTION, Items.REDSTONE, ModEffectsAndPotions.GLOW_SQUID_POWER_LONG_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(ModEffectsAndPotions.GLOW_SQUID_POWER_POTION, Items.GLOWSTONE_DUST, ModEffectsAndPotions.GLOW_SQUID_POWER_STRONG_POTION);
    }
}
