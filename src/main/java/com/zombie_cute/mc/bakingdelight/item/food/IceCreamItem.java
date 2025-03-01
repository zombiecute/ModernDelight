package com.zombie_cute.mc.bakingdelight.item.food;

import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.CreamFlavor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IceCreamItem extends Item {
    public IceCreamItem() {
        super(new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build()));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getNbt();
        if(nbt != null && nbt.contains("flavor")){
            MutableText text = Text.translatable(CreamFlavor.TRANSLATION_KEY);
            text.append(Text.literal(":"));
            text.formatted(Formatting.DARK_GRAY);
            tooltip.add(text);
            int[] array = nbt.getIntArray("flavor");
            for (int i : array){
                tooltip.add(Text.translatable(CreamFlavor.getFlavorByID(i).getTranslationKey()).formatted(Formatting.GRAY));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        MiscUtil.applyFoodEffects(stack,user);
        if (user instanceof PlayerEntity player){
            if (!world.isClient()) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EAT,
                        SoundCategory.PLAYERS, 1.5f, 0.4f / world.getRandom().nextFloat() * 0.4f + 0.8f);
                NbtCompound nbt  = stack.getNbt();
                int hunger = CreamFlavor.getHungerFromFlavorNBT(nbt);
                if (nbt != null && nbt.contains("flavor")){
                    int[] array = nbt.getIntArray("flavor");
                    for(int i : array){
                        CreamFlavor creamFlavor = CreamFlavor.getFlavorByID(i);
                        if (creamFlavor == CreamFlavor.GOLDEN_APPLE){
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1));
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0));
                            break;
                        }
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
        if (user.canConsume(false)){
            user.setCurrentHand(hand);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
}
