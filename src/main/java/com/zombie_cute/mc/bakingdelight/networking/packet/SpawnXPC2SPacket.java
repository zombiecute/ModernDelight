package com.zombie_cute.mc.bakingdelight.networking.packet;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.AdvanceFurnaceBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.FreezerBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.OvenBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnXPC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos pos = buf.readBlockPos();
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
    public static void send(BlockPos pos) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeBlockPos(pos);
        ClientPlayNetworking.send(NetworkHandler.SPAWN_XP_PACKET_ID, buf);
    }
}
