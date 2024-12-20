package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.entities.CuisineTableBlockEntity;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.util.NetworkHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class CuisineTableScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final CuisineTableBlockEntity blockEntity;

    public CuisineTableScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos));
    }
    public CuisineTableScreenHandler(int syncId, PlayerInventory playerInventory,
                                     BlockEntity blockEntity){
        super(ModScreenHandlers.CUISINE_TABLE_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),3);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.blockEntity = ((CuisineTableBlockEntity) blockEntity);
        this.addSlot(new Slot(inventory,0,24,49));
        this.addSlot(new Slot(inventory,1,24,28));
        this.addSlot(new Slot(inventory,2,136,49){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                ItemStack tool = CuisineTableScreenHandler.this.inventory.getStack(1);
                if (player instanceof ServerPlayerEntity serverPlayer){
                    World world = serverPlayer.getWorld();
                    BlockPos pos = CuisineTableScreenHandler.this.blockEntity.getPos();
                    CuisineTableScreenHandler.this.blockEntity.removeStack(0,1);
                    if (tool.isDamageable()){
                        if (tool.getMaxDamage()>tool.getDamage()+1){
                            tool.setDamage(tool.getDamage()+1);
                            CuisineTableScreenHandler.this.blockEntity.setStack(1,tool);
                        } else {
                            CuisineTableScreenHandler.this.blockEntity.setStack(1,ItemStack.EMPTY);
                            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        }
                    } else {
                        if (tool.getTranslationKey().equals("item.create.wrench")){
                            if (world.random.nextDouble() < 0.15){
                                CuisineTableScreenHandler.this.blockEntity.setStack(1,ItemStack.EMPTY);
                                world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            }
                        } else {
                            CuisineTableScreenHandler.this.blockEntity.removeStack(1,1);
                        }
                    }
                    world.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                super.onTakeItem(player, stack);
            }
        });
        addPlayerHotBar(playerInventory);
        addPlayerInventory(playerInventory);
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
    public void populateResult(ItemStack itemStack){
        if (itemStack.isEmpty()){
            NetworkHandler.sendUpdateInventoryPacket(this.blockEntity.getPos(), Items.AIR.toString());
        }
        NetworkHandler.sendUpdateInventoryPacket(this.blockEntity.getPos(), itemStack.getItem().toString(),itemStack.getCount());
    }
    @Override
    public ScreenHandlerType<?> getType() {
        return ModScreenHandlers.CUISINE_TABLE_SCREEN_HANDLER;
    }
    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
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
    private void addPlayerHotBar(PlayerInventory playerInventory){
        for (int i = 0; i < 9; ++i){
            this.addSlot(new Slot (playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
    }
}
