package com.zombie_cute.mc.moderndelight.block.food.clay_pot.crab_sausage_vegetable;

import com.zombie_cute.mc.moderndelight.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class CrabSausageVegetableBlockEntity extends BlockEntity implements GeoBlockEntity {
    public CrabSausageVegetableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRAB_SAUSAGE_VEGETABLE_BLOCK_ENTITY, pos, state);
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
