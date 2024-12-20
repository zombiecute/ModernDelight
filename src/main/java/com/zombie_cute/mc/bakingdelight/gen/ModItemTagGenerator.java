package com.zombie_cute.mc.bakingdelight.gen;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends FabricTagProvider.ItemTagProvider {

    public ModItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(TagKeys.STORAGE_BLOCKS).add(ModBlocks.SILICON_BLOCK.asItem());
        getOrCreateTagBuilder(TagKeys.INGOTS).add(ModItems.SILICON_INGOT);
        getOrCreateTagBuilder(TagKeys.SILICON).add(ModItems.SILICON_INGOT);
        // Bucket of Oil
        getOrCreateTagBuilder(TagKeys.BUCKET_OIL).add(ModItems.VEGETABLE_OIL_BUCKET);
        // Bottle of Oil
        getOrCreateTagBuilder(TagKeys.BOTTLE_OIL).add(ModItems.VEGETABLE_OIL_BOTTLE);
        // Filters
        getOrCreateTagBuilder(TagKeys.FILTERS).add(ModItems.FILTER);
        // Oil Plants
        getOrCreateTagBuilder(TagKeys.OIL_PLANTS).add(ModItems.ROASTED_SUNFLOWER_SEED);
        // Spatulas
        getOrCreateTagBuilder(TagKeys.SPATULAS).add(ModItems.SPATULA);
        // Crowbars
        getOrCreateTagBuilder(TagKeys.CROWBARS).add(ModItems.CROWBAR);
        getOrCreateTagBuilder(TagKeys.CROWBARS).addOptional(Identifier.of("create:wrench"));
        // Upright on Belt
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(ModItems.EGG_TART);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(ModItems.TRUFFLE_EGG_TART);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(ModItems.CUTTLEBONE);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(ModItems.GLOW_CUTTLEBONE);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(ModItems.CRYSTAL_DUMPLING);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).addTag(TagKeys.CREAMS);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(Items.BOWL);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(ModItems.MASHED_POTATO);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(ModItems.SAUCE_MASHED_POTATO);
        // Flat on Baking Tray
        getOrCreateTagBuilder(TagKeys.FLAT_ON_BAKING_TRAY).add(ModBlocks.RAW_PIZZA.asItem());
        getOrCreateTagBuilder(TagKeys.FLAT_ON_BAKING_TRAY).add(ModBlocks.PIZZA_WIP.asItem());
        getOrCreateTagBuilder(TagKeys.FLAT_ON_BAKING_TRAY).add(ModBlocks.PIZZA.asItem());
        getOrCreateTagBuilder(TagKeys.FLAT_ON_BAKING_TRAY).addOptionalTag(ItemTags.TRAPDOORS);
        getOrCreateTagBuilder(TagKeys.FLAT_ON_BAKING_TRAY).addOptionalTag(BlockTags.PRESSURE_PLATES.id());
        // Whisks
        getOrCreateTagBuilder(TagKeys.WHISKS).add(ModItems.AMETHYST_WHISK);
        getOrCreateTagBuilder(TagKeys.WHISKS).add(ModItems.WOODEN_WHISK);
        getOrCreateTagBuilder(TagKeys.WHISKS).add(ModItems.COPPER_WHISK);
        getOrCreateTagBuilder(TagKeys.WHISKS).add(ModItems.STONE_WHISK);
        getOrCreateTagBuilder(TagKeys.WHISKS).add(ModItems.IRON_WHISK);
        getOrCreateTagBuilder(TagKeys.WHISKS).add(ModItems.GOLDEN_WHISK);
        getOrCreateTagBuilder(TagKeys.WHISKS).add(ModItems.DIAMOND_WHISK);
        getOrCreateTagBuilder(TagKeys.WHISKS).add(ModItems.NETHERITE_WHISK);
        getOrCreateTagBuilder(TagKeys.WHISKS).add(ModItems.ELECTRIC_WHISK);
        // Tools
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.TOOLS_KNIVES);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.TOOLS_MINING_TOOLS);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.TOOLS_MELEE_WEAPONS);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.FILTERS);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.WHISKS);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.ROLLING_PINS);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.CROWBARS);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.SPATULAS);
        // Foods
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.SUNFLOWER_SEED_PULP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.EMPTY_CAKE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.RAW_ONION_RING);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.ONION_RING);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_COD);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_SALMON);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_MILK_WIP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_MILK);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_APPLE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.RAW_POTATO_CHIP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.POTATO_CHIP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CHEESE_BALL);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_DOUGH_STICK);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.STEAMED_BUN);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.DEEP_FRIED_BUN);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.EGG_BOWL);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CHERRY_EGG);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FISH_EGG);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.STEAMED_EGG);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.STEAMED_CHERRY_EGG);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.STEAMED_FISH_EGG);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.BEEF_TOMATO_CUP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.BUTTERFLY_CRISP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CARAMEL);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CHEESE_BAKED_POTATO);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CHEESE_RICE_BALL);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CHOCOLATE_SAUCE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.SHRIMP_PASTE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.MIXED_SHRIMP_PASTE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.DEEP_FRIED_SHRIMP_CAKE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.SEAWEED_FRIED_SHRIMP_CAKE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.DONUT);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.POTATO_CHIPS);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.DEEP_FRIED_POTATO_CHIPS);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRENCH_FRIES);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_CHICKEN);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_RED_MUSHROOM);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_BROWN_MUSHROOM);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CREAM_OF_MUSHROOM_SOUP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRENCH_ONION_SOUP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.RAW_DONUT);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CHOCOLATE_DONUT);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CHEESE_BURGER);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_COD_NUGGET);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.MILKS);


        getOrCreateTagBuilder(TagKeys.ICE_CREAMS).add(ModItems.RAW_ICE_CREAM_CONE);
        getOrCreateTagBuilder(TagKeys.ICE_CREAMS).add(ModItems.ICE_CREAM_CONE);
        getOrCreateTagBuilder(TagKeys.ICE_CREAMS).add(ModItems.ICE_CREAM);

        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.ICE_LOLLIES);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.BREADS);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.TRUFFLES);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.PUDDINGS);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.FLOURS);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.DOUGHS);
        getOrCreateTagBuilder(TagKeys.DOUGHS).addOptionalTag(TagKeys.DOUGH_WHEAT);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.CREAMS);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.MOUSSES);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.RAW_FISHES);
        getOrCreateTagBuilder(TagKeys.RAW_FISHES).addOptionalTag(TagKeys.PRAWNS);
        getOrCreateTagBuilder(TagKeys.RAW_FISHES).addOptionalTag(TagKeys.SQUIDS);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.CUTTLEBONES);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.FLOWER_CAKES);
        getOrCreateTagBuilder(TagKeys.SAUSAGES).addOptionalTag(TagKeys.SAUSAGES);
        getOrCreateTagBuilder(TagKeys.BREADS).addOptionalTag(TagKeys.BREAD_WHEAT);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.PIZZA_INGREDIENTS);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.PUMPKINS);
        getOrCreateTagBuilder(TagKeys.SEEDS).addOptionalTag(TagKeys.SEED_BLACK_PEPPERS);
        getOrCreateTagBuilder(TagKeys.CROPS).addOptionalTag(TagKeys.CROP_BLACK_PEPPER);
        getOrCreateTagBuilder(TagKeys.SEEDS).addOptionalTag(TagKeys.SEED_GARLIC);
        getOrCreateTagBuilder(TagKeys.CROPS).addOptionalTag(TagKeys.CROP_GARLIC);
        getOrCreateTagBuilder(TagKeys.CROPS).addOptionalTag(TagKeys.CABBAGE);
        getOrCreateTagBuilder(TagKeys.C_WHEAT_FLOUR).add(ModItems.WHEAT_FLOUR);
        getOrCreateTagBuilder(TagKeys.C_FLOUR).add(ModItems.WHEAT_FLOUR);
        getOrCreateTagBuilder(TagKeys.C_FLOUR).add(ModItems.POTATO_STARCH);
        getOrCreateTagBuilder(TagKeys.C_WHEAT_DOUGH).add(ModBlocks.WHEAT_DOUGH.asItem());
        getOrCreateTagBuilder(TagKeys.C_DOUGH).add(ModItems.MIXED_DOUGH);
        getOrCreateTagBuilder(TagKeys.C_DOUGH).add(ModBlocks.WHEAT_DOUGH.asItem());
        // Flower Cakes
        getOrCreateTagBuilder(TagKeys.FLOWER_CAKES).add(ModItems.BLUE_ORCHID_FLOWER_CAKE);
        getOrCreateTagBuilder(TagKeys.FLOWER_CAKES).add(ModItems.CHERRY_CAKE);
        getOrCreateTagBuilder(TagKeys.FLOWER_CAKES).add(ModItems.LILAC_CAKE);
        getOrCreateTagBuilder(TagKeys.FLOWER_CAKES).add(ModItems.ORANGE_TULIP_CAKE);
        getOrCreateTagBuilder(TagKeys.FLOWER_CAKES).add(ModItems.OXEYE_DAISY_CAKE);
        getOrCreateTagBuilder(TagKeys.FLOWER_CAKES).add(ModItems.PINK_TULIP_CAKE);
        getOrCreateTagBuilder(TagKeys.FLOWER_CAKES).add(ModItems.ROSE_CAKE);
        getOrCreateTagBuilder(TagKeys.FLOWER_CAKES).add(ModItems.SUNFLOWER_CAKE);
        getOrCreateTagBuilder(TagKeys.FLOWER_CAKES).add(ModItems.WHITE_TULIP_CAKE);
        getOrCreateTagBuilder(TagKeys.FLOWER_CAKES).add(ModItems.WITHER_ROSE_CAKE);
        // Ice Lollies
        getOrCreateTagBuilder(TagKeys.ICE_LOLLIES).add(ModItems.ICE_LOLLY);
        getOrCreateTagBuilder(TagKeys.ICE_LOLLIES).addOptional(Identifier.of("farmersdelight","melon_popsicle"));
        getOrCreateTagBuilder(TagKeys.ICE_LOLLIES).add(ModItems.CHERRY_ICE_LOLLY);
        getOrCreateTagBuilder(TagKeys.ICE_LOLLIES).add(ModItems.MATCHA_ICE_LOLLY);
        getOrCreateTagBuilder(TagKeys.ICE_LOLLIES).add(ModItems.WITHER_ICE_LOLLY);
        // Milks
        getOrCreateTagBuilder(TagKeys.MILKS).add(Items.MILK_BUCKET);
        getOrCreateTagBuilder(TagKeys.MILKS).addOptional(Identifier.of("farmersdelight","milk_bottle"));

        // Knives
        getOrCreateTagBuilder(TagKeys.TOOLS_KNIVES).add(ModItems.COPPER_KNIFE);
        getOrCreateTagBuilder(TagKeys.TOOLS_KNIVES).add(ModItems.AMETHYST_KNIFE);
        getOrCreateTagBuilder(TagKeys.KNIVES).add(ModItems.COPPER_KNIFE);
        getOrCreateTagBuilder(TagKeys.KNIVES).add(ModItems.AMETHYST_KNIFE);
        // Amethyst Tools
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_WHISK);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_SWORD);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_PICKAXE);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_AXE);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_SHOVEL);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_HOE);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_KNIFE);
        // Kneading Sticks
        getOrCreateTagBuilder(TagKeys.ROLLING_PINS).add(ModItems.ROLLING_PIN);
        // Vanilla Tools
        getOrCreateTagBuilder(TagKeys.TOOLS_MELEE_WEAPONS).add(ModItems.AMETHYST_SWORD);
        getOrCreateTagBuilder(TagKeys.TOOLS_MELEE_WEAPONS).add(ModItems.AMETHYST_AXE);
        getOrCreateTagBuilder(TagKeys.TOOLS_MINING_TOOLS).add(ModItems.AMETHYST_PICKAXE);
        getOrCreateTagBuilder(ItemTags.HOES).add(ModItems.AMETHYST_HOE);
        getOrCreateTagBuilder(ItemTags.SWORDS).add(ModItems.AMETHYST_SWORD);
        getOrCreateTagBuilder(ItemTags.PICKAXES).add(ModItems.AMETHYST_PICKAXE);
        getOrCreateTagBuilder(ItemTags.AXES).add(ModItems.AMETHYST_AXE);
        getOrCreateTagBuilder(ItemTags.SHOVELS).add(ModItems.AMETHYST_SHOVEL);
        // Enchantable Tools
        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE).addOptionalTag(TagKeys.SPATULAS);
        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE).addOptionalTag(TagKeys.WHISKS);
        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE).addOptionalTag(TagKeys.ROLLING_PINS);
        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE).addOptionalTag(TagKeys.CROWBARS);
        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE).addOptionalTag(TagKeys.FILTERS);

        getOrCreateTagBuilder(ItemTags.SWORD_ENCHANTABLE).addOptionalTag(TagKeys.WHISKS);

        getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).addOptionalTag(TagKeys.SPATULAS);
        getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).addOptionalTag(TagKeys.ROLLING_PINS);
        getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).addOptionalTag(TagKeys.CROWBARS);

        getOrCreateTagBuilder(ItemTags.VANISHING_ENCHANTABLE).addOptionalTag(TagKeys.SPATULAS);
        getOrCreateTagBuilder(ItemTags.VANISHING_ENCHANTABLE).addOptionalTag(TagKeys.WHISKS);
        getOrCreateTagBuilder(ItemTags.VANISHING_ENCHANTABLE).addOptionalTag(TagKeys.ROLLING_PINS);
        getOrCreateTagBuilder(ItemTags.VANISHING_ENCHANTABLE).addOptionalTag(TagKeys.CROWBARS);
        getOrCreateTagBuilder(ItemTags.VANISHING_ENCHANTABLE).addOptionalTag(TagKeys.FILTERS);
        // Dough Wheat
        getOrCreateTagBuilder(TagKeys.DOUGH_WHEAT).add(ModBlocks.WHEAT_DOUGH.asItem());
        // Cold Items
        getOrCreateTagBuilder(TagKeys.COLD_ITEMS).add(ModItems.ICE_BRICK);
        getOrCreateTagBuilder(TagKeys.COLD_ITEMS).add(Items.SNOWBALL);
        // Foods
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.POTATO);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.CARROT);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.BEEF);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.COD);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.SALMON);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.CHICKEN);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.RABBIT);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.MUTTON);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.PORKCHOP);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.TROPICAL_FISH);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(ModItems.BUTTER);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(ModItems.PRAWN);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(ModItems.SQUID);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(ModItems.GLOW_SQUID);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(ModItems.WHITE_TRUFFLE);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(ModItems.BLACK_TRUFFLE);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(ModItems.SECTIONED_SAUSAGE);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).addOptional(Identifier.of("farmersdelight:cabbage"));
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.PUMPKIN);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).addOptional(Identifier.of("farmersdelight:tomato"));
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).addOptional(Identifier.of("farmersdelight:onion"));

        getOrCreateTagBuilder(TagKeys.TRUFFLES).add(ModItems.WHITE_TRUFFLE);
        getOrCreateTagBuilder(TagKeys.TRUFFLES).add(ModItems.BLACK_TRUFFLE);

        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.BLACK_PEPPER_CORN);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.BLACK_PEPPER_DUST);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.GARLIC);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.GARLIC_PETAL);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.SUNFLOWER_SEED);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.MASHED_POTATO);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.SAUCE_MASHED_POTATO);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.APPLE_PETAL);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CHERRY);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.CHEESE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.EGG_TART);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.TRUFFLE_EGG_TART);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.BRAISED_SHRIMP_BALL);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModBlocks.PIZZA.asItem());
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModBlocks.RAW_PIZZA.asItem());
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModBlocks.PIZZA_WIP.asItem());
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.GLOW_SQUID_TENTACLE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.GLOW_SQUID_TENTACLE_KEBABS);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.RAW_GLOW_SQUID_TENTACLE_KEBABS);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.SQUID_TENTACLE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.RAW_SQUID_TENTACLE_KEBABS);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.SQUID_TENTACLE_KEBABS);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.TURNIP);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.RAW_MEATS);
        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.COOKED_MEATS);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.PORK_CHOP_BURGER);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModBlocks.FISH_AND_CHIPS_ITEM);

        getOrCreateTagBuilder(TagKeys.RAW_MEATS).addOptionalTag(TagKeys.RAW_PORK);
        getOrCreateTagBuilder(TagKeys.RAW_PORK).add(ModItems.STREAKY_PORK);
        getOrCreateTagBuilder(TagKeys.RAW_PORK).add(ModItems.PORK_RIBS);
        getOrCreateTagBuilder(TagKeys.RAW_PORK).add(ModItems.PORK_HOOF);

        getOrCreateTagBuilder(TagKeys.COOKED_MEATS).addOptionalTag(TagKeys.COOKED_PORK);
        getOrCreateTagBuilder(TagKeys.COOKED_PORK).add(ModItems.ROAST_STREAKY_PORK);


        getOrCreateTagBuilder(TagKeys.FLOURS).add(ModItems.POTATO_STARCH);
        getOrCreateTagBuilder(TagKeys.FLOURS).add(ModItems.WHEAT_FLOUR);

        getOrCreateTagBuilder(TagKeys.DOUGHS).add(ModItems.MIXED_DOUGH);

        getOrCreateTagBuilder(TagKeys.PUDDINGS).add(ModItems.PUDDING_WIP_1);
        getOrCreateTagBuilder(TagKeys.PUDDINGS).add(ModItems.PUDDING_WIP_2);
        getOrCreateTagBuilder(TagKeys.PUDDINGS).add(ModItems.APPLE_PUDDING);
        getOrCreateTagBuilder(TagKeys.PUDDINGS).add(ModItems.MATCHA_PUDDING);
        getOrCreateTagBuilder(TagKeys.PUDDINGS).add(ModItems.MATCHA_PUDDING);
        getOrCreateTagBuilder(TagKeys.PUDDINGS).add(ModItems.CARAMEL_PUDDING);
        getOrCreateTagBuilder(TagKeys.PUDDINGS).add(ModItems.CHERRY_PUDDING);

        getOrCreateTagBuilder(TagKeys.CREAMS).add(ModItems.CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS).add(ModItems.CHERRY_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS).add(ModItems.GOLDEN_APPLE_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS).add(ModItems.CHOCOLATE_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS).add(ModItems.APPLE_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS).add(ModItems.MATCHA_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS).add(ModItems.PUMPKIN_CREAM);

        getOrCreateTagBuilder(TagKeys.MOUSSES).add(ModItems.MOUSSE_WIP);
        getOrCreateTagBuilder(TagKeys.MOUSSES).add(ModItems.CREAM_MOUSSE);
        getOrCreateTagBuilder(TagKeys.MOUSSES).add(ModItems.MATCHA_MOUSSE);
        getOrCreateTagBuilder(TagKeys.MOUSSES).add(ModItems.CHERRY_MOUSSE);
        getOrCreateTagBuilder(TagKeys.MOUSSES).add(ModItems.CHOCOLATE_MOUSSE);
        getOrCreateTagBuilder(TagKeys.MOUSSES).add(ModItems.GOLDEN_APPLE_MOUSSE);
        getOrCreateTagBuilder(TagKeys.MOUSSES).add(ModItems.PUMPKIN_MOUSSE);

        getOrCreateTagBuilder(TagKeys.SQUIDS).add(ModItems.SQUID);
        getOrCreateTagBuilder(TagKeys.SQUIDS).add(ModItems.GLOW_SQUID);

        getOrCreateTagBuilder(TagKeys.PRAWNS).add(ModItems.PRAWN);

        getOrCreateTagBuilder(TagKeys.CUTTLEBONES).add(ModItems.CUTTLEBONE);
        getOrCreateTagBuilder(TagKeys.CUTTLEBONES).add(ModItems.GLOW_CUTTLEBONE);

        getOrCreateTagBuilder(TagKeys.SAUSAGES).add(ModItems.SAUSAGE);
        getOrCreateTagBuilder(TagKeys.SAUSAGES).add(ModItems.SECTIONED_SAUSAGE);
        getOrCreateTagBuilder(TagKeys.SAUSAGES).add(ModItems.STARCH_SAUSAGE);
        getOrCreateTagBuilder(TagKeys.SAUSAGES).add(ModItems.GRILLED_SAUSAGE);
        getOrCreateTagBuilder(TagKeys.SAUSAGES).add(ModItems.GRILLED_STARCH_SAUSAGE);
        getOrCreateTagBuilder(TagKeys.SAUSAGES).add(ModItems.LITTLE_OCTOPUS_SAUSAGE);

        getOrCreateTagBuilder(TagKeys.BREADS).add(ModItems.BUTTER_BREAD_SLICE);

        getOrCreateTagBuilder(TagKeys.BREAD_WHEAT).add(ModItems.BREAD_SLICE);

        getOrCreateTagBuilder(TagKeys.PUMPKINS).add(Items.PUMPKIN);
        getOrCreateTagBuilder(TagKeys.PUMPKINS).addOptional(Identifier.of("farmersdelight:pumpkin_slice"));

        getOrCreateTagBuilder(TagKeys.SEED_BLACK_PEPPERS).add(ModItems.BLACK_PEPPER_CORN);
        getOrCreateTagBuilder(TagKeys.CROP_BLACK_PEPPER).add(ModItems.BLACK_PEPPER_CORN);
        getOrCreateTagBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(ModItems.BLACK_PEPPER_CORN);

        // Gold
        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED).add(ModItems.GOLDEN_WHISK);
        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED).add(ModItems.GOLDEN_APPLE_CREAM);
        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED).add(ModItems.GOLDEN_APPLE_MOUSSE);
    }
}
