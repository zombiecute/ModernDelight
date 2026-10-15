package com.zombie_cute.mc.moderndelight.block.trawler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrawlerBaitManager
        implements SimpleSynchronousResourceReloadListener {

    private static final Identifier LISTENER_ID = Identifier.of(ModernDelightMain.MOD_ID, "trawler_baits");
    private static volatile List<TrawlerBaitConfig> CONFIGS = List.of();

    @Override
    public Identifier getFabricId() {
        return LISTENER_ID;
    }

    @Override
    public void reload(ResourceManager manager) {
        List<TrawlerBaitConfig> loaded = new ArrayList<>();
        for (var entry : manager.findResources("trawler_baits",
                id -> id.getPath().endsWith(".json")
        ).entrySet()) {
            Identifier fileId = entry.getKey();
            Resource resource = entry.getValue();
            try (Reader reader = resource.getReader()) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                JsonObject bait = json.getAsJsonObject("bait");
                String type = bait.get("type").getAsString();
                Identifier id = Identifier.tryParse(bait.get("id").getAsString());
                int interval = json.get("interval_ticks").getAsInt();
                float probabilityOfConsume = json.has("probability_of_consume") ?
                        json.get("probability_of_consume").getAsFloat() : 0.75F;
                Identifier lootTable = json.has("loot_table") ?
                        Identifier.tryParse(json.get("loot_table").getAsString()) : null;

                if (interval < 1) {
                    throw new IllegalArgumentException("interval_ticks must be greater than 0");
                }
                TrawlerBaitConfig config;
                switch (type) {
                    case "item" -> {
                        if (Registries.ITEM.getOrEmpty(id).isEmpty()) {
                            throw new IllegalArgumentException("Unknown bait item: " + id);
                        }
                        config = TrawlerBaitConfig.forItem(id, interval, probabilityOfConsume, lootTable);
                    }
                    case "tag" -> config = TrawlerBaitConfig.forTag(id, interval, probabilityOfConsume, lootTable);
                    default -> throw new IllegalArgumentException("bait.type must be 'item' or 'tag'");
                }
                loaded.add(config);
            } catch (Exception e) {
                ModernDelightMain.LOGGER.error("[{}}] Failed to load trawler bait {}: {}",
                        ModernDelightMain.MOD_ID, fileId, e.getMessage());
            }
        }
        loaded.sort(Comparator.comparing(config -> config.baitItem() == null));
        CONFIGS = List.copyOf(loaded);
        ModernDelightMain.LOGGER.info("[{}] Loaded {} trawler bait configurations.",ModernDelightMain.MOD_ID, CONFIGS.size());
    }

    public static List<TrawlerBaitConfig> getConfigs() {
        return CONFIGS;
    }
    public static TrawlerBaitConfig getConfig(Item item) {
        for (TrawlerBaitConfig config : CONFIGS) {
            if (config.matches(item)) {
                return config;
            }
        }
        return null;
    }
}
