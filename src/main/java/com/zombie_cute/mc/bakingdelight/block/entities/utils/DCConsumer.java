package com.zombie_cute.mc.bakingdelight.block.entities.utils;

import net.minecraft.item.ItemStack;

public interface DCConsumer {
    Power getPower(ItemStack stack);
    void addPower(ItemStack stack,int value);
    void reducePower(ItemStack stack,int value);
}
