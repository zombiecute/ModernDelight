package com.zombie_cute.mc.moderndelight.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/**
 * Originally by Flandre923
 **/
public class FluidStack {
    public FluidVariant fluidVariant;
    public long amount_droplets;
    public FluidStack(FluidVariant fluidVariant, long amount_droplets){
        this.fluidVariant = fluidVariant;
        this.amount_droplets = amount_droplets;
    }

    public static Fluid getFluidFromString(String fluid_string) {
        return Registries.FLUID.getOrEmpty(Identifier.tryParse(fluid_string)).orElse(Fluids.EMPTY);
    }

    public FluidVariant getFluidVariant() {
        return fluidVariant;
    }

    public void setFluidVariant(FluidVariant fluidVariant) {
        this.fluidVariant = fluidVariant;
    }

    public long getAmount() {
        return amount_droplets;
    }

    public void setAmount(long amount_droplets) {
        this.amount_droplets = amount_droplets;
    }

    public static long convertDropletsToMb(long droplets){
        return droplets/81;
    }

    public static long convertMbToDroplets(long mb){
        return mb*81;
    }
}
