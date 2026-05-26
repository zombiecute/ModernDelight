package com.zombie_cute.mc.bakingdelight.screen.custom;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.CuisineTableBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.packet.UpdateInventoryC2SPacket;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
                        CuisineTableScreenHandler.this.blockEntity.removeStack(1,1);
                    }
                    world.playSound(null, pos, ModSounds.ITEM_STONE_MORTAR_WORKING, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
            if (invSlot == 2){
                originalStack.getItem().onCraft(originalStack, player.getWorld());
                if (!this.insertItem(originalStack, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                if (player instanceof ServerPlayerEntity serverPlayer){
                    ItemStack tool = CuisineTableScreenHandler.this.inventory.getStack(1);
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
                        CuisineTableScreenHandler.this.blockEntity.removeStack(1,1);
                    }
                    world.playSound(null, pos, ModSounds.ITEM_STONE_MORTAR_WORKING, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                slot.onQuickTransfer(originalStack, newStack);
            } else if (invSlot < 2) {
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
        UpdateInventoryC2SPacket.send(this.blockEntity.getPos(), itemStack);
    }
    @Override
    public ScreenHandlerType<?> getType() {
        return ModScreenHandlers.CUISINE_TABLE_SCREEN_HANDLER;
    }
    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
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
    private void addPlayerHotBar(PlayerInventory playerInventory){
        for (int i = 0; i < 9; ++i){
            this.addSlot(new Slot (playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer){
            World world = serverPlayer.getWorld();
            BlockPos pos = blockEntity.getPos();
            if (world.getBlockEntity(pos) instanceof CuisineTableBlockEntity entity){
                entity.setStack(2,ItemStack.EMPTY);
                entity.setCanOpen(true);
            }
        }
        super.onClosed(player);
    }
}
