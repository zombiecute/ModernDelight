package com.zombie_cute.mc.bakingdelight.block.power;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.power.batteries.AbstractBatteryBlock;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.screen.custom.ChargingPostScreenHandler;
import com.zombie_cute.mc.bakingdelight.util.ModConfig;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.DCConsumer;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.Power;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.PowerStorageAble;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class ChargingPostBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos>, PowerStorageAble {
    public ChargingPostBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHARGING_POST_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> ChargingPostBlockEntity.this.isWorking;
                    case 1 -> ChargingPostBlockEntity.this.ticker;
                    case 2 -> (int)ChargingPostBlockEntity.this.power.getPowerValue();
                    case 3 -> (int)ChargingPostBlockEntity.this.power.getMaxPower();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }
    public static final float EFFICIENCY = 0.9f;
    public float getEfficiency(){
        try {
            float e = ModConfig.chargingPostEfficiency;
            if (e > 0 && e < 1){
                return e;
            } else return 0.9f;
        } catch (Throwable e){
            return 0.9f;
        }
    }
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3,ItemStack.EMPTY);
    public final Power power = new Power(1000);
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(10000,1000,1000){
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
    private final PropertyDelegate propertyDelegate;
    private int isWorking = 0;
    private int ticker = 0;
    private boolean dir = false;
    public static void tick(World world, BlockPos pos, BlockState state, ChargingPostBlockEntity b) {
        if (world.isClient){
            return;
        }
        b.power.setPowerValue(b.energyStorage.amount / 10);
        if (world.getTime() % 20L == 0){
            long battery1;
            long battery2;
            battery1 = AbstractBatteryBlock.getBatteryPower(b.getStack(0));
            battery2 = AbstractBatteryBlock.getBatteryPower(b.getStack(1));
            if (b.energyStorage.amount < b.energyStorage.capacity){
                if (battery1 >= 100){
                    AbstractBatteryBlock.changeBatteryPower(b.getStack(0),b.energyStorage,100,false);
                } else if (battery1 >= 10){
                    AbstractBatteryBlock.changeBatteryPower(b.getStack(0),b.energyStorage,10,false);
                } else if (battery1 >= 1){
                    AbstractBatteryBlock.changeBatteryPower(b.getStack(0),b.energyStorage,1,false);
                } else if (battery2 >= 100){
                    AbstractBatteryBlock.changeBatteryPower(b.getStack(1),b.energyStorage,100,false);
                } else if (battery2 >= 10){
                    AbstractBatteryBlock.changeBatteryPower(b.getStack(1),b.energyStorage,10,false);
                } else if (battery2 >= 1){
                    AbstractBatteryBlock.changeBatteryPower(b.getStack(1),b.energyStorage,1,false);
                }
            }
            if (b.getStack(2).getItem() instanceof DCConsumer consumer){
                Power p = consumer.getPower(b.getStack(2));
                long need = p.getMaxPower() - p.getPowerValue();
                if (need >= 30){
                    if (b.energyStorage.amount >= 30 * 10){
                        b.reduceEnergy(30 * 10);
                        consumer.addPower(b.getStack(2), (long) (30 * EFFICIENCY));
                        b.isWorking = 1;
                    } else if (b.energyStorage.amount > 10) {
                        b.reduceEnergy(10);
                        consumer.addPower(b.getStack(2),1);
                        b.isWorking = 1;
                    } else b.isWorking = 0;
                } else if (need > 0) {
                    if (b.energyStorage.amount > 10) {
                        b.reduceEnergy(10);
                        consumer.addPower(b.getStack(2),1);
                        b.isWorking = 1;
                    } else b.isWorking = 0;
                } else b.isWorking = 0;
            } else b.isWorking = 0;
        }
        if (b.isWorking != 0){
            if (!b.dir){
                b.ticker++;
            } else {
                b.ticker--;
            }
            if (b.ticker == 0){
                b.dir = false;
            }
            if (b.ticker == 10){
                b.dir = true;
            }
        } else {
            b.ticker = 0;
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt,inventory,registryLookup);
        nbt.putLong("charging_post.power",this.getPowerValue());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt,inventory,registryLookup);
        this.energyStorage.amount = nbt.getLong("charging_post.power") * 10;
        markDirty();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }

    @Override
    public Text getDisplayName() {
        return ModBlocks.CHARGING_POST.getName();
    }

    @Override
    public void markDirty() {
        if (world != null) {
            ItemStackSyncS2CPacket.send(pos,getItems(),world);
        }
        super.markDirty();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ChargingPostScreenHandler(syncId,playerInventory,this,propertyDelegate);
    }
    public ItemStack getRendererStack(){
        return this.getStack(2);
    }

    @Override
    public Power getPower() {
        return power;
    }

    @Override
    public SimpleEnergyStorage getEnergyStorage() {
        return energyStorage;
    }
}
