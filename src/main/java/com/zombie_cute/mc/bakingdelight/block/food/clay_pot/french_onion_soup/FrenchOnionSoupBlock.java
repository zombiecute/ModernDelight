package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.french_onion_soup;

import com.zombie_cute.mc.bakingdelight.block.food.clay_pot.ClayPotFoodBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class FrenchOnionSoupBlock extends ClayPotFoodBlock {
    public FrenchOnionSoupBlock(int hunger, float saturation){
        super(hunger, saturation);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FrenchOnionSoupBlockEntity(pos, state);
    }
}
