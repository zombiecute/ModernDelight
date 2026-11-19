package com.zombie_cute.mc.moderndelight.block.power.batteries;

import com.zombie_cute.mc.moderndelight.block.multiblock.LargeMachineBlock;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class AdvanceBatteryBlock extends AbstractBatteryBlock {
    public AdvanceBatteryBlock(Settings settings) {
        super(settings);
    }

    @Override
    public long getMaxPower() {
        return 50000;
    }

    @Override
    protected Block getBlock() {
        return ModBlocks.ADVANCE_BATTERY;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BatteryBlockEntity(pos,state,getMaxPower());
    }
}
