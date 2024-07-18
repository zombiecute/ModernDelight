package com.zombie_cute.mc.bakingdelight;

import com.zombie_cute.mc.bakingdelight.gen.*;
import com.zombie_cute.mc.bakingdelight.world.feature.ModConfiguredFeatures;
import com.zombie_cute.mc.bakingdelight.world.feature.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class BakingdelightDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModModelGenerator::new);
		pack.addProvider(ModLangGenerator::new);
		pack.addProvider(ModLangCNGenerator::new);
		pack.addProvider(ModRecipeGenerator::new);
		pack.addProvider(ModBlockLootGenerator::new);
		pack.addProvider(ModBlockTagGenerator::new);
		pack.addProvider(ModItemTagGenerator::new);
		pack.addProvider(ModAdvancementGenerator::new);
		pack.addProvider(ModFluidTagGenerator::new);
		pack.addProvider(ModWorldGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
