package com.zombie_cute.mc.bakingdelight.mixin;

import com.zombie_cute.mc.bakingdelight.components.ModComponents;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "appendTooltip", at = @At("HEAD"))
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
        List<String> seasoningList = stack.getOrDefault(ModComponents.SEASONING_ITEMS, new ArrayList<>());
        if (!seasoningList.isEmpty()) {
            tooltip.add(Text.translatable(TextUtil.SEASONING_ADDED).formatted(Formatting.DARK_GRAY));
            for (String idString : seasoningList) {
                try {
                    Item item = Registries.ITEM.get(Identifier.of(idString));
                    tooltip.addAll(TextUtil.generateToolTip(Text.translatable(item.getTranslationKey()), 11184810));
                } catch (Exception ignored) {}
            }
        }
    }
}
