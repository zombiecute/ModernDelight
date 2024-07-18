package com.zombie_cute.mc.bakingdelight.item.custom;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PackagedItem extends Item {
    public PackagedItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player){
            if (stack.getCount() == 1){
                player.setStackInHand(player.getActiveHand(), ModItems.DIRTY_PACKAGING_BAG.getDefaultStack());
            } else {
                player.giveItemStack(ModItems.DIRTY_PACKAGING_BAG.getDefaultStack());
            }
        }
        return super.finishUsing(stack, world, user);
    }
}
