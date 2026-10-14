package com.zombie_cute.mc.bakingdelight.gen;

import com.zombie_cute.mc.bakingdelight.ModernDelightClient;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.biogas.GasCanisterBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.food.pizza.PizzaWIPBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.*;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.decor.CabinetBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.DeepFryerBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.WoodenBasinBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.juice_extractor.JuiceExtractorBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.steaming.BambooGrateBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.PhotovoltaicGeneratorBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.batteries.BatteryBlockItem;
import com.zombie_cute.mc.bakingdelight.compat.rei.baking_tray.BakingTrayCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.biogas_fermentation.BiogasFermentationCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.glass_bowl.GlassBowlMixWithWaterCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.glass_bowl.GlassBowlWhiskingCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.pizza.PizzaMakingCategory;
import com.zombie_cute.mc.bakingdelight.compat.rei.transform.OvenTransformCategory;
import com.zombie_cute.mc.bakingdelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.bakingdelight.enchantment.ModEnchantments;
import com.zombie_cute.mc.bakingdelight.entity.ModEntities;
import com.zombie_cute.mc.bakingdelight.item.ModItemGroups;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.screen.custom.*;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.BacteriaType;
import com.zombie_cute.mc.bakingdelight.util.enums.CreamFlavor;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
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
                        "factors such as current altitude, time of day, weather, block light and sky light.");
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
        translationBuilder.add(ModItems.OIL_IMPURITY, "Oil Slag");
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
        translationBuilder.add(ModItems.BLACK_PEPPER_SEASONING, "Black Pepper Jar");

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

        translationBuilder.add(ModItems.MOUSSE_WIP, "Mousse (Work In Progress)");
        translationBuilder.add(ModItems.CREAM_MOUSSE, "Cream Mousse");
        translationBuilder.add(ModItems.CHERRY_MOUSSE, "Cherry Mouse");
        translationBuilder.add(ModItems.CHOCOLATE_MOUSSE, "Chocolate Mouse");
        translationBuilder.add(ModItems.PUMPKIN_MOUSSE, "Pumpkin Mouse");
        translationBuilder.add(ModItems.GOLDEN_APPLE_MOUSSE, "Golden Apple Mousse");
        translationBuilder.add(ModItems.MATCHA_MOUSSE, "Matcha Mousse");

        translationBuilder.add(ModItems.CRYSTAL_DUMPLING, "Crystal Dumpling");

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
        translationBuilder.add(ModItems.BLACK_TRUFFLE_SEASONING, "Black Truffle Jar");
        translationBuilder.add(ModItems.WHITE_TRUFFLE_SEASONING, "White Truffle Jar");

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
        translationBuilder.add(ModItems.STREAKY_PORK, "Streaky Pork");
        translationBuilder.add(ModItems.TURNIP, "Rutabaga");
        translationBuilder.add(TextUtil.TURNIP, "A slight grudge from the author.");
        translationBuilder.add(ModItems.RAW_DONUT, "Raw Donut");
        translationBuilder.add(ModItems.CHOCOLATE_DONUT, "Chocolate Donut");
        translationBuilder.add(ModItems.CHEESE_BURGER, "Cheese Burger");
        translationBuilder.add(ModItems.ROAST_STREAKY_PORK, "Roast Streaky Pork");
        translationBuilder.add(ModItems.PORK_CHOP_BURGER, "Pork Chop Burger");
        translationBuilder.add(ModItems.FRIED_COD_NUGGET, "Deep-fried Cod Nugget");
        translationBuilder.add(ModItems.PORK_RIBS, "Pork Ribs");
        translationBuilder.add(ModItems.PORK_HOOF, "Pork Hoof");
        translationBuilder.add(ModBlocks.FISH_AND_CHIPS_ITEM, "Fish and Chips");
        translationBuilder.add(ModBlocks.WILD_PEPPER_CROP, "Wild Black Pepper");
        translationBuilder.add(ModItems.GARLIC, "Garlic");
        translationBuilder.add(ModItems.GARLIC_PETAL, "Garlic Petal");
        translationBuilder.add(ModBlocks.WILD_GARLIC, "Wild Garlic");
        translationBuilder.add(ModItems.ELECTRIC_WHISK, "Electric Whisk");
        translationBuilder.add(ModBlocks.CHARGING_POST, "Charging Post");

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
        translationBuilder.add(ModItems.STEAMED_STUFFED_BUN_WIP, "Steamed Stuffed Bun (Work In Progress)");
        translationBuilder.add(ModItems.STEAMED_STUFFED_BUN, "Steamed Stuffed Bun");
        translationBuilder.add(ModItems.VEGETABLE_STEAMED_STUFFED_BUN_WIP, "Vegetable Steamed Stuffed Bun (Work In Progress)");
        translationBuilder.add(ModItems.VEGETABLE_STEAMED_STUFFED_BUN, "Vegetable Steamed Stuffed Bun");
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


        translationBuilder.add("death.attack.bakingdelight_electroshock","%1$s was electrocuted!");
        translationBuilder.add("death.attack.bakingdelight_turned_to_ashes","%1$s turned to ashes");
        translationBuilder.add("death.attack.bakingdelight_turned_to_ashes.player","%1$s turned to ashes");
        translationBuilder.add("death.attack.bakingdelight_scalded","%1$s scalded to death");

        translationBuilder.add(ModBlocks.CREAM_FLUID_BLOCK, "Cream");
        translationBuilder.add(ModBlocks.VEGETABLE_OIL_FLUID_BLOCK, "Vegetable Oil");
        translationBuilder.add(ModBlocks.LIQUEFIED_BIOGAS_FLUID_BLOCK, "Liquefied Biogas");
        translationBuilder.add(ModBlocks.SWEENTENED_WATER_FLUID_BLOCK, "Sweetened Water");
        translationBuilder.add(ModBlocks.ALCOHOL_FLUID_BLOCK, "Alcohol");
        translationBuilder.add(ModBlocks.VINEGAR_FLUID_BLOCK, "Vinegar");
        translationBuilder.add(ModBlocks.MILK_FLUID_BLOCK, "Milk (Fermenting)");

        translationBuilder.add(ModEnchantments.FINE_GRINDING,"Precision Grinding");
        translationBuilder.add(ModEnchantments.FINE_GRINDING.getTranslationKey()+".desc","The stone mortar will have additional output when grinding.");

        translationBuilder.add(ModEffectsAndPotions.STICKY,"Sticky");
        translationBuilder.add(ModEffectsAndPotions.STICKY.getTranslationKey() + ".description","Drastically reduces movement speed while being unable to jump.");


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

        translationBuilder.add("emi.category.bakingdelight.mix_with_water","Mix with Water");
        translationBuilder.add("emi.category.bakingdelight.whisking","Whisking");
        translationBuilder.add("emi.category.bakingdelight.assembly","Electrician's Desk");
        translationBuilder.add("emi.category.bakingdelight.stir_frying","Stir Frying");
        translationBuilder.add("emi.category.bakingdelight.biogas_fermentation","Biogas Fermentation");
        translationBuilder.add("emi.category.bakingdelight.cuisine","Cuisine");
        translationBuilder.add("emi.category.bakingdelight.deep_frying","Deep Frying");
        translationBuilder.add("emi.category.bakingdelight.freezing","Freezing");
        translationBuilder.add("emi.category.bakingdelight.baking","Baking");
        translationBuilder.add("emi.category.bakingdelight.pizza_making","Pizza Making");
        translationBuilder.add("emi.category.bakingdelight.steaming","Steaming");
        translationBuilder.add("emi.category.bakingdelight.steaming_electric","Electric Steaming");
        translationBuilder.add("emi.category.bakingdelight.advance_furnace_transforming","Transform");
        translationBuilder.add("emi.category.bakingdelight.oven_transforming","Transform");
        translationBuilder.add("emi.category.bakingdelight.oil_extraction","Oil Extraction");
        translationBuilder.add("emi.category.bakingdelight.ice_cream_making","Ice Cream Making");
        translationBuilder.add("emi.category.bakingdelight.juice_extracting","Juice Extracting");
        translationBuilder.add("emi.category.bakingdelight.instant_noodles_making","Making Instant Noodles");
        translationBuilder.add("emi.category.bakingdelight.grinding","Grinding");
        translationBuilder.add("emi.category.bakingdelight.fermenting","Fermenting");

        translationBuilder.add("config.jade.bakingdelight.trawler_interval","Trawler Harvest Time");
        translationBuilder.add("bakingdelight.jade.tooltip.trawler_interval","Harvest time remaining: %d second(s)");

        translationBuilder.add("sounds.bakingdelight.entity_butter_hit", "Butter: Hit");
        translationBuilder.add("sounds.bakingdelight.entity_butter_shoot", "Butter: Shoot");
        translationBuilder.add("sounds.bakingdelight.entity_cherry_bomb_explosion", "Cherry: Explosion");
        translationBuilder.add("sounds.bakingdelight.entity_cherry_bomb_shoot", "Cherry: Shoot");
        translationBuilder.add("sounds.bakingdelight.block_freezer_running", "Freezer: Running");
        translationBuilder.add("sounds.bakingdelight.block_freezer_open", "Freezer: Open");
        translationBuilder.add("sounds.bakingdelight.block_freezer_close", "Freezer: Close");
        translationBuilder.add("sounds.bakingdelight.block_glass_bowl_whisking", "Item: Whisked");
        translationBuilder.add("sounds.bakingdelight.block_food_frying", "Food: Frying");
        translationBuilder.add("sounds.bakingdelight.item_crowbar_hit", "Crowbar: Hit");
        translationBuilder.add("sounds.bakingdelight.item_crowbar_attack", "Crowbar: Attack");
        translationBuilder.add("sounds.bakingdelight.block_gas_canister_filling", "Gas Canister: Filling");
        translationBuilder.add("sounds.bakingdelight.block_gas_cooking_stove_ignite", "Gas Cooking Stove: Try to Ignite");
        translationBuilder.add("sounds.bakingdelight.block_sterling_engine", "Stirling Engine: Running");
        translationBuilder.add("sounds.bakingdelight.block_sterling_engine_start", "Stirling Engine: Staring");
        translationBuilder.add("sounds.bakingdelight.block_sterling_engine_stop", "Stirling Engine: Stopping");
        translationBuilder.add("sounds.bakingdelight.block_tesla_coil", "Tesla Coil: Electroshock");
        translationBuilder.add("sounds.bakingdelight.item_electric_whisk_working", "Electric Whisk: Working");
        translationBuilder.add("sounds.bakingdelight.block_juice_extractor_working", "Juice Extractor: Running");
        translationBuilder.add("sounds.bakingdelight.block_juice_extractor_scream", "Juice Extractor: Screaming");
        translationBuilder.add("sounds.bakingdelight.block_juice_extractor_stop", "Juice Extractor: Stop Running");
        translationBuilder.add("sounds.bakingdelight.item_stone_mortar_working", "Stone Mortar: Grinding");

        translationBuilder.add(ModernDelightClient.ORE_UI_DARK, "Ore UI Style (Dark)");
        translationBuilder.add(ModernDelightClient.ORE_UI_BRIGHT, "Ore UI Style (Bright)");

        translationBuilder.add("modmenu.nameTranslation.bakingdelight","Modern Delight");
        translationBuilder.add("modmenu.descriptionTranslation.bakingdelight","Allow Minecraft to have a more enriched way of processing ingredients.");
        translationBuilder.add("modmenu.summaryTranslation.bakingdelight","A more modern approach to ingredients.");

        translationBuilder.add("config.bakingdelight.title","Modern Delight Config");
        translationBuilder.add("config.bakingdelight.biogasSystem.title","Biogas System Config");
        translationBuilder.add("config.bakingdelight.option.digestate","Composition of Digestate");
        translationBuilder.add("config.bakingdelight.option.digestate.desc","Modification of the composition of the digestate produced by the biogas digester input and output. The Namespaced identifier of the item is required here.");
        translationBuilder.add("config.bakingdelight.powerSystem.title","Power System Config");
        translationBuilder.add("config.bakingdelight.option.acdcConverterMaxWorkSpeed","AC/DC Converter Max Work Speed");
        translationBuilder.add("config.bakingdelight.option.acdcConverterMaxWorkSpeed.desc","Modification of the max work speed of AC/DC converter.");
        translationBuilder.add("config.bakingdelight.option.energyGeneratedByFaradayGenerator","EP Generated by Faraday Generator");
        translationBuilder.add("config.bakingdelight.option.energyGeneratedByFaradayGenerator.desc","Modification of the energy power generated by Faraday generator.");
        translationBuilder.add("config.bakingdelight.option.photovoltaicGeneratorMultiplier","Multiplier for EP Generated by Photovoltaic Generator");
        translationBuilder.add("config.bakingdelight.option.photovoltaicGeneratorMultiplier.desc","Modification of the multiplier for energy power produced by photovoltaic generator.");
        translationBuilder.add("config.bakingdelight.option.windTurbineMultiplier","Multiplier for EP Generated by Wind Turbine");
        translationBuilder.add("config.bakingdelight.option.windTurbineMultiplier.desc","Modification of the multiplier for energy power produced by wind turbine.");
        translationBuilder.add("config.bakingdelight.option.teslaCoilConversionEfficiency","EP Conversion Efficiency of Tesla Coil");
        translationBuilder.add("config.bakingdelight.option.teslaCoilConversionEfficiency.desc","Modification of the energy power conversion efficiency of Tesla coil.");
        translationBuilder.add("config.bakingdelight.option.allowGasCanisterExplode","Allow Gas Canister to Explode");
        translationBuilder.add("config.bakingdelight.option.allowGasCanisterExplode.desc","When this option is enabled, a gas canister may explode if there is a ‘Danger Block’ next to it, and the gas canister may also explode if it is overfilled with gas.");
        translationBuilder.add("config.bakingdelight.option.allowGasCanisterInNether","Allow Normal Use of Gas Canister in the Nether");
        translationBuilder.add("config.bakingdelight.option.allowGasCanisterInNether.desc","When this option is enabled, the gas canister will be allowed to function normally in the Nether without exploding.");
        translationBuilder.add("config.bakingdelight.option.gasCanisterVolume","Capacity of Gas Canister");
        translationBuilder.add("config.bakingdelight.option.gasCanisterVolume.desc","Modification of gas canister capacity.");
        translationBuilder.add("config.bakingdelight.seasoning.title","Flavouring System Config");
        translationBuilder.add("config.bakingdelight.option.maxSeasonings.title","Maximum amount of seasonings");
        translationBuilder.add("config.bakingdelight.option.maxSeasonings.desc","Set the maximum amount of seasoning you can add to a food.");
        translationBuilder.add("config.bakingdelight.option.chargingPostEfficiency.title","EP Conversion Efficiency of Charging Post");
        translationBuilder.add("config.bakingdelight.option.chargingPostEfficiency.desc","Modification of the energy power conversion efficiency of Charging Post.");
        translationBuilder.add("config.bakingdelight.trawler.title","Trawler Config");
        translationBuilder.add("config.bakingdelight.option.base_fishing_intervals.title","Default Trawling Time (seconds)");
        translationBuilder.add("config.bakingdelight.option.base_fishing_intervals.desc","Configure the default trawling duration.");

        translationBuilder.add(ModItems.GUIDE_BOOK,"Modern Delight Guide");
        translationBuilder.add("patchouli.bakingdelight.book.name","Modern Delight Guide");
        translationBuilder.add("patchouli.bakingdelight.book.landing_text","Introducing you: Modern Delight. This book will show you how to play this mod better.");

        translationBuilder.add(ModAdvancementGenerator.GET_WHISK_TITLE, "The truth is it's a whisk");
        translationBuilder.add(ModAdvancementGenerator.GET_WHISK_DESC, "Craft a whisk to start your new kitchen journey.");
        translationBuilder.add(ModAdvancementGenerator.GET_AMETHYST_TOOL_TITLE, "Sparkling!");
        translationBuilder.add(ModAdvancementGenerator.GET_AMETHYST_TOOL_DESC, "Craft an amethyst tool.");
        translationBuilder.add(ModAdvancementGenerator.GET_CUTTLEBONE_TITLE, "Better Than Milk");
        translationBuilder.add(ModAdvancementGenerator.GET_CUTTLEBONE_DESC, "Get a cuttlebone");
        translationBuilder.add(ModAdvancementGenerator.GET_TRUFFLE_TITLE, "Underground");
        translationBuilder.add(ModAdvancementGenerator.GET_TRUFFLE_DESC, "Find a truffle.");
        translationBuilder.add(ModAdvancementGenerator.GET_CHERRY_BOMB_TITLE, "BOOM");
        translationBuilder.add(ModAdvancementGenerator.GET_CHERRY_BOMB_DESC, "✧(≖ ◡ ≖✿)");
        translationBuilder.add(ModAdvancementGenerator.GET_GLASS_BOWL_TITLE, "Handle with Care");
        translationBuilder.add(ModAdvancementGenerator.GET_GLASS_BOWL_DESC, "Be careful not to break the bowl.");
        translationBuilder.add(ModAdvancementGenerator.GET_MASHED_POTATO_TITLE, "Powdery Taste");
        translationBuilder.add(ModAdvancementGenerator.GET_MASHED_POTATO_DESC, "Place a potato in a Glass Bowl and whisk it to make mashed potatoes.");
        translationBuilder.add(ModAdvancementGenerator.GET_POTATO_STARCH_TITLE, "Complete Powder");
        translationBuilder.add(ModAdvancementGenerator.GET_POTATO_STARCH_DESC, "Whisk mashed potatoes in a Glass Bowl to make potato starch.");
        translationBuilder.add(ModAdvancementGenerator.GET_FREEZER_TITLE, "Cool Down");
        translationBuilder.add(ModAdvancementGenerator.GET_FREEZER_DESC, "It's time to let the ingredients cool down a bit, and this freezer can cold process quite a few things!");
        translationBuilder.add(ModAdvancementGenerator.GET_OVEN_TITLE, "\"Hot\" Topic");
        translationBuilder.add(ModAdvancementGenerator.GET_OVEN_DESC, "The oven will help you bake better food.");
        translationBuilder.add(ModAdvancementGenerator.GET_EGG_TART_TITLE, "Sweety!");
        translationBuilder.add(ModAdvancementGenerator.GET_EGG_TART_DESC, "Add cream, mixed dough, egg, then sugar to the oven to bake an egg tart.");
        translationBuilder.add(ModAdvancementGenerator.GET_ALL_PUDDING_TITLE, "Chewy!");
        translationBuilder.add(ModAdvancementGenerator.GET_ALL_PUDDING_DESC, "Collect all the puddings.");
        translationBuilder.add(ModAdvancementGenerator.GET_ALL_MOUSSE_TITLE, "Fluffy!");
        translationBuilder.add(ModAdvancementGenerator.GET_ALL_MOUSSE_DESC, "Collect all the mousses.");
        translationBuilder.add(ModAdvancementGenerator.GET_CREAM_BUCKET_TITLE, "Slimy Milk");
        translationBuilder.add(ModAdvancementGenerator.GET_CREAM_BUCKET_DESC, "Whisk a milk bucket in a Glass Bowl to obtain a cream bucket.");
        translationBuilder.add(ModAdvancementGenerator.GET_BUTTER_TITLE, "Sticky and Sweet");
        translationBuilder.add(ModAdvancementGenerator.GET_BUTTER_DESC, "Whisk cream in a Glass Bowl to churn it into butter.");
        translationBuilder.add("advancement.bakingdelight.get_start_desc", "A simple mod guide begins here. Let's get cooking!");
        translationBuilder.add("advancement.bakingdelight.get_kneading_stick.title", "Grandma's Kneading Stick");
        translationBuilder.add("advancement.bakingdelight.get_kneading_stick.desc", "It's just an ordinary wooden stick.");
        translationBuilder.add("advancement.bakingdelight.get_wheat_flour.title", "Better Bread Making");
        translationBuilder.add("advancement.bakingdelight.get_wheat_flour.desc", "Whisk wheat in a Glass Bowl to make wheat flour.");
        translationBuilder.add("advancement.bakingdelight.get_wheat_dough.title", "Sticky Mess");
        translationBuilder.add("advancement.bakingdelight.get_wheat_dough.desc", "Pour water into a Glass Bowl, add wheat flour, and knead it into dough! Roll it flat with a Kneading Stick, or bake some bread.");
        translationBuilder.add("advancement.bakingdelight.get_raw_pizza.title", "Getting ready to open a pizzeria!");
        translationBuilder.add("advancement.bakingdelight.get_raw_pizza.desc", "Start with flattened dough and cheese, add five ingredients of your choice, then finish with another cheese.");
        translationBuilder.add("advancement.bakingdelight.get_pizza.title", "PIZZA!");
        translationBuilder.add("advancement.bakingdelight.get_pizza.desc", "Bake in this order: ground black pepper, sugar, ground black pepper, then raw pizza.");
        translationBuilder.add("advancement.bakingdelight.get_black_pepper.title", "Tropical Species");
        translationBuilder.add("advancement.bakingdelight.get_black_pepper.desc", "Discover this new crop in a village home or the jungle.");
        translationBuilder.add("advancement.bakingdelight.get_cheese.title", "Not Sour");
        translationBuilder.add("advancement.bakingdelight.get_cheese.desc", "Cook milk in the oven to make cheese.");
        translationBuilder.add("advancement.bakingdelight.get_advance_furnace.title", "Four Times the Heat");
        translationBuilder.add("advancement.bakingdelight.get_advance_furnace.desc", "Craft an Advanced Furnace: it smelts items four times faster than a regular furnace!");
        translationBuilder.add("advancement.bakingdelight.get_baking_tray.title", "Not just a \"Baking\" Tray");
        translationBuilder.add("advancement.bakingdelight.get_baking_tray.desc", "Place it inside an Advanced Furnace to upgrade it into an Oven.");
        translationBuilder.add("advancement.bakingdelight.get_crowbar.title", "Physics Is More of a Suggestion");
        translationBuilder.add("advancement.bakingdelight.get_crowbar.desc", "Right-click to downgrade an Oven or quickly break doors and windows. Whatever you do, don't hit anyone with it.");
        translationBuilder.add("advancement.bakingdelight.wooden_basin.title", "It's not a Washbasin!");
        translationBuilder.add("advancement.bakingdelight.wooden_basin.desc", "Apparently it's a tool for extracting oil.");
        translationBuilder.add("advancement.bakingdelight.filter.title", "Beginning of a life of labor");
        translationBuilder.add("advancement.bakingdelight.filter.desc", "Put a filter and a cooked oil crop, such as sunflower seeds, in the Wooden Basin, then stomp on it to press out the oil.");
        translationBuilder.add("advancement.bakingdelight.vegetable_oil.title", "The First Barrel");
        translationBuilder.add("advancement.bakingdelight.vegetable_oil.desc", "Congratulations, you're not far from deep-frying food.");
        translationBuilder.add("advancement.bakingdelight.deep_fryer.title", "Ordeal (For Food)");
        translationBuilder.add("advancement.bakingdelight.deep_fryer.desc", "Right-click to add oil or insert and remove items. Sneak and right-click to check its status. It needs oil and a connected Gas Canister to run.");
        translationBuilder.add("advancement.bakingdelight.deep_fry_basket.title", "Watch out for hot oil");
        translationBuilder.add("advancement.bakingdelight.deep_fry_basket.desc", "Use tongs to retrieve food from the Deep Fryer. Hot oil is no joke!");
        translationBuilder.add("advancement.bakingdelight.all_fried.title", "Fry Everything! (Not Literally)");
        translationBuilder.add("advancement.bakingdelight.all_fried.desc", "Savor all the fried foods!");
        translationBuilder.add("advancement.bakingdelight.wither_rose_cake.title", "Bloom & Doom");
        translationBuilder.add("advancement.bakingdelight.wither_rose_cake.desc", "Eat the wither rose cake, then think about your life. (Hidden advancement)");
        translationBuilder.add("advancement.bakingdelight.bdc_bdi.title", "Biogas Industry");
        translationBuilder.add("advancement.bakingdelight.bdc_bdi.desc", "Craft a biogas digester controller and a biogas digester I/O port. Remember to read their instructions and build your biogas digester!");
        translationBuilder.add("advancement.bakingdelight.gas_canister.title", "Flammable and Explosive");
        translationBuilder.add("advancement.bakingdelight.gas_canister.desc", "It can either load gas from the biogas digester I/O port or supply gas to a gas cooking stove. Remember to be safe though!");
        translationBuilder.add("advancement.bakingdelight.gas_cooking_stove.title", "Now We're Cooking");
        translationBuilder.add("advancement.bakingdelight.gas_cooking_stove.desc", "A Gas Cooking Stove can heat all sorts of things, including you. Keep your distance!");
        translationBuilder.add("advancement.bakingdelight.spatula.title", "It's stir-fry time!");
        translationBuilder.add("advancement.bakingdelight.spatula.desc", "Heat up a baking tray on the burning gas cooking stove, then add ingredients to it and stir-fry with a spatula.");
        translationBuilder.add("advancement.bakingdelight.kuh.title", "Beautify the kitchen");
        translationBuilder.add("advancement.bakingdelight.kuh.desc", "Make a Kitchen Utensil Holder");
        translationBuilder.add("advancement.bakingdelight.cuisine_table.title", "Everything Has a Recipe");
        translationBuilder.add("advancement.bakingdelight.cuisine_table.desc", "Craft a Cuisine Table. Place a tool in the top slot and an item below to break many ingredients down.");
        translationBuilder.add("advancement.bakingdelight.electricians_desk.title", "Towards Modernization");
        translationBuilder.add("advancement.bakingdelight.electricians_desk.desc", "Make an electrician's desk, and it'll be with you for a long time to come.");
        translationBuilder.add("advancement.bakingdelight.tesla_coil.title", "Wireless Wonders");
        translationBuilder.add("advancement.bakingdelight.tesla_coil.desc", "Craft a Tesla Coil and send power through the air. Who needs wires?");
        translationBuilder.add("advancement.bakingdelight.wind.title", "The Wind is Passing by");
        translationBuilder.add("advancement.bakingdelight.wind.desc", "Make a fan blade and a wind turbine controller and start feeling the power from nature!");
        translationBuilder.add("advancement.bakingdelight.bamboo_steamer.title", "Dense Water Vapor");
        translationBuilder.add("advancement.bakingdelight.bamboo_steamer.desc", "Get ready to build a traditional steamer with a bamboo steamer basket drawer, bamboo steamer cover, and cauldron!");
        translationBuilder.add("advancement.bakingdelight.electric_steamer.title", "Faster and Stronger!");
        translationBuilder.add("advancement.bakingdelight.electric_steamer.desc", "An electric steamer allows you to get rid of the complexity of building a traditional steamer.");
        translationBuilder.add("advancement.bakingdelight.steamed_bun.title", "Just Like Mom's");
        translationBuilder.add("advancement.bakingdelight.steamed_bun.desc", "Steam some dough to make a steamed bun.");
        translationBuilder.add("advancement.bakingdelight.silicon_ingot.title", "A More Advanced Material");
        translationBuilder.add("advancement.bakingdelight.silicon_ingot.desc", "Smelt quartz in a blast furnace to obtain silicon ingots.");
        translationBuilder.add("advancement.bakingdelight.acdcc.title", "AC & DC");
        translationBuilder.add("advancement.bakingdelight.acdcc.desc", "Craft an AC/DC Converter to convert between the two power types, with some energy lost in the process.");
        translationBuilder.add("advancement.bakingdelight.battery1.title", "Introduction to Electricity Storage");
        translationBuilder.add("advancement.bakingdelight.battery1.desc", "Make a simple battery, which is the beginning of all power storage.");
        translationBuilder.add("advancement.bakingdelight.thermal_power_generation.title", "Industrial Revolution");
        translationBuilder.add("advancement.bakingdelight.thermal_power_generation.desc", "Craft a Stirling Engine and Faraday Generator. Mount the engine on a working furnace and connect its flywheel to the generator to produce power!");
        translationBuilder.add("advancement.bakingdelight.battery2.title", "Advanced Power Storage");
        translationBuilder.add("advancement.bakingdelight.battery2.desc", "Make an intermediate battery and you'll be able to store more power again!");
        translationBuilder.add("advancement.bakingdelight.battery3.title", "I need more electricity!");
        translationBuilder.add("advancement.bakingdelight.battery3.desc", "Craft an Advanced Battery to store a whole lot more power!");
        translationBuilder.add("advancement.bakingdelight.battery4.title", "Is There No End to This Power?");
        translationBuilder.add("advancement.bakingdelight.battery4.desc", "Craft a Dimensional Battery and put your power storage worries in another dimension.");
        translationBuilder.add("advancement.bakingdelight.pg.title", "Environmentally Friendly");
        translationBuilder.add("advancement.bakingdelight.pg.desc", "Make a photovoltaic generator that's portable and eco-friendly");
        translationBuilder.add("advancement.bakingdelight.ice_cream_maker.title", "Who can resist ice cream?");
        translationBuilder.add("advancement.bakingdelight.ice_cream_maker.desc", "Make an ice cream maker. When you take out the ice cream, you can definitely double your excitement!");
        translationBuilder.add("advancement.bakingdelight.ice_cream.title", "Sweet Victory!");
        translationBuilder.add("advancement.bakingdelight.ice_cream.desc", "Watch an ice cream cone come together!");
        translationBuilder.add("advancement.bakingdelight.garlic.title", "Garlic: Vampires Hate This One Trick");
        translationBuilder.add("advancement.bakingdelight.garlic.desc", "Find garlic in villagers' houses or in the forest.");
        translationBuilder.add("advancement.bakingdelight.kebabs.title", "Have a Kebab");
        translationBuilder.add("advancement.bakingdelight.kebabs.desc", "Grill a squid tentacle kebab. It's delicious and healthy.");
        translationBuilder.add("advancement.bakingdelight.fish_and_chips.title", "The most convenient take-out food");
        translationBuilder.add("advancement.bakingdelight.fish_and_chips.desc", "Make a portion of fish and chips, excellent!");
        translationBuilder.add("advancement.bakingdelight.all_ice_lolly.title", "Ice Lolly Expert");
        translationBuilder.add("advancement.bakingdelight.all_ice_lolly.desc", "Make all the Ice Lollies.");
        translationBuilder.add("advancement.bakingdelight.charging_post.title", "Greatest Invention");
        translationBuilder.add("advancement.bakingdelight.charging_post.desc", "Craft a Charging Post to recharge DC-powered appliances!");
        translationBuilder.add("advancement.bakingdelight.electric_whisk.title", "The Lazy Kitchen");
        translationBuilder.add("advancement.bakingdelight.electric_whisk.desc", "Regular whisk with glass bowl is too slow to mix manually? Try an electric whisk! It works directly on drops!");
        translationBuilder.add("advancement.bakingdelight.stone_mortar.title", "Crush it!");
        translationBuilder.add("advancement.bakingdelight.stone_mortar.desc", "Make a stone mortar which will help you crush some things.");
        translationBuilder.add("advancement.bakingdelight.juice_extractor.title", "Mix Evenly");
        translationBuilder.add("advancement.bakingdelight.juice_extractor.desc", "Make a juice extractor and make some drinks with it!");
        translationBuilder.add("advancement.bakingdelight.matcha.title", "Sweet Smell");
        translationBuilder.add("advancement.bakingdelight.matcha.desc", "Crushing leaves to obtain matcha.");
        translationBuilder.add("advancement.bakingdelight.fried_noodles.title", "Instant Noodles Moment");
        translationBuilder.add("advancement.bakingdelight.fried_noodles.desc", "Deep-fry some noodles, then combine them with a honeycomb-and-paper wrapper to make instant noodles.");
        translationBuilder.add("advancement.bakingdelight.cooked_portable_pot.title", "Convenience Is Relative");
        translationBuilder.add("advancement.bakingdelight.cooked_portable_pot.desc", "Add packaged instant noodles, quicklime, and water to a Portable Pot, then cook up a quick meal.");
        translationBuilder.add("advancement.bakingdelight.click_gas_cooking_stove.title", "The Gas Stove DJ");
        translationBuilder.add("advancement.bakingdelight.click_gas_cooking_stove.desc", "Rapidly right-click a Gas Cooking Stove 10 times within 3 seconds. (Hidden advancement)");


    }
}
