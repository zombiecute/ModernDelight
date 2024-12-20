package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.SteamingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMISteamingRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/steaming.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.BAMBOO_GRATE);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(Identifier.of(Bakingdelight.MOD_ID, "steaming"), WORKSTATION);

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMISteamingRecipe(SteamingRecipe recipe) {
        this.id = Identifier.of(Bakingdelight.MOD_ID,recipe.getResult(null).getTranslationKey()+recipe.hashCode());
        List<EmiIngredient> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()){
            inputs.add(EmiIngredient.of(ingredient));
        }
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.BAMBOO_COVER)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(Blocks.CAULDRON)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_COOKING_STOVE)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_CANISTER)));
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
        return 95;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,5,5,142,87,4,4);
        widgets.addSlot(input.get(0), 52, 15);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.BAMBOO_COVER)), 23, 6);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.BAMBOO_GRATE)), 23, 24);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(Blocks.CAULDRON)), 23, 55);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_COOKING_STOVE)), 23, 73);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_CANISTER)), 41, 73);
        widgets.addSlot(EmiStack.of(Fluids.WATER), 61, 55);

        widgets.addSlot(output.get(0), 106, 15).recipeContext(this);
    }

}
