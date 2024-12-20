package com.zombie_cute.mc.bakingdelight.compat.rei.assembly;

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
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class AssemblyCategory implements DisplayCategory<AssemblyDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/assembly.png");
    public static final CategoryIdentifier<AssemblyDisplay> ASSEMBLY =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "assembly");

    @Override
    public CategoryIdentifier<? extends AssemblyDisplay> getCategoryIdentifier() {
        return ASSEMBLY;
    }

    @Override
    public Text getTitle() {
        return ModBlocks.ELECTRICIANS_DESK.getName();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.ELECTRICIANS_DESK,1);
    }
    @Override
    public List<Widget> setupDisplay(AssemblyDisplay display, me.shedaniel.math.Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,70)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 12,startPoint.y + 9))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 30,startPoint.y + 9))
                .entries(display.getInputEntries().get(1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 48,startPoint.y + 9))
                .entries(display.getInputEntries().get(2)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 12,startPoint.y + 27))
                .entries(display.getInputEntries().get(3)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 30,startPoint.y + 27))
                .entries(display.getInputEntries().get(4)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 48,startPoint.y + 27))
                .entries(display.getInputEntries().get(5)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 123,startPoint.y + 9))
                .entries(EntryIngredients.of(Items.PAPER,1)));
        HashSet<ItemConvertible> items = new HashSet<>();
        items.add(Items.INK_SAC);
        items.add(Items.GLOW_INK_SAC);
        items.add(Items.BLACK_DYE);
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 123,startPoint.y + 27))
                .entries(EntryIngredients.ofItems(items)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 102,startPoint.y + 18))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 53;
    }
}
