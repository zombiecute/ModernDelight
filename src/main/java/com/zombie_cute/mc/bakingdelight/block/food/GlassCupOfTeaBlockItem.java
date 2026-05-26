package com.zombie_cute.mc.bakingdelight.block.food;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.block_util.Drinkable;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlassCupOfTeaBlockItem extends BlockItem implements Drinkable {
    public GlassCupOfTeaBlockItem(Block block) {
        super(block, new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3f).build()));
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable(TextUtil.CAN_PLACE).formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
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
            player.giveItemStack(ModBlocks.GLASS_CUP.asItem().getDefaultStack());
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return stack;
    }
    @Override
    public int getHunger() {
        return ((GlassCupOfTeaBlock)getBlock()).getHunger();
    }
    @Override
    public float getSaturationModifier() {
        return ((GlassCupOfTeaBlock)getBlock()).getSaturationModifier();
    }
    @Override
    public List<StatusEffectInstance> getEffects() {
        return ((GlassCupOfTeaBlock)getBlock()).getEffects();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return getItemStackTypedActionResult(user, hand);
    }
}
