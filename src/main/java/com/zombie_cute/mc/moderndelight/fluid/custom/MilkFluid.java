package com.zombie_cute.mc.moderndelight.fluid.custom;

import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.fluid.ModAbstractFluid;
import com.zombie_cute.mc.moderndelight.fluid.ModFluid;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.WorldView;

public abstract class MilkFluid extends ModAbstractFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluid.FLOWING_MILK;
    }

    @Override
    public Fluid getStill() {
        return ModFluid.STILL_MILK;
    }

    @Override
    public Item getBucketItem() {
        return Items.MILK_BUCKET;
    }
    public int getFlowSpeed(WorldView world) {
        return 4;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModBlocks.MILK_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15,getBlockStateLevel(state));
    }
    public static class Flowing extends MilkFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }
    public static class Still extends MilkFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
