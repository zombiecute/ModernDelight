package com.zombie_cute.mc.moderndelight.screen.custom;

import com.zombie_cute.mc.moderndelight.block.power.ElectriciansDeskBlockEntity;
import com.zombie_cute.mc.moderndelight.recipe.custom.AssemblyRecipe;
import com.zombie_cute.mc.moderndelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.moderndelight.screen.util.OnlyExtractSlot;
import com.zombie_cute.mc.moderndelight.tag.TagKeys;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ElectriciansDeskScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final ElectriciansDeskBlockEntity blockEntity;
    @Nullable
    private Identifier preparedRecipeId;
    private boolean miniGameActive;
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

    public static boolean isInk(Item item) {
        return item.getRegistryEntry().isIn(TagKeys.INKS);
    }

    public boolean canCraft(){
        return this.propertyDelegate.get(0) != 0 ;
    }
    public boolean isOccupied(){
        return this.propertyDelegate.get(1) != 0 ;
    }

    private SimpleInventory getRecipeInput() {
        SimpleInventory input = new SimpleInventory(6);
        for (int i = 0; i < input.size(); i++) {
            input.setStack(i, inventory.getStack(i).copy());
        }
        return input;
    }

    private Optional<AssemblyRecipe> getRecipe() {
        return blockEntity.getWorld().getRecipeManager()
                .getFirstMatch(AssemblyRecipe.Type.INSTANCE, getRecipeInput(), blockEntity.getWorld());
    }

    private boolean hasPreparedRecipe() {
        return preparedRecipeId != null && getRecipe()
                .filter(recipe -> recipe.getId().equals(preparedRecipeId)).isPresent();
    }

    // The minigame result is a client signal, but its paid session and recipe belong to this menu.
    public void handleAction(ServerPlayerEntity player, int action) {
        if (player.currentScreenHandler != this || !player.isAlive() || player.isSpectator() || !canUse(player)) {
            return;
        }
        switch (action) {
            case 1 -> {
                if (miniGameActive && hasPreparedRecipe() && inventory.getStack(8).isEmpty()) {
                    miniGameActive = false;
                    blockEntity.setCanCraft(true);
                }
            }
            case 2 -> craft(player);
            case 3 -> {
                Optional<AssemblyRecipe> recipe = getRecipe();
                if (isOccupied() || canCraft() || recipe.isEmpty() || !inventory.getStack(8).isEmpty()
                        || !inventory.getStack(6).isOf(Items.PAPER)
                        || inventory.getStack(7).isEmpty() || !isInk(inventory.getStack(7).getItem())
                        || recipe.get().getMiniGameType() < 1 || recipe.get().getMiniGameType() > 3) {
                    return;
                }
                preparedRecipeId = recipe.get().getId();
                miniGameActive = true;
                inventory.removeStack(6, 1);
                inventory.removeStack(7, 1);
                blockEntity.setOccupied(true);
            }
            case 4 -> {
                if (preparedRecipeId != null) {
                    blockEntity.setOccupied(false);
                    if (miniGameActive) {
                        clearPreparation();
                    }
                }
            }
            case 5 -> clearPreparation();
        }
        blockEntity.markDirty();
        sendContentUpdates();
    }

    public void craft(ServerPlayerEntity player) {
        if (player.currentScreenHandler != this || !player.isAlive() || player.isSpectator() || !canUse(player)
                || miniGameActive || !canCraft() || isOccupied() || !hasPreparedRecipe()
                || !inventory.getStack(8).isEmpty()) {
            return;
        }
        ItemStack result = getRecipe().orElseThrow()
                .craft(getRecipeInput(), player.getWorld().getRegistryManager()).copy();
        if (result.isEmpty()) {
            return;
        }
        // Produce and pay on the server thread as a single operation; replayed requests see no credit.
        clearPreparation();
        for (int i = 0; i < 6; i++) {
            inventory.removeStack(i, 1);
        }
        inventory.setStack(8, result);
        blockEntity.markDirty();
        player.getWorld().playSound(null, blockEntity.getPos(), SoundEvents.UI_STONECUTTER_TAKE_RESULT,
                SoundCategory.BLOCKS, 1.0F, 1.0F);
        sendContentUpdates();
    }

    private void clearPreparation() {
        if (preparedRecipeId != null) {
            preparedRecipeId = null;
            miniGameActive = false;
            blockEntity.setCanCraft(false);
            blockEntity.setOccupied(false);
            blockEntity.markDirty();
        }
    }

    @Override
    public void sendContentUpdates() {
        if (!blockEntity.getWorld().isClient() && preparedRecipeId != null
                && (!hasPreparedRecipe() || !inventory.getStack(8).isEmpty())) {
            clearPreparation();
        }
        super.sendContentUpdates();
    }

    @Override
    public void onClosed(PlayerEntity player) {
        if (!player.getWorld().isClient()) {
            clearPreparation();
        }
        super.onClosed(player);
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
            } else if (isInk(originalStack.getItem())){
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
        return player.getWorld() == blockEntity.getWorld() && !blockEntity.isRemoved()
                && v.isInRange(player.getPos(), 8.0)
                && player.getWorld().getBlockEntity(pos) == blockEntity;
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
