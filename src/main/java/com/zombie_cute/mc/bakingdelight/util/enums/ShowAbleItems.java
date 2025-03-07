package com.zombie_cute.mc.bakingdelight.util.enums;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

public enum ShowAbleItems implements StringIdentifiable {
    EMPTY("empty",getIdentifier(Items.AIR)),
    POTATO("potato",getIdentifier(Items.POTATO)),
    POISONOUS_POTATO("poisonous_potato",getIdentifier(Items.POISONOUS_POTATO)),
    SWEET_BERRIES("sweet_berries",getIdentifier(Items.SWEET_BERRIES)),
    COD("cod",getIdentifier(Items.COD)),
    COOKED_COD("cooked_cod",getIdentifier(Items.COOKED_COD)),
    SALMON("salmon",getIdentifier(Items.SALMON)),
    COOKED_SALMON("cooked_salmon",getIdentifier(Items.COOKED_SALMON)),
    APPLE("apple",getIdentifier(Items.APPLE)),
    GOLDEN_APPLE("golden_apple",getIdentifier(Items.GOLDEN_APPLE)),
    ENCHANTED_GOLDEN_APPLE("enchanted_golden_apple",getIdentifier(Items.ENCHANTED_GOLDEN_APPLE));

    final String name;
    final Identifier id;
    ShowAbleItems(String  name, Identifier id) {
        this.name = name;
        this.id = id;
    }
    public static Identifier getIdentifier(Item item){
        return Registries.ITEM.getId(item);
    }
    public static ShowAbleItems getValue(Item item){
        Identifier identifier = Registries.ITEM.getId(item);
        for (ShowAbleItems showAbleItems : ShowAbleItems.values()){
            if (showAbleItems.id.toString().equals(identifier.toString())){
                return showAbleItems;
            }
        }
        return EMPTY;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String asString() {
        return name;
    }
}
