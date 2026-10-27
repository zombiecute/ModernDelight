package com.zombie_cute.mc.moderndelight.effects.custom;

import com.zombie_cute.mc.moderndelight.util.EffectScheduler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

public class FoodComaEffect extends StatusEffect {
    public FoodComaEffect() {
        super(StatusEffectCategory.HARMFUL, 0x6B5B95);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient() && entity instanceof ServerPlayerEntity player) {
            player.setSprinting(false);
            // 新的食困开始，清掉上一轮可能残留的“被打断”标记。
            EffectScheduler.clearComaInterrupted(player.getUuid());
        }
        super.onApplied(entity, attributes, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient() && entity instanceof ServerPlayerEntity player) {
            EffectScheduler.scheduleComaWellFed(player);
        }
    }
}
