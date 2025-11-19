package com.zombie_cute.mc.bakingdelight.tag;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagKeys {
    // Anvil Craft
    public static final TagKey<Item> ROYAL_STEEL_SWORD_BASE = create("royal_steel_sword_base",RegistryKeys.ITEM,"anvilcraft");
    public static final TagKey<Item> ROYAL_STEEL_PICKAXE_BASE = create("royal_steel_pickaxe_base",RegistryKeys.ITEM,"anvilcraft");
    public static final TagKey<Item> ROYAL_STEEL_AXE_BASE = create("royal_steel_axe_base",RegistryKeys.ITEM,"anvilcraft");
    public static final TagKey<Item> ROYAL_STEEL_SHOVEL_BASE = create("royal_steel_shovel_base",RegistryKeys.ITEM,"anvilcraft");
    public static final TagKey<Item> ROYAL_STEEL_HOE_BASE = create("royal_steel_hoe_base",RegistryKeys.ITEM,"anvilcraft");
    public static final TagKey<Item> AMETHYST_SWORD = create("amethyst_sword",RegistryKeys.ITEM,"anvilcraft");
    public static final TagKey<Item> AMETHYST_PICKAXE = create("amethyst_pickaxe",RegistryKeys.ITEM,"anvilcraft");
    public static final TagKey<Item> AMETHYST_AXE = create("amethyst_axe",RegistryKeys.ITEM,"anvilcraft");
    public static final TagKey<Item> AMETHYST_SHOVEL = create("amethyst_shovel",RegistryKeys.ITEM,"anvilcraft");
    public static final TagKey<Item> AMETHYST_HOE = create("amethyst_hoe",RegistryKeys.ITEM,"anvilcraft");
    // Create
    public static final TagKey<Item> UPRIGHT_ON_BELT = create("upright_on_belt",RegistryKeys.ITEM,"create");
    public static final TagKey<Item> BLAZE_BURNER_FUEL = create("blaze_burner_fuel/regular",RegistryKeys.ITEM,"create");
    public static final TagKey<Block> PASSIVE_BOILER_HEATERS = create("passive_boiler_heaters",RegistryKeys.BLOCK,"create");
    public static final TagKey<Block> WRENCH_PICKUP = create("wrench_pickup",RegistryKeys.BLOCK,"create");
    // Farmer's Delight
    public static final TagKey<Block> HEAT_SOURCES = create("heat_sources",RegistryKeys.BLOCK,"farmersdelight");
    // Common
    public static final TagKey<Item> NETHERITE_SCRAP_NUGGETS = commonItemTag("netherite_scrap_nuggets");
    public static final TagKey<Item> NETHERITE_SCRAP_ORES = commonItemTag("netherite_scrap_ores");
    public static final TagKey<Item> MUSHROOMS = commonItemTag("mushrooms");
    public static final TagKey<Item> RICE = commonItemTag("crops/rice");
    public static final TagKey<Item> TOMATO = commonItemTag("vegetables/tomato");
    public static final TagKey<Item> TOMATOES = commonItemTag("foods/vegetables/tomatoes");
    public static final TagKey<Fluid> MILK = create("milk",RegistryKeys.FLUID,"c");
    public static final TagKey<Item> PASTAS = commonItemTag("foods/pastas");
    public static final TagKey<Item> IRON_PLATE = commonItemTag("iron_plates");
    public static final TagKey<Item> COPPER_PLATE = commonItemTag("copper_plates");
    public static final TagKey<Item> ZINC_INGOT = commonItemTag("zinc_ingots");
    public static final TagKey<Item> ONION = commonItemTag("foods/vegetables/onion");

    public static final TagKey<Item> STORAGE_BLOCKS = commonItemTag("storage_blocks");
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
        public static final TagKey<Item> JUICE = commonItemTag("foods/juice");
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
        public static final TagKey<Item> TRUFFLES = commonItemTag("foods/truffles");
        public static final TagKey<Item> PUDDINGS = commonItemTag("foods/puddings");
        public static final TagKey<Item> FLOURS = commonItemTag("foods/flours");
        public static final TagKey<Item> DOUGHS = commonItemTag("foods/doughs");
            public static final TagKey<Item> DOUGH_WHEAT = commonItemTag("foods/doughs/wheat");
        public static final TagKey<Item> CREAMS = commonItemTag("foods/creams");
        public static final TagKey<Item> CREAMS_WITHOUT_PLAIN = commonItemTag("foods/creams_without_plain");
        public static final TagKey<Item> MOUSSES = commonItemTag("foods/mousses");
        public static final TagKey<Item> RAW_FISHES = commonItemTag("foods/raw_fishes");
            public static final TagKey<Item> PRAWNS = commonItemTag("foods/raw_fishes/prawns");
            public static final TagKey<Item> SQUIDS = commonItemTag("foods/raw_fishes/squids");
        public static final TagKey<Item> CUTTLEBONES = commonItemTag("foods/cuttlebones");
        public static final TagKey<Item> SAUSAGES = commonItemTag("foods/sausages");
        public static final TagKey<Item> BREADS = commonItemTag("foods/breads");
            public static final TagKey<Item> BREAD_WHEAT = commonItemTag("foods/breads/wheat");
        public static final TagKey<Item> PIZZA_INGREDIENTS = commonItemTag("foods/pizza_ingredients");
        public static final TagKey<Item> PUMPKINS = commonItemTag("foods/pumpkins");
    public static final TagKey<Item> TOOLS = commonItemTag("tools");
        public static final TagKey<Item> TOOLS_HOES = commonItemTag("hoes");
        public static final TagKey<Item> TOOLS_SWORDS = commonItemTag("swords");
        public static final TagKey<Item> TOOLS_SHOVELS = commonItemTag("shovels");
        public static final TagKey<Item> TOOLS_PICKAXES = commonItemTag("pickaxes");
        public static final TagKey<Item> TOOLS_AXES = commonItemTag("axes");
        public static final TagKey<Item> TOOLS_KNIVES = commonItemTag("tools/knives");
    public static final TagKey<Item> COLD_ITEMS = commonItemTag("cold_items");
    public static final TagKey<Item> SEEDS = commonItemTag("seeds");
        public static final TagKey<Item> SEED_BLACK_PEPPERS = commonItemTag("seeds/black_peppers");
        public static final TagKey<Item> SEED_GARLIC = commonItemTag("seeds/garlics");
    public static final TagKey<Item> CROPS = commonItemTag("crops");
        public static final TagKey<Item> CROP_BLACK_PEPPER = commonItemTag("crops/black_peppers");
        public static final TagKey<Item> CROP_GARLIC = commonItemTag("crops/garlics");
        public static final TagKey<Item> CABBAGE = commonItemTag("crops/cabbage");

    public static final TagKey<Item> CARBONATE_ROCKS = commonItemTag("carbonate_rocks");
    public static final TagKey<Item> QUICKLIMES = commonItemTag("quicklimes");
    public static final TagKey<Item> MILKS = commonItemTag("milks");
    // Mod
    public static final TagKey<Item> WHISKS = create("whisks", RegistryKeys.ITEM);
    public static final TagKey<Item> AMETHYST_TOOLS = create("amethyst_tools", RegistryKeys.ITEM);
    public static final TagKey<Item> KNEADING_STICKS = create("kneading_sticks", RegistryKeys.ITEM);
    public static final TagKey<Block> WHISK_MINEABLE = create("whisk_mineable", RegistryKeys.BLOCK);
    public static final TagKey<Item> FLAT_ON_BAKING_TRAY = create("flat_on_baking_tray", RegistryKeys.ITEM);
    public static final TagKey<Block> CROWBAR_DESTROYABLE = create("crowbar_destroyable", RegistryKeys.BLOCK);
    public static final TagKey<Item> CROWBARS = create("crowbars",RegistryKeys.ITEM);
    public static final TagKey<Item> SPATULAS = create("spatulas",RegistryKeys.ITEM);
    public static final TagKey<Block> DANGER_BLOCKS =create("danger_blocks",RegistryKeys.BLOCK);
    public static final TagKey<Item> OIL_PLANTS = create("oil_plants",RegistryKeys.ITEM);
    public static final TagKey<Item> FILTERS = create("filters",RegistryKeys.ITEM);
    public static final TagKey<Item> CONDIMENTS = create("condiments",RegistryKeys.ITEM);
    public static final TagKey<Item> INKS = create("inks",RegistryKeys.ITEM);

    public static final TagKey<Fluid> OIL = create("oil",RegistryKeys.FLUID);
    public static final TagKey<Fluid> CREAM = create("cream",RegistryKeys.FLUID);
    public static final TagKey<Fluid> GAS = create("gas",RegistryKeys.FLUID);


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
