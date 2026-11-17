package com.zombie_cute.mc.moderndelight.recipe;

import com.zombie_cute.mc.moderndelight.compat.farmersdelight.FarmersDelightCuisineCompat;
import com.zombie_cute.mc.moderndelight.recipe.custom.CuisineRecipe;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** A shared view of native cuisine recipes and optional, live cutting board recipes. */
public final class CuisineRecipes {
    private static final boolean FARMERS_DELIGHT_LOADED = FabricLoader.getInstance().isModLoaded("farmersdelight");

    public static List<CuisineRecipe> getAll(RecipeManager manager) {
        List<CuisineRecipe> recipes = new ArrayList<>(manager.listAllOfType(CuisineRecipe.Type.INSTANCE));
        if (FARMERS_DELIGHT_LOADED) {
            recipes.addAll(FarmersDelightCuisineCompat.getAll(manager));
        }
        return recipes;
    }

    public static List<CuisineRecipe> getMatches(SimpleInventory input, World world) {
        List<CuisineRecipe> recipes = new ArrayList<>(world.getRecipeManager()
                .getAllMatches(CuisineRecipe.Type.INSTANCE, input, world));
        if (FARMERS_DELIGHT_LOADED) {
            recipes.addAll(FarmersDelightCuisineCompat.getMatches(input, world));
        }
        recipes.sort(Comparator.comparing((CuisineRecipe recipe) ->
                recipe.getOutput(world.getRegistryManager()).getTranslationKey())
                .thenComparing(recipe -> recipe.getId().toString()));
        return recipes;
    }

    @Nullable
    public static CuisineRecipe get(RecipeManager manager, Identifier id) {
        Recipe<?> recipe = manager.get(id).orElse(null);
        if (recipe instanceof CuisineRecipe cuisine) {
            return cuisine;
        }
        return FARMERS_DELIGHT_LOADED ? FarmersDelightCuisineCompat.get(manager, id) : null;
    }

    @Nullable
    public static CuisineRecipe fromCuttingRecipe(Recipe<?> recipe) {
        return FARMERS_DELIGHT_LOADED ? FarmersDelightCuisineCompat.wrap(recipe) : null;
    }
}
