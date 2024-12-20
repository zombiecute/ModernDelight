package com.zombie_cute.mc.bakingdelight.fluid.custom;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.fluid.ModAbstractFluid;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.WorldView;

public abstract class CreamFluid extends ModAbstractFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluid.FLOWING_CREAM;
    }

    @Override
    public Fluid getStill() {
        return ModFluid.STILL_CREAM;
    }

    @Override
    public Item getBucketItem() {
        return ModItems.CREAM_BUCKET;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 100;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModBlocks.CREAM_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15,getBlockStateLevel(state));
    }
    public static class Flowing extends CreamFluid{
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        protected int getMaxFlowDistance(WorldView world) {
            return 6;
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
    public static class Still extends CreamFluid {
        @Override
        protected int getMaxFlowDistance(WorldView world) {
            return 6;
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return 5;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
