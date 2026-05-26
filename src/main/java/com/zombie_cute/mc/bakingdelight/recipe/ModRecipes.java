package com.zombie_cute.mc.bakingdelight.recipe;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.recipe.custom.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID, BakingRecipe.Serializer.ID),
                BakingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, BakingRecipe.Type.ID),
                BakingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID, WhiskingRecipe.Serializer.ID),
                WhiskingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, WhiskingRecipe.Type.ID),
                WhiskingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID, FreezingRecipe.Serializer.ID),
                FreezingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, FreezingRecipe.Type.ID),
                FreezingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID, MixWithWaterRecipe.Serializer.ID),
                MixWithWaterRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, MixWithWaterRecipe.Type.ID),
                MixWithWaterRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID, DeepFryingRecipe.Serializer.ID),
                DeepFryingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, DeepFryingRecipe.Type.ID),
                DeepFryingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID, CuisineRecipe.Serializer.ID),
                CuisineRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, CuisineRecipe.Type.ID),
                CuisineRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID, AssemblyRecipe.Serializer.ID),
                AssemblyRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, AssemblyRecipe.Type.ID),
                AssemblyRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID, SteamingRecipe.Serializer.ID),
                SteamingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, SteamingRecipe.Type.ID),
                SteamingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID, JuiceExtractingRecipe.Serializer.ID),
                JuiceExtractingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, JuiceExtractingRecipe.Type.ID),
                JuiceExtractingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID,InstantNoodlesRecipe.Serializer.ID),
                InstantNoodlesRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, GrindingRecipe.Type.ID),
                GrindingRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID,GrindingRecipe.Serializer.ID),
                GrindingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(ModernDelightMain.MOD_ID, SqueezeRecipe.Type.ID),
                SqueezeRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(ModernDelightMain.MOD_ID,SqueezeRecipe.Serializer.ID),
                SqueezeRecipe.Serializer.INSTANCE);
    }
}
