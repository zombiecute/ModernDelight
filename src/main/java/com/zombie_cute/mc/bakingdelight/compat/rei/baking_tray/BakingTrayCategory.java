package com.zombie_cute.mc.bakingdelight.compat.rei.baking_tray;

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

public class BakingTrayCategory implements DisplayCategory<BakingTrayDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/baking_tray.png");
    public static final CategoryIdentifier<BakingTrayDisplay> STIR_FRYING =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "baking_tray");
    public static final String BAKING_TRAY_NAME = "display_name.bakingdelight.baking_tray_name";
    @Override
    public CategoryIdentifier<? extends BakingTrayDisplay> getCategoryIdentifier() {
        return STIR_FRYING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(BAKING_TRAY_NAME);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.SPATULA,1);
    }

    @Override
    public List<Widget> setupDisplay(BakingTrayDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,73)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 37,startPoint.y + 5))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 37,startPoint.y + 23))
                .markInput().entry(EntryStacks.of(ModBlocks.BAKING_TRAY,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 37,startPoint.y + 52))
                .markInput().entry(EntryStacks.of(ModBlocks.GAS_COOKING_STOVE,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 19,startPoint.y + 52))
                .markInput().entry(EntryStacks.of(ModBlocks.GAS_CANISTER,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 66,startPoint.y + 43))
                .markInput().entries(EntryIngredients.ofItemTag(TagKeys.SPATULAS)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 97,startPoint.y + 5))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 73;
    }
}
