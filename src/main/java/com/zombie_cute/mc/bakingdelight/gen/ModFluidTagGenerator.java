package com.zombie_cute.mc.bakingdelight.gen;

import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;

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
        // Oil
        getOrCreateTagBuilder(TagKeys.OIL).add(ModFluid.FLOWING_VEGETABLE_OIL);
        getOrCreateTagBuilder(TagKeys.OIL).add(ModFluid.STILL_VEGETABLE_OIL);
    }
}
