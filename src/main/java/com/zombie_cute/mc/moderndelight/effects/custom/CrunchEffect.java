package com.zombie_cute.mc.moderndelight.effects.custom;

import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.moderndelight.util.FoodEffectDispatcher;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.UUID;

public class CrunchEffect extends StatusEffect {
    private static final String ATTACK_DAMAGE_UUID = "b4e2d3c5-6a7f-4081-9b02-1c2d3e4f5061";

    public CrunchEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xD9A441);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE_UUID,
                2.0, EntityAttributeModifier.Operation.ADDITION);
    }

    public static void consumeHunger(LivingEntity attacker, LivingEntity target, World world) {
        int level = FoodEffectDispatcher.getLevel(attacker, ModEffectsAndPotions.CRUNCH);
        if (level <= 0) {
            return;
        }
        world.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.PLAYERS, 0.8F, 0.6F);
        if (attacker instanceof PlayerEntity player) {
            player.addExhaustion(2.0F * level);
        }
    }
}
