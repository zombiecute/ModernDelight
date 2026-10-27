package com.zombie_cute.mc.moderndelight.mixin;

import com.zombie_cute.mc.moderndelight.util.FoodEffectDispatcher;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class FoodEffectMixin {
    @Inject(method = "applyFoodEffects", at = @At("HEAD"))
    private void moderndelight$applyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci) {
        FoodEffectDispatcher.applyFoodEffects(stack, targetEntity);
    }
}
