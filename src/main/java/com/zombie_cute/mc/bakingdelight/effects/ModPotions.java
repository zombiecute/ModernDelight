package com.zombie_cute.mc.bakingdelight.effects;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static final Potion SQUID_POWER_POTION = registerPotion("squid_power_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,30 * 20,0),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 60 * 20,0)
    ));
    public static final Potion SQUID_POWER_LONG_POTION = registerPotion("squid_power_long_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,60 * 20,0),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 90 * 20,0)
    ));
    public static final Potion SQUID_POWER_STRONG_POTION = registerPotion("squid_power_strong_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,4 * 20,3),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 30 * 20,0)
    ));
    public static final Potion GLOW_SQUID_POWER_POTION = registerPotion("glow_squid_power_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,22 * 20,1),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 90 * 20,0),
            new StatusEffectInstance(StatusEffects.GLOWING,180 * 20,0)
    ));
    public static final Potion GLOW_SQUID_POWER_LONG_POTION = registerPotion("glow_squid_power_long_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,60 * 20,0),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 180 * 20,0),
            new StatusEffectInstance(StatusEffects.GLOWING,8 * 60 * 20,0)
    ));
    public static final Potion GLOW_SQUID_POWER_STRONG_POTION = registerPotion("glow_squid_power_strong_potion", new Potion(
            new StatusEffectInstance(StatusEffects.REGENERATION,7 * 20,3),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 60 * 20,0),
            new StatusEffectInstance(StatusEffects.GLOWING,10 * 60 * 20,0)
    ));
    public static Potion registerPotion(String name, Potion potion){
        return Registry.register(Registries.POTION,Identifier.of(Bakingdelight.MOD_ID,name),potion);
    }
    public static void registerModPotions(){
        Bakingdelight.LOGGER.info("Registering Mod Potions for " + Bakingdelight.MOD_ID);
    }
}
