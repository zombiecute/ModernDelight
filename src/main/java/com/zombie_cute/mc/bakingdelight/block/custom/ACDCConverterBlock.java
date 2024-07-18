package com.zombie_cute.mc.bakingdelight.block.custom;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.ACDCConverterBlockEntity;
import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ACDCConverterBlock extends BlockWithEntity {
    public ACDCConverterBlock() {
        super(FabricBlockSettings.copyOf(Blocks.IRON_BARS));
    }
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape TYPE_X = Block.createCuboidShape(2,0,0,14,9,16);
    private static final VoxelShape TYPE_Z = Block.createCuboidShape(0,0,2,16,9,14);
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.WEST){
            return TYPE_X;
        } else if (direction == Direction.EAST) {
            return TYPE_X;
        } else if (direction == Direction.NORTH){
            return TYPE_Z;
        } else {
            return TYPE_Z;
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){
            tooltip.add(ModUtil.getShiftText(true));
            tooltip.add(ModUtil.getAltText(false));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.translatable(ModUtil.ACDCC_1).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ACDCC_2).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ACDCC_3).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ACDCC_4).formatted(Formatting.GOLD));
        } else if (Screen.hasAltDown()) {
            tooltip.add(ModUtil.getShiftText(false));
            tooltip.add(ModUtil.getAltText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(ModUtil.getACCom("0~100"));
            tooltip.add(ModUtil.getACGen("0~100"));
            tooltip.add(ModUtil.getDCCom("0~100"));
            tooltip.add(ModUtil.getDCGen("0~?"));
            tooltip.add(ModUtil.getDCSto("3000"));
        } else {
            tooltip.add(ModUtil.getShiftText(false));
            tooltip.add(ModUtil.getAltText(false));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.WEST){
            return TYPE_X;
        } else if (direction == Direction.EAST) {
            return TYPE_X;
        } else if (direction == Direction.NORTH){
            return TYPE_Z;
        } else {
            return TYPE_Z;
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        if (ModUtil.isCrowbar(player)) {
            Direction dir = state.get(FACING);
            switch (dir){
                case EAST -> world.setBlockState(pos,state.with(FACING,Direction.SOUTH));
                case SOUTH -> world.setBlockState(pos,state.with(FACING,Direction.WEST));
                case WEST -> world.setBlockState(pos,state.with(FACING,Direction.NORTH));
                case NORTH -> world.setBlockState(pos,state.with(FACING,Direction.EAST));
            }
        } else {
            if (world.getBlockEntity(pos) instanceof ACDCConverterBlockEntity entity){
                player.openHandledScreen(entity);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
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
        return BlockRenderType.MODEL;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ACDCConverterBlockEntity(pos,state);
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ACDCConverterBlockEntity){
                ItemScatterer.spawn(world ,pos, (ACDCConverterBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.AC_DC_CONVERTER_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, blockEntity,state1));
    }
}
