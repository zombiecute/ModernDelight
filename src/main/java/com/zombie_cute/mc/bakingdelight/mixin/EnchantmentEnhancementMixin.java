package com.zombie_cute.mc.bakingdelight.mixin;

import com.zombie_cute.mc.bakingdelight.item.tools.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentEnhancementMixin {
    @Inject(method = "isAcceptableItem", at = @At("RETURN"), cancellable = true)
    private void getPossibleEntriesEnhanced(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        boolean canBeEnchanted = false;

        if (stack.getItem() instanceof WhiskItem) {
            for (Enchantment enchantment : WhiskItem.ALLOWED_ENCHANTMENTS) {
                if ((Object)this == enchantment) {
                    canBeEnchanted = true;
                    break;
                }
            }
            cir.setReturnValue(canBeEnchanted);
        } else if (stack.getItem() instanceof KnifeItem) {
            for (Enchantment enchantment : KnifeItem.ALLOWED_ENCHANTMENTS) {
                if ((Object)this == enchantment) {
                    canBeEnchanted = true;
                    break;
                }
            }
            cir.setReturnValue(canBeEnchanted);
        } else if (stack.getItem() instanceof KneadingStickItem) {
            for (Enchantment enchantment : KneadingStickItem.ALLOWED_ENCHANTMENTS) {
                if ((Object)this == enchantment) {
                    canBeEnchanted = true;
                    break;
                }
            }
            cir.setReturnValue(canBeEnchanted);
        } else if (stack.getItem() instanceof CrowbarItem) {
            for (Enchantment enchantment : CrowbarItem.ALLOWED_ENCHANTMENTS) {
                if ((Object)this == enchantment) {
                    canBeEnchanted = true;
                    break;
                }
            }
            cir.setReturnValue(canBeEnchanted);
        } else if (stack.getItem() instanceof SpatulaItem) {
            for (Enchantment enchantment : SpatulaItem.ALLOWED_ENCHANTMENTS) {
                if ((Object)this == enchantment) {
                    canBeEnchanted = true;
                    break;
                }
            }
            cir.setReturnValue(canBeEnchanted);
        } else if (stack.getItem() instanceof StoneMortarItem) {
            for (Enchantment enchantment : StoneMortarItem.ALLOWED_ENCHANTMENTS) {
                if ((Object)this == enchantment) {
                    canBeEnchanted = true;
                    break;
                }
            }
            cir.setReturnValue(canBeEnchanted);
        }
    }
}