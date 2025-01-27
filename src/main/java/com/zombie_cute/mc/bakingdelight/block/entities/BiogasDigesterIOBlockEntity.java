package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.screen.custom.BiogasDigesterIOScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BiogasDigesterIOBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory, ExtendedScreenHandlerFactory {
    public BiogasDigesterIOBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIOGAS_DIGESTER_IO_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BiogasDigesterIOBlockEntity.this.progress;
                    case 1 -> BiogasDigesterIOBlockEntity.this.maxProgress;
                    case 2 -> BiogasDigesterIOBlockEntity.this.shortGasValue;
                    case 3 -> BiogasDigesterIOBlockEntity.this.checked;
                    case 4 -> BiogasDigesterIOBlockEntity.this.isSplit;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int size() {
                return 5;
            }
        };
    }
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(12,ItemStack.EMPTY);
    private int progress = 0;
    private int maxProgress = 0;
    private int gasValue = 0;
    private int checked = 0;
    private int counter = 0;
    private int shortGasValue = 0;
    private int isSplit = 0;
    protected final PropertyDelegate propertyDelegate;
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("biogas_digester_io.progress",progress);
        nbt.putInt("biogas_digester_io.maxProgress", maxProgress);
        nbt.putBoolean("biogas_digester_io.isCrafting", isCrafting);
        nbt.putInt("biogas_digester_io.tempGasValue", tempGasValue);
        nbt.putInt("biogas_digester_io.counter", counter);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("biogas_digester_io.progress");
        maxProgress = nbt.getInt("biogas_digester_io.maxProgress");
        isCrafting = nbt.getBoolean("biogas_digester_io.isCrafting");
        tempGasValue = nbt.getInt("biogas_digester_io.tempGasValue");
        counter = nbt.getInt("biogas_digester_io.counter");
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot >= 0 && slot <= 8;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot >= 9 && slot <= 11;
    }

    @Override
    public Text getDisplayName() {
        return ModBlocks.BIOGAS_DIGESTER_IO.getName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BiogasDigesterIOScreenHandler(syncId, playerInventory,this,this.propertyDelegate);
    }
    private boolean isCrafting = false;
    private int tempGasValue = 0;
    public void tick(World world, BlockPos pos, BiogasDigesterIOBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        if (world.getBlockEntity(pos.down()) instanceof BiogasDigesterControllerBlockEntity entity){
            if (entity.isChecked()){
                blockEntity.checked = 1;
                blockEntity.gasValue = entity.getGasValue();
                blockEntity.maxProgress = entity.getCurrentSize();
                if (gasValue > Short.MAX_VALUE){
                    shortGasValue = gasValue/19;
                    isSplit = 1;
                } else {
                    shortGasValue = gasValue;
                    isSplit = 0;
                }
                if (!blockEntity.isCrafting){
                    for (int i = 0;i<9;i++){
                        Item item = this.getStack(i).getItem().asItem();
                        if (isFood(item)){
                            blockEntity.tempGasValue = Objects.requireNonNull(item.getFoodComponent()).getHunger() * 30;
                            removeStack(i,1);
                            blockEntity.isCrafting = true;
                            break;
                        }
                    }
                } else {
                    inCreaseProgress();
                    if (blockEntity.progress == blockEntity.maxProgress){
                        entity.addGas(tempGasValue);
                        resetProgress();
                        blockEntity.counter += world.random.nextBetween(0,4);
                        if (blockEntity.counter >= 32){
                            putItem(world);
                            blockEntity.counter = 0;
                        }
                        blockEntity.tempGasValue = 0;
                        blockEntity.isCrafting = false;
                    }
                }
                markDirty();
            }
        } else {
            blockEntity.checked = 0;
            blockEntity.gasValue = 0;
            blockEntity.maxProgress = 0;
            blockEntity.tempGasValue = 0;
            blockEntity.isCrafting = false;
            resetProgress();
            markDirty();
        }
    }

    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        super.markDirty();
    }

    private void putItem(World world) {
        boolean hasSpawn = false;
        for (int i = 9;i<12;i++){
            if (this.getStack(i).isEmpty()){
                setStack(i,Items.BONE_MEAL.getDefaultStack());
                hasSpawn = true;
                break;
            } else if (this.getStack(i).getItem().equals(Items.BONE_MEAL)){
                int count = this.getStack(i).getCount();
                if (count < this.getStack(i).getMaxCount()){
                    setStack(i,new ItemStack(Items.BONE_MEAL,count+1));
                    hasSpawn = true;
                    break;
                }
            }
        }
        if (!hasSpawn){
            ItemScatterer.spawn(world,pos.getX(), pos.getY()+1, pos.getZ(),Items.BONE_MEAL.getDefaultStack());
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void inCreaseProgress() {
        progress++;
    }

    public boolean isFood(Item item){
        return item.isFood();
    }

}
