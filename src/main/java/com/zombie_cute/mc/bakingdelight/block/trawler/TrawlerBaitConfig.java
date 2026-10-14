package com.zombie_cute.mc.bakingdelight.block.trawler;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public record TrawlerBaitConfig(
        Identifier baitItem, TagKey<Item> baitTag, int intervalTicks, float probabilityOfConsume, Identifier lootTable) {
    public boolean matches(Item item) {
        if (baitItem != null) {
            return Registries.ITEM.getId(item).equals(baitItem);
        }
        return baitTag != null && item.getRegistryEntry().isIn(baitTag);
    }
    public static TrawlerBaitConfig forItem(Identifier itemId, int intervalTicks,
            float probabilityOfConsume, Identifier lootTable) {
        return new TrawlerBaitConfig(itemId, null, intervalTicks, probabilityOfConsume, lootTable);
    }

    public static TrawlerBaitConfig forTag(Identifier tagId, int intervalTicks,
                                           float probabilityOfConsume, Identifier lootTable) {
        return new TrawlerBaitConfig(null, TagKey.of(RegistryKeys.ITEM, tagId), intervalTicks,
                probabilityOfConsume,lootTable);
    }
}
