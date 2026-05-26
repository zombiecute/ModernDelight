package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class ModConfig {
    public static final ConfigClassHandler<ModConfig> INSTANCE = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(Identifier.of(ModernDelightMain.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("moderndelight-config.json")).build())
            .build();


    @SerialEntry
    public static String digestate = "minecraft:bone_meal";
    @SerialEntry
    public static int acdcConverterMaxWorkSpeed = 20;
    @SerialEntry
    public static int energyGeneratedByFaradayGenerator = 200;
    @SerialEntry
    public static float photovoltaicGeneratorMultiplier = 2.0f;
    @SerialEntry
    public static float windTurbineMultiplier = 2.0f;
    @SerialEntry
    public static float teslaCoilConversionEfficiency = 0.8f;
    @SerialEntry
    public static float chargingPostEfficiency = 0.9f;
    @SerialEntry
    public static boolean allowGasCanisterExplode = true;
    @SerialEntry
    public static boolean allowGasCanisterInNether = false;
    @SerialEntry
    public static int gasCanisterVolume = 6000;
    @SerialEntry
    public static int maxSeasonings = 5;

    public static Screen makeScreen(Screen parent) {
        return YetAnotherConfigLib.create(INSTANCE, (defaults, config, builder) -> builder
                        .title(Text.translatable("config.bakingdelight.title"))
                        .category(ConfigCategory.createBuilder()
                                .name(Text.translatable("config.bakingdelight.title"))
                                .group(OptionGroup.createBuilder()
                                        .name(Text.translatable("config.bakingdelight.biogasSystem.title"))
                                        .option(Option.<String>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.digestate"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.digestate.desc")))
                                                .binding(
                                                        "minecraft:bone_meal",
                                                        () -> digestate,
                                                        value -> digestate = value
                                                )
                                                .controller(StringControllerBuilder::create)
                                                .build())
                                        .option(Option.<Boolean>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.allowGasCanisterExplode"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.allowGasCanisterExplode.desc")))
                                                .binding(
                                                        true,
                                                        () -> allowGasCanisterExplode,
                                                        value -> allowGasCanisterExplode = value
                                                )
                                                .controller(opt -> BooleanControllerBuilder.create(opt)
                                                        .valueFormatter(val -> Text.literal(val ? "√": "X"))
                                                        .coloured(true))
                                                .build())
                                        .option(Option.<Boolean>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.allowGasCanisterInNether"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.allowGasCanisterInNether.desc")))
                                                .binding(
                                                        false,
                                                        () -> allowGasCanisterInNether,
                                                        value -> allowGasCanisterInNether = value
                                                )
                                                .controller(opt -> BooleanControllerBuilder.create(opt)
                                                        .valueFormatter(val -> Text.literal(val ? "√": "X"))
                                                        .coloured(true))
                                                .build())
                                        .option(Option.<Integer>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.gasCanisterVolume"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.gasCanisterVolume.desc")))
                                                .binding(
                                                        6000,
                                                        () -> gasCanisterVolume,
                                                        value -> gasCanisterVolume = value
                                                )
                                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(100, 30000).step(100)
                                                        .valueFormatter(val -> Text.literal(val + " L")))
                                                .build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.translatable("config.bakingdelight.powerSystem.title"))
                                        .option(Option.<Integer>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.acdcConverterMaxWorkSpeed"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.acdcConverterMaxWorkSpeed.desc")))
                                                .binding(
                                                        20,
                                                        () -> acdcConverterMaxWorkSpeed,
                                                        value -> acdcConverterMaxWorkSpeed = value
                                                )
                                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 50).step(1)
                                                        .valueFormatter(val -> Text.literal(String.valueOf(val))))
                                                .build())
                                        .option(Option.<Integer>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.energyGeneratedByFaradayGenerator"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.energyGeneratedByFaradayGenerator.desc")))
                                                .binding(
                                                        200,
                                                        () -> energyGeneratedByFaradayGenerator,
                                                        value -> energyGeneratedByFaradayGenerator = value
                                                )
                                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(10, 1000).step(10)
                                                        .valueFormatter(val -> Text.literal(val + " EP/s")))
                                                .build())
                                        .option(Option.<Float>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.photovoltaicGeneratorMultiplier"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.photovoltaicGeneratorMultiplier.desc")))
                                                .binding(
                                                        2.0f,
                                                        () -> photovoltaicGeneratorMultiplier,
                                                        value -> photovoltaicGeneratorMultiplier = value
                                                )
                                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.05f, 10.0f).step(0.05f)
                                                        .valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.<Float>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.windTurbineMultiplier"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.windTurbineMultiplier.desc")))
                                                .binding(
                                                        3.0f,
                                                        () -> windTurbineMultiplier,
                                                        value -> windTurbineMultiplier = value
                                                )
                                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.05f, 10.0f).step(0.05f)
                                                        .valueFormatter(val -> Text.literal(String.format("%.2f", val))))
                                                .build())
                                        .option(Option.<Float>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.teslaCoilConversionEfficiency"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.teslaCoilConversionEfficiency.desc")))
                                                .binding(
                                                        0.8f,
                                                        () -> teslaCoilConversionEfficiency,
                                                        value -> teslaCoilConversionEfficiency = value
                                                )
                                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.005f, 1.0f).step(0.005f)
                                                        .valueFormatter(val -> Text.literal(String.format("%.1f",val * 100.0) + "%")))
                                                .build())
                                        .option(Option.<Float>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.chargingPostEfficiency.title"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.chargingPostEfficiency.desc")))
                                                .binding(
                                                        0.9f,
                                                        () -> chargingPostEfficiency,
                                                        value -> chargingPostEfficiency = value
                                                )
                                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.005f, 1.0f).step(0.005f)
                                                        .valueFormatter(val -> Text.literal(String.format("%.1f",val * 100.0) + "%")))
                                                .build())
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.translatable("config.bakingdelight.seasoning.title"))
                                        .option(Option.<Integer>createBuilder()
                                                .name(Text.translatable("config.bakingdelight.option.maxSeasonings.title"))
                                                .description(OptionDescription.of(Text.translatable("config.bakingdelight.option.maxSeasonings.desc")))
                                                .binding(
                                                        5,
                                                        () -> maxSeasonings,
                                                        value -> maxSeasonings = value
                                                )
                                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 15).step(1)
                                                        .valueFormatter(val -> Text.literal(String.valueOf(val))))
                                                .build())
                                        .build())
                                .build()))
                .generateScreen(parent);
    }
}
