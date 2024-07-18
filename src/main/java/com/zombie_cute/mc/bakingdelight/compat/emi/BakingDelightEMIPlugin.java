package com.zombie_cute.mc.bakingdelight.compat.emi;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.compat.emi.recipe.*;
import com.zombie_cute.mc.bakingdelight.recipe.custom.*;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.block.Blocks;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;

public class BakingDelightEMIPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(EMIMixWithWaterRecipe.CATEGORY);
        registry.addCategory(EMIWhiskingRecipe.CATEGORY);
        registry.addCategory(EMIAssemblyRecipe.CATEGORY);
        registry.addCategory(EMIBakingTrayRecipe.CATEGORY);
        registry.addCategory(EMIBiogasFermentationRecipe.CATEGORY);
        registry.addCategory(EMICuisineRecipe.CATEGORY);
        registry.addCategory(EMIDeepFryingRecipe.CATEGORY);
        registry.addCategory(EMIFreezingRecipe.CATEGORY);
        registry.addCategory(EMIBakingRecipe.CATEGORY);
        registry.addCategory(EMIPizzaRecipe.CATEGORY);
        registry.addCategory(EMISteamingRecipe.CATEGORY);
        registry.addCategory(EMIAdvanceFurnaceRecipe.CATEGORY);
        registry.addCategory(EMIOvenRecipe.CATEGORY);
        registry.addCategory(EMIWoodenBasinRecipe.CATEGORY);
        registry.addCategory(EMIIceCreamRecipe.CATEGORY);


        registry.addWorkstation(EMIMixWithWaterRecipe.CATEGORY, EMIMixWithWaterRecipe.WORKSTATION);

        registry.addWorkstation(EMIWhiskingRecipe.CATEGORY, EMIWhiskingRecipe.WORKSTATION);

        registry.addWorkstation(EMIAssemblyRecipe.CATEGORY, EMIAssemblyRecipe.WORKSTATION);

        registry.addWorkstation(EMIBakingTrayRecipe.CATEGORY, EMIBakingTrayRecipe.WORKSTATION);
        registry.addWorkstation(EMIBakingTrayRecipe.CATEGORY, EmiStack.of(ModBlocks.GAS_CANISTER));
        registry.addWorkstation(EMIBakingTrayRecipe.CATEGORY, EmiStack.of(ModBlocks.GAS_COOKING_STOVE));

        registry.addWorkstation(EMIBiogasFermentationRecipe.CATEGORY, EMIBiogasFermentationRecipe.WORKSTATION);
        registry.addWorkstation(EMIBiogasFermentationRecipe.CATEGORY, EmiStack.of(ModBlocks.BIOGAS_DIGESTER_IO));

        registry.addWorkstation(EMICuisineRecipe.CATEGORY, EMICuisineRecipe.WORKSTATION);

        registry.addWorkstation(EMIDeepFryingRecipe.CATEGORY, EMIDeepFryingRecipe.WORKSTATION);
        registry.addWorkstation(EMIDeepFryingRecipe.CATEGORY, EmiStack.of(ModBlocks.GAS_CANISTER));

        registry.addWorkstation(EMIFreezingRecipe.CATEGORY, EMIFreezingRecipe.WORKSTATION);

        registry.addWorkstation(EMIBakingRecipe.CATEGORY, EMIBakingRecipe.WORKSTATION);
        registry.addWorkstation(EMIBakingRecipe.CATEGORY, EmiStack.of(ModBlocks.GAS_CANISTER));
        registry.addWorkstation(EMIBakingRecipe.CATEGORY, EmiStack.of(ModBlocks.GAS_COOKING_STOVE));

        registry.addWorkstation(EMISteamingRecipe.CATEGORY, EmiStack.of(ModBlocks.ELECTRIC_STEAMER));
        registry.addWorkstation(EMISteamingRecipe.CATEGORY, EmiStack.of(ModBlocks.BAMBOO_COVER));
        registry.addWorkstation(EMISteamingRecipe.CATEGORY, EMISteamingRecipe.WORKSTATION);
        registry.addWorkstation(EMISteamingRecipe.CATEGORY, EmiStack.of(Blocks.CAULDRON));
        registry.addWorkstation(EMISteamingRecipe.CATEGORY, EmiStack.of(ModBlocks.GAS_CANISTER));
        registry.addWorkstation(EMISteamingRecipe.CATEGORY, EmiStack.of(ModBlocks.GAS_COOKING_STOVE));

        registry.addWorkstation(EMIWoodenBasinRecipe.CATEGORY, EMIWoodenBasinRecipe.WORKSTATION);

        registry.addWorkstation(EMIIceCreamRecipe.CATEGORY, EMIIceCreamRecipe.WORKSTATION);



        RecipeManager manager = registry.getRecipeManager();
        for (MixWithWaterRecipe recipe : manager.listAllOfType(MixWithWaterRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EMIMixWithWaterRecipe(recipe));
        }
        for (WhiskingRecipe recipe : manager.listAllOfType(WhiskingRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EMIWhiskingRecipe(recipe));
        }
        for (AssemblyRecipe recipe : manager.listAllOfType(AssemblyRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EMIAssemblyRecipe(recipe));
        }
        for (CampfireCookingRecipe recipe : manager.listAllOfType(RecipeType.CAMPFIRE_COOKING)) {
            registry.addRecipe(new EMIBakingTrayRecipe(recipe));
        }
        registry.addRecipe(new EMIBiogasFermentationRecipe());
        for (CuisineRecipe recipe : manager.listAllOfType(CuisineRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EMICuisineRecipe(recipe));
        }
        for (DeepFryingRecipe recipe : manager.listAllOfType(DeepFryingRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EMIDeepFryingRecipe(recipe));
        }
        for (FreezingRecipe recipe : manager.listAllOfType(FreezingRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EMIFreezingRecipe(recipe));
        }
        for (BakingRecipe recipe : manager.listAllOfType(BakingRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EMIBakingRecipe(recipe));
        }
        registry.addRecipe(new EMIPizzaRecipe());
        for (SteamingRecipe recipe : manager.listAllOfType(SteamingRecipe.Type.INSTANCE)) {
            registry.addRecipe(new EMISteamingRecipe(recipe));
        }
        registry.addRecipe(new EMIAdvanceFurnaceRecipe());
        registry.addRecipe(new EMIOvenRecipe());
        registry.addRecipe(new EMIWoodenBasinRecipe());
        registry.addRecipe(new EMIIceCreamRecipe());
    }
}
