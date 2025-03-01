package com.zombie_cute.mc.bakingdelight.networking;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.networking.packet.ChangeBlockEntityDataC2SPacket;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.networking.packet.SpawnXPC2SPacket;
import com.zombie_cute.mc.bakingdelight.networking.packet.UpdateInventoryC2SPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class NetworkHandler {
    public static final Identifier UPDATE_INVENTORY_PACKET_ID = new Identifier(ModernDelightMain.MOD_ID,"update_inventory");
    public static final Identifier SPAWN_XP_PACKET_ID = new Identifier(ModernDelightMain.MOD_ID,"spawn_xp");
    public static final Identifier CHANGE_BLOCK_ENTITY_DATA_PACKET_ID = new Identifier(ModernDelightMain.MOD_ID,"change_block_entity_data");
    public static final Identifier ITEM_SYNC = new Identifier(ModernDelightMain.MOD_ID,"item_sync");

    public static void registerC2SPacket(){
        ModernDelightMain.LOGGER.info("Registering C2S receivers for {}", ModernDelightMain.MOD_ID);
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_INVENTORY_PACKET_ID, UpdateInventoryC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(SPAWN_XP_PACKET_ID, SpawnXPC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(CHANGE_BLOCK_ENTITY_DATA_PACKET_ID, ChangeBlockEntityDataC2SPacket::receive);
    }
    @Environment(EnvType.CLIENT)
    public static void registerS2CPacket(){
        ModernDelightMain.LOGGER.info("Registering S2C receivers for {}", ModernDelightMain.MOD_ID);
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
}
