package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.custom.AdvanceFurnaceBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.BurningGasCookingStoveBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.OvenBlock;
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
    private static final RawAnimation STOP = RawAnimation.begin();

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private boolean isWorking = false;

    public boolean isWorking() {
        return isWorking;
    }
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
                }
            }
            return state.setAndContinue(STOP);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    public static void tick(World world, BlockPos pos, BlockState state, SterlingEngineBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        if (blockEntity.isWorking && world.getTime() % 60L == 0L){
            world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), ModSounds.BLOCK_STERLING_ENGINE, SoundCategory.BLOCKS,0.8f,1.0f);
        }
        if (world.getBlockState(pos.down()).getBlock() instanceof FurnaceBlock){
            blockEntity.isWorking = world.getBlockState(pos.down()).get(FurnaceBlock.LIT);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof BlastFurnaceBlock){
            blockEntity.isWorking = world.getBlockState(pos.down()).get(BlastFurnaceBlock.LIT);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof SmokerBlock){
            blockEntity.isWorking = world.getBlockState(pos.down()).get(SmokerBlock.LIT);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof OvenBlock){
            blockEntity.isWorking = world.getBlockState(pos.down()).get(OvenBlock.OVEN_BURNING);
        } else if (world.getBlockState(pos.down()).getBlock() instanceof AdvanceFurnaceBlock){
            blockEntity.isWorking = world.getBlockState(pos.down()).get(AdvanceFurnaceBlock.BURNING);
        } else blockEntity.isWorking = world.getBlockState(pos.down()).getBlock() instanceof BurningGasCookingStoveBlock;
    }
}
