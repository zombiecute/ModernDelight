package com.zombie_cute.mc.moderndelight.compat.emi.recipe;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBaitPreview;
import com.zombie_cute.mc.moderndelight.compat.trawler.TrawlerDisplayTexts;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class EMITrawlerRecipe implements EmiRecipe {
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.TRAWLER_ITEM);
    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(
            new Identifier(ModernDelightMain.MOD_ID, "trawler"), WORKSTATION);

    private final TrawlerBaitPreview preview;
    private final EmiIngredient bait;
    private final List<EmiStack> outputs;

    public EMITrawlerRecipe(TrawlerBaitPreview preview) {
        this.preview = preview;
        this.bait = EmiIngredient.of(Ingredient.ofStacks(preview.baits().stream()));
        this.outputs = preview.outputs().stream().map(EmiStack::of).toList();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public Identifier getId() {
        return preview.id();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return bait.isEmpty() ? List.of() : List.of(bait);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    @Override
    public boolean hideCraftable() {
        return true;
    }

    @Override
    public int getDisplayWidth() {
        return 166;
    }

    @Override
    public int getDisplayHeight() {
        return 124;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addText(TrawlerDisplayTexts.condition(preview), 4, 4, 0x404040, false);
        widgets.addText(TrawlerDisplayTexts.interval(preview), 4, 18, 0x404040, false);
        widgets.addText(TrawlerDisplayTexts.consumption(preview), 4, 30, 0x404040, false);
        widgets.addSlot(bait, 4, 62);
        widgets.addFillingArrow(28, 62, 4000);
        if (bait.isEmpty()) {
            widgets.addText(Text.translatable(TrawlerDisplayTexts.PREFIX + "no_bait"), 4, 84, 0x404040, false);
        }
        for (int i = 0; i < outputs.size(); i++) {
            widgets.addSlot(outputs.get(i), 54 + (i % 6) * 18, 48 + (i / 6) * 18).recipeContext(this);
        }
        if (outputs.isEmpty()) {
            widgets.addText(Text.translatable(TrawlerDisplayTexts.PREFIX + "no_outputs"), 54, 66, 0x404040, false);
        }
        widgets.addText(TrawlerDisplayTexts.outputs(preview), 4, 108, 0x404040, false);
        widgets.addTooltipText(TrawlerDisplayTexts.tooltip(preview), 0, 104, 166, 20);
    }
}
