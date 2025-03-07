package com.zombie_cute.mc.bakingdelight.mixin;

import com.zombie_cute.mc.bakingdelight.item.food.SeasoningItem;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
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
        NbtCompound nbt = stack.getSubNbt("modern_delight_seasoning");
        if (nbt != null){
            tooltip.add(Text.translatable(MiscUtil.SEASONING_ADDED).formatted(Formatting.DARK_GRAY));
            for (int i = 1; i <= SeasoningItem.getMaxSeasoning(); i++){
                if (nbt.contains("seasoning_"+i)){
                    String registerKey = nbt.getString("seasoning_"+i);
                    Item item = null;
                    try {
                        item = Registries.ITEM.get(new Identifier(registerKey));
                    } catch (Exception ignored){}
                    if (item != null){
                        tooltip.add(Text.translatable(item.getTranslationKey()).formatted(Formatting.GRAY));
                    }
                }
            }
        }
    }
}
