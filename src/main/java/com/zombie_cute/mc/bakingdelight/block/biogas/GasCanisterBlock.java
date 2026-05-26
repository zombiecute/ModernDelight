package com.zombie_cute.mc.bakingdelight.block.biogas;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.tick.OrderedTick;
import org.jetbrains.annotations.Nullable;

public class GasCanisterBlock extends BlockWithEntity implements Waterloggable {
    public GasCanisterBlock() {
        super(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque().pistonBehavior(PistonBehavior.DESTROY));
        setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED,false));
    }
    public static final MapCodec<GasCanisterBlock> CODEC = createCodec((s) -> new GasCanisterBlock());
    protected MapCodec<? extends GasCanisterBlock> getCodec() {
        return CODEC;
    }
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }
    private static final VoxelShape SHAPED = Block.createCuboidShape(3,0,3,13,16,13);
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
        return BlockRenderType.MODEL;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GasCanisterBlockEntity(pos, state);
    }
    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        super.onDestroyedByExplosion(world, pos, explosion);
        if (!world.isClient){
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3.0f, true, World.ExplosionSourceType.BLOCK);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (world.getBlockEntity(pos) instanceof GasCanisterBlockEntity blockEntity) {
            if (!world.isClient) {
                ItemStack itemStack = new ItemStack(ModBlocks.GAS_CANISTER_ITEM);
                blockEntity.setStackNbt(itemStack,world.getRegistryManager());
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof GasCanisterBlockEntity blockEntity){
            return blockEntity.getGasValue() * 15 / GasCanisterBlockEntity.getMaxCapacity();
        } else {
            return 0;
        }
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
        return getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER)
                .with(FACING, context.getHorizontalPlayerFacing().getOpposite());
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
    public FluidState getFluidState(BlockState state) {
        return Boolean.TRUE.equals(state.get(WATERLOGGED)) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient){
            if (player.getMainHandStack().getItem().equals(Items.FLINT_AND_STEEL)||
                    player.getOffHandStack().getItem().equals(Items.FLINT_AND_STEEL)||
                    player.getMainHandStack().getItem().equals(Items.FIRE_CHARGE)||
                    player.getOffHandStack().getItem().equals(Items.FIRE_CHARGE)){
                if (world.getBlockEntity(pos) instanceof GasCanisterBlockEntity container) {
                    container.onUse(player, world);
                }
            } else if (MiscUtil.isPlayerHoldingCrowbar(player)){
                world.breakBlock(pos,true);
            } else {
                NamedScreenHandlerFactory screenHandlerFactory = ((GasCanisterBlockEntity) world.getBlockEntity(pos));
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type, ModBlockEntities.GAS_CANISTER_BLOCK_ENTITY, GasCanisterBlockEntity::tick);
    }
}
