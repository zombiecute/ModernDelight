package com.zombie_cute.mc.bakingdelight.block.custom.abstracts;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.custom.AdvanceBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.DimensionBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.IntermediateBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.SimpleBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.BatteryBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.entities.GasCanisterBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.Power;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.List;

public abstract class AbstractBatteryBlock extends BlockWithEntity {
    public AbstractBatteryBlock(Settings settings) {
        super(settings);
    }
    public static int getBatteryPower(ItemStack batteryItem){
        if (batteryItem.getItem() instanceof BlockItem blockItem){
            if (blockItem.getBlock() instanceof AbstractBatteryBlock){
                NbtCompound nbt = BlockItem.getBlockEntityNbt(batteryItem);
                if (nbt != null && nbt.contains("battery.power")) {
                    return nbt.getInt("battery.power");
                }
            }
        }
        return 0;
    }
    public static void changeBatteryPower(ItemStack batteryItem,int value,boolean isAdd){
        if (batteryItem.getItem() instanceof BlockItem blockItem){
            if (blockItem.getBlock() instanceof AbstractBatteryBlock){
                NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(batteryItem);
                if (nbtCompound != null) {
                    if (nbtCompound.contains("battery.power")) {
                        int batteryPower = nbtCompound.getInt("battery.power");
                        int maxBatteryPower = nbtCompound.getInt("battery.maxPower");
                        if (isAdd){
                            if (batteryPower + value < maxBatteryPower){
                                batteryPower += value;
                                changeBatteryNBT(nbtCompound, batteryPower, batteryItem);
                            } else if (batteryPower < maxBatteryPower){
                                batteryPower ++;
                                changeBatteryNBT(nbtCompound, batteryPower, batteryItem);
                            }
                        } else {
                            if (batteryPower - value > 0){
                                batteryPower -= value;
                                changeBatteryNBT(nbtCompound, batteryPower, batteryItem);
                            } else if (batteryPower > 0){
                                batteryPower --;
                                changeBatteryNBT(nbtCompound, batteryPower, batteryItem);
                            }
                        }
                    }
                } else {
                    NbtCompound newNBT = initNbtCompound(blockItem);
                    BlockItem.setBlockEntityNbt(batteryItem,ModBlockEntities.BATTERY_BLOCK_ENTITY,newNBT);
                    changeBatteryPower(batteryItem,value,isAdd);
                }
            }
        }
    }
    public static ItemStack changeBatteryPower(ItemStack oldBatteryItemStack, Power thisPower, int value, boolean isAddBatteryPower) {
        if (value < 0){
            Bakingdelight.LOGGER.error("Exception battery power value: \"{}\" is not a positive number!", value);
        }
        ItemStack newStack = oldBatteryItemStack.copy();
        if (oldBatteryItemStack.getItem() instanceof BlockItem blockItem){
            if (blockItem.getBlock() instanceof AbstractBatteryBlock){
                NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(oldBatteryItemStack);
                if (nbtCompound != null) {
                    if (nbtCompound.contains("battery.power")) {
                        int batteryPower = nbtCompound.getInt("battery.power");
                        int maxBatteryPower = nbtCompound.getInt("battery.maxPower");
                        if (isAddBatteryPower){
                            if (batteryPower + value < maxBatteryPower && thisPower.getPowerValue() >= value){
                                batteryPower += value;
                                thisPower.reducePower(value);
                                changeBatteryNBT(nbtCompound, batteryPower, newStack);
                            } else if (batteryPower < maxBatteryPower && thisPower.getPowerValue() >= 1){
                                batteryPower ++;
                                thisPower.reducePower(1);
                                changeBatteryNBT(nbtCompound, batteryPower, newStack);
                            }
                        } else {
                            if (batteryPower - value > 0 && thisPower.getPowerValue() + value <= thisPower.getMaxPower()){
                                batteryPower -= value;
                                thisPower.addPower(value);
                                changeBatteryNBT(nbtCompound, batteryPower, newStack);
                            } else if (batteryPower > 0 && thisPower.getPowerValue() != thisPower.getMaxPower()){
                                batteryPower --;
                                thisPower.addPower(1);
                                changeBatteryNBT(nbtCompound, batteryPower, newStack);
                            }
                        }
                    }
                } else {
                    NbtCompound newNBT = initNbtCompound(blockItem);
                    BlockItem.setBlockEntityNbt(newStack,ModBlockEntities.BATTERY_BLOCK_ENTITY,newNBT);
                    return changeBatteryPower(newStack,thisPower,value,isAddBatteryPower);
                }
            }
        }
        return newStack;
    }
    public static void addEnergy(long value, SimpleEnergyStorage energyStorage){
        if (energyStorage.amount + value < energyStorage.capacity){
            energyStorage.amount += value;
        } else {
            energyStorage.amount = energyStorage.capacity;
        }
    }
    public static void reduceEnergy(long value, SimpleEnergyStorage energyStorage){
        if (energyStorage.amount - value > 0){
            energyStorage.amount -= value;
        } else {
            energyStorage.amount = 0;
        }
    }
    public static ItemStack changeBatteryPower(ItemStack oldBatteryItemStack, SimpleEnergyStorage thisPower, int value, boolean isAddBatteryPower) {
        if (value < 0){
            Bakingdelight.LOGGER.error("Exception battery energy value: \"{}\" is not a positive number!", value);
        }
        ItemStack newStack = oldBatteryItemStack.copy();
        if (oldBatteryItemStack.getItem() instanceof BlockItem blockItem){
            if (blockItem.getBlock() instanceof AbstractBatteryBlock){
                NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(oldBatteryItemStack);
                if (nbtCompound != null) {
                    if (nbtCompound.contains("battery.power")) {
                        int batteryPower = nbtCompound.getInt("battery.power");
                        int maxBatteryPower = nbtCompound.getInt("battery.maxPower");
                        if (isAddBatteryPower){
                            if (batteryPower + value < maxBatteryPower && thisPower.amount >= value){
                                batteryPower += value;
                                reduceEnergy(value * 10L,thisPower);
                                changeBatteryNBT(nbtCompound, batteryPower, newStack);
                            } else if (batteryPower < maxBatteryPower && thisPower.amount >= 1){
                                batteryPower ++;
                                reduceEnergy(10,thisPower);
                                changeBatteryNBT(nbtCompound, batteryPower, newStack);
                            }
                        } else {
                            if (batteryPower - value > 0 && thisPower.amount + value <= thisPower.capacity){
                                batteryPower -= value;
                                addEnergy(value * 10L,thisPower);
                                changeBatteryNBT(nbtCompound, batteryPower, newStack);
                            } else if (batteryPower > 0 && thisPower.amount != thisPower.capacity){
                                batteryPower --;
                                addEnergy(10,thisPower);
                                changeBatteryNBT(nbtCompound, batteryPower, newStack);
                            }
                        }
                    }
                } else {
                    NbtCompound newNBT = initNbtCompound(blockItem);
                    BlockItem.setBlockEntityNbt(newStack,ModBlockEntities.BATTERY_BLOCK_ENTITY,newNBT);
                    return changeBatteryPower(newStack,thisPower,value,isAddBatteryPower);
                }
            }
        }
        return newStack;
    }

    private static void changeBatteryNBT(NbtCompound nbtCompound, int batteryPower, ItemStack newStack) {
        nbtCompound.putInt("battery.power", batteryPower);
        BlockItem.setBlockEntityNbt(newStack, ModBlockEntities.BATTERY_BLOCK_ENTITY, nbtCompound);
    }

    private static @NotNull NbtCompound initNbtCompound(BlockItem blockItem) {
        NbtCompound newNBT = new NbtCompound();
        newNBT.putInt("battery.power", 0);
        if (blockItem.getBlock() instanceof SimpleBatteryBlock){
            newNBT.putInt("battery.maxPower", SimpleBatteryBlock.getMaxPower());
        } else if (blockItem.getBlock() instanceof IntermediateBatteryBlock) {
            newNBT.putInt("battery.maxPower", IntermediateBatteryBlock.getMaxPower());
        } else if (blockItem.getBlock() instanceof AdvanceBatteryBlock) {
            newNBT.putInt("battery.maxPower", AdvanceBatteryBlock.getMaxPower());
        } else if (blockItem.getBlock() instanceof DimensionBatteryBlock) {
            newNBT.putInt("battery.maxPower", DimensionBatteryBlock.getMaxPower());
        } else newNBT.putInt("battery.maxPower", 5000);
        return newNBT;
    }
    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        super.onDestroyedByExplosion(world, pos, explosion);
        if (!world.isClient){
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2.0f, true, World.ExplosionSourceType.BLOCK);
        }
    }
    public static final String TOOLTIP_TEXT = "toolTipText.bakingdelight.battery_name";
    protected abstract Block getBlock();
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof BatteryBlockEntity blockEntity) {
            if (!world.isClient) {
                ItemStack itemStack = new ItemStack(getBlock());
                blockEntity.setStackNbt(itemStack);
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            if (nbtCompound.contains("battery.power")) {
                int power = nbtCompound.getInt("battery.power");
                int maxPower = nbtCompound.getInt("battery.maxPower");
                tooltip.add(Text.translatable(TOOLTIP_TEXT).formatted(Formatting.DARK_GRAY));
                tooltip.add(Text.literal(power + "/" + maxPower + "EP").formatted(Formatting.GRAY));
            }
        }
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof GasCanisterBlockEntity blockEntity){
            return blockEntity.getGasValue() *15/6000;
        } else {
            return 0;
        }
    }
}
