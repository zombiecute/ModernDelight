package com.zombie_cute.mc.bakingdelight.compat.rei.ice_cream_making;

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
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class IceCreamMakingCategory implements DisplayCategory<IceCreamMakingDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/ice_cream.png");
    public static final String TITLE = "emi.category.bakingdelight.ice_cream_making";
    public static final CategoryIdentifier<IceCreamMakingDisplay> ICE_CREAM_MAKING =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "ice_cream_making");
    @Override
    public CategoryIdentifier<? extends IceCreamMakingDisplay> getCategoryIdentifier() {
        return ICE_CREAM_MAKING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(TITLE);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.PIZZA.asItem(),1);
    }
    @Override
    public List<Widget> setupDisplay(IceCreamMakingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,90)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 44,startPoint.y + 9))
                .entries(EntryIngredients.ofItemTag(TagKeys.CREAMS)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 44,startPoint.y + 27))
                .entry(EntryStacks.of(Items.SUGAR,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 44,startPoint.y + 45))
                .entry(EntryStacks.of(Items.EGG,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 33,startPoint.y + 69))
                .entry(EntryStacks.of(ModItems.ICE_CREAM_CONE,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 66,startPoint.y + 69))
                .entry(EntryStacks.of(ModBlocks.ICE_CREAM_MAKER.asItem(),1)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 100,startPoint.y + 69))
                .markOutput().entry(EntryStacks.of(ModItems.ICE_CREAM,1)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 90;
    }
    @Override
    public int getFixedDisplaysPerPage() {
        return 1;
    }
}
