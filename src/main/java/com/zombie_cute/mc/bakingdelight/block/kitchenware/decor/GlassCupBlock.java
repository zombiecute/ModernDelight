package com.zombie_cute.mc.bakingdelight.block.kitchenware.decor;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.tick.OrderedTick;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class GlassCupBlock extends Block implements Waterloggable {
    public GlassCupBlock() {
        super(FabricBlockSettings.copyOf(ModBlocks.GLASS_BOWL));
        this.setDefaultState(getStateManager().getDefaultState().with(CUPS, 1).with(WATERLOGGED, false));
    }
    public static final IntProperty CUPS;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape ONE_CUP_SHAPE;
    protected static final VoxelShape TWO_CUPS_SHAPE;
    protected static final VoxelShape THREE_CUPS_SHAPE;
    protected static final VoxelShape FOUR_CUPS_SHAPE;

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()){
            int cups = state.get(CUPS);
            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModBlocks.GLASS_CUP,cups));
        }
        super.onBreak(world, pos, state, player);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable(TextUtil.CAN_PLACE).formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.with(CUPS, Math.min(4, blockState.get(CUPS) + 1));
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
            return Objects.requireNonNull(super.getPlacementState(ctx)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
    }
    protected boolean canPlaceOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return !floor.getCollisionShape(world, pos).getFace(Direction.UP).isEmpty() || floor.isSideSolidFullSquare(world, pos, Direction.UP);
    }
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlaceOnTop(world.getBlockState(blockPos), world, blockPos);
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos,
                                                BlockPos posFrom) {
        if (Boolean.TRUE.equals(state.get(WATERLOGGED))) {
            world.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(CUPS) < 4 || super.canReplace(state, context);
    }
    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClient && world.random.nextFloat() < fallDistance - 0.5f && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512f) {
            world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.4f, 1.6f + world.random.nextFloat() * 0.2f);
            world.breakBlock(pos, true);
        }
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(CUPS)) {
            default -> ONE_CUP_SHAPE;
            case 2 -> TWO_CUPS_SHAPE;
            case 3 -> THREE_CUPS_SHAPE;
            case 4 -> FOUR_CUPS_SHAPE;
        };
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(CUPS)) {
            default -> ONE_CUP_SHAPE;
            case 2 -> TWO_CUPS_SHAPE;
            case 3 -> THREE_CUPS_SHAPE;
            case 4 -> FOUR_CUPS_SHAPE;
        };
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CUPS, WATERLOGGED);
    }

    static {
        CUPS = IntProperty.of("cups",1,4);
        WATERLOGGED = Properties.WATERLOGGED;
        ONE_CUP_SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11, 10, 11);
        TWO_CUPS_SHAPE = Block.createCuboidShape(1, 0.0, 5, 15, 10, 11);
        THREE_CUPS_SHAPE = Block.createCuboidShape(1, 0.0, 1, 15, 10, 15);
        FOUR_CUPS_SHAPE = Block.createCuboidShape(1, 0.0, 1, 15, 10, 15);
    }
}
