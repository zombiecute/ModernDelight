package com.zombie_cute.mc.bakingdelight.block.power.batteries;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class DimensionBatteryBlock extends AbstractBatteryBlock {
    public DimensionBatteryBlock(Settings settings) {
        super(settings);
    }
    public static final MapCodec<DimensionBatteryBlock> CODEC = createCodec((DimensionBatteryBlock::new));
    @Override
    protected MapCodec<? extends DimensionBatteryBlock> getCodec() {
        return CODEC;
    }
    @Override
    public long getMaxPower(){
        return 300000;
    }
    @Override
    protected Block getBlock() {
        return ModBlocks.DIMENSION_BATTERY;
    }
    private static final VoxelShape SHAPED = Block.createCuboidShape(1,0,1,15,16,15);
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BatteryBlockEntity(pos,state,getMaxPower());
    }
}
