package com.zombie_cute.mc.bakingdelight.gen;

import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagGenerator extends FabricTagProvider.FluidTagProvider {

    public ModFluidTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        // Water
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.STILL_CREAM);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.FLOWING_CREAM);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.FLOWING_VEGETABLE_OIL);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.STILL_VEGETABLE_OIL);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.STILL_LIQUEFIED_BIOGAS);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.FLOWING_LIQUEFIED_BIOGAS);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.STILL_SWEETENED_WATER);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.FLOWING_SWEETENED_WATER);
        // Oil
        getOrCreateTagBuilder(TagKeys.OIL).add(ModFluid.FLOWING_VEGETABLE_OIL);
        getOrCreateTagBuilder(TagKeys.OIL).add(ModFluid.STILL_VEGETABLE_OIL);
        // Cream
        getOrCreateTagBuilder(TagKeys.CREAM).add(ModFluid.STILL_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAM).add(ModFluid.FLOWING_CREAM);
        // Milk
        getOrCreateTagBuilder(TagKeys.MILK).addOptionalTag(Identifier.of("forge","milk"));
        // Gas
        getOrCreateTagBuilder(TagKeys.GAS).add(ModFluid.STILL_LIQUEFIED_BIOGAS);
        getOrCreateTagBuilder(TagKeys.GAS).add(ModFluid.FLOWING_LIQUEFIED_BIOGAS);
        getOrCreateTagBuilder(TagKeys.GAS).addOptionalTag(Identifier.of("c","biofuel"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(Identifier.of("techreborn","hydrogen"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(Identifier.of("techreborn","deuterium"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(Identifier.of("techreborn","tritium"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(Identifier.of("techreborn","diesel"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(Identifier.of("techreborn","methane"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(Identifier.of("techreborn","methane"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(Identifier.of("techreborn","nitro_diesel"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(Identifier.of("techreborn","nitrocoal_fuel"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(Identifier.of("techreborn","nitrofuel"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(Identifier.of("techreborn","biofuel"));
    }
}
