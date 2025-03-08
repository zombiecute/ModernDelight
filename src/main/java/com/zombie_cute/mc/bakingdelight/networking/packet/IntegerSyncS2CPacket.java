package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.decor.WoodenPlateBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IntegerSyncS2CPacket {
    @Environment(EnvType.CLIENT)
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender sender){
        int a = buf.readInt();
        BlockPos pos = buf.readBlockPos();
        if (client.world != null) {
            if (client.world.getBlockEntity(pos) instanceof WoodenPlateBlockEntity blockEntity){
                blockEntity.setRotate(a);
            }
        }
    }
    public static void send(BlockPos pos, int i, World world) {
        if (world instanceof ServerWorld serverWorld) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(i);
            data.writeBlockPos(pos);
            for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, pos)) {
                ServerPlayNetworking.send(player, NetworkHandler.INTEGER_SYNC, data);
            }
        }
    }
}
