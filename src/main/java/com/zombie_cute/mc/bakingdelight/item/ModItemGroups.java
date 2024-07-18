package com.zombie_cute.mc.bakingdelight.item;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
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
            new Identifier(Bakingdelight.MOD_ID,"bakingdelight_itemgroup"),
            FabricItemGroup.builder().displayName(Text.translatable(GROUPS_TAB_NAME))
                    .icon(()->new ItemStack(ModBlocks.GLASS_BOWL))
                    .entries((displayContext, entries) -> {
                        // Whisks & Glass Bowl
                        entries.add(ModItems.WOODEN_WHISK);
                        entries.add(ModItems.STONE_WHISK);
                        entries.add(ModItems.COPPER_WHISK);
                        entries.add(ModItems.IRON_WHISK);
                        entries.add(ModItems.GOLDEN_WHISK);
                        entries.add(ModItems.DIAMOND_WHISK);
                        entries.add(ModItems.NETHERITE_WHISK);
                        entries.add(ModItems.AMETHYST_WHISK);
                        entries.add(ModBlocks.GLASS_BOWL);
                        // Amethyst Tools & Knifes & Stir-frying
                        entries.add(ModItems.AMETHYST_SWORD);
                        entries.add(ModItems.AMETHYST_PICKAXE);
                        entries.add(ModItems.AMETHYST_AXE);
                        entries.add(ModItems.AMETHYST_SHOVEL);
                        entries.add(ModItems.AMETHYST_HOE);
                        entries.add(ModItems.AMETHYST_KNIFE);
                        entries.add(ModItems.COPPER_KNIFE);
                        entries.add(ModItems.SPATULA);
                        entries.add(ModBlocks.BAKING_TRAY);
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
                        // Gas System
                        entries.add(ModBlocks.BIOGAS_DIGESTER_CONTROLLER);
                        entries.add(ModBlocks.BIOGAS_DIGESTER_IO);
                        entries.add(ModBlocks.GAS_CANISTER);
                        entries.add(ModBlocks.GAS_COOKING_STOVE);
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
                        // Materials
                        entries.add(ModItems.SILICON_INGOT);
                        entries.add(ModBlocks.SILICON_BLOCK);
                        entries.add(ModItems.REDSTONE_COMPONENT);
                        entries.add(ModItems.SILICON_COMPONENT);
                        entries.add(ModItems.DIAMOND_COMPONENT);
                        // Freezer
                        entries.add(ModBlocks.FREEZER);
                        entries.add(ModItems.ICE_BRICK);
                        // Steamer
                        entries.add(ModBlocks.BAMBOO_GRATE);
                        entries.add(ModBlocks.BAMBOO_COVER);
                        entries.add(ModBlocks.ELECTRIC_STEAMER);
                        // Ice Cream
                        entries.add(ModBlocks.ICE_CREAM_MAKER_ITEM);
                        entries.add(ModItems.RAW_ICE_CREAM_CONE);
                        entries.add(ModItems.ICE_CREAM_CONE);
                        entries.add(ModItems.ICE_CREAM);
                        // Cuisine & Storage
                        entries.add(ModBlocks.CUISINE_TABLE);
                        entries.add(ModBlocks.DEEPSLATE_CABINET);
                        entries.add(ModBlocks.GRANITE_CABINET);
                        entries.add(ModBlocks.DIORITE_CABINET);
                        entries.add(ModBlocks.ANDESITE_CABINET);
                        entries.add(ModBlocks.OBSIDIAN_CABINET);
                        entries.add(ModBlocks.BLACKSTONE_CABINET);
                        entries.add(ModBlocks.BASALT_CABINET);
                        // Building
                        entries.add(ModBlocks.KITCHEN_UTENSIL_HOLDER);
                        // Misc
                        entries.add(ModItems.ANCIENT_SCRAP);
                        // Jars
                        entries.add(ModItems.JAR);
                        entries.add(ModItems.CARAMEL);
                        entries.add(ModItems.CHOCOLATE_SAUCE);
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
                        // Snacks
                        entries.add(ModItems.APPLE_PETAL);
                        entries.add(ModItems.FRIED_APPLE);

                        entries.add(ModItems.RAW_ONION_RING);
                        entries.add(ModItems.ONION_RING);

                        entries.add(ModItems.FRIED_MILK_WIP);
                        entries.add(ModItems.FRIED_MILK);



                        entries.add(ModItems.CHEESE_BALL);
                        entries.add(ModItems.FRIED_DOUGH_STICK);
                        entries.add(ModItems.STEAMED_BUN);
                        entries.add(ModItems.DEEP_FRIED_BUN);

                        entries.add(ModItems.CHERRY);
                        entries.add(ModItems.CHERRY_BOMB);
                        entries.add(ModBlocks.BOXED_CHERRIES);

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
                        entries.add(ModItems.CHERRY_EGG);
                        entries.add(ModItems.FISH_EGG);
                        entries.add(ModItems.STEAMED_EGG);
                        entries.add(ModItems.STEAMED_CHERRY_EGG);
                        entries.add(ModItems.STEAMED_FISH_EGG);
                        // Puddings
                        entries.add(ModItems.PUDDING_WIP_1);
                        entries.add(ModItems.PUDDING_WIP_2);
                        entries.add(ModItems.APPLE_PUDDING);
                        entries.add(ModItems.MATCHA_PUDDING);
                        entries.add(ModItems.CARAMEL_PUDDING);
                        entries.add(ModItems.CHERRY_PUDDING);
                        // Ice Lolly
                        entries.add(ModItems.ICE_LOLLY);
                        entries.add(ModItems.CHERRY_ICE_LOLLY);
                        entries.add(ModItems.MATCHA_ICE_LOLLY);
                        entries.add(ModItems.WITHER_ICE_LOLLY);

                        // Bread
                        entries.add(ModItems.BREAD_SLICE);
                        entries.add(ModItems.BUTTER_BREAD_SLICE);
                        entries.add(ModItems.RAW_DONUT);
                        entries.add(ModItems.DONUT);
                        entries.add(ModItems.CHOCOLATE_DONUT);
                        // Creams
                        entries.add(ModItems.CREAM_BUCKET);
                        entries.add(ModItems.CREAM);
                        entries.add(ModItems.APPLE_CREAM);
                        entries.add(ModItems.CHERRY_CREAM);
                        entries.add(ModItems.CHOCOLATE_CREAM);
                        entries.add(ModItems.GOLDEN_APPLE_CREAM);
                        entries.add(ModItems.MATCHA_CREAM);
                        entries.add(ModItems.PUMPKIN_CREAM);
                        // Mousses
                        entries.add(ModItems.MOUSSE_WIP);
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
                        entries.add(ModItems.CREAM_OF_MUSHROOM_SOUP);
                        entries.add(ModItems.FRENCH_ONION_SOUP);
                        entries.add(ModItems.CHEESE_BURGER);
                        entries.add(ModItems.PORK_CHOP_BURGER);
                        entries.add(ModBlocks.FISH_AND_CHIPS_ITEM);
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
                        entries.add(ModItems.SHRIMP_PASTE);
                        entries.add(ModItems.MIXED_SHRIMP_PASTE);
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
                        entries.add(ModItems.GRILLED_SAUSAGE);
                        entries.add(ModItems.STARCH_SAUSAGE);
                        entries.add(ModItems.GRILLED_STARCH_SAUSAGE);
                        entries.add(ModItems.LITTLE_OCTOPUS_SAUSAGE);
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
                        // Unknown
                        entries.add(ModItems.TURNIP);
                    }).build());
    public static void registerItemGroup(){
        Bakingdelight.LOGGER.info("Registering Item Group for " + Bakingdelight.MOD_ID);
    }
}
