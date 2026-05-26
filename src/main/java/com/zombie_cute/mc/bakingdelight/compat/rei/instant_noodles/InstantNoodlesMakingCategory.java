package com.zombie_cute.mc.bakingdelight.compat.rei.instant_noodles;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.food.instant_noodles.CookedPortablePotItem;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InstantNoodlesMakingCategory implements DisplayCategory<InstantNoodlesMakingDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(ModernDelightMain.MOD_ID, "textures/gui/compats/instant_noodles.png");
    public static final String TITLE = "emi.category.bakingdelight.instant_noodles_making";
    public static final CategoryIdentifier<InstantNoodlesMakingDisplay> INSTANT_NOODLES_MAKING =
            CategoryIdentifier.of(ModernDelightMain.MOD_ID, "instant_noodles_making");
    @Override
    public CategoryIdentifier<? extends InstantNoodlesMakingDisplay> getCategoryIdentifier() {
        return INSTANT_NOODLES_MAKING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(TITLE);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.COOKED_PORTABLE_POT.getDefaultStack());
    }
    @Override
    public List<Widget> setupDisplay(InstantNoodlesMakingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,116)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 12,startPoint.y + 7))
                .entries(EntryIngredients.of(ModItems.MULTIFUNCTIONAL_WRAPPING_PAPER)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 30,startPoint.y + 7))
                .entry(EntryStacks.of(ModItems.FRIED_NOODLES)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 30,startPoint.y + 50))
                .markOutput().entry(EntryStacks.of(ModItems.PACKAGED_INSTANT_NOODLES)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 49,startPoint.y + 50))
                .entry(EntryStacks.of(ModItems.QUICKLIME)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 68,startPoint.y + 50))
                .entry(EntryStacks.of(Items.WATER_BUCKET)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 49,startPoint.y + 82))
                .entry(EntryStacks.of(ModItems.PORTABLE_POT)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 90,startPoint.y + 82))
                .markOutput().entry(EntryStacks.of(ModItems.COOKED_PORTABLE_POT)));
        widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 47,startPoint.y + 6,18,18),Text.translatable(TextUtil.ANYTHING)));
        List<ItemStack> exampleItems = new ArrayList<>();
        for (SpecialIngredient specialIngredient : SpecialIngredient.values()){
            exampleItems.add(CookedPortablePotItem.createCookedPot(specialIngredient.getIngredients()));
        }
        for (int i = 0; i < exampleItems.size(); i++){
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 129,startPoint.y + 5 + (18 * i)))
                    .markOutput().entry(EntryStacks.of(exampleItems.get(i))));
        }
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 116;
    }
    @Override
    public int getFixedDisplaysPerPage() {
        return 1;
    }
}
