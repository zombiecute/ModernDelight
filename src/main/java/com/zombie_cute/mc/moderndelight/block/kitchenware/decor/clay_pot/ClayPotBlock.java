package com.zombie_cute.mc.moderndelight.block.kitchenware.decor.clay_pot;

import com.zombie_cute.mc.moderndelight.block.multiblock.LargeMachineBlock;
import com.zombie_cute.mc.moderndelight.util.block_util.ShapeProfile;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.Nullable;

public class ClayPotBlock extends LargeMachineBlock {
    public static final ShapeProfile COLLISION_PROFILE = ShapeProfile.fixed(VoxelShapes.union(
            createCuboidShape(4, 0, 4, 12, 1, 12),
            createCuboidShape(3, 1, 1, 13, 5, 15),
            createCuboidShape(1, 1, 3, 15, 5, 13),
            createCuboidShape(-1, 3, 6, 17, 5, 10)).simplify());

    public ClayPotBlock() {
        super(FabricBlockSettings.copyOf(Blocks.REPEATER).mapColor(MapColor.BROWN).nonOpaque(), COLLISION_PROFILE);
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
