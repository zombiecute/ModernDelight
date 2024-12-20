package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.WhiskingRecipe;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EMIWhiskingRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = Identifier.of(Bakingdelight.MOD_ID, "textures/gui/compats/glass_bowl.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.GLASS_BOWL);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(Identifier.of(Bakingdelight.MOD_ID, "whisking"), WORKSTATION);
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIWhiskingRecipe(WhiskingRecipe recipe) {
        this.id = Identifier.of(Bakingdelight.MOD_ID,recipe.getResult(null).getTranslationKey()+recipe.hashCode());
        this.input = List.of(EmiIngredient.of(recipe.getIngredients().get(0)));
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
        return 55;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE, 27, 5 ,69,47,26,4);

        widgets.addSlot(EmiIngredient.of(TagKeys.WHISKS,1),27,5);
        widgets.addSlot(input.get(0), 46, 23);

        widgets.addSlot(output.get(0), 101, 20).recipeContext(this);
    }
    
}
