package com.zombie_cute.mc.bakingdelight.mixin;

import com.zombie_cute.mc.bakingdelight.item.tools.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperEnhancementMixin {
    @Inject(method = "getPossibleEntries", at = @At("RETURN"))
    private static void getPossibleEntriesEnhanced(int power, ItemStack stack, boolean treasureAllowed,
                                                   CallbackInfoReturnable<List<EnchantmentLevelEntry>> returnCallback) {
        List<EnchantmentLevelEntry> possibleEnchantmentList = returnCallback.getReturnValue();

        if (stack.getItem() instanceof WhiskItem) {
            WhiskItem.ALLOWED_ENCHANTMENTS.stream()
                    .filter(enchantment -> containsEnchantment(possibleEnchantmentList, enchantment))
                    .forEach(enchantment -> addEntry(possibleEnchantmentList, power, enchantment));
            possibleEnchantmentList.removeIf(enchantmentLevelEntry ->
                    !WhiskItem.ALLOWED_ENCHANTMENTS.contains(enchantmentLevelEntry.enchantment));
        } else if (stack.getItem() instanceof KnifeItem) {
            KnifeItem.ALLOWED_ENCHANTMENTS.stream()
                    .filter(enchantment -> containsEnchantment(possibleEnchantmentList, enchantment))
                    .forEach(enchantment -> addEntry(possibleEnchantmentList, power, enchantment));
            possibleEnchantmentList.removeIf(enchantmentLevelEntry ->
                    !KnifeItem.ALLOWED_ENCHANTMENTS.contains(enchantmentLevelEntry.enchantment));
        } else if (stack.getItem() instanceof KneadingStickItem) {
            KneadingStickItem.ALLOWED_ENCHANTMENTS.stream()
                    .filter(enchantment -> containsEnchantment(possibleEnchantmentList, enchantment))
                    .forEach(enchantment -> addEntry(possibleEnchantmentList, power, enchantment));
            possibleEnchantmentList.removeIf(enchantmentLevelEntry ->
                    !KneadingStickItem.ALLOWED_ENCHANTMENTS.contains(enchantmentLevelEntry.enchantment));
        } else if (stack.getItem() instanceof CrowbarItem) {
            CrowbarItem.ALLOWED_ENCHANTMENTS.stream()
                    .filter(enchantment -> containsEnchantment(possibleEnchantmentList, enchantment))
                    .forEach(enchantment -> addEntry(possibleEnchantmentList, power, enchantment));
            possibleEnchantmentList.removeIf(enchantmentLevelEntry ->
                    !CrowbarItem.ALLOWED_ENCHANTMENTS.contains(enchantmentLevelEntry.enchantment));
        } else if (stack.getItem() instanceof SpatulaItem) {
            SpatulaItem.ALLOWED_ENCHANTMENTS.stream()
                    .filter(enchantment -> containsEnchantment(possibleEnchantmentList, enchantment))
                    .forEach(enchantment -> addEntry(possibleEnchantmentList, power, enchantment));
            possibleEnchantmentList.removeIf(enchantmentLevelEntry ->
                    !SpatulaItem.ALLOWED_ENCHANTMENTS.contains(enchantmentLevelEntry.enchantment));
        } else if (stack.getItem() instanceof StoneMortarItem) {
            StoneMortarItem.ALLOWED_ENCHANTMENTS.stream()
                    .filter(enchantment -> containsEnchantment(possibleEnchantmentList, enchantment))
                    .forEach(enchantment -> addEntry(possibleEnchantmentList, power, enchantment));
            possibleEnchantmentList.removeIf(enchantmentLevelEntry ->
                    !StoneMortarItem.ALLOWED_ENCHANTMENTS.contains(enchantmentLevelEntry.enchantment));
        }
    }

    @Unique
    private static void addEntry(List<EnchantmentLevelEntry> entries, int power, Enchantment enchantment) {
        for (int level = enchantment.getMaxLevel(); level >= enchantment.getMinLevel(); level--) {
            if (enchantment.getMinPower(level) <= power && power <= enchantment.getMaxPower(level)) {
                entries.add(new EnchantmentLevelEntry(enchantment, level));
                break;
            }
        }
    }

    @Unique
    private static boolean containsEnchantment(List<EnchantmentLevelEntry> entries, Enchantment enchantment) {
        return entries.stream().noneMatch(enchantmentLevelEntry -> enchantmentLevelEntry.enchantment == enchantment);
    }
}
