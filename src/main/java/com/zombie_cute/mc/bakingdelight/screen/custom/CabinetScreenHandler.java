package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.decor.CabinetBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CabinetScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final CabinetBlockEntity blockEntity;
    public CabinetScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos)
        );
    }

    public CabinetScreenHandler(int syncId, PlayerInventory playerInventory,
                                BlockEntity blockEntity){
        super(ModScreenHandlers.CABINET_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),36);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.blockEntity = ((CabinetBlockEntity) blockEntity);
        for (int i = 0; i < 4; ++i){
            for (int l = 0; l < 9; ++l){
                this.addSlot(new Slot(inventory,l + i * 9, 8 +l *18, 16 +i * 18));
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
                this.addSlot(new Slot(playerInventory, l + i * 9 +9, 8 +l *18, 102 +i * 18));
            }
        }
    }
    private void addPlayerHotbar(PlayerInventory playerInventory){
        for (int i = 0; i < 9; ++i){
            this.addSlot(new Slot (playerInventory, i, 8 + i * 18, 160));
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer){
            serverPlayer.getWorld().playSound(null,serverPlayer.getBlockPos(),SoundEvents.BLOCK_BARREL_CLOSE,SoundCategory.BLOCKS,1.0f,1.0f);
        }
        super.onClosed(player);
    }
}
