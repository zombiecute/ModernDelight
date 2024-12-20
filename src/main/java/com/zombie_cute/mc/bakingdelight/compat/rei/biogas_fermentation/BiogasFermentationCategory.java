package com.zombie_cute.mc.bakingdelight.compat.rei.biogas_fermentation;

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
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class BiogasFermentationCategory implements DisplayCategory<BiogasFermentationDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/biogas_fermentation.png");
    public static final CategoryIdentifier<BiogasFermentationDisplay> BIOGAS_FERMENTATION =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "biogas_fermentation");
    public static final String FOOD = "bakingdelight.rei_plugin.biogas_fermentation.food";
    public static final String BIOGAS_FERMENTATION_NAME = "display_name.bakingdelight.biogas_fermentation_name";
    @Override
    public CategoryIdentifier<? extends BiogasFermentationDisplay> getCategoryIdentifier() {
        return BIOGAS_FERMENTATION;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(BIOGAS_FERMENTATION_NAME);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.BIOGAS_DIGESTER_IO.asItem(),1);
    }
    @Override
    public List<Widget> setupDisplay(BiogasFermentationDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,122)));
        ItemStack stack = new ItemStack(Items.APPLE);
        stack.set(DataComponentTypes.CUSTOM_NAME,Text.translatable(BiogasFermentationCategory.FOOD));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 49,startPoint.y + 13))
                .entries(EntryIngredients.ofItemStacks(List.of(stack))));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 67,startPoint.y + 34))
                .markInput().entry(EntryStacks.of(ModBlocks.BIOGAS_DIGESTER_IO.asItem(),1)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 67,startPoint.y + 52))
                .markInput().entry(EntryStacks.of(ModBlocks.BIOGAS_DIGESTER_CONTROLLER.asItem(),1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 85,startPoint.y + 34))
                .markOutput().entry(EntryStacks.of(ModBlocks.GAS_CANISTER.asItem(),1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 31,startPoint.y + 34))
                .markOutput().entry(EntryStacks.of(Items.BONE_MEAL,1)));

        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 122;
    }
    @Override
    public int getFixedDisplaysPerPage() {
        return 2;
    }
}
