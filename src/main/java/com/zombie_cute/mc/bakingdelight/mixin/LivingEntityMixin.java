package com.zombie_cute.mc.bakingdelight.mixin;

import com.zombie_cute.mc.bakingdelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "eatFood(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/component/type/FoodComponent;)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"))
    private void eatFood(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        LivingEntity targetEntity = (LivingEntity) (Object) this;
        MiscUtil.applyFoodEffects(stack,targetEntity);
    }
    @Shadow
    public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Inject(method = "travel(Lnet/minecraft/util/math/Vec3d;)V", at = @At("HEAD"))
    private void applyStickyGround(Vec3d movementInput, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        try {
            if (!this.hasStatusEffect(ModEffectsAndPotions.STICKY)) return;
            if (!self.isOnGround()) return;
            self.setVelocity(
                    self.getVelocity().x * 0.001,
                    Math.min(self.getVelocity().y, 0.0),
                    self.getVelocity().z * 0.001
            );
            self.velocityDirty = true;
            self.setJumping(false);
        } catch (Exception ignored) {
        }
    }
}
