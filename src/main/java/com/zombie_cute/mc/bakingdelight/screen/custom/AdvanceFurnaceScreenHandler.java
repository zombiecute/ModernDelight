package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.entities.AdvanceFurnaceBlockEntity;
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
public class AdvanceFurnaceScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final AdvanceFurnaceBlockEntity blockEntity;
    public AdvanceFurnaceScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos),
                new ArrayPropertyDelegate(8));
    }
    public AdvanceFurnaceScreenHandler(int syncId, PlayerInventory playerInventory,
                                       BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.ADVANCE_FURNACE_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),9);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((AdvanceFurnaceBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,44,16));
        this.addSlot(new Slot(inventory,1,80,16));
        this.addSlot(new Slot(inventory,2,116,16));
        this.addSlot(new Slot(inventory,3,152,16));
        this.addSlot(new Slot(inventory,8,8,28));
        this.addSlot(new OnlyExtractSlot(inventory,4,44,60));
        this.addSlot(new OnlyExtractSlot(inventory,5,80,60));
        this.addSlot(new OnlyExtractSlot(inventory,6,116,60));
        this.addSlot(new OnlyExtractSlot(inventory,7,152,60));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addProperties(arrayPropertyDelegate);
    }
    @Environment(EnvType.CLIENT)
    public boolean isCrafting(int slot){
        return switch (slot){
            case 0 -> propertyDelegate.get(0) > 0;
            case 1 -> propertyDelegate.get(1) > 0;
            case 2 -> propertyDelegate.get(2) > 0;
            case 3 -> propertyDelegate.get(3) > 0;
            default -> false;
        };
    }
    @Environment(EnvType.CLIENT)
    public boolean isBurning(){
        return propertyDelegate.get(5) > 0;
    }
    public int getExperiences(){
        return propertyDelegate.get(7);
    }
    @Environment(EnvType.CLIENT)
    public int getScaledProgress(int slot){
        int progress = this.propertyDelegate.get(slot);
        int maxProgress = this.propertyDelegate.get(4); // Max Progress
        int progressArrowSize = 24;// Arrow's Width

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    @Environment(EnvType.CLIENT)
    public int getScaledBurnTime(){
        int progress = this.propertyDelegate.get(5);
        int maxProgress = this.propertyDelegate.get(6); // Max Progress
        int progressArrowSize = 14;// Arrow's Width
        int result = maxProgress != 0 && progress != 0 ? (progressArrowSize * progress/maxProgress) : 0;
        return progress > 0 && result == 0 ? 2 : result;
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
                this.addSlot(new Slot(playerInventory, l + i * 9 +9, 8 +l *18, 90 +i * 18));
            }
        }
    }
    private void addPlayerHotbar(PlayerInventory playerInventory){
        for (int i = 0; i < 9; ++i){
            this.addSlot(new Slot (playerInventory, i, 8 + i * 18, 148));
        }
    }
}
