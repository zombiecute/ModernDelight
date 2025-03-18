package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
import com.zombie_cute.mc.bakingdelight.util.block_util.FluidStorageAble;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidSyncS2CPacket {
    @Environment(EnvType.CLIENT)
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender sender){
        long amount = buf.readLong();
        String s = buf.readString();
        FluidVariant fluidVariant = FluidVariant.blank();
        try {
            fluidVariant = FluidVariant.of(Registries.FLUID.get(new Identifier(s)));
        } catch (Exception ignored){}
        BlockPos pos = buf.readBlockPos();
        if (client.world != null && client.world.getBlockEntity(pos) instanceof FluidStorageAble block) {
            block.setFluid(new FluidStack(fluidVariant,amount));
        }
    }
    public static void send(BlockPos pos, FluidStack fluid, World world) {
        if (world instanceof ServerWorld){
            for(ServerPlayerEntity serverPlayerEntity : PlayerLookup.tracking((ServerWorld) world,pos)){
                PacketByteBuf data = PacketByteBufs.create();
                data.writeLong(fluid.amount_droplets);
                data.writeString(Registries.FLUID.getId(fluid.getFluidVariant().getFluid()).toString());
                data.writeBlockPos(pos);
                ServerPlayNetworking.send(serverPlayerEntity,NetworkHandler.FLUID_SYNC,data);
            }
        }
    }
}
