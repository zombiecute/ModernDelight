package com.zombie_cute.mc.moderndelight.effects;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import com.zombie_cute.mc.moderndelight.effects.custom.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffectsAndPotions {
    public static final StatusEffect STICKY = registerEffect("sticky",new StickyEffect());
    public static final StatusEffect UMAMI = registerEffect("umami",new UmamiEffect());
    public static final StatusEffect MOUTH_AFIRE = registerEffect("mouth_afire",new MouthAfireEffect());
    public static final StatusEffect CREAM_COAT = registerEffect("cream_coat",new CreamCoatEffect());
    public static final StatusEffect FROST_COAT = registerEffect("frost_coat",new FrostCoatEffect());
    public static final StatusEffect WARM_BELLY = registerEffect("warm_belly",new WarmBellyEffect());
    public static final StatusEffect VITAMIN_C = registerEffect("vitamin_c",new VitaminCEffect());
    public static final StatusEffect CRUNCH = registerEffect("crunch",new CrunchEffect());
    public static final StatusEffect WELL_FED = registerEffect("well_fed",new WellFedEffect());
    public static final StatusEffect FOOD_COMA = registerEffect("food_coma",new FoodComaEffect());
    public static final StatusEffect GUT_FLORA = registerEffect("gut_flora",new GutFloraEffect());
    public static final StatusEffect BRAIN_FREEZE = registerEffect("brain_freeze",new BrainFreezeEffect());

    public static final Potion STICKY_POTION = registerPotion("sticky_potion", new Potion(
            new StatusEffectInstance(STICKY, 22 * 20, 0)));
    public static final Potion STICKY_LONG_POTION = registerPotion("sticky_long_potion", new Potion(
            new StatusEffectInstance(STICKY, 42 * 20,0)));
    public static final Potion SQUID_POWER_POTION = registerPotion("squid_power_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,30 * 20,0),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 120 * 20,0)
    ));
    public static final Potion SQUID_POWER_LONG_POTION = registerPotion("squid_power_long_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,60 * 20,0),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 400 * 20,0)
    ));
    public static final Potion SQUID_POWER_STRONG_POTION = registerPotion("squid_power_strong_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,4 * 20,3),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 90 * 20,0)
    ));
    public static final Potion GLOW_SQUID_POWER_POTION = registerPotion("glow_squid_power_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,22 * 20,1),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 180 * 20,0),
            new StatusEffectInstance(StatusEffects.GLOWING,180 * 20,0)
    ));
    public static final Potion GLOW_SQUID_POWER_LONG_POTION = registerPotion("glow_squid_power_long_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,60 * 20,0),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 500 * 20,0),
            new StatusEffectInstance(StatusEffects.GLOWING,8 * 60 * 20,0)
    ));
    public static final Potion GLOW_SQUID_POWER_STRONG_POTION = registerPotion("glow_squid_power_strong_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,8 * 20,3),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 120 * 20,0),
            new StatusEffectInstance(StatusEffects.GLOWING,10 * 60 * 20,0)
    ));
    public static StatusEffect registerEffect(String name, StatusEffect statusEffect){
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(ModernDelightMain.MOD_ID,name),statusEffect);
    }
    public static Potion registerPotion(String name, Potion potion){
        return Registry.register(Registries.POTION,new Identifier(ModernDelightMain.MOD_ID,name),potion);
    }
    public static void registerModEffectsAndPotions(){
    }
}
