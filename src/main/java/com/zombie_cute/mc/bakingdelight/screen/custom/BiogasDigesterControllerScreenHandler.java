package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.entities.BiogasDigesterControllerBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class BiogasDigesterControllerScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;
    public final BiogasDigesterControllerBlockEntity blockEntity;
    public BiogasDigesterControllerScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(4));
    }
    public BiogasDigesterControllerScreenHandler(int syncId, PlayerInventory playerInventory,
                                                 BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.BIOGAS_DIGESTER_CONTROLLER_SCREEN_HANDLER,syncId);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((BiogasDigesterControllerBlockEntity) blockEntity);

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addProperties(arrayPropertyDelegate);
    }
    @Environment(EnvType.CLIENT)
    public int getChecked(){
        return this.propertyDelegate.get(0);
    }
    @Environment(EnvType.CLIENT)
    public int getSize(){
        return this.propertyDelegate.get(1);
    }
    @Environment(EnvType.CLIENT)
    public int getGasValue(){
        int gasValue = this.propertyDelegate.get(2);
        if (this.propertyDelegate.get(3) != 0){
            gasValue = gasValue * 19;
        }
        return gasValue;
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY;
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
