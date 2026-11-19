package com.zombie_cute.mc.moderndelight.compat;

import com.zombie_cute.mc.moderndelight.recipe.CuisineRecipes;
import com.zombie_cute.mc.moderndelight.recipe.custom.CuisineRecipe;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;

public class CuisineCompatibilityTests implements FabricGameTest {
    private static boolean loaded() {
        return FabricLoader.getInstance().isModLoaded("farmersdelight");
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void nativeRecipesAndOptionalClassLoading(TestContext context) {
        var manager = context.getWorld().getRecipeManager();
        var id = new Identifier("moderndelight_tests", "native_compatibility");
        var recipe = new CuisineRecipe(id, DefaultedList.copyOf(Ingredient.EMPTY,
                Ingredient.ofItems(Items.DIRT), Ingredient.ofItems(Items.STICK)), new ItemStack(Items.DIAMOND, 2));
        var recipes = new ArrayList<Recipe<?>>(manager.values());
        recipes.add(recipe);
        manager.setRecipes(recipes);
        context.assertTrue(CuisineRecipes.get(manager, id) == recipe, "Native lookup must preserve the recipe");
        context.assertTrue(CuisineRecipes.getMatches(new SimpleInventory(new ItemStack(Items.DIRT),
                new ItemStack(Items.STICK)), context.getWorld()).contains(recipe), "Native matching must still work");
        context.assertTrue(CuisineRecipes.getAll(manager).contains(recipe), "Native recipes must remain visible");
        context.assertTrue(CuisineRecipes.get(manager, new Identifier("moderndelight", "cutting/missing/recipe")) == null,
                "Missing optional recipes must be harmless");
        context.assertTrue(CuisineRecipes.fromCuttingRecipe(recipe) == null, "Native recipes must not be adapted twice");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void allCuttingRecipesAndRealSalmon(TestContext context) {
        if (loaded()) CuttingCompatibilityChecks.allRecipesAndSalmon(context);
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void multiOutputPickupAndShiftClick(TestContext context) {
        if (loaded()) CuttingCompatibilityChecks.multiOutput(context);
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void previewDoesNotRollAndFortuneIsForwarded(TestContext context) {
        if (loaded()) CuttingCompatibilityChecks.previewAndFortune(context);
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void missingMainResultAndEmptyRollStillConsume(TestContext context) {
        if (loaded()) CuttingCompatibilityChecks.missingMainAndEmptyRoll(context);
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void fullInventoryAndStaleOutputAreRejected(TestContext context) {
        if (loaded()) CuttingCompatibilityChecks.fullInventoryAndStaleOutput(context);
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void dataPackRecipeReplacementAndRemoval(TestContext context) {
        if (loaded()) CuttingCompatibilityChecks.reload(context);
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void nonDamageableToolsAndUnbreakableToolsAreKept(TestContext context) {
        if (loaded()) CuttingCompatibilityChecks.toolRules(context);
        context.complete();
    }
}
