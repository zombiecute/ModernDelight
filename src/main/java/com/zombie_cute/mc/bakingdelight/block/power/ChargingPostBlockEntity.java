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
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class ChargingPostBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory, PowerStorageAble {
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
    public void tick(World world) {
        if (world.isClient){
            return;
        }
        this.power.setPowerValue(energyStorage.amount / 10);
        if (world.getTime() % 20L == 0){
            long battery1;
            long battery2;
            battery1 = AbstractBatteryBlock.getBatteryPower(getStack(0));
            battery2 = AbstractBatteryBlock.getBatteryPower(getStack(1));
            if (energyStorage.amount < energyStorage.capacity){
                if (battery1 >= 100){
                    AbstractBatteryBlock.changeBatteryPower(getStack(0),energyStorage,100,false);
                } else if (battery1 >= 10){
                    AbstractBatteryBlock.changeBatteryPower(getStack(0),energyStorage,10,false);
                } else if (battery1 >= 1){
                    AbstractBatteryBlock.changeBatteryPower(getStack(0),energyStorage,1,false);
                } else if (battery2 >= 100){
                    AbstractBatteryBlock.changeBatteryPower(getStack(1),energyStorage,100,false);
                } else if (battery2 >= 10){
                    AbstractBatteryBlock.changeBatteryPower(getStack(1),energyStorage,10,false);
                } else if (battery2 >= 1){
                    AbstractBatteryBlock.changeBatteryPower(getStack(1),energyStorage,1,false);
                }
            }
            if (getStack(2).getItem() instanceof DCConsumer consumer){
                Power p = consumer.getPower(getStack(2));
                long need = p.getMaxPower() - p.getPowerValue();
                if (need >= 30){
                    if (energyStorage.amount >= 30 * 10){
                        reduceEnergy(30 * 10);
                        consumer.addPower(getStack(2), (long) (30 * EFFICIENCY));
                        isWorking = 1;
                    } else if (energyStorage.amount > 10) {
                        reduceEnergy(10);
                        consumer.addPower(getStack(2),1);
                        isWorking = 1;
                    } else isWorking = 0;
                } else if (need > 0) {
                    if (energyStorage.amount > 10) {
                        reduceEnergy(10);
                        consumer.addPower(getStack(2),1);
                        isWorking = 1;
                    } else isWorking = 0;
                } else isWorking = 0;
            } else isWorking = 0;
        }
        if (isWorking != 0){
            if (!dir){
                ticker++;
            } else {
                ticker--;
            }
            if (ticker == 0){
                dir = false;
            }
            if (ticker == 10){
                dir = true;
            }
        } else {
            ticker = 0;
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        createNbt();
        return super.toInitialChunkDataNbt();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,inventory);
        nbt.putLong("charging_post.power",this.getPowerValue());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,inventory);
        this.energyStorage.amount = nbt.getLong("charging_post.power") * 10;
        markDirty();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
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
