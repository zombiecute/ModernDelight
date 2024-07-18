package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.custom.ElectricSteamerBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.ElectricSteamerBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class ElectricSteamerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final ElectricSteamerBlockEntity blockEntity;
    public ElectricSteamerScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
    }
    public ElectricSteamerScreenHandler(int syncId, PlayerInventory playerInventory,
                                        BlockEntity blockEntity){
        super(ModScreenHandlers.ELECTRIC_STEAMER_SCREEN_HANDLER,syncId);
        this.inventory = (Inventory) blockEntity;
        this.blockEntity = ((ElectricSteamerBlockEntity) blockEntity);
        addSlot(new Slot(inventory,0,53,25));
        addSlot(new Slot(inventory,1,71,25));
        addSlot(new Slot(inventory,2,89,25));
        addSlot(new Slot(inventory,3,107,25));
        addSlot(new Slot(inventory,4,53,43));
        addSlot(new Slot(inventory,5,71,43));
        addSlot(new Slot(inventory,6,89,43));
        addSlot(new Slot(inventory,7,107,43));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
    }
    @Environment(EnvType.CLIENT)
    public boolean isWorking(){
        if (blockEntity.getWorld() != null){
            return blockEntity.getWorld().getBlockState(blockEntity.getPos()).get(ElectricSteamerBlock.IS_WORKING);
        }
        return false;
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
        return distance < 7 && !blockEntity.isRemoved();
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
