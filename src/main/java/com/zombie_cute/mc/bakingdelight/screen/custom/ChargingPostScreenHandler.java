package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.power.ChargingPostBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.batteries.BatteryBlockItem;
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
import net.minecraft.util.math.Vec3d;

public class ChargingPostScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final ChargingPostBlockEntity blockEntity;
    public ChargingPostScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos),
                new ArrayPropertyDelegate(4));
    }
    public ChargingPostScreenHandler(int syncId, PlayerInventory playerInventory,
                                     BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.CHARGING_POST_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),3);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((ChargingPostBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,35,29));
        this.addSlot(new Slot(inventory,1,35,47));
        this.addSlot(new Slot(inventory,2,125,39));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addProperties(arrayPropertyDelegate);
    }
    public int getPower(){
        return this.propertyDelegate.get(2);
    }
    public int getScaledPower(){
        int progress = this.propertyDelegate.get(2);
        int maxProgress = this.propertyDelegate.get(3); // Max Progress
        int progressArrowSize = 53;// Arrow's Width
        return progress != 0 && maxProgress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    public boolean isWorking(){
        return propertyDelegate.get(0) != 0;
    }
    public int getScaledProgress(){
        int progress = this.propertyDelegate.get(1);
        int maxProgress = 10; // Max Progress
        int progressArrowSize = 59;// Arrow's Width
        return progress != 0 ? progress * progressArrowSize / maxProgress : 0;
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
            } else if (!(originalStack.getItem() instanceof BatteryBlockItem)){
                if (!this.insertItem(originalStack, 2, 3, false)) {
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
            this.addSlot(new Slot (playerInventory, i, 8 + i * 18, 142));
        }
    }

}
