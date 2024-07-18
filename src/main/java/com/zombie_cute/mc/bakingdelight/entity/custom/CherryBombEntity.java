package com.zombie_cute.mc.bakingdelight.entity.custom;

import com.zombie_cute.mc.bakingdelight.entity.ModEntities;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.util.ModDamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class CherryBombEntity extends ThrownItemEntity {
    public CherryBombEntity(EntityType<CherryBombEntity> entityType, World world) {
        super(entityType, world);
    }

    public CherryBombEntity(World world, LivingEntity entity) {
        super(ModEntities.CHERRY_BOMB, entity, world);
    }

    public CherryBombEntity(World world, double x, double y, double z) {
        super(ModEntities.CHERRY_BOMB, x, y, z, world);
    }
    @Override
    protected Item getDefaultItem() {
        return ModItems.CHERRY_BOMB;
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
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 1.2f, false, World.ExplosionSourceType.MOB);
            playSound(ModSounds.ENTITY_CHERRY_BOMB_EXPLOSION, 4.0f, (random.nextFloat() - random.nextFloat()) * 2.f + 1.f);
            this.discard();
        }
        super.onCollision(hitResult);
    }
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(ModDamageTypes.of(entity.getWorld(),ModDamageTypes.TURNED_TO_ASHES), 10.5f);
        playSound(ModSounds.ENTITY_CHERRY_BOMB_EXPLOSION, 4.0f, (random.nextFloat() - random.nextFloat()) * 2.f + 1.f);
    }
}
