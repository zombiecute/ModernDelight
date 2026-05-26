package com.zombie_cute.mc.bakingdelight.block.kitchenware.decor;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CabinetBlock extends BlockWithEntity {
    public CabinetBlock() {
        super(AbstractBlock.Settings.copy(Blocks.DEEPSLATE).sounds(BlockSoundGroup.STONE).nonOpaque());
    }
    public static final MapCodec<CabinetBlock> CODEC = createCodec((s) -> new CabinetBlock());
    protected MapCodec<? extends CabinetBlock> getCodec() {
        return CODEC;
    }
    public CabinetBlock(Settings settings) {
        super(settings);
    }
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
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
            if (blockEntity instanceof CabinetBlockEntity entity){
                for (int i = 0; i< ((Inventory) entity).size(); i++){
                    ItemScatterer.spawn(world ,pos.getX(),pos.getY(),pos.getZ(), ((Inventory) entity).getStack(i));
                }
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
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
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CabinetBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient){
            return ActionResult.SUCCESS;
        } else {
            if (world.getBlockEntity(pos) instanceof CabinetBlockEntity entity){
                world.playSound(null, pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5,
                        SoundEvents.BLOCK_BARREL_OPEN, SoundCategory.BLOCKS,
                        1.0f,world.random.nextFloat()+.5f);
                player.openHandledScreen(entity);
            }
            return ActionResult.CONSUME;
        }
    }
}
