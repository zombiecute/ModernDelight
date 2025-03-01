package com.zombie_cute.mc.bakingdelight.item.food;

import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.block_util.Drinkable;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class JuiceItem extends Item implements Drinkable {
    public List<StatusEffectInstance> effects = null;
    public int hunger;
    public float saturationModifier;
    public Item juice_container;
    public JuiceItem(int hunger, float saturationModifier, Item juice_container, StatusEffectInstance... effects) {
        super(new FabricItemSettings().maxCount(16).recipeRemainder(juice_container).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3f).build()));
        this.hunger = hunger;
        this.saturationModifier = saturationModifier;
        this.juice_container = juice_container;
        this.effects = Arrays.asList(effects.clone());
    }
    public JuiceItem(int hunger, float saturationModifier, Item juice_container) {
        super(new FabricItemSettings().maxCount(16).recipeRemainder(juice_container));
        this.hunger = hunger;
        this.saturationModifier = saturationModifier;
        this.juice_container = juice_container;
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        MiscUtil.applyFoodEffects(stack,user);
        if (user instanceof PlayerEntity player && !world.isClient()){
            drink(world,player);
            player.giveItemStack(juice_container.getDefaultStack());
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return getItemStackTypedActionResult(user, hand);
    }

    @Override
    public int getHunger() {
        return hunger;
    }

    @Override
    public float getSaturationModifier() {
        return saturationModifier;
    }

    @Override
    public List<StatusEffectInstance> getEffects() {
        return effects;
    }
}
