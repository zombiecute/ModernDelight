package com.zombie_cute.mc.bakingdelight.compat.rei.grinding;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class GrindingCategory implements DisplayCategory<GrindingDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(ModernDelightMain.MOD_ID, "textures/gui/compats/grinding.png");
    public static final CategoryIdentifier<GrindingDisplay> GRINDING =
            CategoryIdentifier.of(ModernDelightMain.MOD_ID, "grinding");
    public static final String TITLE = "emi.category.bakingdelight.grinding";

    @Override
    public CategoryIdentifier<? extends GrindingDisplay> getCategoryIdentifier() {
        return GRINDING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(TITLE);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.STONE_MORTAR);
    }

    @Override
    public List<Widget> setupDisplay(GrindingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,53)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 15,startPoint.y + 17))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 57,startPoint.y + 28))
                .entries(EntryIngredients.of(ModItems.STONE_MORTAR)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 100,startPoint.y + 17))
                .markOutput().entries(display.getOutputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 118,startPoint.y + 17))
                .markOutput().entries(display.getOutputEntries().get(1)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 53;
    }
}
