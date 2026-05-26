package com.zombie_cute.mc.bakingdelight.enchantment.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record FineGrindingEnchantment(EnchantmentLevelBasedValue amount) implements EnchantmentEntityEffect {
    public static final MapCodec<FineGrindingEnchantment> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EnchantmentLevelBasedValue.CODEC.fieldOf("amount").forGetter(FineGrindingEnchantment::amount)
            ).apply(instance, FineGrindingEnchantment::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    public static String getTranslationKey() {
        return "enchantment."+ ModernDelightMain.MOD_ID +".fine_griding";
    }
}
