package com.zombie_cute.mc.moderndelight.mixin;

import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.moderndelight.effects.custom.CreamCoatEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class FallDamageMixin {
    @Inject(method = "computeFallDamage", at = @At("RETURN"), cancellable = true)
    private void moderndelight$reduceCreamCoatFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        StatusEffectInstance effect = entity.getStatusEffect(ModEffectsAndPotions.CREAM_COAT);
        if (effect == null) {
            return;
        }
        float reduction = CreamCoatEffect.getFallDamageReduction(effect.getAmplifier());
        int reduced = Math.round(cir.getReturnValue() * (1.0F - reduction));
        cir.setReturnValue(Math.max(0, reduced));
    }
}
