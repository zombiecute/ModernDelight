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
    // Create
    public static final TagKey<Item> UPRIGHT_ON_BELT = create("upright_on_belt",RegistryKeys.ITEM,"create");
    public static final TagKey<Item> BLAZE_BURNER_FUEL = create("blaze_burner_fuel/regular",RegistryKeys.ITEM,"create");
    public static final TagKey<Block> PASSIVE_BOILER_HEATERS = create("passive_boiler_heaters",RegistryKeys.BLOCK,"create");
    public static final TagKey<Block> WRENCH_PICKUP = create("wrench_pickup",RegistryKeys.BLOCK,"create");
    // Farmer's Delight
    public static final TagKey<Block> HEAT_SOURCES = create("heat_sources",RegistryKeys.BLOCK,"farmersdelight");
    // Common
    public static final TagKey<Item> NETHERITE_SCRAP_NUGGETS = commonItemTag("nuggets/netherite_scrap");
    public static final TagKey<Item> NETHERITE_SCRAP_ORES = commonItemTag("ores/netherite_scrap");
    public static final TagKey<Item> MUSHROOMS = commonItemTag("mushrooms");
    public static final TagKey<Item> RICE = commonItemTag("crops/rice");
    public static final TagKey<Item> TOMATO = commonItemTag("foods/tomato");
    public static final TagKey<Fluid> MILK = create("drinks/milk",RegistryKeys.FLUID,"c");
    public static final TagKey<Item> PASTAS = commonItemTag("foods/pasta");
    public static final TagKey<Item> IRON_PLATE = commonItemTag("plates/iron");
    public static final TagKey<Item> COPPER_PLATE = commonItemTag("plates/copper");
    public static final TagKey<Item> ZINC_INGOT = commonItemTag("ingots/zinc");
    public static final TagKey<Item> ONION = commonItemTag("foods/onion");

    public static final TagKey<Item> STORAGE_BLOCKS = commonItemTag("storage_blocks");
    public static final TagKey<Item> STORAGE_BLOCKS_SILICON = commonItemTag("storage_blocks/silicon");
    public static final TagKey<Item> INGOTS = commonItemTag("ingots");
    public static final TagKey<Item> SILICON = commonItemTag("ingots/silicon");
    public static final TagKey<Item> FOODS = commonItemTag("foods");
        public static final TagKey<Item> FRUITS = commonItemTag("foods/fruits");
            public static final TagKey<Item> MANGO = commonItemTag("foods/fruits/mango");
            public static final TagKey<Item> LEMON = commonItemTag("foods/fruits/lemon");
        public static final TagKey<Item> JUICE = commonItemTag("foods/juice");
        public static final TagKey<Item> COOKED_MEATS = commonItemTag("foods/cooked_meats");
            public static final TagKey<Item> COOKED_PORK = commonItemTag("foods/cooked_meats/cooked_pork");
        public static final TagKey<Item> RAW_MEATS = commonItemTag("foods/raw_meat");
            public static final TagKey<Item> RAW_PORK = commonItemTag("foods/raw_pork");
            public static final TagKey<Item> RAW_BEEF = commonItemTag("foods/raw_beef");
            public static final TagKey<Item> RAW_CHICKEN = commonItemTag("foods/raw_chicken");
            public static final TagKey<Item> RAW_MUTTON = commonItemTag("foods/raw_mutton");
        public static final TagKey<Item> ICE_LOLLIES = commonItemTag("foods/ice_lollies");
        public static final TagKey<Item> ICE_CREAMS = commonItemTag("foods/ice_creams");
        public static final TagKey<Item> BOTTLE_VEGETABLE_OIL = commonItemTag("foods/bottle_of_vegetable_oil");
        public static final TagKey<Item> FLOWER_CAKES = commonItemTag("flower_cakes");
        public static final TagKey<Item> TRUFFLES = commonItemTag("foods/truffles");
        public static final TagKey<Item> PUDDINGS = commonItemTag("foods/puddings");
        public static final TagKey<Item> FLOURS = commonItemTag("foods/flour");
        public static final TagKey<Item> DOUGHS = commonItemTag("foods/dough");
            public static final TagKey<Item> DOUGH_WHEAT = commonItemTag("foods/dough/wheat");
        public static final TagKey<Item> CREAMS = commonItemTag("foods/creams");
        public static final TagKey<Item> CREAMS_WITHOUT_PLAIN = commonItemTag("foods/creams_without_plain");
        public static final TagKey<Item> MOUSSES = commonItemTag("foods/mousses");
        public static final TagKey<Item> RAW_FISHES = commonItemTag("foods/raw_fish");
            public static final TagKey<Item> PRAWNS = commonItemTag("foods/raw_fish/prawns");
            public static final TagKey<Item> SQUIDS = commonItemTag("foods/raw_fish/squids");
        public static final TagKey<Item> CUTTLEBONES = commonItemTag("foods/cuttlebones");
        public static final TagKey<Item> SAUSAGES = commonItemTag("foods/sausages");
        public static final TagKey<Item> BREADS = commonItemTag("foods/breads");
            public static final TagKey<Item> BREAD_WHEAT = commonItemTag("foods/breads/wheat");
        public static final TagKey<Item> PIZZA_INGREDIENTS = commonItemTag("foods/pizza_ingredients");
        public static final TagKey<Item> PUMPKINS = commonItemTag("foods/pumpkins");
    public static final TagKey<Item> TOOLS = commonItemTag("tools");
        public static final TagKey<Item> TOOLS_KNIVES = commonItemTag("tools/knife");
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
    public static final TagKey<Item> FLAT_ON_BAKING_TRAY = create("flat_on_baking_tray", RegistryKeys.ITEM);
    public static final TagKey<Item> CROWBARS = create("crowbars",RegistryKeys.ITEM);
    public static final TagKey<Item> SPATULAS = create("spatulas",RegistryKeys.ITEM);
    public static final TagKey<Item> OIL_PLANTS = create("oil_plants",RegistryKeys.ITEM);
    public static final TagKey<Item> FILTERS = create("filters",RegistryKeys.ITEM);
    public static final TagKey<Item> CONDIMENTS = create("condiments",RegistryKeys.ITEM);
    public static final TagKey<Item> INKS = create("inks",RegistryKeys.ITEM);
    public static final TagKey<Item> MORTARS = create("mortars",RegistryKeys.ITEM);

    public static final TagKey<Block> CAN_PICK =create("can_pick",RegistryKeys.BLOCK);
    public static final TagKey<Block> CROWBAR_DESTROYABLE = create("crowbar_destroyable", RegistryKeys.BLOCK);
    public static final TagKey<Block> WHISK_MINEABLE = create("whisk_mineable", RegistryKeys.BLOCK);
    public static final TagKey<Block> DANGER_BLOCKS =create("danger_blocks",RegistryKeys.BLOCK);

    public static final TagKey<Fluid> OIL = create("oil",RegistryKeys.FLUID);
    public static final TagKey<Fluid> CREAM = create("cream",RegistryKeys.FLUID);
    public static final TagKey<Fluid> GAS = create("gas",RegistryKeys.FLUID);


    private static TagKey<Item> commonItemTag(String path) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", path));
    }
    public static <E> TagKey<E> create(String pathName, RegistryKey<? extends Registry<E>> registry) {
        return TagKey.of(registry, Identifier.of(ModernDelightMain.MOD_ID, pathName));
    }
    public static <E> TagKey<E> create(String pathName, RegistryKey<? extends Registry<E>> registry, String mod_id) {
        return TagKey.of(registry, Identifier.of(mod_id, pathName));
    }
}
