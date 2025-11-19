package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.AdvanceFurnaceBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.screen.util.OnlyExtractSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.registry.FuelRegistry;
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

public class AdvanceFurnaceScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final AdvanceFurnaceBlockEntity blockEntity;
    public AdvanceFurnaceScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
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
        this.addSlot(new OnlyExtractSlot(inventory,4,44,60));
        this.addSlot(new OnlyExtractSlot(inventory,5,80,60));
        this.addSlot(new OnlyExtractSlot(inventory,6,116,60));
        this.addSlot(new OnlyExtractSlot(inventory,7,152,60));
        this.addSlot(new Slot(inventory,8,8,28));

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
            } else if (FuelRegistry.INSTANCE.get(originalStack.getItem()) != null){
                if (!this.insertItem(originalStack, 8, 9, false)) {
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
