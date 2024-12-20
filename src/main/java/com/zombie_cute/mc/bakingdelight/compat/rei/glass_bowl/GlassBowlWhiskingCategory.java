package com.zombie_cute.mc.bakingdelight.compat.rei.glass_bowl;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
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

public class GlassBowlWhiskingCategory implements DisplayCategory<GlassBowlWhiskingDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/glass_bowl.png");
    public static final CategoryIdentifier<GlassBowlWhiskingDisplay> WHISKING =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "whisking");
    public static final String GLASS_BOWL_NAME = "display_name.bakingdelight.glass_bowl_name";

    @Override
    public CategoryIdentifier<? extends GlassBowlWhiskingDisplay> getCategoryIdentifier() {
        return WHISKING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(GLASS_BOWL_NAME);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.IRON_WHISK,1);
    }

    @Override
    public List<Widget> setupDisplay(GlassBowlWhiskingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,55)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 27,startPoint.y + 5))
                .entries(EntryIngredients.ofItemTag(TagKeys.WHISKS)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 46,startPoint.y + 23))
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
