package com.zombie_cute.mc.bakingdelight.block.power.alternator.wind_power;

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

public class WindTurbineControllerBlock extends BlockWithEntity {
    public WindTurbineControllerBlock() {
        super(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).nonOpaque());
    }
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final MapCodec<WindTurbineControllerBlock> CODEC = createCodec((settings -> new WindTurbineControllerBlock()));
    @Override
    protected MapCodec<? extends WindTurbineControllerBlock> getCodec() {
        return CODEC;
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
    private static final VoxelShape X = Block.createCuboidShape(1,0,0,15,16,16);
    private static final VoxelShape Z = Block.createCuboidShape(0,0,1,16,16,15);
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)){
            case NORTH, SOUTH -> Z;
            default -> X;
        };
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)){
            case NORTH, SOUTH -> Z;
            default -> X;
        };
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
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(TextUtil.getAltText(false));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.WTC)));

        } else if (Screen.hasAltDown()) {
            tooltip.add(TextUtil.getShiftText(false));
            tooltip.add(TextUtil.getAltText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(TextUtil.getACGen("0~"+(int)((float) 319 / 3 * WindTurbineControllerBlockEntity.getMultiplier())));
        } else {
            tooltip.add(TextUtil.getShiftText(false));
            tooltip.add(TextUtil.getAltText(false));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WindTurbineControllerBlockEntity(pos, state);
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
        } else if (world.getBlockEntity(pos) instanceof WindTurbineControllerBlockEntity entity){
            player.openHandledScreen(entity);
        }
        return ActionResult.SUCCESS;
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type, ModBlockEntities.WIND_TURBINE_CONTROLLER_BLOCK_ENTITY, WindTurbineControllerBlockEntity::tick);
    }
}
