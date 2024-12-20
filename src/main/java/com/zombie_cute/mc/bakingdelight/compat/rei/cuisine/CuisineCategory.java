package com.zombie_cute.mc.bakingdelight.compat.rei.cuisine;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.CuisineTableBlockEntity;
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

public class CuisineCategory implements DisplayCategory<CuisineDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/cuisine.png");
    public static final CategoryIdentifier<CuisineDisplay> CUISINE =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "cuisine");

    @Override
    public CategoryIdentifier<? extends CuisineDisplay> getCategoryIdentifier() {
        return CUISINE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(CuisineTableBlockEntity.CUISINE_TABLE_NAME);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.CUISINE_TABLE,1);
    }

    @Override
    public List<Widget> setupDisplay(CuisineDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,51)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 39,startPoint.y + 28))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 39,startPoint.y + 7))
                .entries(display.getInputEntries().get(1)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 94,startPoint.y + 28))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return DisplayCategory.super.getDisplayHeight();
    }
}
