package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.CuisineTableBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class UpdateInventoryC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos pos = buf.readBlockPos();
        ItemStack itemStack = buf.readItemStack();
        server.execute(() -> {
            BlockEntity blockEntity = player.getWorld().getBlockEntity(pos);
            if (blockEntity instanceof CuisineTableBlockEntity cuisineTableBlockEntity) {
                cuisineTableBlockEntity.getItems().set(2, itemStack);
                blockEntity.markDirty();
            } else if (blockEntity instanceof ElectriciansDeskBlockEntity electriciansDeskBlockEntity){
                electriciansDeskBlockEntity.getItems().set(8,itemStack);
                electriciansDeskBlockEntity.markDirty();
            }
        });
    }
    public static void send(BlockPos pos, ItemStack itemStack) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeBlockPos(pos);
        buf.writeItemStack(itemStack);
        ClientPlayNetworking.send(NetworkHandler.UPDATE_INVENTORY_PACKET_ID, buf);
    }
}
