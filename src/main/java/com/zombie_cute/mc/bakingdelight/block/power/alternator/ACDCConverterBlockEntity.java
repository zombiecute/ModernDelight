package com.zombie_cute.mc.bakingdelight.block.power.alternator;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power.FaradayGeneratorBlock;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power.FaradayGeneratorBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.wind_power.WindTurbineControllerBlock;
import com.zombie_cute.mc.bakingdelight.block.power.alternator.wind_power.WindTurbineControllerBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.power.batteries.AbstractBatteryBlock;
import com.zombie_cute.mc.bakingdelight.screen.custom.ACDCConverterScreenHandler;
import com.zombie_cute.mc.bakingdelight.util.*;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.ACConsumer;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.ACGenerateAble;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class ACDCConverterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, PowerStorageAble, ACGenerateAble, ACConsumer {
    public ACDCConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AC_DC_CONVERTER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> (int)ACDCConverterBlockEntity.this.getPowerValue();
                    case 1 -> (int)ACDCConverterBlockEntity.this.getPower().getMaxPower();
                    case 2 -> ACDCConverterBlockEntity.this.isACMode;
                    case 3 -> ACDCConverterBlockEntity.this.workSpeed;
                    case 4 -> ACDCConverterBlockEntity.this.getMaxWorkSpeed();
                    case 5 -> ACDCConverterBlockEntity.this.efficiency;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> ACDCConverterBlockEntity.this.setPower(value);
                    case 2 -> ACDCConverterBlockEntity.this.isACMode = value;
                    case 3 -> ACDCConverterBlockEntity.this.workSpeed = value;
                    case 5 -> ACDCConverterBlockEntity.this.efficiency = value;
                }
            }

            @Override
            public int size() {
                return 6;
            }
        };
    }
    private int efficiency = 0;
    private final Power power = new Power(3000);
    private final DefaultedList<ItemStack> INV = DefaultedList.ofSize(1,ItemStack.EMPTY);
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(30000,10000,10000){
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
    private int isACMode = 0;
    private int workSpeed = 0;
    private final PropertyDelegate propertyDelegate;
    public boolean getIsACMode() {
        return isACMode != 0;
    }
    public void setACMode(boolean value){
        if (value){
            isACMode = 1;
        } else isACMode = 0;
    }
    public int getWorkSpeed() {
        return workSpeed;
    }
    public int getMaxWorkSpeed(){
        int max_speed = ModConfig.acdcConverterMaxWorkSpeed;
        if (max_speed < 1){
            return 20;
        } else return max_speed;
    }
    public void addWorkSpeed(int value) {
        if (workSpeed + value >= getMaxWorkSpeed()){
            workSpeed = getMaxWorkSpeed();
        } else workSpeed += value;
    }
    public void reduceWorkSpeed(int value){
        if (workSpeed - value <= 0){
            workSpeed = 0;
        } else workSpeed -= value;
    }
    public void tick(World world, ACDCConverterBlockEntity blockEntity, BlockState state) {
        if (world.isClient){
            return;
        }
        blockEntity.getPower().setPowerValue(energyStorage.amount / 10);
        if (world.getTime() % 20L == 0L){
            ItemStack itemStack = blockEntity.getStack(0);
            if (blockEntity.getIsACMode()){
                blockEntity.setStack(0,
                        AbstractBatteryBlock.changeBatteryPower(itemStack,blockEntity.energyStorage,
                                10L * blockEntity.workSpeed, false));
                if (blockEntity.getPowerValue() <= 0){
                    blockEntity.efficiency = 0;
                } else {
                    if (blockEntity.getPowerValue() - blockEntity.workSpeed * 10L > 0){
                        blockEntity.reduceEnergy(blockEntity.workSpeed * 10L  * 10L);
                        blockEntity.efficiency = blockEntity.workSpeed * 10;
                    } else {
                        blockEntity.reduceEnergy(10);
                        blockEntity.efficiency = 1;
                    }
                }
            } else {
                blockEntity.efficiency = 0;
                blockEntity.setStack(0,
                        AbstractBatteryBlock.changeBatteryPower(itemStack,blockEntity.energyStorage,
                                10L * blockEntity.workSpeed, true));
                Direction thisDir = state.get(ACDCConverterBlock.FACING);
                ACGenerateAble inputBlock = null;
                switch (thisDir){
                    case EAST, WEST -> {
                        if (world.getBlockEntity(pos.north()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0){
                            if (blockEntity.checkACGeneratorType(world.getBlockEntity(pos.north()),world,Direction.NORTH)){
                                inputBlock = entity;
                            }
                        } else if (world.getBlockEntity(pos.south()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0) {
                            if (blockEntity.checkACGeneratorType(world.getBlockEntity(pos.south()),world,Direction.SOUTH)){
                                inputBlock = entity;
                            }
                        }
                    }
                    case SOUTH, NORTH -> {
                        if (world.getBlockEntity(pos.west()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0){
                            if (blockEntity.checkACGeneratorType(world.getBlockEntity(pos.west()),world,Direction.WEST)){
                                inputBlock = entity;
                            }
                        } else if (world.getBlockEntity(pos.east()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0) {
                            if (blockEntity.checkACGeneratorType(world.getBlockEntity(pos.east()),world,Direction.EAST)){
                                inputBlock = entity;
                            }
                        }
                    }
                }
                if (inputBlock != null && blockEntity.workSpeed != 0){
                    blockEntity.addEnergy((long)((double)inputBlock.getEfficiency() * (1.0 - (double) blockEntity.workSpeed / ((double) getMaxWorkSpeed() * 3.0))) * 10);
                }
            }
        }
    }

    private boolean checkACGeneratorType(BlockEntity blockEntity, World world, Direction dirType){
        if (blockEntity instanceof FaradayGeneratorBlockEntity) {
            return world.getBlockState(blockEntity.getPos()).get(FaradayGeneratorBlock.FACING) == dirType.getOpposite();
        } else if (blockEntity instanceof WindTurbineControllerBlockEntity){
            return world.getBlockState(blockEntity.getPos()).get(WindTurbineControllerBlock.FACING) == dirType;
        } else if (blockEntity instanceof ACDCConverterBlockEntity){
            Direction temp = null;
            switch (dirType){
                case EAST, WEST -> temp = Direction.NORTH;
                case NORTH, SOUTH -> temp = Direction.EAST;
            }
            if (temp == null){
                return false;
            }
            return world.getBlockState(blockEntity.getPos()).get(WindTurbineControllerBlock.FACING) == temp ||
                    world.getBlockState(blockEntity.getPos()).get(WindTurbineControllerBlock.FACING) == temp.getOpposite();
        }
        return false;
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, INV);
        nbt.putLong("acdcc.power", this.getPowerValue());
        nbt.putInt("acdcc.isOpen",this.isACMode);
        nbt.putInt("acdcc.workSpeed",this.workSpeed);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, INV);
        this.setPower(nbt.getLong("acdcc.power"));
        this.workSpeed = nbt.getInt("acdcc.workSpeed");
        this.isACMode = nbt.getInt("acdcc.isOpen");
        this.energyStorage.amount = nbt.getLong("acdcc.power") * 10;
        markDirty();
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public Text getDisplayName() {
        return ModBlocks.AC_DC_CONVERTER.getName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ACDCConverterScreenHandler(syncId,playerInventory,this,propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return INV;
    }

    @Override
    public Power getPower() {
        return power;
    }

    @Override
    public SimpleEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    public long getEfficiency() {
        return this.efficiency;
    }

    @Override
    public long getConsumedValue() {
        return this.workSpeed * 10L;
    }

    @Override
    public boolean isWorking() {
        return !this.getIsACMode() && this.workSpeed != 0;
    }

    @Override
    public void energize() {
        addEnergy((long)((double)getWorkSpeed() * 10 * (1.0 - (double) getWorkSpeed() / ((double)getMaxWorkSpeed() * 3.0))) * 3);
    }
}
