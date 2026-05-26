package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.food.instant_noodles.CookedPortablePotItem;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMIInstantNoodlesMakingRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID, "textures/gui/compats/instant_noodles.png");
    public static final EmiStack WORKSTATION = EmiStack.of(Blocks.CRAFTING_TABLE);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(ModernDelightMain.MOD_ID, "instant_noodles_making"), WORKSTATION);

    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public EMIInstantNoodlesMakingRecipe() {
        List<EmiIngredient> inputs = new ArrayList<>();
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModItems.FRIED_NOODLES)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModItems.PORTABLE_POT)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModItems.PACKAGED_INSTANT_NOODLES)));
        inputs.add(EmiIngredient.of(Ingredient.ofItems(ModItems.MULTIFUNCTIONAL_WRAPPING_PAPER)));
        for (SpecialIngredient specialIngredient : SpecialIngredient.values()){
            for(Ingredient ingredient : specialIngredient.getIngredients()){
                for(ItemStack stack : ingredient.getMatchingStacks()){
                    inputs.add(EmiIngredient.of(Ingredient.ofItems(stack.getItem())));
                }
            }
        }
        this.input = inputs;
        this.output = new ArrayList<>(List.of(EmiStack.of(ModItems.COOKED_PORTABLE_POT),EmiStack.of(ModItems.PACKAGED_INSTANT_NOODLES)));
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return new Identifier(ModernDelightMain.MOD_ID,"instant_noodles_making");
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
        return 116;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,5,5,143,109,4,4);

        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModItems.MULTIFUNCTIONAL_WRAPPING_PAPER)), 12, 7);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModItems.FRIED_NOODLES)), 30, 7);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModItems.PACKAGED_INSTANT_NOODLES)), 30, 50);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModItems.QUICKLIME)), 49, 50);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(Items.WATER_BUCKET)), 68, 50);
        widgets.addSlot(EmiIngredient.of(Ingredient.ofItems(ModItems.PORTABLE_POT)), 49, 82);
        widgets.addTooltip(List.of(TooltipComponent.of(Text.translatable(TextUtil.ANYTHING).asOrderedText())),47,6,18,18);
        List<ItemStack> exampleItems = new ArrayList<>();
        for (SpecialIngredient specialIngredient : SpecialIngredient.values()){
            exampleItems.add(CookedPortablePotItem.createCookedPot(specialIngredient.getIngredients()));
        }
        for (int i = 0; i < exampleItems.size(); i++){
            widgets.addSlot(EmiIngredient.of(Ingredient.ofStacks(exampleItems.get(i))), 129, 5 + (18 * i));
        }

        widgets.addSlot(output.get(0), 90, 82).recipeContext(this);
    }

}
