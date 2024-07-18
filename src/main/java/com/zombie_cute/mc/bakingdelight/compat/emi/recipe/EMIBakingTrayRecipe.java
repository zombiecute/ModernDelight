package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.tag.ModTagKeys;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMIBakingTrayRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID, "textures/gui/compats/baking_tray.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.BAKING_TRAY);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(Bakingdelight.MOD_ID, "stir_frying"), WORKSTATION);

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIBakingTrayRecipe(CampfireCookingRecipe recipe) {
        this.id = new Identifier(Bakingdelight.MOD_ID,"stir_frying/"+recipe.getOutput(null).getItem().toString());
        List<EmiIngredient> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()){
            inputs.add(EmiIngredient.of(ingredient));
        }
        inputs.add(EmiIngredient.of(ModTagKeys.SPATULAS));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_COOKING_STOVE)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_CANISTER)));
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
        return 73;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,37,5,58,47,36,4);
        widgets.addSlot(input.get(0), 37, 5);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.BAKING_TRAY)), 37, 23);
        widgets.addSlot(EmiIngredient.of(ModTagKeys.SPATULAS), 66, 43);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_COOKING_STOVE)), 37, 52);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_CANISTER)), 19, 52);

        widgets.addSlot(output.get(0), 97, 5).recipeContext(this);
    }

}
