package com.zombie_cute.mc.moderndelight.block.kitchenware.clay_pot;

import com.zombie_cute.mc.moderndelight.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class ClayPotBlockEntity extends BlockEntity implements GeoBlockEntity {
    public ClayPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLAY_POT_BLOCK_ENTITY, pos, state);
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
