package com.zombie_cute.mc.bakingdelight.item;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.custom.*;
import com.zombie_cute.mc.bakingdelight.item.enumeration.ModToolMaterials;
import com.zombie_cute.mc.bakingdelight.util.Flavor;
import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ModItems {
    public static final Item EGG_TART = registerItem("egg_tart",new ModStewItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(5).saturationModifier(0.3F).build())));
    public static final Item MASHED_POTATO = registerItem("mashed_potato", new ModStewItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.3F).snack().build()).recipeRemainder(Items.BOWL)));
    public static final Item WOODEN_WHISK = registerItem("wooden_whisk",
            new WhiskItem(1.5F, -2.5F, ToolMaterials.WOOD, new FabricItemSettings()));
    public static final Item STONE_WHISK = registerItem("stone_whisk",
            new WhiskItem(1.5F, -3.0F, ToolMaterials.STONE, new FabricItemSettings()));
    public static final Item COPPER_WHISK = registerItem("copper_whisk",
            new WhiskItem(1.5F, -3.4F, ModToolMaterials.COPPER, new FabricItemSettings()));
    public static final Item IRON_WHISK = registerItem("iron_whisk",
            new WhiskItem(1.5F, -3.3F, ToolMaterials.IRON, new FabricItemSettings()));
    public static final Item GOLDEN_WHISK = registerItem("golden_whisk",
            new WhiskItem(1.5F, -3.0F, ToolMaterials.GOLD, new FabricItemSettings()));
    public static final Item AMETHYST_WHISK = registerItem("amethyst_whisk",
            new WhiskItem(1.5F, -2.6F, ModToolMaterials.AMETHYST, new FabricItemSettings()));
    public static final Item DIAMOND_WHISK = registerItem("diamond_whisk",
            new WhiskItem(1.5F, -2.8F, ToolMaterials.DIAMOND, new FabricItemSettings()));
    public static final Item NETHERITE_WHISK = registerItem("netherite_whisk",
            new WhiskItem(1.5F, -3.0F, ToolMaterials.NETHERITE, new FabricItemSettings().fireproof()));
    public static final Item APPLE_PETAL = registerItem("apple_petal", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).snack().build())));
    public static final Item APPLE_CREAM = registerItem("apple_cream", new CreamItem(Flavor.APPLE,new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3F).build())
            .recipeRemainder(Items.BOWL)
            .maxCount(16)));
    public static final Item BUTTER = registerItem("butter", new ButterItem(new FabricItemSettings().maxCount(16)));
    public static final Item BUTTER_BREAD_SLICE = registerItem("butter_bread_slice", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.8F).snack().build())));
    public static final Item BREAD_SLICE = registerItem("bread_slice", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).snack().build())));
    public static final Item CHEESE = registerItem("cheese", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.3F).snack().build())));
    public static final Item CHERRY = registerItem("cherry", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).build())));
    public static final Item CHERRY_CREAM = registerItem("cherry_cream", new CreamItem(Flavor.CHERRY,new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3F).build())
            .recipeRemainder(Items.BOWL)
            .maxCount(16)));
    public static final Item CHERRY_MOUSSE = registerItem("cherry_mousse", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(5).saturationModifier(0.8F).build())));
    public static final Item CHOCOLATE_CREAM = registerItem("chocolate_cream", new CreamItem(Flavor.CHOCOLATE,new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(5).saturationModifier(0.8F).build())
            .recipeRemainder(Items.BOWL)
            .maxCount(16)));
    public static final Item CHOCOLATE_MOUSSE = registerItem("chocolate_mousse", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.8F).build())));
    public static final Item PRAWN = registerItem("prawn", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.2F).build())));
    public static final Item CREAM = registerItem("cream", new CreamItem(Flavor.PLAIN,new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).build())
            .maxCount(16)));

    public static final Item CREAM_MOUSSE = registerItem("cream_mousse", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).build())));
    public static final Item CRYSTAL_DUMPLING = registerItem("crystal_dumpling", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(9).saturationModifier(0.6F).snack().meat().build())));
    public static final Item GLOW_SQUID = registerItem("glow_squid", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2F).meat()
                    .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 400, 0), 1.0F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER,200, 0), 0.4F).build())));
    public static final Item GRILLED_STARCH_SAUSAGE = registerItem("grilled_starch_sausage", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(9).saturationModifier(0.8F).meat().build())));
    public static final Item GRILLED_SAUSAGE = registerItem("grilled_sausage", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(1.0F).meat().build())));
    public static final Item LITTLE_OCTOPUS_SAUSAGE = registerItem("little_octopus_sausage", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.5F).meat().build())));
    public static final Item GOLDEN_APPLE_CREAM = registerItem("golden_apple_cream", new CreamItem(Flavor.GOLDEN_APPLE,new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1.0F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1.0F)
                    .alwaysEdible().build())
            .recipeRemainder(Items.BOWL)
            .maxCount(16)));
    public static final Item GOLDEN_APPLE_MOUSSE = registerItem("golden_apple_mousse", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.8F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 2), 1.0F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 4800, 1), 1.0F)
                    .alwaysEdible().build())
            .maxCount(16)));
    public static final Item MATCHA_CREAM = registerItem("matcha_cream", new CreamItem(Flavor.MATCHA,new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 600, 0), 0.7F).build())
            .recipeRemainder(Items.BOWL)
            .maxCount(16)));
    public static final Item MATCHA_MOUSSE = registerItem("matcha_mousse", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(5).saturationModifier(0.8F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 1200, 0), 0.7F)
                    .build())));
    public static final Item MIXED_DOUGH = registerItem("mixed_dough", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 0), 0.4F).build())));
    public static final Item POTATO_STARCH = registerItem("potato_starch", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).build())));
    public static final Item PUMPKIN_CREAM = registerItem("pumpkin_cream", new CreamItem(Flavor.PUMPKIN,new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.4F).build())
            .recipeRemainder(Items.BOWL)
            .maxCount(16)));
    public static final Item PUMPKIN_MOUSSE = registerItem("pumpkin_mousse", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(5).saturationModifier(0.7F).build())));
    public static final Item ROASTED_GLOW_SQUID = registerItem("roasted_glow_squid", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).meat()
                    .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1200, 0), 1.0F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,1200, 0), 1.0F).build())));
    public static final Item ROASTED_SQUID = registerItem("roasted_squid", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).meat().build())));
    public static final Item SAUCE_MASHED_POTATO = registerItem("sauce_mashed_potato", new ModStewItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.6F).build())
            .recipeRemainder(Items.BOWL)
            .maxCount(16)));
    public static final Item SQUID = registerItem("squid", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2F).meat().build())));
    public static final Item STARCH_SAUSAGE = registerItem("starch_sausage", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.6F).meat().build())));
    public static final Item SAUSAGE = registerItem("sausage", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.4F).meat().build())));
    public static final Item BLACK_TRUFFLE = registerItem("black_truffle", new TruffleItem(new FabricItemSettings()));
    public static final Item WHITE_TRUFFLE = registerItem("white_truffle", new TruffleItem(new FabricItemSettings()));
    public static final Item AMETHYST_SWORD = registerItem("amethyst_sword",
            new SwordItem(ModToolMaterials.AMETHYST,  3,-2.2F, new FabricItemSettings()));
    public static final Item AMETHYST_PICKAXE = registerItem("amethyst_pickaxe",
            new PickaxeItem(ModToolMaterials.AMETHYST, 2,-2.8F, new FabricItemSettings()));
    public static final Item AMETHYST_AXE = registerItem("amethyst_axe",
            new AxeItem(ModToolMaterials.AMETHYST, 6, -3.0F, new FabricItemSettings()));
    public static final Item AMETHYST_SHOVEL = registerItem("amethyst_shovel",
            new ShovelItem(ModToolMaterials.AMETHYST, 2.5F, -2.7F, new FabricItemSettings()));
    public static final Item AMETHYST_HOE = registerItem("amethyst_hoe",
            new HoeItem(ModToolMaterials.AMETHYST, 0, 0.0F, new FabricItemSettings()));
    public static final Item CUTTLEBONE = registerItem("cuttlebone", new CuttleboneItem(new FabricItemSettings()));
    public static final Item GLOW_CUTTLEBONE = registerItem("glow_cuttlebone", new CuttleboneItem(new FabricItemSettings()));
    public static final Item COPPER_KNIFE = registerItem("copper_knife",
            new KnifeItem(ModToolMaterials.COPPER,0, -2.0f, new FabricItemSettings()));
    public static final Item AMETHYST_KNIFE = registerItem("amethyst_knife",
            new KnifeItem(ModToolMaterials.AMETHYST,0.5f, -2.0f, new FabricItemSettings()));
    public static final Item BLACK_PEPPER_CORN = registerItem("black_pepper_corn", new AliasedBlockItem(ModBlocks.BLACK_PEPPER_CROP,
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(1).build())));
    public static final Item BLACK_PEPPER_DUST = registerItem("black_pepper_dust", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(1).build())));
    public static final Item CREAM_BUCKET = registerItem("cream_bucket",
            new BucketItem(ModFluid.STILL_CREAM,new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));
    public static final Item CHERRY_BOMB = registerItem("cherry_bomb",new CherryBombItem(new FabricItemSettings().maxCount(16)));
    public static final Item APPLE_PUDDING = registerItem("apple_pudding", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(5).saturationModifier(0.4F).snack().build())));
    public static final Item BRAISED_SHRIMP_BALL = registerItem("braised_shrimp_ball", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(9).saturationModifier(0.8F).meat().build())));
    public static final Item MATCHA_PUDDING = registerItem("matcha_pudding", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3F).snack()
                    .statusEffect(new StatusEffectInstance(StatusEffects.LUCK,600,0),1.0f).build())));
    public static final Item SUNFLOWER_SEED = registerItem("sunflower_seed", new SunFlowerSeedItem());
    public static final Item TRUFFLE_EGG_TART = registerItem("truffle_egg_tart", new ModStewItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).build())));
    public static final Item ICE_BRICK = registerItem("ice_brick", new Item(new FabricItemSettings()));
    public static final Item PUDDING_WIP_1 = registerItem("pudding_wip_1", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).build())));
    public static final Item PUDDING_WIP_2 = registerItem("pudding_wip_2", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build())));
    public static final Item MOUSSE_WIP = registerItem("mousse_wip", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build())));
    public static final Item KNEADING_STICK = registerItem("kneading_stick", new KneadingStickItem(ToolMaterials.WOOD,2.5f,-2.5f,
            new FabricItemSettings()));
    public static final Item WHEAT_FLOUR = registerItem("wheat_flour", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER,200,0),0.5f).build())));
    public static final Item SUNFLOWER_SEED_PEEL = registerItem("sunflower_seed_peel", new Item(new FabricItemSettings()));
    public static final Item SECTIONED_SAUSAGE = registerItem("sectioned_sausage", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5F).meat().build())));
    public static final Item SUNFLOWER_SEED_PULP = registerItem("sunflower_seed_pulp", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).snack().build())));
    public static final Item CROWBAR = registerItem("crowbar", new CrowbarItem(ToolMaterials.IRON,7.5f,-3.6f));
    public static final Item SPATULA = registerItem("spatula", new SpatulaItem(ToolMaterials.IRON,2.5f,-2.8f));
    public static final Item ROASTED_SUNFLOWER_SEED = registerItem("roasted_sunflower_seed", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(3).snack().saturationModifier(0.2f).build())));
    public static final Item FILTER = registerItem("filter", new FilterItem(ModToolMaterials.STRING,
            new FabricItemSettings()));
    public static final Item OIL_IMPURITY = registerItem("oil_impurity",new Item(new FabricItemSettings()));
    public static final Item VEGETABLE_OIL_BOTTLE = registerItem("vegetable_oil_bottle",new Item(new FabricItemSettings()
            .maxCount(16).recipeRemainder(Items.GLASS_BOTTLE)));
    public static final Item VEGETABLE_OIL_BUCKET = registerItem("vegetable_oil_bucket",new BucketItem(ModFluid.STILL_VEGETABLE_OIL,new FabricItemSettings()
            .maxCount(1).recipeRemainder(Items.BUCKET)));
    public static final Item EMPTY_CAKE = registerItem("empty_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1f).build())
    ));
    public static final Item BLUE_ORCHID_FLOWER_CAKE = registerItem("blue_orchid_flower_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION,10,0),1.0f).build())
    ));
    public static final Item CHERRY_CAKE = registerItem("cherry_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,200,0),1.0f).build())
    ));
    public static final Item LILAC_CAKE = registerItem("lilac_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,200,0),1.0f).build())
    ));
    public static final Item ORANGE_TULIP_CAKE = registerItem("orange_tulip_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,200,0),1.0f).build())
    ));
    public static final Item OXEYE_DAISY_CAKE = registerItem("oxeye_daisy_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,200,0),1.0f).build())
    ));
    public static final Item PINK_TULIP_CAKE = registerItem("pink_tulip_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,100,1),1.0f).build())
    ));
    public static final Item ROSE_CAKE = registerItem("rose_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,100,1),1.0f).build())
    ));
    public static final Item SUNFLOWER_CAKE = registerItem("sunflower_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,300,0),1.0f).build())
    ));
    public static final Item WHITE_TULIP_CAKE = registerItem("white_tulip_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,200,0),1.0f).build())
    ));
    public static final Item WITHER_ROSE_CAKE = registerItem("wither_rose_cake", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(10).saturationModifier(0.5f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.WITHER,100,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,300,1),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,400,1),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,600,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,40,0),1.0f)
                    .build())
    ));
    public static final Item RAW_ONION_RING = registerItem("raw_onion_ring", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1f).snack().build())
    ));
    public static final Item ONION_RING = registerItem("onion_ring", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.2f).snack().build())
    ));
    public static final Item FRIED_COD = registerItem("fried_cod", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(10).saturationModifier(0.3f).build())
    ));
    public static final Item FRIED_SALMON = registerItem("fried_salmon", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(11).saturationModifier(0.4f).build())
    ));
    public static final Item FRIED_MILK_WIP = registerItem("fried_milk_wip", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).snack().build())
    ));
    public static final Item FRIED_MILK = registerItem("fried_milk", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.3f).snack().build())
    ));
    public static final Item FRIED_APPLE = registerItem("fried_apple", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3f).snack().build())
    ));
    public static final Item RAW_POTATO_CHIP = registerItem("raw_potato_chip", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).snack().build())
    ));
    public static final Item POTATO_CHIP = registerItem("potato_chip", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).snack().build())
    ));
    public static final Item CHEESE_BALL = registerItem("cheese_ball", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2f).snack().build())
    ));
    public static final Item FRIED_DOUGH_STICK = registerItem("fried_dough_stick", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(5).saturationModifier(0.4f).snack().build())
    ));
    public static final Item RAW_CHICKEN_FILLET = registerItem("raw_chicken_fillet",new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0.4f).snack().meat().build())
    ));
    public static final Item CHICKEN_FILLET = registerItem("chicken_fillet",new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(5).saturationModifier(0.4f).snack().meat().build())
    ));
    public static final Item ANCIENT_SCRAP = registerItem("ancient_scrap",new Item(
            new FabricItemSettings().fireproof())
    );
    public static final Item SILICON_INGOT = registerItem("silicon_ingot",new Item(
            new FabricItemSettings())
    );
    public static final Item SILICON_COMPONENT = registerItem("silicon_component",new Item(
            new FabricItemSettings())
    );
    public static final Item REDSTONE_COMPONENT = registerItem("redstone_component",new Item(
            new FabricItemSettings())
    );
    public static final Item DIAMOND_COMPONENT = registerItem("diamond_component",new Item(
            new FabricItemSettings())
    );
    public static final Item RAW_ICE_CREAM_CONE = registerItem("raw_ice_cream_cone",new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).snack().build())
    ));
    public static final Item ICE_CREAM_CONE = registerItem("ice_cream_cone",new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1f).snack().build())
    ));
    public static final Item ICE_CREAM = registerItem("ice_cream",new IceCreamItem());
    public static final Item STEAMED_BUN = registerItem("steamed_bun",new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1f).snack().build())
    ));
    public static final Item DEEP_FRIED_BUN = registerItem("deep_fried_bun",new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(5).saturationModifier(0.2f).snack().build())
    ));
    public static final Item EGG_BOWL = registerItem("egg_bowl",new ModStewItem(
            new FabricItemSettings().maxCount(16).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())
    ));
    public static final Item CHERRY_EGG = registerItem("cherry_egg",new ModStewItem(
            new FabricItemSettings().maxCount(16).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())
    ));
    public static final Item FISH_EGG = registerItem("fish_egg",new ModStewItem(
            new FabricItemSettings().maxCount(16).food(new FoodComponent.Builder().hunger(5).saturationModifier(0.1f).build())
    ));
    public static final Item STEAMED_EGG = registerItem("steamed_egg",new ModStewItem(
            new FabricItemSettings().maxCount(16).food(new FoodComponent.Builder().hunger(5).saturationModifier(0.3f).build())
    ));
    public static final Item STEAMED_CHERRY_EGG = registerItem("steamed_cherry_egg",new ModStewItem(
            new FabricItemSettings().maxCount(16).food(new FoodComponent.Builder().hunger(7).saturationModifier(0.3f).build())
    ));
    public static final Item STEAMED_FISH_EGG = registerItem("steamed_fish_egg",new ModStewItem(
            new FabricItemSettings().maxCount(16).food(new FoodComponent.Builder().hunger(10).saturationModifier(0.4f).build())
    ));
    public static final Item BEEF_TOMATO_CUP = registerItem("beef_tomato_cup",new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(13).saturationModifier(0.6f).meat().build())
    ));
    public static final Item BUTTERFLY_CRISP = registerItem("butterfly_crisp",new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).snack().build())
    ));
    public static final Item JAR = registerItem("jar",new Item(
            new FabricItemSettings().maxCount(16))
    );
    public static final Item CARAMEL = registerItem("caramel",new JarItem(
            new FabricItemSettings().maxCount(16).recipeRemainder(ModItems.JAR).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.2f).build()))
    );
    public static final Item CARAMEL_PUDDING = registerItem("caramel_pudding", new BlockItem(ModBlocks.CARAMEL_PUDDING,new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.4F).snack().build())));
    public static final Item CHEESE_BAKED_POTATO = registerItem("cheese_baked_potato", new ModStewItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.4F).snack().build())));
    public static final Item CHEESE_RICE_BALL = registerItem("cheese_rice_ball", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(9).saturationModifier(0.5F).snack().build())));
    public static final Item CHOCOLATE_SAUCE = registerItem("chocolate_sauce", new JarItem(new FabricItemSettings()
            .maxCount(16).food(new FoodComponent.Builder().hunger(5).saturationModifier(0.3F).build())));
    public static final Item SHRIMP_PASTE = registerItem("shrimp_paste", new ModStewItem(new FabricItemSettings()
            .maxCount(16).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).build())));
    public static final Item MIXED_SHRIMP_PASTE = registerItem("mixed_shrimp_paste", new ModStewItem(new FabricItemSettings()
            .maxCount(16).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1F).build())));
    public static final Item DEEP_FRIED_SHRIMP_CAKE = registerItem("deep_fried_shrimp_cake", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(9).saturationModifier(0.3F).build())));
    public static final Item SEAWEED_FRIED_SHRIMP_CAKE = registerItem("seaweed_fried_shrimp_cake", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(11).saturationModifier(0.4F).build())));
    public static final Item DONUT = registerItem("donut", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(5).saturationModifier(0.2F).build())));
    public static final Item CHERRY_PUDDING = registerItem("cherry_pudding", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(5).saturationModifier(0.3F).snack().build())));
    public static final Item ICE_LOLLY = registerItem("ice_lolly", new StickFoodItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2F).build())));
    public static final Item PACKAGING_BAG = registerItem("packaging_bag", new Item(new FabricItemSettings()));
    public static final Item DIRTY_PACKAGING_BAG = registerItem("dirty_packaging_bag", new Item(new FabricItemSettings()));
    public static final Item POTATO_CHIPS = registerItem("potato_chips", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).snack().build())));
    public static final Item DEEP_FRIED_POTATO_CHIPS = registerItem("deep_fried_potato_chips", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3F).snack().build())));
    public static final Item FRENCH_FRIES = registerItem("french_fries", new PackagedItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.3F).snack().build())));
    public static final Item FRIED_CHICKEN = registerItem("fried_chicken", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(11).saturationModifier(0.5F).meat().build())));
    public static final Item FRIED_BROWN_MUSHROOM = registerItem("fried_brown_mushroom", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2F).build())));
    public static final Item FRIED_RED_MUSHROOM = registerItem("fried_red_mushroom", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build())));
    public static final Item CREAM_OF_MUSHROOM_SOUP = registerItem("cream_of_mushroom_soup", new ModStewItem(new FabricItemSettings()
            .maxCount(16).food(new FoodComponent.Builder().hunger(12).saturationModifier(0.5F).build())));
    public static final Item FRENCH_ONION_SOUP = registerItem("french_onion_soup", new ModStewItem(new FabricItemSettings()
            .maxCount(16).food(new FoodComponent.Builder().hunger(10).saturationModifier(0.6F).build())));
    public static final Item CHERRY_ICE_LOLLY = registerItem("cherry_ice_lolly", new StickFoodItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.3F).build())));
    public static final Item MATCHA_ICE_LOLLY = registerItem("matcha_ice_lolly", new StickFoodItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.3F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.LUCK,30*20,0),1.0f)
                    .build())));
    public static final Item WITHER_ICE_LOLLY = registerItem("wither_ice_lolly", new StickFoodItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.3F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.WITHER,200,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,300,1),1.0f)
                    .build())));
    public static final Item GLOW_SQUID_TENTACLE = registerItem("glow_squid_tentacle", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING,100,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,100,0),1.0f)
                    .build())));
    public static final Item GLOW_SQUID_TENTACLE_KEBABS = registerItem("glow_squid_tentacle_kebabs", new StickFoodItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.3F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING,20*20,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,20*20,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,20*20,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING,20*20,0),1.0f)
                    .build())));
    public static final Item RAW_GLOW_SQUID_TENTACLE_KEBABS = registerItem("raw_glow_squid_tentacle_kebabs", new StickFoodItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING,10*20,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,10*20,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,10*20,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING,10*20,0),1.0f)
                    .build())));
    public static final Item SQUID_TENTACLE = registerItem("squid_tentacle", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3F)
                    .build())));
    public static final Item SQUID_TENTACLE_KEBABS = registerItem("squid_tentacle_kebabs", new StickFoodItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.3F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,20*20,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING,20*20,0),1.0f)
                    .build())));
    public static final Item RAW_SQUID_TENTACLE_KEBABS = registerItem("raw_squid_tentacle_kebabs", new StickFoodItem(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,10*20,0),1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING,10*20,0),1.0f)
                    .build())));
    public static final Item STREAKY_PORK = registerItem("streaky_pork", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2F).meat()
                    .build())));

    public static final Item TURNIP = registerItem("turnip",
            new Item(new FabricItemSettings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F)
                            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.BAD_OMEN,100,0),0.03f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,100,0),0.2f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.DARKNESS,100,0),0.2f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.POISON,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE,100,0),0.03f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.UNLUCK,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.WITHER,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,100,0),0.1f)
                            .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING,100,0),0.1f)
                            .alwaysEdible().build())){
                @Override
                public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                    tooltip.add(Text.translatable(ModUtil.TURNIP).formatted(Formatting.DARK_RED));
                    super.appendTooltip(stack, world, tooltip, context);
                }
                @Override
                public ActionResult useOnBlock(ItemUsageContext context) {
                    World world = context.getWorld();
                    BlockPos pos = context.getBlockPos();
                    if (world.getBlockState(pos).getBlock() instanceof ComposterBlock){
                        Objects.requireNonNull(context.getPlayer()).getActiveItem().decrement(1);
                        world.createExplosion(context.getPlayer(), pos.getX(), pos.getY(), pos.getZ(),1.2f,true, World.ExplosionSourceType.BLOCK);
                    }
                    return super.useOnBlock(context);
                }
            });
    public static final Item CHEESE_BURGER = registerItem("cheese_burger", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.4F)
                    .build())));
    public static final Item RAW_DONUT = registerItem("raw_donut", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.2F)
                    .build())));
    public static final Item CHOCOLATE_DONUT = registerItem("chocolate_donut", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.3F)
                    .build())));
    public static final Item ROAST_STREAKY_PORK = registerItem("roast_streaky_pork", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.5F).meat()
                    .build())));
    public static final Item PORK_CHOP_BURGER = registerItem("pork_chop_burger", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(18).saturationModifier(0.5F).meat()
                    .build())));
    public static final Item FRIED_COD_NUGGET = registerItem("fried_cod_nugget", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.3F)
                    .build())));
    public static final Item PORK_RIBS = registerItem("pork_ribs", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).meat()
                    .build())));
    public static final Item PORK_HOOF = registerItem("pork_hoof", new Item(new FabricItemSettings()
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1F).meat()
                    .build())));
    public static final Item GARLIC = registerItem("garlic", new AliasedBlockItem(ModBlocks.GARLIC_CROP,
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build())));
    public static final Item GARLIC_PETAL = registerItem("garlic_petal", new Item(
            new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0.3f).build())));
    public static final Item ELECTRIC_WHISK = registerItem("electric_whisk",new ElectricWhiskItem());
    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Bakingdelight.MOD_ID,name),item);
    }

    public static void registerModItems(){
        Bakingdelight.LOGGER.info("Registering Mod Items for " + Bakingdelight.MOD_ID);
    }
}
