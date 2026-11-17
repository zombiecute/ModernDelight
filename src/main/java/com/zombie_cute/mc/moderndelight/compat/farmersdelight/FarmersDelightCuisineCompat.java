package com.zombie_cute.mc.moderndelight.compat.farmersdelight;

import com.zombie_cute.mc.moderndelight.recipe.custom.CuisineRecipe;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
import vectorwing.farmersdelight.refabricated.inventory.ItemStackHandler;
import vectorwing.farmersdelight.refabricated.inventory.RecipeWrapper;

import java.util.List;

/** Loaded only after checking that Farmer's Delight is installed. */
public final class FarmersDelightCuisineCompat {
    private static final String ID_PREFIX = "cutting/";

    private FarmersDelightCuisineCompat() {
    }

    public static List<CuisineRecipe> getAll(RecipeManager manager) {
        return manager.listAllOfType(ModRecipeTypes.CUTTING.get()).stream()
                .<CuisineRecipe>map(CuttingCuisineRecipe::new).toList();
    }

    public static List<CuisineRecipe> getMatches(SimpleInventory input, World world) {
        RecipeWrapper wrapper = wrapInput(input);
        return world.getRecipeManager().getAllMatches(ModRecipeTypes.CUTTING.get(), wrapper, world).stream()
                .filter(recipe -> recipe.getTool().test(input.getStack(1)))
                .<CuisineRecipe>map(CuttingCuisineRecipe::new).toList();
    }

    @Nullable
    public static CuisineRecipe get(RecipeManager manager, Identifier id) {
        if (!id.getNamespace().equals("moderndelight") || !id.getPath().startsWith(ID_PREFIX)) {
            return null;
        }
        String source = id.getPath().substring(ID_PREFIX.length());
        int separator = source.indexOf('/');
        if (separator < 1) {
            return null;
        }
        Identifier sourceId = Identifier.tryParse(source.substring(0, separator) + ":" + source.substring(separator + 1));
        return sourceId == null ? null : manager.get(sourceId).map(FarmersDelightCuisineCompat::wrap).orElse(null);
    }

    @Nullable
    public static CuisineRecipe wrap(Recipe<?> recipe) {
        return recipe instanceof CuttingBoardRecipe cutting ? new CuttingCuisineRecipe(cutting) : null;
    }

    private static RecipeWrapper wrapInput(SimpleInventory input) {
        ItemStackHandler handler = new ItemStackHandler(1);
        handler.setStackInSlot(0, input.getStack(0).copyWithCount(1));
        return new RecipeWrapper(handler);
    }

    private static final class CuttingCuisineRecipe extends CuisineRecipe {
        private final CuttingBoardRecipe source;

        private CuttingCuisineRecipe(CuttingBoardRecipe source) {
            // Separate display IDs avoid collisions with the cutting board's REI/EMI entries.
            super(new Identifier("moderndelight", ID_PREFIX + source.getId().getNamespace() + "/" + source.getId().getPath()),
                    DefaultedList.copyOf(Ingredient.EMPTY, source.getIngredients().get(0), source.getTool()),
                    source.getOutput(null).copy());
            this.source = source;
        }

        @Override
        public boolean matches(SimpleInventory inventory, World world) {
            return source.getTool().test(inventory.getStack(1)) && source.matches(wrapInput(inventory), world);
        }

        @Override
        public boolean requiresOutputRoll() {
            return true;
        }

        @Override
        public List<Output> getOutputs() {
            return source.getRollableResults().stream()
                    .map(result -> new Output(result.getStack().copy(), result.getChance())).toList();
        }

        @Override
        public List<ItemStack> rollOutputs(SimpleInventory input, World world) {
            return source.rollResults(world.random, EnchantmentHelper.getLevel(Enchantments.FORTUNE, input.getStack(1)),
                    wrapInput(input));
        }

        @Override
        public SoundEvent getCraftingSound() {
            Identifier sound = Identifier.tryParse(source.getSoundEventID());
            return sound == null ? super.getCraftingSound()
                    : Registries.SOUND_EVENT.getOrEmpty(sound).orElseGet(super::getCraftingSound);
        }
    }
}
