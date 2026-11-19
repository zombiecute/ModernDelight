package com.zombie_cute.mc.moderndelight.block.trawler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBaitManager;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBaitPreview;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerLootPreview;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrawlerPreviewTests implements FabricGameTest {
    @GameTest(templateName = EMPTY_STRUCTURE)
    public void serverBaitsAndWaterConditions(TestContext context) {
        var previews = TrawlerLootPreview.create(context.getWorld().getServer());
        var amethyst = previews.stream().filter(p -> p.openWater() && has(p.baits(), Items.AMETHYST_SHARD))
                .findFirst().orElseThrow();
        context.assertTrue(amethyst.intervalTicks() == 10000 && amethyst.consumeChance() == 1,
                "Item bait must retain the server's timing and consumption chance");
        context.assertTrue(has(amethyst.outputs(), Items.ENCHANTED_BOOK) && !has(amethyst.outputs(), Items.BOOK),
                "Fishing treasure must advertise enchanted books, not ordinary books");
        context.assertTrue(previews.stream().anyMatch(p -> p.openWater() && has(p.baits(), ModItems.CHILI_SEEDS)
                        && has(p.outputs(), ModItems.PRAWN)),
                "Tag baits must expose the mod's fish and shrimp loot");
        context.assertTrue(previews.stream().anyMatch(p -> !p.openWater() && has(p.baits(), Items.AMETHYST_SHARD)
                        && has(p.outputs(), Items.LEATHER) && !has(p.outputs(), Items.ENCHANTED_BOOK)),
                "Restricted water must show junk, even with treasure bait");
        context.assertTrue(previews.stream().anyMatch(p -> p.baits().isEmpty() && p.openWater()
                        && has(p.outputs(), Items.COD) && has(p.outputs(), Items.LEATHER)),
                "No bait must show both default fish and junk");
        for (var preview : previews) {
            for (var bait : preview.baits()) {
                var rule = TrawlerBaitManager.getConfig(bait.getItem());
                context.assertTrue(rule != null && rule.intervalTicks() == preview.intervalTicks()
                                && rule.probabilityOfConsume() == preview.consumeChance(),
                        "Every advertised bait must use its effective first matching rule");
            }
        }
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void nestedTablesTagsAndCycles(TestContext context) {
        Identifier root = id("root");
        Identifier nested = id("nested");
        Map<Identifier, JsonObject> tables = new HashMap<>();
        tables.put(root, table("""
                {"type":"minecraft:group","children":[
                  {"type":"minecraft:loot_table","name":"moderndelight_tests:nested"},
                  {"type":"minecraft:tag","name":"c:seeds","expand":true},
                  {"type":"minecraft:item","name":"minecraft:diamond"}
                ]}
                """));
        tables.put(nested, table("""
                {"type":"minecraft:alternatives","children":[
                  {"type":"minecraft:loot_table","name":"moderndelight_tests:root"},
                  {"type":"minecraft:item","name":"minecraft:emerald"}
                ]}
                """));
        var result = TrawlerLootPreview.collect(List.of(root), tables::get);
        context.assertTrue(result.incomplete() && has(result.items(), Items.DIAMOND)
                        && has(result.items(), Items.EMERALD) && has(result.items(), ModItems.CHILI_SEEDS),
                "A cyclic reference must terminate while preserving sibling, nested and tagged items");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void replacementRemovalAndUnknownEntries(TestContext context) {
        Identifier root = id("reload");
        Map<Identifier, JsonObject> tables = new HashMap<>();
        tables.put(root, table("{\"type\":\"minecraft:item\",\"name\":\"minecraft:diamond\"}"));
        var before = TrawlerLootPreview.collect(List.of(root), tables::get);
        tables.put(root, table("{\"type\":\"minecraft:item\",\"name\":\"minecraft:emerald\"}"));
        var after = TrawlerLootPreview.collect(List.of(root), tables::get);
        context.assertTrue(has(before.items(), Items.DIAMOND) && has(after.items(), Items.EMERALD)
                        && !has(after.items(), Items.DIAMOND),
                "Replacing a loot table must not retain stale outputs");
        tables.remove(root);
        context.assertTrue(TrawlerLootPreview.collect(List.of(root), tables::get).items().isEmpty(),
                "Removing a table must remove its outputs");
        tables.put(root, table("{\"type\":\"third_party:special\"}"));
        context.assertTrue(TrawlerLootPreview.collect(List.of(root), tables::get).incomplete(),
                "Unsupported entries must be disclosed as an incomplete preview");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void paginationFallbackAndEmptyTables(TestContext context) {
        Identifier root = id("many_outputs");
        Map<Identifier, JsonObject> tables = new HashMap<>();
        var many = table("{\"type\":\"minecraft:empty\"}");
        JsonArray entries = many.getAsJsonArray("pools").get(0).getAsJsonObject().getAsJsonArray("entries");
        Registries.ITEM.stream().filter(item -> item != Items.AIR).limit(25).forEach(item -> {
            JsonObject entry = new JsonObject();
            entry.addProperty("type", "minecraft:item");
            entry.addProperty("name", Registries.ITEM.getId(item).toString());
            entries.add(entry);
        });
        tables.put(root, many);
        tables.put(LootTables.FISHING_FISH_GAMEPLAY,
                table("{\"type\":\"minecraft:item\",\"name\":\"minecraft:cod\"}"));
        tables.put(LootTables.FISHING_JUNK_GAMEPLAY,
                table("{\"type\":\"minecraft:item\",\"name\":\"minecraft:leather\"}"));
        List<TrawlerBaitPreview> previews = new ArrayList<>();
        TrawlerLootPreview.addPages(previews, "test", List.of(new ItemStack(Items.APPLE)), 40, 0.5F, root, tables::get);
        var open = previews.stream().filter(TrawlerBaitPreview::openWater).toList();
        context.assertTrue(open.size() == 2 && open.get(0).outputs().size() == 18
                        && open.get(1).outputs().size() == 7 && !open.get(0).id().equals(open.get(1).id()),
                "Large loot sets must be split into distinct pages without truncation");
        tables.remove(root);
        previews.clear();
        TrawlerLootPreview.addPages(previews, "test", List.of(), 40, 0, root, tables::get);
        context.assertTrue(has(previews.get(0).outputs(), Items.COD) && has(previews.get(0).outputs(), Items.LEATHER),
                "A missing custom table must fall back to default fish and junk");
        tables.put(root, new JsonObject());
        previews.clear();
        TrawlerLootPreview.addPages(previews, "test", List.of(), 40, 0, root, tables::get);
        context.assertTrue(previews.get(0).outputs().isEmpty(), "A valid empty custom table must not fall back");
        context.complete();
    }

    @GameTest(templateName = EMPTY_STRUCTURE)
    public void syncRoundTrip(TestContext context) {
        var previews = TrawlerLootPreview.create(context.getWorld().getServer());
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        try {
            buf.writeCollection(previews, (buffer, preview) -> preview.write(buffer));
            var received = buf.readList(TrawlerBaitPreview::read);
            context.assertTrue(received.size() == previews.size() && !buf.isReadable(),
                    "The complete snapshot must be readable without leftover data");
            for (int i = 0; i < previews.size(); i++) {
                var sent = previews.get(i);
                var read = received.get(i);
                context.assertTrue(sent.id().equals(read.id()) && sent.intervalTicks() == read.intervalTicks()
                                && sent.consumeChance() == read.consumeChance() && sent.openWater() == read.openWater()
                                && sent.incomplete() == read.incomplete() && sent.page() == read.page()
                                && sent.pages() == read.pages() && equalStacks(sent.baits(), read.baits())
                                && equalStacks(sent.outputs(), read.outputs()),
                        "The client must receive all bait, loot and display metadata");
            }
        } finally {
            buf.release();
        }
        context.complete();
    }

    private static Identifier id(String path) {
        return new Identifier("moderndelight_tests", path);
    }

    private static JsonObject table(String entry) {
        return JsonParser.parseString("{\"pools\":[{\"entries\":[" + entry + "]}]}").getAsJsonObject();
    }

    private static boolean has(List<ItemStack> stacks, Item item) {
        return stacks.stream().anyMatch(stack -> stack.isOf(item));
    }

    private static boolean equalStacks(List<ItemStack> first, List<ItemStack> second) {
        if (first.size() != second.size()) return false;
        for (int i = 0; i < first.size(); i++) {
            if (!ItemStack.areEqual(first.get(i), second.get(i))) return false;
        }
        return true;
    }
}
