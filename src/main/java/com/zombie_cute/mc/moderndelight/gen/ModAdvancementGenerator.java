package com.zombie_cute.mc.moderndelight.gen;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import com.zombie_cute.mc.moderndelight.advancement.ModCriteria;
import com.zombie_cute.mc.moderndelight.advancement.criterion.FastStoveClickCriterion;
import com.zombie_cute.mc.moderndelight.advancement.criterion.FermentCriterion;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.fluid.ModFluid;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModAdvancementGenerator extends FabricAdvancementProvider {

    public ModAdvancementGenerator(FabricDataOutput output) {
        super(output);
    }

    private static Advancement.Builder advancement(Advancement parent, ItemConvertible icon,
                                                   String id, AdvancementFrame frame) {
        return Advancement.Builder.create()
                .parent(parent)
                .display(
                        icon,
                        Text.translatable("advancement.moderndelight." + id + ".title"),
                        Text.translatable("advancement.moderndelight." + id + ".desc"),
                        null,
                        frame,
                        true,
                        true,
                        false
                );
    }

    private static Advancement.Builder hiddenAdvancement(Advancement parent, ItemConvertible icon,
                                                         String id, AdvancementFrame frame) {
        return Advancement.Builder.create()
                .parent(parent)
                .display(
                        icon,
                        Text.translatable("advancement.moderndelight." + id + ".title"),
                        Text.translatable("advancement.moderndelight." + id + ".desc"),
                        null,
                        frame,
                        true,
                        true,
                        true
                );
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        // 根节点
        Advancement getStart = Advancement.Builder.create()
                .display(
                        ModBlocks.FREEZER,
                        Text.translatable("itemgroup.moderndelight"),
                        Text.translatable("advancement.moderndelight.get_start_desc"),
                        new Identifier("textures/block/beehive_end.png"),
                        AdvancementFrame.TASK,
                        false,
                        false,
                        false
                )
                .criterion("iron_whisk", InventoryChangedCriterion.Conditions.items(ModItems.IRON_WHISK))
                .requirements(new String[][]{new String[]{"iron_whisk"}})
                .build(consumer, ModernDelightMain.MOD_ID + ":root");

        // 厨房入门
        Advancement getWhisk = advancement(getStart, ModItems.IRON_WHISK, "got_whisk", AdvancementFrame.TASK)
                .criterion("iron_whisk", InventoryChangedCriterion.Conditions.items(ModItems.IRON_WHISK))
                .criterion("glass_bowl", InventoryChangedCriterion.Conditions.items(ModBlocks.GLASS_BOWL))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_whisk");

        Advancement getAmethystTool = advancement(getWhisk, ModItems.AMETHYST_KNIFE, "got_amethyst", AdvancementFrame.TASK)
                .criterion("amethyst_knife", InventoryChangedCriterion.Conditions.items(ModItems.AMETHYST_KNIFE))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_amethyst");

        Advancement getCuttlebone = advancement(getAmethystTool, ModItems.CUTTLEBONE, "got_cuttlebone", AdvancementFrame.GOAL)
                .criterion("cuttlebone", InventoryChangedCriterion.Conditions.items(ModItems.CUTTLEBONE))
                .criterion("glow_cuttlebone", InventoryChangedCriterion.Conditions.items(ModItems.GLOW_CUTTLEBONE))
                .requirements(new String[][]{new String[]{"cuttlebone", "glow_cuttlebone"}})
                .build(consumer, ModernDelightMain.MOD_ID + ":got_cuttlebone");

        Advancement getKebabs = advancement(getCuttlebone, ModItems.SQUID_TENTACLE_KEBABS, "got_kebabs", AdvancementFrame.TASK)
                .criterion("kebabs", InventoryChangedCriterion.Conditions.items(ModItems.SQUID_TENTACLE_KEBABS))
                .criterion("glow_kebabs", InventoryChangedCriterion.Conditions.items(ModItems.GLOW_SQUID_TENTACLE_KEBABS))
                .requirements(new String[][]{new String[]{"kebabs", "glow_kebabs"}})
                .build(consumer, ModernDelightMain.MOD_ID + ":got_kebabs");

        Advancement getTruffle = advancement(getWhisk, ModItems.BLACK_TRUFFLE, "got_truffle", AdvancementFrame.GOAL)
                .criterion("black_truffle", InventoryChangedCriterion.Conditions.items(ModItems.BLACK_TRUFFLE))
                .criterion("white_truffle", InventoryChangedCriterion.Conditions.items(ModItems.WHITE_TRUFFLE))
                .requirements(new String[][]{new String[]{"black_truffle", "white_truffle"}})
                .build(consumer, ModernDelightMain.MOD_ID + ":got_truffle");

        Advancement getCherryBomb = hiddenAdvancement(getStart, ModItems.CHERRY_BOMB, "got_cherry_bomb", AdvancementFrame.TASK)
                .criterion("explode", ModCriteria.CHERRY_BOMB_EXPLODE.conditions())
                .build(consumer, ModernDelightMain.MOD_ID + ":got_cherry_bomb");

        Advancement getCuisineTable = advancement(getWhisk, ModBlocks.CUISINE_TABLE, "cuisine_table", AdvancementFrame.TASK)
                .criterion("cuisine_table", InventoryChangedCriterion.Conditions.items(ModBlocks.CUISINE_TABLE))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_cuisine_table");

        Advancement getCabinet = advancement(getCuisineTable, ModBlocks.ANDESITE_CABINET, "cabinet", AdvancementFrame.TASK)
                .criterion("andesite_cabinet", InventoryChangedCriterion.Conditions.items(ModBlocks.ANDESITE_CABINET))
                .criterion("diorite_cabinet", InventoryChangedCriterion.Conditions.items(ModBlocks.DIORITE_CABINET))
                .criterion("granite_cabinet", InventoryChangedCriterion.Conditions.items(ModBlocks.GRANITE_CABINET))
                .criterion("deepslate_cabinet", InventoryChangedCriterion.Conditions.items(ModBlocks.DEEPSLATE_CABINET))
                .criterion("blackstone_cabinet", InventoryChangedCriterion.Conditions.items(ModBlocks.BLACKSTONE_CABINET))
                .criterion("basalt_cabinet", InventoryChangedCriterion.Conditions.items(ModBlocks.BASALT_CABINET))
                .criterion("obsidian_cabinet", InventoryChangedCriterion.Conditions.items(ModBlocks.OBSIDIAN_CABINET))
                .requirements(new String[][]{new String[]{
                        "andesite_cabinet", "diorite_cabinet", "granite_cabinet", "deepslate_cabinet",
                        "blackstone_cabinet", "basalt_cabinet", "obsidian_cabinet"
                }})
                .build(consumer, ModernDelightMain.MOD_ID + ":got_cabinet");

        // 研磨与香料
        Advancement getStoneMortar = advancement(getWhisk, ModItems.STONE_MORTAR, "stone_mortar", AdvancementFrame.TASK)
                .criterion("stone_mortar", InventoryChangedCriterion.Conditions.items(ModItems.STONE_MORTAR))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_stone_mortar");

        Advancement getBlackPepper = advancement(getStoneMortar, ModItems.BLACK_PEPPER_CORN, "get_black_pepper", AdvancementFrame.TASK)
                .criterion("black_pepper", InventoryChangedCriterion.Conditions.items(ModItems.BLACK_PEPPER_CORN))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_black_pepper");

        Advancement getChili = advancement(getBlackPepper, ModItems.CHILI_PEPPER, "chili", AdvancementFrame.TASK)
                .criterion("chili_pepper", InventoryChangedCriterion.Conditions.items(ModItems.CHILI_PEPPER))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_chili");

        Advancement getChiliSauce = advancement(getChili, ModItems.CHILI_SAUCE, "chili_sauce", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(60))
                .criterion("chili_sauce", InventoryChangedCriterion.Conditions.items(ModItems.CHILI_SAUCE))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_chili_sauce");

        Advancement getGarlic = advancement(getStoneMortar, ModItems.GARLIC, "garlic", AdvancementFrame.TASK)
                .criterion("garlic", InventoryChangedCriterion.Conditions.items(ModItems.GARLIC))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_garlic");

        Advancement getMatcha = advancement(getStoneMortar, ModItems.MATCHA, "matcha", AdvancementFrame.TASK)
                .criterion("matcha", InventoryChangedCriterion.Conditions.items(ModItems.MATCHA))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_matcha");

        // 面团与披萨
        Advancement getWheatDough = advancement(getWhisk, ModBlocks.WHEAT_DOUGH, "get_wheat_dough", AdvancementFrame.TASK)
                .criterion("wheat_dough", InventoryChangedCriterion.Conditions.items(ModBlocks.WHEAT_DOUGH))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_wheat_dough");

        Advancement getRawPizza = advancement(getWheatDough, ModBlocks.RAW_PIZZA, "get_raw_pizza", AdvancementFrame.TASK)
                .criterion("raw_pizza", InventoryChangedCriterion.Conditions.items(ModBlocks.RAW_PIZZA))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_raw_pizza");

        Advancement getPizza = advancement(getRawPizza, ModBlocks.PIZZA, "get_pizza", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(100))
                .criterion("pizza", InventoryChangedCriterion.Conditions.items(ModBlocks.PIZZA))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_pizza");

        // 烤炉与烘焙
        Advancement getAdvanceFurnace = advancement(getStart, ModBlocks.ADVANCE_FURNACE, "get_advance_furnace", AdvancementFrame.TASK)
                .criterion("advance_furnace", InventoryChangedCriterion.Conditions.items(ModBlocks.ADVANCE_FURNACE))
                .criterion("baking_tray", InventoryChangedCriterion.Conditions.items(ModBlocks.BAKING_TRAY))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_advance_furnace");

        Advancement getOven = advancement(getAdvanceFurnace, ModBlocks.OVEN, "get_oven", AdvancementFrame.TASK)
                .criterion("oven", InventoryChangedCriterion.Conditions.items(ModBlocks.OVEN))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_oven");

        Advancement getCrowbar = advancement(getOven, ModItems.CROWBAR, "get_crowbar", AdvancementFrame.TASK)
                .criterion("crowbar", InventoryChangedCriterion.Conditions.items(ModItems.CROWBAR))
                .criterion("pry", ModCriteria.CROWBAR_PRY.conditions())
                .build(consumer, ModernDelightMain.MOD_ID + ":got_crowbar");

        Advancement getEggTart = advancement(getOven, ModItems.EGG_TART, "get_egg_tart", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(60))
                .criterion("egg_tart", InventoryChangedCriterion.Conditions.items(ModItems.EGG_TART))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_egg_tart");

        Advancement getTruffleEggTart = advancement(getEggTart, ModItems.TRUFFLE_EGG_TART, "truffle_egg_tart", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(80))
                .criterion("truffle_egg_tart", ConsumeItemCriterion.Conditions.item(ModItems.TRUFFLE_EGG_TART))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_truffle_egg_tart");

        Advancement getDonut = advancement(getOven, ModItems.DONUT, "donut", AdvancementFrame.TASK)
                .criterion("donut", ConsumeItemCriterion.Conditions.item(ModItems.DONUT))
                .criterion("chocolate_donut", ConsumeItemCriterion.Conditions.item(ModItems.CHOCOLATE_DONUT))
                .requirements(new String[][]{new String[]{"donut", "chocolate_donut"}})
                .build(consumer, ModernDelightMain.MOD_ID + ":got_donut");

        Advancement getOperaCake = advancement(getOven, ModItems.OPERA_CAKE, "opera_cake", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(100))
                .criterion("opera_cake", ConsumeItemCriterion.Conditions.item(ModItems.OPERA_CAKE))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_opera_cake");

        Advancement getAllCake = advancement(getEggTart, ModItems.BLUE_ORCHID_FLOWER_CAKE, "all_cake", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(200))
                .criterion("blue_orchid_flower_cake", InventoryChangedCriterion.Conditions.items(ModItems.BLUE_ORCHID_FLOWER_CAKE))
                .criterion("cherry_cake", InventoryChangedCriterion.Conditions.items(ModItems.CHERRY_CAKE))
                .criterion("lilac_cake", InventoryChangedCriterion.Conditions.items(ModItems.LILAC_CAKE))
                .criterion("orange_tulip_cake", InventoryChangedCriterion.Conditions.items(ModItems.ORANGE_TULIP_CAKE))
                .criterion("oxeye_daisy_cake", InventoryChangedCriterion.Conditions.items(ModItems.OXEYE_DAISY_CAKE))
                .criterion("pink_tulip_cake", InventoryChangedCriterion.Conditions.items(ModItems.PINK_TULIP_CAKE))
                .criterion("rose_cake", InventoryChangedCriterion.Conditions.items(ModItems.ROSE_CAKE))
                .criterion("sunflower_cake", InventoryChangedCriterion.Conditions.items(ModItems.SUNFLOWER_CAKE))
                .criterion("white_tulip_cake", InventoryChangedCriterion.Conditions.items(ModItems.WHITE_TULIP_CAKE))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_all_cake");

        // 奶油与甜点
        Advancement getCream = advancement(getWhisk, ModItems.CREAM_BUCKET, "get_cream", AdvancementFrame.TASK)
                .criterion("cream", ModCriteria.WHISK.conditions(ModItems.CREAM_BUCKET))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_cream");

        Advancement getButter = advancement(getCream, ModItems.BUTTER, "get_butter", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(60))
                .criterion("butter", ModCriteria.WHISK.conditions(ModItems.BUTTER))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_butter");

        Advancement getAllPudding = advancement(getButter, ModItems.APPLE_PUDDING, "all_pudding", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(150))
                .criterion("apple_pudding", InventoryChangedCriterion.Conditions.items(ModItems.APPLE_PUDDING))
                .criterion("matcha_pudding", InventoryChangedCriterion.Conditions.items(ModItems.MATCHA_PUDDING))
                .criterion("caramel_pudding", InventoryChangedCriterion.Conditions.items(ModItems.CARAMEL_PUDDING))
                .criterion("cherry_pudding", InventoryChangedCriterion.Conditions.items(ModItems.CHERRY_PUDDING))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_all_pudding");

        // 电力与机械
        Advancement getElectriciansDesk = advancement(getStart, ModBlocks.ELECTRICIANS_DESK, "electricians_desk", AdvancementFrame.TASK)
                .criterion("electricians_desk", InventoryChangedCriterion.Conditions.items(ModBlocks.ELECTRICIANS_DESK))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_electricians_desk");

        Advancement getTeslaCoil = advancement(getElectriciansDesk, ModBlocks.TESLA_COIL, "tesla_coil", AdvancementFrame.GOAL)
                .criterion("tesla_coil", InventoryChangedCriterion.Conditions.items(ModBlocks.TESLA_COIL))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_tesla_coil");

        Advancement getWind = advancement(getTeslaCoil, ModBlocks.FAN_BLADE_ITEM, "wind", AdvancementFrame.TASK)
                .criterion("fan_blade", InventoryChangedCriterion.Conditions.items(ModBlocks.FAN_BLADE_ITEM))
                .criterion("wind_turbine_controller", InventoryChangedCriterion.Conditions.items(ModBlocks.WIND_TURBINE_CONTROLLER))
                .build(consumer, ModernDelightMain.MOD_ID + ":wind");

        Advancement getSiliconIngot = advancement(getWind, ModItems.SILICON_INGOT, "silicon_ingot", AdvancementFrame.TASK)
                .criterion("silicon_ingot", InventoryChangedCriterion.Conditions.items(ModItems.SILICON_INGOT))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_silicon_ingot");

        Advancement getBattery = advancement(getSiliconIngot, ModBlocks.SIMPLE_BATTERY_ITEM, "battery", AdvancementFrame.TASK)
                .criterion("simple_battery", InventoryChangedCriterion.Conditions.items(ModBlocks.SIMPLE_BATTERY_ITEM))
                .criterion("intermediate_battery", InventoryChangedCriterion.Conditions.items(ModBlocks.INTERMEDIATE_BATTERY_ITEM))
                .criterion("advance_battery", InventoryChangedCriterion.Conditions.items(ModBlocks.ADVANCE_BATTERY_ITEM))
                .requirements(new String[][]{new String[]{"simple_battery", "intermediate_battery", "advance_battery"}})
                .build(consumer, ModernDelightMain.MOD_ID + ":got_battery");

        Advancement getBattery4 = advancement(getBattery, ModBlocks.DIMENSION_BATTERY_ITEM, "battery4", AdvancementFrame.CHALLENGE)
                .rewards(AdvancementRewards.Builder.experience(300))
                .criterion("dimension_battery", InventoryChangedCriterion.Conditions.items(ModBlocks.DIMENSION_BATTERY_ITEM))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_battery4");

        Advancement thermalPowerGeneration = advancement(getSiliconIngot, ModBlocks.STERLING_ENGINE_ITEM, "thermal_power_generation", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(200))
                .criterion("sterling_engine", InventoryChangedCriterion.Conditions.items(ModBlocks.STERLING_ENGINE_ITEM))
                .criterion("faraday_generator", InventoryChangedCriterion.Conditions.items(ModBlocks.FARADAY_GENERATOR))
                .build(consumer, ModernDelightMain.MOD_ID + ":thermal_power_generation");

        Advancement getPhotovoltaic = advancement(getBattery, ModBlocks.PHOTOVOLTAIC_GENERATOR, "pg", AdvancementFrame.GOAL)
                .criterion("photovoltaic_generator", InventoryChangedCriterion.Conditions.items(ModBlocks.PHOTOVOLTAIC_GENERATOR))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_pg");

        Advancement getElectricWhisk = advancement(getBattery, ModItems.ELECTRIC_WHISK, "electric_whisk", AdvancementFrame.TASK)
                .criterion("electric_whisk", InventoryChangedCriterion.Conditions.items(ModItems.ELECTRIC_WHISK))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_electric_whisk");

        Advancement getJuiceExtractor = advancement(getWind, ModBlocks.JUICE_EXTRACTOR_ITEM, "juice_extractor", AdvancementFrame.TASK)
                .criterion("juice_extractor", InventoryChangedCriterion.Conditions.items(ModBlocks.JUICE_EXTRACTOR_ITEM))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_juice_extractor");

        Advancement getGlassCup = advancement(getJuiceExtractor, ModBlocks.GLASS_CUP, "glass_cup", AdvancementFrame.TASK)
                .criterion("glass_cup", InventoryChangedCriterion.Conditions.items(ModBlocks.GLASS_CUP))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_glass_cup");

        Advancement getAllTea = advancement(getGlassCup, ModBlocks.CHERRY_MILK_TEA_ITEM, "all_tea", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(100))
                .criterion("cherry_milk_tea", ModCriteria.JUICE_EXTRACT.conditions(ModBlocks.CHERRY_MILK_TEA_ITEM))
                .criterion("rose_ice_tea", ModCriteria.JUICE_EXTRACT.conditions(ModBlocks.ROSE_ICE_TEA_TEA_ITEM))
                .criterion("mango_milk_tea", ModCriteria.JUICE_EXTRACT.conditions(ModItems.MANGO_MILK_TEA))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_all_tea");

        // 冷冻与冰品
        Advancement getFreezer = advancement(getWind, ModBlocks.FREEZER, "get_freezer", AdvancementFrame.TASK)
                .criterion("freezer", InventoryChangedCriterion.Conditions.items(ModBlocks.FREEZER))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_freezer");

        Advancement getIceCreamMaker = advancement(getFreezer, ModBlocks.ICE_CREAM_MAKER_ITEM, "ice_cream_maker", AdvancementFrame.TASK)
                .criterion("ice_cream_maker", InventoryChangedCriterion.Conditions.items(ModBlocks.ICE_CREAM_MAKER_ITEM))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_ice_cream_maker");

        Advancement getIceCream = advancement(getIceCreamMaker, ModItems.ICE_CREAM, "ice_cream", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(80))
                .criterion("ice_cream", InventoryChangedCriterion.Conditions.items(ModItems.ICE_CREAM))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_ice_cream");

        Advancement getAllMousse = advancement(getFreezer, ModItems.CHERRY_MOUSSE, "all_mousse", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(150))
                .criterion("cherry_mousse", InventoryChangedCriterion.Conditions.items(ModItems.CHERRY_MOUSSE))
                .criterion("chocolate_mousse", InventoryChangedCriterion.Conditions.items(ModItems.CHOCOLATE_MOUSSE))
                .criterion("cream_mousse", InventoryChangedCriterion.Conditions.items(ModItems.CREAM_MOUSSE))
                .criterion("golden_apple_mousse", InventoryChangedCriterion.Conditions.items(ModItems.GOLDEN_APPLE_MOUSSE))
                .criterion("matcha_mousse", InventoryChangedCriterion.Conditions.items(ModItems.MATCHA_MOUSSE))
                .criterion("pumpkin_mousse", InventoryChangedCriterion.Conditions.items(ModItems.PUMPKIN_MOUSSE))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_all_mousse");

        Advancement getAllIceLolly = advancement(getFreezer, ModItems.ICE_LOLLY, "all_ice_lolly", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(150))
                .criterion("ice_lolly", InventoryChangedCriterion.Conditions.items(ModItems.ICE_LOLLY))
                .criterion("cherry_ice_lolly", InventoryChangedCriterion.Conditions.items(ModItems.CHERRY_ICE_LOLLY))
                .criterion("matcha_ice_lolly", InventoryChangedCriterion.Conditions.items(ModItems.MATCHA_ICE_LOLLY))
                .criterion("chocolate_crunch_ice_lolly", InventoryChangedCriterion.Conditions.items(ModItems.CHOCOLATE_CRUNCH_ICE_LOLLY))
                .criterion("wither_ice_lolly", InventoryChangedCriterion.Conditions.items(ModItems.WITHER_ICE_LOLLY))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_all_ice_lolly");

        Advancement getWitherLolly = hiddenAdvancement(getAllIceLolly, ModItems.WITHER_ICE_LOLLY, "wither_lolly", AdvancementFrame.TASK)
                .rewards(AdvancementRewards.Builder.experience(150))
                .criterion("wither_ice_lolly", ConsumeItemCriterion.Conditions.item(ModItems.WITHER_ICE_LOLLY))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_wither_lolly");

        // 沼气与燃气
        Advancement getBdcAndBdi = advancement(getStart, ModBlocks.BIOGAS_DIGESTER_IO, "bdc_bdi", AdvancementFrame.TASK)
                .criterion("bdc", InventoryChangedCriterion.Conditions.items(ModBlocks.BIOGAS_DIGESTER_CONTROLLER))
                .criterion("bdi", InventoryChangedCriterion.Conditions.items(ModBlocks.BIOGAS_DIGESTER_IO))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_bdc_bdi");

        Advancement getGasCanister = advancement(getBdcAndBdi, ModBlocks.GAS_CANISTER_ITEM, "gas_canister", AdvancementFrame.TASK)
                .criterion("gas_canister", InventoryChangedCriterion.Conditions.items(ModBlocks.GAS_CANISTER_ITEM))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_gas_canister");

        Advancement getGasStove = advancement(getGasCanister, ModBlocks.GAS_COOKING_STOVE, "gas_cooking_stove", AdvancementFrame.GOAL)
                .criterion("gas_cooking_stove", InventoryChangedCriterion.Conditions.items(ModBlocks.GAS_COOKING_STOVE))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_gas_cooking_stove");

        Advancement clickGasCookingStove = hiddenAdvancement(getGasStove, ModBlocks.BURNING_GAS_COOKING_STOVE, "click_gas_cooking_stove", AdvancementFrame.TASK)
                .criterion("clicking", FastStoveClickCriterion.Conditions.create(10, 3.0))
                .build(consumer, ModernDelightMain.MOD_ID + ":click_gas_cooking_stove");

        Advancement getWoodenBasin = advancement(getGasStove, ModBlocks.WOODEN_BASIN, "wooden_basin", AdvancementFrame.TASK)
                .criterion("wooden_basin", InventoryChangedCriterion.Conditions.items(ModBlocks.WOODEN_BASIN))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_wooden_basin");

        Advancement getSoyMilk = advancement(getWoodenBasin, ModItems.SOY_MILK_BUCKET, "soy_milk", AdvancementFrame.TASK)
                .criterion("soy_milk", InventoryChangedCriterion.Conditions.items(ModItems.SOY_MILK_BUCKET))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_soy_milk");

        Advancement getVegetableOil = advancement(getWoodenBasin, ModItems.VEGETABLE_OIL_BOTTLE, "vegetable_oil", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(60))
                .criterion("filter", InventoryChangedCriterion.Conditions.items(ModItems.FILTER))
                .criterion("bottle", InventoryChangedCriterion.Conditions.items(ModItems.VEGETABLE_OIL_BOTTLE))
                .criterion("bucket", InventoryChangedCriterion.Conditions.items(ModItems.VEGETABLE_OIL_BUCKET))
                .requirements(new String[][]{new String[]{"filter"}, new String[]{"bottle", "bucket"}})
                .build(consumer, ModernDelightMain.MOD_ID + ":got_vegetable_oil");

        Advancement getDeepFryer = advancement(getVegetableOil, ModBlocks.DEEP_FRYER, "deep_fryer", AdvancementFrame.TASK)
                .criterion("deep_fryer", InventoryChangedCriterion.Conditions.items(ModBlocks.DEEP_FRYER))
                .criterion("deep_fry_basket", InventoryChangedCriterion.Conditions.items(ModBlocks.DEEP_FRY_BASKET_ITEM))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_deep_fryer");

        Advancement getFriedNoodles = advancement(getDeepFryer, ModItems.FRIED_NOODLES, "fried_noodles", AdvancementFrame.TASK)
                .criterion("fried_noodles", InventoryChangedCriterion.Conditions.items(ModItems.FRIED_NOODLES))
                .criterion("wrapping_paper", InventoryChangedCriterion.Conditions.items(ModItems.MULTIFUNCTIONAL_WRAPPING_PAPER))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_fried_noodles");

        Advancement eatAllFriedFood = advancement(getFriedNoodles, ModItems.FRIED_MILK, "all_fried", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(300))
                .criterion("fried_noodles", InventoryChangedCriterion.Conditions.items(ModItems.FRIED_NOODLES))
                .criterion("potato_chip", ConsumeItemCriterion.Conditions.item(ModItems.POTATO_CHIP))
                .criterion("salmon", ConsumeItemCriterion.Conditions.item(ModItems.FRIED_SALMON))
                .criterion("milk", ConsumeItemCriterion.Conditions.item(ModItems.FRIED_MILK))
                .criterion("dough_stick", ConsumeItemCriterion.Conditions.item(ModItems.FRIED_DOUGH_STICK))
                .criterion("cod", ConsumeItemCriterion.Conditions.item(ModItems.FRIED_COD))
                .criterion("apple", ConsumeItemCriterion.Conditions.item(ModItems.FRIED_APPLE))
                .criterion("chicken_fillet", ConsumeItemCriterion.Conditions.item(ModItems.CHICKEN_FILLET))
                .criterion("fried_red_mushroom", ConsumeItemCriterion.Conditions.item(ModItems.FRIED_RED_MUSHROOM))
                .criterion("fried_brown_mushroom", ConsumeItemCriterion.Conditions.item(ModItems.FRIED_BROWN_MUSHROOM))
                .criterion("deep_fried_shrimp_cake", InventoryChangedCriterion.Conditions.items(ModItems.DEEP_FRIED_SHRIMP_CAKE))
                .criterion("deep_fried_potato_chips", InventoryChangedCriterion.Conditions.items(ModItems.DEEP_FRIED_POTATO_CHIPS))
                .criterion("french_fries", ConsumeItemCriterion.Conditions.item(ModItems.FRENCH_FRIES))
                .criterion("deep_fried_bun", ConsumeItemCriterion.Conditions.item(ModItems.DEEP_FRIED_BUN))
                .criterion("braised_shrimp_ball", ConsumeItemCriterion.Conditions.item(ModItems.BRAISED_SHRIMP_BALL))
                .criterion("golden_double_crisp", ConsumeItemCriterion.Conditions.item(ModItems.GOLDEN_DOUBLE_CRISP))
                .criterion("scotch_egg", ConsumeItemCriterion.Conditions.item(ModItems.SCOTCH_EGG))
                .criterion("fried_chicken", ConsumeItemCriterion.Conditions.item(ModItems.FRIED_CHICKEN))
                .criterion("seaweed_fried_shrimp_cake", ConsumeItemCriterion.Conditions.item(ModItems.SEAWEED_FRIED_SHRIMP_CAKE))
                .build(consumer, ModernDelightMain.MOD_ID + ":eat_all_fried");

        Advancement getFishAndChips = advancement(getFriedNoodles, ModBlocks.FISH_AND_CHIPS_ITEM, "fish_and_chips", AdvancementFrame.TASK)
                .criterion("fish_and_chips", InventoryChangedCriterion.Conditions.items(ModBlocks.FISH_AND_CHIPS_ITEM))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_fish_and_chips");

        Advancement getPortablePot = advancement(getFriedNoodles, ModItems.COOKED_PORTABLE_POT, "cooked_portable_pot", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(80))
                .criterion("packaged_instant_noodles", InventoryChangedCriterion.Conditions.items(ModItems.PACKAGED_INSTANT_NOODLES))
                .criterion("portable_pot", InventoryChangedCriterion.Conditions.items(ModItems.PORTABLE_POT))
                .criterion("quicklime", InventoryChangedCriterion.Conditions.items(ModItems.QUICKLIME))
                .criterion("cooked_portable_pot", InventoryChangedCriterion.Conditions.items(ModItems.COOKED_PORTABLE_POT))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_cooked_portable_pot");

        // 蒸笼与点心
        Advancement getBambooSteamer = advancement(getGasStove, ModBlocks.BAMBOO_GRATE, "bamboo_steamer", AdvancementFrame.TASK)
                .criterion("bamboo_grate", InventoryChangedCriterion.Conditions.items(ModBlocks.BAMBOO_GRATE))
                .criterion("bamboo_cover", InventoryChangedCriterion.Conditions.items(ModBlocks.BAMBOO_COVER))
                .criterion("cauldron", InventoryChangedCriterion.Conditions.items(Blocks.CAULDRON))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_bamboo_steamer");

        Advancement getElectricSteamer = advancement(getBambooSteamer, ModBlocks.ELECTRIC_STEAMER, "electric_steamer", AdvancementFrame.GOAL)
                .criterion("electric_steamer", InventoryChangedCriterion.Conditions.items(ModBlocks.ELECTRIC_STEAMER))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_electric_steamer");

        Advancement getSteamedBun = advancement(getBambooSteamer, ModItems.STEAMED_BUN, "steamed_bun", AdvancementFrame.TASK)
                .criterion("steamed_bun", InventoryChangedCriterion.Conditions.items(ModItems.STEAMED_BUN))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_steamed_bun");

        Advancement getDimSum = advancement(getSteamedBun, ModItems.SHAOMAI, "dim_sum", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(200))
                .criterion("shaomai", InventoryChangedCriterion.Conditions.items(ModItems.SHAOMAI))
                .criterion("crystal_dumpling", InventoryChangedCriterion.Conditions.items(ModItems.CRYSTAL_DUMPLING))
                .criterion("steamed_stuffed_bun", InventoryChangedCriterion.Conditions.items(ModItems.STEAMED_STUFFED_BUN))
                .criterion("vegetable_steamed_stuffed_bun", InventoryChangedCriterion.Conditions.items(ModItems.VEGETABLE_STEAMED_STUFFED_BUN))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_dim_sum");

        // 发酵与酿造
        Advancement getIndustrialFermenter = advancement(getWind, ModBlocks.INDUSTRIAL_FERMENTER_ITEM, "industrial_fermenter", AdvancementFrame.GOAL)
                .rewards(AdvancementRewards.Builder.experience(120))
                .criterion("industrial_fermenter", InventoryChangedCriterion.Conditions.items(ModBlocks.INDUSTRIAL_FERMENTER_ITEM))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_industrial_fermenter");
        Advancement getFermentationTank = advancement(getIndustrialFermenter, ModItems.FERMENTATION_TANK, "fermentation_tank", AdvancementFrame.TASK)
                .criterion("fermentation_tank", InventoryChangedCriterion.Conditions.items(ModItems.FERMENTATION_TANK))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_fermentation_tank");

        Advancement getCheese = advancement(getIndustrialFermenter, ModItems.CHEESE, "get_cheese", AdvancementFrame.TASK)
                .criterion("cheese", FermentCriterion.Conditions.item(ModItems.CHEESE))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_cheese");

        Advancement getDoubleBurger = advancement(getCheese, ModItems.DOUBLE_MEAT_BURGER, "double_burger", AdvancementFrame.CHALLENGE)
                .rewards(AdvancementRewards.Builder.experience(300))
                .criterion("double_meat_burger", ConsumeItemCriterion.Conditions.item(ModItems.DOUBLE_MEAT_BURGER))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_double_burger");

        Advancement getVinegar = advancement(getIndustrialFermenter, ModItems.VINEGAR_BUCKET, "vinegar", AdvancementFrame.TASK)
                .criterion("vinegar", FermentCriterion.Conditions.fluid(ModFluid.STILL_VINEGAR))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_vinegar");

        Advancement getAlcohol = advancement(getIndustrialFermenter, ModItems.ALCOHOL_BUCKET, "alcohol", AdvancementFrame.TASK)
                .criterion("alcohol", FermentCriterion.Conditions.fluid(ModFluid.STILL_ALCOHOL))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_alcohol");

        // 拖网
        Advancement getTrawler = advancement(getStart, ModBlocks.TRAWLER_ITEM, "trawler", AdvancementFrame.TASK)
                .criterion("trawler", InventoryChangedCriterion.Conditions.items(ModBlocks.TRAWLER_ITEM))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_trawler");

        Advancement getCrab = advancement(getTrawler, ModItems.CRAB, "crab", AdvancementFrame.TASK)
                .criterion("crab", ModCriteria.TRAWLER_CATCH.conditions(ModItems.CRAB))
                .build(consumer, ModernDelightMain.MOD_ID + ":got_crab");

        // 隐藏挑战
        Advancement eatWitherCake = hiddenAdvancement(getStart, ModItems.WITHER_ROSE_CAKE, "wither_rose_cake", AdvancementFrame.CHALLENGE)
                .rewards(AdvancementRewards.Builder.experience(300))
                .criterion("wither_rose_cake", ConsumeItemCriterion.Conditions.item(ModItems.WITHER_ROSE_CAKE))
                .build(consumer, ModernDelightMain.MOD_ID + ":eat_wither_rose_cake");
    }
}
