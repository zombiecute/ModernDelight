package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class InstantNoodleUtil {
    public static void setToolTipFromNoodles(List<String> nbt, List<Text> tooltip) {
        List<ItemStack> items = getStacksFromNbt(nbt);
        if (items != null){
            tooltip.add(Text.translatable(TextUtil.INGREDIENTS).formatted(Formatting.DARK_GRAY));
            for (ItemStack item : items){
                if (!item.isEmpty()){
                    tooltip.add(Text.translatable(item.getTranslationKey()).formatted(Formatting.GRAY));
                }
            }
        }
    }

    public static List<ItemStack> getStacksFromNbt(List<String> nbt){
        List<ItemStack> itemStacks = new ArrayList<>();
        for (String registerKey : nbt){
            Item item = null;
            try {
                item = Registries.ITEM.get(Identifier.of(registerKey));
            } catch (Exception ignored){}
            if (item != null){
                itemStacks.add(item.getDefaultStack());
            }
        }
        if (!itemStacks.isEmpty()){
            return itemStacks;
        }
        return null;
    }

    public static boolean isUnhealthy(List<String> nbt){
        SpecialIngredient special = getSpecialIngredient(nbt);
        if (special == null){
            List<ItemStack> items = getStacksFromNbt(nbt);
            if (items != null){
                for (ItemStack itemStack : items){
                    if (!itemStack.contains(DataComponentTypes.FOOD)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static SpecialIngredient getSpecialIngredient(List<String> nbt){
        List<ItemStack> items = getStacksFromNbt(nbt);
        if (items != null){
            SimpleInventory inventory = new SimpleInventory(items.size());
            for (int i = 0; i < inventory.size(); i++) {
                inventory.setStack(i, items.get(i));
            }
            for (SpecialIngredient ingredient : SpecialIngredient.values()) {
                if (ingredient.match(inventory)) {
                    return ingredient;
                }
            }
        }
        return null;
    }
}
