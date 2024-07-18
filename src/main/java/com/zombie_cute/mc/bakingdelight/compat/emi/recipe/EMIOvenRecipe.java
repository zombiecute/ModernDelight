package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
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

public class EMIOvenRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID, "textures/gui/compats/transform.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModItems.CROWBAR);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(Bakingdelight.MOD_ID, "oven_transforming"), WORKSTATION);

    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIOvenRecipe() {
        List<EmiIngredient> inputs = new ArrayList<>();
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.OVEN)));
        inputs.add(EmiIngredient.of(ModTagKeys.CROWBARS));
        this.input = inputs;
        this.output = List.of(EmiStack.of(ModBlocks.ADVANCE_FURNACE),EmiStack.of(ModBlocks.BAKING_TRAY));
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return new Identifier(Bakingdelight.MOD_ID,"oven_transforming");
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
        return 52;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,5,5,142,44,4,4);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.OVEN)), 22, 18);
        widgets.addSlot(EmiIngredient.of(ModTagKeys.CROWBARS), 55, 6);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.ADVANCE_FURNACE)), 101, 18).recipeContext(this);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.BAKING_TRAY)), 119, 18).recipeContext(this);

    }

}
