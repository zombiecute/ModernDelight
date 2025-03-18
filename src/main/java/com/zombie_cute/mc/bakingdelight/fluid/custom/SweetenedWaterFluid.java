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

public abstract class SweetenedWaterFluid extends ModAbstractFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluid.FLOWING_SWEETENED_WATER;
    }

    @Override
    public Fluid getStill() {
        return ModFluid.STILL_SWEETENED_WATER;
    }

    @Override
    public Item getBucketItem() {
        return ModItems.SWEETENED_WATER_BUCKET;
    }
    public int getFlowSpeed(WorldView world) {
        return 4;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModBlocks.SWEENTENED_WATER_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15,getBlockStateLevel(state));
    }
    public static class Flowing extends SweetenedWaterFluid {
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
    public static class Still extends SweetenedWaterFluid {
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
