package com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.AdvanceFurnaceBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.OvenBlock;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import net.minecraft.block.BlastFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.SmokerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;

public class SterlingEngineBlockEntity extends BlockEntity implements GeoBlockEntity {
    public SterlingEngineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STERLING_ENGINE_BLOCK_ENTITY, pos, state);
    }
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("start").thenLoop("idle");
    private static final RawAnimation STOPPING = RawAnimation.begin().thenPlay("stop");

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private boolean hasStart = false;
    private int ticker = 30;
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            World world1 = state.getAnimatable().getWorld();
            BlockPos pos1 = state.getAnimatable().getPos().down();
            if (world1 != null){
                if (world1.getBlockState(pos1).getBlock() instanceof FurnaceBlock
                        && world1.getBlockState(pos1).get(FurnaceBlock.LIT)){
                    return state.setAndContinue(IDLE);
                } else if (world1.getBlockState(pos1).getBlock() instanceof BlastFurnaceBlock &&
                        world1.getBlockState(pos1).get(BlastFurnaceBlock.LIT)) {
                    return state.setAndContinue(IDLE);
                } else if (world1.getBlockState(pos1).getBlock() instanceof SmokerBlock &&
                        world1.getBlockState(pos1).get(SmokerBlock.LIT)) {
                    return state.setAndContinue(IDLE);
                } else if (world1.getBlockState(pos1).getBlock() instanceof OvenBlock &&
                        world1.getBlockState(pos1).get(OvenBlock.OVEN_BURNING)) {
                    return state.setAndContinue(IDLE);
                } else if (world1.getBlockState(pos1).getBlock() instanceof AdvanceFurnaceBlock &&
                        world1.getBlockState(pos1).get(AdvanceFurnaceBlock.BURNING)) {
                    return state.setAndContinue(IDLE);
                } else if (state.isCurrentAnimation(IDLE)) {
                    return state.setAndContinue(STOPPING);
                } else if (!state.isCurrentAnimation(STOPPING)){
                    return state.setAndContinue(RawAnimation.begin());
                }
            }
            return state.setAndContinue(RawAnimation.begin());
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public static void tick(World world, BlockPos pos, BlockState state, SterlingEngineBlockEntity b) {
        if (world.isClient){
            return;
        }
        boolean small_sound = state.get(SterlingEngineBlock.SMALL_SOUND);
        if (state.get(SterlingEngineBlock.IS_WORKING)){
            if (!b.hasStart){
                b.ticker = 30;
                if (small_sound){
                    world.playSound(null, pos, ModSounds.BLOCK_STERLING_ENGINE_START, SoundCategory.BLOCKS, 0.15f, 1.0f);
                } else {
                    world.playSound(null, pos, ModSounds.BLOCK_STERLING_ENGINE_START, SoundCategory.BLOCKS, 2.3f, 1.0f);
                }
            }
            b.hasStart = true;
            if (b.ticker == 0){
                if (small_sound){
                    if (world.getTime() % 3L == 0){
                        world.playSound(null, pos, ModSounds.BLOCK_STERLING_ENGINE, SoundCategory.BLOCKS, 0.15f, 1.0f);
                    }
                } else {
                    if (world.getTime() % 3L == 0){
                        world.playSound(null, pos, ModSounds.BLOCK_STERLING_ENGINE, SoundCategory.BLOCKS, 2.3f, 1.0f);
                    }
                }
            } else b.ticker--;
        } else {
            if (b.hasStart){
                if (small_sound){
                    world.playSound(null, pos, ModSounds.BLOCK_STERLING_ENGINE_STOP, SoundCategory.BLOCKS, 0.15f, 1.0f);

                } else {
                    world.playSound(null, pos, ModSounds.BLOCK_STERLING_ENGINE_STOP, SoundCategory.BLOCKS, 2.3f, 1.0f);
                }
            }
            b.hasStart = false;
        }
        if (world.getBlockState(pos).getBlock() instanceof SterlingEngineBlock){
            if (world.getBlockState(pos.down()).getBlock() instanceof FurnaceBlock){
                world.setBlockState(pos,state.with(SterlingEngineBlock.IS_WORKING,
                        world.getBlockState(pos.down()).get(FurnaceBlock.LIT)));
            } else if (world.getBlockState(pos.down()).getBlock() instanceof BlastFurnaceBlock){
                world.setBlockState(pos,state.with(SterlingEngineBlock.IS_WORKING,
                        world.getBlockState(pos.down()).get(BlastFurnaceBlock.LIT)));
            } else if (world.getBlockState(pos.down()).getBlock() instanceof SmokerBlock){
                world.setBlockState(pos,state.with(SterlingEngineBlock.IS_WORKING,
                        world.getBlockState(pos.down()).get(SmokerBlock.LIT)));
            } else if (world.getBlockState(pos.down()).getBlock() instanceof OvenBlock){
                world.setBlockState(pos,state.with(SterlingEngineBlock.IS_WORKING,
                        world.getBlockState(pos.down()).get(OvenBlock.OVEN_BURNING)));
            } else if (world.getBlockState(pos.down()).getBlock() instanceof AdvanceFurnaceBlock){
                world.setBlockState(pos,state.with(SterlingEngineBlock.IS_WORKING,
                        world.getBlockState(pos.down()).get(AdvanceFurnaceBlock.BURNING)));
            }
        }
    }
}
