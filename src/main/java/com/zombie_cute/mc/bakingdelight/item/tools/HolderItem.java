package com.zombie_cute.mc.bakingdelight.item.tools;

import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HolderItem extends Item {
    public HolderItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        BlockPos blockPos = context.getBlockPos();
        ItemStack holder = context.getStack();
        PlayerEntity player = context.getPlayer();
        if (!world.isClient()){
            if (world.getBlockEntity(blockPos) instanceof Inventory inventory){
                if (inventory.canPlayerUse(player)){
                    int slots = inventory.size();
                    ItemStack holdingStack = getHoldingStack(holder);
                    if (holdingStack.isEmpty()){
                        for (int i = 0 ; i < slots; i++){
                            ItemStack stack = inventory.getStack(i);
                            if (!stack.isEmpty()){
                                setHoldingStack(stack.copy(),holder);
                                inventory.setStack(i,ItemStack.EMPTY);
                                inventory.markDirty();
                                world.playSound(null,blockPos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat()+0.8f);
                                break;
                            }
                        }
                    } else {
                        for (int i = 0 ; i < slots; i++){
                            ItemStack stack = inventory.getStack(i);
                            if (holdingStack.getItem() == stack.getItem() && !stack.hasNbt()){
                                int holdingCount = holdingStack.getCount();
                                int stackCount = stack.getCount();
                                if (holdingCount + stackCount > holdingStack.getMaxCount()){
                                    stack.setCount(stack.getMaxCount());
                                    holdingStack.setCount(holdingCount - (stack.getMaxCount() - stackCount));
                                    setHoldingStack(holdingStack,holder);
                                    inventory.markDirty();
                                    world.playSound(null,blockPos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat()+0.3f);
                                    return ActionResult.CONSUME;
                                }
                            }
                            if (stack.isEmpty()){
                                inventory.setStack(i,holdingStack);
                                removeHoldingStack(holder);
                                inventory.markDirty();
                                world.playSound(null,blockPos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat()+0.3f);
                                break;
                            }
                        }
                    }
                }
            }
//            else if (world.getBlockEntity(blockPos) instanceof InventoryStorage storage) {
//                int slots = storage.getSlotCount();
//                ItemStack holdingStack = getHoldingStack(holder);
//                if (holdingStack.isEmpty()){
//                    for (int i = 0 ; i < slots; i++){
//                        ItemVariant itemVariant = storage.getSlot(i).getResource();
//                        long count = storage.getSlot(i).getResource().toStack().getCount();
//                        if (!itemVariant.isBlank()){
//                            try (Transaction transaction = Transaction.openOuter()){
//                                ItemVariant res = ItemVariant.blank();
//                                storage.getSlot(i).extract(res,count,transaction);
//                                transaction.commit();
//                                setHoldingStack(res.toStack(),holder);
//                                world.playSound(null,blockPos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat()+0.8f);
//                            }
//                            break;
//                        }
//                    }
//                } else {
//                    for (int i = 0 ; i < slots; i++){
//                        ItemVariant itemVariant = storage.getSlot(i).getResource();
//                        if (itemVariant.matches(holdingStack) || itemVariant.isBlank()){
//                            try (Transaction transaction = Transaction.openOuter()){
//                                ItemVariant res = ItemVariant.of(holdingStack);
//                                storage.getSlot(i).insert(res,holdingStack.getCount(),transaction);
//                                transaction.commit();
//                                setHoldingStack(res.toStack(),holder);
//                                world.playSound(null,blockPos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat()+0.3f);
//                                return ActionResult.CONSUME;
//                            }
//                        }
//                    }
//                }
//            }
        }
        return ActionResult.CONSUME;
    }

    public static ItemStack getHoldingStack(ItemStack holder) {
        NbtCompound nbt = holder.getSubNbt("holding_stack");
        if (nbt != null){
            return ItemStack.fromNbt(nbt);
        }
        return ItemStack.EMPTY;
    }
    public static void setHoldingStack(ItemStack holdingStack, ItemStack holder){
        NbtCompound nbt = holder.getOrCreateSubNbt("holding_stack");
        holdingStack.writeNbt(nbt);
    }
    public static void removeHoldingStack(ItemStack holder){
        holder.removeSubNbt("holding_stack");
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack holder = user.getStackInHand(hand);
        if (world.isClient()){
            return TypedActionResult.success(holder);
        }
        ItemStack otherStack;
        if (hand == Hand.MAIN_HAND){
            otherStack = user.getStackInHand(Hand.OFF_HAND);
        } else {
            otherStack = user.getStackInHand(Hand.MAIN_HAND);
        }
        ItemStack holdingStack = getHoldingStack(holder);
        if (!holdingStack.isEmpty()){
            user.giveItemStack(holdingStack);
            removeHoldingStack(holder);
            world.playSound(null,user.getBlockPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat()+0.8f);
        } else if (!otherStack.isEmpty()){
            NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(otherStack);
            if (otherStack.getItem() == this){
                ItemStack otherStackHolding = getHoldingStack(otherStack);
                if (otherStackHolding.getItem() == this){
                    user.sendMessage(Text.translatable(TextUtil.PUN),true);
                    return TypedActionResult.consume(holder);
                }
            }
            if (nbtCompound != null) {
                if (nbtCompound.contains("Items", 9)) {
                    user.sendMessage(Text.translatable(TextUtil.PUN),true);
                    return TypedActionResult.consume(holder);
                }
            }
            setHoldingStack(otherStack.copy(),holder);
            otherStack.decrement(otherStack.getCount());
            world.playSound(null,user.getBlockPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.0f,world.random.nextFloat()+0.8f);
        }
        return TypedActionResult.consume(holder);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.HOLDER)));

        } else {
            tooltip.add(TextUtil.getShiftText(false));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }
}
