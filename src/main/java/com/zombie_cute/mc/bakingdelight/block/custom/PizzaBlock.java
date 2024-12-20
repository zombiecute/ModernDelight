package com.zombie_cute.mc.bakingdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.custom.abstracts.AbstractPizzaBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.PizzaBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class PizzaBlock extends AbstractPizzaBlock {
    public static final IntProperty BITES = IntProperty.of("bites",0,6);
    public PizzaBlock(){
        super();
        setDefaultState(this.getStateManager().getDefaultState()
                .with(BITES, 0));
    }
    public static final MapCodec<PizzaBlock> CODEC = createCodec((settings -> new PizzaBlock()));
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            if (tryEat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }
        }
        return tryEat(world, pos, state, player);
    }
    private static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        } else {
            if (world.getBlockEntity(pos) instanceof PizzaBlockEntity entity){
                player.incrementStat(Stats.EAT_CAKE_SLICE);
                player.getHungerManager().add(7, 0.4F);
                int i = state.get(BITES);
                world.emitGameEvent(player, GameEvent.EAT, pos);
                world.playSound(player,pos, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS,2.3f,world.getRandom().nextFloat()+0.6f);
                if (i < 6) {
                    world.setBlockState(pos, state.with(BITES, i + 1), 3);
                } else {
                    world.removeBlock(pos, false);
                    world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
                }
            }
            return ActionResult.SUCCESS;
        }
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PizzaBlockEntity(pos, state);
    }
}
