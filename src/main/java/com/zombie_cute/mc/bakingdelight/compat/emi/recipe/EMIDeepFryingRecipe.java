package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.recipe.custom.DeepFryingRecipe;
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

public class EMIDeepFryingRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID, "textures/gui/compats/deep_fryer.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.DEEP_FRYER);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(Bakingdelight.MOD_ID, "deep_frying"), WORKSTATION);

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIDeepFryingRecipe(DeepFryingRecipe recipe) {
        this.id = recipe.getId();
        List<EmiIngredient> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()){
            inputs.add(EmiIngredient.of(ingredient));
        }
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_CANISTER)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.DEEP_FRY_BASKET)));
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
        widgets.addTexture(TEXTURE,20,5,94,64,19,4);
        widgets.addSlot(input.get(0), 20, 10);
        widgets.addSlot(EmiStack.of(ModFluid.STILL_VEGETABLE_OIL),57,10);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.DEEP_FRYER)),38,34);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_CANISTER)),20,34);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.DEEP_FRY_BASKET)),78,52);


        widgets.addSlot(output.get(0), 115, 34).recipeContext(this);
    }

}
