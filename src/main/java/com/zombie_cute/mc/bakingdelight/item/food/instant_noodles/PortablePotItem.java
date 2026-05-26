package com.zombie_cute.mc.bakingdelight.item.food.instant_noodles;

import com.zombie_cute.mc.bakingdelight.components.ModComponents;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.InstantNoodleUtil;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.ArrayList;
import java.util.List;

public class PortablePotItem extends Item {
    public PortablePotItem() {
        super(new Settings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else {
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (!world.canPlayerModifyAt(user, blockPos)) {
                    return TypedActionResult.pass(itemStack);
                }
                if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                    world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                    boolean nbt = itemStack.getOrDefault(ModComponents.POT_HAS_WATER,false);
                    if (nbt){
                        return TypedActionResult.pass(itemStack);
                    }
                    if (world.isClient()){
                        return TypedActionResult.success(itemStack);
                    } else {
                        itemStack.set(ModComponents.POT_HAS_WATER,true);
                        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                        world.playSound(null,blockPos.getX(),blockPos.getY(),blockPos.getZ(),SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS,0.8F, 0.8F + user.getWorld().getRandom().nextFloat() * 0.4F);
                        if (hasQuicklime(itemStack) && hasNoodle(itemStack)){
                            ItemStack cooked = new ItemStack(ModItems.COOKED_PORTABLE_POT);
                            List<String> noodleNBT = itemStack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS,new ArrayList<>());
                            cooked.set(ModComponents.INSTANT_NOODLES_INGREDIENTS,noodleNBT);
                            user.giveItemStack(ModItems.DIRTY_WRAPPING_PAPER.getDefaultStack());
                            user.setStackInHand(hand,cooked);
                            return TypedActionResult.consume(this.craft(itemStack, user, cooked));
                        }
                    }
                }
            }
        }
        return TypedActionResult.pass(itemStack);
    }

    public ItemStack craft(ItemStack stack, PlayerEntity player, ItemStack outputStack) {
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        return ItemUsage.exchangeStack(stack, player, outputStack);
    }

    @Override
    public Text getName(ItemStack stack) {
        List<String> nbt = stack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS,new ArrayList<>());
        SpecialIngredient specialIngredient = InstantNoodleUtil.getSpecialIngredient(nbt);
        if (specialIngredient != null){
            return Text.translatable(specialIngredient.toTranslationKey());
        } else if (InstantNoodleUtil.isUnhealthy(nbt)) {
            return Text.translatable(TextUtil.NOODLE_UNHEALTHY);
        }
        return super.getName(stack);
    }

    @Override
    public boolean onClicked(ItemStack pot, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            List<String> potNoodleNBT = pot.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS,new ArrayList<>());
            if (otherStack.getItem() instanceof PackagedInstantNoodlesItem) {
                if (!hasNoodle(pot)){
                    List<String> noodleNBT = otherStack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS,new ArrayList<>());
                    otherStack.decrement(1);
                    pot.set(ModComponents.INSTANT_NOODLES_INGREDIENTS,noodleNBT);
                    this.playInsertSound(player);
                    if (hasWater(pot) && hasQuicklime(pot)){
                        cook(slot, noodleNBT, player);
                    }
                    return true;
                }
            } else if (isQuicklime(otherStack.getItem())) {
                if (hasQuicklime(pot)){
                    return false;
                }
                pot.set(ModComponents.POT_HAS_QUICKLIME,true);
                otherStack.decrement(1);
                this.playInsertSound(player);
                if (hasWater(pot) && hasNoodle(pot)){
                    cook(slot, potNoodleNBT, player);
                }
                return true;
            } else if (otherStack.getItem() == Items.WATER_BUCKET) {
                if (hasWater(pot)){
                    return false;
                }
                pot.set(ModComponents.POT_HAS_WATER,true);
                otherStack.decrement(1);
                cursorStackReference.set(Items.BUCKET.getDefaultStack());
                this.playWaterFillSound(player);
                if (hasNoodle(pot) && hasQuicklime(pot)){
                    cook(slot, potNoodleNBT, player);
                }
                return true;
            } else if (otherStack.isEmpty()) {
                if (hasNoodle(pot)){
                    ItemStack newNoodles = new ItemStack(ModItems.PACKAGED_INSTANT_NOODLES);
                    newNoodles.set(ModComponents.INSTANT_NOODLES_INGREDIENTS,potNoodleNBT);
                    cursorStackReference.set(newNoodles);
                    pot.remove(ModComponents.INSTANT_NOODLES_INGREDIENTS);
                    this.playRemoveSound(player);
                    return true;
                } else if (hasQuicklime(pot)) {
                    cursorStackReference.set(new ItemStack(ModItems.QUICKLIME));
                    pot.set(ModComponents.POT_HAS_QUICKLIME,false);
                    this.playRemoveSound(player);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onStackClicked(ItemStack pot, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType == ClickType.RIGHT) {
            ItemStack slotStack = slot.getStack();
            List<String> potNoodleNBT = pot.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS,new ArrayList<>());
            if (slotStack.getItem() instanceof PackagedInstantNoodlesItem) {
                if (!hasNoodle(pot)) {
                    List<String> noodleNBT = slotStack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS,new ArrayList<>());
                    pot.set(ModComponents.INSTANT_NOODLES_INGREDIENTS,noodleNBT);
                    slotStack.decrement(1);
                    slot.setStack(slotStack);
                    this.playInsertSound(player);
                    if (hasWater(pot) && hasQuicklime(pot)){
                        cook(pot, player, noodleNBT);
                    }
                    return true;
                }
            } else if (isQuicklime(slotStack.getItem())) {
                if (hasQuicklime(pot)){
                    return false;
                }
                pot.set(ModComponents.POT_HAS_QUICKLIME,true);
                slotStack.decrement(1);
                slot.setStack(slotStack);
                this.playInsertSound(player);
                if (hasWater(pot) && hasNoodle(pot)){
                    cook(pot, player, potNoodleNBT);
                }
                return true;
            } else if (slotStack.getItem() == Items.WATER_BUCKET) {
                if (hasWater(pot)){
                    return false;
                }
                pot.set(ModComponents.POT_HAS_WATER,true);
                slot.setStack(new ItemStack(Items.BUCKET));
                this.playWaterFillSound(player);
                if (hasNoodle(pot) && hasQuicklime(pot)){
                    cook(pot, player, potNoodleNBT);
                }
                return true;
            } else if (slotStack.getItem() == Items.BUCKET) {
                if (!hasWater(pot)){
                    return false;
                }
                this.playWaterFillSound(player);
                pot.set(ModComponents.POT_HAS_WATER,false);
                if (slotStack.getCount() == 1) {
                    slot.setStack(new ItemStack(Items.WATER_BUCKET));
                } else {
                    slotStack.decrement(1);
                    player.giveItemStack(new ItemStack(Items.WATER_BUCKET));
                }
                return true;
            } else if (slotStack.isEmpty()) {
                if (hasNoodle(pot)){
                    ItemStack newNoodles = new ItemStack(ModItems.PACKAGED_INSTANT_NOODLES);
                    newNoodles.set(ModComponents.INSTANT_NOODLES_INGREDIENTS,potNoodleNBT);
                    slot.setStack(newNoodles);
                    pot.remove(ModComponents.INSTANT_NOODLES_INGREDIENTS);
                    this.playRemoveSound(player);
                    return true;
                } else if (hasQuicklime(pot)) {
                    slot.setStack(ModItems.QUICKLIME.getDefaultStack());
                    pot.set(ModComponents.POT_HAS_QUICKLIME,false);
                    this.playRemoveSound(player);
                    return true;
                }
            }
        }
        return false;
    }
    private static void cook(Slot slot, List<String> noodlesData, PlayerEntity player) {
        player.giveItemStack(ModItems.DIRTY_WRAPPING_PAPER.getDefaultStack());
        ItemStack cooked = new ItemStack(ModItems.COOKED_PORTABLE_POT);
        cooked.set(ModComponents.INSTANT_NOODLES_INGREDIENTS, noodlesData);
        slot.setStack(cooked);
    }
    private static void cook(ItemStack pot, PlayerEntity player, List<String> noodlesData) {
        player.giveItemStack(ModItems.DIRTY_WRAPPING_PAPER.getDefaultStack());
        ItemStack cooked = new ItemStack(ModItems.COOKED_PORTABLE_POT);
        cooked.set(ModComponents.INSTANT_NOODLES_INGREDIENTS, noodlesData);
        player.giveItemStack(cooked);
        pot.decrement(1);
    }

    protected void playWaterFillSound(Entity entity){
        entity.playSound(SoundEvents.ITEM_BOTTLE_FILL, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }
    protected void playRemoveSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }
    protected void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (hasQuicklime(stack)){
            tooltip.add(Text.translatable(TextUtil.POT_HAS_QUICKLIME).formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.translatable(TextUtil.POT_MISS_QUICKLIME).formatted(Formatting.DARK_RED));
        }
        if (hasWater(stack)){
            tooltip.add(Text.translatable(TextUtil.POT_HAS_WATER).formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.translatable(TextUtil.POT_MISS_WATER).formatted(Formatting.DARK_RED));
        }
        if (hasNoodle(stack)){
            List<String> noodleNBT = stack.getOrDefault(ModComponents.INSTANT_NOODLES_INGREDIENTS, new ArrayList<>());
            InstantNoodleUtil.setToolTipFromNoodles(noodleNBT,tooltip);
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    public static boolean hasNoodle(ItemStack stack){
        return stack.contains(ModComponents.INSTANT_NOODLES_INGREDIENTS);
    }
    public static boolean hasWater(ItemStack stack){
        return stack.getOrDefault(ModComponents.POT_HAS_WATER,false);
    }
    public static boolean hasQuicklime(ItemStack stack){
        return stack.getOrDefault(ModComponents.POT_HAS_QUICKLIME,false);
    }

    public static boolean isQuicklime(Item item) {
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.QUICKLIMES)) {
            if (item == registryEntry.value()) {
                return true;
            }
        }
        return false;
    }
}
