package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.ACDCConverterBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.FaradayGeneratorBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.WindTurbineControllerBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.abstracts.AbstractBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.*;
import com.zombie_cute.mc.bakingdelight.screen.custom.ACDCConverterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
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
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class ACDCConverterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, PowerStorageAble, ACGenerateAble, ACConsumer {
    public ACDCConverterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AC_DC_CONVERTER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> ACDCConverterBlockEntity.this.getPowerValue();
                    case 1 -> ACDCConverterBlockEntity.this.getPower().getMaxPower();
                    case 2 -> ACDCConverterBlockEntity.this.isACMode;
                    case 3 -> ACDCConverterBlockEntity.this.workSpeed;
                    case 4 -> ACDCConverterBlockEntity.this.MAX_WORK_SPEED;
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
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(30000,1000,1000){
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
    private int isACMode = 0;
    private int workSpeed = 0;
    private final int MAX_WORK_SPEED = 10;
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
    public void addWorkSpeed(int value) {
        if (workSpeed + value >= MAX_WORK_SPEED){
            workSpeed = MAX_WORK_SPEED;
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
        blockEntity.getPower().setPowerValue((int)energyStorage.amount / 10);
        if (world.getTime() % 20L == 0L){
            ItemStack itemStack = blockEntity.getStack(0);
            if (blockEntity.getIsACMode()){
                blockEntity.setStack(0,
                        AbstractBatteryBlock.changeBatteryPower(itemStack,blockEntity.energyStorage,
                                10 * blockEntity.workSpeed, false));
                Direction thisDir = state.get(ACDCConverterBlock.FACING);
                switch (thisDir){
                    case EAST, WEST -> {
                        EnergyStorage maybeStorage1 = EnergyStorage.SIDED.find(world, pos.offset(Direction.NORTH), Direction.NORTH.getOpposite());
                        EnergyStorage maybeStorage2 = EnergyStorage.SIDED.find(world, pos.offset(Direction.SOUTH), Direction.SOUTH.getOpposite());
//                        if (maybeStorage1 != null) {
//                            extract(blockEntity, maybeStorage1);
//                        } else if (maybeStorage2 != null) {
//                            extract(blockEntity, maybeStorage2);
//                        }
                    }
                    case SOUTH, NORTH -> {
                        EnergyStorage maybeStorage1 = EnergyStorage.SIDED.find(world, pos.offset(Direction.WEST), Direction.WEST.getOpposite());
                        EnergyStorage maybeStorage2 = EnergyStorage.SIDED.find(world, pos.offset(Direction.EAST), Direction.EAST.getOpposite());
//                        if (maybeStorage1 != null) {
//                            extract(blockEntity, maybeStorage1);
//                        } else if (maybeStorage2 != null) {
//                            extract(blockEntity, maybeStorage2);
//                        }
                    }
                }
                if (blockEntity.getPowerValue() == 0){
                    blockEntity.efficiency = 0;
                } else {
                    if (blockEntity.getPowerValue() - blockEntity.workSpeed * 10 > 0){
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
                                10 * blockEntity.workSpeed, true));
                Direction thisDir = state.get(ACDCConverterBlock.FACING);
                ACGenerateAble inputBlock = null;
                switch (thisDir){
                    case EAST, WEST -> {
                        EnergyStorage maybeStorage1 = EnergyStorage.SIDED.find(world, pos.offset(Direction.NORTH), Direction.NORTH.getOpposite());
                        EnergyStorage maybeStorage2 = EnergyStorage.SIDED.find(world, pos.offset(Direction.SOUTH), Direction.SOUTH.getOpposite());
                        if (world.getBlockEntity(pos.north()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0){
                            if (blockEntity.checkCGeneratorType(world.getBlockEntity(pos.north()),world,Direction.NORTH)){
                                inputBlock = entity;
                            }
                        } else if (world.getBlockEntity(pos.south()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0) {
                            if (blockEntity.checkCGeneratorType(world.getBlockEntity(pos.south()),world,Direction.SOUTH)){
                                inputBlock = entity;
                            }
                        }
//                        if (maybeStorage1 != null) {
//                            transEnergy(blockEntity,maybeStorage1);
//                        } else if (maybeStorage2 != null) {
//                            transEnergy(blockEntity,maybeStorage2);
//                        }
                    }
                    case SOUTH, NORTH -> {
                        EnergyStorage maybeStorage1 = EnergyStorage.SIDED.find(world, pos.offset(Direction.WEST), Direction.WEST.getOpposite());
                        EnergyStorage maybeStorage2 = EnergyStorage.SIDED.find(world, pos.offset(Direction.EAST), Direction.EAST.getOpposite());
                        if (world.getBlockEntity(pos.west()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0){
                            if (blockEntity.checkCGeneratorType(world.getBlockEntity(pos.west()),world,Direction.WEST)){
                                inputBlock = entity;
                            }
                        } else if (world.getBlockEntity(pos.east()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0) {
                            if (blockEntity.checkCGeneratorType(world.getBlockEntity(pos.east()),world,Direction.EAST)){
                                inputBlock = entity;
                            }
                        }
//                        if (maybeStorage1 != null) {
//                            transEnergy(blockEntity, maybeStorage1);
//                        } else if (maybeStorage2 != null) {
//                            transEnergy(blockEntity, maybeStorage2);
//                        }
                    }
                }
                if (inputBlock != null && blockEntity.workSpeed != 0){
                    blockEntity.addEnergy((long)(inputBlock.getEfficiency() * (1.0 - (float)blockEntity.workSpeed / 10.0)  * 10L));
                }
            }
        }
    }

    private boolean extract(ACDCConverterBlockEntity blockEntity,EnergyStorage maybeStorage) {
        try (Transaction transaction = Transaction.openOuter()) {
            long amountExtracted = maybeStorage.extract(workSpeed * 10L, transaction);
            if (amountExtracted == workSpeed * 10L) {
                transaction.commit();
                blockEntity.addEnergy((int) (workSpeed * 10 * (1.0 - (float) blockEntity.workSpeed / 10.0))  * 10L);
                return true;
            } else return false;
        }
    }

    public boolean transEnergy(ACDCConverterBlockEntity blockEntity, EnergyStorage maybeStorage) {
        try (Transaction transaction = Transaction.openOuter()) {
            long insert = (long) (workSpeed * 10 * (1.0 - (float) blockEntity.workSpeed / 10.0));
            if (blockEntity.energyStorage.amount > workSpeed * 10L){
                long amount = maybeStorage.insert(insert, transaction);
                if (amount == insert) {
                    transaction.commit();
                    reduceEnergy(workSpeed * 10L  * 10L);
                    return true;
                }
            } else if (blockEntity.energyStorage.amount > 0) {
                long amount = maybeStorage.insert(1, transaction);
                if (amount == 1) {
                    transaction.commit();
                    reduceEnergy(10);
                    return true;
                }
            }
            return false;
        }
    }

    private boolean checkCGeneratorType(BlockEntity blockEntity, World world, Direction dirType){
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
        nbt.putInt("acdcc.power", this.getPowerValue());
        nbt.putLong("acdcc.energy",this.energyStorage.amount);
        nbt.putInt("acdcc.isOpen",this.isACMode);
        nbt.putInt("acdcc.workSpeed",this.workSpeed);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, INV);
        this.setPower(nbt.getInt("acdcc.power"));
        this.workSpeed = nbt.getInt("acdcc.workSpeed");
        this.isACMode = nbt.getInt("acdcc.isOpen");
        this.energyStorage.amount = nbt.getLong("acdcc.energy");
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
    public int getEfficiency() {
        return this.efficiency;
    }

    @Override
    public int getConsumedValue() {
        return this.workSpeed * 10;
    }

    @Override
    public boolean isWorking() {
        return !this.getIsACMode() && this.workSpeed != 0;
    }

    @Override
    public void energize() {
        addEnergy((long)(this.workSpeed * 10 * (1.0 - (float)this.workSpeed / 20.0)) * 3);
    }
    public void addEnergy(long value){
        if (this.energyStorage.amount + value < this.energyStorage.capacity){
            this.energyStorage.amount += value;
        } else {
            this.energyStorage.amount = this.energyStorage.capacity;
        }
    }
    public void reduceEnergy(long value){
        if (this.energyStorage.amount - value > 0){
            this.energyStorage.amount -= value;
        } else {
            this.energyStorage.amount = 0;
        }
    }
}
