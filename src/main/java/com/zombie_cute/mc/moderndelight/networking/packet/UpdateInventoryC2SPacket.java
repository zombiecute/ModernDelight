package com.zombie_cute.mc.moderndelight.networking.packet;

import com.zombie_cute.mc.moderndelight.networking.NetworkHandler;
import com.zombie_cute.mc.moderndelight.screen.custom.CuisineTableScreenHandler;
import com.zombie_cute.mc.moderndelight.screen.custom.ElectriciansDeskScreenHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class UpdateInventoryC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        final int syncId;
        final BlockPos pos;
        final Identifier recipeId;
        try {
            syncId = buf.readVarInt();
            pos = buf.readBlockPos();
            recipeId = buf.readBoolean() ? buf.readIdentifier() : null;
            if (buf.isReadable()) {
                return;
            }
        } catch (RuntimeException malformedPacket) {
            return;
        }
        server.execute(() -> {
            if (!player.isAlive() || player.isSpectator() || player.currentScreenHandler.syncId != syncId) {
                return;
            }
            // Resolve the target through the open menu; never load a client-supplied block position.
            if (player.currentScreenHandler instanceof CuisineTableScreenHandler screen
                    && screen.blockEntity.getPos().equals(pos) && screen.canUse(player)) {
                screen.selectRecipe(recipeId);
            } else if (player.currentScreenHandler instanceof ElectriciansDeskScreenHandler screen
                    && recipeId == null && screen.blockEntity.getPos().equals(pos) && screen.canUse(player)) {
                screen.craft(player);
            }
        });
    }
    @Environment(EnvType.CLIENT)
    public static void send(int syncId, BlockPos pos, @Nullable Identifier recipeId) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeVarInt(syncId);
        buf.writeBlockPos(pos);
        buf.writeBoolean(recipeId != null);
        if (recipeId != null) {
            buf.writeIdentifier(recipeId);
        }
        ClientPlayNetworking.send(NetworkHandler.UPDATE_INVENTORY_PACKET_ID, buf);
    }
}
