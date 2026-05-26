package com.zombie_cute.mc.bakingdelight.item.tools;

import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Set;

public class WhiskItem extends MiningToolItem {
    public WhiskItem(ToolMaterial material, Settings settings) {
        super(material, TagKeys.WHISK_MINEABLE, settings);
    }
    public static final Set<RegistryKey<Enchantment>> ALLOWED_ENCHANTMENTS = Set.of(Enchantments.VANISHING_CURSE, Enchantments.SHARPNESS,
            Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.LOOTING,
            Enchantments.UNBREAKING, Enchantments.EFFICIENCY, Enchantments.SILK_TOUCH, Enchantments.FORTUNE, Enchantments.MENDING);

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
        for (RegistryKey<Enchantment> key : ALLOWED_ENCHANTMENTS){
            if (enchantment.matchesKey(key))
                return true;
        }
        return super.canBeEnchantedWith(stack, enchantment, context);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.WHISK)));
        }else {
            tooltip.add(TextUtil.getShiftText(false));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
