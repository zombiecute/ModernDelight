package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.entities.BiogasDigesterIOBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.screen.slot.OnlyExtractSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

public class BiogasDigesterIOScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final BiogasDigesterIOBlockEntity blockEntity;
    public BiogasDigesterIOScreenHandler(int i, PlayerInventory playerInventory, BlockPos pos) {
        this(i, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos),
                new ArrayPropertyDelegate(5));
    }
    public BiogasDigesterIOScreenHandler(int syncId, PlayerInventory playerInventory,
                                         BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.BIOGAS_DIGESTER_IO_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),12);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((BiogasDigesterIOBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,8,19));
        this.addSlot(new Slot(inventory,1,26,19));
        this.addSlot(new Slot(inventory,2,44,19));
        this.addSlot(new Slot(inventory,3,8,37));
        this.addSlot(new Slot(inventory,4,26,37));
        this.addSlot(new Slot(inventory,5,44,37));
        this.addSlot(new Slot(inventory,6,8,55));
        this.addSlot(new Slot(inventory,7,26,55));
        this.addSlot(new Slot(inventory,8,44,55));
        this.addSlot(new OnlyExtractSlot( inventory,9,152,19));
        this.addSlot(new OnlyExtractSlot( inventory,10,152,37));
        this.addSlot(new OnlyExtractSlot( inventory,11,152,55));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addProperties(arrayPropertyDelegate);
    }
    @Environment(EnvType.CLIENT)
    public boolean isCrafting(){
        return propertyDelegate.get(0) > 0;
    }
    @Environment(EnvType.CLIENT)
    public int getGasValue(){
        int gasValue = this.propertyDelegate.get(2);
        if (this.propertyDelegate.get(4) != 0){
            gasValue = gasValue * 19;
        }
        return gasValue;
    }
    @Environment(EnvType.CLIENT)
    public boolean isChecked(){
        return this.propertyDelegate.get(3) != 0;
    }
    @Environment(EnvType.CLIENT)
    public int getScaledProgress(){
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1); // Max Progress
        int progressArrowSize = 84;// Arrow's Width

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
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
        return this.inventory.canPlayerUse(player) && distance < 7 && !blockEntity.isRemoved();
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
