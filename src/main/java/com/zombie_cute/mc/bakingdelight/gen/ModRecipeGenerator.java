package com.zombie_cute.mc.bakingdelight.gen;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.ForgeTagKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;

import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModBlocks.FISH_AND_CHIPS_ITEM, 1)
                .input(Items.BOWL)
                .input(ModItems.FRIED_COD_NUGGET)
                .input(ModItems.FRIED_COD_NUGGET)
                .input(ModItems.FRENCH_FRIES)
                .input(ModItems.FRENCH_FRIES)
                .criterion(FabricRecipeProvider.hasItem(ModItems.DEEP_FRIED_POTATO_CHIPS),
                        FabricRecipeProvider.conditionsFromItem(ModItems.DEEP_FRIED_POTATO_CHIPS))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.RAW_SQUID_TENTACLE_KEBABS, 1)
                .input(Items.STICK)
                .input(ModItems.SQUID_TENTACLE)
                .input(ModItems.SQUID_TENTACLE)
                .input(ModItems.CUTTLEBONE)
                .input(ModItems.BLACK_PEPPER_DUST)
                .criterion(FabricRecipeProvider.hasItem(ModItems.SQUID_TENTACLE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.SQUID_TENTACLE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.RAW_GLOW_SQUID_TENTACLE_KEBABS, 1)
                .input(Items.STICK)
                .input(ModItems.GLOW_SQUID_TENTACLE)
                .input(ModItems.GLOW_SQUID_TENTACLE)
                .input(ModItems.GLOW_CUTTLEBONE)
                .input(ModItems.BLACK_PEPPER_DUST)
                .criterion(FabricRecipeProvider.hasItem(ModItems.GLOW_SQUID_TENTACLE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.GLOW_SQUID_TENTACLE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.FRENCH_FRIES, 1)
                .input(ModItems.PACKAGING_BAG)
                .input(ModItems.DEEP_FRIED_POTATO_CHIPS)
                .input(ModItems.BLACK_PEPPER_DUST)
                .input(ModItems.BLACK_PEPPER_DUST)
                .criterion(FabricRecipeProvider.hasItem(ModItems.DEEP_FRIED_POTATO_CHIPS),
                        FabricRecipeProvider.conditionsFromItem(ModItems.DEEP_FRIED_POTATO_CHIPS))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PACKAGING_BAG, 3)
                .input(Items.PAPER)
                .input(Items.PAPER)
                .input(Items.PAPER)
                .input(Items.RED_DYE)
                .criterion(FabricRecipeProvider.hasItem(Items.PAPER),
                        FabricRecipeProvider.conditionsFromItem(Items.PAPER))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SEAWEED_FRIED_SHRIMP_CAKE, 1)
                .input(Items.DRIED_KELP)
                .input(ModItems.DEEP_FRIED_SHRIMP_CAKE)
                .input(Items.DRIED_KELP)
                .criterion(FabricRecipeProvider.hasItem(ModItems.DEEP_FRIED_SHRIMP_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.DEEP_FRIED_SHRIMP_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.MIXED_SHRIMP_PASTE, 1)
                .input(Items.CARROT)
                .input(ModItems.BLACK_PEPPER_CORN)
                .input(ModItems.MIXED_DOUGH)
                .input(ModItems.SHRIMP_PASTE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.SHRIMP_PASTE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.SHRIMP_PASTE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CHOCOLATE_SAUCE, 1)
                .input(Items.COCOA_BEANS)
                .input(ForgeTagKeys.MILKS)
                .input(Items.SUGAR)
                .input(ModItems.JAR)
                .criterion(FabricRecipeProvider.hasItem(Items.COCOA_BEANS),
                        FabricRecipeProvider.conditionsFromItem(Items.COCOA_BEANS))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CHERRY_EGG, 1)
                .input(ModItems.EGG_BOWL)
                .input(ModItems.CHERRY)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EGG_BOWL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EGG_BOWL))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.FISH_EGG, 1)
                .input(ModItems.EGG_BOWL)
                .input(ForgeTagKeys.RAW_FISHES)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EGG_BOWL),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EGG_BOWL))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.RAW_ICE_CREAM_CONE, 4)
                .input(Items.SUGAR)
                .input(Items.MILK_BUCKET)
                .input(ModItems.MIXED_DOUGH)
                .input(ModItems.MIXED_DOUGH)
                .criterion(FabricRecipeProvider.hasItem(ModItems.MIXED_DOUGH),
                        FabricRecipeProvider.conditionsFromItem(ModItems.MIXED_DOUGH))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CHERRY, 9)
                .input(ModBlocks.BOXED_CHERRIES)
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.BOXED_CHERRIES),
                        FabricRecipeProvider.conditionsFromItem(ModBlocks.BOXED_CHERRIES))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SILICON_INGOT, 9)
                .input(ModBlocks.SILICON_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.SILICON_BLOCK),
                        FabricRecipeProvider.conditionsFromItem(ModBlocks.SILICON_BLOCK))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BLACK_PEPPER_DUST, 1)
                .input(ForgeTagKeys.CROP_BLACK_PEPPER)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BLACK_PEPPER_CORN),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BLACK_PEPPER_CORN))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CREAM, 3)
                .input(ModItems.CREAM_BUCKET)
                .input(Items.BOWL).input(Items.BOWL).input(Items.BOWL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CREAM_BUCKET),
                        FabricRecipeProvider.conditionsFromItem(ModItems.CREAM_BUCKET))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CHERRY_CREAM, 1)
                .input(ModItems.CREAM)
                .input(ModItems.CHERRY).input(ModItems.CHERRY)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CREAM),
                        FabricRecipeProvider.conditionsFromItem(ModItems.CREAM))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CHOCOLATE_CREAM, 1)
                .input(ModItems.CREAM)
                .input(Items.COCOA_BEANS).input(Items.COCOA_BEANS)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CREAM),
                        FabricRecipeProvider.conditionsFromItem(ModItems.CREAM))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.GOLDEN_APPLE_CREAM, 1)
                .input(ModItems.CREAM)
                .input(Items.GOLDEN_APPLE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CREAM),
                        FabricRecipeProvider.conditionsFromItem(ModItems.CREAM))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.MATCHA_CREAM, 1)
                .input(ModItems.CREAM)
                .input(ItemTags.LEAVES).input(ItemTags.LEAVES).input(ItemTags.LEAVES).input(ItemTags.LEAVES).input(ItemTags.LEAVES)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CREAM),
                        FabricRecipeProvider.conditionsFromItem(ModItems.CREAM))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BUTTER_BREAD_SLICE, 1)
                .input(ModItems.BUTTER)
                .input(ModItems.BREAD_SLICE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BUTTER),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BUTTER))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.APPLE_CREAM, 1)
                .input(ModItems.CREAM)
                .input(ModItems.APPLE_PETAL).input(ModItems.APPLE_PETAL)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BUTTER),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BUTTER))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.TRUFFLE_EGG_TART, 1)
                .input(ModItems.EGG_TART)
                .input(ModItems.BLACK_TRUFFLE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BLACK_TRUFFLE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BLACK_TRUFFLE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PUDDING_WIP_1, 8)
                .input(Items.EGG)
                .input(Items.MILK_BUCKET)
                .input(Items.SUGAR)
                .input(ModItems.BUTTER)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BUTTER),
                        FabricRecipeProvider.conditionsFromItem(ModItems.BUTTER))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.MOUSSE_WIP, 12)
                .input(Items.EGG)
                .input(ModItems.CREAM_BUCKET)
                .input(Items.SUGAR)
                .input(ModItems.BUTTER)
                .input(Items.WHEAT)
                .input(Items.WHEAT)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CREAM_BUCKET),
                        FabricRecipeProvider.conditionsFromItem(ModItems.CREAM_BUCKET))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.VEGETABLE_OIL_BOTTLE, 3)
                .input(ModItems.VEGETABLE_OIL_BUCKET)
                .input(Items.GLASS_BOTTLE)
                .input(Items.GLASS_BOTTLE)
                .input(Items.GLASS_BOTTLE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.VEGETABLE_OIL_BUCKET),
                        FabricRecipeProvider.conditionsFromItem(ModItems.VEGETABLE_OIL_BUCKET))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.EMPTY_CAKE, 4)
                .input(ForgeTagKeys.C_DOUGH)
                .input(ForgeTagKeys.C_DOUGH)
                .input(ForgeTagKeys.C_DOUGH)
                .input(Items.MILK_BUCKET)
                .criterion(FabricRecipeProvider.hasItem(Items.MILK_BUCKET),
                        FabricRecipeProvider.conditionsFromItem(Items.MILK_BUCKET))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BLUE_ORCHID_FLOWER_CAKE)
                .input(Items.BLUE_ORCHID)
                .input(Items.SUGAR)
                .input(ModItems.EMPTY_CAKE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.CHERRY_CAKE)
                .input(ModItems.CHERRY)
                .input(Items.SUGAR)
                .input(ModItems.EMPTY_CAKE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.LILAC_CAKE)
                .input(Items.LILAC)
                .input(Items.SUGAR)
                .input(ModItems.EMPTY_CAKE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.ORANGE_TULIP_CAKE)
                .input(Items.ORANGE_TULIP)
                .input(Items.SUGAR)
                .input(ModItems.EMPTY_CAKE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.OXEYE_DAISY_CAKE)
                .input(Items.OXEYE_DAISY)
                .input(Items.SUGAR)
                .input(ModItems.EMPTY_CAKE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PINK_TULIP_CAKE)
                .input(Items.PINK_TULIP)
                .input(Items.SUGAR)
                .input(ModItems.EMPTY_CAKE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.ROSE_CAKE)
                .input(Items.ROSE_BUSH)
                .input(Items.SUGAR)
                .input(ModItems.EMPTY_CAKE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.SUNFLOWER_CAKE)
                .input(Items.SUNFLOWER)
                .input(Items.SUGAR)
                .input(ModItems.EMPTY_CAKE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.WHITE_TULIP_CAKE)
                .input(Items.WHITE_TULIP)
                .input(Items.SUGAR)
                .input(ModItems.EMPTY_CAKE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.WITHER_ROSE_CAKE)
                .input(Items.WITHER_ROSE)
                .input(Items.SUGAR)
                .input(ModItems.EMPTY_CAKE)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.FRIED_MILK_WIP,3)
                .input(Items.MILK_BUCKET)
                .input(Items.SUGAR)
                .input(ForgeTagKeys.C_FLOUR)
                .input(Items.EGG)
                .criterion(FabricRecipeProvider.hasItem(ModItems.EMPTY_CAKE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.EMPTY_CAKE))
                .offerTo(exporter);









        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModBlocks.MASHED_POTATO_BLOCK)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A',ModItems.MASHED_POTATO)
                .criterion(FabricRecipeProvider.hasItem(ModItems.MASHED_POTATO),
                        FabricRecipeProvider.conditionsFromItem(ModItems.MASHED_POTATO))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.WOODEN_WHISK)
                .pattern("I")
                .pattern("N")
                .pattern("S")
                .input('I', ItemTags.PLANKS)
                .input('N', ItemTags.WOODEN_SLABS)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.COPPER_WHISK)
                .pattern("I")
                .pattern("N")
                .pattern("S")
                .input('I', Items.COPPER_BLOCK)
                .input('N', Items.COPPER_INGOT)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.IRON_WHISK)
                .pattern("I")
                .pattern("N")
                .pattern("S")
                .input('I', Items.IRON_INGOT)
                .input('N', Items.IRON_NUGGET)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.GOLDEN_WHISK)
                .pattern("I")
                .pattern("N")
                .pattern("S")
                .input('I', Items.GOLD_INGOT)
                .input('N', Items.GOLD_NUGGET)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.GOLD_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.AMETHYST_WHISK)
                .pattern("III")
                .pattern("INI")
                .pattern(" S ")
                .input('I', Items.AMETHYST_SHARD)
                .input('N', Items.REDSTONE_BLOCK)
                .input('S', Items.IRON_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.DIAMOND_WHISK)
                .pattern("I")
                .pattern("N")
                .pattern("S")
                .input('I', Items.DIAMOND)
                .input('N', Items.AMETHYST_SHARD)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.DIAMOND),
                        FabricRecipeProvider.conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.AMETHYST_AXE)
                .pattern("AB")
                .pattern("AR")
                .pattern(" I")
                .input('I', Items.IRON_INGOT)
                .input('A', Items.AMETHYST_SHARD)
                .input('R', Items.REDSTONE_BLOCK)
                .input('B', Items.AMETHYST_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.AMETHYST_HOE)
                .pattern("AB")
                .pattern(" R")
                .pattern(" I")
                .input('I', Items.IRON_INGOT)
                .input('A', Items.AMETHYST_SHARD)
                .input('R', Items.REDSTONE_BLOCK)
                .input('B', Items.AMETHYST_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.AMETHYST_SHOVEL)
                .pattern("ABA")
                .pattern("ARA")
                .pattern(" I ")
                .input('I', Items.IRON_INGOT)
                .input('A', Items.AMETHYST_SHARD)
                .input('R', Items.REDSTONE_BLOCK)
                .input('B', Items.AMETHYST_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.AMETHYST_PICKAXE)
                .pattern("ABA")
                .pattern(" R ")
                .pattern(" I ")
                .input('I', Items.IRON_INGOT)
                .input('A', Items.AMETHYST_SHARD)
                .input('R', Items.REDSTONE_BLOCK)
                .input('B', Items.AMETHYST_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.AMETHYST_SWORD)
                .pattern("B")
                .pattern("R")
                .pattern("I")
                .input('I', Items.IRON_INGOT)
                .input('R', Items.REDSTONE_BLOCK)
                .input('B', Items.AMETHYST_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.COPPER_KNIFE)
                .pattern("C")
                .pattern("S")
                .input('C', Items.COPPER_BLOCK)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.COPPER_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.AMETHYST_KNIFE)
                .pattern("  A")
                .pattern("RA ")
                .pattern("IR ")
                .input('A', Items.AMETHYST_SHARD)
                .input('R', Items.REDSTONE)
                .input('I', Items.IRON_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.AMETHYST_SHARD),
                        FabricRecipeProvider.conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GLASS_BOWL)
                .pattern("G G")
                .pattern("GGG")
                .input('G', Items.GLASS)
                .criterion(FabricRecipeProvider.hasItem(Items.GLASS),
                        FabricRecipeProvider.conditionsFromItem(Items.GLASS))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CHERRY_BOMB)
                .pattern(" G ")
                .pattern("GCG")
                .pattern(" G ")
                .input('G', Items.GUNPOWDER)
                .input('C', ModItems.CHERRY)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CHERRY),
                        FabricRecipeProvider.conditionsFromItem(ModItems.CHERRY))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ADVANCE_FURNACE)
                .pattern("IFI")
                .pattern("CGC")
                .pattern("CTC")
                .input('F', Items.FURNACE)
                .input('T', Items.IRON_TRAPDOOR)
                .input('G', Items.COPPER_BLOCK)
                .input('I', Items.IRON_INGOT)
                .input('C', Items.DEEPSLATE)
                .criterion(FabricRecipeProvider.hasItem(Items.FURNACE),
                        FabricRecipeProvider.conditionsFromItem(Items.FURNACE))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FREEZER)
                .pattern("GGG")
                .pattern("GPG")
                .pattern("GRB")
                .input('G', Items.IRON_INGOT)
                .input('B', Items.IRON_TRAPDOOR)
                .input('P', Items.CHEST)
                .input('R', ModItems.REDSTONE_COMPONENT)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.KNEADING_STICK)
                .pattern("  S")
                .pattern(" P ")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('P', ItemTags.PLANKS)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BAKING_TRAY)
                .pattern("B B")
                .pattern("III")
                .input('I', Items.IRON_INGOT)
                .input('B', Items.BRICK)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.DEEP_FRYER)
                .pattern("  I")
                .pattern("IKI")
                .pattern("III")
                .input('I', Items.IRON_INGOT)
                .input('K', ModBlocks.DEEP_FRY_BASKET)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CROWBAR)
                .pattern(" NB")
                .pattern(" IN")
                .pattern("I  ")
                .input('I', Items.STICK)
                .input('N', Items.IRON_INGOT)
                .input('B', Items.IRON_HOE)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_HOE),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_HOE))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.SPATULA)
                .pattern(" NB")
                .pattern(" IN")
                .pattern("I  ")
                .input('I', Items.STICK)
                .input('N', Items.IRON_NUGGET)
                .input('B', Items.IRON_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.WOODEN_BASIN)
                .pattern("P P")
                .pattern("PSP")
                .input('P', ItemTags.PLANKS)
                .input('S', ItemTags.WOODEN_SLABS)
                .criterion(FabricRecipeProvider.hasItem(Items.STICK),
                        FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.FILTER)
                .pattern("SSS")
                .pattern("SSS")
                .input('S', Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(Items.STRING),
                        FabricRecipeProvider.conditionsFromItem(Items.STRING))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GAS_CANISTER)
                .pattern(" C ")
                .pattern("ITI")
                .pattern("III")
                .input('C', Items.COMPASS)
                .input('I', Items.IRON_INGOT)
                .input('T', Items.IRON_TRAPDOOR)
                .criterion(FabricRecipeProvider.hasItem(Items.COMPASS),
                        FabricRecipeProvider.conditionsFromItem(Items.COMPASS))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BIOGAS_DIGESTER_CONTROLLER)
                .pattern("DAD")
                .pattern("ICI")
                .pattern("PRP")
                .input('A', Items.COMPASS)
                .input('I', Items.IRON_INGOT)
                .input('P', Items.COPPER_INGOT)
                .input('C', Items.COPPER_BLOCK)
                .input('R', Items.REDSTONE_BLOCK)
                .input('D', Items.QUARTZ)
                .criterion(FabricRecipeProvider.hasItem(Items.QUARTZ),
                        FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BIOGAS_DIGESTER_IO)
                .pattern("PHP")
                .pattern("ICI")
                .pattern("PRP")
                .input('H', Items.HOPPER)
                .input('I', Items.IRON_INGOT)
                .input('P', Items.COPPER_INGOT)
                .input('C', Items.CHEST)
                .input('R', Items.REDSTONE_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GAS_COOKING_STOVE)
                .pattern("IPI")
                .pattern("SQS")
                .pattern("CRC")
                .input('S', Items.IRON_INGOT)
                .input('I', Items.FLINT_AND_STEEL)
                .input('P', Items.IRON_TRAPDOOR)
                .input('C', Items.COPPER_INGOT)
                .input('R', Items.REDSTONE_BLOCK)
                .input('Q', Items.QUARTZ)
                .criterion(FabricRecipeProvider.hasItem(Items.QUARTZ),
                        FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.DEEP_FRY_BASKET)
                .pattern("BBS")
                .pattern("BB ")
                .input('S', Items.STICK)
                .input('B', Items.IRON_BARS)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_BARS),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_BARS))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.KITCHEN_UTENSIL_HOLDER,4)
                .pattern("QQQ")
                .pattern("DDD")
                .input('Q', Items.QUARTZ_SLAB)
                .input('D', Items.POLISHED_DEEPSLATE_SLAB)
                .criterion(FabricRecipeProvider.hasItem(Items.COBBLED_DEEPSLATE),
                        FabricRecipeProvider.conditionsFromItem(Items.COBBLED_DEEPSLATE))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CUISINE_TABLE)
                .pattern("SAQ")
                .pattern("PCP")
                .pattern("SSS")
                .input('Q', Items.QUARTZ)
                .input('C', Items.TRAPPED_CHEST)
                .input('A', Items.POLISHED_ANDESITE)
                .input('S', ItemTags.WOODEN_SLABS)
                .input('P', ItemTags.PLANKS)
                .criterion(FabricRecipeProvider.hasItem(Items.TRAPPED_CHEST),
                        FabricRecipeProvider.conditionsFromItem(Items.TRAPPED_CHEST))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ANDESITE_CABINET)
                .pattern("AAA")
                .pattern("QCQ")
                .pattern("DDD")
                .input('Q', Items.QUARTZ_BLOCK)
                .input('C', Items.BARREL)
                .input('A', Items.POLISHED_ANDESITE_SLAB)
                .input('D', Items.POLISHED_DEEPSLATE_SLAB)
                .criterion(FabricRecipeProvider.hasItem(Items.QUARTZ),
                        FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.GRANITE_CABINET)
                .pattern("AAA")
                .pattern("QCQ")
                .pattern("DDD")
                .input('Q', Items.QUARTZ_BLOCK)
                .input('C', Items.BARREL)
                .input('A', Items.POLISHED_GRANITE_SLAB)
                .input('D', Items.POLISHED_DEEPSLATE_SLAB)
                .criterion(FabricRecipeProvider.hasItem(Items.QUARTZ),
                        FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.DIORITE_CABINET)
                .pattern("AAA")
                .pattern("QCQ")
                .pattern("DDD")
                .input('Q', Items.QUARTZ_BLOCK)
                .input('C', Items.BARREL)
                .input('A', Items.POLISHED_DIORITE_SLAB)
                .input('D', Items.POLISHED_DEEPSLATE_SLAB)
                .criterion(FabricRecipeProvider.hasItem(Items.QUARTZ),
                        FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.DEEPSLATE_CABINET)
                .pattern("DDD")
                .pattern("QCQ")
                .pattern("DDD")
                .input('Q', Items.QUARTZ_BLOCK)
                .input('C', Items.BARREL)
                .input('D', Items.POLISHED_DEEPSLATE_SLAB)
                .criterion(FabricRecipeProvider.hasItem(Items.QUARTZ),
                        FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BASALT_CABINET)
                .pattern("AAA")
                .pattern("QCQ")
                .pattern("DDD")
                .input('Q', Items.QUARTZ_BLOCK)
                .input('C', Items.BARREL)
                .input('A', Items.POLISHED_BASALT)
                .input('D', Items.POLISHED_DEEPSLATE_SLAB)
                .criterion(FabricRecipeProvider.hasItem(Items.QUARTZ),
                        FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BLACKSTONE_CABINET)
                .pattern("AAA")
                .pattern("QCQ")
                .pattern("DDD")
                .input('Q', Items.QUARTZ_BLOCK)
                .input('C', Items.BARREL)
                .input('A', Items.POLISHED_BLACKSTONE_BRICK_SLAB)
                .input('D', Items.POLISHED_DEEPSLATE_SLAB)
                .criterion(FabricRecipeProvider.hasItem(Items.QUARTZ),
                        FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.OBSIDIAN_CABINET)
                .pattern("AAA")
                .pattern("QCQ")
                .pattern("DDD")
                .input('Q', Items.QUARTZ_BLOCK)
                .input('C', Items.BARREL)
                .input('A', Items.OBSIDIAN)
                .input('D', Items.POLISHED_DEEPSLATE_SLAB)
                .criterion(FabricRecipeProvider.hasItem(Items.QUARTZ),
                        FabricRecipeProvider.conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Blocks.ANCIENT_DEBRIS)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', ModItems.ANCIENT_SCRAP)
                .criterion(FabricRecipeProvider.hasItem(ModItems.ANCIENT_SCRAP),
                        FabricRecipeProvider.conditionsFromItem(ModItems.ANCIENT_SCRAP))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ELECTRICIANS_DESK)
                .pattern("PCF")
                .pattern("WWW")
                .pattern("IRI")
                .input('P', Items.PAPER)
                .input('C', Items.COPPER_INGOT)
                .input('F', Items.FEATHER)
                .input('W', ItemTags.PLANKS)
                .input('I', Items.IRON_INGOT)
                .input('R', Items.REDSTONE)
                .criterion(FabricRecipeProvider.hasItem(Items.REDSTONE),
                        FabricRecipeProvider.conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.SILICON_BLOCK)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', ModItems.SILICON_INGOT)
                .criterion(FabricRecipeProvider.hasItem(ModItems.SILICON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(ModItems.SILICON_INGOT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModBlocks.BOXED_CHERRIES)
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .input('A', ModItems.CHERRY)
                .criterion(FabricRecipeProvider.hasItem(ModItems.CHERRY),
                        FabricRecipeProvider.conditionsFromItem(ModItems.CHERRY))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FAN_BLADE)
                .pattern(" A ")
                .pattern(" B ")
                .pattern("A A")
                .input('A', Items.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .input('B', Items.IRON_INGOT)
                .criterion(FabricRecipeProvider.hasItem(Items.HEAVY_WEIGHTED_PRESSURE_PLATE),
                        FabricRecipeProvider.conditionsFromItem(Items.HEAVY_WEIGHTED_PRESSURE_PLATE))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BAMBOO_COVER)
                .pattern("PPP")
                .pattern("B B")
                .input('P', Items.BAMBOO_SLAB)
                .input('B', Items.BAMBOO_PLANKS)
                .criterion(FabricRecipeProvider.hasItem(Items.BAMBOO_PLANKS),
                        FabricRecipeProvider.conditionsFromItem(Items.BAMBOO_PLANKS))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BAMBOO_GRATE)
                .pattern("P P")
                .pattern("BPB")
                .input('P', Items.BAMBOO_SLAB)
                .input('B', Items.BAMBOO_PLANKS)
                .criterion(FabricRecipeProvider.hasItem(Items.BAMBOO_PLANKS),
                        FabricRecipeProvider.conditionsFromItem(Items.BAMBOO_PLANKS))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ELECTRIC_STEAMER)
                .pattern("IGI")
                .pattern("IGI")
                .pattern("IRI")
                .input('G', Items.GLASS_PANE)
                .input('I', Items.IRON_INGOT)
                .input('R', ModItems.REDSTONE_COMPONENT)
                .criterion(FabricRecipeProvider.hasItem(ModItems.REDSTONE_COMPONENT),
                        FabricRecipeProvider.conditionsFromItem(ModItems.REDSTONE_COMPONENT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ICE_CREAM_MAKER)
                .pattern("FDL")
                .pattern("R  ")
                .pattern("IPP")
                .input('F', ModBlocks.FREEZER)
                .input('D', Blocks.DISPENSER)
                .input('P', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .input('L', Blocks.LEVER)
                .input('I', Items.IRON_INGOT)
                .input('R', ModItems.REDSTONE_COMPONENT)
                .criterion(FabricRecipeProvider.hasItem(ModItems.REDSTONE_COMPONENT),
                        FabricRecipeProvider.conditionsFromItem(ModItems.REDSTONE_COMPONENT))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.JAR,6)
                .pattern("GWG")
                .pattern("GPG")
                .pattern("GGG")
                .input('G', Items.GLASS)
                .input('P', Items.GLASS_PANE)
                .input('W', ItemTags.WOODEN_SLABS)
                .criterion(FabricRecipeProvider.hasItem(Items.GLASS),
                        FabricRecipeProvider.conditionsFromItem(Items.GLASS))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ELECTRIC_WHISK,1)
                .pattern("IRI")
                .pattern("  W")
                .input('I', Items.IRON_INGOT)
                .input('R', ModItems.REDSTONE_COMPONENT)
                .input('W', ModItems.IRON_WHISK)
                .criterion(FabricRecipeProvider.hasItem(ModItems.REDSTONE_COMPONENT),
                        FabricRecipeProvider.conditionsFromItem(ModItems.REDSTONE_COMPONENT))
                .offerTo(exporter);
    }
}
