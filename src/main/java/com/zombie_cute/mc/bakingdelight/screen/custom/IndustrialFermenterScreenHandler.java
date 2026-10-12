package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.fermenter.IndustrialFermenterBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.packet.ChangeBlockEntityDataC2SPacket;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.screen.util.OnlyExtractSlot;
import com.zombie_cute.mc.bakingdelight.util.enums.BacteriaType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class IndustrialFermenterScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final IndustrialFermenterBlockEntity blockEntity;
    private final PropertyDelegate propertyDelegate;
    public IndustrialFermenterScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),new ArrayPropertyDelegate(12));
    }
    public IndustrialFermenterScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity,
                                            PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.INDUSTRIAL_FERMENTER_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),7);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.blockEntity = ((IndustrialFermenterBlockEntity) blockEntity);
        propertyDelegate = arrayPropertyDelegate;
        addProperties(arrayPropertyDelegate);

        this.addSlot(new Slot(inventory,0,61,25));
        this.addSlot(new Slot( inventory,1,79,25));
        this.addSlot(new Slot(inventory,2,61,43));
        this.addSlot(new Slot(inventory,3,79,43));
        this.addSlot(new OnlyExtractSlot(inventory,4,99,53));
        this.addSlot(new Slot(inventory,5,148,22));
        this.addSlot(new Slot(inventory,6,148,53));

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
    }
    @Environment(EnvType.CLIENT)
    public int getScaledProgress(){
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1); // Max Progress
        int progressArrowSize = 34;// Arrow's Width
        return maxProgress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    @Environment(EnvType.CLIENT)
    public int getTemperatureFix(){
        return this.propertyDelegate.get(3);
    }
    @Environment(EnvType.CLIENT)
    public int getTemperature(){
        return this.propertyDelegate.get(2) + this.propertyDelegate.get(3);
    }
    @Environment(EnvType.CLIENT)
    public boolean isPowered(){
        return this.propertyDelegate.get(4) != 0;
    }
    @Environment(EnvType.CLIENT)
    public int getEfficiency(){
        return this.propertyDelegate.get(5);
    }
    @Environment(EnvType.CLIENT)
    public int getCFUs(BacteriaType bacteriaType){
        return this.propertyDelegate.get(7 + bacteriaType.getRawID()) * 10;
    }
    @Environment(EnvType.CLIENT)
    public int getLightLevel(){
        return this.propertyDelegate.get(6);
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
            } else if (originalStack.getItem() instanceof BucketItem || originalStack.getItem() == Items.MILK_BUCKET) {
                if (!this.insertItem(originalStack, 5, 6, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, 4, false)) {
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
