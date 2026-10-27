package com.zombie_cute.mc.moderndelight.effects.custom;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class CreamCoatEffect extends StatusEffect {
    private static final String ARMOR_UUID = "a3f1c2d4-5b6e-4f70-8a91-0b1c2d3e4f50";

    public CreamCoatEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xF3E7D3);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, ARMOR_UUID,
                1.0, EntityAttributeModifier.Operation.ADDITION);
    }

    public static float getFallDamageReduction(int amplifier) {
        return Math.min(0.80F + 0.05F * amplifier, 1.0F);
    }
}
