package com.zombie_cute.mc.moderndelight.block.multiblock;

import com.mojang.authlib.GameProfile;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.fermenter.IndustrialFermenterBlockEntity;
import com.zombie_cute.mc.moderndelight.screen.custom.IndustrialFermenterScreenHandler;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.Hand;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.GameMode;
import net.minecraft.world.RaycastContext;

import java.util.UUID;

public class LargeMachineTests implements FabricGameTest {
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void allFacingsHaveLocalShapesRaycastsAndCollisions(TestContext context) {
        ServerPlayerEntity player = player(context, false);
        for (LargeMachineBlock machine : machines()) {
            for (Direction facing : Direction.Type.HORIZONTAL) {
                BlockPos pos = prepare(context);
                BlockState state = place(context, pos, machine, facing);
                VoxelShape combined = VoxelShapes.empty();
                context.assertTrue(machine.getPartShapes(state).size() > 1,
                        "Every cell touched by the original model must be represented");
                for (var entry : machine.getPartShapes(state).entrySet()) {
                    BlockPos offset = entry.getKey();
                    BlockPos partPos = pos.add(offset);
                    BlockState part = context.getWorld().getBlockState(partPos);
                    VoxelShape shape = part.getCollisionShape(context.getWorld(), partPos);
                    Box bounds = shape.getBoundingBox();
                    context.assertTrue(bounds.minX >= 0 && bounds.minY >= 0 && bounds.minZ >= 0
                                    && bounds.maxX <= 1 && bounds.maxY <= 1 && bounds.maxZ <= 1,
                            "Collision shapes must stay inside the cell that owns them");
                    combined = VoxelShapes.union(combined, shape.offset(offset.getX(), offset.getY(), offset.getZ()));
                    Vec3d center = shape.getBoundingBoxes().get(0).getCenter().add(Vec3d.of(partPos));
                    context.assertTrue(!context.getWorld().isSpaceEmpty(new Box(center, center).expand(0.01)),
                            "World collision queries must see even the farthest model cells");
                    BlockHitResult hit = context.getWorld().raycast(new RaycastContext(center, center.add(0.001, 0.001, 0.001),
                            RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
                    context.assertTrue(hit.getBlockPos().equals(partPos),
                            "A ray must report the actual cell under the crosshair");
                    Vec3d delta = hit.getPos().subtract(Vec3d.ofCenter(hit.getBlockPos()));
                    context.assertTrue(Math.abs(delta.x) < 1.0000001 && Math.abs(delta.y) < 1.0000001
                                    && Math.abs(delta.z) < 1.0000001,
                            "Every hit must satisfy vanilla UseItemOnPacket position validation");
                    context.assertTrue(part.getBlock().getPickStack(context.getWorld(), partPos, part).isOf(machine.asItem()),
                            "Picking a remote part must give the machine item");
                }
                context.assertTrue(!VoxelShapes.matchesAnywhere(combined, physicalShape(machine, state), BooleanBiFunction.NOT_SAME),
                        "Splitting cells must preserve the complete original volume");
            }
        }
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void upperFermenterClicksPassTheRealPacketHandler(TestContext context) {
        ServerPlayerEntity player = player(context, false);
        LargeMachineBlock machine = (LargeMachineBlock) ModBlocks.INDUSTRIAL_FERMENTER;
        for (Direction facing : Direction.Type.HORIZONTAL) {
            BlockPos pos = prepare(context);
            place(context, pos, machine, facing);
            // This is above the root cell's allowed hit range and reproduced the original rejection.
            BlockState state = context.getWorld().getBlockState(pos);
            Vec3d target = machine.getPartShape(state, new BlockPos(0, 2, 0)).getBoundingBoxes().get(0).getCenter().add(Vec3d.of(pos.up(2)));
            BlockHitResult hit = raycast(context, target, facing, player);
            context.assertTrue(hit.getBlockPos().equals(pos.up(2)), "The upper edge must belong to its own cell");
            player.networkHandler.onPlayerInteractBlock(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, hit, 1));
            context.assertTrue(player.currentScreenHandler instanceof IndustrialFermenterScreenHandler,
                    "Vanilla packet validation must accept the click and open the owner's menu");
            player.closeHandledScreen();
        }
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void placementChecksTheWholeFootprintAndWorldHeight(TestContext context) {
        for (LargeMachineBlock machine : machines()) {
            for (Direction facing : Direction.Type.HORIZONTAL) {
                BlockPos pos = prepare(context);
                BlockState state = machine.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);
                if (machine == ModBlocks.FAN_BLADE) {
                    context.getWorld().setBlockState(pos.offset(facing.getOpposite()), Blocks.STONE.getDefaultState());
                }
                context.assertTrue(state.canPlaceAt(context.getWorld(), pos), "An empty footprint must accept placement");
                context.getWorld().setBlockState(pos.up(2), Blocks.STONE.getDefaultState());
                context.assertTrue(!state.canPlaceAt(context.getWorld(), pos), "A remote obstacle must reject placement");
                context.getWorld().setBlockState(pos.up(2), Blocks.WATER.getDefaultState());
                context.assertTrue(!state.canPlaceAt(context.getWorld(), pos), "Placement must not erase remote fluids");
                BlockPos ceiling = new BlockPos(pos.getX(), context.getWorld().getTopY() - 1, pos.getZ());
                context.assertTrue(!state.canPlaceAt(context.getWorld(), ceiling), "The whole model must fit below the build limit");
            }
        }
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void survivalMiningAnyPartDropsOneMachineAndItsInventory(TestContext context) {
        ServerPlayerEntity player = player(context, false);
        for (LargeMachineBlock machine : machines()) {
            BlockPos pos = prepare(context);
            place(context, pos, machine, Direction.NORTH);
            if (context.getWorld().getBlockEntity(pos) instanceof IndustrialFermenterBlockEntity fermenter) {
                fermenter.setStack(0, new ItemStack(Items.DIAMOND, 5));
            }
            ItemStack pickaxe = new ItemStack(Items.DIAMOND_PICKAXE);
            player.equipStack(EquipmentSlot.MAINHAND, pickaxe);
            context.assertTrue(player.interactionManager.tryBreakBlock(pos.up(2)), "A remote part must be mineable");
            assertRemoved(context, pos, machine);
            context.assertTrue(countDrops(context, pos, machine.asItem()) == 1, "Mining must drop exactly one machine");
            context.assertTrue(pickaxe.getDamage() == 1, "Mining a part must damage the tool only once");
            if (machine == ModBlocks.INDUSTRIAL_FERMENTER) {
                context.assertTrue(countDrops(context, pos, Items.DIAMOND) == 5, "Inventory contents must spill exactly once");
            }
        }
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void creativeMiningAndBackingRemovalLeaveNoParts(TestContext context) {
        ServerPlayerEntity player = player(context, true);
        for (LargeMachineBlock machine : machines()) {
            BlockPos pos = prepare(context);
            place(context, pos, machine, Direction.NORTH);
            player.interactionManager.tryBreakBlock(pos.up(2));
            assertRemoved(context, pos, machine);
            context.assertTrue(countDrops(context, pos, machine.asItem()) == 0, "Creative breaking must not drop the machine");
        }
        BlockPos pos = prepare(context);
        LargeMachineBlock blades = (LargeMachineBlock) ModBlocks.FAN_BLADE;
        place(context, pos, blades, Direction.NORTH);
        context.getWorld().breakBlock(pos.south(), false);
        assertRemoved(context, pos, blades);
        context.assertTrue(countDrops(context, pos, blades.asItem()) == 1, "Losing the backing must drop one set of blades");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void structurePlacementFillsFreeCellsWithoutOverwritingBlocks(TestContext context) {
        BlockPos pos = prepare(context);
        LargeMachineBlock machine = (LargeMachineBlock) ModBlocks.INDUSTRIAL_FERMENTER;
        context.getWorld().setBlockState(pos.up(2), Blocks.STONE.getDefaultState());
        // Structure placement sets the block directly, without calling onPlaced.
        context.getWorld().setBlockState(pos, machine.getDefaultState());
        context.runAtTick(3, () -> {
            context.assertTrue(context.getWorld().getBlockState(pos.up(2)).isOf(Blocks.STONE),
                    "Structure placement must preserve blocks that obstruct the footprint");
            context.assertTrue(context.getWorld().getBlockState(pos.up()).isOf(machine.getPartBlock()),
                    "Free cells must be filled automatically");
            context.getWorld().setBlockState(pos.up(2), Blocks.AIR.getDefaultState());
        });
        context.runAtTick(25, () -> {
            context.assertTrue(context.getWorld().getBlockState(pos.up(2)).isOf(machine.getPartBlock()),
                    "The footprint must fill once the obstruction is removed");
            context.complete();
        });
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void facingChangesMoveThePartsAndRootRemovalCleansEverything(TestContext context) {
        BlockPos pos = prepare(context);
        LargeMachineBlock machine = (LargeMachineBlock) ModBlocks.INDUSTRIAL_FERMENTER;
        place(context, pos, machine, Direction.NORTH);
        context.getWorld().setBlockState(pos,
                context.getWorld().getBlockState(pos).with(Properties.HORIZONTAL_FACING, Direction.EAST));
        context.runAtTick(3, () -> {
            context.assertTrue(context.getWorld().getBlockState(pos.west()).isAir(), "The previous footprint must be cleared");
            context.assertTrue(context.getWorld().getBlockState(pos.north()).isOf(machine.getPartBlock()),
                    "The new footprint must have its own cells");
            context.getWorld().breakBlock(pos, true);
            assertRemoved(context, pos, machine);
            context.assertTrue(countDrops(context, pos, machine.asItem()) == 1, "Breaking the root must also drop one machine");
            context.complete();
        });
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void halfTurnsUpdatePartFacingBeforeMining(TestContext context) {
        BlockPos pos = prepare(context);
        LargeMachineBlock machine = (LargeMachineBlock) ModBlocks.INDUSTRIAL_FERMENTER;
        place(context, pos, machine, Direction.NORTH);
        context.getWorld().setBlockState(pos,
                context.getWorld().getBlockState(pos).with(Properties.HORIZONTAL_FACING, Direction.SOUTH));
        context.runAtTick(3, () -> {
            context.assertTrue(context.getWorld().getBlockState(pos.up(2)).get(Properties.HORIZONTAL_FACING) == Direction.SOUTH,
                    "A half turn must update parts even though the collision volume is unchanged");
            player(context, false).interactionManager.tryBreakBlock(pos.up(2));
            assertRemoved(context, pos, machine);
            context.assertTrue(countDrops(context, pos, machine.asItem()) == 0,
                    "Mining metal without a pickaxe must not drop a machine");
            context.complete();
        });
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void structuresRotateAndMirrorPartOwnership(TestContext context) {
        for (LargeMachineBlock machine : machines()) {
            for (Direction facing : Direction.Type.HORIZONTAL) {
                BlockState owner = machine.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);
                for (BlockPos offset : machine.getPartShapes(owner).keySet()) {
                    if (offset.equals(BlockPos.ORIGIN)) continue;
                    LargeMachinePartBlock part = machine.getPartBlock();
                    BlockState partState = part.stateFor(owner, offset);
                    for (BlockRotation rotation : BlockRotation.values()) {
                        BlockState rotated = partState.rotate(rotation);
                        context.assertTrue(part.getMachinePos(rotated, offset.rotate(rotation)).equals(BlockPos.ORIGIN)
                                        && rotated.get(Properties.HORIZONTAL_FACING) == rotation.rotate(facing),
                                "Rotated structures must keep every part attached to its owner");
                    }
                    for (BlockMirror mirror : BlockMirror.values()) {
                        BlockPos mirrored = switch (mirror) {
                            case LEFT_RIGHT -> new BlockPos(offset.getX(), offset.getY(), -offset.getZ());
                            case FRONT_BACK -> new BlockPos(-offset.getX(), offset.getY(), offset.getZ());
                            default -> offset;
                        };
                        context.assertTrue(part.getMachinePos(partState.mirror(mirror), mirrored).equals(BlockPos.ORIGIN),
                                "Mirrored structures must keep every part attached to its owner");
                    }
                }
            }
        }
        context.complete();
    }

    private static VoxelShape physicalShape(LargeMachineBlock machine, BlockState state) {
        VoxelShape shape = VoxelShapes.empty();
        for (var entry : machine.getPartShapes(state).entrySet()) {
            BlockPos offset = entry.getKey();
            shape = VoxelShapes.union(shape, entry.getValue().offset(offset.getX(), offset.getY(), offset.getZ()));
        }
        return shape;
    }

    private static LargeMachineBlock[] machines() {
        return new LargeMachineBlock[]{(LargeMachineBlock) ModBlocks.INDUSTRIAL_FERMENTER, (LargeMachineBlock) ModBlocks.FAN_BLADE};
    }

    private static BlockPos prepare(TestContext context) {
        BlockPos pos = context.getAbsolutePos(new BlockPos(3, 3, 3));
        context.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
        for (BlockPos cell : BlockPos.iterate(pos.add(-2, -2, -2), pos.add(2, 2, 2))) {
            context.getWorld().setBlockState(cell, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
        }
        context.getWorld().getEntitiesByClass(ItemEntity.class, new Box(pos).expand(4), entity -> true).forEach(ItemEntity::discard);
        return pos;
    }

    private static BlockState place(TestContext context, BlockPos pos, LargeMachineBlock machine, Direction facing) {
        if (machine == ModBlocks.FAN_BLADE) {
            context.getWorld().setBlockState(pos.offset(facing.getOpposite()), Blocks.STONE.getDefaultState());
        }
        BlockState state = machine.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);
        context.getWorld().setBlockState(pos, state);
        machine.onPlaced(context.getWorld(), pos, state, null, new ItemStack(machine));
        return state;
    }

    private static BlockHitResult raycast(TestContext context, Vec3d target, Direction facing, ServerPlayerEntity player) {
        Vec3d direction = Vec3d.of(facing.getVector()).multiply(4);
        Vec3d start = target.add(direction);
        if (player != null) {
            player.setPosition(start.add(0, -player.getStandingEyeHeight(), 0));
        }
        return context.getWorld().raycast(new RaycastContext(start, target.subtract(direction),
                RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
    }

    private static ServerPlayerEntity player(TestContext context, boolean creative) {
        ServerPlayerEntity player = new ServerPlayerEntity(context.getWorld().getServer(), context.getWorld(),
                new GameProfile(UUID.randomUUID(), "LargeMachineTest"));
        player.networkHandler = new ServerPlayNetworkHandler(context.getWorld().getServer(),
                new ClientConnection(NetworkSide.SERVERBOUND), player) {
            @Override
            public void sendPacket(Packet<?> packet) {
            }
        };
        player.interactionManager.changeGameMode(creative ? GameMode.CREATIVE : GameMode.SURVIVAL);
        return player;
    }

    private static int countDrops(TestContext context, BlockPos pos, Item item) {
        return context.getWorld().getEntitiesByClass(ItemEntity.class, new Box(pos).expand(4), entity -> entity.getStack().isOf(item))
                .stream().mapToInt(entity -> entity.getStack().getCount()).sum();
    }

    private static void assertRemoved(TestContext context, BlockPos pos, LargeMachineBlock machine) {
        context.assertTrue(context.getWorld().getBlockState(pos).isAir(), "Breaking any part must remove the root");
        for (BlockPos cell : BlockPos.iterate(pos.add(-2, -2, -2), pos.add(2, 2, 2))) {
            context.assertTrue(!(context.getWorld().getBlockState(cell).getBlock() instanceof LargeMachinePartBlock),
                    "Breaking a machine must leave no invisible cells behind");
        }
    }
}
