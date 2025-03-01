package com.zombie_cute.mc.bakingdelight.enchantment.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

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
}
