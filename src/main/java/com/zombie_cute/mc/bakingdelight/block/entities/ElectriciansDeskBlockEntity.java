package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.screen.custom.ElectriciansDeskScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ElectriciansDeskBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    public ElectriciansDeskBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ELECTRICIANS_DESK_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> ElectriciansDeskBlockEntity.this.canCraft;
                    case 1 -> ElectriciansDeskBlockEntity.this.isOccupied;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9,ItemStack.EMPTY);
    private int canCraft = 0;
    private int isOccupied = 0;
    private final PropertyDelegate propertyDelegate;

    public void setOccupied(boolean b){
        if (b){
            this.isOccupied = 1;
        } else {
            this.isOccupied = 0;
        }
    }
    @Override
    public Text getDisplayName() {
        return ModBlocks.ELECTRICIANS_DESK.getName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ElectriciansDeskScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt,registryLookup);
        Inventories.writeNbt(nbt,inventory,registryLookup);
        nbt.putInt("electricians_desk.canCraft",this.canCraft);
    }

    @Override
    public void readNbt(NbtCompound nbt,RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt,registryLookup);
        Inventories.readNbt(nbt,inventory,registryLookup);
        this.canCraft = nbt.getInt("electricians_desk.canCraft");
    }
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
    public boolean getCanCraft(){
        return this.canCraft != 0;
    }
    public void setCanCraft(boolean value){
        if (value){
            this.canCraft = 1;
        } else this.canCraft = 0;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
