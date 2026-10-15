package com.zombie_cute.mc.moderndelight.gen;

import com.zombie_cute.mc.moderndelight.fluid.ModFluid;
import com.zombie_cute.mc.moderndelight.tag.TagKeys;
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
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.STILL_ALCOHOL);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.FLOWING_ALCOHOL);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.STILL_VINEGAR);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.FLOWING_VINEGAR);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.STILL_MILK);
        getOrCreateTagBuilder(FluidTags.WATER).add(ModFluid.FLOWING_MILK);
        // Oil
        getOrCreateTagBuilder(TagKeys.OIL).add(ModFluid.FLOWING_VEGETABLE_OIL);
        getOrCreateTagBuilder(TagKeys.OIL).add(ModFluid.STILL_VEGETABLE_OIL);
        // Cream
        getOrCreateTagBuilder(TagKeys.CREAM).add(ModFluid.STILL_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAM).add(ModFluid.FLOWING_CREAM);
        // Milk
        getOrCreateTagBuilder(TagKeys.MILK).addOptionalTag(new Identifier("forge","milk"));
        // Gas
        getOrCreateTagBuilder(TagKeys.GAS).add(ModFluid.STILL_LIQUEFIED_BIOGAS);
        getOrCreateTagBuilder(TagKeys.GAS).add(ModFluid.FLOWING_LIQUEFIED_BIOGAS);
        getOrCreateTagBuilder(TagKeys.GAS).addOptionalTag(new Identifier("c","biofuel"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(new Identifier("techreborn","hydrogen"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(new Identifier("techreborn","deuterium"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(new Identifier("techreborn","tritium"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(new Identifier("techreborn","diesel"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(new Identifier("techreborn","methane"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(new Identifier("techreborn","methane"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(new Identifier("techreborn","nitro_diesel"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(new Identifier("techreborn","nitrocoal_fuel"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(new Identifier("techreborn","nitrofuel"));
        getOrCreateTagBuilder(TagKeys.GAS).addOptional(new Identifier("techreborn","biofuel"));
    }
}
