package com.zombie_cute.mc.moderndelight.effects.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.UUID;

public class WellFedEffect extends StatusEffect {
    private static final String MAX_HEALTH_UUID = "c5f3e4d6-7b80-4192-8c13-2d3e4f506172";

    public WellFedEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFFC640);
        this.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, MAX_HEALTH_UUID,
                2.0, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient()) {
            int level = amplifier + 1;
            int duration = 20 * 3 * level;
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, duration, 0));
        }
        super.onApplied(entity, attributes, amplifier);
    }
}
