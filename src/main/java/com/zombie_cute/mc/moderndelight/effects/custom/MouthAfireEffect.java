package com.zombie_cute.mc.moderndelight.effects.custom;

import com.zombie_cute.mc.moderndelight.util.registry_util.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

public class MouthAfireEffect extends StatusEffect {
    public MouthAfireEffect() {
        super(StatusEffectCategory.HARMFUL, 0xD64524);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!(entity.getWorld() instanceof ServerWorld world)) {
            return;
        }
        double x = entity.getX();
        double y = entity.getEyeY() - 0.15;
        double z = entity.getZ();
        world.spawnParticles(ParticleTypes.FLAME,
                x, y, z, 1, 0.08, 0.06, 0.08, 0.005);
        world.spawnParticles(ParticleTypes.SMOKE,
                x, y, z, 1, 0.06, 0.04, 0.06, 0.005);
        if (entity.age % 60 == 0) {
            float damage = amplifier + 1.0F;
            entity.damage(ModDamageTypes.of(entity.getWorld(),ModDamageTypes.MOUTH_AFIRE), damage);
        }
    }
}
