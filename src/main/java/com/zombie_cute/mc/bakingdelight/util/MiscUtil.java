package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.item.food.SeasoningItem;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
        NbtCompound nbt = stack.getSubNbt("modern_delight_seasoning");
        if (nbt != null) {
            try {
                List<Item> seasoning = new ArrayList<>();
                for (int i = 1; i <= SeasoningItem.getMaxSeasoning(); i++) {
                    if (nbt.contains("seasoning_" + i)) {
                        String name = nbt.getString("seasoning_" + i);
                        seasoning.add(Registries.ITEM.get(new Identifier(name)));
                    }
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
