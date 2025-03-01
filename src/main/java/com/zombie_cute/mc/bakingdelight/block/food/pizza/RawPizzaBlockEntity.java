package com.zombie_cute.mc.bakingdelight.block.food.pizza;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class RawPizzaBlockEntity extends AbstractPizzaBlockEntity {
    public RawPizzaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RAW_PIZZA_BLOCK_ENTITY, pos, state);
    }
}
