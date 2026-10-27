package com.zombie_cute.mc.moderndelight.effects.custom;

import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.moderndelight.util.EffectScheduler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class BrainFreezeEffect extends StatusEffect {
    public BrainFreezeEffect() {
        super(StatusEffectCategory.HARMFUL, 0x7FB8D9);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient()) {
            StatusEffectInstance self = entity.getStatusEffect(ModEffectsAndPotions.BRAIN_FREEZE);
            if (self != null) {
                int duration = self.getDuration();
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, duration, 1));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, duration, 0));
            }
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
            int level = amplifier + 1;
            EffectScheduler.scheduleBrainFreezeFireResistance(player, 20 * 30 * level);
        }
    }
}
