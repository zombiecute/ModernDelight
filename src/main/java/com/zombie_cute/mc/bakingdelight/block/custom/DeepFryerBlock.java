package com.zombie_cute.mc.bakingdelight.block.custom;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.DeepFryerBlockEntity;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeepFryerBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_OIL = BooleanProperty.of("has_oil");
    public static final BooleanProperty RUNNING = BooleanProperty.of("running");
    public DeepFryerBlock() {
        super(FabricBlockSettings.copyOf(Blocks.BRICKS).nonOpaque());
        setDefaultState(this.getStateManager().getDefaultState()
                .with(HAS_OIL, false).with(RUNNING, false));
    }

    private static final VoxelShape TYPE_WEST = Block.createCuboidShape(1,1,0,15,11,16);
    private static final VoxelShape TYPE_EAST = Block.createCuboidShape(1,1,0,15,11,16);
    private static final VoxelShape TYPE_SOUTH = Block.createCuboidShape(0,1,1,16,11,15);
    private static final VoxelShape TYPE_NORTH = Block.createCuboidShape(0,1,1,16,11,15);
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.WEST){
            return TYPE_WEST;
        } else if (direction == Direction.EAST) {
            return TYPE_EAST;
        } else if (direction == Direction.NORTH){
            return TYPE_NORTH;
        } else {
            return TYPE_SOUTH;
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.WEST){
            return TYPE_WEST;
        } else if (direction == Direction.EAST) {
            return TYPE_EAST;
        } else if (direction == Direction.NORTH){
            return TYPE_NORTH;
        } else {
            return TYPE_SOUTH;
        }
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
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DeepFryerBlockEntity){
                ItemScatterer.spawn(world ,pos, (DeepFryerBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(DeepFryerBlock.HAS_OIL) && state.get(DeepFryerBlock.RUNNING)){
            if (world.random.nextFloat() < 0.1f){
                double d = (double)pos.getX() + world.random.nextDouble();
                double e = (double)pos.getY() +  world.random.nextDouble() * 0.5 + 0.8;
                double f = (double)pos.getZ() +  world.random.nextDouble();
                world.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, d, e, f, 0.0, 0.0, 0.0);
            }
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING,HAS_OIL,RUNNING);
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
        return new DeepFryerBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof DeepFryerBlockEntity entity){
            if (player.isSneaking()){
                player.openHandledScreen(entity);
            } else {
                if (hit.getSide().equals(state.get(FACING))){
                    entity.useOnButton(state, world);
                } else if (hit.getSide().equals(Direction.UP)){
                    entity.onUse(state,world,pos,player);
                } else {
                    player.openHandledScreen(entity);
                }
            }
        }
        return ActionResult.SUCCESS;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){
            tooltip.add(ModUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.translatable(ModUtil.DEEP_FRYER_1).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.DEEP_FRYER_2).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.DEEP_FRYER_3).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.DEEP_FRYER_4).formatted(Formatting.GOLD));
        } else {
            tooltip.add(ModUtil.getShiftText(false));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.DEEP_FRYER_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, state1, blockEntity));
    }
}
