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

public class GarlicCropBlock extends CropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 5);
    public GarlicCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.GARLIC;
    }

    @Override
    public int getMaxAge() {
        return 5;
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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int age = state.get(getAgeProperty());
        boolean isMature = age == getMaxAge();
        if (!isMature && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        } else if (isMature){
            int quantity = 1 + world.random.nextInt(2);
            Block.dropStack(world, pos, new ItemStack(ModItems.GARLIC, quantity));
            world.setBlockState(pos, state.with(getAgeProperty(), 0));
            player.playSound(SoundEvents.BLOCK_CROP_BREAK, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }
}
