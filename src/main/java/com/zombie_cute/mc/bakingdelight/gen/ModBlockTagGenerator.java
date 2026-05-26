package com.zombie_cute.mc.bakingdelight.gen;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        // Whisk Mineable
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(ModBlocks.MASHED_POTATO_BLOCK);
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(Blocks.DIRT);
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(Blocks.DIRT_PATH);
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(Blocks.GRASS_BLOCK);
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(Blocks.SAND);
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(Blocks.RED_SAND);
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(Blocks.SOUL_SAND);
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(Blocks.SOUL_SOIL);
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(Blocks.COARSE_DIRT);
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(Blocks.MYCELIUM);
        getOrCreateTagBuilder(TagKeys.WHISK_MINEABLE).add(Blocks.PODZOL);
        // Mineable Pickaxe
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.OVEN);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.FREEZER);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.DEEP_FRYER);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.BAKING_TRAY);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.ADVANCE_FURNACE);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.GAS_CANISTER);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.BIOGAS_DIGESTER_CONTROLLER);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.BIOGAS_DIGESTER_IO);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.GAS_COOKING_STOVE);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.BURNING_GAS_COOKING_STOVE);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.KITCHEN_UTENSIL_HOLDER);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.CUISINE_TABLE);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.ANDESITE_CABINET);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.DIORITE_CABINET);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.GRANITE_CABINET);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.BASALT_CABINET);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.DEEPSLATE_CABINET);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.BLACKSTONE_CABINET);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.OBSIDIAN_CABINET);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.PHOTOVOLTAIC_GENERATOR);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.AC_DC_CONVERTER);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.FAN_BLADE);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.WIND_TURBINE_CONTROLLER);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.SIMPLE_BATTERY);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.INTERMEDIATE_BATTERY);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.ADVANCE_BATTERY);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.DIMENSION_BATTERY);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.STERLING_ENGINE);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.FARADAY_GENERATOR);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.TESLA_COIL);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.SILICON_BLOCK);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.ELECTRIC_STEAMER);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.ICE_CREAM_MAKER);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.CHARGING_POST);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.JUICE_EXTRACTOR);
        // Mineable Axe
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(ModBlocks.WOODEN_BASIN);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(ModBlocks.ELECTRICIANS_DESK);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(ModBlocks.BOXED_CHERRIES);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(ModBlocks.BAMBOO_COVER);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(ModBlocks.BAMBOO_GRATE);
        // Need Stone Tool
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.CHARGING_POST);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.ICE_CREAM_MAKER);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.ELECTRIC_STEAMER);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.SILICON_BLOCK);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.FARADAY_GENERATOR);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.INTERMEDIATE_BATTERY);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.FAN_BLADE);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.WIND_TURBINE_CONTROLLER);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.FREEZER);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.BAKING_TRAY);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.GAS_CANISTER);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.BIOGAS_DIGESTER_CONTROLLER);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.BIOGAS_DIGESTER_IO);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.GAS_COOKING_STOVE);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.BURNING_GAS_COOKING_STOVE);
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.PHOTOVOLTAIC_GENERATOR);
        // Need Iron Tool
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.ADVANCE_BATTERY);
        // Need Diamond Tool
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.OBSIDIAN_CABINET);
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.DIMENSION_BATTERY);
        // Crops
        getOrCreateTagBuilder(BlockTags.CROPS).add(ModBlocks.BLACK_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.CROPS).add(ModBlocks.GARLIC_CROP);

        getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(ModBlocks.GARLIC_CROP);
        getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(ModBlocks.BLACK_PEPPER_CROP);

        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.BLACK_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.WILD_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.WILD_GARLIC);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.GARLIC_CROP);
        // Heater
        getOrCreateTagBuilder(TagKeys.PASSIVE_BOILER_HEATERS).add(ModBlocks.BURNING_GAS_COOKING_STOVE);
        getOrCreateTagBuilder(TagKeys.HEAT_SOURCES).add(ModBlocks.BURNING_GAS_COOKING_STOVE);
        // Crowbar Destroyable
        getOrCreateTagBuilder(TagKeys.CROWBAR_DESTROYABLE).addTag(BlockTags.PICKAXE_MINEABLE);
        getOrCreateTagBuilder(TagKeys.CROWBAR_DESTROYABLE).addTag(BlockTags.AXE_MINEABLE);
        getOrCreateTagBuilder(TagKeys.CROWBAR_DESTROYABLE).addOptionalTag(Identifier.of("c:glass_panes"));
        getOrCreateTagBuilder(TagKeys.CROWBAR_DESTROYABLE).addOptionalTag(Identifier.of("c:glasses"));
        // Wrench Pickup
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.GLASS_BOWL);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.PHOTOVOLTAIC_GENERATOR);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.AC_DC_CONVERTER);

        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.WIND_TURBINE_CONTROLLER);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.FAN_BLADE);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.STERLING_ENGINE);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.FARADAY_GENERATOR);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.BIOGAS_DIGESTER_CONTROLLER);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.BIOGAS_DIGESTER_IO);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.GAS_COOKING_STOVE);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.BURNING_GAS_COOKING_STOVE);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.DEEP_FRYER);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.DEEP_FRY_BASKET);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.WOODEN_BASIN);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.BAKING_TRAY);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.ICE_CREAM_MAKER);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.JUICE_EXTRACTOR);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.ELECTRIC_STEAMER);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.ADVANCE_FURNACE);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.OVEN);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.CUISINE_TABLE);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.FREEZER);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.CHARGING_POST);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.ELECTRICIANS_DESK);
        getOrCreateTagBuilder(TagKeys.WRENCH_PICKUP).add(ModBlocks.TESLA_COIL);
        // Baking Tray Heat Sources
        getOrCreateTagBuilder(TagKeys.DANGER_BLOCKS).add(Blocks.LAVA);
        getOrCreateTagBuilder(TagKeys.DANGER_BLOCKS).add(Blocks.MAGMA_BLOCK);
        getOrCreateTagBuilder(TagKeys.DANGER_BLOCKS).addOptionalTag(BlockTags.CAMPFIRES);
        getOrCreateTagBuilder(TagKeys.DANGER_BLOCKS).addOptionalTag(TagKeys.HEAT_SOURCES);
        getOrCreateTagBuilder(TagKeys.DANGER_BLOCKS).addOptionalTag(TagKeys.PASSIVE_BOILER_HEATERS);
        // Small Flowers
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(ModBlocks.WILD_GARLIC);
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(ModBlocks.WILD_PEPPER_CROP);
        // Can Pick By Tongs
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(Blocks.CHEST);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(Blocks.FURNACE);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(Blocks.BLAST_FURNACE);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(Blocks.SMOKER);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(Blocks.HOPPER);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(Blocks.SHULKER_BOX);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(Blocks.TRAPPED_CHEST);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(Blocks.BARREL);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.OVEN);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.ADVANCE_FURNACE);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.BAKING_TRAY);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.BIOGAS_DIGESTER_IO);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.AC_DC_CONVERTER);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.DEEP_FRY_BASKET);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.DEEP_FRYER);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.KITCHEN_UTENSIL_HOLDER);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.WOODEN_BASIN);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.WOODEN_PLATE);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.BAMBOO_GRATE);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.ELECTRIC_STEAMER);
        getOrCreateTagBuilder(TagKeys.CAN_PICK).add(ModBlocks.PHOTOVOLTAIC_GENERATOR);

    }
}
