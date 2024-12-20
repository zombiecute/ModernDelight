package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.custom.BambooGrateBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.BambooGrateBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class BambooSteamerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final BambooGrateBlockEntity blockEntity;
    public int currentLayer;
    private final PropertyDelegate propertyDelegate;
    public BambooSteamerScreenHandler(int i, PlayerInventory playerInventory, BlockPos pos) {
        this(i, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos),
                playerInventory.player.getWorld().getBlockState(pos).get(BambooGrateBlock.LAYER),
                new ArrayPropertyDelegate(3));
    }
    public BambooSteamerScreenHandler(int syncId, PlayerInventory playerInventory,
                                      BlockEntity blockEntity, int currentLayer, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.BAMBOO_STEAMER_SCREEN_HANDLER,syncId);
        this.currentLayer = currentLayer;
        checkSize(((Inventory) blockEntity),16);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.blockEntity = ((BambooGrateBlockEntity) blockEntity);
        this.propertyDelegate = arrayPropertyDelegate;
        switch (currentLayer){
            case 1 -> {
                quickAddSlot(0,71,25);
                quickAddSlot(1,89,25);
                quickAddSlot(2,71,43);
                quickAddSlot(3,89,43);
            }
            case 2 -> {
                quickAddSlot(0,52,25);
                quickAddSlot(1,70,25);
                quickAddSlot(2,52,43);
                quickAddSlot(3,70,43);
                quickAddSlot(4,91,25);
                quickAddSlot(5,109,25);
                quickAddSlot(6,91,43);
                quickAddSlot(7,109,43);
            }
            case 3 -> {
                quickAddSlot(0,33,25);
                quickAddSlot(1,51,25);
                quickAddSlot(2,33,43);
                quickAddSlot(3,51,43);
                quickAddSlot(4,72,25);
                quickAddSlot(5,90,25);
                quickAddSlot(6,72,43);
                quickAddSlot(7,90,43);
                quickAddSlot(8,111,25);
                quickAddSlot(9,129,25);
                quickAddSlot(10,111,43);
                quickAddSlot(11,129,43);
            }
            case 4 -> {
                quickAddSlot(0,7,25);
                quickAddSlot(1,25,25);
                quickAddSlot(2,7,43);
                quickAddSlot(3,25,43);
                quickAddSlot(4,43,25);
                quickAddSlot(5,61,25);
                quickAddSlot(6,43,43);
                quickAddSlot(7,61,43);
                quickAddSlot(8,79,25);
                quickAddSlot(9,97,25);
                quickAddSlot(10,79,43);
                quickAddSlot(11,97,43);
                quickAddSlot(12,115,25);
                quickAddSlot(13,133,25);
                quickAddSlot(14,115,43);
                quickAddSlot(15,133,43);
            }
        }

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        addProperties(arrayPropertyDelegate);
    }
    private void quickAddSlot(int index,int x,int y){
        this.addSlot(new Slot(inventory,index,x,y));
    }
    public boolean isHeated(){
        return propertyDelegate.get(1) != 0;
    }
    public boolean isCovered(){
        return propertyDelegate.get(0) != 0;
    }
    public int getLayer(){
        return propertyDelegate.get(2);
    }
    public int getCurrentLayer() {
        return currentLayer;
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
        BlockPos pos1 = player.getBlockPos();
        BlockPos pos2 = blockEntity.getPos();
        double distance = Math.sqrt(Math.pow(pos2.getX()-pos1.getX(),2)+Math.pow(pos2.getY()-pos1.getY(),2)+Math.pow(pos2.getZ()-pos1.getZ(),2));
        return this.inventory.canPlayerUse(player) && distance < 7 &&
                !blockEntity.isRemoved() &&
                player.getWorld().getBlockState(pos2).get(BambooGrateBlock.LAYER) == this.currentLayer;
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
