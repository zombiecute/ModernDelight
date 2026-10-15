package com.zombie_cute.mc.moderndelight.block.kitchenware.clay_pot;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class ClayPotBlock extends BlockWithEntity {
    public ClayPotBlock() {
        super(FabricBlockSettings.copyOf(Blocks.REPEATER).mapColor(MapColor.BROWN).nonOpaque());
    }
    private static final VoxelShape SHAPED = Block.createCuboidShape(0,0,0,16,5,16);
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ClayPotBlockEntity(pos, state);
    }
}
