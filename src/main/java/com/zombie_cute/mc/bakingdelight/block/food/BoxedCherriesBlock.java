package com.zombie_cute.mc.bakingdelight.block.food;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BoxedCherriesBlock extends Block {
    public BoxedCherriesBlock() {
        super(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    }
    public static final MapCodec<BoxedCherriesBlock> CODEC = createCodec((s)->new BoxedCherriesBlock());
    protected MapCodec<? extends BoxedCherriesBlock> getCodec() {
        return CODEC;
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (world.isClient){
            if (player.getStackInHand(hand).getItem().equals(Items.GUNPOWDER)){
                return ActionResult.SUCCESS;
            } else return ActionResult.FAIL;
        }
        if (player.getStackInHand(hand).getItem().equals(Items.GUNPOWDER)){
            player.getStackInHand(hand).decrement(1);
            world.breakBlock(pos,false);
            world.createExplosion(null,pos.getX(),pos.getY(),pos.getZ(),3.0f, World.ExplosionSourceType.BLOCK);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}
