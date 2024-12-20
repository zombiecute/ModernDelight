package com.zombie_cute.mc.bakingdelight.compat.rei.glass_bowl;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
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

public class GlassBowlMixWithWaterCategory implements DisplayCategory<GlassBowlMixWithWaterDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/water_glass_bowl.png");
    public static final CategoryIdentifier<GlassBowlMixWithWaterDisplay> MIX_WITH_WATER =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "mix_with_water");
    public static final String WATER_GLASS_BOWL_NAME = "display_name.bakingdelight.water_glass_bowl_name";

    @Override
    public CategoryIdentifier<? extends GlassBowlMixWithWaterDisplay> getCategoryIdentifier() {
        return MIX_WITH_WATER;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(WATER_GLASS_BOWL_NAME);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.WATER_BUCKET,1);
    }

    @Override
    public List<Widget> setupDisplay(GlassBowlMixWithWaterDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,55)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 48,startPoint.y + 13))
                .entries(display.getInputEntries().get(0)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 101,startPoint.y + 20))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 55;
    }
}
