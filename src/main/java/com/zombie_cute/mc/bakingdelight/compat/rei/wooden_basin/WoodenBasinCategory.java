package com.zombie_cute.mc.bakingdelight.compat.rei.wooden_basin;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.WoodenBasinBlockEntity;
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
import net.minecraft.item.ItemConvertible;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WoodenBasinCategory implements DisplayCategory<WoodenBasinDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/wooden_basin.png");
    public static final CategoryIdentifier<WoodenBasinDisplay> WOODEN_BASIN =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "wooden_basin");
    @Override
    public CategoryIdentifier<? extends WoodenBasinDisplay> getCategoryIdentifier() {
        return WOODEN_BASIN;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(WoodenBasinBlockEntity.WOODEN_BASIN_NAME);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.WOODEN_BASIN.asItem(),1);
    }
    @Override
    public List<Widget> setupDisplay(WoodenBasinDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,75)));

        Collection<ItemConvertible> itemCollection = new ArrayList<>(WoodenBasinBlockEntity.createOilMap().keySet());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 84,startPoint.y + 4))
                .entries(EntryIngredients.ofItems(itemCollection)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 84,startPoint.y + 36))
                .markInput().entry(EntryStacks.of(ModItems.FILTER,1)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 39,startPoint.y + 36))
                .markOutput().entries(EntryIngredients.ofFluidTag(TagKeys.OIL)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 129,startPoint.y + 36))
                .markOutput().entry(EntryStacks.of(ModItems.OIL_IMPURITY,1)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 74;
    }
    @Override
    public int getFixedDisplaysPerPage() {
        return 1;
    }
}
