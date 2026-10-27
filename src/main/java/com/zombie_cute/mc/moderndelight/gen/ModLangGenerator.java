package com.zombie_cute.mc.moderndelight.gen;

import com.zombie_cute.mc.moderndelight.ModernDelightClient;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.biogas.GasCanisterBlockEntity;
import com.zombie_cute.mc.moderndelight.block.food.pizza.PizzaWIPBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.*;
import com.zombie_cute.mc.moderndelight.block.kitchenware.decor.CabinetBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.gas_cooking.deep_frying.DeepFryerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.gas_cooking.deep_frying.WoodenBasinBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.juice_extractor.JuiceExtractorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.kitchenware.steaming.BambooGrateBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.PhotovoltaicGeneratorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.batteries.BatteryBlockItem;
import com.zombie_cute.mc.moderndelight.compat.rei.baking_tray.BakingTrayCategory;
import com.zombie_cute.mc.moderndelight.compat.rei.biogas_fermentation.BiogasFermentationCategory;
import com.zombie_cute.mc.moderndelight.compat.rei.glass_bowl.GlassBowlMixWithWaterCategory;
import com.zombie_cute.mc.moderndelight.compat.rei.glass_bowl.GlassBowlWhiskingCategory;
import com.zombie_cute.mc.moderndelight.compat.rei.pizza.PizzaMakingCategory;
import com.zombie_cute.mc.moderndelight.compat.rei.transform.OvenTransformCategory;
import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.moderndelight.enchantment.ModEnchantments;
import com.zombie_cute.mc.moderndelight.entity.ModEntities;
import com.zombie_cute.mc.moderndelight.item.ModItemGroups;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import com.zombie_cute.mc.moderndelight.screen.custom.*;
import com.zombie_cute.mc.moderndelight.util.TextUtil;
import com.zombie_cute.mc.moderndelight.util.enums.BacteriaType;
import com.zombie_cute.mc.moderndelight.util.enums.CreamFlavor;
import com.zombie_cute.mc.moderndelight.util.enums.SpecialIngredient;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModLangGenerator extends FabricLanguageProvider {
    public ModLangGenerator(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(TextUtil.HOLD_A_KEY, "Hold ");
        translationBuilder.add(TextUtil.SHIFT_END, " for summary");
        translationBuilder.add(TextUtil.CTRL_END, " to view fluid information");
        translationBuilder.add(TextUtil.WHISK,
                "A whisk that can be used to mix ingredients, or... whisk the brains of monsters?");
        translationBuilder.add(TextUtil.BUTTER,
                "That sticky texture... maybe it's good for something besides food.");
        translationBuilder.add(TextUtil.TRUFFLE, "Found in podzol.");
        translationBuilder.add(TextUtil.CUTTLEBONE,
                "Dropped when a squid is killed with an amethyst tool.");
        translationBuilder.add(TextUtil.FILTER,
                "A string filter for the wooden basin. It filters oil and sludge.");
        translationBuilder.add(TextUtil.KNEADING_STICK, "It beats and kneads people.");
        translationBuilder.add(TextUtil.SPATULA, "It can be used with a heated tray to stir-fry foods.");
        translationBuilder.add(TextUtil.BDC,
                "The core of a biogas digester. When a flat, completely sealed cuboid space exists beneath it, " +
                        "the controller recognizes it automatically.");
        translationBuilder.add(TextUtil.BDI,
                "Activates when placed above a working biogas digester controller. Fill it with food to ferment it into biogas. " +
                        "Attach a gas canister to the side to fill it with biogas.");
        translationBuilder.add(TextUtil.GAS_COOKING_STOVE,
                "When it is flanked by a gas canister containing gas, " +
                        "right-click to activate it, and when activated," +
                        " it can heat up the block above it, including baking tray, oven, " +
                        "and Advanced Furnaces, all of which are compatible.");
        translationBuilder.add(TextUtil.CROWBAR, "The Holy Sword of Physics.");
        translationBuilder.add(TextUtil.INGREDIENTS, "Ingredients: ");
        translationBuilder.add(TextUtil.BAKING_TRAY,
                "A small, easy-to-use iron plate for upgrading an Advanced Furnace or stir-frying. " +
                        "Heat it with a Gas Cooking Stove, place food on top, then stir-fry it five times with a spatula.");
        translationBuilder.add(TextUtil.DEEP_FRYER,
                "A deep fryer for cooking a variety of foods. Add cooking oil, place a Gas Canister behind it, " +
                        "press the front button, then add food to start frying. Hold Shift and right-click to view its status.");
        translationBuilder.add(TextUtil.WOODEN_BASIN,
                "Extract vegetable oil by adding an oil crop (such as stir-fried sunflower seeds) and a filter, " +
                        "then stand on the basin and jump. Collect the oil with a glass bottle or bucket.");
        translationBuilder.add(TextUtil.HOLDER,
                "Safely remove items from the Deep Fryer and transfer items between containers. " +
                        "Right-click to release a held item; right-click while empty-handed to grab the item in your other hand.");
        translationBuilder.add(TextUtil.ALT_END, " for electricity usage");
        translationBuilder.add(TextUtil.ACDCC,
                "A bidirectional AC/DC converter. In AC->DC mode it converts and stores power from an AC generator, " +
                        "and can charge batteries. In DC->AC mode it outputs stored or battery power as AC. " +
                        "Adjust its efficiency in the GUI; FE, RF and AE conversion is also supported.");
        translationBuilder.add(TextUtil.ALT_ACGen, "[AC power generation]");
        translationBuilder.add(TextUtil.ALT_ACCom, "[AC power consumption]");
        translationBuilder.add(TextUtil.ALT_DCSto, "[Maximum DC storage]");
        translationBuilder.add(TextUtil.ALT_DCGen, "[DC power generation]");
        translationBuilder.add(TextUtil.ALT_DCCom, "[DC power consumption]");
        translationBuilder.add(TextUtil.BAMBOO_STEAMER,
                "A simple bamboo steamer, requires a cover, basket drawers, " +
                        "and a gas cooking stove heated cauldron filled with water," +
                        " add the ingredients and it will start the process, " +
                        "a Steamer can be stacked 3 blocks high, each block can hold 4 layers.");
        translationBuilder.add(TextUtil.CUISINE_TABLE,
                "The cuisine table allows you to deepen the ingredients and use it " +
                        "with a tool that breaks down the ingredients into more parts.");
        translationBuilder.add(TextUtil.ELECTRICIANS_DESK,
                "The electrician's desk allows you to synthesize and process some complex circuit components " +
                        "or appliances, after placing the material correctly into the desk, click on the output button " +
                        "that appears in the GUI to start the synthesis. There are 3 different types of mini-games: " +
                        "1. 2048 (by pressing WASD to synthesize the specified number); " +
                        "2. Splicing of the circuit board (reproduce the pattern of the \"?\" screen, " +
                        "the screwdriver can remove the pattern); 3. Hanno's Tower (just move the left tower to the right, " +
                        "narrow blocks can only be placed on wide blocks); Finish the mini-game and then press that output" +
                        " button again to get the finished product.");
        translationBuilder.add(TextUtil.ELECTRIC_STEAMER,
                "Like a regular steamer, but it consumes AC power to heat the water and can " +
                        "only process 12 ingredients at once, it has the advantage of taking up little space " +
                        "and does not require additional heating equipment.");
        translationBuilder.add(TextUtil.FARADAY_GENERATOR,
                "When properly mounted behind the flywheel of a Stirling engine, AC power is generated," +
                        " be sure that the flywheel of the Stirling engine and the generator " +
                        "must look like they are just able to be connected in order to generate power.");
        translationBuilder.add(TextUtil.FAN_BLADE,
                "A fan blade for wind power generation, which can be mounted on the wind turbine controller" +
                        " or placed elsewhere for decorative purposes, its rotation speed depends on the current " +
                        "altitude and weather conditions.");
        translationBuilder.add(TextUtil.FREEZER,
                "A simple freezer, which can be used for storing items or cold processing some ingredients," +
                        " can be cooled by consuming some refrigerants, such as ice," +
                        " or by directly consuming AC electricity.");
        translationBuilder.add(TextUtil.GLASS_BOWL,
                "A container for processing and whisking ingredients that needs to be used with a whisk, " +
                        "water can also be poured into it for \"mixing with water\".");
        translationBuilder.add(TextUtil.PGEN,
                "A photovoltaic generator converts light energy into DC electrical energy and stores it, " +
                        "batteries can be plugged in and charged, and the amount of power generated depends on " +
                        "factors such as current altitude, time of day, weather, block light and sky light." +
                        "It will also attempt to supply power to the DC-powered devices located behind it.");
        translationBuilder.add(TextUtil.STERLING_ENGINE,
                "Mount the Stirling Engine on a lit furnace, blast furnace, smoker, Advanced Furnace or Oven, " +
                        "then connect it to a Faraday Generator to produce AC power. A sponge dampens its noise.");
        translationBuilder.add(TextUtil.TESLA_COIL,
                "The Tesla Coil can transmit AC power remotely, and when placed on a" +
                        " device that generates AC power it spreads the power over an area of" +
                        " 8 blocks east, south, west, north, up, and down. Enable the visualization in the GUI to see the 48-block area. " +
                        "AC appliances and other Tesla Coils in range receive power, while nearby mobs can be shocked and damaged.");
        translationBuilder.add(TextUtil.WTC,
                "When the fan blades are properly installed, " +
                        "the controller will begin to operate and generate AC power, " +
                        "the amount of power generated depends on the current altitude and weather conditions.");
        translationBuilder.add(TextUtil.ICE_CREAM_MAKER,
                        "Hold Shift and right-click to open the GUI. Add cream of any flavor, sugar, and eggs, then supply AC power. " +
                        "Select the flavors to mix, then hold an ice cream cone and right-click to serve.");
        translationBuilder.add(TextUtil.ELECTRIC_WHISK_MSG, "Low battery!");
        translationBuilder.add(TextUtil.ELECTRIC_WHISK_NEED_BOWL, "This item can only be processed by matching the glass bowl!");
        translationBuilder.add(TextUtil.ELECTRIC_WHISK,
                "Like a regular whisk, it can be used with a glass bowl to enable whisking of food, " +
                        "but it will consume DC power and you will need a charging post to charge it. " +
                        "In addition, it is also effective to the drops on the ground.");
        translationBuilder.add(TextUtil.CHARGING_POST,
                "A device for charging DC appliances. Right-click to open the GUI. " +
                        "Place at least one battery in either of the two slots on the left, and the appliance to charge in the slot on the right.");
        translationBuilder.add(TextUtil.JUICE_EXTRACTOR,
                "A juice extractor that runs on AC power. Right-click while holding an ingredient to insert it; " +
                        "once both ingredients are loaded, right-click again to start the machine.");
        translationBuilder.add(TextUtil.POT_HAS_QUICKLIME, "Contains Quicklime");
        translationBuilder.add(TextUtil.POT_MISS_QUICKLIME, "Lack of Quicklime");
        translationBuilder.add(TextUtil.POT_HAS_WATER, "Contains Water");
        translationBuilder.add(TextUtil.POT_MISS_WATER, "Lack of Water");
        translationBuilder.add(TextUtil.NOODLE_UNHEALTHY, "Weird Instant Noodles");
        translationBuilder.add(TextUtil.ANYTHING, "Anything");
        translationBuilder.add(TextUtil.NEED_FOOD, "You need to hold any food in your other hand.");
        translationBuilder.add(TextUtil.SEASONING_TIP, "Seasoning item, hold this in one hand and any food in the other hand to season the food.");
        translationBuilder.add(TextUtil.FAILED_SEASONING, "You can't add more seasoning to this food.");
        translationBuilder.add(TextUtil.SEASONING_ADDED, "Seasonings:");
        translationBuilder.add(TextUtil.PUN, "Prohibit Unlimited Nesting!");
        translationBuilder.add(TextUtil.WOODEN_PLATE,
                "A plate for displaying items. Right-click to place an item on it; sneak and right-click to rotate it.");
        translationBuilder.add(TextUtil.INDUSTRIAL_FERMENTER,
                "Harness the power of microbes to transform simple ingredients into fine wine, vinegar, and more.");
        translationBuilder.add(TextUtil.FERMENTING_QUALITY,"Quality: ");
        translationBuilder.add(TextUtil.QUALITY_NEG_1,"Toxic");
        translationBuilder.add(TextUtil.QUALITY_0,"Poor");
        translationBuilder.add(TextUtil.QUALITY_1,"Normal");
        translationBuilder.add(TextUtil.QUALITY_2,"High");
        translationBuilder.add(TextUtil.FERMENTATION_SHIFT,"The SHIFT key is already pressed; right-clicking here will delete all current fluids");
        translationBuilder.add(TextUtil.FERMENTATION_TIME,"Assuming the fermenter operates at 100% efficiency, this fermentation will be complete in");
        translationBuilder.add(TextUtil.FERMENTATION_LIGHT,"Optimal light level for this fermentation is");
        translationBuilder.add(TextUtil.FERMENTATION_TEMP,"Optimal temperature for this fermentation is");
        translationBuilder.add(TextUtil.NO_BACTRIA,"None");
        translationBuilder.add(TextUtil.FERMENTATION_TANK,"A fluid storage tank with a capacity of 5 B can be used to transfer fermentation broth and fermentation microorganisms from industrial fermenters.");
        translationBuilder.add(TextUtil.TRAWLER,
                "A device used for harvesting aquatic products; it can only be placed on the water's surface." +
                        " Right-click to raise or lower the trawl net. When the trawl net is on the water's surface, " +
                        "press Shift and right-click to open the GUI. Bait is optional.");
        translationBuilder.add(TextUtil.TRAWLER_BAIT,"Place bait here (optional)");
        translationBuilder.add(TextUtil.GET_EXP,"Click to gain experiences");
        translationBuilder.add(MiniGame2Screen.TOOLTIP,"Elimination Tool");
        translationBuilder.add(TextUtil.STONE_MORTAR,"Hold the item to be ground in one hand and the stone mortar in the other," +
                " then hold down the interaction key to begin grinding.");

        translationBuilder.add(GlassBowlBlockEntity.WHISK_FAIL, "This thing doesn't seem to be able to be handled by the whisk.");
        translationBuilder.add(GlassBowlBlockEntity.NEED_PACKAGE, "The contents require special items to be removed, need");
        translationBuilder.add(PizzaWIPBlockEntity.NEED_INGREDIENT, "You need to put a proper ingredient here.");
        translationBuilder.add(PizzaWIPBlockEntity.NEED_CHEESE, "You need to put a cheese here.");
        translationBuilder.add(FreezerBlock.FAIL_TO_OPEN, "The freezer door is blocked by a block.");
        translationBuilder.add(JuiceExtractorBlockEntity.NO_POWER, "Not enough energy to start the juice extractor.");
        translationBuilder.add(JuiceExtractorBlockEntity.WRONG_RECIPE, "Juicing operation cannot be performed on inserted items.");
        translationBuilder.add(JuiceExtractorBlockEntity.IS_FULL, "Please remove the juice from the juice extractor first. Need ");

        translationBuilder.add(ModItems.IRON_WHISK, "Whisk");

        translationBuilder.add(ModItems.COPPER_KNIFE, "Copper Knife");
        translationBuilder.add(ModItems.AMETHYST_KNIFE, "Amethyst Knife");

        translationBuilder.add(ModItems.KNEADING_STICK, "Kneading Stick");
        translationBuilder.add(ModItems.CROWBAR, "Crowbar");
        translationBuilder.add(ModItems.SPATULA, "Spatula");
        translationBuilder.add(ModItems.FILTER, "Filter");

        translationBuilder.add(ModItems.EGG_TART, "Egg Tart");
        translationBuilder.add(ModItems.APPLE_PUDDING, "Caramel Apple Pudding");
        translationBuilder.add(ModItems.BRAISED_SHRIMP_BALL, "Golden Shrimp Balls");
        translationBuilder.add(ModItems.BRAISED_SHRIMP_BALL_WIP, "Golden Shrimp Balls (Work In Progress)");

        translationBuilder.add(ModItems.MATCHA_PUDDING, "Matcha Pudding");
        translationBuilder.add(ModItems.SUNFLOWER_SEED, "Sunflower Seed");
        translationBuilder.add(ModItems.SUNFLOWER_SEED_PEEL, "Sunflower Seed Peel");
        translationBuilder.add(ModItems.SUNFLOWER_SEED_PULP, "Sunflower Seed Pulp");
        translationBuilder.add(ModItems.ROASTED_SUNFLOWER_SEED, "Stir-fried Sunflower Seed");
        translationBuilder.add(ModItems.BIOMASS, "Biomass");
        translationBuilder.add(ModItems.VEGETABLE_OIL_BOTTLE, "Vegetable Oil Jar");
        translationBuilder.add(ModItems.VEGETABLE_OIL_BUCKET, "Vegetable Oil Bucket");

        translationBuilder.add(ModItems.TRUFFLE_EGG_TART, "Truffle Egg Tart");
        translationBuilder.add(ModItems.PUDDING_WIP_1, "Pudding (Work In Progress 1)");
        translationBuilder.add(ModItems.PUDDING_WIP_2, "Pudding (Work In Progress 2)");

        translationBuilder.add(ModItems.POTATO_STARCH, "Potato Starch");
        translationBuilder.add(ModItems.MIXED_DOUGH, "Mixed Dough");
        translationBuilder.add(ModItems.WHEAT_FLOUR, "Wheat Flour");
        translationBuilder.add(ModBlocks.WHEAT_DOUGH, "Wheat Dough");

        translationBuilder.add(ModItems.BLACK_PEPPER_CORN, "Black Pepper Corn");
        translationBuilder.add(ModItems.BLACK_PEPPER_DUST, "Black Pepper Powder");
        translationBuilder.add(ModItems.BLACK_PEPPER_SEASONING, "Black Pepper Shaker");

        translationBuilder.add(ModItems.MASHED_POTATO, "Mashed Potato");
        translationBuilder.add(ModItems.SAUCE_MASHED_POTATO, "Mashed Potatoes with Sauce");
        translationBuilder.add(ModBlocks.MASHED_POTATO_BLOCK, "Block of Mashed Potato");

        translationBuilder.add(ModItems.APPLE_PETAL, "Apple Petal");
        translationBuilder.add(ModItems.CHERRY, "Cherry");
        translationBuilder.add(ModItems.CHERRY_BOMB, "Cherry");
        translationBuilder.add(ModItems.BUTTER, "Butter");
        translationBuilder.add(ModItems.CHEESE, "Cheese");

        translationBuilder.add(ModItems.PINECONE, "Pinecone");
        translationBuilder.add(ModItems.ORANGE, "Orange");
        translationBuilder.add(ModItems.PEAR, "Pear");
        translationBuilder.add(ModItems.LEMON, "Lemon");
        translationBuilder.add(ModItems.MANGO, "Mango");

        translationBuilder.add(ModItems.BREAD_SLICE, "Bread Slice");
        translationBuilder.add(ModItems.BUTTER_BREAD_SLICE, "Butter Bread Slice");

        translationBuilder.add(ModItems.CREAM_BUCKET, "Bucket of Cream");
        translationBuilder.add(ModItems.CREAM, "Cream");
        translationBuilder.add(ModItems.APPLE_CREAM, "Apple Cream");
        translationBuilder.add(ModItems.CHERRY_CREAM, "Cherry Cream");
        translationBuilder.add(ModItems.CHOCOLATE_CREAM, "Chocolate Cream");
        translationBuilder.add(ModItems.PRAWN, "Prawn");
        translationBuilder.add(ModItems.GOLDEN_APPLE_CREAM, "Golden Apple Cream");
        translationBuilder.add(ModItems.MATCHA_CREAM, "Matcha Cream");
        translationBuilder.add(ModItems.PUMPKIN_CREAM, "Pumpkin Cream");
        translationBuilder.add(ModItems.WHIPPING_CREAM, "Whipping Cream");

        translationBuilder.add(ModItems.COOKED_PRAWN, "Cooked Prawn");
        translationBuilder.add(ModItems.BUTTER_COOKIE, "Butter Cookie");
        translationBuilder.add(ModItems.CHEESE_POTATO_BOAT, "Cheese Potato Boat");
        translationBuilder.add(ModItems.OPERA_CAKE, "Opera Cake");
        translationBuilder.add(ModItems.CRYSTAL_DUMPLING_WIP, "Crystal Shrimp Dumplings (Work In Progress)");
        translationBuilder.add(ModItems.DOUBLE_MEAT_BURGER, "Double Meat Burger");
        translationBuilder.add(ModItems.CRISPY_BARBECUE, "Crispy Barbecue");
        translationBuilder.add(ModItems.MIXED_JAM, "Mixed Jam");
        translationBuilder.add(ModItems.SEASONING_BOTTLE, "Seasoning Bottle");
        translationBuilder.add(ModItems.CRAB, "Crab");
        translationBuilder.add(ModItems.CRAB_ROE, "Crab Roe");
        translationBuilder.add(ModItems.COOKED_CRAB, "Cooked Crab");
        translationBuilder.add(ModItems.GOLDEN_DOUBLE_CRISP_WIP, "Golden Double Crisp (Work In Progress)");
        translationBuilder.add(ModItems.GOLDEN_DOUBLE_CRISP, "Golden Double Crisp");
        translationBuilder.add(ModItems.SAKURA_YOGURT_SAUCE, "Sakura Yogurt Sauce");
        translationBuilder.add(ModItems.SCOTCH_EGG_WIP, "Scotch Egg (Work In Progress)");
        translationBuilder.add(ModItems.SCOTCH_EGG, "Scotch Egg");
        translationBuilder.add(ModItems.CHOW_MEIN, "Chow Mein");
        translationBuilder.add(ModItems.STUFFED_MASHED_POTATO, "Stuffed Massed Potato");
        translationBuilder.add(ModItems.STEAMED_EGG_CUSTARD, "Steamed Egg Custard");
        translationBuilder.add(ModItems.CHILI_SEEDS, "Chili Seeds");
        translationBuilder.add(ModItems.CHILI_PEPPER, "Chili Pepper");
        translationBuilder.add(ModItems.SOYBEAN, "Soybean");
        translationBuilder.add(ModItems.CHILI_POWDER_SEASONING, "Chili Powder Shaker");
        translationBuilder.add(ModItems.SOY_MILK_BUCKET, "Soy Milk Bucket");
        translationBuilder.add(ModItems.CHILI_SAUCE, "Chili Sauce");

        translationBuilder.add(ModItems.MOUSSE_WIP, "Mousse (Work In Progress)");
        translationBuilder.add(ModItems.CREAM_MOUSSE, "Cream Mousse");
        translationBuilder.add(ModItems.CHERRY_MOUSSE, "Cherry Mouse");
        translationBuilder.add(ModItems.CHOCOLATE_MOUSSE, "Chocolate Mouse");
        translationBuilder.add(ModItems.PUMPKIN_MOUSSE, "Pumpkin Mouse");
        translationBuilder.add(ModItems.GOLDEN_APPLE_MOUSSE, "Golden Apple Mousse");
        translationBuilder.add(ModItems.MATCHA_MOUSSE, "Matcha Mousse");

        translationBuilder.add(ModItems.CRYSTAL_DUMPLING, "Crystal Shrimp Dumpling");

        translationBuilder.add(ModItems.SQUID, "Squid");
        translationBuilder.add(ModItems.ROASTED_SQUID, "Roasted Squid");
        translationBuilder.add(ModItems.CUTTLEBONE, "Cuttlebone");
        translationBuilder.add(ModItems.GLOW_SQUID, "Glow Squid");
        translationBuilder.add(ModItems.ROASTED_GLOW_SQUID, "Roasted Glow Squid");
        translationBuilder.add(ModItems.GLOW_CUTTLEBONE, "Glow Cuttlebone");

        translationBuilder.add(ModItems.SAUSAGE, "Sausage");
        translationBuilder.add(ModItems.SECTIONED_SAUSAGE, "Sectioned Sausage");

        translationBuilder.add(ModItems.BLACK_TRUFFLE, "Black Truffle");
        translationBuilder.add(ModItems.WHITE_TRUFFLE, "White Truffle");
        translationBuilder.add(ModItems.BLACK_TRUFFLE_SEASONING, "Black Truffle Shaker");
        translationBuilder.add(ModItems.WHITE_TRUFFLE_SEASONING, "White Truffle Shaker");

        translationBuilder.add(ModItems.SILICON_INGOT, "Silicon Ingot");
        translationBuilder.add(ModBlocks.SILICON_BLOCK, "Block of Silicon");
        translationBuilder.add(ModItems.SILICON_COMPONENT, "Silicon Component");
        translationBuilder.add(ModItems.REDSTONE_COMPONENT, "Redstone Component");
        translationBuilder.add(ModItems.DIAMOND_COMPONENT, "Diamond Component");

        translationBuilder.add(ModItems.EMPTY_CAKE, "Piecrust");
        translationBuilder.add(ModItems.BLUE_ORCHID_FLOWER_CAKE, "Blue Orchid Cake");
        translationBuilder.add(ModItems.CHERRY_CAKE, "Cherry Cake");
        translationBuilder.add(ModItems.LILAC_CAKE, "Lilac Cake");
        translationBuilder.add(ModItems.ORANGE_TULIP_CAKE, "Orange Tulip Cake");
        translationBuilder.add(ModItems.OXEYE_DAISY_CAKE, "Oxeye Daisy Cake");
        translationBuilder.add(ModItems.PINK_TULIP_CAKE, "Pink Tulip Cake");
        translationBuilder.add(ModItems.ROSE_CAKE, "Rose Cake");
        translationBuilder.add(ModItems.SUNFLOWER_CAKE, "Sunflower Cake");
        translationBuilder.add(ModItems.WHITE_TULIP_CAKE, "White Tulip Cake");
        translationBuilder.add(ModItems.WITHER_ROSE_CAKE, "Wither Rose Cake");

        translationBuilder.add(ModItems.FRIED_COD, "Deep-fried Cod");
        translationBuilder.add(ModItems.FRIED_SALMON, "Deep-fried Salmon");
        translationBuilder.add(ModItems.FRIED_MILK_WIP, "Deep-fried Milk (Work In Progress)");
        translationBuilder.add(ModItems.FRIED_MILK, "Deep-fried Milk");
        translationBuilder.add(ModItems.FRIED_APPLE, "Deep-fried Apple");
        translationBuilder.add(ModItems.RAW_POTATO_CHIP, "Sliced Raw Potato");
        translationBuilder.add(ModItems.POTATO_CHIP, "Deep-fried Potato Chip");
        translationBuilder.add(ModItems.FRIED_DOUGH_STICK, "Deep-fried Dough Stick");
        translationBuilder.add(ModItems.RAW_CHICKEN_FILLET, "Raw Chicken Fillet");
        translationBuilder.add(ModItems.CHICKEN_FILLET, "Deep-fried Chicken Fillet");

        translationBuilder.add(ModEntities.BUTTER,"Butter");
        translationBuilder.add(ModEntities.CHERRY_BOMB,"Cherry");

        translationBuilder.add(ModBlocks.PIZZA, "Pizza");
        translationBuilder.add(ModBlocks.RAW_PIZZA, "Raw Pizza");
        translationBuilder.add(ModBlocks.PIZZA_WIP, "Pizza (Work In Progress)");

        translationBuilder.add(ModItems.STEAMED_BUN, "Steamed Bun");
        translationBuilder.add(ModItems.DEEP_FRIED_BUN, "Golden Steamed Buns");
        translationBuilder.add(ModItems.DEEP_FRIED_BUN_WIP, "Golden Steamed Buns (Work In Progress)");

        translationBuilder.add(ModItems.CARAMEL_TOAST, "Caramel Toast");

        translationBuilder.add(ModBlocks.GLASS_BOWL, "Glass Bowl");
        translationBuilder.add(GlassBowlWhiskingCategory.GLASS_BOWL_NAME, "Whisk");
        translationBuilder.add(GlassBowlMixWithWaterCategory.WATER_GLASS_BOWL_NAME,"Mix with Water");
        translationBuilder.add(ModBlocks.OVEN, "Oven");
        translationBuilder.add(ModBlocks.ADVANCE_FURNACE, "Advanced Furnace");
        translationBuilder.add(ModBlocks.BAKING_TRAY, "Baking Tray");
        translationBuilder.add(ModBlocks.DEEP_FRYER, "Deep Fryer");
        translationBuilder.add(BakingTrayCategory.BAKING_TRAY_NAME, "Stir-fried Dish");
        translationBuilder.add(ModBlocks.WOODEN_BASIN, "Wooden Basin");
        translationBuilder.add(WoodenBasinBlockEntity.WOODEN_BASIN_NAME, "Oil Extraction");
        translationBuilder.add(ModBlocks.GAS_CANISTER, "Gas Canister");
        translationBuilder.add(GasCanisterBlockEntity.GAS_CANISTER_NAME, "Capacity");
        translationBuilder.add(ModBlocks.BIOGAS_DIGESTER_CONTROLLER, "Biogas Digester Controller");
        translationBuilder.add(BiogasDigesterControllerScreen.UNAVAILABLE, "The space under the controller is incomplete, not enclosed, or too large");
        translationBuilder.add(BiogasDigesterControllerScreen.SIZE, "Size of the space under the controller");
        translationBuilder.add(BiogasDigesterControllerScreen.GAS_VALUE, "Current gas storage capacity of the digester");
        translationBuilder.add(BiogasDigesterControllerScreen.MAX_GAS_VALUE, "Maximum gas storage capacity of the digester");
        translationBuilder.add(ModBlocks.BIOGAS_DIGESTER_IO, "Biogas Digester I/O port");
        translationBuilder.add(BiogasDigesterIOScreen.UNAVAILABLE, "The block below the port is not a controller, or the controller is not activated");
        translationBuilder.add(BiogasFermentationCategory.BIOGAS_FERMENTATION_NAME, "Biogas Fermentation");
        translationBuilder.add(BiogasFermentationCategory.FOOD, "Any Food");
        translationBuilder.add(ModBlocks.BURNING_GAS_COOKING_STOVE, "Burning Gas Cooking Stove");
        translationBuilder.add(ModBlocks.GAS_COOKING_STOVE, "Gas Cooking Stove");
        translationBuilder.add(ModBlocks.DEEP_FRY_BASKET, "Deep Fry Basket");
        translationBuilder.add(DeepFryerBlockEntity.ADD_OIL, "You need to add cooking oil to it.");
        translationBuilder.add(DeepFryerBlockEntity.TOO_HOT, "It's too hot! Please hold the tongs to remove the items");
        translationBuilder.add(DeepFryerBlockEntity.DEEP_FRYER_NAME, "Deep Frying");
        translationBuilder.add(ModBlocks.KITCHEN_UTENSIL_HOLDER, "Kitchen Utensil Holder");
        translationBuilder.add(ModBlocks.CUISINE_TABLE, "Cuisine Table");
        translationBuilder.add(CuisineTableBlockEntity.CUISINE_TABLE_NAME, "Culinary");
        translationBuilder.add(ModBlocks.ANDESITE_CABINET, "Andesite Cabinet");
        translationBuilder.add(ModBlocks.DIORITE_CABINET, "Diorite Cabinet");
        translationBuilder.add(ModBlocks.GRANITE_CABINET, "Granite Cabinet");
        translationBuilder.add(ModBlocks.BLACKSTONE_CABINET, "Blackstone Cabinet");
        translationBuilder.add(ModBlocks.BASALT_CABINET, "Basalt Cabinet");
        translationBuilder.add(ModBlocks.DEEPSLATE_CABINET, "Deepslate Cabinet");
        translationBuilder.add(ModBlocks.OBSIDIAN_CABINET, "Obsidian Cabinet");
        translationBuilder.add(CabinetBlockEntity.CABINET_NAME, "Cabinet");
        translationBuilder.add(ModItems.ANCIENT_SCRAP, "Ancient Scrap");

        translationBuilder.add(ModItems.EGG_BOWL, "Egg in Bowl");
        translationBuilder.add(ModItems.CHERRY_EGG, "Egg with Cherry");
        translationBuilder.add(ModItems.FISH_EGG, "Egg with Seafood");
        translationBuilder.add(ModItems.STEAMED_EGG, "Steamed Egg");
        translationBuilder.add(ModItems.STEAMED_CHERRY_EGG, "Steamed Egg with Cherry");
        translationBuilder.add(ModItems.STEAMED_FISH_EGG, "Steamed Egg with Seafood");
        translationBuilder.add(ModItems.BEEF_TOMATO_CUP, "Beef and Tomato Cup");
        translationBuilder.add(ModItems.BUTTERFLY_CRISP, "Butterfly Crisp");
        translationBuilder.add(ModItems.JAR, "Jar");
        translationBuilder.add(ModItems.CARAMEL, "Caramel");
        translationBuilder.add(ModItems.CARAMEL_PUDDING, "Caramel Pudding");
        translationBuilder.add(TextUtil.CAN_PLACE, "Placeable");
        translationBuilder.add(ModItems.CHEESE_BAKED_POTATO, "Cheese Baked Potato");
        translationBuilder.add(ModItems.CHEESE_RICE_BALL, "Cheese Rice Ball");
        translationBuilder.add(ModItems.CHOCOLATE_SAUCE, "Chocolate Sauce");
        translationBuilder.add(ModItems.DEEP_FRIED_SHRIMP_CAKE, "Deep-fried Shrimp Cake");
        translationBuilder.add(ModItems.SEAWEED_FRIED_SHRIMP_CAKE, "Seaweed Fried Shrimp Cake");
        translationBuilder.add(ModItems.DONUT, "Donut");
        translationBuilder.add(ModItems.CHERRY_PUDDING, "Cherry Pudding");
        translationBuilder.add(ModItems.ICE_LOLLY, "Ice Lolly");
        translationBuilder.add(ModItems.PACKAGING_BAG, "Packaging Bag");
        translationBuilder.add(ModItems.DIRTY_PACKAGING_BAG, "Dirty Packaging Bag");
        translationBuilder.add(ModItems.POTATO_CHIPS, "Potato Chips");
        translationBuilder.add(ModItems.DEEP_FRIED_POTATO_CHIPS, "Deep-fried Potato Chips");
        translationBuilder.add(ModItems.FRENCH_FRIES, "French Fries");
        translationBuilder.add(ModItems.FRIED_CHICKEN, "Deep-fried Chicken");
        translationBuilder.add(ModItems.FRIED_RED_MUSHROOM, "Deep-fried Red Mushroom");
        translationBuilder.add(ModItems.FRIED_BROWN_MUSHROOM, "Deep-fried Brown Mushroom");
        translationBuilder.add(ModBlocks.CREAM_OF_MUSHROOM_SOUP, "Cream of Mushroom Soup");
        translationBuilder.add(ModBlocks.FRENCH_ONION_SOUP, "French Onion Soup");
        translationBuilder.add(ModItems.CHERRY_ICE_LOLLY, "Cherry Ice Lolly");
        translationBuilder.add(ModItems.MATCHA_ICE_LOLLY, "Matcha Ice Lolly");
        translationBuilder.add(ModItems.WITHER_ICE_LOLLY, "Wither Ice Lolly");
        translationBuilder.add(ModItems.GLOW_SQUID_TENTACLE, "Glow Squid Tentacle");
        translationBuilder.add(ModItems.GLOW_SQUID_TENTACLE_KEBABS, "Glow Squid Tentacle Kebab");
        translationBuilder.add(ModItems.RAW_GLOW_SQUID_TENTACLE_KEBABS, "Raw Glow Squid Tentacle Kebab");
        translationBuilder.add(ModItems.SQUID_TENTACLE, "Squid Tentacle");
        translationBuilder.add(ModItems.RAW_SQUID_TENTACLE_KEBABS, "Raw Squid Tentacle Kebab");
        translationBuilder.add(ModItems.SQUID_TENTACLE_KEBABS, "Squid Tentacle Kebab");
        translationBuilder.add(ModItems.TURNIP, "Rutabaga");
        translationBuilder.add(TextUtil.TURNIP, "A slight grudge from the author.");
        translationBuilder.add(ModItems.RAW_DONUT, "Raw Donut");
        translationBuilder.add(ModItems.CHOCOLATE_DONUT, "Chocolate Donut");
        translationBuilder.add(ModItems.CHEESE_BURGER, "Cheese Burger");
        translationBuilder.add(ModItems.ROAST_STREAKY_PORK, "Roast Streaky Pork");
        translationBuilder.add(ModItems.PORK_CHOP_BURGER, "Pork Chop Burger");
        translationBuilder.add(ModItems.FRIED_COD_NUGGET, "Deep-fried Cod Nugget");
        translationBuilder.add(ModBlocks.FISH_AND_CHIPS_ITEM, "Fish and Chips");
        translationBuilder.add(ModBlocks.WILD_PEPPER_CROP, "Wild Black Pepper");
        translationBuilder.add(ModItems.GARLIC, "Garlic");
        translationBuilder.add(ModItems.GARLIC_PETAL, "Garlic Petal");
        translationBuilder.add(ModBlocks.WILD_GARLIC, "Wild Garlic");
        translationBuilder.add(ModItems.ELECTRIC_WHISK, "Electric Whisk");
        translationBuilder.add(ModBlocks.CHARGING_POST, "Charging Post");
        translationBuilder.add(ModBlocks.WILD_CHILI_CROP, "Wild Chili Pepper");
        translationBuilder.add(ModBlocks.WILD_SOYBEAN, "Wild Soybean");

        translationBuilder.add(ModItems.ICE_CREAM_CONE, "Ice Cream Cone");
        translationBuilder.add(ModItems.RAW_ICE_CREAM_CONE, "Ice Cream Cone (Work In Progress)");
        translationBuilder.add(ModItems.ICE_CREAM, "Ice Cream");

        translationBuilder.add(CreamFlavor.TRANSLATION_KEY, "Flavors");
        translationBuilder.add(CreamFlavor.NULL.getTranslationKey(), "Unknown");
        translationBuilder.add(CreamFlavor.PLAIN.getTranslationKey(), "Plain");
        translationBuilder.add(CreamFlavor.APPLE.getTranslationKey(), "Apple Flavored");
        translationBuilder.add(CreamFlavor.CHERRY.getTranslationKey(), "Cherry Flavored");
        translationBuilder.add(CreamFlavor.CHOCOLATE.getTranslationKey(), "Chocolate Flavored");
        translationBuilder.add(CreamFlavor.GOLDEN_APPLE.getTranslationKey(), "Golden Apple Flavored");
        translationBuilder.add(CreamFlavor.MATCHA.getTranslationKey(), "Matcha Flavored");
        translationBuilder.add(CreamFlavor.PUMPKIN.getTranslationKey(), "Pumpkin Flavored");

        translationBuilder.add(OvenBlockEntity.OVEN_NAME, "Baking");
        translationBuilder.add(AdvanceFurnaceBlockEntity.ADVANCE_FURNACE_NAME, "Advanced Furnace");
        translationBuilder.add(ModBlocks.FREEZER, "Freezer");
        translationBuilder.add(FreezerBlockEntity.FREEZER_NAME, "Refrigerate");
        translationBuilder.add(PizzaMakingCategory.PIZZA_TITLE, "Pizza Making");
        translationBuilder.add(OvenTransformCategory.TRANSFORM_TITLE, "Transform");
        translationBuilder.add(PhotovoltaicGeneratorBlockEntity.PHOTOVOLTAIC_GENERATOR_NAME, "Photovoltaic Power Generation");
        translationBuilder.add(ModBlocks.PHOTOVOLTAIC_GENERATOR, "Photovoltaic Generator");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.GREEN_TIP_1, "High efficiency mode:");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.GREEN_TIP_2, "Daytime only and");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.GREEN_TIP_3, "open air only");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.YELLOW_TIP_1, "Inefficiency mode:");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.YELLOW_TIP_2, "Activated when there is");
        translationBuilder.add(PhotovoltaicGeneratorScreen.TipScreen.YELLOW_TIP_3, "sufficient light nearby");
        translationBuilder.add(ModBlocks.AC_DC_CONVERTER, "AC/DC Converter");
        translationBuilder.add(ModBlocks.FAN_BLADE_ITEM, "Wind Turbine Blades");
        translationBuilder.add(ModBlocks.WIND_TURBINE_CONTROLLER, "Wind Turbine Controller");
        translationBuilder.add(ModBlocks.SIMPLE_BATTERY, "Simple Battery");
        translationBuilder.add(ModBlocks.INTERMEDIATE_BATTERY, "Intermediate Battery");
        translationBuilder.add(ModBlocks.ADVANCE_BATTERY, "Advanced Battery");
        translationBuilder.add(ModBlocks.DIMENSION_BATTERY, "Dimensional Battery");
        translationBuilder.add(BatteryBlockItem.TOOLTIP_TEXT, "Quantity of electric charge or power: ");
        translationBuilder.add(ModBlocks.STERLING_ENGINE_ITEM, "Stirling Engine");
        translationBuilder.add(ModBlocks.FARADAY_GENERATOR, "Faraday Generator");
        translationBuilder.add(ACDCConverterScreen.SPEED_TIP, "Working speed (Hold Shift for quick adjustments): ");
        translationBuilder.add(ACDCConverterScreen.PUT_BATTERY_TIP, "A battery needs to be placed here");
        translationBuilder.add(ACDCConverterScreen.AC2DC, "AC to DC Mode");
        translationBuilder.add(ACDCConverterScreen.DC2AC, "DC to AC Mode");
        translationBuilder.add(ModBlocks.TESLA_COIL, "Tesla Coil");
        translationBuilder.add(ModBlocks.ELECTRICIANS_DESK, "Electrician's Desk");
        translationBuilder.add(ModBlocks.BOXED_CHERRIES, "Cherries Crate");
        translationBuilder.add(ModBlocks.BAMBOO_COVER, "Bamboo Steamer Cover");
        translationBuilder.add(ModBlocks.BAMBOO_GRATE, "Bamboo Steamer Basket Drawer");
        translationBuilder.add(BambooGrateBlockEntity.NAME, "Steamer");
        translationBuilder.add(ModBlocks.ELECTRIC_STEAMER, "Electric Steamer");
        translationBuilder.add(ModBlocks.ICE_CREAM_MAKER, "Ice Cream Maker");
        translationBuilder.add(IceCreamMakerBlockEntity.NEED_CONE, "Please use an ice cream cone to pick up the ice cream.");
        translationBuilder.add(IceCreamMakerBlockEntity.NEED_SELECT, "Please select a flavor in the GUI screen or insufficient ice cream left.");
        translationBuilder.add(CuisineTableBlock.CANT_OPEN, "The Cuisine Table is occupied by other player and cannot be opened.");
        translationBuilder.add(ModBlocks.JUICE_EXTRACTOR, "Juice Extractor");
        translationBuilder.add(ModItems.SWEET_BERRIES_JUICE, "Sweet Berry Juice");
        translationBuilder.add(ModItems.TOMATO_JUICE, "Tomato Juice");
        translationBuilder.add(ModBlocks.GLASS_CUP, "Glass Cup");
        translationBuilder.add(ModBlocks.CHERRY_MILK_TEA, "Sakura Milk Tea");
        translationBuilder.add(ModBlocks.ROSE_ICE_TEA, "Rose Iced Tea");
        translationBuilder.add(ModItems.LIQUEFIED_BIOGAS_BUCKET, "Liquefied Biogas Bucket");
        translationBuilder.add(ModBlocks.WOODEN_PLATE, "Wooden Plate");
        translationBuilder.add(ModItems.FRIED_NOODLES, "Fried Noodles");
        translationBuilder.add(ModItems.MULTIFUNCTIONAL_WRAPPING_PAPER, "Multifunctional Wrapping Paper");
        translationBuilder.add(ModItems.QUICKLIME, "Quicklime");
        translationBuilder.add(ModItems.PACKAGED_INSTANT_NOODLES, "Packaged Instant Noodles");
        translationBuilder.add(SpecialIngredient.BRAISED_BEEF_NOODLE_SOUP.toTranslationKey(), "Braised Beef Noodle Soup");
        translationBuilder.add(SpecialIngredient.STEW_CHICKEN_NOODLE_WITH_MUSHROOM.toTranslationKey(), "Stew Chicken Noodle With Mushroom");
        translationBuilder.add(SpecialIngredient.TONKOTSU_RAMEN.toTranslationKey(), "Tonkotsu Ramen");
        translationBuilder.add(ModItems.PORTABLE_POT, "Portable Pot");
        translationBuilder.add(ModItems.COOKED_PORTABLE_POT, "Cooked Instant Noodles");
        translationBuilder.add(ModItems.DIRTY_WRAPPING_PAPER, "Dirty Wrapping Paper");
        translationBuilder.add(ModItems.HONEY_CRYSTALLIZATION, "Honey Crystal");
        translationBuilder.add(ModItems.SEA_SALT, "Sea Salt");
        translationBuilder.add(ModItems.GARLIC_PUREE, "Garlic Powder");
        translationBuilder.add(ModItems.STONE_MORTAR, "Stone Mortar");
        translationBuilder.add(ModItems.MATCHA, "Matcha");
        translationBuilder.add(ModItems.GRINDED_LEAVES, "Ground Leaves");
        translationBuilder.add(ModItems.MANGO_MILK_TEA, "Mango Milk Tea");
        translationBuilder.add(ModItems.SEA_SALT_LEMON, "Sea Salt Lemon");
        translationBuilder.add(ModItems.STEAMED_PUMPKIN_IN_BOWL, "Steamed Pumpkin In Bowl");
        translationBuilder.add(ModBlocks.STEAMED_PUMPKIN, "Steamed Pumpkin");
        translationBuilder.add(ModItems.STEAMED_PUMPKIN_WIP, "Steamed Pumpkin (Work In Progress)");
        translationBuilder.add(ModItems.CHOCOLATE_CRUNCH_ICE_LOLLY, "Chocolate Crunch Ice Lolly");
        translationBuilder.add(ModItems.STEAMED_STUFFED_BUN_WIP, "Steamed Stuffed Buns (Work In Progress)");
        translationBuilder.add(ModItems.STEAMED_STUFFED_BUN, "Steamed Stuffed Buns");
        translationBuilder.add(ModItems.VEGETABLE_STEAMED_STUFFED_BUN_WIP, "Jade Cabbage Buns (Work In Progress)");
        translationBuilder.add(ModItems.VEGETABLE_STEAMED_STUFFED_BUN, "Jade Cabbage Buns");
        translationBuilder.add(ModItems.SHAOMAI_WIP, "Shumai (Work In Progress)");
        translationBuilder.add(ModItems.SHAOMAI, "Shumai");
        translationBuilder.add(ModItems.BLACK_PEPPER_STEAK, "Black Pepper Steak");
        translationBuilder.add(ModItems.HOLDER, "Tongs");
        translationBuilder.add(ModItems.HOLDER_UP, "Half Tongs");
        translationBuilder.add(ModItems.RAW_NOODLES, "Raw Noodles");
        translationBuilder.add(ModItems.SWEETENED_WATER_BUCKET, "Sweetened Water Bucket");
        translationBuilder.add(ModItems.ALCOHOL_BUCKET, "Alcohol Bucket");
        translationBuilder.add(ModItems.VINEGAR_BUCKET, "Vinegar Bucket");
        translationBuilder.add(ModBlocks.CLAY_POT, "Clay Pot");
        translationBuilder.add(ModBlocks.BRITISH_BREAKFAST, "British Breakfast");
        translationBuilder.add(ModBlocks.MARSHALA_CHEESE_BALL, "Marshala Cheese Ball");
        translationBuilder.add(ModBlocks.CHICKEN_RICE, "Chicken Rice");
        translationBuilder.add(ModBlocks.CHILI_FISH, "Chili Fish");
        translationBuilder.add(ModBlocks.CRAB_SAUSAGE_VEGETABLE, "Crab Sausage Vegetable");
        translationBuilder.add(ModBlocks.PORK_NOODLE, "Pork Noodle");
        translationBuilder.add(ModBlocks.PEPPER_BEEF_RICE, "Pepper Beef Rice");
        translationBuilder.add(ModBlocks.VEGETABLE_NOODLE, "Vegetable Noodle");
        translationBuilder.add(ModItems.FERMENTATION_TANK, "Fermentation Tank");
        translationBuilder.add(ModBlocks.TRAWLER, "Trawler");


        translationBuilder.add(ModBlocks.INDUSTRIAL_FERMENTER, "Industrial Fermenter");
        translationBuilder.add(BacteriaType.ACETOBACTER.getTranslationKey(), "Acetobacter");
        translationBuilder.add(BacteriaType.ESCHERICHIA.getTranslationKey(), "Escherichia");
        translationBuilder.add(BacteriaType.YEAST.getTranslationKey(), "Yeast");
        translationBuilder.add(BacteriaType.MUCOR.getTranslationKey(), "Mucor");
        translationBuilder.add(BacteriaType.LACTOBACILLUS.getTranslationKey(), "Lactobacillus");


        translationBuilder.add("death.attack.moderndelight_electroshock","%1$s was electrocuted!");
        translationBuilder.add("death.attack.moderndelight_turned_to_ashes","%1$s turned to ashes");
        translationBuilder.add("death.attack.moderndelight_turned_to_ashes.player","%1$s turned to ashes");
        translationBuilder.add("death.attack.moderndelight_scalded","%1$s scalded to death");
        translationBuilder.add("death.attack.moderndelight_mouth_afire","%1$s died from the fire in their mouth");

        translationBuilder.add(ModBlocks.CREAM_FLUID_BLOCK, "Cream");
        translationBuilder.add(ModBlocks.VEGETABLE_OIL_FLUID_BLOCK, "Vegetable Oil");
        translationBuilder.add(ModBlocks.LIQUEFIED_BIOGAS_FLUID_BLOCK, "Liquefied Biogas");
        translationBuilder.add(ModBlocks.SWEENTENED_WATER_FLUID_BLOCK, "Sweetened Water");
        translationBuilder.add(ModBlocks.ALCOHOL_FLUID_BLOCK, "Alcohol");
        translationBuilder.add(ModBlocks.VINEGAR_FLUID_BLOCK, "Vinegar");
        translationBuilder.add(ModBlocks.MILK_FLUID_BLOCK, "Milk (Fermenting)");
        translationBuilder.add(ModBlocks.SOY_MILK_FLUID_BLOCK, "Soy Milk");

        translationBuilder.add(ModEnchantments.FINE_GRINDING,"Precision Grinding");
        translationBuilder.add(ModEnchantments.FINE_GRINDING.getTranslationKey()+".desc","The stone mortar will have additional output when grinding.");

        translationBuilder.add(ModEffectsAndPotions.STICKY,"Sticky");
        translationBuilder.add(ModEffectsAndPotions.STICKY.getTranslationKey() + ".description","Drastically reduces movement speed while being unable to jump.");
        translationBuilder.add(ModEffectsAndPotions.UMAMI,"Savory depth");
        translationBuilder.add(ModEffectsAndPotions.UMAMI.getTranslationKey() + ".description","Your next meal restores extra hunger. The duration of newly acquired buffs will be extended.");
        translationBuilder.add(ModEffectsAndPotions.MOUTH_AFIRE,"Mouth Afire");
        translationBuilder.add(ModEffectsAndPotions.MOUTH_AFIRE.getTranslationKey() + ".description","Your mouth is on fire. Cream, ice cream, or milk helps.");
        translationBuilder.add(ModEffectsAndPotions.CREAM_COAT,"Cream Coat");
        translationBuilder.add(ModEffectsAndPotions.CREAM_COAT.getTranslationKey() + ".description","Armor has been improved, significantly reducing fall damage.");
        translationBuilder.add(ModEffectsAndPotions.FROST_COAT,"Frost Coat");
        translationBuilder.add(ModEffectsAndPotions.FROST_COAT.getTranslationKey() + ".description","Melee hits slow the target.");
        translationBuilder.add(ModEffectsAndPotions.WARM_BELLY,"Warm Belly");
        translationBuilder.add(ModEffectsAndPotions.WARM_BELLY.getTranslationKey() + ".description","Immune to powder snow freezing.");
        translationBuilder.add(ModEffectsAndPotions.VITAMIN_C,"Vitamin C");
        translationBuilder.add(ModEffectsAndPotions.VITAMIN_C.getTranslationKey() + ".description","Cures and prevents poison and wither.");
        translationBuilder.add(ModEffectsAndPotions.CRUNCH,"Crunch");
        translationBuilder.add(ModEffectsAndPotions.CRUNCH.getTranslationKey() + ".description","Increases melee attack damage, but each attack consumes more hunger.");
        translationBuilder.add(ModEffectsAndPotions.WELL_FED,"Well Fed");
        translationBuilder.add(ModEffectsAndPotions.WELL_FED.getTranslationKey() + ".description","Increases maximum health and slowly restores health over time.");
        translationBuilder.add(ModEffectsAndPotions.FOOD_COMA,"Food Coma");
        translationBuilder.add(ModEffectsAndPotions.FOOD_COMA.getTranslationKey() + ".description","Too full to sprint. Taking damage snaps you out of it.");
        translationBuilder.add(ModEffectsAndPotions.GUT_FLORA,"Gut Flora");
        translationBuilder.add(ModEffectsAndPotions.GUT_FLORA.getTranslationKey() + ".description","Slower hunger drain and no food coma from hearty meals.");
        translationBuilder.add(ModEffectsAndPotions.BRAIN_FREEZE,"Brain Freeze");
        translationBuilder.add(ModEffectsAndPotions.BRAIN_FREEZE.getTranslationKey() + ".description","Slowed for a moment.");

        translationBuilder.add("item.minecraft.potion.effect.sticky_potion","Potion of Sticky");
        translationBuilder.add("item.minecraft.potion.effect.sticky_long_potion","Potion of Sticky");
        translationBuilder.add("item.minecraft.potion.effect.squid_power_potion","Potion of the Squid Power");
        translationBuilder.add("item.minecraft.potion.effect.squid_power_long_potion","Potion of the Squid Power");
        translationBuilder.add("item.minecraft.potion.effect.squid_power_strong_potion","Potion of the Squid Power");
        translationBuilder.add("item.minecraft.potion.effect.glow_squid_power_potion","Potion of the Glow Squid Power");
        translationBuilder.add("item.minecraft.potion.effect.glow_squid_power_long_potion","Potion of the Glow Squid Power");
        translationBuilder.add("item.minecraft.potion.effect.glow_squid_power_strong_potion","Potion of the Glow Squid Power");
        translationBuilder.add("item.minecraft.splash_potion.effect.sticky_potion","Splash Potion of Sticky");
        translationBuilder.add("item.minecraft.splash_potion.effect.sticky_long_potion","Splash Potion of Sticky");
        translationBuilder.add("item.minecraft.splash_potion.effect.squid_power_potion","Splash Potion of the Squid Power");
        translationBuilder.add("item.minecraft.splash_potion.effect.squid_power_long_potion","Splash Potion of the Squid Power");
        translationBuilder.add("item.minecraft.splash_potion.effect.squid_power_strong_potion","Splash Potion of the Squid Power");
        translationBuilder.add("item.minecraft.splash_potion.effect.glow_squid_power_potion","Splash Potion of the Glow Squid Power");
        translationBuilder.add("item.minecraft.splash_potion.effect.glow_squid_power_long_potion","Splash Potion of the Glow Squid Power");
        translationBuilder.add("item.minecraft.splash_potion.effect.glow_squid_power_strong_potion","Splash Potion of the Glow Squid Power");
        translationBuilder.add("item.minecraft.lingering_potion.effect.sticky_potion","Lingering Potion of Sticky");
        translationBuilder.add("item.minecraft.lingering_potion.effect.sticky_long_potion","Lingering Potion of Sticky");
        translationBuilder.add("item.minecraft.lingering_potion.effect.squid_power_potion","Lingering Potion of the Squid Power");
        translationBuilder.add("item.minecraft.lingering_potion.effect.squid_power_long_potion","Lingering Potion of the Squid Power");
        translationBuilder.add("item.minecraft.lingering_potion.effect.squid_power_strong_potion","Lingering Potion of the Squid Power");
        translationBuilder.add("item.minecraft.lingering_potion.effect.glow_squid_power_potion","Lingering Potion of the Glow Squid Power");
        translationBuilder.add("item.minecraft.lingering_potion.effect.glow_squid_power_long_potion","Lingering Potion of the Glow Squid Power");
        translationBuilder.add("item.minecraft.lingering_potion.effect.glow_squid_power_strong_potion","Lingering Potion of the Glow Squid Power");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.sticky_potion","Arrow of Sticky");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.sticky_long_potion","Arrow of Sticky");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.squid_power_potion","Arrow of the Squid Power");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.squid_power_long_potion","Arrow of the Squid Power");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.squid_power_strong_potion","Arrow of the Squid Power");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.glow_squid_power_potion","Arrow of the Glow Squid Power");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.glow_squid_power_long_potion","Arrow of the Glow Squid Power");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.glow_squid_power_strong_potion","Arrow of the Glow Squid Power");

        translationBuilder.add(ModItemGroups.GROUPS_TAB_NAME, "Modern Delight");

        translationBuilder.add("emi.category.moderndelight.mix_with_water","Mix with Water");
        translationBuilder.add("emi.category.moderndelight.whisking","Whisking");
        translationBuilder.add("emi.category.moderndelight.assembly","Electrician's Desk");
        translationBuilder.add("emi.category.moderndelight.stir_frying","Stir Frying");
        translationBuilder.add("emi.category.moderndelight.biogas_fermentation","Biogas Fermentation");
        translationBuilder.add("emi.category.moderndelight.cuisine","Cuisine");
        translationBuilder.add("emi.category.moderndelight.deep_frying","Deep Frying");
        translationBuilder.add("emi.category.moderndelight.freezing","Freezing");
        translationBuilder.add("emi.category.moderndelight.baking","Baking");
        translationBuilder.add("emi.category.moderndelight.pizza_making","Pizza Making");
        translationBuilder.add("emi.category.moderndelight.steaming","Steaming");
        translationBuilder.add("emi.category.moderndelight.steaming_electric","Electric Steaming");
        translationBuilder.add("emi.category.moderndelight.advance_furnace_transforming","Transform");
        translationBuilder.add("emi.category.moderndelight.oven_transforming","Transform");
        translationBuilder.add("emi.category.moderndelight.oil_extraction","Oil Extraction");
        translationBuilder.add("emi.category.moderndelight.ice_cream_making","Ice Cream Making");
        translationBuilder.add("emi.category.moderndelight.juice_extracting","Juice Extracting");
        translationBuilder.add("emi.category.moderndelight.instant_noodles_making","Making Instant Noodles");
        translationBuilder.add("emi.category.moderndelight.grinding","Grinding");
        translationBuilder.add("emi.category.moderndelight.fermenting","Fermenting");

        translationBuilder.add("config.jade.moderndelight.machine_details", "Machine Details");
        translationBuilder.add("moderndelight.jade.tooltip.trawler.trawler_interval","Harvest time remaining: %d seconds");
        translationBuilder.add("moderndelight.jade.tooltip.sterling_engine.has_sponge","Noise has been reduced");
        translationBuilder.add("moderndelight.jade.tooltip.sterling_engine.no_sponge","Noise has not been reduced");
        translationBuilder.add("moderndelight.jade.tooltip.sterling_engine.working","Working");
        translationBuilder.add("moderndelight.jade.tooltip.sterling_engine.not_working","Standby");
        translationBuilder.add("moderndelight.jade.tooltip.ac_generator.power","Current AC power generation: %d EP/s");
        translationBuilder.add("moderndelight.jade.tooltip.juice_extractor.time","Juicing time remaining: %d seconds");
        translationBuilder.add("moderndelight.jade.tooltip.ac_device.powered","Powered on");
        translationBuilder.add("moderndelight.jade.tooltip.ac_device.not_powered","Not powered on");
        translationBuilder.add("moderndelight.jade.tooltip.industrial_fermenter.temperature", "Temperature: %d °C");
        translationBuilder.add("moderndelight.jade.tooltip.industrial_fermenter.dominate", "Current dominate bacteria: %s");
        translationBuilder.add("moderndelight.jade.tooltip.industrial_fermenter.progress", "Fermentation time remaining: %d seconds");
        translationBuilder.add("moderndelight.jade.tooltip.electric_steamer.steam", "Steam: %d / %d mB");
        translationBuilder.add("moderndelight.jade.tooltip.electric_steamer.active_slots", "Active steaming slots: %d");
        translationBuilder.add("moderndelight.jade.tooltip.ice_cream_maker.progress", "Ice cream production progress: %d seconds");
        translationBuilder.add("moderndelight.jade.tooltip.ice_cream_maker.flavor", "Selected flavor: %s (%d mB)");
        translationBuilder.add("moderndelight.jade.tooltip.freezer.cooling_time", "Cooling time remaining: %d seconds");
        translationBuilder.add("moderndelight.jade.tooltip.freezer.progress", "Freezing progress: %d seconds");
        translationBuilder.add("moderndelight.jade.tooltip.oven.fuel_time", "Fuel remaining: %d seconds");
        translationBuilder.add("moderndelight.jade.tooltip.oven.progress", "Baking time remaining: %d seconds");
        translationBuilder.add("moderndelight.jade.tooltip.advance_furnace.active_slots", "Active cooking slots: %d");
        translationBuilder.add("moderndelight.jade.tooltip.advance_furnace.fuel_time", "Fuel remaining: %d seconds");
        translationBuilder.add("moderndelight.jade.tooltip.deep_fryer.heated", "Heater: %s");
        translationBuilder.add("moderndelight.jade.tooltip.deep_fryer.running", "On");
        translationBuilder.add("moderndelight.jade.tooltip.deep_fryer.stopped", "Off");
        translationBuilder.add("moderndelight.jade.tooltip.deep_fryer.active_slots", "Active frying slots: %d");
        translationBuilder.add("moderndelight.jade.tooltip.baking_tray.stir_fry", "Stirring: %d / %d times");
        translationBuilder.add("moderndelight.jade.tooltip.baking_tray.heat_source", "Heat source: %s");
        translationBuilder.add("moderndelight.jade.tooltip.baking_tray.heated", "Heated");
        translationBuilder.add("moderndelight.jade.tooltip.baking_tray.cold", "Not heated");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.heat", "Heat: %s");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.cover", "Cover: %s");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.layer", "Layer: %d / %d");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.active_slots", "Active steaming slots: %d");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.heated", "Heated");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.cold", "Not heated");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.covered", "Covered");
        translationBuilder.add("moderndelight.jade.tooltip.bamboo_grate.uncovered", "Uncovered");
        translationBuilder.add("moderndelight.jade.tooltip.gas_canister.gas", "Gas has been filled");
        translationBuilder.add("moderndelight.jade.tooltip.gas_canister.not_gas", "Gas Not Filled");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_controller.gas", "Biogas: %d / %d mB");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_controller.volume", "Tank volume: %d blocks");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_controller.structure", "Structure: %s");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_controller.valid", "Valid");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_controller.invalid", "Invalid");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_io.gas", "Biogas: %d mB");
        translationBuilder.add("moderndelight.jade.tooltip.biogas_digester_io.progress", "Fermentation progress: %d ticks");
        translationBuilder.add("moderndelight.jade.tooltip.dc_generator.power", "Current DC power generation: %d EP/s");
        translationBuilder.add("moderndelight.jade.tooltip.ac_dc_converter.mode", "Output mode: %s");
        translationBuilder.add("moderndelight.jade.tooltip.ac_dc_converter.mode_ac", "AC");
        translationBuilder.add("moderndelight.jade.tooltip.ac_dc_converter.mode_dc", "DC");
        translationBuilder.add("moderndelight.jade.tooltip.ac_dc_converter.speed", "Work speed: %d / %d");
        translationBuilder.add("moderndelight.jade.tooltip.tesla_coil.efficiency", "Current load: %d EP");
        translationBuilder.add("moderndelight.jade.tooltip.tesla_coil.devices", "Activated devices: %d");


        translationBuilder.add("sounds.moderndelight.entity_butter_hit", "Butter: Hit");
        translationBuilder.add("sounds.moderndelight.entity_butter_shoot", "Butter: Shoot");
        translationBuilder.add("sounds.moderndelight.entity_cherry_bomb_explosion", "Cherry: Explosion");
        translationBuilder.add("sounds.moderndelight.entity_cherry_bomb_shoot", "Cherry: Shoot");
        translationBuilder.add("sounds.moderndelight.block_freezer_running", "Freezer: Running");
        translationBuilder.add("sounds.moderndelight.block_freezer_open", "Freezer: Open");
        translationBuilder.add("sounds.moderndelight.block_freezer_close", "Freezer: Close");
        translationBuilder.add("sounds.moderndelight.block_glass_bowl_whisking", "Item: Whisked");
        translationBuilder.add("sounds.moderndelight.block_food_frying", "Food: Frying");
        translationBuilder.add("sounds.moderndelight.item_crowbar_hit", "Crowbar: Hit");
        translationBuilder.add("sounds.moderndelight.item_crowbar_attack", "Crowbar: Attack");
        translationBuilder.add("sounds.moderndelight.block_gas_canister_filling", "Gas Canister: Filling");
        translationBuilder.add("sounds.moderndelight.block_gas_cooking_stove_ignite", "Gas Cooking Stove: Try to Ignite");
        translationBuilder.add("sounds.moderndelight.block_sterling_engine", "Stirling Engine: Running");
        translationBuilder.add("sounds.moderndelight.block_sterling_engine_start", "Stirling Engine: Staring");
        translationBuilder.add("sounds.moderndelight.block_sterling_engine_stop", "Stirling Engine: Stopping");
        translationBuilder.add("sounds.moderndelight.block_tesla_coil", "Tesla Coil: Electroshock");
        translationBuilder.add("sounds.moderndelight.item_electric_whisk_working", "Electric Whisk: Working");
        translationBuilder.add("sounds.moderndelight.block_juice_extractor_working", "Juice Extractor: Running");
        translationBuilder.add("sounds.moderndelight.block_juice_extractor_scream", "Juice Extractor: Screaming");
        translationBuilder.add("sounds.moderndelight.block_juice_extractor_stop", "Juice Extractor: Stop Running");
        translationBuilder.add("sounds.moderndelight.item_stone_mortar_working", "Stone Mortar: Grinding");

        translationBuilder.add(ModernDelightClient.ORE_UI_DARK, "Ore UI Style (Dark)");
        translationBuilder.add(ModernDelightClient.ORE_UI_BRIGHT, "Ore UI Style (Bright)");

        translationBuilder.add("modmenu.nameTranslation.moderndelight","Modern Delight");
        translationBuilder.add("modmenu.descriptionTranslation.moderndelight","Allow Minecraft to have a more enriched way of processing ingredients.");
        translationBuilder.add("modmenu.summaryTranslation.moderndelight","A more modern approach to ingredients.");

        translationBuilder.add("config.moderndelight.title","Modern Delight Config");
        translationBuilder.add("config.moderndelight.biogasSystem.title","Biogas System Config");
        translationBuilder.add("config.moderndelight.option.digestate","Composition of Digestate");
        translationBuilder.add("config.moderndelight.option.digestate.desc","Modification of the composition of the digestate produced by the biogas digester input and output. The Namespaced identifier of the item is required here.");
        translationBuilder.add("config.moderndelight.powerSystem.title","Power System Config");
        translationBuilder.add("config.moderndelight.option.acdcConverterMaxWorkSpeed","AC/DC Converter Max Work Speed");
        translationBuilder.add("config.moderndelight.option.acdcConverterMaxWorkSpeed.desc","Modification of the max work speed of AC/DC converter.");
        translationBuilder.add("config.moderndelight.option.energyGeneratedByFaradayGenerator","EP Generated by Faraday Generator");
        translationBuilder.add("config.moderndelight.option.energyGeneratedByFaradayGenerator.desc","Modification of the energy power generated by Faraday generator.");
        translationBuilder.add("config.moderndelight.option.photovoltaicGeneratorMultiplier","Multiplier for EP Generated by Photovoltaic Generator");
        translationBuilder.add("config.moderndelight.option.photovoltaicGeneratorMultiplier.desc","Modification of the multiplier for energy power produced by photovoltaic generator.");
        translationBuilder.add("config.moderndelight.option.windTurbineMultiplier","Multiplier for EP Generated by Wind Turbine");
        translationBuilder.add("config.moderndelight.option.windTurbineMultiplier.desc","Modification of the multiplier for energy power produced by wind turbine.");
        translationBuilder.add("config.moderndelight.option.teslaCoilConversionEfficiency","EP Conversion Efficiency of Tesla Coil");
        translationBuilder.add("config.moderndelight.option.teslaCoilConversionEfficiency.desc","Modification of the energy power conversion efficiency of Tesla coil.");
        translationBuilder.add("config.moderndelight.option.allowGasCanisterExplode","Allow Gas Canister to Explode");
        translationBuilder.add("config.moderndelight.option.allowGasCanisterExplode.desc","When this option is enabled, a gas canister may explode if there is a ‘Danger Block’ next to it, and the gas canister may also explode if it is overfilled with gas.");
        translationBuilder.add("config.moderndelight.option.allowGasCanisterInNether","Allow Normal Use of Gas Canister in the Nether");
        translationBuilder.add("config.moderndelight.option.allowGasCanisterInNether.desc","When this option is enabled, the gas canister will be allowed to function normally in the Nether without exploding.");
        translationBuilder.add("config.moderndelight.option.gasCanisterVolume","Capacity of Gas Canister");
        translationBuilder.add("config.moderndelight.option.gasCanisterVolume.desc","Modification of gas canister capacity.");
        translationBuilder.add("config.moderndelight.seasoning.title","Flavouring System Config");
        translationBuilder.add("config.moderndelight.option.maxSeasonings.title","Maximum amount of seasonings");
        translationBuilder.add("config.moderndelight.option.maxSeasonings.desc","Set the maximum amount of seasoning you can add to a food.");
        translationBuilder.add("config.moderndelight.option.chargingPostEfficiency.title","EP Conversion Efficiency of Charging Post");
        translationBuilder.add("config.moderndelight.option.chargingPostEfficiency.desc","Modification of the energy power conversion efficiency of Charging Post.");
        translationBuilder.add("config.moderndelight.trawler.title","Trawler Config");
        translationBuilder.add("config.moderndelight.option.base_fishing_intervals.title","Default Trawling Time (seconds)");
        translationBuilder.add("config.moderndelight.option.base_fishing_intervals.desc","Configure the default trawling duration.");
        translationBuilder.add("config.moderndelight.option.heartyMealThreshold.title","The Threshold for a Hearty Meal");
        translationBuilder.add("config.moderndelight.option.heartyMealThreshold.desc","Adjust the hunger threshold at which a meal is classified as a hearty meal.");


        translationBuilder.add(ModItems.GUIDE_BOOK,"Modern Delight Guide");
        translationBuilder.add("patchouli.moderndelight.book.name","Modern Delight Guide");
        translationBuilder.add("patchouli.moderndelight.book.landing_text","Introducing you: Modern Delight. This book will show you how to play this mod better.");

        translationBuilder.add("advancement.moderndelight.get_start_desc", "A simple mod guide begins here. Let's get cooking!");
        translationBuilder.add("advancement.moderndelight.got_whisk.title", "The Truth Is It's a Whisk");
        translationBuilder.add("advancement.moderndelight.got_whisk.desc", "A whisk, a glass bowl, and absolutely no idea what you're doing. Welcome to Modern Delight.");
        translationBuilder.add("advancement.moderndelight.got_amethyst.title", "Sparkling!");
        translationBuilder.add("advancement.moderndelight.got_amethyst.desc", "Craft an amethyst knife. Against squid, it's less a knife and more of a can opener.");
        translationBuilder.add("advancement.moderndelight.got_cuttlebone.title", "Better Than Milk");
        translationBuilder.add("advancement.moderndelight.got_cuttlebone.desc", "Kill a squid with an amethyst knife and take its cuttlebone. Rich in calcium, low in dignity.");
        translationBuilder.add("advancement.moderndelight.got_kebabs.title", "Have a Kebab");
        translationBuilder.add("advancement.moderndelight.got_kebabs.desc", "Grill a squid tentacle kebab. Delicious, healthy, and slightly judgmental.");
        translationBuilder.add("advancement.moderndelight.got_truffle.title", "Truffle Shuffle");
        translationBuilder.add("advancement.moderndelight.got_truffle.desc", "Break podzol to dig up a truffle. Pigs are optional; patience is not.");
        translationBuilder.add("advancement.moderndelight.got_cherry_bomb.title", "Cherry Bomb");
        translationBuilder.add("advancement.moderndelight.got_cherry_bomb.desc", "Throw a Cherry Bomb and let it explode. There's a zombie on your lawn; this should sort it out. (Hidden Advancement)");
        translationBuilder.add("advancement.moderndelight.cuisine_table.title", "Deconstructivism");
        translationBuilder.add("advancement.moderndelight.cuisine_table.desc", "Craft a Cuisine Table. Put a tool on top, an ingredient below, and watch it have an existential crisis.");
        translationBuilder.add("advancement.moderndelight.cabinet.title", "Kitchen Makeover");
        translationBuilder.add("advancement.moderndelight.cabinet.desc", "Get any cabinet. Your kitchen called; it wants to be taken seriously.");
        translationBuilder.add("advancement.moderndelight.stone_mortar.title", "Caveman Chemistry");
        translationBuilder.add("advancement.moderndelight.stone_mortar.desc", "Craft a stone mortar. It's just rocks, but with a licence to grind.");
        translationBuilder.add("advancement.moderndelight.get_black_pepper.title", "Tropical Species");
        translationBuilder.add("advancement.moderndelight.get_black_pepper.desc", "Find wild black pepper in the jungle. Yes, it's a jungle out there.");
        translationBuilder.add("advancement.moderndelight.chili.title", "Some Like It Hot");
        translationBuilder.add("advancement.moderndelight.chili.desc", "Harvest a chili pepper. Eat it raw if you're feeling brave or stupid.");
        translationBuilder.add("advancement.moderndelight.chili_sauce.title", "Sriracha Tycoon");
        translationBuilder.add("advancement.moderndelight.chili_sauce.desc", "Make chili sauce. Somewhere, a bottle of ketchup feels threatened.");
        translationBuilder.add("advancement.moderndelight.garlic.title", "Vampires Hate This One Trick");
        translationBuilder.add("advancement.moderndelight.garlic.desc", "Find wild garlic in forests, mountains, and taigas. Works on vampires; does nothing for your breath.");
        translationBuilder.add("advancement.moderndelight.matcha.title", "Much Ado About Matcha");
        translationBuilder.add("advancement.moderndelight.matcha.desc", "Grind leaves into matcha. A lot of ceremony for a green drink.");
        translationBuilder.add("advancement.moderndelight.get_wheat_dough.title", "Sticky Situation II");
        translationBuilder.add("advancement.moderndelight.get_wheat_dough.desc", "Water plus flour plus regret. Roll it flat with a Kneading Stick, or don't - it's your dough.");
        translationBuilder.add("advancement.moderndelight.get_raw_pizza.title", "Pizza Time!");
        translationBuilder.add("advancement.moderndelight.get_raw_pizza.desc", "Place flattened dough, add five pizza ingredients, then finish with a layer of cheese.");
        translationBuilder.add("advancement.moderndelight.get_pizza.title", "A Slice of Life");
        translationBuilder.add("advancement.moderndelight.get_pizza.desc", "Bake the raw pizza: black pepper, sugar, black pepper - in that order. Don't ask.");
        translationBuilder.add("advancement.moderndelight.get_advance_furnace.title", "Four Times the Heat");
        translationBuilder.add("advancement.moderndelight.get_advance_furnace.desc", "Craft an Advanced Furnace and a Baking Tray. Four times faster, same amount of panic.");
        translationBuilder.add("advancement.moderndelight.get_oven.title", "Bake to the Future");
        translationBuilder.add("advancement.moderndelight.get_oven.desc", "Upgrade the Advanced Furnace into an Oven. Great Scott - it actually works.");
        translationBuilder.add("advancement.moderndelight.get_crowbar.title", "Physics Is More of a Suggestion");
        translationBuilder.add("advancement.moderndelight.get_crowbar.desc", "Craft a crowbar, then pry a block apart with it. Physics will cope.");
        translationBuilder.add("advancement.moderndelight.get_egg_tart.title", "Egg-cellent!");
        translationBuilder.add("advancement.moderndelight.get_egg_tart.desc", "Bake an egg tart: cream, mixed dough, egg, then sugar. The order matters, apparently.");
        translationBuilder.add("advancement.moderndelight.truffle_egg_tart.title", "Bougie Egg Tart");
        translationBuilder.add("advancement.moderndelight.truffle_egg_tart.desc", "Eat a truffle egg tart. Congratulations, your dessert now has a market price.");
        translationBuilder.add("advancement.moderndelight.donut.title", "Donut Disturb");
        translationBuilder.add("advancement.moderndelight.donut.desc", "Bake two donuts and eat one. The hole doesn't count as calories.");
        translationBuilder.add("advancement.moderndelight.opera_cake.title", "The Phantom of the Oven");
        translationBuilder.add("advancement.moderndelight.opera_cake.desc", "Bake an Opera Cake. The first bite is chocolate; the second is an aria.");
        translationBuilder.add("advancement.moderndelight.all_cake.title", "Flower Power");
        translationBuilder.add("advancement.moderndelight.all_cake.desc", "Collect all nine flower cakes. Cake can be a flower, apparently.");
        translationBuilder.add("advancement.moderndelight.get_cream.title", "Whipped Into Shape");
        translationBuilder.add("advancement.moderndelight.get_cream.desc", "Whisk a milk bucket in a Glass Bowl to make cream. It's not spoiled - it's upgraded.");
        translationBuilder.add("advancement.moderndelight.get_butter.title", "Butter Late Than Never");
        translationBuilder.add("advancement.moderndelight.get_butter.desc", "Whisk cream in a Glass Bowl to churn butter. Everything you cook is now 40% more dangerous.");
        translationBuilder.add("advancement.moderndelight.all_pudding.title", "Pudding on the Ritz");
        translationBuilder.add("advancement.moderndelight.all_pudding.desc", "Collect all four puddings: apple, matcha, caramel, cherry. Cheap to make, expensive to resist.");
        translationBuilder.add("advancement.moderndelight.electricians_desk.title", "Towards Modernization");
        translationBuilder.add("advancement.moderndelight.electricians_desk.desc", "Craft an Electrician's Desk. It will be with you for a long time.");
        translationBuilder.add("advancement.moderndelight.tesla_coil.title", "Wireless Wonders");
        translationBuilder.add("advancement.moderndelight.tesla_coil.desc", "Craft a Tesla Coil and send power through the air. Edison is typing...");
        translationBuilder.add("advancement.moderndelight.wind.title", "The Wind Is Passing By");
        translationBuilder.add("advancement.moderndelight.wind.desc", "Make a fan blade and a wind turbine controller. Free electricity, courtesy of the sky.");
        translationBuilder.add("advancement.moderndelight.silicon_ingot.title", "Silicon Valley");
        translationBuilder.add("advancement.moderndelight.silicon_ingot.desc", "Smelt quartz into a silicon ingot. Somewhere, a startup just got funded.");
        translationBuilder.add("advancement.moderndelight.battery.title", "Battery Included");
        translationBuilder.add("advancement.moderndelight.battery.desc", "Craft any battery. Storing power is the second-best thing to having power.");
        translationBuilder.add("advancement.moderndelight.battery4.title", "Is There No End to This Power?");
        translationBuilder.add("advancement.moderndelight.battery4.desc", "Craft a Dimensional Battery. Your storage problems are now in another dimension.");
        translationBuilder.add("advancement.moderndelight.thermal_power_generation.title", "Industrial Revolution");
        translationBuilder.add("advancement.moderndelight.thermal_power_generation.desc", "Stirling Engine on a working furnace, flywheel to a Faraday Generator. The 19th century is impressed.");
        translationBuilder.add("advancement.moderndelight.pg.title", "Solar Punk");
        translationBuilder.add("advancement.moderndelight.pg.desc", "Craft a Photovoltaic Generator. Eco-friendly, and completely useless at night.");
        translationBuilder.add("advancement.moderndelight.electric_whisk.title", "The Lazy Kitchen");
        translationBuilder.add("advancement.moderndelight.electric_whisk.desc", "Craft an Electric Whisk. Charge it at a Charging Post, then whisk dropped items directly. Peak laziness.");
        translationBuilder.add("advancement.moderndelight.juice_extractor.title", "Centrifugal Force");
        translationBuilder.add("advancement.moderndelight.juice_extractor.desc", "Craft a Juice Extractor. Physics, but make it brunch.");
        translationBuilder.add("advancement.moderndelight.glass_cup.title", "Glass Half Full");
        translationBuilder.add("advancement.moderndelight.glass_cup.desc", "Craft a glass cup. Optimism, but drinkable.");
        translationBuilder.add("advancement.moderndelight.all_tea.title", "Boba Time");
        translationBuilder.add("advancement.moderndelight.all_tea.desc", "Extract cherry milk tea, rose ice tea, and mango milk tea. Boba is a food group.");
        translationBuilder.add("advancement.moderndelight.get_freezer.title", "Cool Down");
        translationBuilder.add("advancement.moderndelight.get_freezer.desc", "Craft a Freezer. Finally, a place where your ingredients - and your temper - can chill.");
        translationBuilder.add("advancement.moderndelight.ice_cream_maker.title", "Who Can Resist Ice Cream?");
        translationBuilder.add("advancement.moderndelight.ice_cream_maker.desc", "Craft an Ice Cream Maker. The answer is nobody. It's nobody.");
        translationBuilder.add("advancement.moderndelight.ice_cream.title", "Sweet Victory!");
        translationBuilder.add("advancement.moderndelight.ice_cream.desc", "Watch an ice cream cone assemble itself. This is the peak of civilization.");
        translationBuilder.add("advancement.moderndelight.all_mousse.title", "Mousse or Mouse?");
        translationBuilder.add("advancement.moderndelight.all_mousse.desc", "Collect all six mousses. The spelling has never been this delicious.");
        translationBuilder.add("advancement.moderndelight.all_ice_lolly.title", "I Scream, You Scream");
        translationBuilder.add("advancement.moderndelight.all_ice_lolly.desc", "Make all five ice lollies. Brain freeze is a small price for happiness.");
        translationBuilder.add("advancement.moderndelight.wither_lolly.title", "Brain Freeze");
        translationBuilder.add("advancement.moderndelight.wither_lolly.desc", "Eat a Wither Ice Lolly. Side effects include decay, darkness, and regret. (Hidden advancement)");
        translationBuilder.add("advancement.moderndelight.bdc_bdi.title", "Biogas Industry");
        translationBuilder.add("advancement.moderndelight.bdc_bdi.desc", "Craft a Biogas Digester Controller and an I/O Port. Read the instructions. Please. For once.");
        translationBuilder.add("advancement.moderndelight.gas_canister.title", "Flammable and Explosive");
        translationBuilder.add("advancement.moderndelight.gas_canister.desc", "Craft a Gas Canister. It carries gas for your stove and anxiety for your neighbours.");
        translationBuilder.add("advancement.moderndelight.gas_cooking_stove.title", "Now We're Cooking");
        translationBuilder.add("advancement.moderndelight.gas_cooking_stove.desc", "Craft a Gas Cooking Stove. It heats food, pots, and - if you're careless - you.");
        translationBuilder.add("advancement.moderndelight.click_gas_cooking_stove.title", "The Gas Stove DJ");
        translationBuilder.add("advancement.moderndelight.click_gas_cooking_stove.desc", "Right-click a Gas Cooking Stove 10 times in 3 seconds. Drop the beat - and the stove. (Hidden advancement)");
        translationBuilder.add("advancement.moderndelight.wooden_basin.title", "It's Not a Washbasin!");
        translationBuilder.add("advancement.moderndelight.wooden_basin.desc", "Craft a Wooden Basin. It presses oil by stomping. Your feet will never forgive you.");
        translationBuilder.add("advancement.moderndelight.soy_milk.title", "Soy to the World");
        translationBuilder.add("advancement.moderndelight.soy_milk.desc", "Squeeze soybeans into soy milk. Joy to the world, and to your breakfast.");
        translationBuilder.add("advancement.moderndelight.vegetable_oil.title", "The First Barrel");
        translationBuilder.add("advancement.moderndelight.vegetable_oil.desc", "Press oil with a Filter and an oil crop. Congratulations, deep-frying is now inevitable.");
        translationBuilder.add("advancement.moderndelight.deep_fryer.title", "Ordeal (For Food)");
        translationBuilder.add("advancement.moderndelight.deep_fryer.desc", "Craft a Deep Fryer and a Deep Fry Basket. Oil, gas, and questionable decisions.");
        translationBuilder.add("advancement.moderndelight.fried_noodles.title", "Instant Noodles Moment");
        translationBuilder.add("advancement.moderndelight.fried_noodles.desc", "Deep-fry noodles, then wrap them in multifunctional wrapping paper to make instant noodles.");
        translationBuilder.add("advancement.moderndelight.all_fried.title", "Fry Everything! (Not Literally)");
        translationBuilder.add("advancement.moderndelight.all_fried.desc", "Eat every fried food. Your cardiologist has been notified.");
        translationBuilder.add("advancement.moderndelight.fish_and_chips.title", "The Most British Thing Ever");
        translationBuilder.add("advancement.moderndelight.fish_and_chips.desc", "Make fish and chips. Yes, it's a full meal. No, the peas are not included.");
        translationBuilder.add("advancement.moderndelight.cooked_portable_pot.title", "Convenience Is Relative");
        translationBuilder.add("advancement.moderndelight.cooked_portable_pot.desc", "Cook instant noodles in a Portable Pot with quicklime and water. Nothing about this is convenient.");
        translationBuilder.add("advancement.moderndelight.bamboo_steamer.title", "Dense Water Vapor");
        translationBuilder.add("advancement.moderndelight.bamboo_steamer.desc", "Build a traditional steamer: basket, cover, cauldron. Steam is just water with ambition.");
        translationBuilder.add("advancement.moderndelight.electric_steamer.title", "Faster and Stronger!");
        translationBuilder.add("advancement.moderndelight.electric_steamer.desc", "Craft an Electric Steamer. Goodbye, cauldron-based architecture.");
        translationBuilder.add("advancement.moderndelight.steamed_bun.title", "Just Like Mom's");
        translationBuilder.add("advancement.moderndelight.steamed_bun.desc", "Steam some dough into a bun. If it's not like Mom's, don't tell her.");
        translationBuilder.add("advancement.moderndelight.dim_sum.title", "Dim Sum Sunday");
        translationBuilder.add("advancement.moderndelight.dim_sum.desc", "Make shaomai, crystal dumplings, and both kinds of stuffed buns. Yum cha is a lifestyle.");
        translationBuilder.add("advancement.moderndelight.fermentation_tank.title", "It's Alive!");
        translationBuilder.add("advancement.moderndelight.fermentation_tank.desc", "Craft a Fermentation Tank. Fill it with liquid and bacteria, then hand it to the Industrial Fermenter.");
        translationBuilder.add("advancement.moderndelight.industrial_fermenter.title", "Fermentation Station");
        translationBuilder.add("advancement.moderndelight.industrial_fermenter.desc", "Craft an Industrial Fermenter. Now the bacteria have a factory and a pension plan.");
        translationBuilder.add("advancement.moderndelight.get_cheese.title", "Say Cheese!");
        translationBuilder.add("advancement.moderndelight.get_cheese.desc", "Ferment milk with lactobacillus until it turns into cheese. Say cheese - you're on camera.");
        translationBuilder.add("advancement.moderndelight.double_burger.title", "Two Number Nines");
        translationBuilder.add("advancement.moderndelight.double_burger.desc", "Stack bread, two cooked pork chops, cabbage, and cheese into a double burger. Your diet can wait.");
        translationBuilder.add("advancement.moderndelight.vinegar.title", "Sour Grapes");
        translationBuilder.add("advancement.moderndelight.vinegar.desc", "Ferment sweetened water or alcohol into a bucket of vinegar. It's not a failure; it's a condiment.");
        translationBuilder.add("advancement.moderndelight.alcohol.title", "Moonshine");
        translationBuilder.add("advancement.moderndelight.alcohol.desc", "Ferment sweetened water into a bucket of alcohol.");
        translationBuilder.add("advancement.moderndelight.trawler.title", "Gone Fishin'");
        translationBuilder.add("advancement.moderndelight.trawler.desc", "Craft a Trawler. Why fish with a rod when you can fish with infrastructure?");
        translationBuilder.add("advancement.moderndelight.crab.title", "Crab Rave");
        translationBuilder.add("advancement.moderndelight.crab.desc", "Catch a crab with the Trawler. No music required, but strongly recommended.");
        translationBuilder.add("advancement.moderndelight.wither_rose_cake.title", "Bloom & Doom");
        translationBuilder.add("advancement.moderndelight.wither_rose_cake.desc", "Eat the Wither Rose Cake, then sit quietly and think about your life. (Hidden advancement)");
        translationBuilder.add("advancement.moderndelight.not_spicy.title", "Whoa... it's not spicy");
        translationBuilder.add("advancement.moderndelight.not_spicy.desc", "By constantly eating spicy food, and getting Mouth Afire V. (Hidden advancement)");

    }
}
