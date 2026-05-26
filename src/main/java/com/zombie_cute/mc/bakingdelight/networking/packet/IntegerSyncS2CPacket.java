package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.decor.WoodenPlateBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record IntegerSyncS2CPacket(int a, BlockPos pos) implements CustomPayload {
    public static final Id<IntegerSyncS2CPacket> ID = new Id<>(NetworkHandler.INTEGER_SYNC);
    public static final PacketCodec<RegistryByteBuf, IntegerSyncS2CPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.INTEGER, IntegerSyncS2CPacket::a,
                    BlockPos.PACKET_CODEC, IntegerSyncS2CPacket::pos,
                    IntegerSyncS2CPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    @Environment(EnvType.CLIENT)
    public static void receive(MinecraftClient client, int a, BlockPos pos){
        if (client.world != null) {
            if (client.world.getBlockEntity(pos) instanceof WoodenPlateBlockEntity blockEntity){
                blockEntity.setRotate(a);
            }
        }
    }
    public static void send(BlockPos pos, int i, World world) {
        if (world instanceof ServerWorld serverWorld) {
            for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, pos)) {
                ServerPlayNetworking.send(player, new IntegerSyncS2CPacket(i,pos));
            }
        }
    }
}
