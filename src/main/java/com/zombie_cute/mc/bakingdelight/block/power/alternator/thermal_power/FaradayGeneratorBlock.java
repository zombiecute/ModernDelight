package com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FaradayGeneratorBlock extends BlockWithEntity {
    public FaradayGeneratorBlock() {
        super(AbstractBlock.Settings.copy(Blocks.IRON_BARS));
    }
    public static final MapCodec<FaradayGeneratorBlock> CODEC = createCodec((settings -> new FaradayGeneratorBlock()));
    protected MapCodec<? extends FaradayGeneratorBlock> getCodec() {
        return CODEC;
    }
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPED_SOUTH = Block.createCuboidShape(2,0,0,14,16,16);
    private static final VoxelShape SHAPED_NORTH = Block.createCuboidShape(2,0,0,14,16,16);
    private static final VoxelShape SHAPED_EAST = Block.createCuboidShape(0,0,2,16,16,14);
    private static final VoxelShape SHAPED_WEST = Block.createCuboidShape(0,0,2,16,16,14);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
        return switch (dir) {
            case SOUTH -> SHAPED_SOUTH;
            case EAST -> SHAPED_EAST;
            case WEST -> SHAPED_WEST;
            default -> SHAPED_NORTH;
        };
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
        return switch (dir) {
            case SOUTH -> SHAPED_SOUTH;
            case EAST -> SHAPED_EAST;
            case WEST -> SHAPED_WEST;
            default -> SHAPED_NORTH;
        };
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(TextUtil.getAltText(false));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.FARADAY_GENERATOR)));
        } else if (Screen.hasAltDown()) {
            tooltip.add(TextUtil.getShiftText(false));
            tooltip.add(TextUtil.getAltText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(TextUtil.getACGen(String.valueOf(FaradayGeneratorBlockEntity.getEnergyEfficiency())));
        } else {
            tooltip.add(TextUtil.getShiftText(false));
            tooltip.add(TextUtil.getAltText(false));
        }
        super.appendTooltip(stack, context, tooltip, options);
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING,rotation.rotate(state.get(FACING)));
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        if (MiscUtil.isPlayerHoldingCrowbar(player)){
            Direction dir = state.get(FACING);
            switch (dir){
                case EAST -> world.setBlockState(pos,state.with(FACING,Direction.SOUTH));
                case SOUTH -> world.setBlockState(pos,state.with(FACING,Direction.WEST));
                case WEST -> world.setBlockState(pos,state.with(FACING,Direction.NORTH));
                case NORTH -> world.setBlockState(pos,state.with(FACING,Direction.EAST));
            }
            world.playSound(null,pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.8f);
        } else if (world.getBlockEntity(pos) instanceof FaradayGeneratorBlockEntity entity){
            player.openHandledScreen(entity);
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FaradayGeneratorBlockEntity(pos,state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type, ModBlockEntities.FARADAY_GENERATOR_BLOCK_ENTITY, FaradayGeneratorBlockEntity::tick);
    }
}
