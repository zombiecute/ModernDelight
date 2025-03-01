package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemStackSyncS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender sender){
        int size = buf.readInt();
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size,ItemStack.EMPTY);
        for(int i =0;i<size;i++){
            list.set(i,buf.readItemStack());
        }
        BlockPos pos = buf.readBlockPos();
        if (client.world != null && client.world.getBlockEntity(pos) instanceof ImplementedInventory block) {
            block.setInventory(list);
        }
    }
    public static void send(BlockPos pos, DefaultedList<ItemStack> list, World world) {
        if(!world.isClient()){
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(list.size());
            for (ItemStack itemStack : list) {
                data.writeItemStack(itemStack);
            }
            data.writeBlockPos(pos);
            for(ServerPlayerEntity serverPlayerEntity : PlayerLookup.tracking((ServerWorld) world,pos)){
                ServerPlayNetworking.send(serverPlayerEntity,NetworkHandler.ITEM_SYNC,data);
            }
        }
    }
}
