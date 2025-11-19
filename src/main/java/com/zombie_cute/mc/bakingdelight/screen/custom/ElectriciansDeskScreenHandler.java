package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.power.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.screen.util.OnlyExtractSlot;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ElectriciansDeskScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final ElectriciansDeskBlockEntity blockEntity;
    public ElectriciansDeskScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(2));
    }
    public ElectriciansDeskScreenHandler(int syncId, PlayerInventory playerInventory,
                                         BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.ELECTRICIANS_DESK_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),9);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((ElectriciansDeskBlockEntity) blockEntity);
        this.addSlot(new Slot(inventory,0,24,17));
        this.addSlot(new Slot(inventory,1,42,17));
        this.addSlot(new Slot(inventory,2,60,17));
        this.addSlot(new Slot(inventory,3,24,35));
        this.addSlot(new Slot(inventory,4,42,35));
        this.addSlot(new Slot(inventory,5,60,35));

        this.addSlot(new Slot(inventory,6,135,17));
        this.addSlot(new Slot(inventory,7,135,35));

        this.addSlot(new OnlyExtractSlot(inventory,8,114,56));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addProperties(arrayPropertyDelegate);
    }
    public boolean canCraft(){
        return this.propertyDelegate.get(0) != 0 ;
    }
    public boolean isOccupied(){
        return this.propertyDelegate.get(1) != 0 ;
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()-1) {
                if (!this.insertItem(originalStack, this.inventory.size()-1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (MiscUtil.isInk(originalStack.getItem())){
                if (!this.insertItem(originalStack, 7, 8, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (originalStack.getItem() == Items.PAPER){
                if (!this.insertItem(originalStack, 6, 7, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size()-1, false)) {
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
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
