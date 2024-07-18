package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.ModTagKeys;
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

public class EMIWoodenBasinRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID, "textures/gui/compats/wooden_basin.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.WOODEN_BASIN);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(Bakingdelight.MOD_ID, "oil_extraction"), WORKSTATION);

    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIWoodenBasinRecipe() {
        List<EmiIngredient> inputs = new ArrayList<>();
        inputs.add(EmiIngredient.of(ModTagKeys.OIL_PLANTS));
        inputs.add(EmiIngredient.of(ModTagKeys.FILTERS));
        this.input = inputs;
        this.output = List.of(EmiStack.of(ModFluid.STILL_VEGETABLE_OIL),EmiStack.of(ModItems.OIL_IMPURITY));
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return new Identifier(Bakingdelight.MOD_ID,"oil_extraction");
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
        widgets.addSlot(EmiIngredient.of(ModTagKeys.OIL_PLANTS), 84, 4);
        widgets.addSlot(EmiIngredient.of(ModTagKeys.FILTERS), 84, 36);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModItems.OIL_IMPURITY)), 129, 36).recipeContext(this);
        widgets.addSlot(EmiStack.of(ModFluid.STILL_VEGETABLE_OIL), 39, 36).recipeContext(this);
    }

}
