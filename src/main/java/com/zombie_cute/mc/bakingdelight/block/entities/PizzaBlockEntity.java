package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.abstracts.AbstractPizzaBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class PizzaBlockEntity extends AbstractPizzaBlockEntity {
    public PizzaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PIZZA_BLOCK_ENTITY, pos, state);
    }

}
