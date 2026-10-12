package com.zombie_cute.mc.bakingdelight.mixin;

import com.zombie_cute.mc.bakingdelight.item.food.SeasoningItem;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "appendTooltip", at = @At("HEAD"))
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        NbtCompound seasoning = stack.getSubNbt("modern_delight_seasoning");
        if (seasoning != null){
            tooltip.add(Text.translatable(TextUtil.SEASONING_ADDED).formatted(Formatting.DARK_GRAY));
            for (int i = 1; i <= SeasoningItem.getMaxSeasoning(); i++){
                if (seasoning.contains("seasoning_"+i)){
                    String registerKey = seasoning.getString("seasoning_"+i);
                    Item item = null;
                    try {
                        item = Registries.ITEM.get(new Identifier(registerKey));
                    } catch (Exception ignored){}
                    if (item != null){
                        tooltip.addAll(TextUtil.generateToolTip(Text.translatable(item.getTranslationKey()),11184810));
                    }
                }
            }
        }
        NbtCompound quality = stack.getNbt();
        if (quality != null && quality.contains("ferment_quality")) {
            MutableText text = Text.translatable(TextUtil.FERMENTING_QUALITY).formatted(Formatting.DARK_GRAY);
            text.append(" ");
            int qualityValue = quality.getInt("ferment_quality");
            switch (qualityValue) {
                case -1 -> text.append(Text.translatable(TextUtil.QUALITY_NEG_1).formatted(Formatting.DARK_RED));
                case 0 -> text.append(Text.translatable(TextUtil.QUALITY_0).formatted(Formatting.GOLD));
                case 1 -> text.append(Text.translatable(TextUtil.QUALITY_1).formatted(Formatting.DARK_GREEN));
                case 2 -> text.append(Text.translatable(TextUtil.QUALITY_2).formatted(Formatting.GREEN));
            }
            tooltip.add(text);
        }
    }
}
