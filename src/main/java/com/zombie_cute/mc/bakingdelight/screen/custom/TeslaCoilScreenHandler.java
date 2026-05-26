package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.power.TeslaCoilBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class TeslaCoilScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;
    public final TeslaCoilBlockEntity blockEntity;
    public TeslaCoilScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos),
                new ArrayPropertyDelegate(2));
    }
    public TeslaCoilScreenHandler(int syncId, PlayerInventory playerInventory,
                                  BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.TESLA_COIL_SCREEN_HANDLER,syncId);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((TeslaCoilBlockEntity) blockEntity);

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addProperties(arrayPropertyDelegate);
    }
    @Environment(EnvType.CLIENT)
    public int getEfficiency(){
        return this.propertyDelegate.get(0);
    }
    @Environment(EnvType.CLIENT)
    public boolean getShowParticle(){
        return this.propertyDelegate.get(1) != 0;
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot >= 0 && slot < 27) {
                if (!this.insertItem(itemStack2, 27, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= 27 && slot < 36 && !this.insertItem(itemStack2, 0, 27, false)) {
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
