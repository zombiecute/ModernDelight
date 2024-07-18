package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class ModFuels {
    public static void registerFuels(){
        FuelRegistry.INSTANCE.add(ModItems.WOODEN_WHISK, 200);
        FuelRegistry.INSTANCE.add(ModItems.KNEADING_STICK, 200);
        FuelRegistry.INSTANCE.add(ModItems.SUNFLOWER_SEED_PEEL, 40);
        FuelRegistry.INSTANCE.add(ModBlocks.WOODEN_BASIN, 1350);
        FuelRegistry.INSTANCE.add(ModItems.FILTER, 100);
        FuelRegistry.INSTANCE.add(ModItems.PACKAGING_BAG, 100);
        FuelRegistry.INSTANCE.add(ModItems.DIRTY_PACKAGING_BAG, 50);

    }
}
