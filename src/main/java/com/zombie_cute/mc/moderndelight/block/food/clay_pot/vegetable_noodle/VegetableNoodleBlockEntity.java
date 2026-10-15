package com.zombie_cute.mc.moderndelight.block.food.clay_pot.vegetable_noodle;

import com.zombie_cute.mc.moderndelight.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class VegetableNoodleBlockEntity extends BlockEntity implements GeoBlockEntity {
    public VegetableNoodleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VEGETABLE_NOODLE_BLOCK_ENTITY, pos, state);
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
