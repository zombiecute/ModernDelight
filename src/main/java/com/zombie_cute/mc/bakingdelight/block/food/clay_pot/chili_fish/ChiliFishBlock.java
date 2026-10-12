package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.chili_fish;

import com.zombie_cute.mc.bakingdelight.block.food.clay_pot.ClayPotFoodBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ChiliFishBlock extends ClayPotFoodBlock {
    public ChiliFishBlock(int hunger, float saturation){
        super(hunger, saturation);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ChiliFishBlockEntity(pos, state);
    }
}
