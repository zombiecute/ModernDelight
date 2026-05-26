package com.zombie_cute.mc.bakingdelight.entity.custom;

import com.zombie_cute.mc.bakingdelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.bakingdelight.entity.ModEntities;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class ButterEntity extends ThrownItemEntity {
    public ButterEntity(EntityType<ButterEntity> entityType, World world) {
        super(entityType, world);
    }

    public ButterEntity(World world, LivingEntity entity) {
        super(ModEntities.BUTTER, entity, world);
    }

    public ButterEntity(World world, double x, double y, double z) {
        super(ModEntities.BUTTER, x, y, z, world);
    }
    @Override
    protected Item getDefaultItem() {
        return ModItems.BUTTER;
    }
    @Override
    public void handleStatus(byte status) {
        ItemStack entityStack = new ItemStack(this.getDefaultItem());
        if (status == 3) {
            for (int i = 0; i < 12; ++i) {
                this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, entityStack),
                        this.getX(), this.getY(), this.getZ(),
                        ((double) this.random.nextFloat() * 2.0D - 1.0D) * 0.1F,
                        ((double) this.random.nextFloat() * 2.0D - 1.0D) * 0.1F + 0.1F,
                        ((double) this.random.nextFloat() * 2.0D - 1.0D) * 0.1F);
            }
        }
    }
    @Override
    protected void onCollision(HitResult hitResult) {
        Entity entity = hitResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) hitResult).getEntity() : null;
        if (!this.getWorld().isClient) {
            Box box = this.getBoundingBox().expand(3.0, 2.0, 3.0);
            List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, box);
            Entity entity2 = this.getEffectCause();
            StatusEffectInstance sticky = new StatusEffectInstance(ModEffectsAndPotions.STICKY,80,0);
            StatusEffectInstance mining_fatigue = new StatusEffectInstance(StatusEffects.MINING_FATIGUE,80,9);
            StatusEffectInstance weakness = new StatusEffectInstance(StatusEffects.WEAKNESS,80,9);
            for (LivingEntity livingEntity : list) {
                double d;
                if (!livingEntity.isAffectedBySplashPotions() || !((d = this.squaredDistanceTo(livingEntity)) < 16.0))
                    continue;
                double e = livingEntity == entity ? 1.0 : 1.0 - Math.sqrt(d) / 4.0;
                StatusEffect STICKY = sticky.getEffectType();
                StatusEffect MINING_FATIGUE = mining_fatigue.getEffectType();
                StatusEffect WEAKNESS = weakness.getEffectType();
                int i2 = sticky.mapDuration(i -> (int) (e * (double) i + 0.5));
                int i3 = mining_fatigue.mapDuration(i -> (int) (e * (double) i + 0.5));
                int i4 = weakness.mapDuration(i -> (int) (e * (double) i + 0.5));
                StatusEffectInstance Sticky = new StatusEffectInstance(STICKY, i2, sticky.getAmplifier(), sticky.isAmbient(), true);
                StatusEffectInstance MiningFatigue = new StatusEffectInstance(MINING_FATIGUE, i3, mining_fatigue.getAmplifier(), mining_fatigue.isAmbient(), false);
                StatusEffectInstance Weakness = new StatusEffectInstance(WEAKNESS, i4, weakness.getAmplifier(), weakness.isAmbient(), false);
                if (Sticky.isDurationBelow(20)||
                        MiningFatigue.isDurationBelow(20)||
                        Weakness.isDurationBelow(20)) continue;
                livingEntity.addStatusEffect(Sticky, entity2);
                livingEntity.addStatusEffect(MiningFatigue, entity2);
                livingEntity.addStatusEffect(Weakness, entity2);
            }
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
        super.onCollision(hitResult);
    }
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(getDamageSources().thrown(this, getOwner()), 0);
        playSound(ModSounds.ENTITY_BUTTER_HIT, 1.0f, (random.nextFloat() - random.nextFloat()) * 2.f + 1.f);
    }
    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!getWorld().isClient) {
            getWorld().sendEntityStatus(this, (byte) 3);
            playSound(ModSounds.ENTITY_BUTTER_HIT, 1.0f, (random.nextFloat() - random.nextFloat()) * 2.f + 1.f);
            discard();
        }
    }
}
