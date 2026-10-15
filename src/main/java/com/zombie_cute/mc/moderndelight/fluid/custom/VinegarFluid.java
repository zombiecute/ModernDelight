package com.zombie_cute.mc.moderndelight.fluid.custom;

import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.fluid.ModAbstractFluid;
import com.zombie_cute.mc.moderndelight.fluid.ModFluid;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.WorldView;

public abstract class VinegarFluid extends ModAbstractFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluid.FLOWING_VINEGAR;
    }

    @Override
    public Fluid getStill() {
        return ModFluid.STILL_VINEGAR;
    }

    @Override
    public Item getBucketItem() {
        return ModItems.VINEGAR_BUCKET;
    }
    public int getFlowSpeed(WorldView world) {
        return 4;
    }
    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModBlocks.VINEGAR_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15,getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    public static class Flowing extends VinegarFluid {
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
    public static class Still extends VinegarFluid {
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
