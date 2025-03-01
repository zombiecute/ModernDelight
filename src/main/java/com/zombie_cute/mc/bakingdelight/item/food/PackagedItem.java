package com.zombie_cute.mc.bakingdelight.item.food;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PackagedItem extends Item {
    private final Item packageItem;
    public PackagedItem(Item packageItem,Settings settings) {
        super(settings);
        this.packageItem = packageItem;
    }

    public Item getPackageItem() {
        return packageItem;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player){
            if (stack.getCount() == 1){
                player.setStackInHand(player.getActiveHand(), packageItem.getDefaultStack());
            } else {
                player.giveItemStack(packageItem.getDefaultStack());
            }
        }
        return super.finishUsing(stack, world, user);
    }
}
