package com.zombie_cute.mc.bakingdelight.compat.rei.steaming;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
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
import net.minecraft.fluid.Fluids;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class SteamingElectricCategory implements DisplayCategory<SteamingElectricDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(ModernDelightMain.MOD_ID, "textures/gui/compats/steaming_electric.png");
    public static final CategoryIdentifier<SteamingElectricDisplay> STEAMING_ELECTRIC =
            CategoryIdentifier.of(ModernDelightMain.MOD_ID, "steaming_electric");

    @Override
    public CategoryIdentifier<? extends SteamingElectricDisplay> getCategoryIdentifier() {
        return STEAMING_ELECTRIC;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(ModBlocks.ELECTRIC_STEAMER.getTranslationKey());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.ELECTRIC_STEAMER);
    }

    @Override
    public List<Widget> setupDisplay(SteamingElectricDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,140,53)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 14,startPoint.y + 28))
                .entries(EntryIngredients.of(Fluids.WATER)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 55,startPoint.y + 10))
                .entries(display.getInputEntries().get(0)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 107,startPoint.y + 10))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 53;
    }
}
