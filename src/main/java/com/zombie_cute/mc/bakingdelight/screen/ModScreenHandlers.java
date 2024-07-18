package com.zombie_cute.mc.bakingdelight.screen;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.screen.custom.*;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<OvenScreenHandler> OVEN_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "oven_screen"),
                    new ExtendedScreenHandlerType<>(OvenScreenHandler::new));
    public static final ScreenHandlerType<FreezerScreenHandler> FREEZER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "freezing_screen"),
                    new ExtendedScreenHandlerType<>(FreezerScreenHandler::new));
    public static final ScreenHandlerType<AdvanceFurnaceScreenHandler> ADVANCE_FURNACE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "advance_furnace_screen"),
                    new ExtendedScreenHandlerType<>(AdvanceFurnaceScreenHandler::new));
    public static final ScreenHandlerType<WoodenBasinScreenHandler> WOODEN_BASIN_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "wooden_basin_screen"),
                    new ExtendedScreenHandlerType<>(WoodenBasinScreenHandler::new));
    public static final ScreenHandlerType<GasCanisterScreenHandler> GAS_CANISTER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "gas_canister_screen"),
                    new ExtendedScreenHandlerType<>(GasCanisterScreenHandler::new));
    public static final ScreenHandlerType<BiogasDigesterControllerScreenHandler> BIOGAS_DIGESTER_CONTROLLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "biogas_digester_controller_screen"),
                    new ExtendedScreenHandlerType<>(BiogasDigesterControllerScreenHandler::new));
    public static final ScreenHandlerType<BiogasDigesterIOScreenHandler> BIOGAS_DIGESTER_IO_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "biogas_digester_io_screen"),
                    new ExtendedScreenHandlerType<>(BiogasDigesterIOScreenHandler::new));
    public static final ScreenHandlerType<DeepFryerScreenHandler> DEEP_FRYER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "deep_fryer_screen"),
                    new ExtendedScreenHandlerType<>(DeepFryerScreenHandler::new));
    public static final ScreenHandlerType<CuisineTableScreenHandler> CUISINE_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "cuisine_table_screen"),
                    new ExtendedScreenHandlerType<>(CuisineTableScreenHandler::new));
    public static final ScreenHandlerType<CabinetScreenHandler> CABINET_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "cabinet_screen"),
                    new ExtendedScreenHandlerType<>(CabinetScreenHandler::new));
    public static final ScreenHandlerType<PhotovoltaicGeneratorScreenHandler> PHOTOVOLTAIC_GENERATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "photovoltaic_generator_screen"),
                    new ExtendedScreenHandlerType<>(PhotovoltaicGeneratorScreenHandler::new));
    public static final ScreenHandlerType<ACDCConverterScreenHandler> ACDC_CONVERTER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "gas_pump_screen"),
                    new ExtendedScreenHandlerType<>(ACDCConverterScreenHandler::new));
    public static final ScreenHandlerType<WindTurbineControllerScreenHandler> WIND_TURBINE_CONTROLLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "wind_turbine_controller_screen"),
                    new ExtendedScreenHandlerType<>(WindTurbineControllerScreenHandler::new));
    public static final ScreenHandlerType<FaradayGeneratorScreenHandler> FARADAY_GENERATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "faraday_generator_screen"),
                    new ExtendedScreenHandlerType<>(FaradayGeneratorScreenHandler::new));
    public static final ScreenHandlerType<TeslaCoilScreenHandler> TESLA_COIL_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "tesla_coil_screen"),
                    new ExtendedScreenHandlerType<>(TeslaCoilScreenHandler::new));
    public static final ScreenHandlerType<ElectriciansDeskScreenHandler> ELECTRICIANS_DESK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "electric_desk_screen"),
                    new ExtendedScreenHandlerType<>(ElectriciansDeskScreenHandler::new));
    public static final ScreenHandlerType<BambooSteamerScreenHandler> BAMBOO_STEAMER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "bamboo_steamer_screen"),
                    new ExtendedScreenHandlerType<>(BambooSteamerScreenHandler::new));
    public static final ScreenHandlerType<ElectricSteamerScreenHandler> ELECTRIC_STEAMER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "electric_steamer_screen"),
                    new ExtendedScreenHandlerType<>(ElectricSteamerScreenHandler::new));
    public static final ScreenHandlerType<IceCreamMakerScreenHandler> ICE_CREAM_MAKER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Bakingdelight.MOD_ID, "ice_cream_maker_screen"),
                    new ExtendedScreenHandlerType<>(IceCreamMakerScreenHandler::new));
    public static void registerScreenHandlers(){
        Bakingdelight.LOGGER.info("Registering Screen Handlers for " + Bakingdelight.MOD_ID);
    }
}
