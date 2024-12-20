package com.zombie_cute.mc.bakingdelight.block.custom;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlackPepperCropBlock extends CropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 6);
    public BlackPepperCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.BLACK_PEPPER_CORN;
    }

    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        int age = state.get(getAgeProperty());
        boolean isMature = age == getMaxAge();
        if (!isMature && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (isMature){
            int quantity = 1 + world.random.nextInt(2);
            Block.dropStack(world, pos, new ItemStack(ModItems.BLACK_PEPPER_CORN, quantity));
            world.setBlockState(pos, state.with(getAgeProperty(), 2));
            player.playSound(SoundEvents.BLOCK_CROP_BREAK, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }else {
            return super.onUse(state, world, pos, player, hit);
        }
    }
}
