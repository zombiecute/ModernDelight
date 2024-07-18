package com.zombie_cute.mc.bakingdelight.world.feature;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> WILD_BLACK_PEPPER = registerKey("wild_black_pepper_cf");
    public static final RegistryKey<ConfiguredFeature<?,?>> WILD_GARLIC = registerKey("wild_garlic_cf");

    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context){
        register(context, WILD_BLACK_PEPPER, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(
                32, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,new SimpleBlockFeatureConfig(
                        BlockStateProvider.of(ModBlocks.WILD_PEPPER_CROP)
                ))
        ));
        register(context, WILD_GARLIC, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(
                20, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,new SimpleBlockFeatureConfig(
                        BlockStateProvider.of(ModBlocks.WILD_GARLIC)
                ))
        ));
    }
    public static RegistryKey<ConfiguredFeature<?,?>> registerKey(String name){
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,new Identifier(Bakingdelight.MOD_ID,name));
    }

    private static <FC extends FeatureConfig,F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> context,
                                                                                  RegistryKey<ConfiguredFeature<?,?>> key, F feature, FC cofiguration){
        context.register(key,new ConfiguredFeature<>(feature,cofiguration));
    }
}
