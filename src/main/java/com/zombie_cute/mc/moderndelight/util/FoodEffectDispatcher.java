package com.zombie_cute.mc.moderndelight.util;

import com.zombie_cute.mc.moderndelight.advancement.ModCriteria;
import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.moderndelight.item.food.SeasoningItem;
import com.zombie_cute.mc.moderndelight.mixin.StatusEffectInstanceAccessor;
import com.zombie_cute.mc.moderndelight.tag.TagKeys;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public final class FoodEffectDispatcher {
    public static void applyFoodEffects(ItemStack stack, LivingEntity targetEntity) {
        if (targetEntity.getWorld().isClient()) {
            return;
        }

        PlayerEntity player = targetEntity instanceof PlayerEntity p ? p : null;

        for (Item item : SeasoningItem.getSeasonings(stack)) {
            if (!(item instanceof SeasoningItem seasoning)) {
                continue;
            }
            for (StatusEffectInstance effect : seasoning.getEffects()) {
                if (effect == null) {
                    continue;
                }
                if (player != null && effect.getEffectType() == ModEffectsAndPotions.CREAM_COAT) {
                    applyCreamCoat(player, effect.getDuration(), effect.getAmplifier());
                } else {
                    targetEntity.addStatusEffect(new StatusEffectInstance(effect));
                }
            }
        }

        if (player == null) {
            return;
        }
        FoodComponent food = stack.getItem().getFoodComponent();
        if (food == null) {
            return;
        }

        // 鲜味
        boolean hadUmami = player.hasStatusEffect(ModEffectsAndPotions.UMAMI);
        int umamiLevel = hadUmami ? getLevel(player, ModEffectsAndPotions.UMAMI) : 0;
        int effectiveHunger = food.getHunger() + (hadUmami ? 2 * umamiLevel : 0);
        if (hadUmami) {
            player.getHungerManager().add(2 * umamiLevel, 0.25F);
        }

        // 食困
        if (effectiveHunger >= getHeartyMealThreshold() && !stack.isIn(TagKeys.NO_FOOD_COMA)) {
            if (player.hasStatusEffect(ModEffectsAndPotions.GUT_FLORA)) {
                applyWellFed(player);
            } else if (!player.hasStatusEffect(ModEffectsAndPotions.FOOD_COMA)) {
                player.addStatusEffect(new StatusEffectInstance(ModEffectsAndPotions.FOOD_COMA, 8 * 20));
            }
        }

        // 标签 -> 效果
        if (stack.isIn(TagKeys.UMAMI)) {
            player.addStatusEffect(new StatusEffectInstance(ModEffectsAndPotions.UMAMI, 180 * 20));
        }
        if (stack.isIn(TagKeys.SPICY)) {
            int mouthAfireLevel = Math.min(getLevel(player, ModEffectsAndPotions.MOUTH_AFIRE), 4);
            if (player instanceof ServerPlayerEntity serverPlayer && mouthAfireLevel == 4) {
                ModCriteria.NOT_SPICY.trigger(serverPlayer);
            }
            player.addStatusEffect(new StatusEffectInstance(ModEffectsAndPotions.MOUTH_AFIRE, 12 * 20, mouthAfireLevel));
        }
        if (stack.isIn(TagKeys.WARM)) {
            player.addStatusEffect(new StatusEffectInstance(ModEffectsAndPotions.WARM_BELLY, 480 * 20));
        }
        if (stack.isIn(TagKeys.CITRUS)) {
            player.addStatusEffect(new StatusEffectInstance(ModEffectsAndPotions.VITAMIN_C, 18 * 20));
        }
        if (stack.isIn(TagKeys.FRIED)) {
            player.addStatusEffect(new StatusEffectInstance(ModEffectsAndPotions.CRUNCH, 60 * 20));
        }
        if (stack.isIn(TagKeys.FERMENTED)) {
            player.addStatusEffect(new StatusEffectInstance(ModEffectsAndPotions.GUT_FLORA, 480 * 20));
        }

        // 最后结算解药和特殊效果
        if (stack.isIn(TagKeys.COOLS_MOUTH)) {
            if (stack.isIn(TagKeys.ICE_LOLLIES)) {
                if (player.hasStatusEffect(ModEffectsAndPotions.MOUTH_AFIRE)) {
                    int mouthAfireLevel = getLevel(player, ModEffectsAndPotions.MOUTH_AFIRE);
                    int duration = 30 * 20 + (mouthAfireLevel - 1) * 15 * 20;
                    player.removeStatusEffect(ModEffectsAndPotions.MOUTH_AFIRE);
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, duration));
                } else if (player.getWorld().getDimension().ultrawarm() || player.getWorld().random.nextFloat() < 0.35) {
                    player.addStatusEffect(new StatusEffectInstance(ModEffectsAndPotions.BRAIN_FREEZE, 3 * 20));
                }
            } else {
                applyCreamCoat(player, 30 * 20, 0);
            }
        }
    }

    private static void applyWellFed(PlayerEntity player) {
        int level = Math.min(getLevel(player, ModEffectsAndPotions.WELL_FED),4);
        player.addStatusEffect(new StatusEffectInstance(ModEffectsAndPotions.WELL_FED, 120 * 20, level));
    }

    public static void applyCreamCoat(PlayerEntity player, int duration, int amplifier) {
        StatusEffectInstance current = player.getStatusEffect(ModEffectsAndPotions.CREAM_COAT);
        if (current != null) {
            duration = Math.max(duration, current.getDuration());
            amplifier = Math.max(amplifier, current.getAmplifier());
        }
        int mouthAfireLevel = getLevel(player, ModEffectsAndPotions.MOUTH_AFIRE);
        if (mouthAfireLevel > 0) {
            amplifier = Math.max(amplifier, mouthAfireLevel + 1);
        }
        player.addStatusEffect(new StatusEffectInstance(
                ModEffectsAndPotions.CREAM_COAT, duration, amplifier));
        if (mouthAfireLevel > 0 && player.hasStatusEffect(ModEffectsAndPotions.CREAM_COAT)) {
            player.removeStatusEffect(ModEffectsAndPotions.MOUTH_AFIRE);
        }
    }

    public static StatusEffectInstance addStatusEffect(LivingEntity entity, StatusEffectInstance effect) {
        if (entity.getWorld().isClient() || !(entity instanceof PlayerEntity player)) {
            return effect;
        }
        int umamiLevel = getLevel(player, ModEffectsAndPotions.UMAMI);
        if (umamiLevel > 0) {
            return handleUmami(player, effect, umamiLevel);
        }
        return effect;
    }

    public static StatusEffectInstance handleUmami(PlayerEntity player, StatusEffectInstance newEffect, int umamiLevel) {
        // 只延长正面效果，且不处理瞬间效果。
        if (newEffect.getEffectType().getCategory() != StatusEffectCategory.BENEFICIAL
                || newEffect.getEffectType().isInstant()) {
            return newEffect;
        }
        // 已有同类型效果时，不重复乘算持续时间。
        if (player.hasStatusEffect(newEffect.getEffectType())) {
            return newEffect;
        }
        int originalDuration = newEffect.getDuration();
        if (originalDuration <= 0) {
            return newEffect;
        }
        double multiplier = 1.0 + Math.min(0.25 * umamiLevel, 1.0);
        int newDuration = (int) Math.floor(originalDuration * multiplier);

        // 用复制构造保留 hiddenEffect / factorCalculationData，只改 duration。
        StatusEffectInstance copy = new StatusEffectInstance(newEffect);
        ((StatusEffectInstanceAccessor) copy).moderndelight$setDuration(newDuration);
        return copy;
    }

    public static int getHeartyMealThreshold() {
        try {
            return ModConfig.heartyMealThreshold;
        } catch (Throwable ignored) {
            return 10;
        }
    }

    /**
     * @return amplifier + 1
     **/
    public static int getLevel(LivingEntity entity, StatusEffect effect) {
        StatusEffectInstance instance = entity.getStatusEffect(effect);
        return instance == null ? 0 : instance.getAmplifier() + 1;
    }
}
