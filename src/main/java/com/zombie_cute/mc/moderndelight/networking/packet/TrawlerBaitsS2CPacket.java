package com.zombie_cute.mc.moderndelight.networking.packet;

import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerLootPreview;
import com.zombie_cute.mc.moderndelight.networking.NetworkHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public final class TrawlerBaitsS2CPacket {
    private TrawlerBaitsS2CPacket() {
    }

    public static void send(ServerPlayerEntity player) {
        // The first vanilla data sync can precede the client's channel registration.
        PacketByteBuf buf = PacketByteBufs.create();
        MinecraftServer server = player.getServer();
        if (server != null) {
            buf.writeCollection(TrawlerLootPreview.create(server), (buffer, preview) -> preview.write(buffer));
        }
        ServerPlayNetworking.send(player, NetworkHandler.TRAWLER_BAITS_SYNC, buf);
    }
}
