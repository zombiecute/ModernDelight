package com.zombie_cute.mc.bakingdelight.block.kitchenware.decor;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.ShowAbleItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class WoodenPlateBlock extends BlockWithEntity implements Waterloggable {
    public WoodenPlateBlock() {
        super(AbstractBlock.Settings.copy(Blocks.REPEATER).sounds(BlockSoundGroup.WOOD).mapColor(MapColor.BROWN).nonOpaque());
        this.setDefaultState(getStateManager().getDefaultState().with(WATERLOGGED, false).with(SHOWING_ITEM,ShowAbleItems.EMPTY));
    }
    public static final MapCodec<WoodenPlateBlock> CODEC = createCodec((s) -> new WoodenPlateBlock());
    protected MapCodec<? extends WoodenPlateBlock> getCodec() {
        return CODEC;
    }
    public static final VoxelShape SHAPED = Block.createCuboidShape(1,0,1,15,1,15);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<ShowAbleItems> SHOWING_ITEM = EnumProperty.of("showing_item", ShowAbleItems.class);

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.WOODEN_PLATE)));

        } else {
            tooltip.add(TextUtil.getShiftText(false));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
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
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, SHOWING_ITEM);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof WoodenPlateBlockEntity){
                ItemScatterer.spawn(world ,pos, (WoodenPlateBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (world.isClient()){
            return ActionResult.SUCCESS;
        }
        if (world.getBlockEntity(pos) instanceof WoodenPlateBlockEntity blockEntity){
            blockEntity.use(world, state, player, hand);
        }
        return ActionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenPlateBlockEntity(pos,state);
    }
}
