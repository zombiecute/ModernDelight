package com.zombie_cute.mc.moderndelight.tag;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagKeys {
    // Create
    public static final TagKey<Item> UPRIGHT_ON_BELT = create("upright_on_belt",RegistryKeys.ITEM,"create");
    public static final TagKey<Item> BLAZE_BURNER_FUEL = create("blaze_burner_fuel/regular",RegistryKeys.ITEM,"create");
    public static final TagKey<Block> PASSIVE_BOILER_HEATERS = create("passive_boiler_heaters",RegistryKeys.BLOCK,"create");
    public static final TagKey<Block> WRENCH_PICKUP = create("wrench_pickup",RegistryKeys.BLOCK,"create");
    // Farmer's Delight
    public static final TagKey<Block> HEAT_SOURCES = create("heat_sources",RegistryKeys.BLOCK,"farmersdelight");
    public static final TagKey<Block> WILD_CROPS = create("wild_crops",RegistryKeys.BLOCK,"farmersdelight");
    // Common
    public static final TagKey<Item> NETHERITE_SCRAP_NUGGETS = commonItemTag("netherite_scrap_nuggets");
    public static final TagKey<Item> NETHERITE_SCRAP_ORES = commonItemTag("netherite_scrap_ores");
    public static final TagKey<Item> MUSHROOMS = commonItemTag("mushrooms");
    public static final TagKey<Item> RICE = commonItemTag("crops/rice");
    public static final TagKey<Fluid> MILK = create("milk",RegistryKeys.FLUID,"c");
    public static final TagKey<Item> PASTAS = commonItemTag("foods/pastas");
    public static final TagKey<Item> RAW_PASTAS = commonItemTag("foods/pastas/raw_pastas");
    public static final TagKey<Item> IRON_PLATE = commonItemTag("iron_plates");
    public static final TagKey<Item> COPPER_PLATE = commonItemTag("copper_plates");
    public static final TagKey<Item> ZINC_INGOT = commonItemTag("zinc_ingots");

    public static final TagKey<Item> STORAGE_BLOCKS_ITEM = commonItemTag("storage_blocks");
    public static final TagKey<Item> SILICON_STORGE_BLOCK_ITEM = commonItemTag("storage_blocks/silicon");
    public static final TagKey<Item> CHERRY_STORGE_BLOCK_ITEM = commonItemTag("storage_blocks/cherry");

    public static final TagKey<Block> STORAGE_BLOCKS_BLOCK = create("storage_blocks",RegistryKeys.BLOCK,"c");
    public static final TagKey<Block> SILICON_STORGE_BLOCK_BLOCK = create("storage_blocks/silicon",RegistryKeys.BLOCK,"c");
    public static final TagKey<Block> CHERRY_STORGE_BLOCK_BLOCK = create("storage_blocks/cherry",RegistryKeys.BLOCK,"c");

    public static final TagKey<Item> INGOTS = commonItemTag("ingots");
    public static final TagKey<Item> SILICON = commonItemTag("silicon");
    public static final TagKey<Item> C_WHEAT_FLOUR = commonItemTag("wheat_flour");
    public static final TagKey<Item> C_FLOUR = commonItemTag("flour");
    public static final TagKey<Item> C_WHEAT_DOUGH = commonItemTag("wheat_dough");
    public static final TagKey<Item> C_DOUGH = commonItemTag("dough");

    public static final TagKey<Item> FOODS = commonItemTag("foods");
        public static final TagKey<Item> FRUITS = commonItemTag("foods/fruits");
            public static final TagKey<Item> MANGO = commonItemTag("foods/fruits/mango");
            public static final TagKey<Item> LEMON = commonItemTag("foods/fruits/lemon");
            public static final TagKey<Item> APPLE = commonItemTag("foods/fruits/apple");
            public static final TagKey<Item> ORANGE = commonItemTag("foods/fruits/orange");
            public static final TagKey<Item> PEAR = commonItemTag("foods/fruits/pear");
            public static final TagKey<Item> CHERRY = commonItemTag("foods/fruits/cherry");

        public static final TagKey<Item> JUICE = commonItemTag("foods/juices");
        public static final TagKey<Item> COOKED_MEATS = commonItemTag("foods/cooked_meats");
            public static final TagKey<Item> COOKED_PORK = commonItemTag("foods/cooked_meats/cooked_pork");
        public static final TagKey<Item> RAW_MEATS = commonItemTag("foods/raw_meats");
            public static final TagKey<Item> RAW_PORK = commonItemTag("foods/raw_meats/raw_pork");
            public static final TagKey<Item> RAW_BEEF = commonItemTag("foods/raw_meats/raw_beef");
            public static final TagKey<Item> RAW_CHICKEN = commonItemTag("foods/raw_meats/raw_chicken");
            public static final TagKey<Item> RAW_MUTTON = commonItemTag("foods/raw_meats/raw_mutton");
        public static final TagKey<Item> ICE_LOLLIES = commonItemTag("foods/ice_lollies");
        public static final TagKey<Item> ICE_CREAMS = commonItemTag("foods/ice_creams");
        public static final TagKey<Item> BOTTLE_VEGETABLE_OIL = commonItemTag("foods/bottle_of_vegetable_oil");
        public static final TagKey<Item> FLOWER_CAKES = commonItemTag("flower_cakes");
        public static final TagKey<Item> TRUFFLES = commonItemTag("mushrooms/truffle");
        public static final TagKey<Item> PUDDINGS = commonItemTag("foods/puddings");
        public static final TagKey<Item> DOUGHS = commonItemTag("foods/doughs");
            public static final TagKey<Item> DOUGH_WHEAT = commonItemTag("foods/doughs/wheat");
        public static final TagKey<Item> CREAMS = commonItemTag("foods/creams");
        public static final TagKey<Item> CREAMS_WITHOUT_PLAIN = commonItemTag("foods/creams_without_plain");
        public static final TagKey<Item> MOUSSES = commonItemTag("foods/mousses");
        public static final TagKey<Item> RAW_FISHES = commonItemTag("foods/raw_fishes");
            public static final TagKey<Item> PRAWNS = commonItemTag("foods/raw_fishes/prawn");
            public static final TagKey<Item> SQUIDS = commonItemTag("foods/raw_fishes/squid");
            public static final TagKey<Item> CRABS = commonItemTag("foods/raw_fishes/crab");
        public static final TagKey<Item> COOKED_FISHES = commonItemTag("foods/cooked_fishes");
            public static final TagKey<Item> COOKED_PRAWNS = commonItemTag("foods/cooked_fishes/prawn");
            public static final TagKey<Item> COOKED_SQUIDS = commonItemTag("foods/cooked_fishes/squid");
            public static final TagKey<Item> COOKED_CRABS = commonItemTag("foods/cooked_fishes/crab");
        public static final TagKey<Item> CUTTLEBONES = commonItemTag("foods/cuttlebones");
        public static final TagKey<Item> SAUSAGES = commonItemTag("foods/sausage");
        public static final TagKey<Item> BREADS = commonItemTag("foods/breads");
            public static final TagKey<Item> BREAD_WHEAT = commonItemTag("foods/breads/wheat");
        public static final TagKey<Item> PUMPKINS = commonItemTag("foods/pumpkins");
    public static final TagKey<Item> TOOLS = commonItemTag("tools");
        public static final TagKey<Item> TOOLS_KNIVES = commonItemTag("tools/knives");
    public static final TagKey<Item> SEEDS = commonItemTag("seeds");
        public static final TagKey<Item> SEED_BLACK_PEPPERS = commonItemTag("seeds/black_pepper");
        public static final TagKey<Item> SEED_CHILI_PEPPER = commonItemTag("seeds/chili_pepper");
    public static final TagKey<Item> CROPS = commonItemTag("crops");
        public static final TagKey<Item> CROP_BLACK_PEPPER = commonItemTag("crops/black_pepper");
        public static final TagKey<Item> CROP_GARLIC = commonItemTag("crops/garlic");
        public static final TagKey<Item> CROP_CABBAGE = commonItemTag("crops/cabbage");
        public static final TagKey<Item> CROP_CHILI_PEPPER = commonItemTag("crops/chili_pepper");
        public static final TagKey<Item> CROP_SOYBEAN = commonItemTag("crops/soybean");

    public static final TagKey<Item> CARBONATE_ROCKS = commonItemTag("carbonate_rocks");
    public static final TagKey<Item> QUICKLIMES = commonItemTag("quicklimes");
    public static final TagKey<Item> MILKS = commonItemTag("milks");
    // Mod
    public static final TagKey<Item> WHISKS = create("whisks", RegistryKeys.ITEM);
    public static final TagKey<Item> AMETHYST_TOOLS = create("amethyst_tools", RegistryKeys.ITEM);
    public static final TagKey<Item> KNEADING_STICKS = create("kneading_sticks", RegistryKeys.ITEM);
    public static final TagKey<Item> FLAT_ON_BAKING_TRAY = create("flat_on_baking_tray", RegistryKeys.ITEM);
    public static final TagKey<Item> CROWBARS = create("crowbars",RegistryKeys.ITEM);
    public static final TagKey<Item> SPATULAS = create("spatulas",RegistryKeys.ITEM);
    public static final TagKey<Item> OIL_PLANTS = create("oil_plants",RegistryKeys.ITEM);
    public static final TagKey<Item> FILTERS = create("filters",RegistryKeys.ITEM);
    public static final TagKey<Item> CONDIMENTS = create("condiments",RegistryKeys.ITEM);
    public static final TagKey<Item> INKS = create("inks",RegistryKeys.ITEM);
    public static final TagKey<Item> COLD_ITEMS = create("cold_items", RegistryKeys.ITEM);
    public static final TagKey<Item> PIZZA_INGREDIENTS = create("pizza_ingredients",RegistryKeys.ITEM);
    public static final TagKey<Item> UMAMI = create("umami",RegistryKeys.ITEM);
    public static final TagKey<Item> DAIRY = create("dairy",RegistryKeys.ITEM);
    public static final TagKey<Item> WARM = create("warm",RegistryKeys.ITEM);
    public static final TagKey<Item> FRIED = create("fried",RegistryKeys.ITEM);
    public static final TagKey<Item> CITRUS = create("citrus",RegistryKeys.ITEM);
    public static final TagKey<Item> FERMENTED = create("fermented",RegistryKeys.ITEM);
    public static final TagKey<Item> SPICY = create("spicy",RegistryKeys.ITEM);
    public static final TagKey<Item> COOLS_MOUTH = create("cools_mouth",RegistryKeys.ITEM);
    public static final TagKey<Item> NO_FOOD_COMA = create("no_food_coma",RegistryKeys.ITEM);

    public static final TagKey<Fluid> OIL = create("oil",RegistryKeys.FLUID);
    public static final TagKey<Fluid> CREAM = create("cream",RegistryKeys.FLUID);
    public static final TagKey<Fluid> GAS = create("gas",RegistryKeys.FLUID);
    
    public static final TagKey<Block> CAN_PICK =create("can_pick",RegistryKeys.BLOCK);
    public static final TagKey<Block> WHISK_MINEABLE = create("whisk_mineable", RegistryKeys.BLOCK);
    public static final TagKey<Block> CROWBAR_DESTROYABLE = create("crowbar_destroyable", RegistryKeys.BLOCK);
    public static final TagKey<Block> DANGER_BLOCKS = create("danger_blocks",RegistryKeys.BLOCK);
    public static final TagKey<Block> TEMPERATURE_0 = create("temperature_0",RegistryKeys.BLOCK);
    public static final TagKey<Block> TEMPERATURE_MINUS_10 = create("temperature_minus_10",RegistryKeys.BLOCK);
    public static final TagKey<Block> TEMPERATURE_MINUS_20 = create("temperature_minus_20",RegistryKeys.BLOCK);
    public static final TagKey<Block> TEMPERATURE_300 = create("temperature_300",RegistryKeys.BLOCK);
    public static final TagKey<Block> TEMPERATURE_600 = create("temperature_600",RegistryKeys.BLOCK);
    public static final TagKey<Block> TEMPERATURE_100 = create("temperature_100",RegistryKeys.BLOCK);
    public static final TagKey<Block> TEMPERATURE_1000 = create("temperature_1000",RegistryKeys.BLOCK);
    public static final TagKey<Block> TEMPERATURE_80 = create("temperature_80",RegistryKeys.BLOCK);
    public static final TagKey<Block> TEMPERATURE_60 = create("temperature_60",RegistryKeys.BLOCK);
    public static final TagKey<Block> TEMPERATURE_40 = create("temperature_40",RegistryKeys.BLOCK);


    private static TagKey<Item> commonItemTag(String path) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("c", path));
    }
    public static <E> TagKey<E> create(String pathName, RegistryKey<? extends Registry<E>> registry) {
        return TagKey.of(registry, new Identifier(ModernDelightMain.MOD_ID, pathName));
    }
    public static <E> TagKey<E> create(String pathName, RegistryKey<? extends Registry<E>> registry, String mod_id) {
        return TagKey.of(registry, new Identifier(mod_id, pathName));
    }
}
