package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.marshala_cheese_ball;

import com.zombie_cute.mc.bakingdelight.block.food.clay_pot.ClayPotFoodBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MarshalaCheeseBallBlock extends ClayPotFoodBlock {
    public MarshalaCheeseBallBlock(int hunger, float saturation){
        super(hunger, saturation);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new  MarshalaCheeseBallBlockEntity(pos, state);
    }
}
