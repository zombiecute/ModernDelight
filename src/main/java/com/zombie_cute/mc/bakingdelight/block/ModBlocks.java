package com.zombie_cute.mc.bakingdelight.block;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.biogas.BiogasDigesterControllerBlock;
import com.zombie_cute.mc.bakingdelight.block.biogas.BiogasDigesterIOBlock;
import com.zombie_cute.mc.bakingdelight.block.biogas.GasCanisterBlock;
import com.zombie_cute.mc.bakingdelight.block.biogas.GasCanisterBlockItem;
import com.zombie_cute.mc.bakingdelight.block.crops.BlackPepperCropBlock;
import com.zombie_cute.mc.bakingdelight.block.crops.GarlicCropBlock;
import com.zombie_cute.mc.bakingdelight.block.food.*;
import com.zombie_cute.mc.bakingdelight.block.food.fish_and_chips.FishAndChipsBlock;
import com.zombie_cute.mc.bakingdelight.block.food.pizza.PizzaBlock;
import com.zombie_cute.mc.bakingdelight.block.food.pizza.PizzaWIPBlock;
import com.zombie_cute.mc.bakingdelight.block.food.pizza.RawPizzaBlock;
import com.zombie_cute.mc.bakingdelight.block.food.pizza.WheatDoughBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.*;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.decor.CabinetBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.decor.GlassCupBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.decor.KitchenUtensilHolderBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.decor.WoodenPlateBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.BakingTrayBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.DeepFryBasketBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.DeepFryerBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.WoodenBasinBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.gas_cooking_stove.BurningGasCookingStoveBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.gas_cooking_stove.GasCookingStoveBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.ice_cream_maker.IceCreamMakerBlockItem;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.juice_extractor.JuiceExtractorBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.juice_extractor.JuiceExtractorBlockItem;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.steaming.BambooCoverBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.steaming.BambooGrateBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.steaming.ElectricSteamerBlock;
import com.zombie_cute.mc.bakingdelight.block.power.ChargingPostBlock;
import com.zombie_cute.mc.bakingdelight.block.power.ElectriciansDeskBlock;
import com.zombie_cute.mc.bakingdelight.block.power.TeslaCoilBlock;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.ACDCConverterBlock;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.PhotovoltaicGeneratorBlock;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power.FaradayGeneratorBlock;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power.SterlingEngineBlock;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.wind_power.FanBladeBlock;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.wind_power.WindTurbineControllerBlock;
import com.zombie_cute.mc.bakingdelight.block.power.batteries.*;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModBlocks {
    public static final Block SILICON_BLOCK = registerBlock("silicon_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block MASHED_POTATO_BLOCK = registerBlock("mashed_potato_block",
            new MashedPotatoBlock(FabricBlockSettings.copyOf(Blocks.DIRT).sounds(BlockSoundGroup.SAND)
                    .mapColor(MapColor.YELLOW).velocityMultiplier(0.6f).jumpVelocityMultiplier(0.5f)));
    public static final Block GLASS_BOWL = registerBlock("glass_bowl",
            new GlassBowlBlock(FabricBlockSettings.copyOf(Blocks.GLASS).hardness(0)
                    .mapColor(MapColor.WHITE).nonOpaque()));
    public static final Block BLACK_PEPPER_CROP = registerBlockWithoutItem("black_pepper_crop",
            new BlackPepperCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES)));
    public static final Block OVEN = registerBlock("oven",
            new OvenBlock(FabricBlockSettings.copyOf(Blocks.BRICKS)
                    .luminance(state -> state.get(OvenBlock.OVEN_BURNING) ? 15 : 0)));
    public static final Block FREEZER = registerBlock("freezer",
            new FreezerBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));
    public static final Block WHEAT_DOUGH = registerBlockWithoutItem("wheat_dough",
            new WheatDoughBlock(FabricBlockSettings.copyOf(Blocks.REPEATER).burnable().sounds(BlockSoundGroup.HONEY)
                    .jumpVelocityMultiplier(0.5f).mapColor(MapColor.WHITE).nonOpaque()));
    public static final Block PIZZA_WIP = registerBlockWithoutItem("pizza_wip", new PizzaWIPBlock());
    public static final Block RAW_PIZZA = registerBlockWithoutItem("raw_pizza", new RawPizzaBlock());
    public static final Block PIZZA = registerBlockWithoutItem("pizza", new PizzaBlock());
    public static final Block BAKING_TRAY = registerBlock("baking_tray", new BakingTrayBlock());
    public static final Block DEEP_FRYER = registerBlock("deep_fryer", new DeepFryerBlock());
    public static final Block ADVANCE_FURNACE = registerBlock("advance_furnace", new AdvanceFurnaceBlock());
    public static final Block CREAM_FLUID_BLOCK = registerBlockWithoutItem("cream_fluid",
            new FluidBlock(ModFluid.STILL_CREAM,FabricBlockSettings.copyOf(Blocks.WATER)));
    public static final Block WOODEN_BASIN = registerBlock("wooden_basin",
            new WoodenBasinBlock());
    public static final Block VEGETABLE_OIL_FLUID_BLOCK = registerBlockWithoutItem("vegetable_oil_fluid",
            new FluidBlock(ModFluid.STILL_VEGETABLE_OIL,FabricBlockSettings.copyOf(Blocks.WATER)));
    public static final Block GAS_CANISTER = registerBlockWithoutItem("gas_canister",
            new GasCanisterBlock());
    public static final Block BIOGAS_DIGESTER_CONTROLLER = registerBlock("biogas_digester_controller",
            new BiogasDigesterControllerBlock());
    public static final Block BIOGAS_DIGESTER_IO = registerBlock("biogas_digester_io",
            new BiogasDigesterIOBlock());
    public static final Block BURNING_GAS_COOKING_STOVE = registerBlock("burning_gas_cooking_stove",
            new BurningGasCookingStoveBlock());
    public static final Block GAS_COOKING_STOVE = registerBlock("gas_cooking_stove",
            new GasCookingStoveBlock());
    public static final Block DEEP_FRY_BASKET = registerBlockWithoutItem("deep_fry_basket",
            new DeepFryBasketBlock());
    public static final Block KITCHEN_UTENSIL_HOLDER = registerBlock("kitchen_utensil_holder",
            new KitchenUtensilHolderBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).hardness(0)
                    .sounds(BlockSoundGroup.STONE).mapColor(MapColor.WHITE).strength(5.0f).nonOpaque()));
    public static final Block CUISINE_TABLE = registerBlock("cuisine_table",new CuisineTableBlock());
    public static final Block ANDESITE_CABINET = registerBlock("andesite_cabinet",new CabinetBlock());
    public static final Block DIORITE_CABINET = registerBlock("diorite_cabinet",new CabinetBlock());
    public static final Block GRANITE_CABINET = registerBlock("granite_cabinet",new CabinetBlock());
    public static final Block DEEPSLATE_CABINET = registerBlock("deepslate_cabinet",new CabinetBlock(
            FabricBlockSettings.copyOf(Blocks.DEEPSLATE).nonOpaque()
    ));
    public static final Block BLACKSTONE_CABINET = registerBlock("blackstone_cabinet",new CabinetBlock(
            FabricBlockSettings.copyOf(Blocks.BLACKSTONE).nonOpaque()
    ));
    public static final Block BASALT_CABINET = registerBlock("basalt_cabinet",new CabinetBlock(
            FabricBlockSettings.copyOf(Blocks.BASALT).nonOpaque()
    ));
    public static final Block OBSIDIAN_CABINET = registerBlock("obsidian_cabinet",new CabinetBlock(
            FabricBlockSettings.copyOf(Blocks.OBSIDIAN).nonOpaque()
    ));
    public static final Block PHOTOVOLTAIC_GENERATOR = registerBlock("photovoltaic_generator",new PhotovoltaicGeneratorBlock());
    public static final Block AC_DC_CONVERTER = registerBlock("ac_dc_converter", new ACDCConverterBlock());
    public static final Block FAN_BLADE = registerBlockWithoutItem("fan_blade", new FanBladeBlock());
    public static final Block WIND_TURBINE_CONTROLLER = registerBlock("wind_turbine_controller", new WindTurbineControllerBlock());
    public static final Block SIMPLE_BATTERY = registerBlockWithoutItem("simple_battery", new SimpleBatteryBlock(FabricBlockSettings.copyOf(Blocks.IRON_BARS)));
    public static final Block INTERMEDIATE_BATTERY = registerBlockWithoutItem("intermediate_battery", new IntermediateBatteryBlock(FabricBlockSettings.copyOf(Blocks.IRON_BARS)));
    public static final Block ADVANCE_BATTERY = registerBlockWithoutItem("advance_battery", new AdvanceBatteryBlock(FabricBlockSettings.copyOf(Blocks.IRON_BARS)));
    public static final Block DIMENSION_BATTERY = registerBlockWithoutItem("dimension_battery", new DimensionBatteryBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).luminance(8)));
    public static final Block STERLING_ENGINE = registerBlockWithoutItem("sterling_engine", new SterlingEngineBlock());
    public static final Block FARADAY_GENERATOR = registerBlock("faraday_generator", new FaradayGeneratorBlock());
    public static final Block TESLA_COIL = registerBlock("tesla_coil", new TeslaCoilBlock());
    public static final Block ELECTRICIANS_DESK = registerBlock("electricians_desk", new ElectriciansDeskBlock());
    public static final Block BOXED_CHERRIES = registerBlock("boxed_cherries",new BoxedCherriesBlock());
    public static final Block BAMBOO_GRATE = registerBlock("bamboo_grate",new BambooGrateBlock());
    public static final Block BAMBOO_COVER = registerBlock("bamboo_cover",new BambooCoverBlock());
    public static final Block ELECTRIC_STEAMER = registerBlock("electric_steamer",new ElectricSteamerBlock());
    public static final Block ICE_CREAM_MAKER = registerBlockWithoutItem("ice_cream_maker", new IceCreamMakerBlock());
    public static final Block CARAMEL_PUDDING = registerBlockWithoutItem("caramel_pudding", new CaramelPuddingBlock());
    public static final Block FISH_AND_CHIPS = registerBlockWithoutItem("fish_and_chips", new FishAndChipsBlock());
    public static final Block WILD_PEPPER_CROP = registerBlock("wild_pepper_crop",new PlantBlock(
            FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()
    ));
    public static final Block GARLIC_CROP = registerBlockWithoutItem("garlic_crop",
            new GarlicCropBlock(FabricBlockSettings.copyOf(Blocks.POTATOES)));
    public static final Block WILD_GARLIC = registerBlock("wild_garlic",new PlantBlock(
            FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()
    ));
    public static final Block CHARGING_POST = registerBlock("charging_post", new ChargingPostBlock());
    public static final Block JUICE_EXTRACTOR = registerBlockWithoutItem("juice_extractor", new JuiceExtractorBlock());
    public static final Block GLASS_CUP = registerBlock("glass_cup", new GlassCupBlock());
    public static final Block CHERRY_MILK_TEA = registerBlockWithoutItem("cherry_milk_tea",
            new GlassCupOfTeaBlock(5,0.3f,
                    new StatusEffectInstance(StatusEffects.STRENGTH,10 * 20,0),
                    new StatusEffectInstance(StatusEffects.HASTE,60 * 20,0)));
    public static final Block ROSE_ICE_TEA = registerBlockWithoutItem("rose_ice_tea",
            new GlassCupOfTeaBlock(5,0.3f,
                    new StatusEffectInstance(StatusEffects.RESISTANCE,20 * 20,0),
                    new StatusEffectInstance(StatusEffects.SPEED,60 * 20,0)));
    public static final Block LIQUEFIED_BIOGAS_FLUID_BLOCK = registerBlockWithoutItem("liquefied_biogas_fluid",
            new FluidBlock(ModFluid.STILL_LIQUEFIED_BIOGAS,FabricBlockSettings.copyOf(Blocks.WATER)));
    public static final Block WOODEN_PLATE = registerBlock("wooden_plate",
            new WoodenPlateBlock());
    public static final Block STEAMED_PUMPKIN = registerBlockWithoutItem("steamed_pumpkin",
            new SteamedPumpkinBlock());

    // Block Items
    public static final BlockItem FISH_AND_CHIPS_ITEM = Registry.register(Registries.ITEM,new Identifier(ModernDelightMain.MOD_ID,"fish_and_chips"),
            new BlockItem(FISH_AND_CHIPS,new FabricItemSettings().maxCount(16).food(
                    new FoodComponent.Builder().hunger(20).saturationModifier(0.6f).build()
            )){
                @Override
                public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
                    if (user instanceof PlayerEntity player){
                        if (stack.getCount() == 1){
                            player.setStackInHand(player.getActiveHand(), WOODEN_PLATE.asItem().getDefaultStack());
                        } else {
                            player.giveItemStack(WOODEN_PLATE.asItem().getDefaultStack());
                        }
                    }
                    return super.finishUsing(stack, world, user);
                }
            });
    public static final BlockItem ICE_CREAM_MAKER_ITEM = Registry.register(Registries.ITEM,new Identifier(ModernDelightMain.MOD_ID,"ice_cream_maker"),
            new IceCreamMakerBlockItem());
    public static final BlockItem STERLING_ENGINE_ITEM = Registry.register(Registries.ITEM,new Identifier(ModernDelightMain.MOD_ID,"sterling_engine"),
            new BlockItem(STERLING_ENGINE,new FabricItemSettings().maxCount(16)));
    public static final BlockItem FAN_BLADE_ITEM = Registry.register(Registries.ITEM,new Identifier(ModernDelightMain.MOD_ID,"fan_blade"),
            new BlockItem(FAN_BLADE,new FabricItemSettings().maxCount(16)));
    public static final BlockItem SIMPLE_BATTERY_ITEM = Registry.register(Registries.ITEM,new Identifier(ModernDelightMain.MOD_ID,"simple_battery"),
            new BatteryBlockItem(SIMPLE_BATTERY));
    public static final BlockItem INTERMEDIATE_BATTERY_ITEM = Registry.register(Registries.ITEM,new Identifier(ModernDelightMain.MOD_ID,"intermediate_battery"),
            new BatteryBlockItem(INTERMEDIATE_BATTERY));
    public static final BlockItem ADVANCE_BATTERY_ITEM = Registry.register(Registries.ITEM,new Identifier(ModernDelightMain.MOD_ID,"advance_battery"),
            new BatteryBlockItem(ADVANCE_BATTERY));
    public static final BlockItem DIMENSION_BATTERY_ITEM = Registry.register(Registries.ITEM,new Identifier(ModernDelightMain.MOD_ID,"dimension_battery"),
            new BatteryBlockItem(DIMENSION_BATTERY));
    public static final BlockItem DEEP_FRY_BASKET_ITEM = Registry.register(Registries.ITEM,new Identifier(ModernDelightMain.MOD_ID,"deep_fry_basket"),
            new BlockItem(DEEP_FRY_BASKET,new FabricItemSettings().maxCount(1)));
    public static final BlockItem GAS_CANISTER_ITEM = Registry.register(Registries.ITEM,new Identifier(ModernDelightMain.MOD_ID,"gas_canister"),
            new GasCanisterBlockItem());
    public static final BlockItem PIZZA_ITEM = Registry.register(Registries.ITEM, new Identifier(ModernDelightMain.MOD_ID, "pizza"),
            new BlockItem(PIZZA, new FabricItemSettings().maxCount(1)));
    public static final BlockItem RAW_PIZZA_ITEM = Registry.register(Registries.ITEM, new Identifier(ModernDelightMain.MOD_ID, "raw_pizza"),
            new BlockItem(RAW_PIZZA, new FabricItemSettings()
                    .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER,200,0),0.4f).build()).maxCount(1)));
    public static final BlockItem PIZZA_WIP_ITEM = Registry.register(Registries.ITEM, new Identifier(ModernDelightMain.MOD_ID, "pizza_wip"),
            new BlockItem(PIZZA_WIP, new FabricItemSettings()
                    .food(new FoodComponent.Builder().hunger(5).saturationModifier(0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER,200,0),0.3f).build()).maxCount(1)));
    public static final BlockItem WHEAT_DOUGH_ITEM = Registry.register(Registries.ITEM, new Identifier(ModernDelightMain.MOD_ID, "wheat_dough"),
            new BlockItem(WHEAT_DOUGH, new FabricItemSettings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER,200,0),0.5f).build())));
    public static final BlockItem JUICE_EXTRACTOR_ITEM = Registry.register(Registries.ITEM, new Identifier(ModernDelightMain.MOD_ID, "juice_extractor"),
            new JuiceExtractorBlockItem());
    public static final BlockItem CHERRY_MILK_TEA_ITEM = Registry.register(Registries.ITEM, new Identifier(ModernDelightMain.MOD_ID, "cherry_milk_tea"),
            new GlassCupOfTeaBlockItem(CHERRY_MILK_TEA));
    public static final BlockItem ROSE_ICE_TEA_TEA_ITEM = Registry.register(Registries.ITEM, new Identifier(ModernDelightMain.MOD_ID, "rose_ice_tea"),
            new GlassCupOfTeaBlockItem(ROSE_ICE_TEA));
    public static final BlockItem STEAMED_PUMPKIN_ITEM = Registry.register(Registries.ITEM, new Identifier(ModernDelightMain.MOD_ID, "steamed_pumpkin"),
            new BlockItem(STEAMED_PUMPKIN, new FabricItemSettings().maxCount(16).recipeRemainder(WOODEN_PLATE.asItem())));

    private static Block registerBlockWithoutItem(String name,Block block){
        return Registry.register(Registries.BLOCK,new Identifier(ModernDelightMain.MOD_ID,name),block);
    }
    private static Block registerBlock(String name,Block block){
        registerBlockItem(name,block);
        return Registry.register(Registries.BLOCK,new Identifier(ModernDelightMain.MOD_ID,name),block);
    }
    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, new Identifier(ModernDelightMain.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerModBlocks(){
        ModernDelightMain.LOGGER.info("Registering Mod Blocks for " + ModernDelightMain.MOD_ID);
    }
}
