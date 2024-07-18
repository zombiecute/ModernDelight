package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> ELECTROSHOCK = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE, new Identifier(Bakingdelight.MOD_ID, "electroshock"));
    public static final RegistryKey<DamageType> TURNED_TO_ASHES = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE, new Identifier(Bakingdelight.MOD_ID, "turned_to_ashes"));
    public static final RegistryKey<DamageType> SCALDED = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE, new Identifier(Bakingdelight.MOD_ID, "scalded"));
    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
}
