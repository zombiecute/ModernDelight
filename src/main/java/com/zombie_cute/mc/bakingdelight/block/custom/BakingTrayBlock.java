package com.zombie_cute.mc.bakingdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.BakingTrayBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.entities.BurningGasCookingStoveBlockEntity;
import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.tick.OrderedTick;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BakingTrayBlock extends BlockWithEntity implements Waterloggable{
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public BakingTrayBlock() {
        super(AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR));
        setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED,false));
    }
    public static final MapCodec<BakingTrayBlock> CODEC = createCodec(settings -> new BakingTrayBlock());
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    private static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,4,16);

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BakingTrayBlockEntity(pos, state);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BakingTrayBlockEntity){
                ItemScatterer.spawn(world ,pos, (BakingTrayBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return Boolean.TRUE.equals(state.get(WATERLOGGED)) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
        return getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if(Screen.hasShiftDown()){
            tooltip.add(ModUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.translatable(ModUtil.BAKING_TRAY_1).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.BAKING_TRAY_2).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.BAKING_TRAY_3).formatted(Formatting.GOLD));
        } else {
            tooltip.add(ModUtil.getShiftText(false));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.getBlockEntity(pos) instanceof BakingTrayBlockEntity entity &&
        world.getBlockEntity(pos.down()) instanceof BurningGasCookingStoveBlockEntity){
            if (!entity.getStack(0).isEmpty()){
                world.addParticle(ParticleTypes.SMOKE, pos.getX()+0.25, pos.getY()+0.2, pos.getZ()+0.25, 0.0, 5.0E-4, 0.0);
            }
            if (!entity.getStack(1).isEmpty()){
                world.addParticle(ParticleTypes.SMOKE, pos.getX()+0.25, pos.getY()+0.2, pos.getZ()+0.75, 0.0, 5.0E-4, 0.0);
            }
            if (!entity.getStack(2).isEmpty()){
                world.addParticle(ParticleTypes.SMOKE, pos.getX()+0.75, pos.getY()+0.2, pos.getZ()+0.25, 0.0, 5.0E-4, 0.0);
            }
            if (!entity.getStack(3).isEmpty()){
                world.addParticle(ParticleTypes.SMOKE, pos.getX()+0.75, pos.getY()+0.2, pos.getZ()+0.75, 0.0, 5.0E-4, 0.0);
            }
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (world.getBlockEntity(pos.down()) instanceof BurningGasCookingStoveBlockEntity){
            entity.damage(world.getDamageSources().inFire(), 1.5f);
        }
        super.onSteppedOn(world, pos, state, entity);
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
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof BakingTrayBlockEntity container) {
            container.onUse(player, world);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type,ModBlockEntities.BAKING_TRAY_BLOCK_ENTITY, BakingTrayBlockEntity::tick);
    }
}
