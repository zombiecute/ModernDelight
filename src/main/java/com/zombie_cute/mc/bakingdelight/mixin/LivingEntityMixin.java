package com.zombie_cute.mc.bakingdelight.mixin;

import com.zombie_cute.mc.bakingdelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "applyFoodEffects", at = @At("HEAD"))
    private void applyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci) {
        MiscUtil.applyFoodEffects(stack,targetEntity);
    }
    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Inject(method = "travel", at = @At("HEAD"))
    private void applyStickyGround(Vec3d movementInput, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!this.hasStatusEffect(ModEffectsAndPotions.STICKY)) return;
        if (!self.isOnGround()) return;
        double scale = 0.01;
        Vec3d scaledInput = movementInput.multiply(scale);
        self.setVelocity(
                self.getVelocity().x * 0.001,
                Math.min(self.getVelocity().y, 0.0),
                self.getVelocity().z * 0.001
        );
        self.velocityDirty = true;
        self.setJumping(false);
    }
}
