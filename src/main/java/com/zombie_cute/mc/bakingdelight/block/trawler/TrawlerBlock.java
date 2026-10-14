package com.zombie_cute.mc.bakingdelight.block.trawler;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TrawlerBlock extends BlockWithEntity {
    public TrawlerBlock() {
        super(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).nonOpaque());
        setDefaultState(getStateManager().getDefaultState().with(UP, true).with(RISING,false).with(FALLING,false));
    }
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty RISING = BooleanProperty.of("rising");
    public static final BooleanProperty FALLING = BooleanProperty.of("falling");

    private static final VoxelShape SHAPED = Block.createCuboidShape(0,-3,0,16,36,16);
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.TRAWLER)));
        } else {
            tooltip.add(TextUtil.getShiftText(false));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TrawlerBlockEntity entity){
                for (int i = 0; i< entity.size(); i++){
                    ItemScatterer.spawn(world ,pos.getX(),pos.getY(),pos.getZ(), ((Inventory) entity).getStack(i));
                }
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING,UP,RISING,FALLING);
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING,rotation.rotate(state.get(FACING)));
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TrawlerBlockEntity(pos,state);
    }
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos below = pos.down();
        return world.getFluidState(below).isOf(Fluids.WATER) && world.getFluidState(below).isStill();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        checkWaterFlow(world, pos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        if (world.getBlockEntity(pos) instanceof TrawlerBlockEntity entity){
            if (MiscUtil.isPlayerHoldingCrowbar(player)){
                Direction dir = state.get(FACING);
                switch (dir){
                    case EAST -> world.setBlockState(pos,state.with(FACING,Direction.SOUTH));
                    case SOUTH -> world.setBlockState(pos,state.with(FACING,Direction.WEST));
                    case WEST -> world.setBlockState(pos,state.with(FACING,Direction.NORTH));
                    case NORTH -> world.setBlockState(pos,state.with(FACING,Direction.EAST));
                }
                world.playSound(null,pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.8f);
            } else entity.onUse(state, world, pos, player);
        }
        return ActionResult.CONSUME;
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        checkWaterFlow(world, pos);
    }
    private boolean isHorizontalWaterFlowing(WorldView world, BlockPos pos) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos neighborPos = pos.offset(direction);
            FluidState fluid = world.getFluidState(neighborPos);
            if (fluid.isIn(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

    private void checkWaterFlow(World world, BlockPos pos) {
        if (world.isClient || !world.getBlockState(pos).isOf(this)) {
            return;
        }
        // 下方水源被移除或变成流动水时，也不能继续存在
        FluidState below = world.getFluidState(pos.down());
        boolean validSupport = below.isIn(FluidTags.WATER) && below.isStill();
        if (!validSupport || isHorizontalWaterFlowing(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.TRAWLER_BLOCK_ENTITY, TrawlerBlockEntity::tick);
    }
}
