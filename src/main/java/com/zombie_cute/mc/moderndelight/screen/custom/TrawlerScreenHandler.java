package com.zombie_cute.mc.moderndelight.screen.custom;

import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBlockEntity;
import com.zombie_cute.mc.moderndelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.moderndelight.screen.util.OnlyExtractSlot;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TrawlerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final TrawlerBlockEntity blockEntity;
    public TrawlerScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
    }
    public TrawlerScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity){
        super(ModScreenHandlers.TRAWLER_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),19);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.blockEntity = ((TrawlerBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,80,16));
        for (int i = 0; i < 2; ++i){
            for (int l = 0; l < 9; ++l){
                this.addSlot(new OnlyExtractSlot(inventory, l + i * 9 + 1, 8 + l * 18, 36 + i * 18));
            }
        }
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
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
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
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
