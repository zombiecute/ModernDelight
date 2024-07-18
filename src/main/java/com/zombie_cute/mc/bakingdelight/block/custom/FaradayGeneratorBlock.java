package com.zombie_cute.mc.bakingdelight.block.custom;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.FaradayGeneratorBlockEntity;
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

public class FaradayGeneratorBlock extends BlockWithEntity {
    public FaradayGeneratorBlock() {
        super(FabricBlockSettings.copyOf(Blocks.IRON_BARS));
    }
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPED_SOUTH = Block.createCuboidShape(2,0,0,14,20,16);
    private static final VoxelShape SHAPED_NORTH = Block.createCuboidShape(2,0,0,14,20,16);
    private static final VoxelShape SHAPED_EAST = Block.createCuboidShape(0,0,2,16,20,14);
    private static final VoxelShape SHAPED_WEST = Block.createCuboidShape(0,0,2,16,20,14);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
        return switch (dir) {
            case SOUTH -> SHAPED_SOUTH;
            case EAST -> SHAPED_EAST;
            case WEST -> SHAPED_WEST;
            default -> SHAPED_NORTH;
        };
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
        return switch (dir) {
            case SOUTH -> SHAPED_SOUTH;
            case EAST -> SHAPED_EAST;
            case WEST -> SHAPED_WEST;
            default -> SHAPED_NORTH;
        };
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){
            tooltip.add(ModUtil.getShiftText(true));
            tooltip.add(ModUtil.getAltText(false));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.translatable(ModUtil.FARADAY_GENERATOR_1).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.FARADAY_GENERATOR_2).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.FARADAY_GENERATOR_3).formatted(Formatting.GOLD));
        } else if (Screen.hasAltDown()) {
            tooltip.add(ModUtil.getShiftText(false));
            tooltip.add(ModUtil.getAltText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(ModUtil.getACGen("100"));
        } else {
            tooltip.add(ModUtil.getShiftText(false));
            tooltip.add(ModUtil.getAltText(false));
        }
        super.appendTooltip(stack, world, tooltip, options);
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

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        if (world.getBlockEntity(pos) instanceof FaradayGeneratorBlockEntity entity){
            player.openHandledScreen(entity);
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FaradayGeneratorBlockEntity(pos,state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.FARADAY_GENERATOR_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
