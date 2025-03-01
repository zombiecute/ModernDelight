package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power.FaradayGeneratorBlockEntity;
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
import net.minecraft.util.math.Vec3d;

public class FaradayGeneratorScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;
    public final FaradayGeneratorBlockEntity blockEntity;
    public FaradayGeneratorScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(1));
    }
    public FaradayGeneratorScreenHandler(int syncId, PlayerInventory playerInventory,
                                         BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.FARADAY_GENERATOR_SCREEN_HANDLER,syncId);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((FaradayGeneratorBlockEntity) blockEntity);

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);

        addProperties(arrayPropertyDelegate);
    }
    @Environment(EnvType.CLIENT)
    public int getChecked(){
        return this.propertyDelegate.get(0);
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY;
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
