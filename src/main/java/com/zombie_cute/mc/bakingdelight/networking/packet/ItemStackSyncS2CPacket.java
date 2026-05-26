package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public record ItemStackSyncS2CPacket(BlockPos pos, List<ItemStack> inventory) implements CustomPayload {
    public static final Id<ItemStackSyncS2CPacket> ID = new Id<>(NetworkHandler.ITEM_SYNC);
    public static final PacketCodec<RegistryByteBuf, ItemStackSyncS2CPacket> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, ItemStackSyncS2CPacket::pos,
                    ItemStack.OPTIONAL_LIST_PACKET_CODEC, ItemStackSyncS2CPacket::inventory,
                    ItemStackSyncS2CPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    @Environment(EnvType.CLIENT)
    public static void receive(MinecraftClient client,  List<ItemStack> list, BlockPos pos) {
        if (client.world != null && client.world.getBlockEntity(pos) instanceof ImplementedInventory block) {
            DefaultedList<ItemStack> items = DefaultedList.of();
            items.addAll(list);
            block.setInventory(items);
        }
    }
    public static void send(BlockPos pos, DefaultedList<ItemStack> list, World world) {
        if (world instanceof ServerWorld){
            for(ServerPlayerEntity serverPlayerEntity : PlayerLookup.tracking((ServerWorld) world,pos)){
                ServerPlayNetworking.send(serverPlayerEntity,new ItemStackSyncS2CPacket(pos, list));
            }
        }
    }
}
