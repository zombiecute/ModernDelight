package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.CuisineTableBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.TeslaCoilBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.ACDCConverterBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public record ChangeBlockEntityDataC2SPacket(BlockPos pos, byte[] array) implements CustomPayload {
    public static final Id<ChangeBlockEntityDataC2SPacket> ID = new Id<>(NetworkHandler.CHANGE_BLOCK_ENTITY_DATA_PACKET_ID);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    public static final PacketCodec<RegistryByteBuf, ChangeBlockEntityDataC2SPacket> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, ChangeBlockEntityDataC2SPacket::pos,
                    PacketCodecs.BYTE_ARRAY, ChangeBlockEntityDataC2SPacket::array,
                    ChangeBlockEntityDataC2SPacket::new);
    public static void receive(MinecraftServer server, ServerPlayerEntity player, BlockPos pos, byte[] array) {
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
        byte[] array2 = new byte[array.length];
        for (int i = 0; i < array.length; i++){
            array2[i] = (byte)array[i];
        }
        ClientPlayNetworking.send(new ChangeBlockEntityDataC2SPacket(pos, array2));
    }
}
