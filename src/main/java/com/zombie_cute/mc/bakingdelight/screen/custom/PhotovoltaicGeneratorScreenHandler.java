package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.entities.PhotovoltaicGeneratorBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
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

public class PhotovoltaicGeneratorScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final PhotovoltaicGeneratorBlockEntity blockEntity;
    public PhotovoltaicGeneratorScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos),
                new ArrayPropertyDelegate(4));
    }
    public PhotovoltaicGeneratorScreenHandler(int syncId, PlayerInventory playerInventory,
                                              BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.PHOTOVOLTAIC_GENERATOR_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),1);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((PhotovoltaicGeneratorBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,143,52));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addProperties(arrayPropertyDelegate);
    }
    @Environment(EnvType.CLIENT)
    public boolean isWorking(){
        return propertyDelegate.get(0) != 0;
    }
    @Environment(EnvType.CLIENT)
    public boolean isInSlowMode(){
        return propertyDelegate.get(3) != 0;
    }
    @Environment(EnvType.CLIENT)
    public int getYValue(){
        return blockEntity.getPos().getY();
    }
    @Environment(EnvType.CLIENT)
    public int getPower(){
        return this.propertyDelegate.get(1);
    }
    @Environment(EnvType.CLIENT)
    public int getScaledProgress(){
        int progress = this.propertyDelegate.get(1);
        int maxProgress = this.propertyDelegate.get(2); // Max Progress
        int progressArrowSize = 53;// Arrow's Width
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
