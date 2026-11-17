package com.zombie_cute.mc.moderndelight.block.trawler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/** Enumerates declared loot without rolling it or executing context-sensitive loot functions. */
public final class TrawlerLootPreview {
    private TrawlerLootPreview() {
    }

    public static List<TrawlerBaitPreview> create(MinecraftServer server) {
        LootManager manager = server.getLootManager();
        Set<Identifier> tables = new HashSet<>(manager.getIds(LootDataType.LOOT_TABLES));
        Function<Identifier, JsonObject> lookup = id -> tables.contains(id)
                ? LootDataType.LOOT_TABLES.getGson().toJsonTree(manager.getLootTable(id), LootTable.class).getAsJsonObject()
                : null;
        List<TrawlerBaitPreview> previews = new ArrayList<>();
        addPages(previews, "default", List.of(), TrawlerFishingLoot.getDefaultIntervalTicks(), 0, null, lookup);

        // Resolve the effective rule on the server: item rules win over tags, and overlapping tags
        // use the same first match as the actual trawler. Do not advertise shadowed bait rules.
        for (TrawlerBaitConfig config : TrawlerBaitManager.getConfigs()) {
            List<ItemStack> baits = Registries.ITEM.stream()
                    .filter(item -> item != Items.AIR && TrawlerBaitManager.getConfig(item) == config)
                    .sorted(Comparator.comparing(Registries.ITEM::getId))
                    .map(ItemStack::new).toList();
            if (baits.isEmpty()) {
                continue;
            }
            Identifier baitId = config.baitItem() != null ? config.baitItem() : config.baitTag().id();
            String path = (config.baitItem() != null ? "item/" : "tag/")
                    + baitId.getNamespace() + "/" + baitId.getPath();
            addPages(previews, path, baits, config.intervalTicks(), config.probabilityOfConsume(),
                    config.lootTable(), lookup);
        }
        return List.copyOf(previews);
    }

    static void addPages(List<TrawlerBaitPreview> previews, String path, List<ItemStack> baits,
                                 int interval, float consumeChance, Identifier customTable,
                                 Function<Identifier, JsonObject> lookup) {
        for (boolean openWater : new boolean[]{true, false}) {
            List<Identifier> tables;
            if (!openWater) {
                tables = List.of(LootTables.FISHING_JUNK_GAMEPLAY);
            } else if (customTable != null && lookup.apply(customTable) != null) {
                tables = List.of(customTable);
            } else {
                tables = List.of(LootTables.FISHING_FISH_GAMEPLAY, LootTables.FISHING_JUNK_GAMEPLAY);
            }
            Result result = collect(tables, lookup);
            int pages = Math.max(1, (result.items().size() + TrawlerBaitPreview.OUTPUTS_PER_PAGE - 1)
                    / TrawlerBaitPreview.OUTPUTS_PER_PAGE);
            for (int page = 0; page < pages; page++) {
                int start = page * TrawlerBaitPreview.OUTPUTS_PER_PAGE;
                List<ItemStack> outputs = result.items().subList(start,
                        Math.min(start + TrawlerBaitPreview.OUTPUTS_PER_PAGE, result.items().size()));
                previews.add(new TrawlerBaitPreview(new Identifier(ModernDelightMain.MOD_ID,
                        "trawler/" + path + (openWater ? "/open_water/" : "/restricted_water/") + page),
                        baits, outputs, interval, consumeChance, openWater, result.incomplete(), page + 1, pages));
            }
        }
    }

    static Result collect(List<Identifier> tables, Function<Identifier, JsonObject> lookup) {
        Collector collector = new Collector(lookup);
        Set<Item> items = new LinkedHashSet<>();
        for (Identifier table : tables) {
            items.addAll(collector.table(table));
        }
        return new Result(items.stream().filter(item -> item != Items.AIR)
                .sorted(Comparator.comparing(Registries.ITEM::getId)).map(ItemStack::new).toList(),
                collector.incomplete);
    }

    record Result(List<ItemStack> items, boolean incomplete) {
    }

    private static final class Collector {
        private final Function<Identifier, JsonObject> lookup;
        private final Set<Identifier> visiting = new HashSet<>();
        private boolean incomplete;

        private Collector(Function<Identifier, JsonObject> lookup) {
            this.lookup = lookup;
        }

        private Set<Item> table(Identifier id) {
            if (visiting.size() >= 64 || !visiting.add(id)) {
                incomplete = true;
                return Set.of();
            }
            try {
                JsonObject table = lookup.apply(id);
                if (table == null) {
                    return Set.of();
                }
                Set<Item> result = new LinkedHashSet<>();
                if (table.has("pools")) {
                    for (JsonElement element : table.getAsJsonArray("pools")) {
                        JsonObject pool = element.getAsJsonObject();
                        Set<Item> poolItems = new LinkedHashSet<>();
                        for (JsonElement entry : pool.getAsJsonArray("entries")) {
                            poolItems.addAll(entry(entry.getAsJsonObject(), 0));
                        }
                        result.addAll(applyFunctions(pool, poolItems));
                    }
                }
                return applyFunctions(table, result);
            } catch (RuntimeException e) {
                incomplete = true;
                ModernDelightMain.LOGGER.warn("Could not preview trawler loot table {}", id, e);
                return Set.of();
            } finally {
                visiting.remove(id);
            }
        }

        private Set<Item> entry(JsonObject entry, int depth) {
            if (depth >= 64) {
                incomplete = true;
                return Set.of();
            }
            Set<Item> items = new LinkedHashSet<>();
            String type = new Identifier(entry.get("type").getAsString()).toString();
            switch (type) {
                case "minecraft:item" -> Registries.ITEM.getOrEmpty(new Identifier(entry.get("name").getAsString()))
                        .ifPresent(items::add);
                case "minecraft:tag" -> Registries.ITEM.getEntryList(TagKey.of(RegistryKeys.ITEM,
                        new Identifier(entry.get("name").getAsString())))
                        .ifPresent(entries -> entries.forEach(item -> items.add(item.value())));
                case "minecraft:loot_table" -> items.addAll(table(new Identifier(entry.get("name").getAsString())));
                case "minecraft:group", "minecraft:alternatives", "minecraft:sequence" -> {
                    for (JsonElement child : entry.getAsJsonArray("children")) {
                        items.addAll(entry(child.getAsJsonObject(), depth + 1));
                    }
                }
                case "minecraft:empty" -> { }
                default -> incomplete = true;
            }
            return applyFunctions(entry, items);
        }

        private Set<Item> applyFunctions(JsonObject node, Set<Item> items) {
            if (node.has("functions")) {
                for (JsonElement element : node.getAsJsonArray("functions")) {
                    JsonObject function = element.getAsJsonObject();
                    String type = new Identifier(function.get("function").getAsString()).toString();
                    if ((type.equals("minecraft:enchant_randomly") || type.equals("minecraft:enchant_with_levels"))
                            && items.contains(Items.BOOK)) {
                        // Vanilla fishing treasure declares a book and turns it into an enchanted book.
                        // Conditional functions can also leave the original item intact.
                        if (!function.has("conditions") || function.getAsJsonArray("conditions").isEmpty()) {
                            items.remove(Items.BOOK);
                        }
                        items.add(Items.ENCHANTED_BOOK);
                    } else if (type.equals("minecraft:furnace_smelt")) {
                        incomplete = true;
                    }
                }
            }
            return items;
        }
    }
}
