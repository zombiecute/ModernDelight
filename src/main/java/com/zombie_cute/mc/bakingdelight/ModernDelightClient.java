package com.zombie_cute.mc.bakingdelight;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.food.fish_and_chips.FishAndChipsBlockEntityRender;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.FreezerBlockEntityRenderer;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.GlassBowlBlockEntityRenderer;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.decor.KitchenUtensilHolderBlockEntityRender;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.BakingTrayBlockEntityRenderer;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.DeepFryBasketBlockEntityRenderer;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.DeepFryerBlockEntityRenderer;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.WoodenBasinBlockEntityRenderer;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlockEntityRender;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.juice_extractor.JuiceExtractorBlockEntityRender;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.steaming.BambooGrateBlockEntityRenderer;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.steaming.ElectricSteamerBlockEntityRenderer;
import com.zombie_cute.mc.bakingdelight.block.power.ChargingPostBlockEntityRenderer;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power.SterlingEngineBlockEntityRender;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.wind_power.FanBladeBlockEntityRender;
import com.zombie_cute.mc.bakingdelight.entity.ModEntities;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.food.instant_noodles.CookedPortablePotItem;
import com.zombie_cute.mc.bakingdelight.item.food.instant_noodles.PortablePotItem;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.screen.custom.*;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModernDelightClient implements ClientModInitializer {
    public static final String ORE_UI_DARK = "bakingdelight.builtInResourcePack.ore_ui_dark";
    public static final String ORE_UI_BRIGHT = "bakingdelight.builtInResourcePack.ore_ui_bright";
    @Override
    public void onInitializeClient() {
        NetworkHandler.registerC2SPacket();

        ResourceManagerHelper.registerBuiltinResourcePack(
                new Identifier(ModernDelightMain.MOD_ID, "ore_ui_dark"),
                FabricLoader.getInstance().getModContainer(ModernDelightMain.MOD_ID).orElseThrow(),
                Text.translatable(ORE_UI_DARK),
                ResourcePackActivationType.NORMAL
        );
        ResourceManagerHelper.registerBuiltinResourcePack(
                new Identifier(ModernDelightMain.MOD_ID, "ore_ui_bright"),
                FabricLoader.getInstance().getModContainer(ModernDelightMain.MOD_ID).orElseThrow(),
                Text.translatable(ORE_UI_BRIGHT),
                ResourcePackActivationType.NORMAL
        );

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.GLASS_BOWL, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEEP_FRYER, RenderLayer.getTranslucent());

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
        BlockEntityRendererFactories.register(ModBlockEntities.JUICE_EXTRACTOR_BLOCK_ENTITY, JuiceExtractorBlockEntityRender::new);
        BlockEntityRendererFactories.register(ModBlockEntities.DEEP_FRY_BASKET_BLOCK_ENTITY, DeepFryBasketBlockEntityRenderer::new);

        registerModelPredicateProviders();

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluid.STILL_CREAM, ModFluid.FLOWING_CREAM,
                new SimpleFluidRenderHandler(
                new Identifier(ModernDelightMain.MOD_ID,"block/cream_still"),
                new Identifier(ModernDelightMain.MOD_ID,"block/cream_flow")
        ));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(),
                ModFluid.STILL_CREAM, ModFluid.FLOWING_CREAM);
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluid.STILL_VEGETABLE_OIL, ModFluid.FLOWING_VEGETABLE_OIL,
                new SimpleFluidRenderHandler(
                        new Identifier(ModernDelightMain.MOD_ID,"block/vegetable_oil_still"),
                        new Identifier(ModernDelightMain.MOD_ID,"block/vegetable_oil_flow")
                ));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluid.STILL_VEGETABLE_OIL, ModFluid.FLOWING_VEGETABLE_OIL);
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluid.STILL_LIQUEFIED_BIOGAS, ModFluid.FLOWING_LIQUEFIED_BIOGAS,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft","block/water_still"),
                        new Identifier("minecraft","block/water_flow"),
                        0x8b7a49
                ));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluid.STILL_LIQUEFIED_BIOGAS, ModFluid.FLOWING_LIQUEFIED_BIOGAS);
    }
    public static void registerModelPredicateProviders() {
        ModelPredicateProviderRegistry.register(ModItems.PORTABLE_POT, new Identifier(ModernDelightMain.MOD_ID,"pot_state"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (PortablePotItem.hasNoodle(itemStack) || PortablePotItem.hasWater(itemStack) || PortablePotItem.hasQuicklime(itemStack)){
                return 0.1F;
            }
            return 0.0F;
        });
        ModelPredicateProviderRegistry.register(ModItems.COOKED_PORTABLE_POT, new Identifier(ModernDelightMain.MOD_ID,"noodle_type"), (itemStack, clientWorld, livingEntity, seed) -> {
            SpecialIngredient specialIngredient = PortablePotItem.getNoodleType(itemStack);
            if (specialIngredient != null){
                return switch (specialIngredient){
                    case STEW_CHICKEN_NOODLE_WITH_MUSHROOM -> 0.1F;
                    case BRAISED_BEEF_NOODLE_SOUP -> 0.2F;
                    case TONKOTSU_RAMEN -> 0.3F;
                };
            }
            if (CookedPortablePotItem.isUnhealthy(itemStack)){
                return 0.9F;
            }
            return 0.0F;
        });
    }
}