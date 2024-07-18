package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.ModTagKeys;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class ModLootTableModifies {
    public static final Identifier SQUID_ID
            = new Identifier("minecraft", "entities/squid");
    public static final Identifier GLOW_SQUID_ID
            = new Identifier("minecraft", "entities/glow_squid");
    public static final Identifier PODZOL_ID
            = new Identifier("minecraft", "blocks/podzol");
    public static final Identifier CHERRY_LEAVES_ID
            = new Identifier("minecraft", "blocks/cherry_leaves");
    public static final Identifier SHIPWRECK_SUPPLY_ID
            = new Identifier("minecraft", "chests/shipwreck_supply");
    public static final Identifier VILLAGE_DESERT_HOUSE_ID
            = new Identifier("minecraft", "chests/village/village_desert_house");
    public static final Identifier VILLAGE_SAVANNA_HOUSE_ID
            = new Identifier("minecraft", "chests/village/village_savanna_house");
    public static final Identifier ABANDONED_MINESHAFT_ID
            = new Identifier("minecraft", "chests/abandoned_mineshaft");
    public static final Identifier FISH_ID
            = new Identifier("minecraft", "gameplay/fishing/fish");
    public static final Identifier BLUE_ICE_ID
            = new Identifier("minecraft", "blocks/blue_ice");
    public static final Identifier ICE_ID
            = new Identifier("minecraft", "blocks/ice");
    public static final Identifier PACKED_ICE_ID
            = new Identifier("minecraft", "blocks/packed_ice");
    public static final Identifier VILLAGE_BUTCHER_ID
            = new Identifier("minecraft", "chests/village/village_butcher");
    public static final Identifier VILLAGE_PLAINS_HOUSE_ID
            = new Identifier("minecraft", "chests/village/village_plains_house");
    public static final Identifier PILLAGER_OUTPOST_ID
            = new Identifier("minecraft", "chests/pillager_outpost");
    public static void modifyLootTables () {
        LootTableEvents.MODIFY.register(ModLootTableModifies::modifyLootTable);
    }

    private static void modifyLootTable(ResourceManager resourceManager, LootManager lootManager, Identifier id, LootTable.Builder tableBuilder, LootTableSource source) {
        if (SQUID_ID.equals(id)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(1.0f))
                    .with(ItemEntry.builder(ModItems.SQUID))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if (SQUID_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                            new EntityPredicate.Builder().equipment(EntityEquipmentPredicate.Builder.create()
                                    .mainhand(ItemPredicate.Builder.create().tag(ModTagKeys.AMETHYST_TOOLS).build()).build()).build()))
                    .with(ItemEntry.builder(ModItems.CUTTLEBONE))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if (GLOW_SQUID_ID.equals(id)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(1.0f))
                    .with(ItemEntry.builder(ModItems.GLOW_SQUID))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if (GLOW_SQUID_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
                            new EntityPredicate.Builder().equipment(EntityEquipmentPredicate.Builder.create()
                                    .mainhand(ItemPredicate.Builder.create().tag(ModTagKeys.AMETHYST_TOOLS).build()).build()).build()))
                    .with(ItemEntry.builder(ModItems.GLOW_CUTTLEBONE))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if (PODZOL_ID.equals(id)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.1F))
                    .with(ItemEntry.builder(ModItems.BLACK_TRUFFLE))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if (PODZOL_ID.equals(id)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.03F))
                    .with(ItemEntry.builder(ModItems.WHITE_TRUFFLE))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if (ICE_ID.equals(id)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.3F))
                    .with(ItemEntry.builder(ModItems.ICE_BRICK))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.8f, 2.3f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if (PACKED_ICE_ID.equals(id)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.5F))
                    .with(ItemEntry.builder(ModItems.ICE_BRICK))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.8f, 3.3f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if (BLUE_ICE_ID.equals(id)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.7F))
                    .with(ItemEntry.builder(ModItems.ICE_BRICK))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(6.8f, 10.3f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if (CHERRY_LEAVES_ID.equals(id)) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.3F))
                    .with(ItemEntry.builder(ModItems.CHERRY))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if(SHIPWRECK_SUPPLY_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(2))
                    .conditionally(RandomChanceLootCondition.builder(1f))
                    .with(ItemEntry.builder(ModItems.BLACK_PEPPER_CORN))
                    .with(ItemEntry.builder(ModItems.BUTTER))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,2.5f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if(VILLAGE_SAVANNA_HOUSE_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.8f))
                    .with(ItemEntry.builder(ModItems.BLACK_PEPPER_CORN))
                    .with(ItemEntry.builder(ModItems.BUTTER))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,2.5f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if(VILLAGE_DESERT_HOUSE_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.8f))
                    .with(ItemEntry.builder(ModItems.BLACK_PEPPER_CORN))
                    .with(ItemEntry.builder(ModItems.BUTTER))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.8f,2.5f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if(ABANDONED_MINESHAFT_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.4f))
                    .with(ItemEntry.builder(ModItems.IRON_WHISK))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if(FISH_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.4f))
                    .with(ItemEntry.builder(ModItems.PRAWN))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if(VILLAGE_BUTCHER_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.8f))
                    .with(ItemEntry.builder(ModItems.GARLIC))
                    .with(ItemEntry.builder(ModItems.BUTTER))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.2f,3.5f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if(VILLAGE_PLAINS_HOUSE_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.7f))
                    .with(ItemEntry.builder(ModItems.GARLIC))
                    .with(ItemEntry.builder(ModItems.GARLIC_PETAL))
                    .with(ItemEntry.builder(ModItems.BUTTER))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.8f,2.5f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
        if(PILLAGER_OUTPOST_ID.equals(id)){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(2))
                    .conditionally(RandomChanceLootCondition.builder(0.5f))
                    .with(ItemEntry.builder(ModItems.REDSTONE_COMPONENT))
                    .with(ItemEntry.builder(ModItems.GARLIC_PETAL))
                    .with(ItemEntry.builder(ModItems.SILICON_INGOT))
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.8f,2.5f)).build());
            tableBuilder.pool(poolBuilder.build());
        }
    }
}
