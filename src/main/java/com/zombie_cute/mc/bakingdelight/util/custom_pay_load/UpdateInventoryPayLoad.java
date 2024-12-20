package com.zombie_cute.mc.bakingdelight.util.custom_pay_load;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record UpdateInventoryPayLoad(BlockPos pos, String item, int count) implements CustomPayload {
    public static final CustomPayload.Id<UpdateInventoryPayLoad> ID =
            new CustomPayload.Id<>(Bakingdelight.UPDATE_INVENTORY_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, UpdateInventoryPayLoad> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC,UpdateInventoryPayLoad::pos,
                    PacketCodecs.STRING,UpdateInventoryPayLoad::item,
                    PacketCodecs.INTEGER,UpdateInventoryPayLoad::count,
                    UpdateInventoryPayLoad::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
