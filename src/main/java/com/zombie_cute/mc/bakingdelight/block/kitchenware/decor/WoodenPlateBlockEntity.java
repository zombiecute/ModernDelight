package com.zombie_cute.mc.bakingdelight.block.kitchenware.decor;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.util.enums.ShowAbleItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WoodenPlateBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory {
    public WoodenPlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WOODEN_PLATE_BLOCK_ENTITY, pos, state);
    }
    final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1,ItemStack.EMPTY);
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,inventory);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,inventory);
        markDirty();
    }

    @Override
    public void markDirty() {
        if (world != null) {
            if (!world.isClient()){
                if (!getStack(0).isEmpty()){
                    if (getStack(0).getCount() > 1){
                        ItemStack copy = getStack(0).copy();
                        copy.setCount(getStack(0).getCount()-1);
                        getStack(0).setCount(1);
                        ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),copy);
                    }
                }
            }
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
            ItemStackSyncS2CPacket.send(pos,getItems(),world);
        }
        super.markDirty();
    }
    public ItemStack getRenderStack(){
        if (getCachedState().get(WoodenPlateBlock.SHOWING_ITEM) == ShowAbleItems.EMPTY){
            return getStack(0);
        }
        return ItemStack.EMPTY;
    }
    public void use(World world, BlockState state, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isEmpty()){
            if (!getStack(0).isEmpty()){
                world.setBlockState(pos,state.with(WoodenPlateBlock.SHOWING_ITEM,ShowAbleItems.EMPTY));
                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),getStack(0));
                setStack(0,ItemStack.EMPTY);
                world.playSound(null,pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS,1.0f,0.8f);
                markDirty();
            }
        } else {
            if (getStack(0).isEmpty()){
                world.setBlockState(pos,state.with(WoodenPlateBlock.SHOWING_ITEM,ShowAbleItems.getValue(itemStack.getItem())));
                setStack(0,itemStack.split(1));
                world.playSound(null,pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS,1.0f,0.8f);
                markDirty();
            }
        }
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }
}
