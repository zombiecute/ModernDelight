package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.effects.ModPotions;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;

public class ModBrewingRecipe {
    public static void registerModBrewingRecipe(){
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
                builder.registerPotionRecipe(
                        Potions.AWKWARD,
                        ModItems.CUTTLEBONE,
                        Registries.POTION.getEntry(ModPotions.SQUID_POWER_POTION)));
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
                builder.registerPotionRecipe(
                        Registries.POTION.getEntry(ModPotions.SQUID_POWER_POTION),
                        Items.REDSTONE,
                        Registries.POTION.getEntry(ModPotions.SQUID_POWER_LONG_POTION)));
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
                builder.registerPotionRecipe(
                        Registries.POTION.getEntry(ModPotions.SQUID_POWER_POTION),
                        Items.GLOWSTONE_DUST,
                        Registries.POTION.getEntry(ModPotions.SQUID_POWER_STRONG_POTION)));
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
                builder.registerPotionRecipe(
                        Potions.AWKWARD,
                        ModItems.GLOW_CUTTLEBONE,
                        Registries.POTION.getEntry(ModPotions.GLOW_SQUID_POWER_POTION)));
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
                builder.registerPotionRecipe(
                        Registries.POTION.getEntry(ModPotions.GLOW_SQUID_POWER_POTION),
                        Items.REDSTONE,
                        Registries.POTION.getEntry(ModPotions.GLOW_SQUID_POWER_LONG_POTION)));
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder ->
                builder.registerPotionRecipe(
                        Registries.POTION.getEntry(ModPotions.GLOW_SQUID_POWER_POTION),
                        Items.GLOWSTONE_DUST,
                        Registries.POTION.getEntry(ModPotions.GLOW_SQUID_POWER_STRONG_POTION)));
    }
}
