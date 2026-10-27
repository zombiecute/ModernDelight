package com.zombie_cute.mc.moderndelight.effects.custom;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class GutFloraEffect extends StatusEffect {
    public GutFloraEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x9CCB86);
    }
    public static float getExhaustionMultiplier(int amplifier) {
        int level = amplifier + 1;
        return Math.max(0.5F, 0.75F - 0.05F * (level - 1));
    }
}
