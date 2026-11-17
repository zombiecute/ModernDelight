package com.zombie_cute.mc.moderndelight.compat.rei.trawler;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.compat.trawler.TrawlerDisplayTexts;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TrawlerCategory implements DisplayCategory<TrawlerDisplay> {
    public static final CategoryIdentifier<TrawlerDisplay> TRAWLER =
            CategoryIdentifier.of(ModernDelightMain.MOD_ID, "trawler");

    @Override
    public CategoryIdentifier<? extends TrawlerDisplay> getCategoryIdentifier() {
        return TRAWLER;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(TrawlerDisplayTexts.TITLE);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.TRAWLER_ITEM);
    }

    @Override
    public int getDisplayWidth(TrawlerDisplay display) {
        return 174;
    }

    @Override
    public int getDisplayHeight() {
        return 132;
    }

    @Override
    public List<Widget> setupDisplay(TrawlerDisplay display, Rectangle bounds) {
        int x = bounds.getCenterX() - 83;
        int y = bounds.y + 4;
        var preview = display.preview();
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createLabel(new Point(x + 4, y + 4), TrawlerDisplayTexts.condition(preview)).color(0x404040)
                .leftAligned().noShadow());
        widgets.add(Widgets.createLabel(new Point(x + 4, y + 18), TrawlerDisplayTexts.interval(preview)).color(0x404040)
                .leftAligned().noShadow());
        widgets.add(Widgets.createLabel(new Point(x + 4, y + 30), TrawlerDisplayTexts.consumption(preview)).color(0x404040)
                .leftAligned().noShadow());
        widgets.add(Widgets.createSlot(new Point(x + 5, y + 63))
                .markInput().entries(preview.baits().stream().map(EntryStacks::of).toList()));
        widgets.add(Widgets.createArrow(new Point(x + 28, y + 62)));
        if (preview.baits().isEmpty()) {
            widgets.add(Widgets.createLabel(new Point(x + 4, y + 84),
                    Text.translatable(TrawlerDisplayTexts.PREFIX + "no_bait")).color(0x404040).leftAligned().noShadow());
        }
        var outputs = display.getOutputEntries();
        for (int i = 0; i < outputs.size(); i++) {
            widgets.add(Widgets.createSlot(new Point(x + 55 + (i % 6) * 18, y + 49 + (i / 6) * 18))
                    .markOutput().entries(outputs.get(i)));
        }
        if (outputs.isEmpty()) {
            widgets.add(Widgets.createLabel(new Point(x + 54, y + 66),
                    Text.translatable(TrawlerDisplayTexts.PREFIX + "no_outputs")).color(0x404040).leftAligned().noShadow());
        }
        widgets.add(Widgets.createLabel(new Point(x + 4, y + 108), TrawlerDisplayTexts.outputs(preview)).color(0x404040)
                .leftAligned().noShadow());
        widgets.add(Widgets.createTooltip(new Rectangle(x, y + 104, 166, 20),
                TrawlerDisplayTexts.tooltip(preview).toArray(Text[]::new)));
        return widgets;
    }
}
