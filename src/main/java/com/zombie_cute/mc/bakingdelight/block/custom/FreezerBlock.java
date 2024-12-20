package com.zombie_cute.mc.bakingdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.FreezerBlockEntity;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
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
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class FreezerBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty IS_OPEN = BooleanProperty.of("is_open");
    private static final VoxelShape TYPE_WEST = Block.createCuboidShape(2,0,0,15,16,16);
    private static final VoxelShape TYPE_EAST = Block.createCuboidShape(1,0,0,14,16,16);
    private static final VoxelShape TYPE_SOUTH = Block.createCuboidShape(0,0,1,16,16,14);
    private static final VoxelShape TYPE_NORTH = Block.createCuboidShape(0,0,2,16,16,15);
    public static final String FAIL_TO_OPEN = "bakingdelight.freezer_message.fail_to_open";
    public static final MapCodec<FreezerBlock> CODEC = createCodec((FreezerBlock::new));
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(IS_OPEN,false);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if(Screen.hasShiftDown()){
            tooltip.add(ModUtil.getShiftText(true));
            tooltip.add(ModUtil.getAltText(false));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.translatable(ModUtil.FREEZER_1).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.FREEZER_2).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.FREEZER_3).formatted(Formatting.GOLD));
        } else if (Screen.hasAltDown()) {
            tooltip.add(ModUtil.getShiftText(false));
            tooltip.add(ModUtil.getAltText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(ModUtil.getACCom("15"));
        } else {
            tooltip.add(ModUtil.getShiftText(false));
            tooltip.add(ModUtil.getAltText(false));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING,IS_OPEN);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING,rotation.rotate(state.get(FACING)));
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public FreezerBlock(Settings settings) {
        super(settings);
    }
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
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FreezerBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FreezerBlockEntity){
                if (((FreezerBlockEntity) blockEntity).getExperience() != 0){
                    ExperienceOrbEntity xp =
                            new ExperienceOrbEntity(world,pos.getX(),pos.getY(),pos.getZ(),
                                    ((FreezerBlockEntity) blockEntity).getExperience());
                    world.spawnEntity(xp);
                    world.updateComparators(pos,this);
                }
                ItemScatterer.spawn(world ,pos, (FreezerBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, state.with(IS_OPEN, false));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!world.getBlockState(getSideBlock(state, pos)).isAir() && state.get(IS_OPEN)){
            world.scheduleBlockTick(pos,this,1);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient){
            Direction direction = state.get(FACING);
            BlockPos facingBlock = pos;
            BlockPos sideBlock = pos;
            switch (direction){
                case EAST: {
                    facingBlock = pos.east(1);
                    sideBlock = pos.east(1).south(1);
                    break;
                }
                case SOUTH: {
                    facingBlock = pos.south(1);
                    sideBlock = pos.south(1).west(1);
                    break;
                }
                case WEST: {
                    facingBlock = pos.west(1);
                    sideBlock = pos.west(1).north(1);
                    break;
                }
                case NORTH: {
                    facingBlock = pos.north(1);
                    sideBlock = pos.north(1).east(1);
                    break;
                }
            }
            if (!world.getBlockState(getSideBlock(state, pos)).isAir()){
                world.setBlockState(pos, state.with(IS_OPEN, false));
                player.sendMessage(Text.translatable(FAIL_TO_OPEN),true);
            }
            if (state.get(IS_OPEN)){
                if (world.getBlockState(facingBlock).isAir()){
                    if (player.isSneaking()){
                        if (world.getBlockState(sideBlock).isAir()){
                            world.setBlockState(pos,state.with(IS_OPEN, false));
                            Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, ModSounds.BLOCK_FREEZER_CLOSE, SoundCategory.BLOCKS, 1.7f, world.random.nextFloat()+0.8f);
                        } else {
                            player.sendMessage(Text.translatable(FAIL_TO_OPEN),true);
                        }
                    } else {
                        NamedScreenHandlerFactory screenHandlerFactory = ((FreezerBlockEntity) world.getBlockEntity(pos));
                        if (screenHandlerFactory != null){
                            player.openHandledScreen(screenHandlerFactory);
                        }
                    }
                } else {
                    player.sendMessage(Text.translatable(FAIL_TO_OPEN),true);
                }
            } else {
                if (world.getBlockState(facingBlock).isAir() && world.getBlockState(sideBlock).isAir()){
                    world.setBlockState(pos,state.with(IS_OPEN, true));
                    Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, ModSounds.BLOCK_FREEZER_OPEN, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat()+0.8f);
                } else {
                    player.sendMessage(Text.translatable(FAIL_TO_OPEN),true);
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    private static BlockPos getSideBlock(BlockState state, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos sideBlock = pos;
        switch (direction){
            case EAST: {
                sideBlock = pos.east(1).south(1);
                break;
            }
            case SOUTH: {
                sideBlock = pos.south(1).west(1);
                break;
            }
            case WEST: {
                sideBlock = pos.west(1).north(1);
                break;
            }
            case NORTH: {
                sideBlock = pos.north(1).east(1);
                break;
            }
        }
        return sideBlock;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type,ModBlockEntities.FREEZER_ENTITY, FreezerBlockEntity::tick);
    }
}
