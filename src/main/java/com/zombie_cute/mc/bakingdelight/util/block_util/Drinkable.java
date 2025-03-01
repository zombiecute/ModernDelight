package com.zombie_cute.mc.bakingdelight.util.block_util;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Drinkable {
    int getHunger();
    float getSaturationModifier();
    List<StatusEffectInstance> getEffects();
    default void drink(World world, PlayerEntity player) {
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GENERIC_DRINK,
                SoundCategory.PLAYERS, 1.5f, 0.4f / world.getRandom().nextFloat() * 0.4f + 0.8f);
        if (getEffects() != null){
            for (StatusEffectInstance effect : getEffects()){
                StatusEffectInstance effectInstance = new StatusEffectInstance(effect);
                player.addStatusEffect(effectInstance);
            }
        }
        player.getHungerManager().add(getHunger(), getSaturationModifier());
    }
    default @NotNull TypedActionResult<ItemStack> getItemStackTypedActionResult(PlayerEntity user, Hand hand) {
        if (user.canConsume(false)){
            user.setCurrentHand(hand);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
