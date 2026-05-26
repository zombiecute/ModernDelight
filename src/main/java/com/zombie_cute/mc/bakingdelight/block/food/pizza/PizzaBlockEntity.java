package com.zombie_cute.mc.bakingdelight.block.food.pizza;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class PizzaBlockEntity extends AbstractPizzaBlockEntity {
    public PizzaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PIZZA_BLOCK_ENTITY, pos, state);
    }
    public int getHunger(){
        int result = 0;
        for(int i=0; i <PIZZA_INV.size();i++){
            ItemStack item = getStack(i);
            if (item.contains(DataComponentTypes.FOOD)){
                result += Objects.requireNonNull(item.get(DataComponentTypes.FOOD)).nutrition();
            }
        }
        return result / 3;
    }
}
