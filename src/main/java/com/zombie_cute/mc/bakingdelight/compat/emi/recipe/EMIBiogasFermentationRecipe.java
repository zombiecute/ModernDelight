package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.biogas.BiogasDigesterIOBlockEntity;
import com.zombie_cute.mc.bakingdelight.compat.rei.biogas_fermentation.BiogasFermentationCategory;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMIBiogasFermentationRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID, "textures/gui/compats/biogas_fermentation.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.BIOGAS_DIGESTER_CONTROLLER);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(ModernDelightMain.MOD_ID, "biogas_fermentation"), WORKSTATION);

    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIBiogasFermentationRecipe() {
        List<EmiIngredient> inputs = new ArrayList<>();
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.BIOGAS_DIGESTER_IO)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_CANISTER)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModBlocks.BIOGAS_DIGESTER_CONTROLLER)));
        this.input = inputs;
        List<EmiStack> lists = new ArrayList<>(List.of(EmiStack.of(BiogasDigesterIOBlockEntity.getDigestate())));
        lists.add(EmiStack.of(ModFluid.STILL_LIQUEFIED_BIOGAS));
        this.output = lists;
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return new Identifier(ModernDelightMain.MOD_ID,"biogas_fermentation");
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
        return 122;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,5,5,142,114,4,4);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.GAS_CANISTER)), 85, 34).recipeContext(this);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.BIOGAS_DIGESTER_IO)), 67, 34);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModBlocks.BIOGAS_DIGESTER_CONTROLLER)), 67, 52);
        ItemStack stack = new ItemStack(Items.APPLE);
        stack.setCustomName(Text.translatable(BiogasFermentationCategory.FOOD));
        widgets.addSlot(EmiIngredient.of(Ingredient.ofStacks(stack)), 49, 13);

        widgets.addSlot(output.get(0), 31, 34).recipeContext(this);
        widgets.addSlot(output.get(1), 121, 34).recipeContext(this);
    }

}
