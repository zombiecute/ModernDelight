package com.zombie_cute.mc.bakingdelight.compat.rei.transform;

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
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class AdvanceFurnaceTransformCategory implements DisplayCategory<AdvanceFurnaceTransformDisplay> {
    public static final Identifier TEXTURE = OvenTransformCategory.TEXTURE;
    public static final CategoryIdentifier<AdvanceFurnaceTransformDisplay> ADVANCE_FURNACE_TRANSFORMING =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "advance_furnace_transforming");
    @Override
    public CategoryIdentifier<? extends AdvanceFurnaceTransformDisplay> getCategoryIdentifier() {
        return ADVANCE_FURNACE_TRANSFORMING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(OvenTransformCategory.TRANSFORM_TITLE);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.GRASS_BLOCK,1);
    }
    @Override
    public List<Widget> setupDisplay(AdvanceFurnaceTransformDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,52)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 22,startPoint.y + 18))
                .entry(EntryStacks.of(ModBlocks.ADVANCE_FURNACE.asItem(),1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 55,startPoint.y + 6))
                .entry(EntryStacks.of(ModBlocks.BAKING_TRAY.asItem(),1)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 101,startPoint.y + 18))
                .markOutput().entry(EntryStacks.of(ModBlocks.OVEN.asItem(),1)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 52;
    }
    @Override
    public int getFixedDisplaysPerPage() {
        return 1;
    }
}
