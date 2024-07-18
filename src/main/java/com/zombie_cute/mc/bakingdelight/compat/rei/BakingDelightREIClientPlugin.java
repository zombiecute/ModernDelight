package com.zombie_cute.mc.bakingdelight.compat.rei;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.compat.rei.assembly.AssemblyCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.assembly.AssemblyDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.baking_tray.BakingTrayCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.baking_tray.BakingTrayDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.biogas_fermentation.BiogasFermentationCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.biogas_fermentation.BiogasFermentationDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.cuisine.CuisineCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.cuisine.CuisineDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.deep_frying.DeepFryingCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.deep_frying.DeepFryingDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.freezer.FreezerFreezingCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.freezer.FreezerFreezingDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.glass_bowl.GlassBowlMixWithWaterCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.glass_bowl.GlassBowlMixWithWaterDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.glass_bowl.GlassBowlWhiskingCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.glass_bowl.GlassBowlWhiskingDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.ice_cream_making.IceCreamMakingCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.ice_cream_making.IceCreamMakingDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.oven.OvenBakingCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.oven.OvenBakingDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.pizza.PizzaMakingCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.pizza.PizzaMakingDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.steaming.SteamingCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.steaming.SteamingDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.transform.AdvanceFurnaceTransformCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.transform.AdvanceFurnaceTransformDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.transform.OvenTransformCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.transform.OvenTransformDisplay;
import com.zombie_cute.mc.bakingdelight.compat.rei.wooden_basin.WoodenBasinCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.wooden_basin.WoodenBasinDisplay;
import com.zombie_cute.mc.bakingdelight.recipe.custom.*;
import com.zombie_cute.mc.bakingdelight.screen.custom.*;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.block.Blocks;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeType;

public class BakingDelightREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(
                new OvenBakingCategory(),
                new FreezerFreezingCategory(),
                new GlassBowlWhiskingCategory(),
                new GlassBowlMixWithWaterCategory(),
                new PizzaMakingCategory(),
                new OvenTransformCategory(),
                new AdvanceFurnaceTransformCategory(),
                new BakingTrayCategory(),
                new WoodenBasinCategory(),
                new BiogasFermentationCategory(),
                new DeepFryingCategory(),
                new CuisineCategory(),
                new AssemblyCategory(),
                new SteamingCategory(),
                new IceCreamMakingCategory()
        );
        registry.addWorkstations(OvenBakingCategory.OVEN_BAKING, EntryStacks.of(ModBlocks.OVEN));
        registry.addWorkstations(OvenBakingCategory.OVEN_BAKING, EntryStacks.of(ModBlocks.GAS_CANISTER));
        registry.addWorkstations(OvenBakingCategory.OVEN_BAKING, EntryStacks.of(ModBlocks.GAS_COOKING_STOVE));
        registry.addWorkstations(FreezerFreezingCategory.FREEZING, EntryStacks.of(ModBlocks.FREEZER));
        registry.addWorkstations(GlassBowlWhiskingCategory.WHISKING, EntryStacks.of(ModBlocks.GLASS_BOWL));
        registry.addWorkstations(GlassBowlMixWithWaterCategory.MIX_WITH_WATER, EntryStacks.of(ModBlocks.GLASS_BOWL));
        registry.addWorkstations(CategoryIdentifier.of("minecraft:plugins/smelting"),EntryStacks.of(ModBlocks.ADVANCE_FURNACE));
        registry.addWorkstations(CategoryIdentifier.of("minecraft:plugins/smelting"),EntryStacks.of(ModBlocks.GAS_CANISTER));
        registry.addWorkstations(CategoryIdentifier.of("minecraft:plugins/smelting"),EntryStacks.of(ModBlocks.GAS_COOKING_STOVE));
        registry.addWorkstations(CategoryIdentifier.of("minecraft:plugins/fuel"),EntryStacks.of(ModBlocks.ADVANCE_FURNACE));
        registry.addWorkstations(CategoryIdentifier.of("minecraft:plugins/fuel"),EntryStacks.of(ModBlocks.OVEN));
        registry.addWorkstations(BakingTrayCategory.STIR_FRYING, EntryStacks.of(ModBlocks.BAKING_TRAY));
        registry.addWorkstations(BakingTrayCategory.STIR_FRYING, EntryStacks.of(ModBlocks.GAS_CANISTER));
        registry.addWorkstations(BakingTrayCategory.STIR_FRYING, EntryStacks.of(ModBlocks.GAS_COOKING_STOVE));
        registry.addWorkstations(WoodenBasinCategory.WOODEN_BASIN,EntryStacks.of(ModBlocks.WOODEN_BASIN));
        registry.addWorkstations(BiogasFermentationCategory.BIOGAS_FERMENTATION, EntryStacks.of(ModBlocks.BIOGAS_DIGESTER_IO));
        registry.addWorkstations(BiogasFermentationCategory.BIOGAS_FERMENTATION, EntryStacks.of(ModBlocks.BIOGAS_DIGESTER_CONTROLLER));
        registry.addWorkstations(DeepFryingCategory.DEEP_FRYING, EntryStacks.of(ModBlocks.DEEP_FRYER));
        registry.addWorkstations(DeepFryingCategory.DEEP_FRYING, EntryStacks.of(ModBlocks.DEEP_FRY_BASKET));
        registry.addWorkstations(DeepFryingCategory.DEEP_FRYING, EntryStacks.of(ModBlocks.GAS_CANISTER));
        registry.addWorkstations(CuisineCategory.CUISINE, EntryStacks.of(ModBlocks.CUISINE_TABLE));
        registry.addWorkstations(AssemblyCategory.ASSEMBLY, EntryStacks.of(ModBlocks.ELECTRICIANS_DESK));
        registry.addWorkstations(SteamingCategory.STEAMING, EntryStacks.of(ModBlocks.ELECTRIC_STEAMER));
        registry.addWorkstations(SteamingCategory.STEAMING, EntryStacks.of(ModBlocks.BAMBOO_COVER));
        registry.addWorkstations(SteamingCategory.STEAMING, EntryStacks.of(ModBlocks.BAMBOO_GRATE));
        registry.addWorkstations(SteamingCategory.STEAMING, EntryStacks.of(Blocks.CAULDRON));
        registry.addWorkstations(SteamingCategory.STEAMING, EntryStacks.of(ModBlocks.GAS_COOKING_STOVE));
        registry.addWorkstations(SteamingCategory.STEAMING, EntryStacks.of(ModBlocks.GAS_CANISTER));
        registry.addWorkstations(IceCreamMakingCategory.ICE_CREAM_MAKING, EntryStacks.of(ModBlocks.ICE_CREAM_MAKER));

    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(BakingRecipe.class, BakingRecipe.Type.INSTANCE, OvenBakingDisplay::new);
        registry.registerRecipeFiller(FreezingRecipe.class, FreezingRecipe.Type.INSTANCE, FreezerFreezingDisplay::new);
        registry.registerRecipeFiller(WhiskingRecipe.class, WhiskingRecipe.Type.INSTANCE, GlassBowlWhiskingDisplay::new);
        registry.registerRecipeFiller(MixWithWaterRecipe.class, MixWithWaterRecipe.Type.INSTANCE, GlassBowlMixWithWaterDisplay::new);
        registry.add(new PizzaMakingDisplay());
        registry.add(new OvenTransformDisplay());
        registry.add(new AdvanceFurnaceTransformDisplay());
        registry.registerRecipeFiller(CampfireCookingRecipe.class, RecipeType.CAMPFIRE_COOKING, BakingTrayDisplay::new);
        registry.add(new WoodenBasinDisplay());
        registry.add(new BiogasFermentationDisplay());
        registry.registerRecipeFiller(DeepFryingRecipe.class, DeepFryingRecipe.Type.INSTANCE, DeepFryingDisplay::new);
        registry.registerRecipeFiller(CuisineRecipe.class, CuisineRecipe.Type.INSTANCE, CuisineDisplay::new);
        registry.registerRecipeFiller(AssemblyRecipe.class, AssemblyRecipe.Type.INSTANCE, AssemblyDisplay::new);
        registry.registerRecipeFiller(SteamingRecipe.class, SteamingRecipe.Type.INSTANCE, SteamingDisplay::new);
        registry.add(new IceCreamMakingDisplay());

    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 92,(screen.height-166)/2 + 21,24,17), OvenScreen.class,
                OvenBakingCategory.OVEN_BAKING);
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 154,(screen.height-166)/2 + 34,12,16), FreezerScreen.class,
                FreezerFreezingCategory.FREEZING);
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 43,(screen.height-166)/2 + 34,126,24), AdvanceFurnaceScreen.class,
                CategoryIdentifier.of("minecraft:plugins/smelting"));
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 161,(screen.height-166)/2 + 5,11,11), WoodenBasinScreen.class,
                WoodenBasinCategory.WOODEN_BASIN);
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 161,(screen.height-166)/2 + 5,11,11), BiogasDigesterControllerScreen.class,
                BiogasFermentationCategory.BIOGAS_FERMENTATION);
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 64,(screen.height-166)/2 + 36,84,18), BiogasDigesterIOScreen.class,
                BiogasFermentationCategory.BIOGAS_FERMENTATION);
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 161,(screen.height-166)/2 + 5,11,11), DeepFryerScreen.class,
                DeepFryingCategory.DEEP_FRYING);
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 161,(screen.height-166)/2 + 5,11,11), CuisineTableScreen.class,
                CuisineCategory.CUISINE);
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 84,(screen.height-166)/2 + 26,22,15), ElectriciansDeskScreen.class,
                AssemblyCategory.ASSEMBLY);
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 149,(screen.height-166)/2 + 5,22,19), BambooSteamerScreen.class,
                SteamingCategory.STEAMING);
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 161,(screen.height-166)/2 + 5,11,11), ElectricSteamerScreen.class,
                SteamingCategory.STEAMING);
        registry.registerClickArea(screen -> new Rectangle((screen.width-176)/2 + 63,(screen.height-166)/2 + 22,19,42), IceCreamMakerScreen.class,
                IceCreamMakingCategory.ICE_CREAM_MAKING);
    }
}
