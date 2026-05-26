package com.zombie_cute.mc.bakingdelight.util.enums;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;

public enum CreamFlavor {
    NULL(-1,0xffffff,"null", 0),
    PLAIN(0, 0xefece1,"plain", 2),
    APPLE(1, 0xf7d888,"apple", 3),
    CHERRY(2,0xf97aa8,"cherry", 3),
    CHOCOLATE(3, 0x8b674a,"chocolate", 5),
    GOLDEN_APPLE(4, 0xf7ee36,"golden_apple", 6),
    MATCHA(5, 0x6da42e,"matcha", 3),
    PUMPKIN(6, 0xd09b39,"pumpkin", 4);
    final int id;
    final int color;
    final String name;
    final int hunger;
    public static final String TRANSLATION_KEY = "bakingdelight.flavor";

    CreamFlavor(int id, int color, String name, int hunger){
        this.id = id;
        this.color = color;
        this.name = name;
        this.hunger = hunger;
    }
    public static void addFlavorToFood(ItemStack stack, CreamFlavor creamFlavor){
        NbtCompound nbt = stack.getNbt();
        if (nbt == null){
            nbt = new NbtCompound();
        }
        List<Integer> integers = new ArrayList<>();
        if (nbt.contains("flavor")){
            int[] array = nbt.getIntArray("flavor");
            for(int i : array){
                integers.add(i);
            }
        }
        integers.add(creamFlavor.getId());
        nbt.putIntArray("flavor",integers);
        stack.setNbt(nbt);
    }
    public static int getHungerFromFlavorNBT(NbtCompound nbt){
        if (nbt != null && nbt.contains("flavor")){
            int count = 0;
            int[] array = nbt.getIntArray("flavor");
            for(int i : array){
                CreamFlavor creamFlavor = getFlavorByID(i);
                count += creamFlavor.getHunger();
            }
            return count;
        } else return 0;
    }

    public int getHunger() {
        return hunger;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
    public static CreamFlavor getFlavorByID(int id){
        for (CreamFlavor creamFlavor : CreamFlavor.values()){
            if (creamFlavor.getId() == id){
                return creamFlavor;
            }
        }
        return NULL;
    }
    public static Item getCream(CreamFlavor creamFlavor){
        return switch (creamFlavor){
            case PLAIN -> ModItems.CREAM;
            case APPLE -> ModItems.APPLE_CREAM;
            case CHERRY -> ModItems.CHERRY_CREAM;
            case CHOCOLATE -> ModItems.CHOCOLATE_CREAM;
            case GOLDEN_APPLE -> ModItems.GOLDEN_APPLE_CREAM;
            case MATCHA -> ModItems.MATCHA_CREAM;
            case PUMPKIN -> ModItems.PUMPKIN_CREAM;
            case NULL -> Items.AIR;
        };
    }
    public String getTranslationKey(){
        return "bakingdelight.flavor."+name;
    }
    public int getId() {
        return id;
    }
}
