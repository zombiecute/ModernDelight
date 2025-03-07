package com.zombie_cute.mc.bakingdelight.item.food;

import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.ModConfig;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SeasoningItem extends Item {
    public List<StatusEffectInstance> effects;
    public SeasoningItem(Settings settings, StatusEffectInstance... effects) {
        super(settings);
        this.effects = Arrays.asList(effects.clone());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(MiscUtil.SEASONING_TIP).formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
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
            if (this.isFood()) {
                ItemStack itemStack = user.getStackInHand(hand);
                if (user.canConsume(this.getFoodComponent().isAlwaysEdible())) {
                    user.setCurrentHand(hand);
                    return TypedActionResult.consume(itemStack);
                }
            }
        }
        if (foodItem.isFood()){
            if (world.isClient()){
                return TypedActionResult.success(user.getStackInHand(hand));
            } else {
                user.getItemCooldownManager().set(this, 20);
                if (foodItem.getItem() instanceof SeasoningItem){
                    user.sendMessage(Text.translatable(MiscUtil.FAILED_SEASONING),true);
                } else {
                    int foodCount = foodItem.getCount();
                    int thisCount = thisStack.getCount();
                    boolean count = thisCount > foodCount;
                    ItemStack newFood = foodItem.copy();
                    newFood.setCount(count ? foodCount : thisCount);
                    NbtCompound nbt = newFood.getOrCreateSubNbt("modern_delight_seasoning");
                    boolean hasAdded = false;
                    for (int i = 1; i <= getMaxSeasoning();i++){
                        if (nbt.contains("seasoning_"+i)){
                            continue;
                        }
                        String name = Registries.ITEM.getId(thisStack.getItem()).toString();
                        nbt.putString("seasoning_"+i,name);
                        hasAdded = true;
                        break;
                    }
                    if (hasAdded){
                        if (thisStack.getItem().getRecipeRemainder() != null){
                            ItemStack recipeRemainder = new ItemStack(thisStack.getItem().getRecipeRemainder(),newFood.getCount());
                            user.giveItemStack(recipeRemainder);
                        }
                        foodItem.decrement(newFood.getCount());
                        thisStack.decrement(newFood.getCount());
                        user.giveItemStack(newFood);
                        world.playSound(null,user.getX(),user.getY(),user.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat() + 0.8f);
                    } else {
                        user.sendMessage(Text.translatable(MiscUtil.FAILED_SEASONING),true);
                    }
                }
                return TypedActionResult.consume(user.getStackInHand(hand));
            }
        } else {
            user.sendMessage(Text.translatable(MiscUtil.NEED_FOOD),true);
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
