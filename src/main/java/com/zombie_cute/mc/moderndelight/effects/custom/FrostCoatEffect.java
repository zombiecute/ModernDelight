package com.zombie_cute.mc.moderndelight.effects.custom;

import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

public class FrostCoatEffect extends StatusEffect {
    public FrostCoatEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x9FD8E8);
    }
    public static void applyFrostCoatEffect(LivingEntity effectOwner, LivingEntity target, ServerWorld world) {
        StatusEffectInstance frostCoat = effectOwner.getStatusEffect(ModEffectsAndPotions.FROST_COAT);
        if (frostCoat == null || !target.isAlive()) {
            return;
        }
        int duration = 40 * (frostCoat.getAmplifier() + 1);
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, duration, 1), effectOwner);
        // 在受到效果的实体周围生成雪花粒子
        world.spawnParticles(ParticleTypes.SNOWFLAKE, target.getX(), target.getBodyY(0.6), target.getZ(),
                6, 0.3, 0.35, 0.3, 0.02);
    }
}
