package com.zombie_cute.mc.bakingdelight.util.block_util.power_util;

import net.minecraft.item.ItemStack;

public interface DCConsumer {
    Power getPower(ItemStack stack);
    void addPower(ItemStack stack,long value);
    void reducePower(ItemStack stack,long value);
}
