package com.zombie_cute.mc.bakingdelight.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

public class FluidUtil {
    public FluidVariant fluidVariant;
    public long amount;
    public FluidUtil(FluidVariant fluidVariant,long amount){
        this.fluidVariant = fluidVariant;
        this.amount = amount;
    }

    public FluidVariant getFluidVariant() {
        return fluidVariant;
    }

    public void setFluidVariant(FluidVariant fluidVariant) {
        this.fluidVariant = fluidVariant;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public static long convertDropletsToMb(long droplets){
        return droplets/81;
    }

    public static long convertMbToDroplets(long mb){
        return mb*81;
    }
}
