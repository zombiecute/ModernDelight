package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
import com.zombie_cute.mc.bakingdelight.util.block_util.FluidStorageAble;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record FluidSyncS2CPacket(long amount, String fluidName, BlockPos pos) implements CustomPayload {

    public static final Id<FluidSyncS2CPacket> ID = new Id<>(NetworkHandler.FLUID_SYNC);
    public static final PacketCodec<RegistryByteBuf, FluidSyncS2CPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.VAR_LONG, FluidSyncS2CPacket::amount,
                    PacketCodecs.STRING, FluidSyncS2CPacket::fluidName,
                    BlockPos.PACKET_CODEC, FluidSyncS2CPacket::pos,
                    FluidSyncS2CPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    @Environment(EnvType.CLIENT)
    public static void receive(MinecraftClient client, long amount, String s, BlockPos pos) {
        FluidVariant fluidVariant = FluidVariant.blank();
        try {
            fluidVariant = FluidVariant.of(Registries.FLUID.get(Identifier.of(s)));
        } catch (Exception ignored){}
        if (client.world != null && client.world.getBlockEntity(pos) instanceof FluidStorageAble block) {
            block.setFluid(new FluidStack(fluidVariant,amount));
        }
    }
    public static void send(BlockPos pos, FluidStack fluid, World world) {
        if (world instanceof ServerWorld){
            for(ServerPlayerEntity serverPlayerEntity : PlayerLookup.tracking((ServerWorld) world,pos)){
                ServerPlayNetworking.send(serverPlayerEntity,
                        new FluidSyncS2CPacket(fluid.amount_droplets,Registries.FLUID.getId(fluid.getFluidVariant().getFluid()).toString(),pos));
            }
        }
    }
}
