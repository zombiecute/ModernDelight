package com.zombie_cute.mc.bakingdelight.item.food.instant_noodles;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PortablePotItem extends Item {
    public PortablePotItem() {
        super(new FabricItemSettings().maxCount(1));
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
                    NbtCompound nbt = itemStack.getOrCreateNbt();
                    if (nbt.contains("has_water")){
                        if (nbt.getBoolean("has_water")){
                            return TypedActionResult.pass(itemStack);
                        }
                    }
                    if (world.isClient()){
                        return TypedActionResult.success(itemStack);
                    } else {
                        nbt.putBoolean("has_water",true);
                        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                        world.playSound(null,blockPos.getX(),blockPos.getY(),blockPos.getZ(),SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS,0.8F, 0.8F + user.getWorld().getRandom().nextFloat() * 0.4F);
                        if (hasQuicklime(itemStack) && hasNoodle(itemStack)){
                            ItemStack cooked = new ItemStack(ModItems.COOKED_PORTABLE_POT);
                            NbtCompound newNbt = cooked.getOrCreateNbt();
                            NbtCompound noodleNBT = nbt.getCompound("noodles_data").copy();
                            newNbt.put("noodles_data",noodleNBT);
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
        SpecialIngredient specialIngredient = getNoodleType(stack);
        if (specialIngredient != null){
            return Text.translatable(specialIngredient.toTranslationKey());
        } else if (CookedPortablePotItem.isUnhealthy(stack)) {
            return Text.translatable(MiscUtil.NOODLE_UNHEALTHY);
        }
        return super.getName(stack);
    }

    @Override
    public boolean onClicked(ItemStack pot, ItemStack noodles, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            NbtCompound nbt = pot.getOrCreateNbt();
            if (noodles.getItem() instanceof PackagedInstantNoodlesItem) {
                if (!nbt.contains("noodles_data")) {
                    NbtCompound noodleNBT = new NbtCompound();
                    ItemStack newNoodles = noodles.copy();
                    newNoodles.setCount(1);
                    newNoodles.writeNbt(noodleNBT);
                    nbt.put("noodles_data", noodleNBT);
                    noodles.decrement(1);
                    this.playInsertSound(player);
                    if (hasWater(pot) && hasQuicklime(pot)){
                        cook(slot, nbt, player);
                    }
                    return true;
                }
            } else if (isQuicklime(noodles.getItem())) {
                if (nbt.contains("has_quicklime")){
                    if (nbt.getBoolean("has_quicklime")){
                        return false;
                    }
                }
                nbt.putBoolean("has_quicklime",true);
                noodles.decrement(1);
                this.playInsertSound(player);
                if (hasWater(pot) && hasNoodle(pot)){
                    cook(slot, nbt, player);
                }
                return true;
            } else if (noodles.getItem() == Items.WATER_BUCKET) {
                if (nbt.contains("has_water")){
                    if (nbt.getBoolean("has_water")){
                        return false;
                    }
                }
                nbt.putBoolean("has_water",true);
                noodles.decrement(1);
                cursorStackReference.set(Items.BUCKET.getDefaultStack());
                this.playWaterFillSound(player);
                if (hasNoodle(pot) && hasQuicklime(pot)){
                    cook(slot, nbt, player);
                }
                return true;
            } else if (noodles.isEmpty()) {
                if (nbt.contains("noodles_data")){
                    ItemStack newNoodles = ItemStack.fromNbt(nbt.getCompound("noodles_data"));
                    cursorStackReference.set(newNoodles);
                    nbt.remove("noodles_data");
                    this.playRemoveSound(player);
                    return true;
                } else if (nbt.contains("has_quicklime")) {
                    if (nbt.getBoolean("has_quicklime")){
                        cursorStackReference.set(new ItemStack(ModItems.QUICKLIME));
                        nbt.putBoolean("has_quicklime",false);
                        this.playRemoveSound(player);
                        return true;
                    }
                }
            }
        }
        return false;
    }



    @Override
    public boolean onStackClicked(ItemStack pot, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType == ClickType.RIGHT) {
            ItemStack noodles = slot.getStack();
            NbtCompound nbt = pot.getOrCreateNbt();
            if (noodles.getItem() instanceof PackagedInstantNoodlesItem) {
                if (!nbt.contains("noodles_data")) {
                    NbtCompound noodleNBT = new NbtCompound();
                    ItemStack newNoodles = noodles.copy();
                    newNoodles.setCount(1);
                    newNoodles.writeNbt(noodleNBT);
                    nbt.put("noodles_data", noodleNBT);
                    noodles.decrement(1);
                    slot.setStack(noodles);
                    this.playInsertSound(player);
                    if (hasWater(pot) && hasQuicklime(pot)){
                        cook(pot, player, nbt);
                    }
                    return true;
                }
            } else if (isQuicklime(noodles.getItem())) {
                if (nbt.contains("has_quicklime")){
                    if (nbt.getBoolean("has_quicklime")){
                        return false;
                    }
                }
                nbt.putBoolean("has_quicklime", true);
                noodles.decrement(1);
                slot.setStack(noodles);
                this.playInsertSound(player);
                if (hasWater(pot) && hasNoodle(pot)){
                    cook(pot, player, nbt);
                }
                return true;
            } else if (noodles.getItem() == Items.WATER_BUCKET) {
                if (nbt.contains("has_water")){
                    if (nbt.getBoolean("has_water")){
                        return false;
                    }
                }
                nbt.putBoolean("has_water", true);
                slot.setStack(new ItemStack(Items.BUCKET));
                this.playWaterFillSound(player);
                if (hasNoodle(pot) && hasQuicklime(pot)){
                    cook(pot, player, nbt);
                }
                return true;
            } else if (noodles.isEmpty()) {
                if (nbt.contains("noodles_data")){
                    ItemStack newNoodles = ItemStack.fromNbt(nbt.getCompound("noodles_data"));
                    slot.setStack(newNoodles);
                    nbt.remove("noodles_data");
                    this.playRemoveSound(player);
                    return true;
                } else if (nbt.contains("has_quicklime")) {
                    if (nbt.getBoolean("has_quicklime")){
                        slot.setStack(ModItems.QUICKLIME.getDefaultStack());
                        nbt.putBoolean("has_quicklime",false);
                        this.playRemoveSound(player);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static void cook(Slot slot, NbtCompound nbt, PlayerEntity player) {
        player.giveItemStack(ModItems.DIRTY_WRAPPING_PAPER.getDefaultStack());
        ItemStack cooked = new ItemStack(ModItems.COOKED_PORTABLE_POT);
        NbtCompound newNbt = cooked.getOrCreateNbt();
        NbtCompound newNoodleNBT = nbt.getCompound("noodles_data").copy();
        newNbt.put("noodles_data",newNoodleNBT);
        slot.setStack(cooked);
    }
    private static void cook(ItemStack pot, PlayerEntity player, NbtCompound nbt) {
        player.giveItemStack(ModItems.DIRTY_WRAPPING_PAPER.getDefaultStack());
        ItemStack cooked = new ItemStack(ModItems.COOKED_PORTABLE_POT);
        NbtCompound newNbt = cooked.getOrCreateNbt();
        NbtCompound newNoodleNBT = nbt.getCompound("noodles_data").copy();
        newNbt.put("noodles_data",newNoodleNBT);
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (hasQuicklime(stack)){
            tooltip.add(Text.translatable(MiscUtil.POT_HAS_QUICKLIME).formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.translatable(MiscUtil.POT_MISS_QUICKLIME).formatted(Formatting.DARK_RED));
        }
        if (hasWater(stack)){
            tooltip.add(Text.translatable(MiscUtil.POT_HAS_WATER).formatted(Formatting.AQUA));
        } else {
            tooltip.add(Text.translatable(MiscUtil.POT_MISS_WATER).formatted(Formatting.DARK_RED));
        }
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains("noodles_data")){
            NbtCompound noodleNBT = nbt.getCompound("noodles_data");
            ItemStack noodles = ItemStack.fromNbt(noodleNBT);
            PackagedInstantNoodlesItem.setToolTipFromNoodles(noodles,tooltip);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
    public static boolean hasNoodle(ItemStack stack){
        NbtCompound nbt = stack.getNbt();
        if (nbt != null){
            return nbt.contains("noodles_data");
        }
        return false;
    }
    public static boolean hasWater(ItemStack stack){
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains("has_water")){
            return nbt.getBoolean("has_water");
        }
        return false;
    }
    public static boolean hasQuicklime(ItemStack stack){
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains("has_quicklime")){
            return nbt.getBoolean("has_quicklime");
        }
        return false;
    }
    public static SpecialIngredient getNoodleType(ItemStack stack){
        NbtCompound nbt = stack.getNbt();
        if (nbt != null){
            if (nbt.contains("noodles_data")){
                NbtCompound noodleNBT = nbt.getCompound("noodles_data");
                ItemStack noodles = ItemStack.fromNbt(noodleNBT);
                return PackagedInstantNoodlesItem.getSpecialIngredient(noodles);
            }
        }
        return null;
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
