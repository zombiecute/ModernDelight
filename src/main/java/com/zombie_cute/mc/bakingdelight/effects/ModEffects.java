package com.zombie_cute.mc.bakingdelight.effects;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.effects.custom_effects.StickyEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> STICKY = registerEffect("sticky",
            new StickyEffect().addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    Identifier.of(Bakingdelight.MOD_ID,"sticky"), -0.10f,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static RegistryEntry<StatusEffect> registerEffect(String name, StatusEffect statusEffect){
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Bakingdelight.MOD_ID,name),statusEffect);
    }
    public static void registerModEffects(){
        Bakingdelight.LOGGER.info("Registering Mod Effects for " + Bakingdelight.MOD_ID);
    }
}
