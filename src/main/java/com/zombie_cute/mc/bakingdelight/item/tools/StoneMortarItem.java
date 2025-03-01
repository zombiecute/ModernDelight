package com.zombie_cute.mc.bakingdelight.item.tools;

import com.zombie_cute.mc.bakingdelight.enchantment.ModEnchantments;
import com.zombie_cute.mc.bakingdelight.recipe.custom.GrindingRecipe;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
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
    public static final Set<Enchantment> ALLOWED_ENCHANTMENTS = Set.of(
            ModEnchantments.FINE_GRINDING,
            Enchantments.UNBREAKING);
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public SoundEvent getEatSound() {
        return ModSounds.ITEM_STONE_MORTAR_WORKING;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity player && !world.isClient()){
            NbtCompound nbt = stack.getSubNbt("crafting_stack");
            if (nbt != null){
                ItemStack input = ItemStack.fromNbt(nbt);
                NbtList enchants = stack.getEnchantments();
                short grinding_level = 0;
                for(int i = 0; i < enchants.size(); ++i) {
                    NbtCompound nbtCompound = enchants.getCompound(i);
                    String name = nbtCompound.getString("id");
                    try {
                        Enchantment enchant = Registries.ENCHANTMENT.get(new Identifier(name));
                        if (enchant == ModEnchantments.FINE_GRINDING){
                            grinding_level = nbtCompound.getShort("lvl");
                            break;
                        }
                    } catch (Exception ignored){}
                }
                List<ItemStack> outputs = craft(input,world,grinding_level);
                for (ItemStack itemStack : outputs){
                    player.giveItemStack(itemStack);
                }
            }
            stack.removeSubNbt("crafting_stack");
            stack.damage(1, player, (p) -> p.sendToolBreakStatus(player.getActiveHand()));
            player.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        return stack;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClient() && user instanceof PlayerEntity player){
            NbtCompound nbt = stack.getSubNbt("crafting_stack");
            if (nbt != null){
                ItemStack input = ItemStack.fromNbt(nbt);
                player.giveItemStack(input);
                world.playSound(null,user.getBlockPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat() + 0.8f);
            }
            stack.removeSubNbt("crafting_stack");
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    private static List<ItemStack> craft(ItemStack stack, World world, short fine_grinding_level){
        SimpleInventory inventory = new SimpleInventory(1);
        inventory.setStack(0,stack);
        Optional<GrindingRecipe> match = world.getRecipeManager()
                .getFirstMatch(GrindingRecipe.Type.INSTANCE, inventory,world);
        List<ItemStack> outputs = new ArrayList<>();
        if (match.isPresent()){
            float luck = fine_grinding_level * 0.2f;
            ItemStack out1 = match.get().getOutput(null).copy();
            ItemStack out2 = match.get().getChancedOutput().copy();
            if (Math.random() < luck){
                int count1 = out1.getCount() + world.random.nextBetween(1,fine_grinding_level+1);
                int count2 = out2.getCount() + world.random.nextBetween(1,fine_grinding_level+1);
                out1.setCount(Math.min(count1, out1.getMaxCount()));
                out2.setCount(Math.min(count2, out2.getMaxCount()));
            }
            outputs.add(out1);
            float chance = match.get().getChance();
            if (Math.random() < chance){
                outputs.add(out2);
            }
        }
        return outputs;
    }
    private static boolean hasRecipe(ItemStack stack, World world) {
        SimpleInventory inventory = new SimpleInventory(1);
        inventory.setStack(0,stack);
        Optional<GrindingRecipe> match = world.getRecipeManager()
                .getFirstMatch(GrindingRecipe.Type.INSTANCE, inventory,world);
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
                    NbtCompound nbt = thisStack.getOrCreateSubNbt("crafting_stack");
                    ItemStack newInput = input.copy();
                    newInput.setCount(1);
                    newInput.writeNbt(nbt);
                    input.decrement(1);
                    user.setCurrentHand(hand);
                }
                return TypedActionResult.consume(user.getStackInHand(hand));
            }
        } else {
            if (!world.isClient()){
                user.giveItemStack(cachedStack);
                thisStack.removeSubNbt("crafting_stack");
                world.playSound(null,user.getBlockPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat() + 0.8f);
            }
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
    public static ItemStack getInsideStack(ItemStack stack){
        NbtCompound nbt = stack.getSubNbt("crafting_stack");
        if (nbt != null){
            try {
                return ItemStack.fromNbt(nbt);
            } catch (Exception ignored){}
        }
        return ItemStack.EMPTY;
    }
}
