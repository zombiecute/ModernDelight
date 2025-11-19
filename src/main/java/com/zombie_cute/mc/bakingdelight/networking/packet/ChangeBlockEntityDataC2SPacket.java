package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.CuisineTableBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.TeslaCoilBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.ACDCConverterBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class ChangeBlockEntityDataC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos pos = buf.readBlockPos();
        int[] array = buf.readIntArray();
        server.execute(() -> {
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
            } else if (blockEntity instanceof ElectriciansDeskBlockEntity electriciansDeskBlockEntity) {
                switch (array[0]){
                    case 1 -> electriciansDeskBlockEntity.setCanCraft(true);
                    case 2 -> {
                        electriciansDeskBlockEntity.setCanCraft(false);
                        for (int i = 0;i<6;i++){
                            electriciansDeskBlockEntity.removeStack(i,1);
                        }
                        player.getWorld().playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    case 3 -> {
                        electriciansDeskBlockEntity.removeStack(6, 1);
                        electriciansDeskBlockEntity.removeStack(7, 1);
                        electriciansDeskBlockEntity.setOccupied(true);
                    }
                    case 4 -> electriciansDeskBlockEntity.setOccupied(false);
                    case 5 -> electriciansDeskBlockEntity.setCanCraft(false);
                }
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
