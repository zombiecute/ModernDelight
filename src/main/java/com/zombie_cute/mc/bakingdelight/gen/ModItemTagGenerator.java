package com.zombie_cute.mc.bakingdelight.gen;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
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
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.WHISKS);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.SPATULAS);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.KNEADING_STICKS);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.CROWBARS);


        getOrCreateTagBuilder(TagKeys.STORAGE_BLOCKS).add(ModBlocks.SILICON_BLOCK.asItem());
        getOrCreateTagBuilder(TagKeys.INGOTS).add(ModItems.SILICON_INGOT);
        getOrCreateTagBuilder(TagKeys.SILICON).add(ModItems.SILICON_INGOT);
        // Bottle of Vegetable Oil
        getOrCreateTagBuilder(TagKeys.BOTTLE_VEGETABLE_OIL).add(ModItems.VEGETABLE_OIL_BOTTLE);
        // Filters
        getOrCreateTagBuilder(TagKeys.FILTERS).add(ModItems.FILTER);
        // Oil Plants
        getOrCreateTagBuilder(TagKeys.OIL_PLANTS).add(ModItems.ROASTED_SUNFLOWER_SEED);
        // Spatulas
        getOrCreateTagBuilder(TagKeys.SPATULAS).add(ModItems.SPATULA);
        // Blaze Burner Fuel
        getOrCreateTagBuilder(TagKeys.BLAZE_BURNER_FUEL).add(ModItems.LIQUEFIED_BIOGAS_BUCKET);
        // Crowbars
        getOrCreateTagBuilder(TagKeys.CROWBARS).add(ModItems.CROWBAR);
        getOrCreateTagBuilder(TagKeys.CROWBARS).addOptional(new Identifier("create:wrench"));
        getOrCreateTagBuilder(TagKeys.CROWBARS).addOptional(new Identifier("techreborn:wrench"));
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
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(ModItems.LIQUEFIED_BIOGAS_BUCKET);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(ModItems.VEGETABLE_OIL_BUCKET);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).add(ModItems.CREAM_BUCKET);
        getOrCreateTagBuilder(TagKeys.UPRIGHT_ON_BELT).addTag(TagKeys.JUICE);
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
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.TOOLS_AXES);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.TOOLS_HOES);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.TOOLS_PICKAXES);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.TOOLS_SWORDS);
        getOrCreateTagBuilder(TagKeys.TOOLS).addOptionalTag(TagKeys.TOOLS_SHOVELS);
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
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.STEAMED_STUFFED_BUN_WIP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.STEAMED_STUFFED_BUN);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.VEGETABLE_STEAMED_STUFFED_BUN);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.VEGETABLE_STEAMED_STUFFED_BUN_WIP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.SHAOMAI);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.SHAOMAI_WIP);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.BLACK_PEPPER_STEAK);

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
        getOrCreateTagBuilder(TagKeys.ICE_LOLLIES).addOptional(new Identifier("farmersdelight","melon_popsicle"));
        getOrCreateTagBuilder(TagKeys.ICE_LOLLIES).add(ModItems.CHERRY_ICE_LOLLY);
        getOrCreateTagBuilder(TagKeys.ICE_LOLLIES).add(ModItems.MATCHA_ICE_LOLLY);
        getOrCreateTagBuilder(TagKeys.ICE_LOLLIES).add(ModItems.WITHER_ICE_LOLLY);
        getOrCreateTagBuilder(TagKeys.ICE_LOLLIES).add(ModItems.CHOCOLATE_CRUNCH_ICE_LOLLY);
        // Knives
        getOrCreateTagBuilder(TagKeys.TOOLS_KNIVES).add(ModItems.COPPER_KNIFE);
        getOrCreateTagBuilder(TagKeys.TOOLS_KNIVES).add(ModItems.AMETHYST_KNIFE);
        // Amethyst Tools
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_WHISK);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_SWORD);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_PICKAXE);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_AXE);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_SHOVEL);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_HOE);
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).add(ModItems.AMETHYST_KNIFE);

        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).addOptional(new Identifier("anvilcraft:amethyst_sword"));
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).addOptional(new Identifier("anvilcraft:amethyst_pickaxe"));
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).addOptional(new Identifier("anvilcraft:amethyst_axe"));
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).addOptional(new Identifier("anvilcraft:amethyst_shovel"));
        getOrCreateTagBuilder(TagKeys.AMETHYST_TOOLS).addOptional(new Identifier("anvilcraft:amethyst_hoe"));
        // Mushrooms
        getOrCreateTagBuilder(TagKeys.MUSHROOMS).add(Items.BROWN_MUSHROOM);
        getOrCreateTagBuilder(TagKeys.MUSHROOMS).add(Items.RED_MUSHROOM);
        // Kneading Sticks
        getOrCreateTagBuilder(TagKeys.KNEADING_STICKS).add(ModItems.KNEADING_STICK);
        // Vanilla Tools
        getOrCreateTagBuilder(TagKeys.TOOLS_SWORDS).add(ModItems.AMETHYST_SWORD);
        getOrCreateTagBuilder(TagKeys.TOOLS_AXES).add(ModItems.AMETHYST_AXE);
        getOrCreateTagBuilder(TagKeys.TOOLS_HOES).add(ModItems.AMETHYST_HOE);
        getOrCreateTagBuilder(TagKeys.TOOLS_PICKAXES).add(ModItems.AMETHYST_PICKAXE);
        getOrCreateTagBuilder(TagKeys.TOOLS_SHOVELS).add(ModItems.AMETHYST_SHOVEL);
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
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).addOptional(new Identifier("farmersdelight:cabbage"));
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).add(Items.PUMPKIN);
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).addOptional(new Identifier("farmersdelight:tomato"));
        getOrCreateTagBuilder(TagKeys.PIZZA_INGREDIENTS).addOptional(new Identifier("farmersdelight:onion"));

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
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.FRIED_NOODLES);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.PACKAGED_INSTANT_NOODLES);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.MATCHA);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.GARLIC_PUREE);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.STEAMED_PUMPKIN_IN_BOWL);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModBlocks.STEAMED_PUMPKIN_ITEM);
        getOrCreateTagBuilder(TagKeys.FOODS).add(ModItems.STEAMED_PUMPKIN_WIP);

        getOrCreateTagBuilder(TagKeys.FOODS).addTag(TagKeys.PASTAS);
        getOrCreateTagBuilder(TagKeys.PASTAS).add(ModItems.RAW_NOODLES);

        getOrCreateTagBuilder(TagKeys.RAW_MEATS).addOptionalTag(TagKeys.RAW_PORK);
        getOrCreateTagBuilder(TagKeys.RAW_MEATS).addOptionalTag(TagKeys.RAW_BEEF);

        getOrCreateTagBuilder(TagKeys.RAW_PORK).add(ModItems.STREAKY_PORK);
        getOrCreateTagBuilder(TagKeys.RAW_PORK).add(ModItems.PORK_RIBS);
        getOrCreateTagBuilder(TagKeys.RAW_PORK).add(ModItems.PORK_HOOF);
        getOrCreateTagBuilder(TagKeys.RAW_PORK).add(Items.PORKCHOP);

        getOrCreateTagBuilder(TagKeys.RAW_BEEF).add(Items.BEEF);

        getOrCreateTagBuilder(TagKeys.COOKED_MEATS).addOptionalTag(TagKeys.COOKED_PORK);
        getOrCreateTagBuilder(TagKeys.COOKED_PORK).add(ModItems.ROAST_STREAKY_PORK);

        getOrCreateTagBuilder(TagKeys.FOODS).addOptionalTag(TagKeys.FRUITS);
        getOrCreateTagBuilder(TagKeys.FRUITS).addOptionalTag(TagKeys.LEMON);


        getOrCreateTagBuilder(TagKeys.FRUITS).addOptionalTag(TagKeys.MANGO);

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

        getOrCreateTagBuilder(TagKeys.CREAMS_WITHOUT_PLAIN).add(ModItems.CHERRY_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS_WITHOUT_PLAIN).add(ModItems.GOLDEN_APPLE_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS_WITHOUT_PLAIN).add(ModItems.CHOCOLATE_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS_WITHOUT_PLAIN).add(ModItems.APPLE_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS_WITHOUT_PLAIN).add(ModItems.MATCHA_CREAM);
        getOrCreateTagBuilder(TagKeys.CREAMS_WITHOUT_PLAIN).add(ModItems.PUMPKIN_CREAM);

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
        getOrCreateTagBuilder(TagKeys.PUMPKINS).addOptional(new Identifier("farmersdelight:pumpkin_slice"));

        getOrCreateTagBuilder(TagKeys.SEED_BLACK_PEPPERS).add(ModItems.BLACK_PEPPER_CORN);
        getOrCreateTagBuilder(TagKeys.CROP_BLACK_PEPPER).add(ModItems.BLACK_PEPPER_CORN);
        getOrCreateTagBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(ModItems.BLACK_PEPPER_CORN);

        getOrCreateTagBuilder(TagKeys.NETHERITE_SCRAP_ORES).add(Items.ANCIENT_DEBRIS);
        getOrCreateTagBuilder(TagKeys.NETHERITE_SCRAP_NUGGETS).add(ModItems.ANCIENT_SCRAP);
        // Quicklimes
        getOrCreateTagBuilder(TagKeys.QUICKLIMES).add(ModItems.QUICKLIME);
        // Gold
        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED).add(ModItems.GOLDEN_WHISK);
        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED).add(ModItems.GOLDEN_APPLE_CREAM);
        getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED).add(ModItems.GOLDEN_APPLE_MOUSSE);
        // Anvil Craft
        getOrCreateTagBuilder(TagKeys.ROYAL_STEEL_AXE_BASE).add(ModItems.AMETHYST_AXE);
        getOrCreateTagBuilder(TagKeys.ROYAL_STEEL_HOE_BASE).add(ModItems.AMETHYST_HOE);
        getOrCreateTagBuilder(TagKeys.ROYAL_STEEL_PICKAXE_BASE).add(ModItems.AMETHYST_PICKAXE);
        getOrCreateTagBuilder(TagKeys.ROYAL_STEEL_SHOVEL_BASE).add(ModItems.AMETHYST_SHOVEL);
        getOrCreateTagBuilder(TagKeys.ROYAL_STEEL_SWORD_BASE).add(ModItems.AMETHYST_SWORD);
        getOrCreateTagBuilder(TagKeys.AMETHYST_SWORD).add(ModItems.AMETHYST_SWORD);
        getOrCreateTagBuilder(TagKeys.AMETHYST_PICKAXE).add(ModItems.AMETHYST_PICKAXE);
        getOrCreateTagBuilder(TagKeys.AMETHYST_AXE).add(ModItems.AMETHYST_AXE);
        getOrCreateTagBuilder(TagKeys.AMETHYST_SHOVEL).add(ModItems.AMETHYST_SHOVEL);
        getOrCreateTagBuilder(TagKeys.AMETHYST_HOE).add(ModItems.AMETHYST_HOE);
        // Juice
        getOrCreateTagBuilder(TagKeys.FOODS).addTag(TagKeys.JUICE);
        getOrCreateTagBuilder(TagKeys.JUICE).add(ModItems.SWEET_BERRIES_JUICE);
        getOrCreateTagBuilder(TagKeys.JUICE).add(ModItems.TOMATO_JUICE);
        getOrCreateTagBuilder(TagKeys.JUICE).add(ModBlocks.CHERRY_MILK_TEA_ITEM);
        getOrCreateTagBuilder(TagKeys.JUICE).add(ModBlocks.ROSE_ICE_TEA_TEA_ITEM);
        getOrCreateTagBuilder(TagKeys.JUICE).add(ModItems.MANGO_MILK_TEA);
        getOrCreateTagBuilder(TagKeys.JUICE).add(ModItems.SEA_SALT_LEMON);
        getOrCreateTagBuilder(TagKeys.JUICE).addOptional(new Identifier("farmersdelight","apple_cider"));
        getOrCreateTagBuilder(TagKeys.JUICE).addOptional(new Identifier("farmersdelight","melon_juice"));
        getOrCreateTagBuilder(TagKeys.JUICE).addOptional(new Identifier("create","builders_tea"));
        // Carbonate Rocks
        getOrCreateTagBuilder(TagKeys.CARBONATE_ROCKS).addOptionalTag(new Identifier("create","stone_types/limestone"));
        getOrCreateTagBuilder(TagKeys.CARBONATE_ROCKS).addOptionalTag(new Identifier("create","stone_types/dripstone"));
        getOrCreateTagBuilder(TagKeys.CARBONATE_ROCKS).addOptionalTag(new Identifier("create","stone_types/calcite"));
        getOrCreateTagBuilder(TagKeys.CARBONATE_ROCKS).add(Blocks.DRIPSTONE_BLOCK.asItem());
        getOrCreateTagBuilder(TagKeys.CARBONATE_ROCKS).add(Blocks.CALCITE.asItem());
        // Condiments
        getOrCreateTagBuilder(TagKeys.CONDIMENTS).add(ModItems.HONEY_CRYSTALLIZATION);
        getOrCreateTagBuilder(TagKeys.CONDIMENTS).add(ModItems.SEA_SALT);
        getOrCreateTagBuilder(TagKeys.CONDIMENTS).add(ModItems.GARLIC_PUREE);
        getOrCreateTagBuilder(TagKeys.CONDIMENTS).add(ModItems.BLACK_PEPPER_DUST);
        getOrCreateTagBuilder(TagKeys.CONDIMENTS).add(ModItems.BLACK_TRUFFLE);
        getOrCreateTagBuilder(TagKeys.CONDIMENTS).add(ModItems.WHITE_TRUFFLE);
        getOrCreateTagBuilder(TagKeys.CONDIMENTS).add(ModItems.CUTTLEBONE);
        getOrCreateTagBuilder(TagKeys.CONDIMENTS).add(ModItems.GLOW_CUTTLEBONE);

        // Leaves
        getOrCreateTagBuilder(ItemTags.LEAVES).add(ModItems.MATCHA);
        // Forge
        getOrCreateTagBuilder(TagKeys.CABBAGE).addOptionalTag(new Identifier("forge","crops/cabbage"));
        getOrCreateTagBuilder(TagKeys.MANGO).addOptionalTag(new Identifier("forge:fruits/mango"));
        getOrCreateTagBuilder(TagKeys.LEMON).addOptionalTag(new Identifier("forge:fruits/lemon"));
        getOrCreateTagBuilder(TagKeys.RICE).addOptionalTag(new Identifier("forge:crops/rice"));
        getOrCreateTagBuilder(TagKeys.TOMATO).addOptionalTag(new Identifier("forge:vegetables/tomato"));
        getOrCreateTagBuilder(TagKeys.TOMATOES).addOptionalTag(new Identifier("forge:vegetables/tomato"));
        getOrCreateTagBuilder(TagKeys.PASTAS).addOptionalTag(new Identifier("forge:pasta"));
        getOrCreateTagBuilder(TagKeys.IRON_PLATE).addOptionalTag(new Identifier("forge:plates/iron"));
        getOrCreateTagBuilder(TagKeys.COPPER_PLATE).addOptionalTag(new Identifier("forge:plates/copper"));
        getOrCreateTagBuilder(TagKeys.ZINC_INGOT).addOptionalTag(new Identifier("forge:ingots/zinc"));
        getOrCreateTagBuilder(TagKeys.MILKS).addOptionalTag(new Identifier("forge:milk"));
        getOrCreateTagBuilder(TagKeys.RAW_CHICKEN).addOptionalTag(new Identifier("forge:raw_chicken"));
        getOrCreateTagBuilder(TagKeys.ONION).addOptionalTag(new Identifier("forge:crops/onion"));
        getOrCreateTagBuilder(TagKeys.RAW_MUTTON).addOptionalTag(new Identifier("forge:raw_mutton"));

    }
}
