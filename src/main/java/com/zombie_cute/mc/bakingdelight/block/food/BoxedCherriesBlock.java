package com.zombie_cute.mc.bakingdelight.block.food;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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
        super(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS));
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
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
