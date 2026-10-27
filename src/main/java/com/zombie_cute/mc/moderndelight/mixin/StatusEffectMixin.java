package com.zombie_cute.mc.moderndelight.mixin;

import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.moderndelight.util.FoodEffectDispatcher;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class StatusEffectMixin {
    /**
     * 鲜味：新获得正面效果时延长时长
     */
    @ModifyVariable(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z",
            at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private StatusEffectInstance moderndelight$extendBeneficialEffect(StatusEffectInstance effect) {
        LivingEntity entity = (LivingEntity) (Object) this;
        return FoodEffectDispatcher.addStatusEffect(entity, effect);
    }

    /**
     * 维 C 阻止新的中毒/凋零。
     */
    @Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z",
            at = @At("HEAD"), cancellable = true)
    private void moderndelight$rejectPoisonAndWither(StatusEffectInstance effect,
                                                     net.minecraft.entity.Entity source,
                                                     CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.getWorld().isClient()) {
            return;
        }
        if (entity.hasStatusEffect(ModEffectsAndPotions.VITAMIN_C)
                && (effect.getEffectType() == StatusEffects.POISON
                || effect.getEffectType() == StatusEffects.WITHER)) {
            cir.setReturnValue(false);
        }
    }
}
