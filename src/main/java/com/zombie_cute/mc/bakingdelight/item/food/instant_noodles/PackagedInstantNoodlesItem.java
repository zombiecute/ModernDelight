package com.zombie_cute.mc.bakingdelight.item.food.instant_noodles;

import com.zombie_cute.mc.bakingdelight.components.ModComponents;
import com.zombie_cute.mc.bakingdelight.util.InstantNoodleUtil;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class PackagedInstantNoodlesItem extends Item {
    public PackagedInstantNoodlesItem() {
        super(new Settings());
    }

    @Override
    public Text getName(ItemStack stack) {
        List<String> nbt = stack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS, new ArrayList<>());
        SpecialIngredient special = InstantNoodleUtil.getSpecialIngredient(nbt);
        if (special != null){
            return Text.translatable(special.toTranslationKey());
        } else if (InstantNoodleUtil.isUnhealthy(nbt)){
            return Text.translatable(TextUtil.NOODLE_UNHEALTHY);
        }
        return super.getName(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        InstantNoodleUtil.setToolTipFromNoodles(stack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS,new ArrayList<>()), tooltip);
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

}
