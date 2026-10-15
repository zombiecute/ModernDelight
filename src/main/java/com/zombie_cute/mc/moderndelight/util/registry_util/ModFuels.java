package com.zombie_cute.mc.moderndelight.util.registry_util;

import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class ModFuels {
    public static void registerFuels(){
        FuelRegistry.INSTANCE.add(ModItems.KNEADING_STICK, 200);
        FuelRegistry.INSTANCE.add(ModItems.SUNFLOWER_SEED_PEEL, 60);
        FuelRegistry.INSTANCE.add(ModBlocks.WOODEN_BASIN, 1350);
        FuelRegistry.INSTANCE.add(ModBlocks.WOODEN_PLATE, 60);
        FuelRegistry.INSTANCE.add(ModItems.FILTER, 160);
        FuelRegistry.INSTANCE.add(ModItems.PACKAGING_BAG, 100);
        FuelRegistry.INSTANCE.add(ModItems.DIRTY_PACKAGING_BAG, 120);
        FuelRegistry.INSTANCE.add(ModItems.MULTIFUNCTIONAL_WRAPPING_PAPER, 100);
        FuelRegistry.INSTANCE.add(ModItems.DIRTY_WRAPPING_PAPER, 80);
        FuelRegistry.INSTANCE.add(ModItems.PINECONE, 200);

    }
}
