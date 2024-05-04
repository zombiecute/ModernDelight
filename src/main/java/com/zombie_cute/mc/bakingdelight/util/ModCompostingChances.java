package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

public class ModCompostingChances {
    public static void registerCompostingChances(){
        CompostingChanceRegistry.INSTANCE.add(ModItems.WHITE_TRUFFLE, 1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BLACK_TRUFFLE, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BLACK_PEPPER_CORN, 0.15f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BLACK_PEPPER_DUST, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.WHEAT_DOUGH.asItem(), 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.MIXED_DOUGH, 0.35f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.WHEAT_FLOUR, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.POTATO_STARCH, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.APPLE_PETAL, 0.1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CHERRY, 0.15f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BUTTER, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CHEESE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.EGG_TART, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.TRUFFLE_EGG_TART, 1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BRAISED_SHRIMP_BALL, 1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.SUNFLOWER_SEED, 0.1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.PUDDING_WIP_1, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.PUDDING_WIP_2, 0.6f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.MATCHA_PUDDING, 0.7f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.APPLE_PUDDING, 0.7f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BUTTER_BREAD_SLICE, 0.6f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BREAD_SLICE, 0.4f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.MOUSSE_WIP, 0.4f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.MATCHA_MOUSSE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.GOLDEN_APPLE_MOUSSE, 1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.PUMPKIN_MOUSSE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CHERRY_MOUSSE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CREAM_MOUSSE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CHOCOLATE_MOUSSE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.MASHED_POTATO_BLOCK.asItem(), 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CRYSTAL_DUMPLING, 1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.SUNFLOWER_SEED_PEEL, 0.05f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.SUNFLOWER_SEED_PULP, 0.15f);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.PIZZA_WIP.asItem(), 0.6f);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.RAW_PIZZA.asItem(), 1f);
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.PIZZA.asItem(), 1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.OIL_IMPURITY, 0.2f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.ROASTED_SUNFLOWER_SEED, 0.4f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.BLUE_ORCHID_FLOWER_CAKE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.EMPTY_CAKE, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CHERRY_CAKE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.LILAC_CAKE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.ORANGE_TULIP_CAKE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.OXEYE_DAISY_CAKE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.PINK_TULIP_CAKE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.ROSE_CAKE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.SUNFLOWER_CAKE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.WHITE_TULIP_CAKE, 0.5f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.WITHER_ROSE_CAKE, 1.0f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.RAW_ONION_RING, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.ONION_RING, 0.6f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.FRIED_MILK_WIP, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.FRIED_MILK, 0.8f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.FRIED_APPLE, 0.7f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.RAW_POTATO_CHIP, 0.1f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.POTATO_CHIP, 0.4f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.CHEESE_BALL, 0.4f);
        CompostingChanceRegistry.INSTANCE.add(ModItems.FRIED_DOUGH_STICK, 0.3f);

    }
}
