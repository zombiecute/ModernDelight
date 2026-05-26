package com.zombie_cute.mc.bakingdelight.effects.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class StickyEffect extends StatusEffect {
    public StickyEffect() {
        super(StatusEffectCategory.HARMFUL, 0xB4C92A);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        World world = entity.getWorld();
        if (entity.isOnGround() && world instanceof ServerWorld serverWorld) {
            spawnParticles(entity, serverWorld);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    private static void spawnParticles(LivingEntity entity, ServerWorld serverWorld) {
        serverWorld.spawnParticles(
                ParticleTypes.FALLING_HONEY,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                1,
                0.0, 0.0, 0.0,
                0.0
        );
    }
}
