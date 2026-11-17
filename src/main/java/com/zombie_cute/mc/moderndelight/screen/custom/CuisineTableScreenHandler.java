package com.zombie_cute.mc.moderndelight.screen.custom;

import com.zombie_cute.mc.moderndelight.block.kitchenware.CuisineTableBlockEntity;
import com.zombie_cute.mc.moderndelight.recipe.CuisineRecipes;
import com.zombie_cute.mc.moderndelight.recipe.custom.CuisineRecipe;
import com.zombie_cute.mc.moderndelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.moderndelight.sound.ModSounds;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CuisineTableScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final CuisineTableBlockEntity blockEntity;
    @Nullable
    private Identifier selectedRecipeId;

    public CuisineTableScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
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
            public boolean canTakeItems(PlayerEntity player) {
                if (player.getWorld().isClient()) {
                    return true;
                }
                CuisineRecipe recipe = getSelectedRecipe();
                // Rolled results are committed by onSlotClick, never extracted as preview items.
                return canUse(player) && recipe != null && !recipe.requiresOutputRoll() && ItemStack.areEqual(getStack(),
                        recipe.craft(getRecipeInput(), player.getWorld().getRegistryManager()));
            }
            @Override
            public ItemStack takeStack(int amount) {
                return CuisineTableScreenHandler.this.inventory.removeStack(2);
            }
            @Override
            public boolean canTakePartial(PlayerEntity player) {
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
                    updateResult();
                }

                super.onTakeItem(player, stack);
            }
        });
        addPlayerHotBar(playerInventory);
        addPlayerInventory(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        if (invSlot == 2 && player instanceof ServerPlayerEntity serverPlayer) {
            CuisineRecipe recipe = getSelectedRecipe();
            if (recipe != null && recipe.requiresOutputRoll()) {
                ItemStack preview = inventory.getStack(2).copy();
                return craftRolledRecipe(serverPlayer, true, false) ? preview : ItemStack.EMPTY;
            }
        }
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack() && slot.canTakeItems(player)) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot == 2){
                originalStack.getItem().onCraft(originalStack, player.getWorld(), player);
                if (!this.insertItem(originalStack, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(originalStack, newStack);
                slot.setStack(ItemStack.EMPTY);
                slot.onTakeItem(player, newStack);
                player.dropItem(originalStack, false);
                return newStack;
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
    private SimpleInventory getRecipeInput() {
        return new SimpleInventory(inventory.getStack(0).copy(), inventory.getStack(1).copy());
    }

    @Nullable
    private CuisineRecipe getSelectedRecipe() {
        World world = blockEntity.getWorld();
        if (world == null || selectedRecipeId == null) {
            return null;
        }
        CuisineRecipe recipe = CuisineRecipes.get(world.getRecipeManager(), selectedRecipeId);
        return recipe != null && recipe.matches(getRecipeInput(), world) ? recipe : null;
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        if (slotIndex == 2 && player instanceof ServerPlayerEntity serverPlayer) {
            CuisineRecipe recipe = getSelectedRecipe();
            if (recipe != null && recipe.requiresOutputRoll()) {
                if (actionType == SlotActionType.PICKUP && (button == 0 || button == 1)) {
                    craftRolledRecipe(serverPlayer, false, false);
                } else if (actionType == SlotActionType.QUICK_MOVE) {
                    craftRolledRecipe(serverPlayer, true, false);
                } else if (actionType == SlotActionType.THROW && getCursorStack().isEmpty()) {
                    craftRolledRecipe(serverPlayer, false, true);
                }
                return;
            }
        }
        super.onSlotClick(slotIndex, button, actionType, player);
    }

    private boolean craftRolledRecipe(ServerPlayerEntity player, boolean toInventory, boolean dropOutput) {
        CuisineRecipe recipe = getSelectedRecipe();
        World world = player.getWorld();
        SimpleInventory input = getRecipeInput();
        if (!canUse(player) || recipe == null || !recipe.requiresOutputRoll()
                || !ItemStack.areEqual(inventory.getStack(2), recipe.craft(input, world.getRegistryManager()))) {
            return false;
        }

        ItemStack preview = inventory.getStack(2);
        ItemStack cursor = getCursorStack();
        if (!dropOutput) {
            if (toInventory) {
                boolean hasRoom = player.getInventory().main.stream().anyMatch(stack -> stack.isEmpty()
                        || ItemStack.canCombine(stack, preview) && stack.getCount() < stack.getMaxCount());
                if (!hasRoom) {
                    return false;
                }
            } else if (!cursor.isEmpty() && (!ItemStack.canCombine(cursor, preview)
                    || cursor.getCount() + preview.getCount() > cursor.getMaxCount())) {
                return false;
            }
        }

        // Roll only after a valid take. Previewing, selecting and syncing never spend randomness.
        List<ItemStack> results = recipe.rollOutputs(input, world);
        inventory.removeStack(0, 1);
        ItemStack tool = inventory.getStack(1);
        tool.damage(1, player, broken -> world.playSound(null, blockEntity.getPos(),
                SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F));

        for (int i = 0; i < results.size(); i++) {
            ItemStack result = results.get(i).copy();
            if (i == 0 && dropOutput) {
                player.dropItem(result, false);
            } else {
                if (i == 0 && !toInventory) {
                    if (cursor.isEmpty()) {
                        setCursorStack(result.split(Math.min(result.getCount(), result.getMaxCount())));
                    } else if (ItemStack.canCombine(cursor, result)) {
                        int count = Math.min(result.getCount(), cursor.getMaxCount() - cursor.getCount());
                        cursor.increment(count);
                        result.decrement(count);
                    }
                }
                player.getInventory().offerOrDrop(result);
            }
        }
        world.playSound(null, blockEntity.getPos(), recipe.getCraftingSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        blockEntity.markDirty();
        sendContentUpdates();
        return true;
    }

    public void selectRecipe(@Nullable Identifier recipeId) {
        if (blockEntity.getWorld() == null || blockEntity.getWorld().isClient()) {
            return;
        }
        selectedRecipeId = recipeId;
        sendContentUpdates();
    }

    private void updateResult() {
        World world = blockEntity.getWorld();
        if (world == null || world.isClient()) {
            return;
        }
        CuisineRecipe recipe = getSelectedRecipe();
        ItemStack result = recipe == null ? ItemStack.EMPTY
                : recipe.craft(getRecipeInput(), world.getRegistryManager()).copy();
        if (!ItemStack.areEqual(inventory.getStack(2), result)) {
            inventory.setStack(2, result);
            blockEntity.markDirty();
        }
    }

    @Override
    public void sendContentUpdates() {
        updateResult();
        super.sendContentUpdates();
    }
    @Override
    public ScreenHandlerType<?> getType() {
        return ModScreenHandlers.CUISINE_TABLE_SCREEN_HANDLER;
    }
    @Override
    public boolean canUse(@NotNull PlayerEntity player) {
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
