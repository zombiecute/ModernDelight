package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.AdvanceFurnaceBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.FreezerBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.OvenBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record SpawnXPC2SPacket(BlockPos pos) implements CustomPayload {
    public static final Id<SpawnXPC2SPacket> ID = new Id<>(NetworkHandler.SPAWN_XP_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, SpawnXPC2SPacket> CODEC =
            PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, SpawnXPC2SPacket::pos,
                    SpawnXPC2SPacket::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
    public static void receive(MinecraftServer server, ServerPlayerEntity player, BlockPos pos) {
        server.execute(() -> {
            World world = player.getWorld();
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AdvanceFurnaceBlockEntity entity) {
                if (entity.getExperience() != 0) {
                    ExperienceOrbEntity xp = new ExperienceOrbEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), entity.getExperience());
                    world.spawnEntity(xp);
                    entity.setExperience(0);
                }
            } else if (blockEntity instanceof OvenBlockEntity entity) {
                if (entity.getExperience() != 0) {
                    ExperienceOrbEntity xp = new ExperienceOrbEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), entity.getExperience());
                    world.spawnEntity(xp);
                    entity.setExperience(0);
                }
            } else if (blockEntity instanceof FreezerBlockEntity entity) {
                if (entity.getExperience() != 0) {
                    ExperienceOrbEntity xp = new ExperienceOrbEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), entity.getExperience());
                    world.spawnEntity(xp);
                    entity.setExperience(0);
                }
            }
        });
    }
    @Environment(EnvType.CLIENT)
    public static void send(BlockPos pos) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeBlockPos(pos);
        ClientPlayNetworking.send(new SpawnXPC2SPacket(pos));
    }
}
