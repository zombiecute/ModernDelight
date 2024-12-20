package com.zombie_cute.mc.bakingdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.custom.abstracts.AbstractPizzaBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.PizzaWIPBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PizzaWIPBlock extends AbstractPizzaBlock {
    public static final IntProperty CRAFT_STATE = IntProperty.of("craft_state",0,5);
    public PizzaWIPBlock() {
        super();
        setDefaultState(this.getStateManager().getDefaultState()
                .with(CRAFT_STATE, 0));
    }
    public static final MapCodec<PizzaWIPBlock> CODEC = createCodec((settings -> new PizzaWIPBlock()));
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CRAFT_STATE);
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PizzaWIPBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof PizzaWIPBlockEntity container){
            container.onUse(player, state, world);
            return ActionResult.SUCCESS;
        }
        return ActionResult.SUCCESS;
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PizzaWIPBlockEntity){
                ItemScatterer.spawn(world ,pos, (PizzaWIPBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
