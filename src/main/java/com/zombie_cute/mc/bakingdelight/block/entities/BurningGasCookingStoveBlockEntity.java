package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.BurningGasCookingStoveBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.GasCanisterBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.GasCookingStoveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BurningGasCookingStoveBlockEntity extends BlockEntity {
    public BurningGasCookingStoveBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BURNING_GAS_COOKING_STOVE_BLOCK_ENTITY, pos, state);
    }
    public void tick(World world, BlockPos pos,BlockState state) {
        if (world.isClient){
            return;
        }
        if (world.random.nextFloat() < 0.15f && world.getTime() % 240L == 0L){
            if (world.getBlockState(pos.up()).getBlock() instanceof LeveledCauldronBlock){
                int level = world.getBlockState(pos.up()).get(LeveledCauldronBlock.LEVEL);
                if (level > 1){
                    world.setBlockState(pos.up(),world.getBlockState(pos.up()).with(LeveledCauldronBlock.LEVEL,level-1));
                    world.playSound(null,pos.getX(),pos.getY(),pos.getZ(),SoundEvents.BLOCK_LAVA_EXTINGUISH,SoundCategory.BLOCKS,1.0f,1.0f);
                } else world.setBlockState(pos.up(), Blocks.CAULDRON.getDefaultState());
            }
        }
        if (world.getTime() % 20L == 0L){
            world.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f,
                    SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS,
                    1.0f, world.random.nextFloat()+0.5f);
        }
        boolean hasGas = false;
        Direction[] directions = {Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH};

        for (Direction direction : directions) {
            BlockPos neighborPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(neighborPos);

            if (neighborState.getBlock() instanceof GasCanisterBlock) {
                if (neighborState.get(GasCanisterBlock.FACING) == direction.getOpposite()) {
                    BlockEntity blockEntity = world.getBlockEntity(neighborPos);
                    if (blockEntity instanceof GasCanisterBlockEntity entity && entity.getGasValue() != 0) {
                        hasGas = true;
                        entity.reduceGas();
                        break;
                    }
                }
            }
        }
        if (!hasGas){
            world.playSound(null,
                    pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f,
                    SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS,
                    1.0f, world.random.nextFloat()+0.5f);
            if (state.get(BurningGasCookingStoveBlock.HAS_BRACKET)){
                world.setBlockState(pos, ModBlocks.GAS_COOKING_STOVE.getDefaultState().with(GasCookingStoveBlock.HAS_BRACKET,true));
            } else {
                world.setBlockState(pos, ModBlocks.GAS_COOKING_STOVE.getDefaultState());
            }
        }

    }

}
