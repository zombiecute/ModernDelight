package com.zombie_cute.mc.bakingdelight;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.renderer.*;
import com.zombie_cute.mc.bakingdelight.entity.ModEntities;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.screen.custom.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BakingdelightClient implements ClientModInitializer {
    public static final String ORE_UI_DARK = "bakingdelight.builtInResourcePack.ore_ui_dark";
    public static final String ORE_UI_BRIGHT = "bakingdelight.builtInResourcePack.ore_ui_bright";
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.registerBuiltinResourcePack(
                new Identifier(Bakingdelight.MOD_ID, "ore_ui_dark"),
                FabricLoader.getInstance().getModContainer(Bakingdelight.MOD_ID).orElseThrow(),
                Text.translatable(ORE_UI_DARK),
                ResourcePackActivationType.NORMAL
        );
        ResourceManagerHelper.registerBuiltinResourcePack(
                new Identifier(Bakingdelight.MOD_ID, "ore_ui_bright"),
                FabricLoader.getInstance().getModContainer(Bakingdelight.MOD_ID).orElseThrow(),
                Text.translatable(ORE_UI_BRIGHT),
                ResourcePackActivationType.NORMAL
        );
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLASS_BOWL, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_PEPPER_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILD_PEPPER_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GARLIC_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILD_GARLIC, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.BUTTER, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CHERRY_BOMB, FlyingItemEntityRenderer::new);

        HandledScreens.register(ModScreenHandlers.OVEN_SCREEN_HANDLER, OvenScreen::new);
        HandledScreens.register(ModScreenHandlers.FREEZER_SCREEN_HANDLER, FreezerScreen::new);
        HandledScreens.register(ModScreenHandlers.ADVANCE_FURNACE_SCREEN_HANDLER, AdvanceFurnaceScreen::new);
        HandledScreens.register(ModScreenHandlers.WOODEN_BASIN_SCREEN_HANDLER, WoodenBasinScreen::new);
        HandledScreens.register(ModScreenHandlers.GAS_CANISTER_SCREEN_HANDLER, GasCanisterScreen::new);
        HandledScreens.register(ModScreenHandlers.BIOGAS_DIGESTER_CONTROLLER_SCREEN_HANDLER, BiogasDigesterControllerScreen::new);
        HandledScreens.register(ModScreenHandlers.BIOGAS_DIGESTER_IO_SCREEN_HANDLER, BiogasDigesterIOScreen::new);
        HandledScreens.register(ModScreenHandlers.DEEP_FRYER_SCREEN_HANDLER, DeepFryerScreen::new);
        HandledScreens.register(ModScreenHandlers.CUISINE_TABLE_SCREEN_HANDLER, CuisineTableScreen::new);
        HandledScreens.register(ModScreenHandlers.CABINET_SCREEN_HANDLER, CabinetScreen::new);
        HandledScreens.register(ModScreenHandlers.PHOTOVOLTAIC_GENERATOR_SCREEN_HANDLER, PhotovoltaicGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.ACDC_CONVERTER_SCREEN_HANDLER, ACDCConverterScreen::new);
        HandledScreens.register(ModScreenHandlers.WIND_TURBINE_CONTROLLER_SCREEN_HANDLER, WindTurbineControllerScreen::new);
        HandledScreens.register(ModScreenHandlers.FARADAY_GENERATOR_SCREEN_HANDLER, FaradayGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.TESLA_COIL_SCREEN_HANDLER, TeslaCoilScreen::new);
        HandledScreens.register(ModScreenHandlers.ELECTRICIANS_DESK_SCREEN_HANDLER, ElectriciansDeskScreen::new);
        HandledScreens.register(ModScreenHandlers.BAMBOO_STEAMER_SCREEN_HANDLER, BambooSteamerScreen::new);
        HandledScreens.register(ModScreenHandlers.ELECTRIC_STEAMER_SCREEN_HANDLER, ElectricSteamerScreen::new);
        HandledScreens.register(ModScreenHandlers.ICE_CREAM_MAKER_SCREEN_HANDLER, IceCreamMakerScreen::new);
        HandledScreens.register(ModScreenHandlers.CHARGING_POST_SCREEN_HANDLER, ChargingPostScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.GLASS_BOWL_ENTITY, GlassBowlBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.FREEZER_ENTITY, FreezerBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.BAKING_TRAY_BLOCK_ENTITY, BakingTrayBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.WOODEN_BASIN_BLOCK_ENTITY, WoodenBasinBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.DEEP_FRYER_BLOCK_ENTITY, DeepFryerBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.FAN_BLADE_BLOCK_ENTITY, FanBladeBlockEntityRender::new);
        BlockEntityRendererFactories.register(ModBlockEntities.KITCHEN_UTENSIL_HOLDER_BLOCK_ENTITY, KitchenUtensilHolderBlockEntityRender::new);
        BlockEntityRendererFactories.register(ModBlockEntities.STERLING_ENGINE_BLOCK_ENTITY, SterlingEngineBlockEntityRender::new);
        BlockEntityRendererFactories.register(ModBlockEntities.BAMBOO_GRATE_BLOCK_ENTITY, BambooGrateBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.ICE_CREAM_MAKER_BLOCK_ENTITY, IceCreamMakerBlockEntityRender::new);
        BlockEntityRendererFactories.register(ModBlockEntities.FISH_AND_CHIPS_BLOCK_ENTITY, FishAndChipsBlockEntityRender::new);
        BlockEntityRendererFactories.register(ModBlockEntities.CHARGING_POST_BLOCK_ENTITY, ChargingPostBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.ELECTRIC_STEAMER_BLOCK_ENTITY, ElectricSteamerBlockEntityRenderer::new);

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluid.STILL_CREAM, ModFluid.FLOWING_CREAM,
                new SimpleFluidRenderHandler(
                new Identifier(Bakingdelight.MOD_ID,"block/cream_still"),
                new Identifier(Bakingdelight.MOD_ID,"block/cream_flow")
        ));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(),
                ModFluid.STILL_CREAM, ModFluid.FLOWING_CREAM);
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluid.STILL_VEGETABLE_OIL, ModFluid.FLOWING_VEGETABLE_OIL,
                new SimpleFluidRenderHandler(
                        new Identifier(Bakingdelight.MOD_ID,"block/vegetable_oil_still"),
                        new Identifier(Bakingdelight.MOD_ID,"block/vegetable_oil_flow")
                ));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluid.STILL_VEGETABLE_OIL, ModFluid.FLOWING_VEGETABLE_OIL);
    }
}