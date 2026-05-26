package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.MixWithWaterRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EMIMixWithWaterRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = Identifier.of(ModernDelightMain.MOD_ID, "textures/gui/compats/water_glass_bowl.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.GLASS_BOWL);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(Identifier.of(ModernDelightMain.MOD_ID, "mix_with_water"), WORKSTATION);
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIMixWithWaterRecipe(MixWithWaterRecipe recipe) {
        this.input = List.of(EmiIngredient.of(recipe.getIngredients().getFirst()));
        this.output = List.of(EmiStack.of(recipe.getResult(null)));
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
        return 55;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE, 44, 20 ,52,22,43,19);
        widgets.addSlot(input.get(0), 48, 13);

        widgets.addSlot(output.get(0), 101, 20).recipeContext(this);
    }

}
