package com.zombie_cute.mc.moderndelight.block.multiblock;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import com.zombie_cute.mc.moderndelight.util.block_util.ModelShapes;
import com.zombie_cute.mc.moderndelight.util.block_util.ShapeProfile;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.fluid.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** A rendered block entity whose physical volume is split between ordinary block cells. */
public abstract class LargeMachineBlock extends BlockWithEntity {
    private static final Map<LargeMachineBlock, LargeMachinePartBlock> PARTS = new LinkedHashMap<>();
    private final Map<VoxelShape, Map<BlockPos, VoxelShape>> shapes = new ConcurrentHashMap<>();
    private final ShapeProfile collisionProfile;

    protected LargeMachineBlock() {
        this(ModelShapes.VANILLA);
    }

    protected LargeMachineBlock(ShapeProfile collisionProfile) {
        this(FabricBlockSettings.copyOf(Blocks.IRON_BARS), collisionProfile);
    }

    protected LargeMachineBlock(Settings settings) {
        this(settings, ModelShapes.VANILLA);
    }

    protected LargeMachineBlock(Settings settings, ShapeProfile collisionProfile) {
        super(settings.nonOpaque().dynamicBounds().pistonBehavior(PistonBehavior.BLOCK));
        this.collisionProfile = collisionProfile;
    }

    public VoxelShape getMachineShape(BlockState state) { return ModelShapes.get(state, collisionProfile); }

    @Nullable
    public final LargeMachinePartBlock getPartBlock() { return PARTS.get(this); }

    public static Map<LargeMachineBlock, LargeMachinePartBlock> getRegisteredParts() { return Map.copyOf(PARTS); }

    protected boolean occupiesCell(BlockState state, BlockPos offset) { return true; }

    /** Whether an existing block already supplies this part's collision and should be retained. */
    protected boolean canSharePartCell(BlockState state, WorldView world, BlockPos pos, BlockPos offset) { return false; }

    protected boolean allowsWaterParts() { return false; }

    protected final boolean isClearForMachine(WorldView world, BlockPos target, BlockPos owner) {
        return world.getBlockState(target).isAir() || (getPartBlock() != null
                && getPartBlock().belongsTo(world.getBlockState(target), target, owner));
    }

    public final Map<BlockPos, VoxelShape> getPartShapes(BlockState state) {
        return shapes.computeIfAbsent(getMachineShape(state), shape -> {
            Map<BlockPos, VoxelShape> parts = new LinkedHashMap<>();
            if (shape.isEmpty()) return Map.of();
            var bounds = shape.getBoundingBox();
            for (int x = MathHelper.floor(bounds.minX); x < MathHelper.ceil(bounds.maxX); x++) {
                for (int y = MathHelper.floor(bounds.minY); y < MathHelper.ceil(bounds.maxY); y++) {
                    for (int z = MathHelper.floor(bounds.minZ); z < MathHelper.ceil(bounds.maxZ); z++) {
                        VoxelShape part = VoxelShapes.combineAndSimplify(shape.offset(-x, -y, -z),
                                VoxelShapes.fullCube(), BooleanBiFunction.AND);
                        if (!part.isEmpty() && occupiesCell(state, new BlockPos(x, y, z))) {
                            parts.put(new BlockPos(x, y, z), part);
                        }
                    }
                }
            }
            return Map.copyOf(parts);
        });
    }

    public final VoxelShape getPartShape(BlockState state, BlockPos offset) {
        return getPartShapes(state).getOrDefault(offset, VoxelShapes.empty());
    }

    @Override
    public final VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getPartShape(state, BlockPos.ORIGIN);
    }

    @Override
    public final VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getPartShape(state, BlockPos.ORIGIN);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        for (BlockPos offset : getPartShapes(state).keySet()) {
            BlockPos partPos = pos.add(offset);
            if (world.isOutOfHeightLimit(partPos)
                    || (world instanceof World actualWorld && !actualWorld.getWorldBorder().contains(partPos))) {
                return false;
            }
            if (!offset.equals(BlockPos.ORIGIN) && !canReplacePart(world, partPos, pos)
                    && !canSharePartCell(state, world, pos, offset)) {
                return false;
            }
        }
        return true;
    }

    protected final boolean canPlaceMachine(BlockState state, ItemPlacementContext context) {
        BlockPos pos = context.getBlockPos();
        return state.canPlaceAt(context.getWorld(), pos)
                && context.getWorld().doesNotIntersectEntities(null,
                getMachineShape(state).offset(pos.getX(), pos.getY(), pos.getZ()));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = getDefaultState();
        return canPlaceMachine(state, context) ? state : null;
    }

    private boolean canReplacePart(WorldView world, BlockPos partPos, BlockPos machinePos) {
        BlockState existing = world.getBlockState(partPos);
        return getPartBlock().belongsTo(existing, partPos, machinePos)
                || (existing.isReplaceable() && (existing.getFluidState().isEmpty()
                || (allowsWaterParts() && existing.getFluidState().isOf(Fluids.WATER) && existing.getFluidState().isStill())));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onPlaced(world, pos, state, placer, stack);
        if (world instanceof ServerWorld serverWorld) {
            updateParts(serverWorld, pos, state);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (!world.isClient) {
            world.scheduleBlockTick(pos, this, 1);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        updateParts(world, pos, state);
    }

    /** Updates the current footprint without overwriting blocks that obstruct it. */
    public final void updateParts(ServerWorld world, BlockPos pos, BlockState state) {
        if (!world.getBlockState(pos).isOf(this)) {
            return;
        }
        Map<BlockPos, VoxelShape> parts = getPartShapes(state);
        removeParts(world, pos, parts);
        boolean retry = false;
        for (BlockPos offset : parts.keySet()) {
            if (offset.equals(BlockPos.ORIGIN)) {
                continue;
            }
            BlockPos partPos = pos.add(offset);
            if (world.isOutOfHeightLimit(partPos)) {
                continue;
            }
            if (!world.isChunkLoaded(partPos)) {
                retry = true;
                continue;
            }
            if (!canReplacePart(world, partPos, pos)) {
                retry |= !canSharePartCell(state, world, pos, offset);
                continue;
            }
            world.setBlockState(partPos, getPartBlock().stateFor(state, offset)
                    .with(Properties.WATERLOGGED, !world.getFluidState(partPos).isEmpty()), Block.NOTIFY_ALL);
        }
        if (retry) {
            world.scheduleBlockTick(pos, this, 20);
        }
    }

    private void removeParts(World world, BlockPos pos, Map<BlockPos, VoxelShape> retained) {
        if (getPartBlock() == null) return;
        int radius = getPartBlock().getOffsetRadius();
        for (BlockPos partPos : BlockPos.iterate(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
            // Unloaded cells validate their owner when their scheduled tick resumes.
            // Never force a neighboring chunk to load during removal or world shutdown.
            if (!world.isChunkLoaded(partPos) || retained.containsKey(partPos.subtract(pos))) {
                continue;
            }
            BlockState part = world.getBlockState(partPos);
            if (getPartBlock().belongsTo(part, partPos, pos)) {
                world.setBlockState(partPos, part.getFluidState().getBlockState(), Block.NOTIFY_ALL);
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient) {
            if (!newState.isOf(this)) {
                removeParts(world, pos, Map.of());
            } else if (getMachineShape(state) != getMachineShape(newState)
                    || (state.contains(Properties.HORIZONTAL_FACING) && state.get(Properties.HORIZONTAL_FACING) != newState.get(Properties.HORIZONTAL_FACING))) {
                world.scheduleBlockTick(pos, this, 1);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    public static void registerParts() {
        for (Block block : Registries.BLOCK) {
            if (!(block instanceof LargeMachineBlock machine)) continue;
            int radius = 0;
            for (BlockState state : machine.getStateManager().getStates()) {
                for (BlockPos offset : machine.getPartShapes(state).keySet()) {
                    radius = Math.max(radius, Math.max(Math.abs(offset.getX()),
                            Math.max(Math.abs(offset.getY()), Math.abs(offset.getZ()))));
                }
            }
            if (radius > 0) {
                PARTS.put(machine, radius > 2 ? new LargeMachinePartBlock.Wide(machine) : new LargeMachinePartBlock(machine));
            }
        }
        PARTS.forEach((machine, part) -> Registry.register(Registries.BLOCK,
                new Identifier("moderndelight", Registries.BLOCK.getId(machine).getPath() + "_part"), part));
    }
}
