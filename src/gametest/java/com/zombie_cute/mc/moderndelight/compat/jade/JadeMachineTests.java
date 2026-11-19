package com.zombie_cute.mc.moderndelight.compat.jade;

import com.mojang.authlib.GameProfile;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.multiblock.LargeMachinePartBlock;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import snownee.jade.api.BlockAccessor;
import snownee.jade.impl.BlockAccessorImpl;

import java.util.UUID;

public class JadeMachineTests implements FabricGameTest {
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void fermenterPartRequestsRootDetails(TestContext context) {
        var world = context.getWorld();
        var fermenter = ModBlocks.INDUSTRIAL_FERMENTER;
        BlockPos root = context.getAbsolutePos(new BlockPos(3, 2, 3));
        var rootState = fermenter.getDefaultState();
        world.setBlockState(root, rootState);
        fermenter.onPlaced(world, root, rootState, null, new ItemStack(fermenter));

        BlockPos partPos = root.up();
        var partState = world.getBlockState(partPos);
        context.assertTrue(partState.getBlock() instanceof LargeMachinePartBlock, "The test must target a real part");
        BlockHitResult hit = new BlockHitResult(Vec3d.ofCenter(partPos), Direction.NORTH, partPos, false);
        var player = new ServerPlayerEntity(world.getServer(), world, new GameProfile(UUID.randomUUID(), "JadeTest"));
        player.setPosition(Vec3d.ofCenter(root.north(2)));
        var original = new BlockAccessorImpl.Builder().level(world).player(player)
                .hit(hit).blockState(partState).serverConnected(true).build();
        var redirected = (BlockAccessor) JadePlugin.redirectMachinePart(original, BlockAccessorImpl.Builder::new);
        context.assertTrue(redirected.getPosition().equals(root) && redirected.getBlock() == fermenter
                        && redirected.getBlockEntity() == world.getBlockEntity(root),
                "Jade must use the root position, state and block entity");
        context.assertTrue(original.getPosition().equals(partPos) && redirected.getHitResult().getPos().equals(hit.getPos()),
                "Only the Jade target changes; the actual hit point stays on the part");

        var request = new PacketByteBuf(Unpooled.buffer());
        try {
            redirected.toNetwork(request);
            BlockAccessorImpl.handleRequest(request, player, Runnable::run, data -> {
                context.assertTrue(redirected.verifyData(data), "Jade's reply must match the root position");
                context.assertTrue(!data.getList("MachineDetails", NbtElement.COMPOUND_TYPE).isEmpty(),
                        "The existing machine provider must return fermenter details");
                context.complete();
            });
        } finally {
            request.release();
        }
    }
}
