package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.SqueezeRecipe;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EMIWoodenBasinRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = Identifier.of(ModernDelightMain.MOD_ID, "textures/gui/compats/wooden_basin.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.WOODEN_BASIN);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(Identifier.of(ModernDelightMain.MOD_ID, "oil_extraction"), WORKSTATION);
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    public EMIWoodenBasinRecipe(SqueezeRecipe recipe) {
        this.input = List.of(EmiIngredient.of(recipe.getIngredients().getFirst()));
        this.output = List.of(
                EmiStack.of(recipe.getResult(null)),
                EmiStack.of(recipe.getOutputFluid().getFluidVariant().getFluid(),recipe.getOutputFluid().getAmount()));
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return null;
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
        return 75;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,5,5,142,67,4,4);
        widgets.addSlot(input.get(0), 84, 4);
        widgets.addSlot(EmiIngredient.of(TagKeys.FILTERS), 84, 36);
        widgets.addSlot(output.get(0), 129, 36).recipeContext(this);
        widgets.addSlot(output.get(1), 39, 36).recipeContext(this);
    }

}
