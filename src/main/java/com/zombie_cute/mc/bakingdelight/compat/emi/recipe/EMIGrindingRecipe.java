package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.recipe.custom.GrindingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMIGrindingRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID, "textures/gui/compats/grinding.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModItems.STONE_MORTAR);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(ModernDelightMain.MOD_ID, "grinding"), WORKSTATION);
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIGrindingRecipe(GrindingRecipe recipe) {
        this.id = recipe.getId();
        this.input = List.of(EmiIngredient.of(recipe.getIngredients().get(0)),EmiIngredient.of(Ingredient.ofItems(ModItems.STONE_MORTAR)));
        List<EmiStack> stacks = new ArrayList<>();
        for (ItemStack item : recipe.getOutputs()){
            stacks.add(EmiStack.of(item));
        }
        this.output = stacks;
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
        widgets.addTexture(TEXTURE, 5, 5 ,142,45,4,4);
        widgets.addSlot(input.get(0), 15, 17);
        widgets.addSlot(EmiStack.of(ModItems.STONE_MORTAR), 57, 28);
        widgets.addSlot(output.get(0), 100, 17).recipeContext(this);
        widgets.addSlot(output.get(1), 118, 17).recipeContext(this);

    }

}
