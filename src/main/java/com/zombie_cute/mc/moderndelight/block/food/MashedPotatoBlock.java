package com.zombie_cute.mc.moderndelight.block.food;

import com.zombie_cute.mc.moderndelight.util.block_util.ModelShapes;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class MashedPotatoBlock extends Block {
    public MashedPotatoBlock(Settings settings) {
        super(settings.dynamicBounds());
        setDefaultState(this.getStateManager().getDefaultState()
                .with(LEVEL, 9));
    }
    public static IntProperty LEVEL = IntProperty.of("level",1,9);

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ModelShapes.get(state);
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ModelShapes.get(state);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(LEVEL);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient){
            if (player.getStackInHand(hand).getItem().equals(Items.BOWL)){
                int i = state.get(LEVEL);
                world.playSound(null,pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.5f);
                if (player.getStackInHand(hand).getCount() != 1){
                    player.getStackInHand(hand).decrement(1);
                    player.giveItemStack(ModItems.MASHED_POTATO.getDefaultStack());
                } else {
                    player.setStackInHand(hand,ModItems.MASHED_POTATO.getDefaultStack());
                }
                if (i != 1){
                    world.setBlockState(pos,state.with(LEVEL,i-1));
                } else {
                    world.removeBlock(pos, false);
                    world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
                }
                return ActionResult.SUCCESS;
            } else {
                if (!player.canConsume(false)) {
                    return ActionResult.FAIL;
                } else {
                    player.incrementStat(Stats.EAT_CAKE_SLICE);
                    player.getHungerManager().add(1, 0.3F);
                    int i = state.get(LEVEL);
                    world.emitGameEvent(player, GameEvent.EAT, pos);
                    world.playSound(null,pos.getX(),pos.getY(),pos.getZ(),SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS,2.3f,world.getRandom().nextFloat()+0.8f);
                    if (i != 1) {
                        world.setBlockState(pos, state.with(LEVEL, i - 1), 3);
                    } else {
                        world.removeBlock(pos, false);
                        world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
                    }
                    return ActionResult.SUCCESS;
                }
            }
        } else {
            if (player.isCreative()) return ActionResult.SUCCESS;
           if (player.getHungerManager().isNotFull()){
               return ActionResult.SUCCESS;
           } else {
               return ActionResult.FAIL;
           }
        }
    }
}
