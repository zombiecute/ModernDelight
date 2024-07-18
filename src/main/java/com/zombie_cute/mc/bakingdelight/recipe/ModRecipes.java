package com.zombie_cute.mc.bakingdelight.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.recipe.custom.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Bakingdelight.MOD_ID, BakingRecipe.Serializer.ID),
                BakingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Bakingdelight.MOD_ID, BakingRecipe.Type.ID),
                BakingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Bakingdelight.MOD_ID, WhiskingRecipe.Serializer.ID),
                WhiskingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Bakingdelight.MOD_ID, WhiskingRecipe.Type.ID),
                WhiskingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Bakingdelight.MOD_ID, FreezingRecipe.Serializer.ID),
                FreezingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Bakingdelight.MOD_ID, FreezingRecipe.Type.ID),
                FreezingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Bakingdelight.MOD_ID, MixWithWaterRecipe.Serializer.ID),
                MixWithWaterRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Bakingdelight.MOD_ID, MixWithWaterRecipe.Type.ID),
                MixWithWaterRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Bakingdelight.MOD_ID, DeepFryingRecipe.Serializer.ID),
                DeepFryingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Bakingdelight.MOD_ID, DeepFryingRecipe.Type.ID),
                DeepFryingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Bakingdelight.MOD_ID, CuisineRecipe.Serializer.ID),
                CuisineRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Bakingdelight.MOD_ID, CuisineRecipe.Type.ID),
                CuisineRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Bakingdelight.MOD_ID, AssemblyRecipe.Serializer.ID),
                AssemblyRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Bakingdelight.MOD_ID, AssemblyRecipe.Type.ID),
                AssemblyRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Bakingdelight.MOD_ID, SteamingRecipe.Serializer.ID),
                SteamingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Bakingdelight.MOD_ID, SteamingRecipe.Type.ID),
                SteamingRecipe.Type.INSTANCE);
    }
}
