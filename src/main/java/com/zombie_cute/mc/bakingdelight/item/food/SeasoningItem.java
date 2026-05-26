package com.zombie_cute.mc.bakingdelight.item.food;

import com.zombie_cute.mc.bakingdelight.util.ModConfig;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.components.ModComponents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeasoningItem extends Item {
    public List<StatusEffectInstance> effects;
    public SeasoningItem(Settings settings, StatusEffectInstance... effects) {
        super(settings);
        this.effects = Arrays.asList(effects.clone());
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable(TextUtil.SEASONING_TIP).formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, type);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack foodItem;
        ItemStack thisStack = user.getStackInHand(hand);
        if (hand == Hand.OFF_HAND){
            foodItem = user.getMainHandStack();
        } else {
            foodItem = user.getOffHandStack();
        }
        if (foodItem.isEmpty()){
            return super.use(world, user, hand);
        }
        if (foodItem.getComponents().contains(DataComponentTypes.FOOD)){
            if (world.isClient()){
                return TypedActionResult.success(user.getStackInHand(hand));
            } else {
                user.getItemCooldownManager().set(this, 20);
                if (foodItem.getItem() instanceof SeasoningItem){
                    user.sendMessage(Text.translatable(TextUtil.FAILED_SEASONING),true);
                } else {
                    int foodCount = foodItem.getCount();
                    int thisCount = thisStack.getCount();
                    boolean count = thisCount > foodCount;
                    ItemStack newFood = foodItem.copy();
                    newFood.setCount(count ? foodCount : thisCount);
                    List<String> seasoningList = new ArrayList<>(
                            newFood.getOrDefault(ModComponents.SEASONING_ITEMS, List.of())
                    );
                    if (seasoningList.size() < getMaxSeasoning()){
                        String name = Registries.ITEM.getId(thisStack.getItem()).toString();
                        seasoningList.add(name);
                        newFood.set(ModComponents.SEASONING_ITEMS,seasoningList);
                        if (thisStack.getItem().getRecipeRemainder() != null){
                            ItemStack recipeRemainder = new ItemStack(thisStack.getItem().getRecipeRemainder(),newFood.getCount());
                            user.giveItemStack(recipeRemainder);
                        }
                        foodItem.decrement(newFood.getCount());
                        thisStack.decrement(newFood.getCount());
                        user.giveItemStack(newFood);
                        world.playSound(null,user.getX(),user.getY(),user.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat() + 0.8f);
                    } else {
                        user.sendMessage(Text.translatable(TextUtil.FAILED_SEASONING),true);
                    }
                }
                return TypedActionResult.consume(user.getStackInHand(hand));
            }
        } else {
            user.sendMessage(Text.translatable(TextUtil.NEED_FOOD),true);
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
    }
    public static int getMaxSeasoning(){
        try {
            int m = ModConfig.maxSeasonings;
            if (m > 0){
                return m;
            } else return 5;
        } catch (Throwable e){
            return 5;
        }
    }
    public List<StatusEffectInstance> getEffects() {
        return effects;
    }
}
