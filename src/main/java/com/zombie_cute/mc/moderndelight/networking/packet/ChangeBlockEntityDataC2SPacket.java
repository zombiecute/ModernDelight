package com.zombie_cute.mc.moderndelight.networking.packet;

import com.zombie_cute.mc.moderndelight.block.fermenter.IndustrialFermenterBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.CuisineTableBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.TeslaCoilBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.ACDCConverterBlockEntity;
import com.zombie_cute.mc.moderndelight.networking.NetworkHandler;
import com.zombie_cute.mc.moderndelight.screen.custom.ElectriciansDeskScreenHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class ChangeBlockEntityDataC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos pos = buf.readBlockPos();
        int[] array = buf.readIntArray();
        if (array.length == 0) {
            return;
        }
        server.execute(() -> {
            // Desk actions must belong to the currently open menu, including minigame callbacks.
            if (player.currentScreenHandler instanceof ElectriciansDeskScreenHandler screen
                    && screen.blockEntity.getPos().equals(pos)) {
                screen.handleAction(player, array[0]);
                return;
            }
            BlockEntity blockEntity = player.getWorld().getBlockEntity(pos);
            if (blockEntity instanceof TeslaCoilBlockEntity teslaCoilBlockEntity){
                switch (array[0]){
                    case 1 -> teslaCoilBlockEntity.setShowParticle(false);
                    case 2 -> teslaCoilBlockEntity.setShowParticle(true);
                }
            } else if (blockEntity instanceof ACDCConverterBlockEntity acdc) {
                switch (array[0]){
                    case 1 -> acdc.addWorkSpeed(1);
                    case 2 -> acdc.reduceWorkSpeed(1);
                    case 3 -> acdc.addWorkSpeed(5);
                    case 4 -> acdc.reduceWorkSpeed(5);
                }
                switch (array[1]){
                    case 1 -> acdc.setACMode(false);
                    case 2 -> acdc.setACMode(true);
                }
                acdc.markDirty();
            } else if (blockEntity instanceof IceCreamMakerBlockEntity iceCreamMakerBlockEntity) {
                switch (array[0]){
                    case 1 -> iceCreamMakerBlockEntity.changeIceCream1();
                    case 2 -> iceCreamMakerBlockEntity.changeIceCream2();
                    case 3 -> iceCreamMakerBlockEntity.changeIceCream3();
                }
            } else if (blockEntity instanceof CuisineTableBlockEntity cuisineTableBlockEntity) {
                switch (array[0]){
                    case 1 -> cuisineTableBlockEntity.setCanOpen(true);
                }
            } else if (blockEntity instanceof IndustrialFermenterBlockEntity industrialFermenterBlockEntity) {
                switch (array[0]){
                    case 1 -> industrialFermenterBlockEntity.setClosing();
                    case 2 -> industrialFermenterBlockEntity.reduceFix();
                    case 3 -> industrialFermenterBlockEntity.addFix();
                    case 4 -> industrialFermenterBlockEntity.clear();
                }
            }
        });
    }
    @Environment(EnvType.CLIENT)
    public static void send(BlockPos pos, int[] array) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeBlockPos(pos);
        buf.writeIntArray(array);
        ClientPlayNetworking.send(NetworkHandler.CHANGE_BLOCK_ENTITY_DATA_PACKET_ID, buf);
    }
}
