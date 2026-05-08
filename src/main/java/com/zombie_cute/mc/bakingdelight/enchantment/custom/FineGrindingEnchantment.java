package com.zombie_cute.mc.bakingdelight.enchantment.custom;

import com.zombie_cute.mc.bakingdelight.item.tools.StoneMortarItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class FineGrindingEnchantment extends Enchantment {

    public FineGrindingEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }
    @Override
    public int getMinPower(int level) {
        return 1;
    }
    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof StoneMortarItem;
    }
}
