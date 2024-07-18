package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.Objects;

public class FanBladeBlockEntity extends BlockEntity implements GeoBlockEntity {
    public FanBladeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FAN_BLADE_BLOCK_ENTITY, pos, state);
    }
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation IDLE_FAST = RawAnimation.begin().thenPlay("start").thenLoop("idle_fast");

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            if (state.getAnimatable().getPos().getY() >= 200 || Objects.requireNonNull(state.getAnimatable().getWorld()).isRaining()) {
                state.setControllerSpeed((float) state.getAnimatable().getPos().getY() / 100);
                return state.setAndContinue(IDLE_FAST);
            } else {
                return state.setAndContinue(IDLE);
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
