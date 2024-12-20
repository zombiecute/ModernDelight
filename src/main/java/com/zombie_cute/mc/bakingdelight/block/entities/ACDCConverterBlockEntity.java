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
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ACDCConverterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory, PowerStorageAble, ACGenerateAble, ACConsumer {
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
    public static void tick(World world, BlockPos pos, BlockState state, ACDCConverterBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        if (world.getTime() % 20L == 0L){
            ItemStack itemStack = blockEntity.getStack(0);
            if (blockEntity.getIsACMode()){
                blockEntity.setStack(0,
                        AbstractBatteryBlock.changeBatteryPower(itemStack,blockEntity.getPower(),
                                10 * blockEntity.workSpeed, false));
                if (blockEntity.getPowerValue() == 0){
                    blockEntity.efficiency = 0;
                } else {
                    if (blockEntity.getPowerValue() - blockEntity.workSpeed * 10 > 0){
                        blockEntity.reducePower(blockEntity.workSpeed * 10);
                        blockEntity.efficiency = blockEntity.workSpeed * 10;
                    } else {
                        blockEntity.reducePower(1);
                        blockEntity.efficiency = 1;
                    }
                }
            } else {
                blockEntity.efficiency = 0;
                blockEntity.setStack(0,
                        AbstractBatteryBlock.changeBatteryPower(itemStack,blockEntity.getPower(),
                                10 * blockEntity.workSpeed, true));
                Direction thisDir = state.get(ACDCConverterBlock.FACING);
                ACGenerateAble inputBlock = null;
                switch (thisDir){
                    case EAST, WEST -> {
                        if (world.getBlockEntity(pos.north()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0){
                            if (blockEntity.checkCGeneratorType(world.getBlockEntity(pos.north()),world,Direction.NORTH)){
                                inputBlock = entity;
                            }
                        } else if (world.getBlockEntity(pos.south()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0) {
                            if (blockEntity.checkCGeneratorType(world.getBlockEntity(pos.south()),world,Direction.SOUTH)){
                                inputBlock = entity;
                            }
                        }
                    }
                    case SOUTH, NORTH -> {
                        if (world.getBlockEntity(pos.west()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0){
                            if (blockEntity.checkCGeneratorType(world.getBlockEntity(pos.west()),world,Direction.WEST)){
                                inputBlock = entity;
                            }
                        } else if (world.getBlockEntity(pos.east()) instanceof ACGenerateAble entity && entity.getEfficiency() != 0) {
                            if (blockEntity.checkCGeneratorType(world.getBlockEntity(pos.east()),world,Direction.EAST)){
                                inputBlock = entity;
                            }
                        }
                    }
                }
                if (inputBlock != null && blockEntity.workSpeed != 0){
                    blockEntity.addPower((int)(inputBlock.getEfficiency() * (1.0 - (float)blockEntity.workSpeed / 10.0)));
                }
            }
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
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt,registryLookup);
        Inventories.writeNbt(nbt, INV, registryLookup);
        nbt.putInt("acdcc.power", this.getPowerValue());
        nbt.putInt("acdcc.isOpen",this.isACMode);
        nbt.putInt("acdcc.workSpeed",this.workSpeed);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, INV,registryLookup);
        this.setPower(nbt.getInt("acdcc.power"));
        this.workSpeed = nbt.getInt("acdcc.workSpeed");
        this.isACMode = nbt.getInt("acdcc.isOpen");
        markDirty();
    }
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
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
        this.addPower((int)(this.workSpeed * 10 * (1.0 - (float)this.workSpeed / 20.0)) * 3);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
