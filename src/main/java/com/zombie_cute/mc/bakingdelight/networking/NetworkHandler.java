package com.zombie_cute.mc.bakingdelight.networking;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.networking.packet.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class NetworkHandler {
    public static final Identifier UPDATE_INVENTORY_PACKET_ID = Identifier.of(ModernDelightMain.MOD_ID,"update_inventory");
    public static final Identifier SPAWN_XP_PACKET_ID = Identifier.of(ModernDelightMain.MOD_ID,"spawn_xp");
    public static final Identifier CHANGE_BLOCK_ENTITY_DATA_PACKET_ID = Identifier.of(ModernDelightMain.MOD_ID,"change_block_entity_data");
    public static final Identifier ITEM_SYNC = Identifier.of(ModernDelightMain.MOD_ID,"item_sync");
    public static final Identifier INTEGER_SYNC = Identifier.of(ModernDelightMain.MOD_ID,"integer_sync");
    public static final Identifier FLUID_SYNC = Identifier.of(ModernDelightMain.MOD_ID,"fluid_sync");

    public static void registerC2SPacket(){
        ModernDelightMain.LOGGER.info("Registering C2S receivers for {}", ModernDelightMain.MOD_ID);
        ServerPlayNetworking.registerGlobalReceiver(UpdateInventoryC2SPacket.ID,
                (payload, context) -> UpdateInventoryC2SPacket.receive(
                        context.server(),context.player(),payload.pos(),payload.itemStack()
                ));
        ServerPlayNetworking.registerGlobalReceiver(SpawnXPC2SPacket.ID,
                (payload, context) -> SpawnXPC2SPacket.receive(
                        context.server(),context.player(),payload.pos()
                ));
        ServerPlayNetworking.registerGlobalReceiver(ChangeBlockEntityDataC2SPacket.ID,
                (payload, context) -> ChangeBlockEntityDataC2SPacket.receive(
                    context.server(), context.player(), payload.pos(), payload.array()
                ));
    }
    @Environment(EnvType.CLIENT)
    public static void registerS2CPacket(){
        ModernDelightMain.LOGGER.info("Registering S2C receivers for {}", ModernDelightMain.MOD_ID);
        ClientPlayNetworking.registerGlobalReceiver(ItemStackSyncS2CPacket.ID,
                (payload, context) ->  ItemStackSyncS2CPacket.receive(
                        context.client(), payload.inventory(),payload.pos()
                ));
        ClientPlayNetworking.registerGlobalReceiver(IntegerSyncS2CPacket.ID,
                (payload, context) -> IntegerSyncS2CPacket.receive(
                        context.client(),payload.a(),payload.pos()
                ));
        ClientPlayNetworking.registerGlobalReceiver(FluidSyncS2CPacket.ID,
                (packet, context) -> FluidSyncS2CPacket.receive(
                        context.client(), packet.amount(), packet.fluidName(), packet.pos()
                ));
    }
    public static void registerPayloadTypes() {
        // C2S
        PayloadTypeRegistry.playC2S().register(UpdateInventoryC2SPacket.ID, UpdateInventoryC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(SpawnXPC2SPacket.ID, SpawnXPC2SPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(ChangeBlockEntityDataC2SPacket.ID, ChangeBlockEntityDataC2SPacket.CODEC);

        // S2C
        PayloadTypeRegistry.playS2C().register(ItemStackSyncS2CPacket.ID, ItemStackSyncS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(IntegerSyncS2CPacket.ID, IntegerSyncS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(FluidSyncS2CPacket.ID, FluidSyncS2CPacket.CODEC);
    }
}
