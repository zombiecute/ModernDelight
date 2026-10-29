package com.zombie_cute.mc.moderndelight.mixin;

import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MovementEffectMixin {
    @Inject(method = "jump()V", at = @At("HEAD"), cancellable = true)
    private void moderndelight$preventStickyJump(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasStatusEffect(ModEffectsAndPotions.STICKY)) {
            ci.cancel();
        }
    }

    @Inject(method = "setSprinting(Z)V", at = @At("HEAD"), cancellable = true)
    private void moderndelight$preventFoodComaSprinting(boolean sprinting, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (sprinting && entity instanceof PlayerEntity
                && entity.hasStatusEffect(ModEffectsAndPotions.FOOD_COMA)) {
            ci.cancel();
        }
    }
}
