package com.zombie_cute.mc.bakingdelight.item.custom;

import com.zombie_cute.mc.bakingdelight.util.components.ModComponents;
import com.zombie_cute.mc.bakingdelight.util.components.cumstom.FlavorComponent;
import com.zombie_cute.mc.bakingdelight.util.components.cumstom.FlavorListComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public class IceCreamItem extends Item {
    public IceCreamItem() {
        super(new Item.Settings().component(ModComponents.FLAVOR_LIST,
                        new FlavorListComponent(List.of(FlavorComponent.NULL))).maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        MutableText text = Text.translatable(FlavorComponent.TRANSLATION_KEY);
        text.append(Text.literal(":"));
        text.formatted(Formatting.DARK_GRAY);
        tooltip.add(text);
        FlavorListComponent flavorListComponent = stack.getOrDefault(ModComponents.FLAVOR_LIST,new FlavorListComponent(List.of()));
        List<FlavorComponent> flavors = flavorListComponent.flavors();
        for (FlavorComponent i : flavors){
            tooltip.add(Text.translatable(i.getTranslationKey()).formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, context, tooltip, type);
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
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player){
            if (!world.isClient()) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EAT,
                        SoundCategory.PLAYERS, 1.5f, 0.4f / world.getRandom().nextFloat() * 0.4f + 0.8f);
                FlavorListComponent flavorListComponent = stack.getOrDefault(ModComponents.FLAVOR_LIST,new FlavorListComponent(List.of()));
                List<FlavorComponent> flavors = flavorListComponent.flavors();
                int hunger = 0;
                for (FlavorComponent i : flavors){
                    hunger = hunger + i.getNutrition();
                    if (i.getName().equals(FlavorComponent.GOLDEN_APPLE.getName())){
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1));
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0));
                    }
                    if (i.getName().equals(FlavorComponent.MATCHA.getName())){
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 20 * 60, 0));
                    }
                }
                player.getHungerManager().add(hunger + 2, 0.3F);
            }
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.canConsume(true)){
            user.setCurrentHand(hand);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
