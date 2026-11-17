package com.zombie_cute.mc.moderndelight.compat.trawler;

import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBaitPreview;
import com.zombie_cute.mc.moderndelight.networking.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.util.List;

@Environment(EnvType.CLIENT)
public final class TrawlerClientData {
    private static volatile List<TrawlerBaitPreview> previews = List.of();

    private TrawlerClientData() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkHandler.TRAWLER_BAITS_SYNC,
                (client, handler, buf, sender) -> {
                    List<TrawlerBaitPreview> received = List.copyOf(buf.readList(TrawlerBaitPreview::read));
                    // Queued before vanilla tag/recipe synchronization triggers either viewer's reload.
                    client.execute(() -> previews = received);
                });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> previews = List.of());
    }

    public static List<TrawlerBaitPreview> getPreviews() {
        return previews;
    }
}
