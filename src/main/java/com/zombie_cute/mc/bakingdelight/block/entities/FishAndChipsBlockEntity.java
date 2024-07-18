package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.custom.FishAndChipsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.RenderUtils;

public class FishAndChipsBlockEntity extends BlockEntity implements GeoBlockEntity {
    public FishAndChipsBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FISH_AND_CHIPS_BLOCK_ENTITY, pos, state);
    }
    private static final RawAnimation STATE_1 = RawAnimation.begin().thenLoop("state1");
    private static final RawAnimation STATE_2 = RawAnimation.begin().thenLoop("state2");
    private static final RawAnimation STATE_3 = RawAnimation.begin().thenLoop("state3");
    private static final RawAnimation STATE_4 = RawAnimation.begin().thenLoop("state4");
    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            int i = state.getAnimatable().getCachedState().get(FishAndChipsBlock.BITES);
            switch (i){
                case 1 -> {
                    return state.setAndContinue(STATE_2);
                }
                case 2 -> {
                    return state.setAndContinue(STATE_3);
                }
                case 3 -> {
                    return state.setAndContinue(STATE_4);
                }
                default -> {
                    return state.setAndContinue(STATE_1);
                }
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
