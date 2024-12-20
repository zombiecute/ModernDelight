package com.zombie_cute.mc.bakingdelight.compat.rei.pizza;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
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

public class PizzaMakingCategory implements DisplayCategory<PizzaMakingDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/pizza.png");
    public static final String PIZZA_TITLE = "display_name.bakingdelight.pizza_name";
    public static final CategoryIdentifier<PizzaMakingDisplay> PIZZA_MAKING =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "pizza_making");
    @Override
    public CategoryIdentifier<? extends PizzaMakingDisplay> getCategoryIdentifier() {
        return PIZZA_MAKING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(PIZZA_TITLE);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.PIZZA.asItem(),1);
    }
    @Override
    public List<Widget> setupDisplay(PizzaMakingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,55)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 5,startPoint.y + 10))
                .entry(EntryStacks.of(ModBlocks.WHEAT_DOUGH.asItem(),1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 24,startPoint.y + 29))
                .entries(EntryIngredients.ofItemTag(TagKeys.ROLLING_PINS)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 44,startPoint.y + 29))
                .entry(EntryStacks.of(ModItems.CHEESE,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 67,startPoint.y + 10))
                .entry(EntryStacks.of(ModBlocks.PIZZA_WIP.asItem(),1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 86,startPoint.y + 29))
                .entries(EntryIngredients.ofItemTag(TagKeys.PIZZA_INGREDIENTS)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 106,startPoint.y + 29))
                .entry(EntryStacks.of(ModItems.CHEESE,1)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 129,startPoint.y + 10))
                .markOutput().entry(EntryStacks.of(ModBlocks.RAW_PIZZA.asItem(),1)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 55;
    }
    @Override
    public int getFixedDisplaysPerPage() {
        return 1;
    }
}
