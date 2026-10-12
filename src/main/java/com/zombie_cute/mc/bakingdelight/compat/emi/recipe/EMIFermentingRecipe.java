package com.zombie_cute.mc.bakingdelight.compat.emi.recipe;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.recipe.custom.FermentingRecipe;
import com.zombie_cute.mc.bakingdelight.recipe.custom.SqueezeRecipe;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.BacteriaType;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EMIFermentingRecipe implements EmiRecipe {
    public static final Identifier TEXTURE = new Identifier(ModernDelightMain.MOD_ID, "textures/gui/compats/fermenting.png");
    public static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.INDUSTRIAL_FERMENTER);
    public static final EmiRecipeCategory CATEGORY
            = new EmiRecipeCategory(new Identifier(ModernDelightMain.MOD_ID, "fermenting"), WORKSTATION);

    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final Identifier id;
    private final int time;
    private final int luminosity;
    private final int temperature;
    private final BacteriaType inputBacteria;
    private final BacteriaType outputBacteria;
    private final int outputBacteriaCFU;
    public EMIFermentingRecipe(FermentingRecipe recipe) {
        List<EmiIngredient> inputs = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()){
            if (ingredient.test(Items.BARRIER.getDefaultStack())){
                inputs.add(EmiIngredient.of(Ingredient.ofStacks(ItemStack.EMPTY)));
            } else inputs.add(EmiIngredient.of(ingredient));
        }
        Fluid inputFluid = recipe.getInputFluid();
        if (inputFluid == Fluids.EMPTY){
            inputs.add(EmiIngredient.of(Collections.singletonList(EmiStack.of(inputFluid))));
        } else inputs.add(EmiIngredient.of(Collections.singletonList(EmiStack.of(inputFluid,recipe.getMinFluidAmount()))));
        this.input = inputs;

        ItemStack outputItem = recipe.getOutput(null);
        if (outputItem.getItem() == Items.BARRIER) {
            outputItem = ItemStack.EMPTY;
        }
        Fluid outputFluid = recipe.getOutputFluid();
        EmiStack outputFluidStack;
        if (recipe.getIngredients().isEmpty()){
            outputFluidStack = EmiStack.of(outputFluid);
        } else outputFluidStack = EmiStack.of(outputFluid,81);
        this.output = List.of(EmiStack.of(outputItem), outputFluidStack);
        this.id = recipe.getId();
        this.time = recipe.getTime();
        this.luminosity = recipe.getLuminosity();
        this.temperature = recipe.getTemperature();
        this.inputBacteria = recipe.getBacteria_input();
        this.outputBacteria = recipe.getBacteria_output();
        this.outputBacteriaCFU = recipe.getBacteria_output_amount();
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
        return 151;
    }

    @Override
    public int getDisplayHeight() {
        return 72;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE,5,5,143,64,4,4);
        widgets.addSlot(input.get(0), 11, 11);
        widgets.addSlot(input.get(1), 29, 11);
        widgets.addSlot(input.get(2), 11, 29);
        widgets.addSlot(input.get(3), 29, 29);
        widgets.addSlot(input.get(4), 20, 49);

        MutableText text1 = Text.translatable(TextUtil.FERMENTATION_TIME);
        text1.append(Text.literal(" "+time+" s"));
        widgets.addTooltipText(List.of(text1),49,6,18,18);

        MutableText text2 = Text.translatable(TextUtil.FERMENTATION_LIGHT);
        text2.append(Text.literal(" "+luminosity));
        widgets.addTooltipText(List.of(text2),67, 6, 18, 18);

        MutableText text3 = Text.translatable(TextUtil.FERMENTATION_TEMP);
        text3.append(Text.literal(" "+temperature+" ℃"));
        widgets.addTooltipText(List.of(text3), 85,  6, 18, 18);

        if(inputBacteria != null) {
            widgets.addTooltipText(List.of(Text.translatable(inputBacteria.getTranslationKey())),
                    68,  35, 15, 15);
        } else {
            widgets.addTooltipText(List.of(Text.translatable(TextUtil.NO_BACTRIA)),
                    68,  35, 15, 15);
        }

        if(outputBacteria != null) {
            MutableText text4 = Text.literal(outputBacteriaCFU+" ").formatted(Formatting.GRAY);
            text4.append(Text.literal("CFU").formatted(Formatting.DARK_GRAY));
            widgets.addTooltipText(List.of(Text.translatable(outputBacteria.getTranslationKey()),text4),
                    97, 36, 13, 13);
        } else {
            widgets.addTooltipText(List.of(Text.translatable(TextUtil.NO_BACTRIA)),
                    97, 36, 15, 15);
        }

        widgets.addSlot(output.get(0), 119, 20).recipeContext(this);
        widgets.addSlot(output.get(1), 119, 49).recipeContext(this);
    }
}
