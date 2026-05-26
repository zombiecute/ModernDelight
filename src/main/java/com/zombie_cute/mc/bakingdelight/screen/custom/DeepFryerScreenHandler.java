package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying.DeepFryerBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.screen.util.OnlyShowSlot;
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

public class DeepFryerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final DeepFryerBlockEntity blockEntity;
    public DeepFryerScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos),
                new ArrayPropertyDelegate(6));
    }
    public DeepFryerScreenHandler(int syncId, PlayerInventory playerInventory,
                                  BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.DEEP_FRYER_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),4);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((DeepFryerBlockEntity) blockEntity);

        this.addSlot(new OnlyShowSlot(inventory,0,54,21));
        this.addSlot(new OnlyShowSlot(inventory,1,72,21));
        this.addSlot(new OnlyShowSlot(inventory,2,90,21));
        this.addSlot(new OnlyShowSlot(inventory,3,108,21));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addProperties(arrayPropertyDelegate);
    }
    public boolean isBurning(){
        return propertyDelegate.get(4) > 0;
    }
    public int getScaledProgress(int slot){
        int progress = this.propertyDelegate.get(slot);
        int maxProgress = 300;
        int progressArrowSize = 16;// Arrow's Width

        return progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    public boolean hasOil(){
        return this.propertyDelegate.get(5) > 0;
    }
    public int getOil(){
        return this.propertyDelegate.get(5);
    }
    public int getScaledOilLevel(){
        int progress = this.propertyDelegate.get(5);
        int maxProgress = DeepFryerBlockEntity.MAX_OIL; // Max Progress
        int progressArrowSize = 24;// Arrow's Width
        int result = progress != 0 ? progressArrowSize * progress/maxProgress : 0;
        return progress > 0 && result == 0 ? 1 : result;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot >= 4 && slot < 31) {
                if (!this.insertItem(itemStack2, 31, 40, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 31 && slot < 40 && !this.insertItem(itemStack2, 4, 31, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            }
            slot2.markDirty();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot2.onTakeItem(player, itemStack2);
            this.sendContentUpdates();
        }
        return itemStack;
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
