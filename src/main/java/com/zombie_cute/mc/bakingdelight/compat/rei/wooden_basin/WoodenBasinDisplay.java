package com.zombie_cute.mc.bakingdelight.compat.rei.wooden_basin;

import com.zombie_cute.mc.bakingdelight.recipe.custom.SqueezeRecipe;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;
import java.util.Optional;

public class WoodenBasinDisplay extends BasicDisplay {
    public WoodenBasinDisplay(SqueezeRecipe recipe){
        super(EntryIngredients.ofIngredients(recipe.getIngredients()),
                List.of(
                        EntryIngredients.of(recipe.getOutput(null)),
                        EntryIngredients.of(FluidStack.create(
                                recipe.getOutputFluid().fluidVariant.getFluid(),
                                recipe.getOutputFluid().amount_droplets)
                        )
                ),
                Optional.ofNullable(recipe.getId()));
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return WoodenBasinCategory.WOODEN_BASIN;
    }

}
