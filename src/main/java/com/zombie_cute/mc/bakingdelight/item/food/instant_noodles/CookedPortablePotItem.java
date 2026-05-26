package com.zombie_cute.mc.bakingdelight.item.food.instant_noodles;

import com.zombie_cute.mc.bakingdelight.components.ModComponents;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.util.InstantNoodleUtil;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CookedPortablePotItem extends Item {
    public CookedPortablePotItem() {
        super(new Settings().maxCount(1).food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.1f).build()));
    }
    @Override
    public Text getName(ItemStack stack) {
        List<String> nbt = stack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS,new ArrayList<>());
        SpecialIngredient special = InstantNoodleUtil.getSpecialIngredient(nbt);
        if (special != null){
            return Text.translatable(special.toTranslationKey());
        } else if (InstantNoodleUtil.isUnhealthy(nbt)) {
            return Text.translatable(TextUtil.NOODLE_UNHEALTHY);
        }
        return super.getName(stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        List<String> nbt = stack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS,new ArrayList<>());
        InstantNoodleUtil.setToolTipFromNoodles(nbt,tooltip);
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        MiscUtil.applyFoodEffects(stack,user);
        if (user instanceof PlayerEntity player){
            if (!world.isClient()) {
                List<String> nbt = stack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS,new ArrayList<>());
                SpecialIngredient specialIngredient = InstantNoodleUtil.getSpecialIngredient(nbt);
                if (specialIngredient != null){
                    player.getHungerManager().add(8, 1.5F);
                } else {
                    List<ItemStack> items = InstantNoodleUtil.getStacksFromNbt(nbt);
                    if (items != null){
                        for (ItemStack item : items){
                            if (item.contains(DataComponentTypes.FOOD)){
                                player.eatFood(world, item, item.get(DataComponentTypes.FOOD));
                            } else {
                                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,200,0));
                                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER,300,0));
                            }
                        }
                    }
                }
                player.eatFood(world, ModItems.FRIED_NOODLES.getDefaultStack(), ModItems.FRIED_NOODLES.getDefaultStack().get(DataComponentTypes.FOOD));
                player.setStackInHand(player.getActiveHand(),ModItems.PORTABLE_POT.getDefaultStack());
            }
        }
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.canConsume(false)){
            user.setCurrentHand(hand);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
    public static ItemStack createCookedPot(List<Ingredient> ingredients){
        List<String> noodleNbt = new ArrayList<>();
        for (int i = 1; i <= ingredients.size();i++){
            try {
                String name = Registries.ITEM.getId(ingredients.get(i-1).getMatchingStacks()[0].getItem()).toString();
                noodleNbt.add(name);
            } catch (Exception ignored){}
        }
        ItemStack cookedPot = new ItemStack(ModItems.COOKED_PORTABLE_POT);
        cookedPot.set(ModComponents.INSTANT_NOODLES_INGREDIENTS,noodleNbt);
        return cookedPot;
    }
}
