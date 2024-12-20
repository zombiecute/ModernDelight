package com.zombie_cute.mc.bakingdelight.compat.rei.oven;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
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

import static com.zombie_cute.mc.bakingdelight.block.entities.OvenBlockEntity.OVEN_NAME;

public class OvenBakingCategory implements DisplayCategory<OvenBakingDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/oven.png");
    public static final CategoryIdentifier<OvenBakingDisplay> OVEN_BAKING =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "oven_baking");

    @Override
    public CategoryIdentifier<? extends OvenBakingDisplay> getCategoryIdentifier() {
        return OVEN_BAKING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(OVEN_NAME);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.OVEN.asItem(),1);
    }

    @Override
    public List<Widget> setupDisplay(OvenBakingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,55)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 9,startPoint.y + 19))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 27,startPoint.y + 19))
                .entries(display.getInputEntries().get(1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 45,startPoint.y + 19))
                .entries(display.getInputEntries().get(2)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 63,startPoint.y + 19))
                .entries(display.getInputEntries().get(3)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 118,startPoint.y + 19))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 55;
    }
}
