package com.zombie_cute.mc.bakingdelight.tag;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
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
    public static final TagKey<Item> UPRIGHT_ON_BELT = addCreateItemTag("upright_on_belt");
    public static final TagKey<Block> PASSIVE_BOILER_HEATERS = addCreateBlockTag("passive_boiler_heaters");
    // Farmer's Delight
    public static final TagKey<Block> HEAT_SOURCES = addFarmersDelightBlockTag("heat_sources");
    public static final TagKey<Item> KNIVES = addFarmersDelightItemTag("tools/knives");
    // Common
    public static final TagKey<Item> STORAGE_BLOCKS = addCommonItemTag("storage_blocks");
    public static final TagKey<Item> INGOTS = addCommonItemTag("ingots");
    public static final TagKey<Item> SILICON = addCommonItemTag("silicon");
    public static final TagKey<Item> C_WHEAT_FLOUR = addCommonItemTag("wheat_flour");
    public static final TagKey<Item> C_FLOUR = addCommonItemTag("flour");
    public static final TagKey<Item> C_WHEAT_DOUGH = addCommonItemTag("wheat_dough");
    public static final TagKey<Item> C_DOUGH = addCommonItemTag("dough");
    public static final TagKey<Item> FOODS = addCommonItemTag("foods");
    public static final TagKey<Item> COOKED_MEATS = addCommonItemTag("foods/cooked_meats");
    public static final TagKey<Item> COOKED_PORK = addCommonItemTag("foods/cooked_meats/cooked_pork");
    public static final TagKey<Item> RAW_MEATS = addCommonItemTag("foods/raw_meats");
    public static final TagKey<Item> RAW_PORK = addCommonItemTag("foods/raw_meats/raw_pork");
    public static final TagKey<Item> ICE_LOLLIES = addCommonItemTag("foods/ice_lollies");
    public static final TagKey<Item> ICE_CREAMS = addCommonItemTag("foods/ice_creams");
    public static final TagKey<Item> BUCKET_OIL = addCommonItemTag("foods/bucket_of_oil");
    public static final TagKey<Item> BOTTLE_OIL = addCommonItemTag("foods/bottle_of_oil");
    public static final TagKey<Item> FLOWER_CAKES = addCommonItemTag("flower_cakes");
    public static final TagKey<Item> TRUFFLES = addCommonItemTag("foods/truffles");
    public static final TagKey<Item> PUDDINGS = addCommonItemTag("foods/puddings");
    public static final TagKey<Item> FLOURS = addCommonItemTag("foods/flours");
    public static final TagKey<Item> DOUGHS = addCommonItemTag("foods/doughs");
    public static final TagKey<Item> DOUGH_WHEAT = addCommonItemTag("foods/doughs/wheat");
    public static final TagKey<Item> CREAMS = addCommonItemTag("foods/creams");
    public static final TagKey<Item> MOUSSES = addCommonItemTag("foods/mousses");
    public static final TagKey<Item> RAW_FISHES = addCommonItemTag("foods/raw_fishes");
    public static final TagKey<Item> PRAWNS = addCommonItemTag("foods/raw_fishes/prawns");
    public static final TagKey<Item> SQUIDS = addCommonItemTag("foods/raw_fishes/squids");
    public static final TagKey<Item> CUTTLEBONES = addCommonItemTag("foods/cuttlebones");
    public static final TagKey<Item> SAUSAGES = addCommonItemTag("foods/sausages");
    public static final TagKey<Item> BREADS = addCommonItemTag("foods/breads");
    public static final TagKey<Item> BREAD_WHEAT = addCommonItemTag("foods/breads/wheat");
    public static final TagKey<Item> PIZZA_INGREDIENTS = addCommonItemTag("foods/pizza_ingredients");
    public static final TagKey<Item> PUMPKINS = addCommonItemTag("foods/pumpkins");
    public static final TagKey<Item> TOOLS = addCommonItemTag("tools");
    public static final TagKey<Item> TOOLS_MINING_TOOLS = addCommonItemTag("tools/mining_tools");
    public static final TagKey<Item> TOOLS_MELEE_WEAPONS = addCommonItemTag("tools/melee_weapons");
    public static final TagKey<Item> TOOLS_KNIVES = addCommonItemTag("tools/knife");
    public static final TagKey<Item> COLD_ITEMS = addCommonItemTag("cold_items");
    public static final TagKey<Item> SEEDS = addCommonItemTag("seeds");
    public static final TagKey<Item> SEED_BLACK_PEPPERS = addCommonItemTag("seeds/black_peppers");
    public static final TagKey<Item> SEED_GARLIC = addCommonItemTag("seeds/garlics");
    public static final TagKey<Item> CROPS = addCommonItemTag("crops");
    public static final TagKey<Item> CROP_BLACK_PEPPER = addCommonItemTag("crops/black_peppers");
    public static final TagKey<Item> CROP_GARLIC = addCommonItemTag("crops/garlics");
    public static final TagKey<Item> CABBAGE = addCommonItemTag("crops/cabbage");
    public static final TagKey<Item> MILKS = addCommonItemTag("foods/milk");
    public static final TagKey<Item> WHISKS = addCommonItemTag("tools/whisk");
    public static final TagKey<Item> ROLLING_PINS = addCommonItemTag("tools/rolling_pins");
    public static final TagKey<Item> CROWBARS = addCommonItemTag("tools/crowbar");
    public static final TagKey<Item> SPATULAS = addCommonItemTag("tools/spatula");
    public static final TagKey<Item> FILTERS = addCommonItemTag("tools/filter");

    // ModernDelight
    public static final TagKey<Item> AMETHYST_TOOLS = addModernDelightTag("amethyst_tools", RegistryKeys.ITEM);
    public static final TagKey<Item> FLAT_ON_BAKING_TRAY = addModernDelightTag("flat_on_baking_tray", RegistryKeys.ITEM);
    public static final TagKey<Block> CROWBAR_DESTROYABLE = addModernDelightTag("crowbar_destroyable", RegistryKeys.BLOCK);
    public static final TagKey<Block> DANGER_BLOCKS = addModernDelightTag("danger_blocks",RegistryKeys.BLOCK);
    public static final TagKey<Item> OIL_PLANTS = addModernDelightTag("oil_plants",RegistryKeys.ITEM);
    public static final TagKey<Fluid> OIL = addModernDelightTag("oil",RegistryKeys.FLUID);


    private static <E> TagKey<E> addModernDelightTag(String pathName, RegistryKey<? extends Registry<E>> registry) {
        return TagKey.of(registry, Identifier.of(Bakingdelight.MOD_ID, pathName));
    }
    private static TagKey<Block> addCommonBlockTag(String path) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", path));
    }
    private static TagKey<Item> addCommonItemTag(String path) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", path));
    }
    private static TagKey<Item> addCreateItemTag(String path){
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("create", path));
    }
    private static TagKey<Block> addCreateBlockTag(String path){
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of("create", path));
    }
    private static TagKey<Block> addFarmersDelightBlockTag(String path){
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of("farmersdelight", path));
    }
    private static TagKey<Item> addFarmersDelightItemTag(String path){
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("farmersdelight", path));
    }
}
