package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.CuisineRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMICuisineRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/cuisine.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.CUISINE_TABLE);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(Identifier.of(Bakingdelight.MOD_ID, "cuisine"), WORKSTATION);

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMICuisineRecipe(CuisineRecipe recipe) {
        this.id = Identifier.of(Bakingdelight.MOD_ID,recipe.getResult(null).getTranslationKey()+recipe.hashCode());
        List<EmiIngredient> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()){
            inputs.add(EmiIngredient.of(ingredient));
        }
        this.input = inputs;
        this.output = List.of(EmiStack.of(recipe.getResult(null)));
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
        return 51;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 63,27);
        widgets.addSlot(input.get(0), 39, 28);
        widgets.addSlot(input.get(1), 39, 7);

        widgets.addSlot(output.get(0), 94, 28).recipeContext(this);
    }

}
