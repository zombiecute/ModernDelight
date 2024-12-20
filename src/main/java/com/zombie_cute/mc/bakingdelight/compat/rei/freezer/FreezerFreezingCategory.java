package com.zombie_cute.mc.bakingdelight.compat.rei.freezer;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
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

import static com.zombie_cute.mc.bakingdelight.block.entities.FreezerBlockEntity.FREEZER_NAME;

public class FreezerFreezingCategory implements DisplayCategory<FreezerFreezingDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/freezer.png");
    public static final CategoryIdentifier<FreezerFreezingDisplay> FREEZING =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "freezing");

    @Override
    public CategoryIdentifier<? extends FreezerFreezingDisplay> getCategoryIdentifier() {
        return FREEZING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(FREEZER_NAME);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.FREEZER.asItem(),1);
    }

    @Override
    public List<Widget> setupDisplay(FreezerFreezingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,70)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 47,startPoint.y + 9))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 65,startPoint.y + 9))
                .entries(display.getInputEntries().get(1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 83,startPoint.y + 9))
                .entries(display.getInputEntries().get(2)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 29,startPoint.y + 45))
                .entries(EntryIngredients.ofItemStacks(FreezerFreezingDisplay.getCoolItems())));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 65,startPoint.y + 45))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 70;
    }
}
