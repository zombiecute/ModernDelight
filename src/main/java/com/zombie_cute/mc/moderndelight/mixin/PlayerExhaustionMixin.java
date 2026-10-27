package com.zombie_cute.mc.moderndelight.mixin;

import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.moderndelight.effects.custom.GutFloraEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerExhaustionMixin {
    @ModifyVariable(method = "addExhaustion(F)V", at = @At("HEAD"), argsOnly = true)
    private float moderndelight$reduceExhaustion(float exhaustion) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        StatusEffectInstance gutFlora = player.getStatusEffect(ModEffectsAndPotions.GUT_FLORA);
        if (gutFlora == null || exhaustion <= 0.0F) {
            return exhaustion;
        }
        float multiplier = GutFloraEffect.getExhaustionMultiplier(gutFlora.getAmplifier());
        return exhaustion * multiplier;
    }
}
