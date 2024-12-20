package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.util.custom_pay_load.ChangeBlockEntityDataPayLoad;
import com.zombie_cute.mc.bakingdelight.util.custom_pay_load.SpawnXPPayLoad;
import com.zombie_cute.mc.bakingdelight.util.custom_pay_load.UpdateInventoryPayLoad;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.BlockPos;

public class NetworkHandler {
    public static void sendUpdateInventoryPacket(BlockPos pos, String item, int count) {
        ClientPlayNetworking.send(new UpdateInventoryPayLoad(pos,item,count));
    }
    public static void sendUpdateInventoryPacket(BlockPos pos, String item) {
        ClientPlayNetworking.send(new UpdateInventoryPayLoad(pos,item,1));
    }
    public static void sendSpawnXPPacket(BlockPos pos) {
        ClientPlayNetworking.send(new SpawnXPPayLoad(pos));
    }
    public static void sendChangeBlockEntityDataPacket(BlockPos pos, byte[] array) {
        ClientPlayNetworking.send(new ChangeBlockEntityDataPayLoad(pos,array));
    }
}
