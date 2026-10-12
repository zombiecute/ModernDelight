package com.zombie_cute.mc.bakingdelight.item;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final String GROUPS_TAB_NAME = "itemgroup.bakingdelight";
    public static final ItemGroup ITEM_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(ModernDelightMain.MOD_ID,"bakingdelight_itemgroup"),
            FabricItemGroup.builder().displayName(Text.translatable(GROUPS_TAB_NAME))
                    .icon(()->new ItemStack(ModBlocks.GLASS_BOWL))
                    .entries((displayContext, entries) -> {
                        // Whisks & Glass Bowl
                        entries.add(ModItems.ELECTRIC_WHISK);
                        entries.add(ModItems.IRON_WHISK);
                        entries.add(ModBlocks.GLASS_BOWL);
                        // Amethyst Tools & Knifes & Stir-frying
                        entries.add(ModItems.AMETHYST_KNIFE);
                        entries.add(ModItems.COPPER_KNIFE);
                        entries.add(ModItems.SPATULA);
                        entries.add(ModBlocks.BAKING_TRAY);
                        entries.add(ModItems.STONE_MORTAR);
                        // Pizza Making & Oven
                        entries.add(ModItems.KNEADING_STICK);
                        entries.add(ModBlocks.WHEAT_DOUGH);
                        entries.add(ModBlocks.PIZZA_WIP);
                        entries.add(ModBlocks.RAW_PIZZA);
                        entries.add(ModBlocks.PIZZA);
                        entries.add(ModItems.CHEESE);
                        entries.add(ModBlocks.ADVANCE_FURNACE);
                        entries.add(ModBlocks.OVEN);
                        entries.add(ModItems.CROWBAR);
                        // Oil Extraction
                        entries.add(ModItems.FILTER);
                        entries.add(ModBlocks.WOODEN_BASIN);
                        entries.add(ModItems.VEGETABLE_OIL_BOTTLE);
                        entries.add(ModItems.VEGETABLE_OIL_BUCKET);
                        entries.add(ModItems.OIL_IMPURITY);
                        entries.add(ModBlocks.DEEP_FRYER);
                        entries.add(ModBlocks.DEEP_FRY_BASKET);
                        entries.add(ModItems.HOLDER);
                        // Gas System
                        entries.add(ModBlocks.BIOGAS_DIGESTER_CONTROLLER);
                        entries.add(ModBlocks.BIOGAS_DIGESTER_IO);
                        entries.add(ModBlocks.GAS_CANISTER);
                        entries.add(ModBlocks.GAS_COOKING_STOVE);
                        entries.add(ModItems.LIQUEFIED_BIOGAS_BUCKET);
                        // Power System
                        entries.add(ModBlocks.ELECTRICIANS_DESK);
                        entries.add(ModBlocks.PHOTOVOLTAIC_GENERATOR);
                        entries.add(ModBlocks.FAN_BLADE_ITEM);
                        entries.add(ModBlocks.WIND_TURBINE_CONTROLLER);
                        entries.add(ModBlocks.STERLING_ENGINE_ITEM);
                        entries.add(ModBlocks.FARADAY_GENERATOR);
                        entries.add(ModBlocks.AC_DC_CONVERTER);
                        entries.add(ModBlocks.TESLA_COIL);
                        entries.add(ModBlocks.SIMPLE_BATTERY);
                        entries.add(ModBlocks.INTERMEDIATE_BATTERY);
                        entries.add(ModBlocks.ADVANCE_BATTERY);
                        entries.add(ModBlocks.DIMENSION_BATTERY);
                        entries.add(ModBlocks.CHARGING_POST);
                        // Materials
                        entries.add(ModItems.SILICON_INGOT);
                        entries.add(ModBlocks.SILICON_BLOCK);
                        entries.add(ModItems.REDSTONE_COMPONENT);
                        entries.add(ModItems.SILICON_COMPONENT);
                        entries.add(ModItems.DIAMOND_COMPONENT);
                        // Freezer
                        entries.add(ModBlocks.FREEZER);
                        // Steamer
                        entries.add(ModBlocks.BAMBOO_GRATE);
                        entries.add(ModBlocks.BAMBOO_COVER);
                        entries.add(ModBlocks.ELECTRIC_STEAMER);
                        // Ice Cream
                        entries.add(ModBlocks.ICE_CREAM_MAKER_ITEM);
                        entries.add(ModItems.RAW_ICE_CREAM_CONE);
                        entries.add(ModItems.ICE_CREAM_CONE);
                        entries.add(ModItems.ICE_CREAM);
                        // Juice
                        entries.add(ModBlocks.JUICE_EXTRACTOR);
                        entries.add(ModItems.TOMATO_JUICE);
                        entries.add(ModItems.SWEET_BERRIES_JUICE);
                        entries.add(ModBlocks.GLASS_CUP);
                        entries.add(ModBlocks.CHERRY_MILK_TEA);
                        entries.add(ModBlocks.ROSE_ICE_TEA);
                        entries.add(ModItems.MANGO_MILK_TEA);
                        entries.add(ModItems.SEA_SALT_LEMON);
                        // Cuisine & Storage
                        entries.add(ModBlocks.CUISINE_TABLE);
                        entries.add(ModBlocks.DEEPSLATE_CABINET);
                        entries.add(ModBlocks.GRANITE_CABINET);
                        entries.add(ModBlocks.DIORITE_CABINET);
                        entries.add(ModBlocks.ANDESITE_CABINET);
                        entries.add(ModBlocks.OBSIDIAN_CABINET);
                        entries.add(ModBlocks.BLACKSTONE_CABINET);
                        entries.add(ModBlocks.BASALT_CABINET);
                        // Fermenter
                        entries.add(ModBlocks.INDUSTRIAL_FERMENTER);
                        entries.add(ModItems.FERMENTATION_TANK);
                        entries.add(ModItems.SWEETENED_WATER_BUCKET);
                        entries.add(ModItems.ALCOHOL_BUCKET);
                        entries.add(ModItems.VINEGAR_BUCKET);
                        // Clay Pots
                        entries.add(ModBlocks.CLAY_POT);
                        entries.add(ModBlocks.MARSHALA_CHEESE_BALL);
                        entries.add(ModBlocks.CHICKEN_RICE);
                        entries.add(ModBlocks.CHILI_FISH);
                        entries.add(ModBlocks.CRAB_SAUSAGE_VEGETABLE);
                        entries.add(ModBlocks.FRENCH_ONION_SOUP);
                        entries.add(ModBlocks.PORK_NOODLE);
                        entries.add(ModBlocks.PEPPER_BEEF_RICE);
                        entries.add(ModBlocks.VEGETABLE_NOODLE);
                        // Building
                        entries.add(ModBlocks.KITCHEN_UTENSIL_HOLDER);
                        entries.add(ModBlocks.WOODEN_PLATE);
                        // Instant Noodles
                        entries.add(ModItems.RAW_NOODLES);
                        entries.add(ModItems.FRIED_NOODLES);
                        entries.add(ModItems.MULTIFUNCTIONAL_WRAPPING_PAPER);
                        entries.add(ModItems.DIRTY_WRAPPING_PAPER);
                        entries.add(ModItems.PACKAGED_INSTANT_NOODLES);
                        entries.add(ModItems.PORTABLE_POT);
                        entries.add(ModItems.COOKED_PORTABLE_POT);
                        entries.add(ModItems.QUICKLIME);
                        // Misc
                        entries.add(ModItems.ANCIENT_SCRAP);
                        entries.add(ModItems.GRINDED_LEAVES);
                        // Jars
                        entries.add(ModItems.JAR);
                        entries.add(ModItems.CARAMEL);
                        entries.add(ModItems.CHOCOLATE_SAUCE);
                        // Seasoning Items
                        entries.add(ModItems.GARLIC_PUREE);
                        entries.add(ModItems.HONEY_CRYSTALLIZATION);
                        entries.add(ModItems.SEA_SALT);
                        entries.add(ModItems.MATCHA);
                        entries.add(ModItems.BLACK_PEPPER_SEASONING);
                        entries.add(ModItems.BLACK_TRUFFLE_SEASONING);
                        entries.add(ModItems.WHITE_TRUFFLE_SEASONING);
                        // Truffles
                        entries.add(ModItems.BLACK_TRUFFLE);
                        entries.add(ModItems.WHITE_TRUFFLE);
                        // Wild Crops
                        entries.add(ModBlocks.WILD_PEPPER_CROP);
                        entries.add(ModBlocks.WILD_GARLIC);
                        // Black Pepper
                        entries.add(ModItems.BLACK_PEPPER_CORN);
                        entries.add(ModItems.BLACK_PEPPER_DUST);
                        // Garlic
                        entries.add(ModItems.GARLIC);
                        entries.add(ModItems.GARLIC_PETAL);
                        // Starch
                        entries.add(ModItems.POTATO_STARCH);
                        entries.add(ModItems.WHEAT_FLOUR);
                        entries.add(ModItems.MIXED_DOUGH);
                        // Potato
                        entries.add(ModItems.MASHED_POTATO);
                        entries.add(ModItems.SAUCE_MASHED_POTATO);
                        entries.add(ModBlocks.MASHED_POTATO_BLOCK);
                        entries.add(ModItems.RAW_POTATO_CHIP);
                        entries.add(ModItems.POTATO_CHIP);
                        entries.add(ModItems.POTATO_CHIPS);
                        entries.add(ModItems.DEEP_FRIED_POTATO_CHIPS);
                        entries.add(ModItems.FRENCH_FRIES);
                        entries.add(ModItems.PACKAGING_BAG);
                        entries.add(ModItems.DIRTY_PACKAGING_BAG);
                        // Chicken
                        entries.add(ModItems.RAW_CHICKEN_FILLET);
                        entries.add(ModItems.CHICKEN_FILLET);
                        // Fruits
                        entries.add(ModItems.APPLE_PETAL);
                        entries.add(ModItems.FRIED_APPLE);
                        entries.add(ModItems.CHERRY);
                        entries.add(ModItems.CHERRY_BOMB);
                        entries.add(ModBlocks.BOXED_CHERRIES);
                        entries.add(ModItems.PINECONE);
                        entries.add(ModItems.ORANGE);
                        entries.add(ModItems.PEAR);
                        entries.add(ModItems.LEMON);
                        entries.add(ModItems.MANGO);

                        // Snacks
                        entries.add(ModItems.FRIED_MILK_WIP);
                        entries.add(ModItems.FRIED_MILK);

                        entries.add(ModItems.FRIED_DOUGH_STICK);
                        entries.add(ModItems.STEAMED_BUN);
                        entries.add(ModItems.DEEP_FRIED_BUN);

                        entries.add(ModItems.BUTTER);
                        entries.add(ModItems.BUTTERFLY_CRISP);

                        entries.add(ModItems.EGG_TART);
                        entries.add(ModItems.TRUFFLE_EGG_TART);

                        entries.add(ModItems.FRIED_BROWN_MUSHROOM);
                        entries.add(ModItems.FRIED_RED_MUSHROOM);

                        entries.add(ModItems.SUNFLOWER_SEED);
                        entries.add(ModItems.SUNFLOWER_SEED_PEEL);
                        entries.add(ModItems.SUNFLOWER_SEED_PULP);
                        entries.add(ModItems.ROASTED_SUNFLOWER_SEED);

                        entries.add(ModItems.EGG_BOWL);
                        entries.add(ModItems.STEAMED_EGG);
                        entries.add(ModItems.CHERRY_EGG);
                        entries.add(ModItems.STEAMED_CHERRY_EGG);
                        entries.add(ModItems.FISH_EGG);
                        entries.add(ModItems.STEAMED_FISH_EGG);
                        entries.add(ModItems.STEAMED_STUFFED_BUN);
                        entries.add(ModItems.VEGETABLE_STEAMED_STUFFED_BUN);
                        entries.add(ModItems.SHAOMAI);

                        entries.add(ModBlocks.STEAMED_PUMPKIN);
                        entries.add(ModItems.STEAMED_PUMPKIN_IN_BOWL);
                        // Puddings
                        entries.add(ModItems.APPLE_PUDDING);
                        entries.add(ModItems.MATCHA_PUDDING);
                        entries.add(ModItems.CARAMEL_PUDDING);
                        entries.add(ModItems.CHERRY_PUDDING);
                        // Ice Lolly
                        entries.add(ModItems.ICE_LOLLY);
                        entries.add(ModItems.CHERRY_ICE_LOLLY);
                        entries.add(ModItems.MATCHA_ICE_LOLLY);
                        entries.add(ModItems.WITHER_ICE_LOLLY);
                        entries.add(ModItems.CHOCOLATE_CRUNCH_ICE_LOLLY);
                        // Bread
                        entries.add(ModItems.BREAD_SLICE);
                        entries.add(ModItems.BUTTER_BREAD_SLICE);
                        entries.add(ModItems.CARAMEL_TOAST);
                        entries.add(ModItems.RAW_DONUT);
                        entries.add(ModItems.DONUT);
                        entries.add(ModItems.CHOCOLATE_DONUT);
                        // Creams
                        entries.add(ModItems.CREAM_BUCKET);
                        entries.add(ModItems.WHIPPING_CREAM);
                        entries.add(ModItems.CREAM);
                        entries.add(ModItems.APPLE_CREAM);
                        entries.add(ModItems.CHERRY_CREAM);
                        entries.add(ModItems.CHOCOLATE_CREAM);
                        entries.add(ModItems.GOLDEN_APPLE_CREAM);
                        entries.add(ModItems.MATCHA_CREAM);
                        entries.add(ModItems.PUMPKIN_CREAM);
                        // Mousses
                        entries.add(ModItems.CREAM_MOUSSE);
                        entries.add(ModItems.CHERRY_MOUSSE);
                        entries.add(ModItems.CHOCOLATE_MOUSSE);
                        entries.add(ModItems.PUMPKIN_MOUSSE);
                        entries.add(ModItems.GOLDEN_APPLE_MOUSSE);
                        entries.add(ModItems.MATCHA_MOUSSE);
                        // Dumplings
                        entries.add(ModItems.CRYSTAL_DUMPLING);
                        // Great Meal
                        entries.add(ModItems.BEEF_TOMATO_CUP);
                        entries.add(ModItems.CHEESE_BAKED_POTATO);
                        entries.add(ModItems.CHEESE_RICE_BALL);
                        entries.add(ModItems.FRIED_CHICKEN);
                        entries.add(ModItems.CHEESE_BURGER);
                        entries.add(ModItems.PORK_CHOP_BURGER);
                        entries.add(ModBlocks.FISH_AND_CHIPS_ITEM);
                        entries.add(ModItems.BLACK_PEPPER_STEAK);
                        entries.add(ModBlocks.BRITISH_BREAKFAST);
                        // Pork
                        entries.add(ModItems.STREAKY_PORK);
                        entries.add(ModItems.ROAST_STREAKY_PORK);
                        entries.add(ModItems.PORK_RIBS);
                        entries.add(ModItems.PORK_HOOF);
                        // Fish
                        entries.add(ModItems.FRIED_COD);
                        entries.add(ModItems.FRIED_COD_NUGGET);
                        entries.add(ModItems.FRIED_SALMON);
                        // Shrimp
                        entries.add(ModItems.PRAWN);
                        entries.add(ModItems.BRAISED_SHRIMP_BALL);
                        entries.add(ModItems.DEEP_FRIED_SHRIMP_CAKE);
                        entries.add(ModItems.SEAWEED_FRIED_SHRIMP_CAKE);
                        // Squid
                        entries.add(ModItems.SQUID);
                        entries.add(ModItems.ROASTED_SQUID);
                        entries.add(ModItems.SQUID_TENTACLE);
                        entries.add(ModItems.RAW_SQUID_TENTACLE_KEBABS);
                        entries.add(ModItems.SQUID_TENTACLE_KEBABS);
                        entries.add(ModItems.CUTTLEBONE);

                        entries.add(ModItems.GLOW_SQUID);
                        entries.add(ModItems.ROASTED_GLOW_SQUID);
                        entries.add(ModItems.GLOW_SQUID_TENTACLE);
                        entries.add(ModItems.RAW_GLOW_SQUID_TENTACLE_KEBABS);
                        entries.add(ModItems.GLOW_SQUID_TENTACLE_KEBABS);
                        entries.add(ModItems.GLOW_CUTTLEBONE);
                        // Sausages
                        entries.add(ModItems.SAUSAGE);
                        entries.add(ModItems.SECTIONED_SAUSAGE);
                        // Cakes
                        entries.add(ModItems.EMPTY_CAKE);
                        entries.add(ModItems.BLUE_ORCHID_FLOWER_CAKE);
                        entries.add(ModItems.CHERRY_CAKE);
                        entries.add(ModItems.LILAC_CAKE);
                        entries.add(ModItems.ORANGE_TULIP_CAKE);
                        entries.add(ModItems.OXEYE_DAISY_CAKE);
                        entries.add(ModItems.PINK_TULIP_CAKE);
                        entries.add(ModItems.ROSE_CAKE);
                        entries.add(ModItems.SUNFLOWER_CAKE);
                        entries.add(ModItems.WHITE_TULIP_CAKE);
                        entries.add(ModItems.WITHER_ROSE_CAKE);
                        // WIP Item (Not unfinished items, but rather unfinished items crafted by players)
                        entries.add(ModItems.PUDDING_WIP_1);
                        entries.add(ModItems.PUDDING_WIP_2);
                        entries.add(ModItems.MOUSSE_WIP);
                        entries.add(ModItems.SHAOMAI_WIP);
                        entries.add(ModItems.DEEP_FRIED_BUN_WIP);
                        entries.add(ModItems.STEAMED_STUFFED_BUN_WIP);
                        entries.add(ModItems.STEAMED_PUMPKIN_WIP);
                        entries.add(ModItems.VEGETABLE_STEAMED_STUFFED_BUN_WIP);
                        entries.add(ModItems.BRAISED_SHRIMP_BALL_WIP);
                        // Unknown
                        entries.add(ModItems.TURNIP);
                    }).build());
    public static void registerItemGroup(){
        ModernDelightMain.LOGGER.info("Registering Item Group for " + ModernDelightMain.MOD_ID);
    }
}
