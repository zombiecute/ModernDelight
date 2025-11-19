package com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DeepFryBasketBlockEntity extends BlockEntity implements ImplementedInventory {
    public DeepFryBasketBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEEP_FRY_BASKET_BLOCK_ENTITY, pos, state);
    }
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    public void onUse(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isEmpty()){
            for(int i = 0; i < getItems().size(); i ++){
                if (!getStack(i).isEmpty()){
                    ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),getStack(i).copy());
                    setStack(i,ItemStack.EMPTY);
                    world.playSound(null,pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.8f);
                    markDirty();
                    break;
                }
            }
        } else {
            NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(itemStack);
            if (nbtCompound != null) {
                if (nbtCompound.contains("Items", 9)) {
                    player.sendMessage(Text.translatable(TextUtil.PUN),true);
                    return;
                }
            }
            for(int i = 0; i < getItems().size(); i ++){
                if (getStack(i).isEmpty()){
                    setStack(i,itemStack.copy());
                    player.setStackInHand(hand,ItemStack.EMPTY);
                    world.playSound(null,pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+1.2f);
                    markDirty();
                    break;
                }
            }
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt,getItems());
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt,getItems());
        super.writeNbt(nbt);
    }

    @Override
    public void markDirty() {
        if (world != null) {
            ItemStackSyncS2CPacket.send(pos,getItems(),world);
        }
        super.markDirty();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
}
