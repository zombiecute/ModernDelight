package com.zombie_cute.mc.bakingdelight.util.block_util;

import com.zombie_cute.mc.bakingdelight.networking.packet.FluidSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface FluidStorageAble {
    SingleVariantStorage<FluidVariant> getFluidStorage();
    default void sendFluidPacket(World world, BlockPos pos){
        FluidSyncS2CPacket.send(pos,new FluidStack(getFluidStorage().variant,getFluidStorage().amount),world);
    }

    default void setFluid(FluidStack fluidStack){
        getFluidStorage().variant = fluidStack.getFluidVariant();
        getFluidStorage().amount = fluidStack.getAmount();
    };
}
