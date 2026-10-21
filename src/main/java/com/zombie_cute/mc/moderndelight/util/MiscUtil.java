package com.zombie_cute.mc.moderndelight.util;

import com.google.gson.JsonSyntaxException;
import com.zombie_cute.mc.moderndelight.item.food.SeasoningItem;
import com.zombie_cute.mc.moderndelight.tag.TagKeys;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class MiscUtil {
    public static boolean isPlayerHoldingCrowbar(PlayerEntity player) {
        Item item = player.getMainHandStack().getItem();
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.CROWBARS)) {
            if (item == registryEntry.value()) {
                return true;
            }
        }
        return false;
    }
    public static boolean isInk(Item item) {
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.INKS)) {
            if (item == registryEntry.value()) {
                return true;
            }
        }
        return false;
    }
    public static void applyFoodEffects(ItemStack stack, LivingEntity targetEntity) {
        // SeasoningItem#getSeasonings skips invalid individual entries instead of
        // aborting the whole list, so one broken id cannot disable every effect.
        for (Item item : SeasoningItem.getSeasonings(stack)) {
            if (!(item instanceof SeasoningItem seasoning)) {
                continue;
            }
            for (StatusEffectInstance effect : seasoning.getEffects()) {
                if (effect != null) {
                    targetEntity.addStatusEffect(new StatusEffectInstance(effect));
                }
            }
        }
    }

    public static Fluid getFluidFromString(String fluid_string) {
        return Registries.FLUID.getOrEmpty(Identifier.tryParse(fluid_string)).orElse(Fluids.EMPTY);
    }
}
