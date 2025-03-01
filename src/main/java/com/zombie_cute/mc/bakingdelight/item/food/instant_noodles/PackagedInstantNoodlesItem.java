package com.zombie_cute.mc.bakingdelight.item.food.instant_noodles;

import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PackagedInstantNoodlesItem extends Item {
    public PackagedInstantNoodlesItem() {
        super(new FabricItemSettings());
    }
    public static SpecialIngredient getSpecialIngredient(ItemStack stack){
        List<Item> items = getStacksFromNbt(stack);
        if (items != null){
            SimpleInventory inventory = new SimpleInventory(items.size());
            for (int i = 0; i < inventory.size(); i++) {
                inventory.setStack(i, items.get(i).getDefaultStack());
            }
            for (SpecialIngredient ingredient : SpecialIngredient.values()) {
                if (ingredient.match(inventory)) {
                    return ingredient;
                }
            }
        }
        return null;
    }
    @Override
    public Text getName(ItemStack stack) {
        SpecialIngredient special = getSpecialIngredient(stack);
        if (special != null){
            return Text.translatable(special.toTranslationKey());
        } else if (isUnhealthy(stack)){
            return Text.translatable(MiscUtil.NOODLE_UNHEALTHY);
        }
        return super.getName(stack);
    }
    public static boolean isUnhealthy(ItemStack noodleItem){
        SpecialIngredient special = getSpecialIngredient(noodleItem);
        if (special == null){
            List<Item> items = getStacksFromNbt(noodleItem);
            if (items != null){
                for (Item item : items){
                    if (!item.isFood()){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        setToolTipFromNoodles(stack, tooltip);
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static void setToolTipFromNoodles(ItemStack noodles, List<Text> tooltip) {
        List<Item> items = getStacksFromNbt(noodles);
        if (items != null){
            tooltip.add(Text.translatable(MiscUtil.PIZZA_INGREDIENTS).formatted(Formatting.DARK_GRAY));
            for (Item item : items){
                if (!item.equals(Items.AIR)){
                    tooltip.add(Text.translatable(item.getTranslationKey()).formatted(Formatting.GRAY));
                }
            }
        }
    }



    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }
    public static List<Item> getStacksFromNbt(ItemStack stack){
        NbtCompound nbt = stack.getSubNbt("instant_noodles_ingredients");
        if (nbt != null) {
            List<Item> itemStacks = new ArrayList<>();
            for (int i = 1; i <= 7;i++){
                if (nbt.contains("ingredients_"+i)){
                    String registerKey = nbt.getString("ingredients_"+i);
                    Item item = null;
                    try {
                        item = Registries.ITEM.get(new Identifier(registerKey));
                    } catch (Exception ignored){}
                    if (item != null){
                        itemStacks.add(item);
                    }
                }
            }
            if (!itemStacks.isEmpty()){
                return itemStacks;
            }
        }
        return null;
    }

}
