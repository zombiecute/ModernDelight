package com.zombie_cute.mc.moderndelight.effects.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class StickyEffect extends StatusEffect {
    private static final String MOVEMENT_SPEED_UUID = "d6a4f5e7-8c91-4a03-9d24-3e4f50617283";

    public StickyEffect() {
        super(StatusEffectCategory.HARMFUL, 0xB4C92A);
        // -80% 移动速度，配合 tick 里的 setJumping(false) 达到“黏在地上”的效果。
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                MOVEMENT_SPEED_UUID, -0.8, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier) {
        return modifier.getValue();
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.isOnGround()) {
            entity.setJumping(false);
        }

        World world = entity.getWorld();
        if (entity.isOnGround() && world instanceof ServerWorld serverWorld) {
            spawnParticles(entity, serverWorld);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    private static void spawnParticles(LivingEntity entity, ServerWorld serverWorld) {
        serverWorld.spawnParticles(ParticleTypes.FALLING_HONEY, entity.getX(), entity.getY(), entity.getZ(),
                1, 0.0, 0.0, 0.0, 0.0);
    }
}
