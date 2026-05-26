package com.zombie_cute.mc.bakingdelight.util.enums;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

import java.util.Arrays;
import java.util.List;

public enum SpecialIngredient{
    BRAISED_BEEF_NOODLE_SOUP("braised_beef_noodle_soup",
            Ingredient.fromTag(TagKeys.RAW_BEEF),
            Ingredient.ofItems(Items.CARROT),
            Ingredient.fromTag(TagKeys.CABBAGE)),
    STEW_CHICKEN_NOODLE_WITH_MUSHROOM("stewed_chicken_noodle_with_mushroom",
            Ingredient.ofItems(Items.BROWN_MUSHROOM),
            Ingredient.ofItems(Items.CHICKEN),
            Ingredient.fromTag(TagKeys.CABBAGE)),
    TONKOTSU_RAMEN("tonkotsu_ramen",
            Ingredient.fromTag(TagKeys.RAW_PORK),
            Ingredient.ofItems(Items.DRIED_KELP),
            Ingredient.ofItems(Items.EGG));
    final String id;
    final List<Ingredient> ingredients;
    SpecialIngredient(String id, Ingredient... ingredients) {
        this.id = id;
        this.ingredients = Arrays.asList(ingredients);
    }

    public String getId() {
        return id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public String toTranslationKey(){
        return "items." + ModernDelightMain.MOD_ID + ".packaged_instant_noodles." + id;
    }
    public boolean match(SimpleInventory match){
        List<Ingredient> copy = List.copyOf(ingredients);
        for (Ingredient ingredient : copy) {
            boolean foundMatchForIngredient = false;
            // 获取Ingredient中的所有Item
            ItemStack[] ingredientItems = ingredient.getMatchingStacks();
            // 遍历SimpleInventory中的每个ItemStack
            for (int i = 0; i < match.size(); i++) {
                ItemStack stack = match.getStack(i);
                if (stack.isEmpty()) continue; // 如果是空的ItemStack，跳过
                Item item = stack.getItem(); // 获取ItemStack对应的Item
                // 如果SimpleInventory中的ItemStack与Ingredient中的Item匹配，则认为该项通过
                for (ItemStack ingredientItem : ingredientItems) {
                    if (item == ingredientItem.getItem()) {
                        foundMatchForIngredient = true;
                        break; // 找到匹配的Item，跳出循环
                    }
                }
                if (foundMatchForIngredient) {
                    break; // 找到匹配的Ingredient项，跳出SimpleInventory遍历
                }
            }
            // 如果没有找到匹配的物品，说明当前Ingredient没有被完全匹配，返回false
            if (!foundMatchForIngredient) {
                return false;
            }
        }
        // 如果所有的Ingredient项都通过了匹配，返回true
        return true;
    }
}
