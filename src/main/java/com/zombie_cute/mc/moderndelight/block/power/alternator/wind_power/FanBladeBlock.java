package com.zombie_cute.mc.moderndelight.block.power.alternator.wind_power;

import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.multiblock.LargeMachineBlock;
import com.zombie_cute.mc.moderndelight.util.block_util.ShapeProfile;
import com.zombie_cute.mc.moderndelight.block.multiblock.LargeMachinePartBlock;
import com.zombie_cute.mc.moderndelight.util.TextUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FanBladeBlock extends LargeMachineBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final ShapeProfile COLLISION_PROFILE = ShapeProfile.geo()
            // Keep the shaft and replace the animated blades and hub with their swept volumes.
            .filterCubes((bone, origin) -> !bone.equals("bone"))
            .addDiskInXY(8, 8.5f, 34, 7.5f, 9.5f)
            .addDiskInXY(8, 8.5f, 5, 6.5f, 10.5f)
            .build();

    public FanBladeBlock() {
        super(COLLISION_PROFILE);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.FAN_BLADE)));
        } else {
            tooltip.add(TextUtil.getShiftText(false));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        Direction[] directions = ctx.getPlacementDirections();
        for (Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction2 = direction.getOpposite();
                blockState = blockState.with(FACING, direction2);
                if (canPlaceMachine(blockState, ctx)) {
                    return blockState;
                }
            }
        }
        return null;
    }

    @Override
    protected boolean occupiesCell(BlockState state, BlockPos offset) {
        // The mounting shaft embeds half a model pixel into its backing block.
        return !offset.equals(BlockPos.ORIGIN.offset(state.get(FACING).getOpposite()));
    }
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return !blockState.isAir() && !(blockState.getBlock() instanceof LargeMachinePartBlock)
                && super.canPlaceAt(state, world, pos);
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        // Only losing the backing block detaches the blades. Part placement also sends neighbor updates.
        return direction.getOpposite() == state.get(FACING) && neighborState.isAir() ? Blocks.AIR.getDefaultState() : state;
    }
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING,rotation.rotate(state.get(FACING)));
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FanBladeBlockEntity(pos,state);
    }
}
