package com.zombie_cute.mc.bakingdelight.enchantment;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.enchantment.custom.FineGrindingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final RegistryKey<Enchantment> FINE_GRINDING = of("fine_grinding");
    public static final MapCodec<FineGrindingEnchantment> FINE_GRINDING_EFFECT = register("fine_grinding",FineGrindingEnchantment.CODEC);

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = Identifier.of(ModernDelightMain.MOD_ID, path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }
    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(ModernDelightMain.MOD_ID, id), codec);
    }
    public static void registerModEnchantments(){
        ModernDelightMain.LOGGER.info("Registering Mod Enchantments for " + ModernDelightMain.MOD_ID);
    }
}
