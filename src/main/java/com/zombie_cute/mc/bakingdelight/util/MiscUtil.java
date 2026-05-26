package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.item.food.SeasoningItem;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.components.ModComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

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
        List<String> nbt = stack.getOrDefault(ModComponents.SEASONING_ITEMS, new ArrayList<>());
        if (!nbt.isEmpty()) {
            try {
                List<Item> seasoning = new ArrayList<>();
                for (String s : nbt) {
                    seasoning.add(Registries.ITEM.get(Identifier.of(s)));
                }
                for (Item i : seasoning) {
                    if (i instanceof SeasoningItem s) {
                        List<StatusEffectInstance> effects = s.getEffects();
                        for (StatusEffectInstance effect : effects) {
                            targetEntity.addStatusEffect(new StatusEffectInstance(effect));
                        }
                    }
                }
            } catch (Exception ignored) {}
        }
    }
}
