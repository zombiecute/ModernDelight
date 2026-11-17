package com.zombie_cute.mc.moderndelight.compat.rei.trawler;

import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBaitPreview;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public record TrawlerDisplay(TrawlerBaitPreview preview) implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        return preview.baits().isEmpty() ? List.of() : List.of(EntryIngredients.ofItemStacks(preview.baits()));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return preview.outputs().stream().map(EntryIngredients::of).toList();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return TrawlerCategory.TRAWLER;
    }

    @Override
    public Optional<Identifier> getDisplayLocation() {
        return Optional.of(preview.id());
    }
}
