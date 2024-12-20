package com.zombie_cute.mc.bakingdelight.block.custom;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BoxedCherriesBlock extends Block {
    public BoxedCherriesBlock() {
        super(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS));
    }
    public static final String TIP = "bakingdelight.tooltips.boxed_cherries_tip";

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (world.isClient){
            if (player.getStackInHand(hand).getItem().equals(Items.GUNPOWDER)){
                return ActionResult.SUCCESS;
            } else return ActionResult.FAIL;
        }
        if (player.getStackInHand(hand).getItem().equals(Items.GUNPOWDER)){
            if (player.getStackInHand(hand).getCount() >= 32){
                player.getStackInHand(hand).decrement(32);
                world.breakBlock(pos,false);
                world.createExplosion(null,pos.getX(),pos.getY(),pos.getZ(),1.0f, World.ExplosionSourceType.BLOCK);
                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModItems.CHERRY_BOMB,9));
            } else {
                player.sendMessage(Text.translatable(TIP),true);
            }
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}
