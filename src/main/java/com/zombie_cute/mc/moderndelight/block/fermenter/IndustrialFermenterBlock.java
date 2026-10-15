package com.zombie_cute.mc.moderndelight.block.fermenter;

import com.zombie_cute.mc.moderndelight.block.ModBlockEntities;
import com.zombie_cute.mc.moderndelight.util.TextUtil;
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
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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

public class IndustrialFermenterBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape TYPE_WEST_EAST = Block.createCuboidShape(2,0,-4,14,35,20);
    private static final VoxelShape TYPE_NORTH_SOUTH = Block.createCuboidShape(-4,0,2,20,35,14);
    public static final BooleanProperty IS_OPENING = BooleanProperty.of("is_opening");
    public static final BooleanProperty IS_CLOSING = BooleanProperty.of("is_closing");
    public static final BooleanProperty IS_RUNNING = BooleanProperty.of("is_running");

    public IndustrialFermenterBlock() {
        super(FabricBlockSettings.copyOf(Blocks.IRON_BARS));
        setDefaultState(getStateManager().getDefaultState().with(IS_OPENING, false).with(IS_RUNNING, false).with(IS_CLOSING,false));
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(TextUtil.getAltText(false));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.INDUSTRIAL_FERMENTER)));
        } else if (Screen.hasAltDown()) {
            tooltip.add(TextUtil.getShiftText(false));
            tooltip.add(TextUtil.getAltText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(TextUtil.getACCom("30+"));
        } else {
            tooltip.add(TextUtil.getShiftText(false));
            tooltip.add(TextUtil.getAltText(false));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(IS_OPENING,false).with(IS_RUNNING,false).with(IS_CLOSING,false);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.WEST || direction == Direction.EAST) {
            return TYPE_WEST_EAST;
        } else {
            return TYPE_NORTH_SOUTH;
        }
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.WEST || direction == Direction.EAST) {
            return TYPE_WEST_EAST;
        } else {
            return TYPE_NORTH_SOUTH;
        }
    }
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, IS_OPENING, IS_RUNNING, IS_CLOSING);
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING,rotation.rotate(state.get(FACING)));
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof IndustrialFermenterBlockEntity){
                ItemScatterer.spawn(world ,pos, (IndustrialFermenterBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        if (world.getBlockEntity(pos) instanceof IndustrialFermenterBlockEntity blockEntity){
            NamedScreenHandlerFactory screenHandlerFactory = ((IndustrialFermenterBlockEntity) world.getBlockEntity(pos));
            if (screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
                blockEntity.setOpening();
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }
    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IndustrialFermenterBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.INDUSTRIAL_FERMENTER_BLOCK_ENTITY,
                IndustrialFermenterBlockEntity::tick);
    }
}
