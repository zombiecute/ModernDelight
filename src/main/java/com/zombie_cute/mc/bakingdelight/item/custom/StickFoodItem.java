package com.zombie_cute.mc.bakingdelight.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class StickFoodItem extends Item {
    public StickFoodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player){
            if (stack.getCount() == 1){
                player.setStackInHand(player.getActiveHand(), Items.STICK.getDefaultStack());
            } else {
                player.giveItemStack(Items.STICK.getDefaultStack());
            }
        }
        return super.finishUsing(stack, world, user);
    }
}
