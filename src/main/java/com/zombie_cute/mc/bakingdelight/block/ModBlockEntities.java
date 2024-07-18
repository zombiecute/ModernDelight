package com.zombie_cute.mc.bakingdelight.block;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.entities.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<OvenBlockEntity> OVEN_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID,"oven_be"),
                    FabricBlockEntityTypeBuilder.create(OvenBlockEntity::new, ModBlocks.OVEN).build());
    public static final BlockEntityType<GlassBowlBlockEntity> GLASS_BOWL_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "glass_bowl_be"),
            FabricBlockEntityTypeBuilder.create(GlassBowlBlockEntity::new, ModBlocks.GLASS_BOWL).build(null)
    );
    public static final BlockEntityType<FreezerBlockEntity> FREEZER_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "freezer_be"),
            FabricBlockEntityTypeBuilder.create(FreezerBlockEntity::new, ModBlocks.FREEZER).build(null)
    );
    public static final BlockEntityType<PizzaWIPBlockEntity> PIZZA_WIP_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "pizza_wip_be"),
            FabricBlockEntityTypeBuilder.create(PizzaWIPBlockEntity::new, ModBlocks.PIZZA_WIP).build(null)
    );
    public static final BlockEntityType<BakingTrayBlockEntity> BAKING_TRAY_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "baking_tray_be"),
            FabricBlockEntityTypeBuilder.create(BakingTrayBlockEntity::new, ModBlocks.BAKING_TRAY).build(null)
    );
    public static final BlockEntityType<AdvanceFurnaceBlockEntity> ADVANCE_FURNACE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "advance_furnace_block_entity"),
            FabricBlockEntityTypeBuilder.create(AdvanceFurnaceBlockEntity::new, ModBlocks.ADVANCE_FURNACE).build(null)
    );;
    public static final BlockEntityType<WoodenBasinBlockEntity> WOODEN_BASIN_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID,"wooden_basin_block_be"),
            FabricBlockEntityTypeBuilder.create(WoodenBasinBlockEntity::new, ModBlocks.WOODEN_BASIN).build(null)
    );
    public static final BlockEntityType<GasCanisterBlockEntity> GAS_CANISTER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID,"gas_canister_block_be"),
            FabricBlockEntityTypeBuilder.create(GasCanisterBlockEntity::new, ModBlocks.GAS_CANISTER).build(null)
    );
    public static final BlockEntityType<BiogasDigesterControllerBlockEntity> BIOGAS_DIGESTER_CONTROLLER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID,"biogas_digester_controller_block_be"),
            FabricBlockEntityTypeBuilder.create(BiogasDigesterControllerBlockEntity::new, ModBlocks.BIOGAS_DIGESTER_CONTROLLER).build(null)
    );
    public static final BlockEntityType<BiogasDigesterIOBlockEntity> BIOGAS_DIGESTER_IO_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID,"biogas_digester_io_block_be"),
            FabricBlockEntityTypeBuilder.create(BiogasDigesterIOBlockEntity::new, ModBlocks.BIOGAS_DIGESTER_IO).build(null)
    );
    public static final BlockEntityType<BurningGasCookingStoveBlockEntity> BURNING_GAS_COOKING_STOVE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID,"gas_cooking_stove_block_be"),
            FabricBlockEntityTypeBuilder.create(BurningGasCookingStoveBlockEntity::new, ModBlocks.BURNING_GAS_COOKING_STOVE).build(null)
    );
    public static final BlockEntityType<DeepFryerBlockEntity> DEEP_FRYER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID,"deep_fryer_block_be"),
            FabricBlockEntityTypeBuilder.create(DeepFryerBlockEntity::new, ModBlocks.DEEP_FRYER).build(null)
    );
    public static final BlockEntityType<KitchenUtensilHolderBlockEntity> KITCHEN_UTENSIL_HOLDER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "kitchen_utensil_holder_be"),
            FabricBlockEntityTypeBuilder.create(KitchenUtensilHolderBlockEntity::new, ModBlocks.KITCHEN_UTENSIL_HOLDER).build(null)
    );
    public static final BlockEntityType<CuisineTableBlockEntity> CUISINE_TABLE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "cuisine_table_be"),
            FabricBlockEntityTypeBuilder.create(CuisineTableBlockEntity::new, ModBlocks.CUISINE_TABLE).build(null)
    );
    public static final BlockEntityType<RawPizzaBlockEntity> RAW_PIZZA_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "raw_pizza_be"),
            FabricBlockEntityTypeBuilder.create(RawPizzaBlockEntity::new, ModBlocks.RAW_PIZZA).build(null)
    );
    public static final BlockEntityType<PizzaBlockEntity> PIZZA_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "pizza_be"),
            FabricBlockEntityTypeBuilder.create(PizzaBlockEntity::new, ModBlocks.PIZZA).build(null)
    );
    public static final BlockEntityType<CabinetBlockEntity> CABINET_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "cabinet_be"),
            FabricBlockEntityTypeBuilder.create(CabinetBlockEntity::new, ModBlocks.ANDESITE_CABINET,
                    ModBlocks.BLACKSTONE_CABINET, ModBlocks.BASALT_CABINET, ModBlocks.DEEPSLATE_CABINET,
                    ModBlocks.DIORITE_CABINET, ModBlocks.GRANITE_CABINET, ModBlocks.OBSIDIAN_CABINET)
                    .build(null)
    );
    public static final BlockEntityType<PhotovoltaicGeneratorBlockEntity> PHOTOVOLTAIC_GENERATOR_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "photovoltaic_generator_be"),
            FabricBlockEntityTypeBuilder.create(PhotovoltaicGeneratorBlockEntity::new, ModBlocks.PHOTOVOLTAIC_GENERATOR).build(null)
    );
    public static final BlockEntityType<GasPipeBlockEntity> GAS_PIPE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "gas_pipe_be"),
            FabricBlockEntityTypeBuilder.create(GasPipeBlockEntity::new, ModBlocks.GAS_PIPE).build(null)
    );
    public static final BlockEntityType<ACDCConverterBlockEntity> AC_DC_CONVERTER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "gas_pump_be"),
            FabricBlockEntityTypeBuilder.create(ACDCConverterBlockEntity::new, ModBlocks.AC_DC_CONVERTER).build(null)
    );
    public static final BlockEntityType<FanBladeBlockEntity> FAN_BLADE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "fan_blade_be"),
            FabricBlockEntityTypeBuilder.create(FanBladeBlockEntity::new, ModBlocks.FAN_BLADE).build(null)
    );
    public static final BlockEntityType<WindTurbineControllerBlockEntity> WIND_TURBINE_CONTROLLER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "wind_turbine_controller_be"),
            FabricBlockEntityTypeBuilder.create(WindTurbineControllerBlockEntity::new, ModBlocks.WIND_TURBINE_CONTROLLER).build(null)
    );
    public static final BlockEntityType<BatteryBlockEntity> BATTERY_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "battery_be"),
            FabricBlockEntityTypeBuilder.create(BatteryBlockEntity::new,
                    ModBlocks.SIMPLE_BATTERY,
                    ModBlocks.INTERMEDIATE_BATTERY,
                    ModBlocks.ADVANCE_BATTERY,
                    ModBlocks.DIMENSION_BATTERY).build(null)
    );
    public static final BlockEntityType<SterlingEngineBlockEntity> STERLING_ENGINE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "sterling_engine_be"),
            FabricBlockEntityTypeBuilder.create(SterlingEngineBlockEntity::new, ModBlocks.STERLING_ENGINE).build(null)
    );
    public static final BlockEntityType<FaradayGeneratorBlockEntity> FARADAY_GENERATOR_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "faraday_generator_be"),
            FabricBlockEntityTypeBuilder.create(FaradayGeneratorBlockEntity::new, ModBlocks.FARADAY_GENERATOR).build(null)
    );
    public static final BlockEntityType<TeslaCoilBlockEntity> TESLA_COIL_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "tesla_coil_be"),
            FabricBlockEntityTypeBuilder.create(TeslaCoilBlockEntity::new, ModBlocks.TESLA_COIL).build(null)
    );
    public static final BlockEntityType<ElectriciansDeskBlockEntity> ELECTRICIANS_DESK_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "electricians_desk_be"),
            FabricBlockEntityTypeBuilder.create(ElectriciansDeskBlockEntity::new, ModBlocks.ELECTRICIANS_DESK).build(null)
    );
    public static final BlockEntityType<BambooGrateBlockEntity> BAMBOO_GRATE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "bamboo_grate_be"),
            FabricBlockEntityTypeBuilder.create(BambooGrateBlockEntity::new, ModBlocks.BAMBOO_GRATE).build(null)
    );
    public static final BlockEntityType<ElectricSteamerBlockEntity> ELECTRIC_STEAMER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "electric_steamer_be"),
            FabricBlockEntityTypeBuilder.create(ElectricSteamerBlockEntity::new, ModBlocks.ELECTRIC_STEAMER).build(null)
    );
    public static final BlockEntityType<IceCreamMakerBlockEntity> ICE_CREAM_MAKER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "ice_cream_maker_be"),
            FabricBlockEntityTypeBuilder.create(IceCreamMakerBlockEntity::new, ModBlocks.ICE_CREAM_MAKER).build(null)
    );
    public static final BlockEntityType<FishAndChipsBlockEntity> FISH_AND_CHIPS_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Bakingdelight.MOD_ID, "fish_and_chips_be"),
            FabricBlockEntityTypeBuilder.create(FishAndChipsBlockEntity::new, ModBlocks.FISH_AND_CHIPS).build(null)
    );
    public static void registerBlockEntities(){
        Bakingdelight.LOGGER.info("Registering Mod Block Entities for " + Bakingdelight.MOD_ID);
    }
}
