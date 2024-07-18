package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.AssemblyRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMIAssemblyRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID, "textures/gui/compats/assembly.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.ELECTRICIANS_DESK);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(Bakingdelight.MOD_ID, "assembly"), WORKSTATION);

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIAssemblyRecipe(AssemblyRecipe recipe) {
        this.id = recipe.getId();
        List<EmiIngredient> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()){
            inputs.add(EmiIngredient.of(ingredient));
        }
        inputs.add(EmiIngredient.of(Ingredient.ofItems(Items.PAPER)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(Items.INK_SAC,Items.GLOW_INK_SAC,Items.BLACK_DYE)));
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
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 72,18);
        widgets.addSlot(input.get(0), 12, 9);
        widgets.addSlot(input.get(1), 30, 9);
        widgets.addSlot(input.get(2), 48, 9);
        widgets.addSlot(input.get(3), 12, 27);
        widgets.addSlot(input.get(4), 30, 27);
        widgets.addSlot(input.get(5), 48, 27);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(Items.PAPER)), 123, 9);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(Items.INK_SAC,Items.GLOW_INK_SAC,Items.BLACK_DYE)), 123, 27);

        widgets.addSlot(output.get(0), 102, 18).recipeContext(this);
    }

}
