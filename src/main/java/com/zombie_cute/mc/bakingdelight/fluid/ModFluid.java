package com.zombie_cute.mc.bakingdelight.fluid;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.fluid.custom.CreamFluid;
import com.zombie_cute.mc.bakingdelight.fluid.custom.VegetableOilFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluid {
    public static final FlowableFluid STILL_CREAM = registerFluid("still_cream",new CreamFluid.Still());
    public static final FlowableFluid FLOWING_CREAM = registerFluid("flowing_cream",new CreamFluid.Flowing());
    public static final FlowableFluid FLOWING_VEGETABLE_OIL = registerFluid("flowing_vegetable_oil", new VegetableOilFluid.Flowing());
    public static final FlowableFluid STILL_VEGETABLE_OIL = registerFluid("still_vegetable_oil", new VegetableOilFluid.Still());
    public static FlowableFluid registerFluid(String name, FlowableFluid fluid){
         return Registry.register(Registries.FLUID, Identifier.of(Bakingdelight.MOD_ID,name),fluid);
    }
    public static void registerModFluid(){
         Bakingdelight.LOGGER.info("Registering Mod Fluid for " + Bakingdelight.MOD_ID);
    }
}
