package com.zombie_cute.mc.moderndelight.compat;

import com.mojang.authlib.GameProfile;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.kitchenware.CuisineTableBlockEntity;
import com.zombie_cute.mc.moderndelight.recipe.CuisineRecipes;
import com.zombie_cute.mc.moderndelight.recipe.custom.CuisineRecipe;
import com.zombie_cute.mc.moderndelight.screen.custom.CuisineTableScreenHandler;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.test.TestContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
import vectorwing.farmersdelight.refabricated.inventory.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final class CuttingCompatibilityChecks {
    static void allRecipesAndSalmon(TestContext context) {
        var manager = context.getWorld().getRecipeManager();
        var source = manager.listAllOfType(ModRecipeTypes.CUTTING.get());
        context.assertTrue(source.size() >= 80, "Actual Farmer's Delight cutting recipes must be loaded");
        long imported = CuisineRecipes.getAll(manager).stream().filter(CuisineRecipe::requiresOutputRoll).count();
        context.assertTrue(imported == source.size(), "Every loaded cutting recipe must appear exactly once");
        for (var cutting : source) {
            CuisineRecipe adapted = CuisineRecipes.fromCuttingRecipe(cutting);
            context.assertTrue(CuisineRecipes.get(manager, adapted.getId()) != null, "Adapted IDs must resolve");
            context.assertTrue(adapted.getOutputs().size() == cutting.getRollableResults().size(),
                    "All outputs must be exposed");
        }
        var knife = new ItemStack(Registries.ITEM.get(new Identifier("farmersdelight", "iron_knife")));
        var input = new SimpleInventory(new ItemStack(Items.SALMON), knife);
        var matches = CuisineRecipes.getMatches(input, context.getWorld());
        CuisineRecipe salmon = matches.stream().filter(CuisineRecipe::requiresOutputRoll).findFirst().orElseThrow();
        context.assertTrue(salmon.getOutput(null).getCount() == 2 && salmon.getOutputs().size() == 2,
                "Salmon must include two slices and bone meal");
        input.setStack(1, new ItemStack(Items.STICK));
        context.assertTrue(CuisineRecipes.getMatches(input, context.getWorld()).isEmpty(), "Wrong tools must not match");
    }

    static void multiOutput(TestContext context) {
        var fixture = fixture(context, "multi_output", 3, 1, false);
        fixture.screen.onSlotClick(2, 1, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(fixture.screen.getCursorStack().getCount() == 2
                && fixture.screen.getCursorStack().isOf(Items.DIAMOND), "Right-click must take the entire main output");
        context.assertTrue(fixture.player.getInventory().count(Items.GOLD_INGOT) == 4,
                "Guaranteed secondary outputs must be delivered");
        context.assertTrue(fixture.player.getInventory().count(Items.EMERALD) == 0,
                "Zero-chance outputs must not be granted");
        context.assertTrue(fixture.entity.getStack(0).getCount() == 2 && fixture.entity.getStack(1).getDamage() == 1,
                "One operation must consume one ingredient and one durability");
        fixture.screen.quickMove(fixture.player, 2);
        context.assertTrue(fixture.player.getInventory().count(Items.DIAMOND) == 2
                && fixture.player.getInventory().count(Items.GOLD_INGOT) == 8, "Shift-click must deliver all outputs once");
        context.assertTrue(fixture.recipe.rolls == 2 && fixture.entity.getStack(0).getCount() == 1,
                "Shift-click must pay for exactly one operation");
    }

    static void previewAndFortune(TestContext context) {
        var fixture = fixture(context, "preview", 8, 1, false);
        for (int i = 0; i < 10; i++) {
            fixture.screen.selectRecipe(fixture.adapted.getId());
            fixture.screen.sendContentUpdates();
        }
        context.assertTrue(fixture.recipe.rolls == 0, "Preview and reselection must never roll output");
        fixture.entity.getStack(1).addEnchantment(Enchantments.FORTUNE, 3);
        fixture.screen.onSlotClick(2, 0, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(fixture.recipe.rolls == 1 && fixture.recipe.lastFortune == 3,
                "Fortune must be passed to Farmer's Delight exactly once");
        context.assertTrue(fixture.recipe.inputCount == 1, "The cutting board context must contain one input item");
    }

    static void missingMainAndEmptyRoll(TestContext context) {
        var fixture = fixture(context, "missing_main", 2, 0, false);
        fixture.screen.onSlotClick(2, 0, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(!fixture.screen.getCursorStack().isOf(Items.DIAMOND)
                && fixture.screen.getCursorStack().isOf(Items.GOLD_INGOT),
                "A failed main roll must never give the guaranteed preview");
        fixture.recipe.emptyRoll = true;
        fixture.screen.setCursorStack(ItemStack.EMPTY);
        fixture.screen.onSlotClick(2, 0, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(fixture.entity.getStack(0).isEmpty() && fixture.entity.getStack(2).isEmpty(),
                "An empty roll must still consume its input and clear the output");
        context.assertTrue(fixture.screen.getCursorStack().isEmpty(), "An empty roll must not grant preview items");
    }

    static void fullInventoryAndStaleOutput(TestContext context) {
        var fixture = fixture(context, "blocked", 2, 1, false);
        for (int i = 0; i < fixture.player.getInventory().main.size(); i++) {
            fixture.player.getInventory().main.set(i, new ItemStack(Items.COBBLESTONE, 64));
        }
        fixture.screen.quickMove(fixture.player, 2);
        context.assertTrue(fixture.recipe.rolls == 0 && fixture.entity.getStack(0).getCount() == 2,
                "A full inventory must not spend ingredients or randomness on shift-click");
        fixture.screen.setCursorStack(new ItemStack(Items.STICK));
        fixture.screen.onSlotClick(2, 0, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(fixture.recipe.rolls == 0, "An incompatible cursor must not craft");
        fixture.screen.setCursorStack(ItemStack.EMPTY);
        fixture.entity.setStack(1, new ItemStack(Items.STICK));
        fixture.screen.onSlotClick(2, 0, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(fixture.recipe.rolls == 0 && fixture.screen.getCursorStack().isEmpty(),
                "Stale previews must not be extractable after changing tools");
        fixture.entity.setStack(1, new ItemStack(Items.DIAMOND_PICKAXE));
        fixture.screen.sendContentUpdates();
        fixture.screen.onSlotClick(2, 0, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(fixture.recipe.rolls == 1 && fixture.screen.getCursorStack().getCount() == 2,
                "Normal pickup must work even when secondary outputs need to drop");
    }

    static void reload(TestContext context) {
        var fixture = fixture(context, "reload", 2, 1, false);
        var manager = context.getWorld().getRecipeManager();
        var recipes = new ArrayList<Recipe<?>>(manager.values());
        recipes.removeIf(recipe -> recipe.getId().equals(fixture.recipe.getId()));
        var replacement = new CountingRecipe(fixture.recipe.getId(), 1, false);
        replacement.getRollableResults().set(0, new ChanceResult(new ItemStack(Items.DIAMOND, 5), 1));
        recipes.add(replacement);
        manager.setRecipes(recipes);
        fixture.screen.onSlotClick(2, 0, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(replacement.rolls == 0 && fixture.screen.getCursorStack().isEmpty(),
                "An old preview must not be crafted after recipe replacement");
        fixture.screen.sendContentUpdates();
        context.assertTrue(fixture.entity.getStack(2).getCount() == 5, "Reload must update the live preview");
        fixture.screen.onSlotClick(2, 0, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(fixture.screen.getCursorStack().getCount() == 5 && replacement.rolls == 1,
                "Reloaded recipes must use the new output");
        recipes.remove(replacement);
        manager.setRecipes(recipes);
        fixture.screen.sendContentUpdates();
        context.assertTrue(fixture.entity.getStack(2).isEmpty()
                && CuisineRecipes.get(manager, fixture.adapted.getId()) == null, "Removed recipes must disappear");
    }

    static void toolRules(TestContext context) {
        var fixture = fixture(context, "nondamageable", 2, 1, true);
        fixture.screen.onSlotClick(2, 0, SlotActionType.PICKUP, fixture.player);
        context.assertTrue(fixture.entity.getStack(1).isOf(Items.STICK), "Non-damageable cutting tools must be kept");
        var damageable = fixture(context, "unbreakable", 1, 1, false);
        damageable.entity.getStack(1).getOrCreateNbt().putBoolean("Unbreakable", true);
        damageable.screen.onSlotClick(2, 0, SlotActionType.PICKUP, damageable.player);
        context.assertTrue(damageable.entity.getStack(1).getDamage() == 0, "Unbreakable tool NBT must be respected");
    }

    private static Fixture fixture(TestContext context, String name, int count, float mainChance, boolean stickTool) {
        var recipe = new CountingRecipe(new Identifier("third_party_test", "nested/" + name), mainChance, stickTool);
        var manager = context.getWorld().getRecipeManager();
        var recipes = new ArrayList<Recipe<?>>(manager.values());
        recipes.add(recipe);
        manager.setRecipes(recipes);
        var adapted = CuisineRecipes.fromCuttingRecipe(recipe);
        var relative = new BlockPos(1, 1, 1);
        context.setBlockState(relative, ModBlocks.CUISINE_TABLE);
        var entity = (CuisineTableBlockEntity) context.getBlockEntity(relative);
        entity.setStack(0, new ItemStack(Items.COBBLESTONE, count));
        entity.setStack(1, new ItemStack(stickTool ? Items.STICK : Items.DIAMOND_PICKAXE));
        var player = new ServerPlayerEntity(context.getWorld().getServer(), context.getWorld(),
                new GameProfile(UUID.randomUUID(), "CuisineCompat"));
        player.networkHandler = new ServerPlayNetworkHandler(context.getWorld().getServer(),
                new ClientConnection(NetworkSide.SERVERBOUND), player);
        player.setPosition(Vec3d.ofCenter(entity.getPos()));
        var screen = new CuisineTableScreenHandler(31, player.getInventory(), entity);
        player.currentScreenHandler = screen;
        screen.selectRecipe(adapted.getId());
        return new Fixture(recipe, adapted, entity, player, screen);
    }

    private record Fixture(CountingRecipe recipe, CuisineRecipe adapted, CuisineTableBlockEntity entity,
                           ServerPlayerEntity player, CuisineTableScreenHandler screen) {}

    private static class CountingRecipe extends CuttingBoardRecipe {
        int rolls;
        int lastFortune;
        int inputCount;
        boolean emptyRoll;

        CountingRecipe(Identifier id, float mainChance, boolean stickTool) {
            super(id, "", Ingredient.ofItems(Items.COBBLESTONE),
                    Ingredient.ofItems(stickTool ? Items.STICK : Items.DIAMOND_PICKAXE),
                    DefaultedList.copyOf(ChanceResult.EMPTY,
                            new ChanceResult(new ItemStack(Items.DIAMOND, 2), mainChance),
                            new ChanceResult(new ItemStack(Items.EMERALD, 3), 0),
                            new ChanceResult(new ItemStack(Items.GOLD_INGOT, 4), 1)), "minecraft:item.axe.strip");
        }

        @Override
        public List<ItemStack> rollResults(Random random, int fortune, RecipeWrapper inventory) {
            rolls++;
            lastFortune = fortune;
            inputCount = inventory.getStack(0).getCount();
            return emptyRoll ? List.of() : super.rollResults(random, fortune, inventory);
        }
    }
}
