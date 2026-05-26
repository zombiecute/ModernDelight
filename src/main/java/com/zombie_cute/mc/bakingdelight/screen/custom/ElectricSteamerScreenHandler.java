package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.steaming.ElectricSteamerBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.steaming.ElectricSteamerBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ElectricSteamerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final ElectricSteamerBlockEntity blockEntity;
    private final PropertyDelegate propertyDelegate;
    public ElectricSteamerScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos),new ArrayPropertyDelegate(27));
    }
    public ElectricSteamerScreenHandler(int syncId, PlayerInventory playerInventory,
                                        BlockEntity blockEntity, PropertyDelegate propertyDelegate){
        super(ModScreenHandlers.ELECTRIC_STEAMER_SCREEN_HANDLER,syncId);
        this.inventory = (Inventory) blockEntity;
        this.blockEntity = ((ElectricSteamerBlockEntity) blockEntity);
        this.propertyDelegate = propertyDelegate;
        addSlot(new Slot(inventory,0,54,8));
        addSlot(new Slot(inventory,1,72,8));
        addSlot(new Slot(inventory,2,36,26));
        addSlot(new Slot(inventory,3,54,26));
        addSlot(new Slot(inventory,4,72,26));
        addSlot(new Slot(inventory,5,90,26));
        addSlot(new Slot(inventory,6,36,44));
        addSlot(new Slot(inventory,7,54,44));
        addSlot(new Slot(inventory,8,72,44));
        addSlot(new Slot(inventory,9,90,44));
        addSlot(new Slot(inventory,10,54,62));
        addSlot(new Slot(inventory,11,72,62));
        addSlot(new Slot(inventory,12,152,61));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        addProperties(propertyDelegate);
    }
    @Environment(EnvType.CLIENT)
    public boolean isWorking(){
        if (blockEntity.getWorld() != null){
            return blockEntity.getWorld().getBlockState(blockEntity.getPos()).get(ElectricSteamerBlock.IS_WORKING);
        }
        return false;
    }
    @Environment(EnvType.CLIENT)
    public int getScaledProgress(int slot){
        int progress = this.propertyDelegate.get(slot);
        int maxProgress = this.propertyDelegate.get(slot+12); // Max Progress
        int progressArrowSize = 16;// Arrow's Width

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    @Environment(EnvType.CLIENT)
    public int getScaledSteamProgress(){
        int progress = this.propertyDelegate.get(26);
        int maxProgress = ElectricSteamerBlockEntity.MAX_STEAM_PROGRESS; // Max Progress
        int progressArrowSize = 24;// Arrow's Width

        return progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    @Environment(EnvType.CLIENT)
    public int getScaledSteam(){
        int progress = this.propertyDelegate.get(25);
        int maxProgress = ElectricSteamerBlockEntity.MAX_WATER_OR_STEAM; // Max Progress
        int progressArrowSize = 70;// Arrow's Width

        return progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    @Environment(EnvType.CLIENT)
    public int getScaledWater(){
        int progress = this.propertyDelegate.get(24);
        int maxProgress = ElectricSteamerBlockEntity.MAX_WATER_OR_STEAM; // Max Progress
        int progressArrowSize = 41;// Arrow's Width

        return progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    @Environment(EnvType.CLIENT)
    public int getWaterAmount(){
        return this.propertyDelegate.get(24);
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
            } else if (originalStack.getItem() == Items.WATER_BUCKET){
                if (!this.insertItem(originalStack, 12, 13, false)) {
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
