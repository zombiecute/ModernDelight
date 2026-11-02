package com.zombie_cute.mc.moderndelight.effects.custom;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class MagneticEffect extends StatusEffect {
    private static final double RANGE = 5.0;
    private static final double RANGE_SQUARED = RANGE * RANGE;
    private static final double PULL_STRENGTH = 0.08;

    public MagneticEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xC557FF);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 5 == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!(entity instanceof PlayerEntity player)) {
            return;
        }
        if (!(player.getWorld() instanceof ServerWorld world)) {
            return;
        }
        // 搜索玩家周围 5 格内的掉落物
         List<ItemEntity> items = world.getEntitiesByClass(
                ItemEntity.class,
                player.getBoundingBox().expand(RANGE),
                item -> item.isAlive() && item.squaredDistanceTo(player) <= RANGE_SQUARED);

        for (ItemEntity item : items) {
            // 以玩家身体稍微靠上的位置作为吸引目标
            Vec3d targetPos = player.getPos().add(0, 0.5, 0);
            Vec3d direction = targetPos.subtract(item.getPos());
            // 避免距离过近时产生异常速度
            if (direction.lengthSquared() < 0.01) {
                continue;
            }
            // 计算吸引方向并施加速度
            Vec3d velocity = direction.normalize().multiply(PULL_STRENGTH);
            item.addVelocity(velocity.x, velocity.y, velocity.z);
        }
    }
}
