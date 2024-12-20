package com.zombie_cute.mc.bakingdelight.util.custom_pay_load;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record ChangeBlockEntityDataPayLoad(BlockPos pos, byte[] array) implements CustomPayload {
    public static final Id<ChangeBlockEntityDataPayLoad> ID =
            new Id<>(Bakingdelight.CHANGE_BLOCK_ENTITY_DATA_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, ChangeBlockEntityDataPayLoad> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, ChangeBlockEntityDataPayLoad::pos,
                    PacketCodecs.BYTE_ARRAY, ChangeBlockEntityDataPayLoad::array,
                    ChangeBlockEntityDataPayLoad::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
