package com.zombie_cute.mc.bakingdelight.compat.rei.juice_extracting;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class JuiceExtractingCategory implements DisplayCategory<JuiceExtractingDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(ModernDelightMain.MOD_ID, "textures/gui/compats/juice_extracting.png");
    public static final CategoryIdentifier<JuiceExtractingDisplay> JUICE_EXTRACTING =
            CategoryIdentifier.of(ModernDelightMain.MOD_ID, "juice_extracting");

    @Override
    public CategoryIdentifier<? extends JuiceExtractingDisplay> getCategoryIdentifier() {
        return JUICE_EXTRACTING;
    }

    @Override
    public Text getTitle() {
        return ModBlocks.JUICE_EXTRACTOR.getName();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.JUICE_EXTRACTOR.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(JuiceExtractingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,53)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 12,startPoint.y + 9))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 30,startPoint.y + 9))
                .entries(display.getInputEntries().get(1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 12,startPoint.y + 27))
                .entries(display.getInputEntries().get(2)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 30,startPoint.y + 27))
                .entries(display.getInputEntries().get(3)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 115,startPoint.y + 18))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 53;
    }
}
