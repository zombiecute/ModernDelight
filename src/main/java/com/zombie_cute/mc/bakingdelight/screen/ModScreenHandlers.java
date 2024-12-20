package com.zombie_cute.mc.bakingdelight.screen;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.screen.custom.*;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<ACDCConverterScreenHandler> ACDC_CONVERTER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "ac_dc_converter_screen"),
                    new ExtendedScreenHandlerType<ACDCConverterScreenHandler, BlockPos>
                            (ACDCConverterScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<BambooSteamerScreenHandler> BAMBOO_STEAMER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "bamboo_steamer_screen"),
                    new ExtendedScreenHandlerType<BambooSteamerScreenHandler, BlockPos>
                            (BambooSteamerScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<BiogasDigesterControllerScreenHandler> BIOGAS_DIGESTER_CONTROLLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "biogas_digester_controller_screen"),
                    new ExtendedScreenHandlerType<BiogasDigesterControllerScreenHandler, BlockPos>
                            (BiogasDigesterControllerScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<BiogasDigesterIOScreenHandler> BIOGAS_DIGESTER_IO_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "biogas_digester_io_screen"),
                    new ExtendedScreenHandlerType<BiogasDigesterIOScreenHandler, BlockPos>
                            (BiogasDigesterIOScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<DeepFryerScreenHandler> DEEP_FRYER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "deep_fryer_screen"),
                    new ExtendedScreenHandlerType<DeepFryerScreenHandler, BlockPos>
                            (DeepFryerScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<ElectricSteamerScreenHandler> ELECTRIC_STEAMER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "electric_steamer_screen"),
                    new ExtendedScreenHandlerType<ElectricSteamerScreenHandler, BlockPos>
                            (ElectricSteamerScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<FaradayGeneratorScreenHandler> FARADAY_GENERATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "faraday_generator_screen"),
                    new ExtendedScreenHandlerType<FaradayGeneratorScreenHandler, BlockPos>
                            (FaradayGeneratorScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<GasCanisterScreenHandler> GAS_CANISTER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "gas_canister_screen"),
                    new ExtendedScreenHandlerType<GasCanisterScreenHandler, BlockPos>
                            (GasCanisterScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<OvenScreenHandler> OVEN_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "oven_screen"),
                    new ExtendedScreenHandlerType<OvenScreenHandler, BlockPos>
                            (OvenScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<FreezerScreenHandler> FREEZER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "freezing_screen"),
                    new ExtendedScreenHandlerType<FreezerScreenHandler, BlockPos>
                            (FreezerScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<AdvanceFurnaceScreenHandler> ADVANCE_FURNACE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "advance_furnace_screen"),
                    new ExtendedScreenHandlerType<AdvanceFurnaceScreenHandler, BlockPos>
                            (AdvanceFurnaceScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<WoodenBasinScreenHandler> WOODEN_BASIN_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "wooden_basin_screen"),
                    new ExtendedScreenHandlerType<WoodenBasinScreenHandler, BlockPos>
                            (WoodenBasinScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<CuisineTableScreenHandler> CUISINE_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "cuisine_table_screen"),
                    new ExtendedScreenHandlerType<CuisineTableScreenHandler, BlockPos>
                            (CuisineTableScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<CabinetScreenHandler> CABINET_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "cabinet_screen"),
                    new ExtendedScreenHandlerType<CabinetScreenHandler, BlockPos>
                            (CabinetScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<PhotovoltaicGeneratorScreenHandler> PHOTOVOLTAIC_GENERATOR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "photovoltaic_generator_screen"),
                    new ExtendedScreenHandlerType<PhotovoltaicGeneratorScreenHandler, BlockPos>
                            (PhotovoltaicGeneratorScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<WindTurbineControllerScreenHandler> WIND_TURBINE_CONTROLLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "wind_turbine_controller_screen"),
                    new ExtendedScreenHandlerType<WindTurbineControllerScreenHandler, BlockPos>
                            (WindTurbineControllerScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<TeslaCoilScreenHandler> TESLA_COIL_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "tesla_coil_screen"),
                    new ExtendedScreenHandlerType<TeslaCoilScreenHandler, BlockPos>
                            (TeslaCoilScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<ElectriciansDeskScreenHandler> ELECTRICIANS_DESK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "electric_desk_screen"),
                    new ExtendedScreenHandlerType<ElectriciansDeskScreenHandler, BlockPos>
                            (ElectriciansDeskScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<IceCreamMakerScreenHandler> ICE_CREAM_MAKER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "ice_cream_maker_screen"),
                    new ExtendedScreenHandlerType<IceCreamMakerScreenHandler, BlockPos>
                            (IceCreamMakerScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static final ScreenHandlerType<ChargingPostScreenHandler> CHARGING_POST_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Bakingdelight.MOD_ID, "charging_post_screen"),
                    new ExtendedScreenHandlerType<ChargingPostScreenHandler, BlockPos>
                            (ChargingPostScreenHandler::new, new PacketCodec<>() {
                                @Override
                                public BlockPos decode(RegistryByteBuf buf) {
                                    return buf.readBlockPos();
                                }

                                @Override
                                public void encode(RegistryByteBuf buf, BlockPos value) {
                                    buf.writeBlockPos(value);
                                }
                            }));
    public static void registerScreenHandlers(){
        Bakingdelight.LOGGER.info("Registering Screen Handlers for " + Bakingdelight.MOD_ID);
    }
}
