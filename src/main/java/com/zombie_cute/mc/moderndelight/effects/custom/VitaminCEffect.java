package com.zombie_cute.mc.moderndelight.effects.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class VitaminCEffect extends StatusEffect {
    public VitaminCEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xF4C542);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient()) {
            entity.removeStatusEffect(StatusEffects.POISON);
            entity.removeStatusEffect(StatusEffects.WITHER);
        }
        super.onApplied(entity, attributes, amplifier);
    }
}
