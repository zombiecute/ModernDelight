package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.CuisineTableBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public record UpdateInventoryC2SPacket(BlockPos pos, ItemStack itemStack) implements CustomPayload {
    public static final Id<UpdateInventoryC2SPacket> ID = new Id<>(NetworkHandler.UPDATE_INVENTORY_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, UpdateInventoryC2SPacket> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, UpdateInventoryC2SPacket::pos,
                    ItemStack.OPTIONAL_PACKET_CODEC, UpdateInventoryC2SPacket::itemStack,
                    UpdateInventoryC2SPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    public static void receive(MinecraftServer server, ServerPlayerEntity player,BlockPos pos, ItemStack itemStack) {
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
    @Environment(EnvType.CLIENT)
    public static void send(BlockPos pos, ItemStack itemStack) {
        ClientPlayNetworking.send(new UpdateInventoryC2SPacket(pos,itemStack));
    }
}
