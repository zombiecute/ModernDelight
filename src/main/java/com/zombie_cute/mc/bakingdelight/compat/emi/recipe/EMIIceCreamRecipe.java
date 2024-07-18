package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.ForgeTagKeys;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMIIceCreamRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(Bakingdelight.MOD_ID, "textures/gui/compats/ice_cream.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.ICE_CREAM_MAKER);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(Bakingdelight.MOD_ID, "ice_cream_making"), WORKSTATION);

    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIIceCreamRecipe() {
        List<EmiIngredient> inputs = new ArrayList<>();
        inputs.add(EmiIngredient.of(ForgeTagKeys.CREAMS));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(Items.SUGAR)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(Items.EGG)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModItems.ICE_CREAM_CONE)));
        this.input = inputs;
        this.output = List.of(EmiStack.of(ModItems.ICE_CREAM));
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return new Identifier(Bakingdelight.MOD_ID,"ice_cream_making");
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
        return 90;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,5,5,142,83,4,4);
        widgets.addSlot(EmiIngredient.of(ForgeTagKeys.CREAMS), 44, 9);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(Items.SUGAR)), 44, 27);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(Items.EGG)), 44, 45);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModItems.ICE_CREAM_CONE)), 33, 69);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.ICE_CREAM_MAKER)), 66, 69);


        widgets.addSlot(output.get(0), 100, 69).recipeContext(this);
    }

}
