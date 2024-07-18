package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.ForgeTagKeys;
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

public class EMIPizzaRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID, "textures/gui/compats/pizza.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.WHEAT_DOUGH);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(Bakingdelight.MOD_ID, "pizza_making"), WORKSTATION);

    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIPizzaRecipe() {
        List<EmiIngredient> inputs = new ArrayList<>();
        inputs.add(EmiIngredient.of(ModTagKeys.KNEADING_STICKS));
        inputs.add(EmiIngredient.of(ForgeTagKeys.PIZZA_INGREDIENTS));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModItems.CHEESE)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.PIZZA_WIP)));
        this.input = inputs;
        this.output = List.of(EmiStack.of(ModBlocks.RAW_PIZZA),EmiStack.of(ModBlocks.PIZZA_WIP));
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return new Identifier(Bakingdelight.MOD_ID,"pizza_making");
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
        return 55;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,5,5,142,47,4,4);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.WHEAT_DOUGH)), 5, 10);
        widgets.addSlot(EmiIngredient.of(ModTagKeys.KNEADING_STICKS), 24, 29);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModItems.CHEESE)), 44, 29);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.PIZZA_WIP)), 67, 10);
        widgets.addSlot(EmiIngredient.of(ForgeTagKeys.PIZZA_INGREDIENTS), 86, 29);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModItems.CHEESE)), 106, 29);


        widgets.addSlot(output.get(0), 129, 10).recipeContext(this);
    }

}
