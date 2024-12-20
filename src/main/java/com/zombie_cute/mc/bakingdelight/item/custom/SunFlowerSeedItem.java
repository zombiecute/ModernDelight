package com.zombie_cute.mc.bakingdelight.item.custom;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class SunFlowerSeedItem extends Item {
    public SunFlowerSeedItem() {
        super(new Item.Settings());
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 10;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player){
            player.getItemCooldownManager().set(this, 8);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EAT,
                    SoundCategory.PLAYERS, 1.5f, 0.4f / world.getRandom().nextFloat() * 0.4f + 0.8f);
            if (!world.isClient()) {
                ItemEntity itemEntity = new ItemEntity(world,user.getX(),user.getEyeY(),user.getZ(), ModItems.SUNFLOWER_SEED_PEEL.getDefaultStack());
                itemEntity.setVelocity(user.getRotationVecClient().multiply(0.5));
                itemEntity.setPickupDelay(20);
                world.spawnEntity(itemEntity);
            }
            player.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            player.giveItemStack(ModItems.SUNFLOWER_SEED_PULP.getDefaultStack());
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
