package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.entity.EntityEquipmentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

public class ModLootTableModifies {
    public static final RegistryKey<LootTable> SQUID_ID
            = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("minecraft", "entities/squid"));
    public static final RegistryKey<LootTable> GLOW_SQUID_ID
            = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("minecraft", "entities/glow_squid"));
    public static final RegistryKey<LootTable> FISH_ID
            = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("minecraft", "gameplay/fishing/fish"));

    public static void modifyLootTables () {
        LootTableEvents.MODIFY.register(ModLootTableModifies::modifyLootTables);
    }

    private static void modifyLootTables(RegistryKey<LootTable> key,
                                         LootTable.Builder tableBuilder,
                                         LootTableSource source,
                                         RegistryWrapper.WrapperLookup registries) {
        if (SQUID_ID == key && source.isBuiltin()) {
            LootPool.Builder pool = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(1.0f))
                    .with(ItemEntry.builder(ModItems.SQUID));
            tableBuilder.pool(pool);
        }
        if (SQUID_ID == key && source.isBuiltin()){
            LootPool.Builder pool = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.DIRECT_ATTACKER,
                            new EntityPredicate.Builder().equipment(EntityEquipmentPredicate.Builder.create()
                                    .mainhand(ItemPredicate.Builder.create().tag(TagKeys.AMETHYST_TOOLS)).build()).build()))
                    .with(ItemEntry.builder(ModItems.CUTTLEBONE));
            tableBuilder.pool(pool);
        }
        if (GLOW_SQUID_ID == key && source.isBuiltin()) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(1.0f))
                    .with(ItemEntry.builder(ModItems.GLOW_SQUID));
            tableBuilder.pool(poolBuilder);
        }
        if (GLOW_SQUID_ID == key && source.isBuiltin()){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.DIRECT_ATTACKER,
                            new EntityPredicate.Builder().equipment(EntityEquipmentPredicate.Builder.create()
                                    .mainhand(ItemPredicate.Builder.create().tag(TagKeys.AMETHYST_TOOLS)).build()).build()))
                    .with(ItemEntry.builder(ModItems.GLOW_CUTTLEBONE));
            tableBuilder.pool(poolBuilder);
        }
        if (Blocks.PODZOL.getLootTableKey() == key && source.isBuiltin()) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.1F))
                    .with(ItemEntry.builder(ModItems.BLACK_TRUFFLE));
            tableBuilder.pool(poolBuilder);
        }
        if (Blocks.PODZOL.getLootTableKey() == key && source.isBuiltin()) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.03F))
                    .with(ItemEntry.builder(ModItems.WHITE_TRUFFLE));
            tableBuilder.pool(poolBuilder);
        }
        if (Blocks.ICE.getLootTableKey() == key && source.isBuiltin()) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .bonusRolls(ConstantLootNumberProvider.create(0.5f))
                    .conditionally(RandomChanceLootCondition.builder(0.3F))
                    .with(ItemEntry.builder(ModItems.ICE_BRICK));
            tableBuilder.pool(poolBuilder);
        }
        if (Blocks.PACKED_ICE.getLootTableKey() == key && source.isBuiltin()) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .bonusRolls(ConstantLootNumberProvider.create(0.5f))
                    .with(ItemEntry.builder(ModItems.ICE_BRICK));
            tableBuilder.pool(poolBuilder);
        }
        if (Blocks.BLUE_ICE.getLootTableKey() == key && source.isBuiltin()) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .bonusRolls(ConstantLootNumberProvider.create(0.5f))
                    .with(ItemEntry.builder(ModItems.ICE_BRICK));
            tableBuilder.pool(poolBuilder);
        }
        if (Blocks.CHERRY_LEAVES.getLootTableKey() == key && source.isBuiltin()) {
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.3F))
                    .with(ItemEntry.builder(ModItems.CHERRY));
            tableBuilder.pool(poolBuilder);
        }
        if(LootTables.SHIPWRECK_SUPPLY_CHEST == key && source.isBuiltin()){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(2))
                    .conditionally(RandomChanceLootCondition.builder(0.8f))
                    .with(ItemEntry.builder(ModItems.BLACK_PEPPER_CORN))
                    .with(ItemEntry.builder(ModItems.BUTTER));
            tableBuilder.pool(poolBuilder);
        }
        if(LootTables.VILLAGE_SAVANNA_HOUSE_CHEST == key && source.isBuiltin()){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.8f))
                    .with(ItemEntry.builder(ModItems.BLACK_PEPPER_CORN))
                    .with(ItemEntry.builder(ModItems.BUTTER));
            tableBuilder.pool(poolBuilder);
        }
        if(LootTables.VILLAGE_DESERT_HOUSE_CHEST == key && source.isBuiltin()){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.8f))
                    .with(ItemEntry.builder(ModItems.BLACK_PEPPER_CORN))
                    .with(ItemEntry.builder(ModItems.BUTTER));
            tableBuilder.pool(poolBuilder);
        }
        if(LootTables.ABANDONED_MINESHAFT_CHEST == key && source.isBuiltin()){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.4f))
                    .with(ItemEntry.builder(ModItems.IRON_WHISK));
            tableBuilder.pool(poolBuilder);
        }
        if(FISH_ID == key && source.isBuiltin()){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.4f))
                    .with(ItemEntry.builder(ModItems.PRAWN));
            tableBuilder.pool(poolBuilder);
        }
        if(LootTables.VILLAGE_BUTCHER_CHEST == key && source.isBuiltin()){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.8f))
                    .with(ItemEntry.builder(ModItems.GARLIC))
                    .with(ItemEntry.builder(ModItems.BUTTER));
            tableBuilder.pool(poolBuilder);
        }
        if(LootTables.VILLAGE_PLAINS_CHEST == key && source.isBuiltin()){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1))
                    .conditionally(RandomChanceLootCondition.builder(0.7f))
                    .with(ItemEntry.builder(ModItems.GARLIC))
                    .with(ItemEntry.builder(ModItems.GARLIC_PETAL))
                    .with(ItemEntry.builder(ModItems.BUTTER));
            tableBuilder.pool(poolBuilder);
        }
        if(LootTables.PILLAGER_OUTPOST_CHEST == key && source.isBuiltin()){
            LootPool.Builder poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(2))
                    .conditionally(RandomChanceLootCondition.builder(0.5f))
                    .with(ItemEntry.builder(ModItems.REDSTONE_COMPONENT))
                    .with(ItemEntry.builder(ModItems.GARLIC_PETAL))
                    .with(ItemEntry.builder(ModItems.SILICON_INGOT));
            tableBuilder.pool(poolBuilder);
        }
    }
}
