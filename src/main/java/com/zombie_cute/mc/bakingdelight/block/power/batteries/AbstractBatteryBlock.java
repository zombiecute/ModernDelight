package com.zombie_cute.mc.bakingdelight.block.power.batteries;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.Power;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public abstract class AbstractBatteryBlock extends BlockWithEntity {
    public AbstractBatteryBlock(Settings settings) {
        super(settings);
    }
    public abstract long getMaxPower();
    public static long getBatteryPower(ItemStack batteryItem){
        if (batteryItem.getItem() instanceof BlockItem blockItem){
            if (blockItem.getBlock() instanceof AbstractBatteryBlock){
                NbtCompound nbt = BlockItem.getBlockEntityNbt(batteryItem);
                if (nbt != null && nbt.contains("battery.power")) {
                    return nbt.getLong("battery.power");
                }
            }
        }
        return 0;
    }
    public static void changeBatteryPower(ItemStack batteryItem,long value,boolean isAdd){
        if (batteryItem.getItem() instanceof BlockItem blockItem){
            if (blockItem.getBlock() instanceof AbstractBatteryBlock){
                NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(batteryItem);
                if (nbtCompound != null) {
                    if (nbtCompound.contains("battery.power")) {
                        long batteryPower = nbtCompound.getLong("battery.power");
                        long maxBatteryPower = nbtCompound.getLong("battery.maxPower");
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
    public static ItemStack changeBatteryPower(ItemStack oldBatteryItemStack, Power thisPower, long valueEP, boolean isAddBatteryPower) {
        if (valueEP < 0){
            ModernDelightMain.LOGGER.error("Exception battery power value: \"{}\" is not a positive number!", valueEP);
        }
        ItemStack newStack = oldBatteryItemStack.copy();
        if (oldBatteryItemStack.getItem() instanceof BlockItem blockItem){
            if (blockItem.getBlock() instanceof AbstractBatteryBlock){
                NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(oldBatteryItemStack);
                if (nbtCompound != null) {
                    if (nbtCompound.contains("battery.power")) {
                        long batteryPower = nbtCompound.getLong("battery.power");
                        long maxBatteryPower = nbtCompound.getLong("battery.maxPower");
                        if (isAddBatteryPower){
                            if (batteryPower + valueEP < maxBatteryPower && thisPower.getPowerValue() >= valueEP){
                                batteryPower += valueEP;
                                thisPower.reducePower(valueEP);
                                changeBatteryNBT(nbtCompound, batteryPower, newStack);
                            } else if (batteryPower < maxBatteryPower && thisPower.getPowerValue() >= 1){
                                batteryPower ++;
                                thisPower.reducePower(1);
                                changeBatteryNBT(nbtCompound, batteryPower, newStack);
                            }
                        } else {
                            if (batteryPower - valueEP > 0 && thisPower.getPowerValue() + valueEP <= thisPower.getMaxPower()){
                                batteryPower -= valueEP;
                                thisPower.addPower(valueEP);
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
                    return changeBatteryPower(newStack,thisPower,valueEP,isAddBatteryPower);
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
    public static ItemStack changeBatteryPower(ItemStack oldBatteryItemStack, SimpleEnergyStorage thisPower, long valueEP, boolean isAddBatteryPower) {
        if (valueEP < 0){
            ModernDelightMain.LOGGER.error("Exception battery energy value: \"{}\" is not a positive number!", valueEP);
        }
        ItemStack newStack = oldBatteryItemStack.copy();
        if (oldBatteryItemStack.getItem() instanceof BlockItem blockItem){
            if (blockItem.getBlock() instanceof AbstractBatteryBlock){
                NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(oldBatteryItemStack);
                if (nbtCompound != null) {
                    if (nbtCompound.contains("battery.power")) {
                        long batteryPower = nbtCompound.getLong("battery.power");
                        long maxBatteryPower = nbtCompound.getLong("battery.maxPower");
                        if (isAddBatteryPower){
                            if (batteryPower + valueEP <= maxBatteryPower){
                                if (thisPower.amount >= valueEP * 10){
                                    batteryPower += valueEP;
                                    reduceEnergy(valueEP * 10,thisPower);
                                    changeBatteryNBT(nbtCompound, batteryPower, newStack);
                                } else {
                                    if (thisPower.amount >= 10){
                                        batteryPower++;
                                        reduceEnergy(10,thisPower);
                                        changeBatteryNBT(nbtCompound, batteryPower, newStack);
                                    }
                                }
                            } else {
                                long consumedPower = maxBatteryPower - batteryPower;
                                if (consumedPower > 0){
                                    if (thisPower.amount >= consumedPower * 10){
                                        batteryPower += consumedPower;
                                        reduceEnergy(consumedPower * 10,thisPower);
                                        changeBatteryNBT(nbtCompound, batteryPower, newStack);
                                    } else {
                                        if (thisPower.amount >= 10){
                                            batteryPower++;
                                            reduceEnergy(10,thisPower);
                                            changeBatteryNBT(nbtCompound, batteryPower, newStack);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (thisPower.amount + valueEP * 10 <= thisPower.capacity){
                                if (batteryPower - valueEP > 0){
                                    batteryPower -= valueEP;
                                    addEnergy(valueEP * 10, thisPower);
                                    changeBatteryNBT(nbtCompound, batteryPower, newStack);
                                } else {
                                    if (batteryPower > 0){
                                        batteryPower--;
                                        addEnergy(10,thisPower);
                                        changeBatteryNBT(nbtCompound, batteryPower, newStack);
                                    }
                                }
                            } else {
                                long consumedPower = (thisPower.capacity - thisPower.amount) / 10;
                                if (consumedPower > 0){
                                    if (batteryPower >= consumedPower){
                                        batteryPower -= consumedPower;
                                        addEnergy(consumedPower * 10,thisPower);
                                        changeBatteryNBT(nbtCompound, batteryPower, newStack);
                                    } else {
                                        if (batteryPower > 0){
                                            batteryPower--;
                                            addEnergy(10,thisPower);
                                            changeBatteryNBT(nbtCompound, batteryPower, newStack);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    NbtCompound newNBT = initNbtCompound(blockItem);
                    BlockItem.setBlockEntityNbt(newStack,ModBlockEntities.BATTERY_BLOCK_ENTITY,newNBT);
                    return changeBatteryPower(newStack,thisPower,valueEP,isAddBatteryPower);
                }
            }
        }
        return newStack;
    }

    private static void changeBatteryNBT(NbtCompound nbtCompound, long batteryPower, ItemStack newStack) {
        nbtCompound.putLong("battery.power", batteryPower);
        BlockItem.setBlockEntityNbt(newStack, ModBlockEntities.BATTERY_BLOCK_ENTITY, nbtCompound);
    }

    private static @NotNull NbtCompound initNbtCompound(BlockItem blockItem) {
        NbtCompound newNBT = new NbtCompound();
        newNBT.putLong("battery.power", 0);
        AbstractBatteryBlock block = (AbstractBatteryBlock) blockItem.getBlock();
        newNBT.putLong("battery.maxPower", block.getMaxPower());
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
    protected abstract Block getBlock();

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (world.getBlockEntity(pos) instanceof BatteryBlockEntity blockEntity) {
            if (!world.isClient) {
                ItemStack itemStack = new ItemStack(getBlock());
                blockEntity.setStackNbt(itemStack);
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        if (MiscUtil.isCrowbar(player) && player.isSneaking()){
            world.breakBlock(pos,true);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof BatteryBlockEntity blockEntity){
            return (int) (blockEntity.getPower().getPowerValue() *15 / blockEntity.getPower().getMaxPower());
        } else {
            return 0;
        }
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.BATTERY_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1,pos));
    }
}
