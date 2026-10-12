package com.zombie_cute.mc.bakingdelight.compat.rei.fermenting;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.BacteriaType;
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
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class FermentingCategory implements DisplayCategory<FermentingDisplay> {
    public static final Identifier TEXTURE =
            new Identifier(ModernDelightMain.MOD_ID, "textures/gui/compats/fermenting.png");
    public static final CategoryIdentifier<FermentingDisplay> FERMENTING =
            CategoryIdentifier.of(ModernDelightMain.MOD_ID, "fermenting");

    @Override
    public CategoryIdentifier<? extends FermentingDisplay> getCategoryIdentifier() {
        return FERMENTING;
    }

    @Override
    public Text getTitle() {
        return ModBlocks.INDUSTRIAL_FERMENTER.getName();
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.INDUSTRIAL_FERMENTER_ITEM.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(FermentingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,176,72)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 11,startPoint.y + 11))
                .entries(display.getInputItems().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 29,startPoint.y + 11))
                .entries(display.getInputItems().get(1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 11,startPoint.y + 29))
                .entries(display.getInputItems().get(2)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 29,startPoint.y + 29))
                .entries(display.getInputItems().get(3)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 20,startPoint.y + 49))
                .entries(display.getInputFluid()));

        MutableText text1 = Text.translatable(TextUtil.FERMENTATION_TIME);
        text1.append(Text.literal(" "+display.getTime()+" s"));
        widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 49, startPoint.y + 6, 18, 18),text1));

        MutableText text2 = Text.translatable(TextUtil.FERMENTATION_LIGHT);
        text2.append(Text.literal(" "+display.getLuminosity()));
        widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 67, startPoint.y + 6, 18, 18),text2));

        MutableText text3 = Text.translatable(TextUtil.FERMENTATION_TEMP);
        text3.append(Text.literal(" "+display.getTemperature()+" ℃"));
        widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 85, startPoint.y + 6, 18, 18),text3));

        BacteriaType inputBacteria = display.getBacteriaInput();
        if(inputBacteria != null) {
            widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 68, startPoint.y + 35, 15, 15),
                    Text.translatable(inputBacteria.getTranslationKey())));
        } else {
            widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 68, startPoint.y + 35, 15, 15),
                    Text.translatable(TextUtil.NO_BACTRIA)));
        }

        BacteriaType outputBacteria = display.getBacteriaOutput();
        int outputBacteriaCFU = display.getBacteriaOutputCFU();
        if(outputBacteria != null) {
            MutableText text4 = Text.literal(outputBacteriaCFU+" ").formatted(Formatting.GRAY);
            text4.append(Text.literal("CFU").formatted(Formatting.DARK_GRAY));
            widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 97, startPoint.y + 36, 13, 13),
                    Text.translatable(outputBacteria.getTranslationKey()),text4));
        } else {
            widgets.add(Widgets.createTooltip(new Rectangle(startPoint.x + 97, startPoint.y + 36, 15, 15),
                    Text.translatable(TextUtil.NO_BACTRIA)));
        }

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 119,startPoint.y + 20))
                .markOutput().entries(display.getOutputItem()));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 119,startPoint.y + 49))
                .markOutput().entries(display.getOutputFluid()));
        return widgets;
    }
    @Override
    public int getDisplayHeight() {
        return 72;
    }
}
