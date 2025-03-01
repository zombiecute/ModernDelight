package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.OvenBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.screen.slot.OnlyExtractSlot;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class OvenScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final OvenBlockEntity blockEntity;
    public OvenScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(5));
    }
    public OvenScreenHandler(int syncId, PlayerInventory playerInventory,
                             BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.OVEN_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),6);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((OvenBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,20,21));
        this.addSlot(new Slot(inventory,1,38,21));
        this.addSlot(new Slot(inventory,2,56,21));
        this.addSlot(new Slot(inventory,3,74,21));
        this.addSlot(new Slot(inventory,4,48,52));
        this.addSlot(new OnlyExtractSlot(inventory,5,133,21));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addProperties(arrayPropertyDelegate);
    }
    public int getExperiences() {
        return this.propertyDelegate.get(4);
    }
    public boolean isBurning(){
        return propertyDelegate.get(3) > 0;
    }
    public boolean isCrafting(){
        return propertyDelegate.get(0) > 0;
    }
    public int getScaledProgress(){
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1); // Max Progress
        int progressArrowSize = 24;// Arrow's Width

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    public int getScaledBurnTime(){
        int progress = this.propertyDelegate.get(2);
        int maxProgress = this.propertyDelegate.get(3); // Max Progress
        int progressArrowSize = 11;// Arrow's Width
        int result = maxProgress != 0 && progress != 0 ? (progressArrowSize * progress/maxProgress) : 0;
        return progress > 0 && result == 0 ? 1 : result;
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
            this.addSlot(new Slot (playerInventory, i, 8 + i * 18, 142));
        }
    }

}
