package com.zombie_cute.mc.moderndelight.gen;

import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.multiblock.LargeMachineBlock;
import com.zombie_cute.mc.moderndelight.tag.TagKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
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
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.OVEN);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.FREEZER);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.DEEP_FRYER);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.BAKING_TRAY);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.ADVANCE_FURNACE);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.GAS_CANISTER);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.BIOGAS_DIGESTER_CONTROLLER);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.BIOGAS_DIGESTER_IO);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.GAS_COOKING_STOVE);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.BURNING_GAS_COOKING_STOVE);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.KITCHEN_UTENSIL_HOLDER);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.CUISINE_TABLE);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.ANDESITE_CABINET);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.DIORITE_CABINET);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.GRANITE_CABINET);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.BASALT_CABINET);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.DEEPSLATE_CABINET);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.BLACKSTONE_CABINET);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.OBSIDIAN_CABINET);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.PHOTOVOLTAIC_GENERATOR);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.AC_DC_CONVERTER);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.FAN_BLADE);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.INDUSTRIAL_FERMENTER);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.WIND_TURBINE_CONTROLLER);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.SIMPLE_BATTERY);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.INTERMEDIATE_BATTERY);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.ADVANCE_BATTERY);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.DIMENSION_BATTERY);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.STERLING_ENGINE);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.FARADAY_GENERATOR);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.TESLA_COIL);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.SILICON_BLOCK);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.ELECTRIC_STEAMER);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.ICE_CREAM_MAKER);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.CHARGING_POST);
        addToolTag(BlockTags.PICKAXE_MINEABLE, ModBlocks.JUICE_EXTRACTOR);
        // Mineable Axe
        addToolTag(BlockTags.AXE_MINEABLE, ModBlocks.WOODEN_BASIN);
        addToolTag(BlockTags.AXE_MINEABLE, ModBlocks.ELECTRICIANS_DESK);
        addToolTag(BlockTags.AXE_MINEABLE, ModBlocks.BOXED_CHERRIES);
        addToolTag(BlockTags.AXE_MINEABLE, ModBlocks.BAMBOO_COVER);
        addToolTag(BlockTags.AXE_MINEABLE, ModBlocks.BAMBOO_GRATE);
        // Need Stone Tool
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.CHARGING_POST);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.ICE_CREAM_MAKER);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.ELECTRIC_STEAMER);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.SILICON_BLOCK);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.FARADAY_GENERATOR);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.INTERMEDIATE_BATTERY);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.FAN_BLADE);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.WIND_TURBINE_CONTROLLER);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.FREEZER);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.BAKING_TRAY);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.GAS_CANISTER);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.BIOGAS_DIGESTER_CONTROLLER);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.BIOGAS_DIGESTER_IO);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.GAS_COOKING_STOVE);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.BURNING_GAS_COOKING_STOVE);
        addToolTag(BlockTags.NEEDS_STONE_TOOL, ModBlocks.PHOTOVOLTAIC_GENERATOR);
        // Need Iron Tool
        addToolTag(BlockTags.NEEDS_IRON_TOOL, ModBlocks.ADVANCE_BATTERY);
        // Need Diamond Tool
        addToolTag(BlockTags.NEEDS_DIAMOND_TOOL, ModBlocks.OBSIDIAN_CABINET);
        addToolTag(BlockTags.NEEDS_DIAMOND_TOOL, ModBlocks.DIMENSION_BATTERY);
        // Crops
        getOrCreateTagBuilder(BlockTags.CROPS).add(ModBlocks.BLACK_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.CROPS).add(ModBlocks.GARLIC_CROP);
        getOrCreateTagBuilder(BlockTags.CROPS).add(ModBlocks.CHILI_CROP);
        getOrCreateTagBuilder(BlockTags.CROPS).add(ModBlocks.SOYBEAN_CROP);

        getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(ModBlocks.GARLIC_CROP);
        getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(ModBlocks.BLACK_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(ModBlocks.CHILI_CROP);
        getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(ModBlocks.SOYBEAN_CROP);

        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.BLACK_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.WILD_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.WILD_GARLIC);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.GARLIC_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.CHILI_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.WILD_CHILI_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.SOYBEAN_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.WILD_SOYBEAN);

        getOrCreateTagBuilder(BlockTags.FLOWERS).add(ModBlocks.WILD_CHILI_CROP);
        getOrCreateTagBuilder(BlockTags.FLOWERS).add(ModBlocks.WILD_GARLIC);
        getOrCreateTagBuilder(BlockTags.FLOWERS).add(ModBlocks.WILD_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.FLOWERS).add(ModBlocks.WILD_SOYBEAN);

        getOrCreateTagBuilder(TagKeys.WILD_CROPS).add(ModBlocks.WILD_CHILI_CROP);
        getOrCreateTagBuilder(TagKeys.WILD_CROPS).add(ModBlocks.WILD_GARLIC);
        getOrCreateTagBuilder(TagKeys.WILD_CROPS).add(ModBlocks.WILD_PEPPER_CROP);
        getOrCreateTagBuilder(TagKeys.WILD_CROPS).add(ModBlocks.WILD_SOYBEAN);

        // Heater
        getOrCreateTagBuilder(TagKeys.PASSIVE_BOILER_HEATERS).add(ModBlocks.BURNING_GAS_COOKING_STOVE);
        getOrCreateTagBuilder(TagKeys.HEAT_SOURCES).add(ModBlocks.BURNING_GAS_COOKING_STOVE);
        // Crowbar Destroyable
        getOrCreateTagBuilder(TagKeys.CROWBAR_DESTROYABLE).addTag(BlockTags.PICKAXE_MINEABLE);
        getOrCreateTagBuilder(TagKeys.CROWBAR_DESTROYABLE).addTag(BlockTags.AXE_MINEABLE);
        getOrCreateTagBuilder(TagKeys.CROWBAR_DESTROYABLE).addOptionalTag(new Identifier("c:glass_panes"));
        getOrCreateTagBuilder(TagKeys.CROWBAR_DESTROYABLE).addOptionalTag(new Identifier("c:glasses"));
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
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(ModBlocks.WILD_CHILI_CROP);
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(ModBlocks.WILD_SOYBEAN);
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
        // Storage Block
        getOrCreateTagBuilder(TagKeys.STORAGE_BLOCKS_BLOCK).addTag(TagKeys.SILICON_STORGE_BLOCK_BLOCK);
        getOrCreateTagBuilder(TagKeys.SILICON_STORGE_BLOCK_BLOCK).add(ModBlocks.SILICON_BLOCK);
        getOrCreateTagBuilder(TagKeys.STORAGE_BLOCKS_BLOCK).addTag(TagKeys.CHERRY_STORGE_BLOCK_BLOCK);
        getOrCreateTagBuilder(TagKeys.CHERRY_STORGE_BLOCK_BLOCK).add(ModBlocks.BOXED_CHERRIES);
        // Temperature
        // 0 ℃
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_0).add(Blocks.FROSTED_ICE);
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_0).add(Blocks.ICE);
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_0).add(Blocks.SNOW_BLOCK);
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_0).add(Blocks.SNOW);
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_0).add(Blocks.POWDER_SNOW);
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_0).add(Blocks.POWDER_SNOW_CAULDRON);
        // -10 ℃
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_MINUS_10).add(Blocks.PACKED_ICE);
        // -20 ℃
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_MINUS_20).add(Blocks.BLUE_ICE);
        // 40 ℃
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_40).add(Blocks.LANTERN);
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_40).add(Blocks.JACK_O_LANTERN);
        // 60 ℃
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_60).add(Blocks.SOUL_LANTERN);
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_60).add(Blocks.CANDLE);
        // 80 ℃
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_80).add(Blocks.TORCH);
        // 100 ℃
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_100).add(Blocks.SOUL_TORCH);
        // 300 ℃
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_300).add(Blocks.FIRE);
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_300).add(Blocks.MAGMA_BLOCK);
        // 600 ℃
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_600).add(Blocks.SOUL_FIRE);
        // 1000 ℃
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_1000).add(Blocks.LAVA);
        getOrCreateTagBuilder(TagKeys.TEMPERATURE_1000).add(Blocks.LAVA_CAULDRON);

    }
    /** Add each tool requirement once, alongside the invisible cells of the same machine. */
    private void addToolTag(TagKey<Block> tag, Block block) {
        var builder = getOrCreateTagBuilder(tag).add(block);
        if (block instanceof LargeMachineBlock machine && machine.getPartBlock() != null) {
            builder.add(machine.getPartBlock());
        }
    }

}
