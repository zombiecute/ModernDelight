package com.zombie_cute.mc.bakingdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.entities.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElectriciansDeskBlock extends BlockWithEntity {
    public ElectriciansDeskBlock() {
        super(AbstractBlock.Settings.copy(Blocks.CHERRY_PLANKS).nonOpaque());
    }
    public static final MapCodec<ElectriciansDeskBlock> CODEC = createCodec((settings -> new ElectriciansDeskBlock()));
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPED = Block.createCuboidShape(0,0,0,16,14,16);

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if(Screen.hasShiftDown()){
            tooltip.add(ModUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.translatable(ModUtil.ELECTRICIANS_DESK_1).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ELECTRICIANS_DESK_2).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ELECTRICIANS_DESK_3).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ELECTRICIANS_DESK_4).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ELECTRICIANS_DESK_5).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ELECTRICIANS_DESK_6).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ELECTRICIANS_DESK_7).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ELECTRICIANS_DESK_8).formatted(Formatting.GOLD));

        } else {
            tooltip.add(ModUtil.getShiftText(false));
        }
        super.appendTooltip(stack, context, tooltip, options);
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
        if (world.isClient){
            super.onStateReplaced(state, world, pos, newState, moved);
        }
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ElectriciansDeskBlockEntity entity){
                for (int i = 0; i< ((Inventory) entity).size()-1; i++){
                    ItemScatterer.spawn(world ,pos.getX(),pos.getY(),pos.getZ(), ((Inventory) entity).getStack(i));
                }
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
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
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ElectriciansDeskBlockEntity(pos,state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient){
            if (world.getBlockEntity(pos) instanceof ElectriciansDeskBlockEntity entity){
                player.openHandledScreen(entity);
            }
        }
        return ActionResult.CONSUME;
    }
}
