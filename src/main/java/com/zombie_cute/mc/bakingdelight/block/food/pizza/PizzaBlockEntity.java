package com.zombie_cute.mc.bakingdelight.block.food.pizza;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class PizzaBlockEntity extends AbstractPizzaBlockEntity {
    public PizzaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PIZZA_BLOCK_ENTITY, pos, state);
    }
    public int getHunger(){
        int result = 0;
        for(int i=0; i <PIZZA_INV.size();i++){
            Item item = getStack(i).getItem();
            if (item.isFood()){
                result += Objects.requireNonNull(item.getFoodComponent()).getHunger();
            }
        }
        return result / 3;
    }
}
