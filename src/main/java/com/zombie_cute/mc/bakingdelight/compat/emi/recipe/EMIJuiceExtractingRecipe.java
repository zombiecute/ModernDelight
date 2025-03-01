package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.JuiceExtractingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMIJuiceExtractingRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID, "textures/gui/compats/juice_extracting.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.JUICE_EXTRACTOR);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(ModernDelightMain.MOD_ID, "juice_extracting"), WORKSTATION);

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIJuiceExtractingRecipe(JuiceExtractingRecipe recipe) {
        this.id = recipe.getId();
        List<EmiIngredient> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()){
            inputs.add(EmiIngredient.of(ingredient));
        }
        this.input = inputs;
        this.output = List.of(EmiStack.of(recipe.getOutput(null)));
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 150;
    }

    @Override
    public int getDisplayHeight() {
        return 53;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,50,11,63,26,49,10);

        widgets.addSlot(input.get(0), 12, 9);
        widgets.addSlot(input.get(1), 30, 9);
        widgets.addSlot(input.get(2), 12, 27);
        widgets.addSlot(input.get(3), 30, 27);

        widgets.addSlot(output.get(0), 115, 18).recipeContext(this);
    }

}
