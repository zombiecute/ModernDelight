package com.zombie_cute.mc.bakingdelight.block.food;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PackagedFoodBlockItem extends BlockItem {
    private final Item packageItem;
    public PackagedFoodBlockItem(Block block,int hunger, float saturation, Item packageItem) {
        super(block,new FabricItemSettings().maxCount(16).food(
                new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation).build()
        ).recipeRemainder(packageItem));
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
