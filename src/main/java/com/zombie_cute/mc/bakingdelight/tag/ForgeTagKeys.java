package com.zombie_cute.mc.bakingdelight.tag;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ForgeTagKeys {
    // Create Mod
    public static final TagKey<Item> UPRIGHT_ON_BELT = createModItemTag("upright_on_belt");
    public static final TagKey<Block> PASSIVE_BOILER_HEATERS = createModBlockTag("passive_boiler_heaters");
    // Farmer's Delight Mod
    public static final TagKey<Block> HEAT_SOURCES = fdModBlockTag("heat_sources");
    // C
    public static final TagKey<Item> STORAGE_BLOCKS = forgeItemTag("storage_blocks");
    public static final TagKey<Item> INGOTS = forgeItemTag("ingots");
    public static final TagKey<Item> SILICON = forgeItemTag("silicon");
    public static final TagKey<Item> C_WHEAT_FLOUR = forgeItemTag("wheat_flour");
    public static final TagKey<Item> C_FLOUR = forgeItemTag("flour");
    public static final TagKey<Item> C_WHEAT_DOUGH = forgeItemTag("wheat_dough");
    public static final TagKey<Item> C_DOUGH = forgeItemTag("dough");
    public static final TagKey<Item> FOODS = forgeItemTag("foods");
        public static final TagKey<Item> COOKED_MEATS = forgeItemTag("foods/cooked_meats");
            public static final TagKey<Item> COOKED_PORK = forgeItemTag("foods/cooked_meats/cooked_pork");
        public static final TagKey<Item> RAW_MEATS = forgeItemTag("foods/raw_meats");
            public static final TagKey<Item> RAW_PORK = forgeItemTag("foods/raw_meats/raw_pork");
        public static final TagKey<Item> ICE_LOLLIES = forgeItemTag("foods/ice_lollies");
        public static final TagKey<Item> ICE_CREAMS = forgeItemTag("foods/ice_creams");
        public static final TagKey<Item> BUCKET_OIL = forgeItemTag("foods/bucket_of_oil");
        public static final TagKey<Item> BOTTLE_OIL = forgeItemTag("foods/bottle_of_oil");
        public static final TagKey<Item> FLOWER_CAKES = forgeItemTag("flower_cakes");
        public static final TagKey<Item> TRUFFLES = forgeItemTag("foods/truffles");
        public static final TagKey<Item> PUDDINGS = forgeItemTag("foods/puddings");
        public static final TagKey<Item> FLOURS = forgeItemTag("foods/flours");
        public static final TagKey<Item> DOUGHS = forgeItemTag("foods/doughs");
            public static final TagKey<Item> DOUGH_WHEAT = forgeItemTag("foods/doughs/wheat");
        public static final TagKey<Item> CREAMS = forgeItemTag("foods/creams");
        public static final TagKey<Item> MOUSSES = forgeItemTag("foods/mousses");
        public static final TagKey<Item> RAW_FISHES = forgeItemTag("foods/raw_fishes");
            public static final TagKey<Item> PRAWNS = forgeItemTag("foods/raw_fishes/prawns");
            public static final TagKey<Item> SQUIDS = forgeItemTag("foods/raw_fishes/squids");
        public static final TagKey<Item> CUTTLEBONES = forgeItemTag("foods/cuttlebones");
        public static final TagKey<Item> SAUSAGES = forgeItemTag("foods/sausages");
        public static final TagKey<Item> BREADS = forgeItemTag("foods/breads");
            public static final TagKey<Item> BREAD_WHEAT = forgeItemTag("foods/breads/wheat");
        public static final TagKey<Item> PIZZA_INGREDIENTS = forgeItemTag("foods/pizza_ingredients");
        public static final TagKey<Item> PUMPKINS = forgeItemTag("foods/pumpkins");
    public static final TagKey<Item> TOOLS = forgeItemTag("tools");
        public static final TagKey<Item> TOOLS_HOES = forgeItemTag("hoes");
        public static final TagKey<Item> TOOLS_SWORDS = forgeItemTag("swords");
        public static final TagKey<Item> TOOLS_SHOVELS = forgeItemTag("shovels");
        public static final TagKey<Item> TOOLS_PICKAXES = forgeItemTag("pickaxes");
        public static final TagKey<Item> TOOLS_AXES = forgeItemTag("axes");
        public static final TagKey<Item> TOOLS_KNIVES = forgeItemTag("tools/knives");
    public static final TagKey<Item> COLD_ITEMS = forgeItemTag("cold_items");
    public static final TagKey<Item> SEEDS = forgeItemTag("seeds");
        public static final TagKey<Item> SEED_BLACK_PEPPERS = forgeItemTag("seeds/black_peppers");
        public static final TagKey<Item> SEED_GARLIC = forgeItemTag("seeds/garlics");
    public static final TagKey<Item> CROPS = forgeItemTag("crops");
        public static final TagKey<Item> CROP_BLACK_PEPPER = forgeItemTag("crops/black_peppers");
        public static final TagKey<Item> CROP_GARLIC = forgeItemTag("crops/garlics");
        public static final TagKey<Item> CABBAGE = forgeItemTag("crops/cabbage");

    public static final TagKey<Item> MILKS = forgeItemTag("milks");
    private static TagKey<Block> forgeBlockTag(String path) {
        // Change namespace to 'c'. Porting Lib does this too.
        return TagKey.of(RegistryKeys.BLOCK, new Identifier("c", path));
    }
    private static TagKey<Item> forgeItemTag(String path) {
        // Change namespace to 'c'. Porting Lib does this too.
        return TagKey.of(RegistryKeys.ITEM, new Identifier("c", path));
    }
    private static TagKey<Item> createModItemTag(String path){
        return TagKey.of(RegistryKeys.ITEM, new Identifier("create", path));
    }
    private static TagKey<Block> createModBlockTag(String path){
        return TagKey.of(RegistryKeys.BLOCK, new Identifier("create", path));
    }
    private static TagKey<Block> fdModBlockTag(String path){
        return TagKey.of(RegistryKeys.BLOCK, new Identifier("farmersdelight", path));
    }
}
