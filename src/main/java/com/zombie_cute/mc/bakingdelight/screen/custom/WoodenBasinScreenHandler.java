package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.WoodenBasinBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.screen.util.OnlyExtractSlot;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WoodenBasinScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final WoodenBasinBlockEntity blockEntity;
    public WoodenBasinScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
    }
    public WoodenBasinScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity){
        super(ModScreenHandlers.WOODEN_BASIN_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),5);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.blockEntity = ((WoodenBasinBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,20,21));
        this.addSlot(new OnlyExtractSlot( inventory,1,20,52));
        this.addSlot(new Slot(inventory,2,95,53));
        this.addSlot(new Slot(inventory,3,95,21));
        this.addSlot(new OnlyExtractSlot( inventory,4,141,53));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (originalStack.getItem() == Items.BUCKET ||
            originalStack.getItem() == Items.GLASS_BOTTLE) {
                if (!this.insertItem(originalStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (WoodenBasinBlockEntity.isFilter(originalStack.getItem())) {
                if (!this.insertItem(originalStack, 2, 3, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 3, 4, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        BlockPos pos = blockEntity.getPos();
        Vec3d v = new Vec3d(pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5);
        return !blockEntity.isRemoved() && v.isInRange(player.getPos(), 8.0);
    }
    private void addPlayerInventory(PlayerInventory playerInventory){
        for (int i = 0; i < 3; ++i){
            for (int l = 0; l < 9; ++l){
                this.addSlot(new Slot(playerInventory, l + i * 9 +9, 8 +l *18, 84 +i * 18));
            }
        }
    }
    private void addPlayerHotbar(PlayerInventory playerInventory){
        for (int i = 0; i < 9; ++i){
            this.addSlot(new Slot (playerInventory, i, 8 + i * 18, 142));
        }
    }

}
