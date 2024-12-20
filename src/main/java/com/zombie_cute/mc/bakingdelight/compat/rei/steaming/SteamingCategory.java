package com.zombie_cute.mc.bakingdelight.compat.rei.steaming;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.BambooGrateBlockEntity;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class SteamingCategory implements DisplayCategory<SteamingDisplay> {
    public static final Identifier TEXTURE =
            Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/steaming.png");
    public static final CategoryIdentifier<SteamingDisplay> STEAMING =
            CategoryIdentifier.of(Bakingdelight.MOD_ID, "steaming");

    @Override
    public CategoryIdentifier<? extends SteamingDisplay> getCategoryIdentifier() {
        return STEAMING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(BambooGrateBlockEntity.NAME);
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.BAMBOO_GRATE,1);
    }

    @Override
    public List<Widget> setupDisplay(SteamingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 77, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y,150,95)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 23,startPoint.y + 6))
                .entries(EntryIngredients.of(ModBlocks.BAMBOO_COVER,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 23,startPoint.y + 24))
                .entries(EntryIngredients.of(ModBlocks.BAMBOO_GRATE,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 23,startPoint.y + 55))
                .entries(EntryIngredients.of(Blocks.CAULDRON,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61,startPoint.y + 55))
                .entries(EntryIngredients.of(Fluids.WATER,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 23,startPoint.y + 73))
                .entries(EntryIngredients.of(ModBlocks.GAS_COOKING_STOVE,1)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 41,startPoint.y + 73))
                .entries(EntryIngredients.of(ModBlocks.GAS_CANISTER,1)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 52,startPoint.y + 15))
                .entries(display.getInputEntries().get(0)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 106,startPoint.y + 15))
                .markOutput().entries(display.getOutputEntries().get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 95;
    }
}
