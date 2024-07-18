package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.FreezerBlockEntity;
import com.zombie_cute.mc.bakingdelight.recipe.custom.FreezingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMIFreezingRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID, "textures/gui/compats/freezer.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.FREEZER);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(Bakingdelight.MOD_ID, "freezing"), WORKSTATION);

    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final List<EmiIngredient> coolItems = new ArrayList<>();

    public EMIFreezingRecipe(FreezingRecipe recipe) {
        this.id = recipe.getId();
        List<EmiIngredient> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()){
            inputs.add(EmiIngredient.of(ingredient));
        }
        for (Item item : FreezerBlockEntity.createCoolTimeMap().keySet()){
            inputs.add(EmiIngredient.of(Ingredient.ofItems(item)));
            coolItems.add(EmiIngredient.of(Ingredient.ofItems(item)));
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
        return 70;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,4,26,144,42,3,25);
        widgets.addSlot(input.get(0), 47, 9);
        widgets.addSlot(input.get(1), 65, 9);
        widgets.addSlot(input.get(2), 83, 9);
        widgets.addSlot(EmiIngredient.of(coolItems),29,45);

        widgets.addSlot(output.get(0), 65, 45).recipeContext(this);
    }

}
