package com.zombie_cute.mc.moderndelight.block.multiblock;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/** Invisible cells retain their owner offset in block states, so no extra block entities are needed. */
public class LargeMachinePartBlock extends Block implements Waterloggable {
    // Vanilla integer properties are nonnegative: encode offsets -2..2 as 0..4.
    public static final IntProperty OFFSET_X = IntProperty.of("offset_x", 0, 4);
    public static final IntProperty OFFSET_Y = IntProperty.of("offset_y", 0, 4);
    public static final IntProperty OFFSET_Z = IntProperty.of("offset_z", 0, 4);
    private final LargeMachineBlock ownerBlock;
    private final java.util.Map<BlockPos, BrokenMachine> broken = new java.util.HashMap<>();
    private record BrokenMachine(BlockState state, @Nullable BlockEntity entity) {}

    public LargeMachinePartBlock(LargeMachineBlock ownerBlock) {
        super(FabricBlockSettings.copyOf(ownerBlock).luminance(0).nonOpaque().dynamicBounds()
                .pistonBehavior(PistonBehavior.BLOCK).dropsNothing());
        this.ownerBlock = ownerBlock;
        setDefaultState(getDefaultState().with(Properties.WATERLOGGED, false));
    }

    public int getOffsetRadius() { return 2; }
    protected IntProperty xProperty() { return OFFSET_X; }
    protected IntProperty yProperty() { return OFFSET_Y; }
    protected IntProperty zProperty() { return OFFSET_Z; }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING, xProperty(), yProperty(), zProperty(), Properties.WATERLOGGED);
    }

    public BlockState stateFor(BlockState machineState, BlockPos offset) {
        Direction facing = machineState.contains(Properties.HORIZONTAL_FACING) ? machineState.get(Properties.HORIZONTAL_FACING) : Direction.NORTH;
        return getDefaultState().with(Properties.HORIZONTAL_FACING, facing)
                .with(xProperty(), offset.getX() + getOffsetRadius()).with(yProperty(), offset.getY() + getOffsetRadius())
                .with(zProperty(), offset.getZ() + getOffsetRadius());
    }

    private BlockPos offset(BlockState state) {
        return new BlockPos(state.get(xProperty()) - getOffsetRadius(), state.get(yProperty()) - getOffsetRadius(),
                state.get(zProperty()) - getOffsetRadius());
    }

    public BlockPos getMachinePos(BlockState state, BlockPos pos) {
        return pos.subtract(offset(state));
    }

    public static BlockPos resolveMachinePos(BlockView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof LargeMachinePartBlock part ? part.getMachinePos(state, pos) : pos;
    }

    public boolean belongsTo(BlockState state, BlockPos pos, BlockPos machinePos) {
        return state.isOf(this) && getMachinePos(state, pos).equals(machinePos);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return stateFor(machineState(state).rotate(rotation), offset(state).rotate(rotation))
                .with(Properties.WATERLOGGED, state.get(Properties.WATERLOGGED));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        BlockPos offset = offset(state);
        BlockPos mirrored = switch (mirror) {
            case LEFT_RIGHT -> new BlockPos(offset.getX(), offset.getY(), -offset.getZ());
            case FRONT_BACK -> new BlockPos(-offset.getX(), offset.getY(), offset.getZ());
            default -> offset;
        };
        return stateFor(machineState(state).mirror(mirror), mirrored)
                .with(Properties.WATERLOGGED, state.get(Properties.WATERLOGGED));
    }

    private BlockState machineState(BlockState state) {
        BlockState owner = ownerBlock.getDefaultState();
        return owner.contains(Properties.HORIZONTAL_FACING)
                ? owner.with(Properties.HORIZONTAL_FACING, state.get(Properties.HORIZONTAL_FACING)) : owner;
    }

    @Nullable
    public BlockState findMachine(BlockView world, BlockPos pos, BlockState state) {
        BlockState owner = world.getBlockState(getMachinePos(state, pos));
        return owner.isOf(ownerBlock) ? owner : null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        BlockState owner = findMachine(world, pos, state);
        return owner == null ? VoxelShapes.empty() : ownerBlock.getPartShape(owner, offset(state));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getOutlineShape(state, world, pos, context);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        // Lighting runs off-thread: it must not resolve the owner through ServerWorld.getBlockState.
        return !state.get(Properties.WATERLOGGED);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return state.get(Properties.WATERLOGGED) ? 1 : 0;
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockState owner = findMachine(world, pos, state);
        return owner == null ? ActionResult.PASS : owner.onUse(world, player, hand,
                hit.withBlockPos(getMachinePos(state, pos)));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockState owner = findMachine(world, pos, state);
        return owner == null ? ItemStack.EMPTY
                : owner.getBlock().getPickStack(world, getMachinePos(state, pos), owner);
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        BlockState owner = findMachine(world, pos, state);
        return owner == null ? super.calcBlockBreakingDelta(state, player, world, pos)
                : owner.calcBlockBreakingDelta(player, world, getMachinePos(state, pos));
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        // Mining calls this after the owner has been removed. Its identity and facing remain in this state.
        BlockState owner = machineState(state);
        return owner.getDroppedStacks(builder.add(LootContextParameters.BLOCK_STATE, owner));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockState owner = findMachine(world, pos, state);
        if (!world.isClient && owner != null) {
            BlockPos root = getMachinePos(state, pos);
            broken.put(pos.toImmutable(), new BrokenMachine(owner, world.getBlockEntity(root)));
            owner.getBlock().onBreak(world, root, owner, player);
            // Creative mining does not call afterBreak.
            if (player.isCreative()) broken.remove(pos);
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
                           @Nullable BlockEntity entity, ItemStack tool) {
        BrokenMachine owner = broken.remove(pos);
        if (owner == null) {
            super.afterBreak(world, player, pos, state, entity, tool);
        } else {
            owner.state.getBlock().afterBreak(world, player, getMachinePos(state, pos), owner.state, owner.entity, tool);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient && !newState.isOf(this)) {
            BlockState owner = findMachine(world, pos, state);
            if (owner != null && ownerBlock.getPartShapes(owner).containsKey(offset(state))) {
                // The broken part supplies the owner's loot; the owner only releases its inventory here.
                world.breakBlock(getMachinePos(state, pos), false);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient) {
            world.scheduleBlockTick(pos, this, 1);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                               WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.scheduleBlockTick(pos, this, 1);
        if (state.get(Properties.WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos machinePos = getMachinePos(state, pos);
        if (!world.isChunkLoaded(machinePos)) {
            world.scheduleBlockTick(pos, this, 20);
            return;
        }
        BlockState owner = findMachine(world, pos, state);
        if (owner == null || !ownerBlock.getPartShapes(owner).containsKey(offset(state))) {
            world.setBlockState(pos, state.getFluidState().getBlockState(), Block.NOTIFY_ALL);
        }
    }

    /** The Stirling flywheel reaches four cells from its furnace. */
    public static final class Wide extends LargeMachinePartBlock {
        private static final IntProperty X = IntProperty.of("offset_x", 0, 8);
        private static final IntProperty Y = IntProperty.of("offset_y", 0, 8);
        private static final IntProperty Z = IntProperty.of("offset_z", 0, 8);
        public Wide(LargeMachineBlock machine) { super(machine); }
        @Override public int getOffsetRadius() { return 4; }
        @Override protected IntProperty xProperty() { return X; }
        @Override protected IntProperty yProperty() { return Y; }
        @Override protected IntProperty zProperty() { return Z; }
    }
}
