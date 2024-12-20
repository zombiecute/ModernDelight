package com.zombie_cute.mc.bakingdelight.util.custom_pay_load;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record SpawnXPPayLoad(BlockPos pos) implements CustomPayload {
    public static final Id<SpawnXPPayLoad> ID =
            new Id<>(Bakingdelight.SPAWN_XP_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, SpawnXPPayLoad> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, SpawnXPPayLoad::pos,
                    SpawnXPPayLoad::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
