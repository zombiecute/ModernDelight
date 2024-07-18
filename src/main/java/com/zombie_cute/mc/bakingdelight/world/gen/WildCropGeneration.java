package com.zombie_cute.mc.bakingdelight.world.gen;

import com.zombie_cute.mc.bakingdelight.world.feature.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.gen.GenerationStep;

public class WildCropGeneration {
    public static void generate(){
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_JUNGLE),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WILD_BLACK_PEPPER_PLACED);
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST)
                        .or(BiomeSelectors.tag(BiomeTags.IS_MOUNTAIN))
                        .or(BiomeSelectors.tag(BiomeTags.IS_TAIGA)),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WILD_GARLIC_PLACED);
    }
}
