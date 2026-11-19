package com.zombie_cute.mc.moderndelight.networking;

import com.mojang.authlib.GameProfile;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.kitchenware.CuisineTableBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.moderndelight.networking.packet.ChangeBlockEntityDataC2SPacket;
import com.zombie_cute.mc.moderndelight.networking.packet.UpdateInventoryC2SPacket;
import com.zombie_cute.mc.moderndelight.recipe.custom.AssemblyRecipe;
import com.zombie_cute.mc.moderndelight.recipe.custom.CuisineRecipe;
import com.zombie_cute.mc.moderndelight.screen.custom.CuisineTableScreenHandler;
import com.zombie_cute.mc.moderndelight.screen.custom.ElectriciansDeskScreenHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkstationPacketTests implements FabricGameTest {
    private static final Identifier CUISINE = new Identifier("moderndelight_tests", "cuisine");
    private static final Identifier ASSEMBLY = new Identifier("moderndelight_tests", "assembly");
    private static final int SYNC_ID = 23;

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void cuisineRejectsUnauthorizedAndInvalidSelections(TestContext context) {
        CuisineFixture fixture = cuisine(context, 1);
        ServerPlayerEntity player = fixture.player;
        player.currentScreenHandler = player.playerScreenHandler;
        request(player, SYNC_ID, fixture.entity.getPos(), CUISINE);
        context.assertTrue(fixture.entity.getStack(2).isEmpty(), "A closed menu must reject requests");
        player.currentScreenHandler = fixture.screen;
        request(player, SYNC_ID + 1, fixture.entity.getPos(), CUISINE);
        request(player, SYNC_ID, fixture.entity.getPos().east(), CUISINE);
        context.assertTrue(fixture.entity.getStack(2).isEmpty(), "Wrong menu or position must be rejected");
        player.setPosition(Vec3d.ofCenter(fixture.entity.getPos().up(20)));
        request(player, SYNC_ID, fixture.entity.getPos(), CUISINE);
        context.assertTrue(fixture.entity.getStack(2).isEmpty(), "Remote requests must be rejected");
        player.setPosition(Vec3d.ofCenter(fixture.entity.getPos()));
        request(player, SYNC_ID, fixture.entity.getPos(), ASSEMBLY);
        request(player, SYNC_ID, fixture.entity.getPos(), new Identifier("moderndelight_tests", "missing"));
        context.assertTrue(fixture.entity.getStack(2).isEmpty(), "Unknown and wrong-type recipes must be rejected");
        fixture.entity.setStack(1, new ItemStack(Items.STICK));
        request(player, SYNC_ID, fixture.entity.getPos(), CUISINE);
        context.assertTrue(fixture.entity.getStack(2).isEmpty(), "Wrong ingredients must be rejected");
        fixture.entity.setStack(1, new ItemStack(Items.DIAMOND_PICKAXE));
        request(player, SYNC_ID, fixture.entity.getPos(), CUISINE);
        context.assertTrue(ItemStack.areEqual(fixture.entity.getStack(2), cuisineOutput()),
                "The server recipe must determine the item, count and NBT");
        request(player, SYNC_ID, fixture.entity.getPos(), null);
        context.assertTrue(fixture.entity.getStack(2).isEmpty(), "Deselection must clear the preview");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void legacyAndMalformedPayloadsCannotInsertItems(TestContext context) {
        CuisineFixture fixture = cuisine(context, 1);
        PacketByteBuf legacy = new PacketByteBuf(Unpooled.buffer());
        legacy.writeBlockPos(fixture.entity.getPos());
        ItemStack forged = new ItemStack(Items.NETHERITE_BLOCK, 64);
        forged.getOrCreateNbt().putBoolean("forged", true);
        legacy.writeItemStack(forged);
        receive(fixture.player, legacy);
        receive(fixture.player, new PacketByteBuf(Unpooled.buffer()));
        context.assertTrue(fixture.entity.getStack(2).isEmpty(), "Legacy stacks and truncated packets must not write output");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void cuisineRightClickConsumesOneRecipeAndCannotReplay(TestContext context) {
        CuisineFixture fixture = cuisine(context, 1);
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), CUISINE);
        fixture.screen.onSlotClick(2, 1, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(ItemStack.areEqual(fixture.screen.getCursorStack(), cuisineOutput()),
                "Right-click must take the whole recipe result");
        context.assertTrue(fixture.entity.getStack(0).isEmpty(), "Taking a result must consume one ingredient");
        context.assertTrue(fixture.entity.getStack(1).getDamage() == 1, "Taking a result must damage the tool once");
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), CUISINE);
        fixture.screen.sendContentUpdates();
        context.assertTrue(fixture.entity.getStack(2).isEmpty(), "Replays cannot refill a result without ingredients");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void cuisineRejectsStaleOutputBeforeTheNextTick(TestContext context) {
        CuisineFixture fixture = cuisine(context, 1);
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), CUISINE);
        fixture.entity.setStack(0, ItemStack.EMPTY);
        fixture.screen.onSlotClick(2, 0, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(fixture.screen.getCursorStack().isEmpty(), "A stale preview must not be takeable");
        context.assertTrue(fixture.screen.quickMove(fixture.player, 2).isEmpty(), "Shift-click must also reject stale output");
        fixture.screen.sendContentUpdates();
        context.assertTrue(fixture.entity.getStack(2).isEmpty(), "The next sync must clear stale output");
        context.assertTrue(fixture.entity.getStack(1).getDamage() == 0, "Rejected takes must not damage tools");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void cuisineShiftClickPaysOnceAndFullInventoryPaysNothing(TestContext context) {
        CuisineFixture fixture = cuisine(context, 2);
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), CUISINE);
        fixture.screen.quickMove(fixture.player, 2);
        context.assertTrue(fixture.player.getInventory().count(Items.DIAMOND) == 3, "Shift-click must grant one batch");
        context.assertTrue(fixture.entity.getStack(0).getCount() == 1, "Shift-click must consume one ingredient");
        context.assertTrue(fixture.entity.getStack(1).getDamage() == 1, "Shift-click must damage the tool once");
        for (int i = 0; i < 36; i++) {
            fixture.player.getInventory().setStack(i, new ItemStack(Items.STONE, 64));
        }
        context.assertTrue(fixture.screen.quickMove(fixture.player, 2).isEmpty(), "A full inventory must reject the transfer");
        context.assertTrue(fixture.entity.getStack(0).getCount() == 1, "A failed transfer must not consume ingredients");
        context.assertTrue(fixture.entity.getStack(1).getDamage() == 1, "A failed transfer must not damage the tool");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void cuisineToolBreakPreventsAnotherResult(TestContext context) {
        CuisineFixture fixture = cuisine(context, 2);
        ItemStack tool = fixture.entity.getStack(1);
        tool.setDamage(tool.getMaxDamage() - 1);
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), CUISINE);
        fixture.screen.quickMove(fixture.player, 2);
        context.assertTrue(fixture.entity.getStack(1).isEmpty(), "The final durability point must break the tool");
        context.assertTrue(fixture.entity.getStack(0).getCount() == 1, "Only one ingredient should be consumed");
        context.assertTrue(fixture.entity.getStack(2).isEmpty(), "A broken tool must not leave another result");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void deskRequiresPaidSessionAndCraftsAtomically(TestContext context) {
        DeskFixture fixture = desk(context);
        action(fixture.player, fixture.entity.getPos(), 1);
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), null);
        context.assertTrue(fixture.entity.getStack(8).isEmpty(), "A forged completion without a paid session must fail");
        fixture.entity.setStack(7, ItemStack.EMPTY);
        action(fixture.player, fixture.entity.getPos(), 3);
        context.assertTrue(fixture.entity.getStack(6).getCount() == 1, "Missing ink must not consume paper");
        fixture.entity.setStack(7, new ItemStack(Items.INK_SAC));
        prepare(fixture);
        context.assertTrue(fixture.entity.getStack(6).isEmpty() && fixture.entity.getStack(7).isEmpty(),
                "Starting a session must pay paper and ink exactly once");
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), null);
        context.assertTrue(ItemStack.areEqual(fixture.entity.getStack(8), assemblyOutput()),
                "Assembly output, count and NBT must come from the server recipe");
        for (int i = 0; i < 6; i++) {
            context.assertTrue(fixture.entity.getStack(i).getCount() == 1, "Crafting must consume every ingredient once");
        }
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), null);
        action(fixture.player, fixture.entity.getPos(), 2);
        context.assertTrue(fixture.entity.getStack(0).getCount() == 1, "Replayed and legacy craft requests must not pay twice");
        fixture.screen.quickMove(fixture.player, 8);
        context.assertTrue(fixture.player.getInventory().count(Items.EMERALD) == 4, "Assembly output must shift-click into inventory");
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), null);
        context.assertTrue(fixture.entity.getStack(8).isEmpty(), "Extracting output must not allow replaying the crafting credit");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void deskRevalidatesIngredientsAndProtectsExistingOutput(TestContext context) {
        DeskFixture fixture = desk(context);
        prepare(fixture);
        fixture.entity.setStack(0, ItemStack.EMPTY);
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), null);
        context.assertTrue(fixture.entity.getStack(8).isEmpty(), "Removing ingredients must invalidate crafting");
        context.assertTrue(fixture.entity.getStack(1).getCount() == 2, "Failed crafting must not consume other ingredients");
        fixture.screen.sendContentUpdates();
        fixture.entity.setStack(0, new ItemStack(Items.COBBLESTONE, 2));
        fixture.entity.setStack(6, new ItemStack(Items.PAPER));
        fixture.entity.setStack(7, new ItemStack(Items.INK_SAC));
        prepare(fixture);
        ItemStack existing = new ItemStack(Items.GOLD_INGOT);
        fixture.entity.setStack(8, existing);
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), null);
        context.assertTrue(ItemStack.areEqual(fixture.entity.getStack(8), existing), "Crafting must not overwrite existing output");
        context.assertTrue(fixture.entity.getStack(0).getCount() == 2, "Blocked output must not consume inputs");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void deskSessionCannotBeStolenOrUsedAfterClosing(TestContext context) {
        DeskFixture fixture = desk(context);
        action(fixture.player, fixture.entity.getPos(), 3);
        ServerPlayerEntity other = player(context, fixture.entity.getPos());
        other.currentScreenHandler = fixture.entity.createMenu(SYNC_ID, other.getInventory(), other);
        action(other, fixture.entity.getPos(), 1);
        action(other, fixture.entity.getPos(), 4);
        request(other, SYNC_ID, fixture.entity.getPos(), null);
        context.assertTrue(fixture.screen.isOccupied() && !fixture.screen.canCraft(),
                "Another player must not complete or cancel the owner's session");
        action(fixture.player, fixture.entity.getPos(), 1);
        action(fixture.player, fixture.entity.getPos(), 4);
        request(other, SYNC_ID, fixture.entity.getPos(), null);
        context.assertTrue(fixture.entity.getStack(8).isEmpty(), "Another player must not use a prepared crafting credit");
        fixture.screen.onClosed(fixture.player);
        fixture.player.currentScreenHandler = fixture.player.playerScreenHandler;
        action(fixture.player, fixture.entity.getPos(), 1);
        request(fixture.player, SYNC_ID, fixture.entity.getPos(), null);
        context.assertTrue(!fixture.screen.canCraft() && !fixture.screen.isOccupied(), "Closing must release the session");
        context.assertTrue(fixture.entity.getStack(8).isEmpty(), "Closed-menu callbacks must not craft");
        context.complete();
    }

    private static CuisineFixture cuisine(TestContext context, int ingredients) {
        registerRecipes(context);
        BlockPos relative = new BlockPos(1, 1, 1);
        context.setBlockState(relative, ModBlocks.CUISINE_TABLE);
        CuisineTableBlockEntity entity = (CuisineTableBlockEntity) context.getBlockEntity(relative);
        entity.setStack(0, new ItemStack(Items.COBBLESTONE, ingredients));
        entity.setStack(1, new ItemStack(Items.DIAMOND_PICKAXE));
        ServerPlayerEntity player = player(context, entity.getPos());
        CuisineTableScreenHandler screen = new CuisineTableScreenHandler(SYNC_ID, player.getInventory(), entity);
        player.currentScreenHandler = screen;
        return new CuisineFixture(entity, player, screen);
    }

    private static DeskFixture desk(TestContext context) {
        registerRecipes(context);
        BlockPos relative = new BlockPos(1, 1, 1);
        context.setBlockState(relative, ModBlocks.ELECTRICIANS_DESK);
        ElectriciansDeskBlockEntity entity = (ElectriciansDeskBlockEntity) context.getBlockEntity(relative);
        for (int i = 0; i < 6; i++) {
            entity.setStack(i, new ItemStack(Items.COBBLESTONE, 2));
        }
        entity.setStack(6, new ItemStack(Items.PAPER));
        entity.setStack(7, new ItemStack(Items.INK_SAC));
        ServerPlayerEntity player = player(context, entity.getPos());
        ElectriciansDeskScreenHandler screen = (ElectriciansDeskScreenHandler)
                entity.createMenu(SYNC_ID, player.getInventory(), player);
        player.currentScreenHandler = screen;
        return new DeskFixture(entity, player, screen);
    }

    private static ServerPlayerEntity player(TestContext context, BlockPos pos) {
        ServerPlayerEntity player = new ServerPlayerEntity(context.getWorld().getServer(), context.getWorld(),
                new GameProfile(UUID.randomUUID(), "PacketTest"));
        player.setPosition(Vec3d.ofCenter(pos));
        return player;
    }

    private static void prepare(DeskFixture fixture) {
        action(fixture.player, fixture.entity.getPos(), 3);
        action(fixture.player, fixture.entity.getPos(), 1);
        action(fixture.player, fixture.entity.getPos(), 4);
    }

    private static void request(ServerPlayerEntity player, int syncId, BlockPos pos, Identifier recipeId) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeVarInt(syncId);
        buf.writeBlockPos(pos);
        buf.writeBoolean(recipeId != null);
        if (recipeId != null) {
            buf.writeIdentifier(recipeId);
        }
        receive(player, buf);
    }

    private static void receive(ServerPlayerEntity player, PacketByteBuf buf) {
        try {
            UpdateInventoryC2SPacket.receive(player.getServer(), player, null, buf, null);
        } finally {
            buf.release();
        }
    }

    private static void action(ServerPlayerEntity player, BlockPos pos, int action) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        try {
            buf.writeBlockPos(pos);
            buf.writeIntArray(new int[]{action});
            ChangeBlockEntityDataC2SPacket.receive(player.getServer(), player, null, buf, null);
        } finally {
            buf.release();
        }
    }

    private static ItemStack cuisineOutput() {
        ItemStack output = new ItemStack(Items.DIAMOND, 3);
        output.getOrCreateNbt().putString("source", "server-cuisine-recipe");
        return output;
    }

    private static ItemStack assemblyOutput() {
        ItemStack output = new ItemStack(Items.EMERALD, 4);
        output.getOrCreateNbt().putString("source", "server-assembly-recipe");
        return output;
    }

    private static void registerRecipes(TestContext context) {
        var manager = context.getWorld().getRecipeManager();
        if (manager.get(CUISINE).isPresent()) {
            return;
        }
        List<Recipe<?>> recipes = new ArrayList<>(manager.values());
        recipes.add(new CuisineRecipe(CUISINE, DefaultedList.copyOf(Ingredient.EMPTY,
                Ingredient.ofItems(Items.COBBLESTONE), Ingredient.ofItems(Items.DIAMOND_PICKAXE)), cuisineOutput()));
        recipes.add(new AssemblyRecipe(ASSEMBLY, DefaultedList.ofSize(6, Ingredient.ofItems(Items.COBBLESTONE)),
                assemblyOutput(), 1, 64));
        manager.setRecipes(recipes);
    }

    private record CuisineFixture(CuisineTableBlockEntity entity, ServerPlayerEntity player, CuisineTableScreenHandler screen) {}
    private record DeskFixture(ElectriciansDeskBlockEntity entity, ServerPlayerEntity player, ElectriciansDeskScreenHandler screen) {}
}
