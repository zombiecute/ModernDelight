package com.zombie_cute.mc.bakingdelight.gen;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.BlackPepperCropBlock;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootGenerator extends FabricBlockLootTableProvider {

    public ModBlockLootGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput,registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.GLASS_BOWL);
        addDrop(ModBlocks.FREEZER);
        addDrop(ModBlocks.WHEAT_DOUGH);
        addDrop(ModBlocks.PIZZA_WIP);
        addDrop(ModBlocks.BAKING_TRAY);
        addDrop(ModBlocks.DEEP_FRYER);
        addDrop(ModBlocks.ADVANCE_FURNACE);
        addDrop(ModBlocks.OVEN);
        addDrop(ModBlocks.WOODEN_BASIN);
        addDrop(ModBlocks.BIOGAS_DIGESTER_CONTROLLER);
        addDrop(ModBlocks.BIOGAS_DIGESTER_IO);
        addDrop(ModBlocks.GAS_COOKING_STOVE);
        addDrop(ModBlocks.BURNING_GAS_COOKING_STOVE,ModBlocks.GAS_COOKING_STOVE);
        addDrop(ModBlocks.DEEP_FRY_BASKET);
        addDrop(ModBlocks.KITCHEN_UTENSIL_HOLDER);
        addDrop(ModBlocks.CUISINE_TABLE);
        addDrop(ModBlocks.ANDESITE_CABINET);
        addDrop(ModBlocks.BLACKSTONE_CABINET);
        addDrop(ModBlocks.BASALT_CABINET);
        addDrop(ModBlocks.DEEPSLATE_CABINET);
        addDrop(ModBlocks.GRANITE_CABINET);
        addDrop(ModBlocks.DIORITE_CABINET);
        addDrop(ModBlocks.OBSIDIAN_CABINET);
        addDrop(ModBlocks.PHOTOVOLTAIC_GENERATOR);
        addDrop(ModBlocks.AC_DC_CONVERTER);
        addDrop(ModBlocks.FAN_BLADE);
        addDrop(ModBlocks.WIND_TURBINE_CONTROLLER);
        addDrop(ModBlocks.STERLING_ENGINE);
        addDrop(ModBlocks.FARADAY_GENERATOR);
        addDrop(ModBlocks.TESLA_COIL);
        addDrop(ModBlocks.ELECTRICIANS_DESK);
        addDrop(ModBlocks.SILICON_BLOCK);
        addDrop(ModBlocks.BOXED_CHERRIES);
        addDrop(ModBlocks.BAMBOO_COVER);
        addDrop(ModBlocks.BAMBOO_GRATE);
        addDrop(ModBlocks.ELECTRIC_STEAMER);
        addDrop(ModBlocks.ICE_CREAM_MAKER);
        addDrop(ModBlocks.CARAMEL_PUDDING);
        addDrop(ModBlocks.CHARGING_POST);
        addDrop(ModBlocks.FISH_AND_CHIPS,block -> new LootTable.Builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(2))
                        .with(ItemEntry.builder(ModItems.DIRTY_PACKAGING_BAG))
                )
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(Items.BOWL))
                        ));
        addDrop(ModBlocks.WILD_PEPPER_CROP,block -> new LootTable.Builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(createWithoutShearsOrSilkTouchCondition())
                        .with(ItemEntry.builder(ModItems.BLACK_PEPPER_CORN))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f,2.0f))))
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(createWithShearsOrSilkTouchCondition())
                        .with(ItemEntry.builder(ModBlocks.WILD_PEPPER_CROP))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))));
        addDrop(ModBlocks.WILD_GARLIC,block -> new LootTable.Builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(createWithoutShearsOrSilkTouchCondition())
                        .with(ItemEntry.builder(ModItems.GARLIC))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))))
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(createWithShearsOrSilkTouchCondition())
                        .with(ItemEntry.builder(ModBlocks.WILD_GARLIC))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))));

        this.addDrop(ModBlocks.BLACK_PEPPER_CROP,
                this.cropDrops(ModBlocks.BLACK_PEPPER_CROP, Items.STICK, ModItems.BLACK_PEPPER_CORN,
                        BlockStatePropertyLootCondition.builder(ModBlocks.BLACK_PEPPER_CROP)
                                .properties(StatePredicate.Builder.create()
                                        .exactMatch(BlackPepperCropBlock.AGE, 6))));
        this.addDrop(ModBlocks.GARLIC_CROP,
                this.cropDrops(ModBlocks.GARLIC_CROP, ModItems.GARLIC, ModItems.GARLIC,
                        BlockStatePropertyLootCondition.builder(ModBlocks.GARLIC_CROP)
                        .properties(StatePredicate.Builder.create()
                                .exactMatch(BlackPepperCropBlock.AGE, 5))));
    }
}
