package com.zombie_cute.mc.bakingdelight.item.tools;

import com.zombie_cute.mc.bakingdelight.components.ModComponents;
import com.zombie_cute.mc.bakingdelight.enchantment.ModEnchantments;
import com.zombie_cute.mc.bakingdelight.recipe.custom.GrindingRecipe;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StoneMortarItem extends ToolItem {
    public StoneMortarItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public SoundEvent getEatSound() {
        return ModSounds.ITEM_STONE_MORTAR_WORKING;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 40;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player && !world.isClient()){
            ItemStack craftingStack = stack.getOrDefault(ModComponents.STONE_MORTAR_CRAFTING_STACK, ItemStack.EMPTY);
            if (!craftingStack.isEmpty()) {
                Set<RegistryEntry<Enchantment>> enchants = stack.getEnchantments().getEnchantments();
                int grinding_level = 0;
                for (RegistryEntry<Enchantment> enchantment : enchants) {
                    if (enchantment.matchesKey(ModEnchantments.FINE_GRINDING)){
                        grinding_level = stack.getEnchantments().getLevel(enchantment);
                        break;
                    }
                }
                List<ItemStack> outputs = craft(craftingStack,world,grinding_level);
                for (ItemStack itemStack : outputs){
                    player.giveItemStack(itemStack);
                }
            }
            stack.remove(ModComponents.STONE_MORTAR_CRAFTING_STACK);
            stack.damage(1, player,player.getActiveHand()==Hand.MAIN_HAND?EquipmentSlot.MAINHAND:EquipmentSlot.OFFHAND);
            player.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        return stack;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClient() && user instanceof PlayerEntity player){
            ItemStack crafting_stack = stack.getOrDefault(ModComponents.STONE_MORTAR_CRAFTING_STACK, ItemStack.EMPTY);
            if (!crafting_stack.isEmpty()) {
                player.giveItemStack(crafting_stack);
                world.playSound(null,user.getBlockPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat() + 0.8f);
            }
            stack.remove(ModComponents.STONE_MORTAR_CRAFTING_STACK);
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    private static List<ItemStack> craft(ItemStack stack, World world, int fine_grinding_level){
        Optional<RecipeEntry<GrindingRecipe>> match = world.getRecipeManager()
                .getFirstMatch(GrindingRecipe.Type.INSTANCE, new SingleStackRecipeInput(stack),world);
        List<ItemStack> outputs = new ArrayList<>();
        if (match.isPresent()){
            float luck = fine_grinding_level * 0.2f;
            ItemStack out1 = match.get().value().getResult(null).copy();
            ItemStack out2 = match.get().value().getChancedOutput().copy();
            if (Math.random() < luck){
                int count1 = out1.getCount() + world.random.nextBetween(1,fine_grinding_level+1);
                int count2 = out2.getCount() + world.random.nextBetween(1,fine_grinding_level+1);
                out1.setCount(Math.min(count1, out1.getMaxCount()));
                out2.setCount(Math.min(count2, out2.getMaxCount()));
            }
            outputs.add(out1);
            float chance = match.get().value().getChance();
            if (Math.random() < chance){
                outputs.add(out2);
            }
        }
        return outputs;
    }
    private static boolean hasRecipe(ItemStack stack, World world) {
        Optional<RecipeEntry<GrindingRecipe>> match = world.getRecipeManager()
                .getFirstMatch(GrindingRecipe.Type.INSTANCE, new SingleStackRecipeInput(stack),world);
        return match.isPresent();
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack thisStack = user.getStackInHand(hand);
        ItemStack cachedStack = getInsideStack(thisStack);
        if (cachedStack.isEmpty()){
            ItemStack input;
            if (hand == Hand.MAIN_HAND){
                input = user.getOffHandStack();
            } else {
                input = user.getMainHandStack();
            }
            if (hasRecipe(input,world)){
                if (!world.isClient()){
                    ItemStack newInput = input.copy();
                    newInput.setCount(1);
                    thisStack.set(ModComponents.STONE_MORTAR_CRAFTING_STACK,newInput);
                    input.decrement(1);
                    user.setCurrentHand(hand);
                }
                return TypedActionResult.consume(user.getStackInHand(hand));
            }
        } else {
            if (!world.isClient()){
                user.giveItemStack(cachedStack);
                thisStack.remove(ModComponents.STONE_MORTAR_CRAFTING_STACK);
                world.playSound(null,user.getBlockPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat() + 0.8f);
            }
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
    public static ItemStack getInsideStack(ItemStack stack){
        return stack.getOrDefault(ModComponents.STONE_MORTAR_CRAFTING_STACK,ItemStack.EMPTY);
    }
}
