package com.zombie_cute.mc.bakingdelight.gen;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.tag.ForgeTagKeys;
import com.zombie_cute.mc.bakingdelight.tag.ModTagKeys;
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
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(ModBlocks.MASHED_POTATO_BLOCK);
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(Blocks.DIRT);
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(Blocks.DIRT_PATH);
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(Blocks.GRASS_BLOCK);
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(Blocks.SAND);
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(Blocks.RED_SAND);
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(Blocks.SOUL_SAND);
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(Blocks.SOUL_SOIL);
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(Blocks.COARSE_DIRT);
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(Blocks.MYCELIUM);
        getOrCreateTagBuilder(ModTagKeys.WHISK_MINEABLE)
                .add(Blocks.PODZOL);
        // Mineable
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
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.GAS_PIPE);
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

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(ModBlocks.WOODEN_BASIN);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(ModBlocks.ELECTRICIANS_DESK);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(ModBlocks.BOXED_CHERRIES);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(ModBlocks.BAMBOO_COVER);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(ModBlocks.BAMBOO_GRATE);

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

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.ADVANCE_BATTERY);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.OBSIDIAN_CABINET);
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.DIMENSION_BATTERY);

        getOrCreateTagBuilder(BlockTags.CROPS).add(ModBlocks.BLACK_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(ModBlocks.BLACK_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.BLACK_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.WILD_PEPPER_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.WILD_GARLIC);
        getOrCreateTagBuilder(BlockTags.CROPS).add(ModBlocks.GARLIC_CROP);
        getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(ModBlocks.GARLIC_CROP);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.GARLIC_CROP);
        // Heater
        getOrCreateTagBuilder(ForgeTagKeys.PASSIVE_BOILER_HEATERS).add(ModBlocks.BURNING_GAS_COOKING_STOVE);
        getOrCreateTagBuilder(ForgeTagKeys.HEAT_SOURCES).add(ModBlocks.BURNING_GAS_COOKING_STOVE);
        // Crowbar Destroyable
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).add(Blocks.IRON_BARS);
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).add(ModBlocks.GAS_PIPE);
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).add(ModBlocks.AC_DC_CONVERTER);
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).addOptionalTag(BlockTags.TRAPDOORS);
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).addOptionalTag(BlockTags.DOORS);
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).addOptionalTag(BlockTags.FENCES);
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).addOptionalTag(BlockTags.FENCE_GATES);
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).addOptionalTag(BlockTags.RAILS);
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).addOptional(new Identifier("create:andesite_bars"));
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).addOptional(new Identifier("create:brass_bars"));
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).addOptional(new Identifier("create:copper_bars"));

        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).addOptionalTag(new Identifier("c:glass_panes"));
        getOrCreateTagBuilder(ModTagKeys.CROWBAR_DESTROYABLE).addOptionalTag(new Identifier("c:glasses"));
        // Baking Tray Heat Sources
        getOrCreateTagBuilder(ModTagKeys.DANGER_BLOCKS).add(Blocks.LAVA);
        getOrCreateTagBuilder(ModTagKeys.DANGER_BLOCKS).add(Blocks.MAGMA_BLOCK);
        getOrCreateTagBuilder(ModTagKeys.DANGER_BLOCKS).addOptionalTag(BlockTags.CAMPFIRES);
        getOrCreateTagBuilder(ModTagKeys.DANGER_BLOCKS).addOptionalTag(ForgeTagKeys.HEAT_SOURCES);
        getOrCreateTagBuilder(ModTagKeys.DANGER_BLOCKS).addOptionalTag(ForgeTagKeys.PASSIVE_BOILER_HEATERS);
        // Small Flowers
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(ModBlocks.WILD_GARLIC);
        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(ModBlocks.WILD_PEPPER_CROP);

    }
}
