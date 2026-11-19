package com.zombie_cute.mc.moderndelight.block.multiblock;

import com.mojang.authlib.GameProfile;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.kitchenware.FreezerBlock;
import com.zombie_cute.mc.moderndelight.block.kitchenware.decor.GlassCupBlock;
import com.zombie_cute.mc.moderndelight.block.kitchenware.steaming.BambooGrateBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.SterlingEngineBlock;
import com.zombie_cute.mc.moderndelight.block.power.batteries.BatteryBlockEntity;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBlock;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerFishingLoot;
import com.zombie_cute.mc.moderndelight.util.block_util.ModelShapes;
import com.zombie_cute.mc.moderndelight.util.block_util.ShapeProfile;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.*;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.GameMode;

import java.util.UUID;

public class ModelShapeTests implements FabricGameTest {
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void engineFlywheelKeepsItsLowerHalfInEveryFacing(TestContext context) {
        var world = context.getWorld();
        var machine = (LargeMachineBlock)ModBlocks.STERLING_ENGINE;
        var player = player(context);
        BlockPos root = context.getAbsolutePos(new BlockPos(5, 5, 5));
        world.setBlockState(root.down(), Blocks.FURNACE.getDefaultState());
        for (Direction facing : Direction.Type.HORIZONTAL) {
            var state = machine.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);
            VoxelShape shape = machine.getMachineShape(state);
            Vec3d lower = switch (facing) {
                case EAST -> new Vec3d(56, -25, 18.5);
                case SOUTH -> new Vec3d(-2.5, -25, 56);
                case WEST -> new Vec3d(-40, -25, -2.5);
                default -> new Vec3d(18.5, -25, -40);
            };
            context.assertTrue(occupied(shape, lower.x, lower.y, lower.z)
                    && occupied(shape, lower.x, 15, lower.z), "Both halves of the flywheel must be present: " + facing);
            context.assertTrue(shape.getBoundingBoxes().size() <= 35, "The complete wheel retains its simplified profile");
            world.setBlockState(root, state);
            machine.onPlaced(world, root, state, null, new ItemStack(machine));
            Vec3d target = Vec3d.of(root).add(lower.multiply(1.0 / 16));
            BlockPos partPos = BlockPos.ofFloored(target);
            var part = world.getBlockState(partPos);
            context.assertTrue(part.getBlock() instanceof LargeMachinePartBlock, "Lower rim needs a physical part: " + facing);
            Vec3d local = target.subtract(Vec3d.of(partPos)).multiply(16);
            context.assertTrue(occupied(part.getCollisionShape(world, partPos), local.x, local.y, local.z),
                    "The lower rim must collide in its own cell: " + facing);
            player.setPosition(target.add(0, -player.getStandingEyeHeight(), 0));
            player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.SPONGE));
            player.networkHandler.onPlayerInteractBlock(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND,
                    new BlockHitResult(target, facing, partPos, false), 0));
            context.assertTrue(world.getBlockState(root).get(SterlingEngineBlock.SMALL_SOUND), "The lower rim forwards interaction: " + facing);
            world.breakBlock(partPos, false);
            context.assertTrue(world.getBlockState(root).isAir(), "The lower rim can remove the engine: " + facing);
            for (BlockPos offset : machine.getPartShapes(state).keySet())
                context.assertTrue(!(world.getBlockState(root.add(offset)).getBlock() instanceof LargeMachinePartBlock),
                        "Removing the engine cleans up the complete wheel: " + facing);
        }
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void engineRestoresLowerFlywheelAfterGroundRemoval(TestContext context) {
        var world = context.getWorld();
        var machine = (LargeMachineBlock)ModBlocks.STERLING_ENGINE;
        var state = machine.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH);
        BlockPos root = context.getAbsolutePos(new BlockPos(5, 5, 5));
        world.setBlockState(root.down(), Blocks.FURNACE.getDefaultState());
        BlockPos offset = machine.getPartShapes(state).keySet().stream().filter(p -> p.getY() < -1).findFirst().orElseThrow();
        BlockPos ground = root.add(offset);
        world.setBlockState(ground, Blocks.OAK_FENCE.getDefaultState());
        context.assertTrue(!state.canPlaceAt(world, root), "A lower obstruction that does not contain the wheel must still prevent placement");
        world.setBlockState(ground, Blocks.STONE.getDefaultState());
        context.assertTrue(state.canPlaceAt(world, root), "Solid ground can supply the lower wheel's collision");
        world.setBlockState(root, state);
        machine.onPlaced(world, root, state, null, new ItemStack(machine));
        context.assertTrue(world.getBlockState(ground).isOf(Blocks.STONE), "Engine placement preserves existing ground");
        world.breakBlock(ground, false);
        context.runAtTick(30, () -> {
            context.assertTrue(world.getBlockState(ground).getBlock() instanceof LargeMachinePartBlock,
                    "Exposing the lower wheel restores its physical collision automatically");
            context.assertTrue(world.getBlockState(root).isOf(machine), "Ground removal preserves the engine");
            world.breakBlock(root, false);
            context.assertTrue(world.getBlockState(root.down()).isOf(Blocks.FURNACE), "Engine removal preserves its furnace");
            context.complete();
        });
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void engineItemFitsOnGroundedFurnaces(TestContext context) {
        var world = context.getWorld();
        var machine = (LargeMachineBlock)ModBlocks.STERLING_ENGINE;
        var player = player(context);
        BlockPos root = context.getAbsolutePos(new BlockPos(5, 5, 5));
        for (BlockPos floor : BlockPos.iterate(root.add(-5, -2, -5), root.add(5, -2, 5)))
            world.setBlockState(floor, Blocks.STONE.getDefaultState());
        for (Block furnace : new Block[]{Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER, ModBlocks.OVEN, ModBlocks.ADVANCE_FURNACE}) {
            for (Direction facing : Direction.Type.HORIZONTAL) {
                var furnaceState = furnace.getDefaultState();
                if (furnaceState.contains(Properties.HORIZONTAL_FACING)) furnaceState = furnaceState.with(Properties.HORIZONTAL_FACING, facing);
                world.setBlockState(root.down(), furnaceState);
                furnace.onPlaced(world, root.down(), furnaceState, null, new ItemStack(furnace));
                var state = machine.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);
                context.assertTrue(state.canPlaceAt(world, root), "Grounded furnace clearance: " + furnace + "/" + facing + ": "
                        + machine.getPartShapes(state).keySet().stream().filter(p -> !p.equals(BlockPos.ORIGIN) && !world.getBlockState(root.add(p)).isAir())
                        .map(p -> p + "=" + world.getBlockState(root.add(p))).toList());
                player.setYaw(facing.getOpposite().asRotation());
                player.setPosition(Vec3d.ofCenter(root.up(4)));
                var stack = new ItemStack(machine);
                player.setStackInHand(Hand.MAIN_HAND, stack);
                var placement = new net.minecraft.item.ItemPlacementContext(player, Hand.MAIN_HAND, stack,
                        new BlockHitResult(Vec3d.ofCenter(root.down()).add(0, .5, 0), Direction.UP, root.down(), false));
                context.assertTrue(((net.minecraft.item.BlockItem)machine.asItem()).place(placement).isAccepted(), "Engine item placement: " + furnace + "/" + facing);
                context.assertTrue(world.getBlockState(root).isOf(machine), "Engine root must be above the furnace");
                for (BlockPos floor : BlockPos.iterate(root.add(-5, -2, -5), root.add(5, -2, 5)))
                    context.assertTrue(world.getBlockState(floor).isOf(Blocks.STONE), "Engine placement must preserve the floor");
                world.breakBlock(root, false);
                world.breakBlock(root.down(), false);
            }
        }
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void teslaCoilItemFitsBehindWindController(TestContext context) {
        var world = context.getWorld();
        var controller = (LargeMachineBlock)ModBlocks.WIND_TURBINE_CONTROLLER;
        var player = player(context);
        BlockPos root = context.getAbsolutePos(new BlockPos(4, 4, 4));
        for (Direction facing : Direction.Type.HORIZONTAL) {
            var state = controller.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);
            world.setBlockState(root, state);
            controller.onPlaced(world, root, state, null, new ItemStack(controller));
            BlockPos rear = root.offset(facing.getOpposite());
            context.assertTrue(world.getBlockState(rear).isAir(), "Rear power connector must accept a coil: " + facing);
            player.setPosition(Vec3d.ofCenter(rear.offset(facing.getOpposite(), 3)));
            player.setYaw(facing.asRotation());
            var stack = new ItemStack(ModBlocks.TESLA_COIL);
            player.setStackInHand(Hand.MAIN_HAND, stack);
            var placement = new net.minecraft.item.ItemPlacementContext(player, Hand.MAIN_HAND, stack,
                    new BlockHitResult(Vec3d.ofCenter(root).add(Vec3d.of(facing.getOpposite().getVector()).multiply(.5)), facing.getOpposite(), root, false));
            context.assertTrue(((net.minecraft.item.BlockItem)ModBlocks.TESLA_COIL.asItem()).place(placement).isAccepted(), "Coil item must place against controller: " + facing);
            context.assertTrue(world.getBlockState(rear).isOf(ModBlocks.TESLA_COIL), "Coil must occupy the immediately adjacent cell");
            world.breakBlock(root, false);
            context.assertTrue(world.getBlockState(rear).isOf(ModBlocks.TESLA_COIL), "Controller cleanup must preserve the coil");
            world.breakBlock(rear, false);
        }
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void reportCollisionComplexity(TestContext context) {
        for (Block block : new Block[]{ModBlocks.FAN_BLADE, ModBlocks.STERLING_ENGINE, ModBlocks.INDUSTRIAL_FERMENTER,
                ModBlocks.TRAWLER, ModBlocks.JUICE_EXTRACTOR, ModBlocks.ELECTRIC_STEAMER, ModBlocks.DEEP_FRY_BASKET,
                ModBlocks.GLASS_CUP}) {
            VoxelShape shape = wholeShape(context, block.getDefaultState());
            int[] edges = {0};
            shape.forEachEdge((a,b,c,d,e,f) -> edges[0]++);
            System.out.println("COLLISION_COMPLEXITY " + Registries.BLOCK.getId(block).getPath() + " boxes=" + shape.getBoundingBoxes().size() + " edges=" + edges[0]);
        }
        context.complete();
    }
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void everyModelStateUsesLocalCellShapes(TestContext context) {
        int states = 0;
        for (var block : Registries.BLOCK) {
            if (!(block instanceof LargeMachineBlock machine)) continue;
            LargeMachinePartBlock part = machine.getPartBlock();
            boolean needsPart = false;
            for (var state : machine.getStateManager().getStates()) {
                context.assertTrue(!machine.getMachineShape(state).isEmpty(), "A rendered block needs a shape: " + state);
                for (var entry : machine.getPartShapes(state).entrySet()) {
                    Box box = entry.getValue().getBoundingBox();
                    context.assertTrue(box.minX >= 0 && box.minY >= 0 && box.minZ >= 0
                                    && box.maxX <= 1 && box.maxY <= 1 && box.maxZ <= 1,
                            "All state variants must have cell-local geometry: " + state);
                    if (!entry.getKey().equals(BlockPos.ORIGIN)) {
                        needsPart = true;
                        context.assertTrue(part != null && part.getMachinePos(part.stateFor(state, entry.getKey()), entry.getKey()).equals(BlockPos.ORIGIN),
                                "Every overhang needs a representable owner offset: " + state);
                    }
                }
                states++;
            }
            context.assertTrue(needsPart == (part != null), "Part registration must match the current footprint: " + block);
        }
        context.assertTrue(states > 200, "The audit must include all machine, food and kitchenware state variants");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void cavitiesAndFoodPortionsStayEmpty(TestContext context) {
        VoxelShape cups = wholeShape(context, ModBlocks.GLASS_CUP.getDefaultState().with(GlassCupBlock.CUPS, 2));
        context.assertTrue(!occupied(cups, 8, 5, 8), "There must be no invisible wall between two cups");
        VoxelShape basin = wholeShape(context, ModBlocks.WOODEN_BASIN.getDefaultState());
        context.assertTrue(!occupied(basin, 8, 4, 8) && occupied(basin, 8, .5, 8), "Basin retains its bottom and open cavity");
        VoxelShape bowl = wholeShape(context, ModBlocks.GLASS_BOWL.getDefaultState());
        context.assertTrue(!occupied(bowl, 8, 4, 8), "An empty bowl must be hollow");
        var pizza = ModBlocks.PIZZA.getDefaultState();
        var bites = pizza.getProperties().stream().filter(p -> p.getName().equals("bites")).findFirst().orElseThrow();
        VoxelShape full = wholeShape(context, pizza), eaten = wholeShape(context, withInt(pizza, bites, 6));
        context.assertTrue(volume(eaten) < volume(full) * .6, "Eaten pizza must lose its collision volume");
        VoxelShape fermenter = wholeShape(context, ModBlocks.INDUSTRIAL_FERMENTER.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
        context.assertTrue(occupied(fermenter, 14, 12, 8), "The inaccessible tank cavity is solid");
        var fan = ModBlocks.FAN_BLADE.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH);
        context.assertTrue(!occupied(wholeShape(context, fan), -23, -23, 8), "A circular blade envelope must not fill its square corners");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void simpleProfilesKeepMeshAndFoodEasyToSelect(TestContext context) {
        for (Block block : new Block[]{ModBlocks.JUICE_EXTRACTOR, ModBlocks.ELECTRIC_STEAMER,
                ModBlocks.BAMBOO_GRATE, ModBlocks.TRAWLER, ModBlocks.DEEP_FRY_BASKET}) {
            for (var state : block.getStateManager().getStates()) {
                var shape = wholeShape(context, state);
                context.assertTrue(shape.getBoundingBoxes().size() <= 8, "Simple profile box budget: " + state);
                context.assertTrue(occupied(shape, shape.getBoundingBox().getCenter().x*16,
                        shape.getBoundingBox().getCenter().y*16, shape.getBoundingBox().getCenter().z*16), "Mesh and container centers remain selectable: " + state);
            }
        }
        VoxelShape pot = wholeShape(context, ModBlocks.CLAY_POT.getDefaultState());
        for (Block food : new Block[]{ModBlocks.CHICKEN_RICE, ModBlocks.CHILI_FISH, ModBlocks.CRAB_SAUSAGE_VEGETABLE,
                ModBlocks.CREAM_OF_MUSHROOM_SOUP, ModBlocks.FRENCH_ONION_SOUP, ModBlocks.MARSHALA_CHEESE_BALL,
                ModBlocks.PEPPER_BEEF_RICE, ModBlocks.PORK_NOODLE, ModBlocks.VEGETABLE_NOODLE})
            context.assertTrue(wholeShape(context, food.getDefaultState()) == pot, "Pot meals share the cached empty pot shape");
        var cup = wholeShape(context, ModBlocks.GLASS_CUP.getDefaultState());
        for (Block tea : new Block[]{ModBlocks.CHERRY_MILK_TEA, ModBlocks.ROSE_ICE_TEA}) {
            context.assertTrue(wholeShape(context, tea.getDefaultState()) == cup, "Filled cups reuse the empty cup shape");
        }
        context.assertTrue(occupied(cup, 8, 5, 8) && occupied(cup, 8, 2, 8)
                && !occupied(cup, 6, 2, 8) && cup.getBoundingBoxes().size() <= 6,
                "Cup cavity is filled, while the stem remains narrow");
        context.assertTrue(wholeShape(context, ModBlocks.FAN_BLADE.getDefaultState()).getBoundingBoxes().size() <= 30, "Rotor must use broad bands");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void singleCellBlocksUseTheirOriginalShapes(TestContext context) {
        assertSingleCellShape(context, ModBlocks.WOODEN_PLATE, Block.createCuboidShape(1, 0, 1, 15, 1, 15));
        assertSingleCellShape(context, ModBlocks.FISH_AND_CHIPS, Block.createCuboidShape(1, 0, 1, 15, 2, 15));
        assertSingleCellShape(context, ModBlocks.BRITISH_BREAKFAST, Block.createCuboidShape(1, 0, 1, 15, 3, 15));
        assertSingleCellShape(context, ModBlocks.OVEN, VoxelShapes.fullCube());
        assertSingleCellShape(context, ModBlocks.ADVANCE_FURNACE, VoxelShapes.fullCube());
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void differentProfilesCanBakeTheSameModel(TestContext context) {
        var state = ModBlocks.WIND_TURBINE_CONTROLLER.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH);
        var filtered = wholeShape(context, state);
        var unfiltered = ModelShapes.get(state, ShapeProfile.vanilla().build());
        context.assertTrue(filtered.getMax(Direction.Axis.Z) <= 1 && unfiltered.getMax(Direction.Axis.Z) > 1,
                "Two profiles reading the same model must retain their own geometry");
        context.assertTrue(wholeShape(context, state) == filtered, "Repeated queries must reuse the block's shape");
        context.complete();
    }

    private static VoxelShape wholeShape(TestContext context, BlockState state) {
        if (state.getBlock() instanceof LargeMachineBlock machine) return machine.getMachineShape(state);
        return state.getOutlineShape(context.getWorld(), context.getAbsolutePos(BlockPos.ORIGIN));
    }

    private static void assertSingleCellShape(TestContext context, Block block, VoxelShape expected) {
        var world = context.getWorld();
        BlockPos pos = context.getAbsolutePos(BlockPos.ORIGIN);
        context.assertTrue(!(block instanceof LargeMachineBlock), "This block does not need a machine footprint: " + block);
        for (BlockState state : block.getStateManager().getStates()) {
            context.assertTrue(!VoxelShapes.matchesAnywhere(expected, state.getCollisionShape(world, pos), BooleanBiFunction.NOT_SAME)
                            && !VoxelShapes.matchesAnywhere(expected, state.getOutlineShape(world, pos), BooleanBiFunction.NOT_SAME),
                    "All states use the original single-cell shape: " + state);
        }
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void partLightingNeverReadsTheOwner(TestContext context) {
        var world = (net.minecraft.world.BlockView)java.lang.reflect.Proxy.newProxyInstance(
                getClass().getClassLoader(), new Class<?>[]{net.minecraft.world.BlockView.class},
                (proxy, method, args) -> { throw new AssertionError("Lighting must not query a neighboring chunk: " + method.getName()); });
        for (var part : LargeMachineBlock.getRegisteredParts().values()) {
            for (boolean waterlogged : new boolean[]{false, true}) {
                var state = part.getDefaultState().with(Properties.WATERLOGGED, waterlogged);
                context.assertTrue(state.isTransparent(world, BlockPos.ORIGIN) == !waterlogged, "Water controls sky-light transparency");
                context.assertTrue(state.getOpacity(world, BlockPos.ORIGIN) == (waterlogged ? 1 : 0), "Invisible parts only attenuate contained water");
                context.assertTrue(state.getCullingShape(world, BlockPos.ORIGIN).isEmpty(), "Invisible cells do not cull neighboring models");
            }
        }
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void remoteBatteryMiningPreservesEnergyOnce(TestContext context) {
        BlockPos pos = context.getAbsolutePos(new BlockPos(3, 3, 3));
        var battery = (LargeMachineBlock)ModBlocks.ADVANCE_BATTERY;
        var state = battery.getDefaultState();
        context.getWorld().setBlockState(pos, state);
        battery.onPlaced(context.getWorld(), pos, state, null, new ItemStack(battery));
        var entity = (BatteryBlockEntity)context.getWorld().getBlockEntity(pos);
        entity.resetPower(12345, 50000);
        entity.energyStorage.amount = 123450;
        var player = player(context);
        player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.DIAMOND_PICKAXE));
        var remote = battery.getPartShapes(state).keySet().stream().filter(p -> !p.equals(BlockPos.ORIGIN)).findFirst().orElseThrow();
        context.assertTrue(player.interactionManager.tryBreakBlock(pos.add(remote)), "A terminal can be mined like its battery");
        var drops = context.getWorld().getEntitiesByClass(ItemEntity.class, new Box(pos).expand(2),
                item -> item.getStack().isOf(battery.asItem()));
        context.assertTrue(drops.stream().mapToInt(item -> item.getStack().getCount()).sum() == 1, "Remote mining drops exactly one battery");
        var nbt = net.minecraft.item.BlockItem.getBlockEntityNbt(drops.get(0).getStack());
        context.assertTrue(nbt != null && nbt.getLong("battery.power") == 12345, "Remote mining retains battery energy");
        context.assertTrue(context.getWorld().getBlockState(pos).isAir(), "Remote mining removes the root");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void trawlerWaterSurvivesLoweringAndRemoval(TestContext context) {
        BlockPos pos = context.getAbsolutePos(new BlockPos(3, 4, 3));
        for (BlockPos cell : BlockPos.iterate(pos.add(-2, -3, -2), pos.add(2, -1, 2)))
            context.getWorld().setBlockState(cell, Blocks.WATER.getDefaultState());
        TrawlerBlock machine = (TrawlerBlock)ModBlocks.TRAWLER;
        var state = machine.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH);
        context.assertTrue(state.canPlaceAt(context.getWorld(), pos), "A trawler fits above a source-water pool");
        context.getWorld().setBlockState(pos, state);
        machine.onPlaced(context.getWorld(), pos, state, null, new ItemStack(machine));
        context.getWorld().setBlockState(pos, state.with(TrawlerBlock.UP, false));
        context.runAtTick(4, () -> {
            var lowered = context.getWorld().getBlockState(pos);
            context.assertTrue(lowered.isOf(machine), "Own waterlogged parts must not detach the trawler");
            for (var offset : machine.getPartShapes(lowered).keySet()) {
                if (offset.getY() < 0)
                    context.assertTrue(context.getWorld().getFluidState(pos.add(offset)).isOf(Fluids.WATER)
                                    && context.getWorld().getFluidState(pos.add(offset)).isStill(), "Submerged parts must retain source water");
            }
            context.assertTrue(TrawlerFishingLoot.isOpenWater(context.getWorld(), pos), "Own net must not invalidate open-water fishing");
            var offset = machine.getPartShapes(lowered).keySet().stream().filter(p -> p.getY() < 0).findFirst().orElseThrow();
            context.getWorld().breakBlock(pos.add(offset), true);
            context.assertTrue(context.getWorld().getBlockState(pos).isAir(), "Breaking the submerged net removes the root");
            for (BlockPos cell : BlockPos.iterate(pos.add(-2, -3, -2), pos.add(2, -1, 2)))
                context.assertTrue(context.getWorld().getFluidState(cell).isStill() && context.getWorld().getFluidState(cell).isOf(Fluids.WATER),
                        "Removing any part must restore every source water cell");
            context.complete();
        });
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void stirlingSupportsFourFacingsAndRemotePackets(TestContext context) {
        SterlingEngineBlock machine = (SterlingEngineBlock)ModBlocks.STERLING_ENGINE;
        ServerPlayerEntity player = player(context);
        BlockPos pos = context.getAbsolutePos(new BlockPos(5, 4, 5));
        for (Direction facing : Direction.Type.HORIZONTAL) {
            for (boolean generatorFirst : new boolean[]{true, false}) {
            context.getWorld().setBlockState(pos.down(), Blocks.FURNACE.getDefaultState());
            var rotation = switch (facing) {
                case EAST -> net.minecraft.util.BlockRotation.CLOCKWISE_90;
                case SOUTH -> net.minecraft.util.BlockRotation.CLOCKWISE_180;
                case WEST -> net.minecraft.util.BlockRotation.COUNTERCLOCKWISE_90;
                default -> net.minecraft.util.BlockRotation.NONE;
            };
            BlockPos generatorPos = pos.add(new BlockPos(-1, -1, -3).rotate(rotation));
            var generator = (LargeMachineBlock)ModBlocks.FARADAY_GENERATOR;
            var generatorState = generator.getDefaultState().with(Properties.HORIZONTAL_FACING, facing.rotateYCounterclockwise());
            if (generatorFirst) {
                context.getWorld().setBlockState(generatorPos, generatorState);
                generator.onPlaced(context.getWorld(), generatorPos, generatorState, null, new ItemStack(generator));
            }
            var state = machine.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);
            context.assertTrue(state.canPlaceAt(context.getWorld(), pos), "The engine must fit around its furnace in " + facing + "; blocked cells: "
                    + machine.getPartShapes(state).keySet().stream().filter(offset -> !offset.equals(BlockPos.ORIGIN)
                    && !context.getWorld().getBlockState(pos.add(offset)).isAir())
                    .map(offset -> offset + "=" + context.getWorld().getBlockState(pos.add(offset))).toList());
            context.getWorld().setBlockState(pos, state);
            machine.onPlaced(context.getWorld(), pos, state, null, new ItemStack(machine));
            if (!generatorFirst) {
                context.assertTrue(context.getWorld().getBlockState(generatorPos).isAir()
                        && generatorState.canPlaceAt(context.getWorld(), generatorPos), "The generator must also fit after the engine");
                context.getWorld().setBlockState(generatorPos, generatorState);
                generator.onPlaced(context.getWorld(), generatorPos, generatorState, null, new ItemStack(generator));
            }
            context.assertTrue(context.getWorld().getBlockState(pos.down()).isOf(Blocks.FURNACE), "Engine must preserve its supporting furnace");
            var remote = machine.getPartShapes(state).entrySet().stream().filter(e -> e.getKey().getManhattanDistance(BlockPos.ORIGIN) >= 3).findFirst().orElseThrow();
            BlockPos partPos = pos.add(remote.getKey());
            Vec3d target = remote.getValue().getBoundingBoxes().get(0).getCenter().add(Vec3d.of(partPos));
            player.setPosition(target.add(0, -player.getStandingEyeHeight(), 0));
            player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.SPONGE, 2));
            player.networkHandler.onPlayerInteractBlock(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND,
                    new BlockHitResult(target, facing, partPos, false), 0));
            context.assertTrue(context.getWorld().getBlockState(pos).get(SterlingEngineBlock.SMALL_SOUND),
                    "A remote flywheel click must pass packet validation and apply the sponge to its root");
            context.getWorld().breakBlock(partPos, false);
            context.assertTrue(context.getWorld().getBlockState(pos).isAir(), "Remote removal must remove the engine");
            for (BlockPos offset : machine.getPartShapes(state).keySet())
                context.assertTrue(!(context.getWorld().getBlockState(pos.add(offset)).getBlock() instanceof LargeMachinePartBlock),
                        "Even farthest flywheel cells must be cleaned");
            context.assertTrue(context.getWorld().getBlockState(generatorPos).isOf(generator), "Engine cleanup must preserve its attached generator");
            context.getWorld().breakBlock(generatorPos, false);
            }
        }
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void freezerDoorAndSteamerLidKeepTheirParts(TestContext context) {
        BlockPos pos = context.getAbsolutePos(new BlockPos(3, 3, 3));
        var machine = (LargeMachineBlock)ModBlocks.FREEZER;
        var open = machine.getDefaultState().with(FreezerBlock.IS_OPEN, true).with(Properties.HORIZONTAL_FACING, Direction.NORTH);
        context.getWorld().setBlockState(pos, open);
        machine.onPlaced(context.getWorld(), pos, open, null, new ItemStack(machine));
        BlockPos steamerPos = pos.east(5);
        var steamer = (LargeMachineBlock)ModBlocks.BAMBOO_GRATE;
        var covered = steamer.getDefaultState().with(BambooGrateBlock.LAYER, 4).with(BambooGrateBlock.COVERED, true);
        context.getWorld().setBlockState(steamerPos, covered);
        steamer.onPlaced(context.getWorld(), steamerPos, covered, null, new ItemStack(steamer));
        context.runAtTick(5, () -> {
            context.assertTrue(context.getWorld().getBlockState(pos).get(FreezerBlock.IS_OPEN), "Own door parts must not force the freezer shut");
            context.assertTrue(context.getWorld().getBlockState(steamerPos).get(BambooGrateBlock.COVERED), "Own lid must not be treated as an obstruction");
            context.assertTrue(context.getWorld().getBlockState(steamerPos.up()).getBlock() instanceof LargeMachinePartBlock,
                    "Fourth-layer lid must have collision above its root cell");
            context.complete();
        });
    }

    private static boolean occupied(VoxelShape shape, double x, double y, double z) {
        return VoxelShapes.matchesAnywhere(shape, VoxelShapes.cuboid(x/16-.0001,y/16-.0001,z/16-.0001,
                x/16+.0001,y/16+.0001,z/16+.0001), BooleanBiFunction.AND);
    }
    private static double volume(VoxelShape shape) {
        return shape.getBoundingBoxes().stream().mapToDouble(b -> b.getXLength()*b.getYLength()*b.getZLength()).sum();
    }
    @SuppressWarnings("unchecked")
    private static BlockState withInt(BlockState state, net.minecraft.state.property.Property<?> property, int value) {
        return state.with((net.minecraft.state.property.Property<Integer>)property, value);
    }
    private static ServerPlayerEntity player(TestContext context) {
        var player = new ServerPlayerEntity(context.getWorld().getServer(), context.getWorld(), new GameProfile(UUID.randomUUID(), "ModelShapeTest"));
        player.networkHandler = new ServerPlayNetworkHandler(context.getWorld().getServer(), new ClientConnection(NetworkSide.SERVERBOUND), player) {
            @Override public void sendPacket(Packet<?> packet) {}
        };
        player.interactionManager.changeGameMode(GameMode.SURVIVAL);
        return player;
    }
}
