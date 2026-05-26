package com.zombie_cute.mc.bakingdelight.block.power.batteries;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.Power;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.PowerStorageAble;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class BatteryBlockEntity extends BlockEntity implements PowerStorageAble {

    public BatteryBlockEntity(BlockPos pos, BlockState state, long maxPower) {
        super(ModBlockEntities.BATTERY_BLOCK_ENTITY, pos, state);
        this.power = new Power(maxPower);
    }
    public BatteryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BATTERY_BLOCK_ENTITY, pos, state);
        this.power = new Power(5000);
    }
    public final Power power;
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(3000000,1000,1000){
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
    @Override
    public Power getPower() {
        return power;
    }
    @Override
    public SimpleEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putLong("battery.maxPower",this.getPower().getMaxPower());
        nbt.putLong("battery.power",this.getPowerValue());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.resetPower(nbt.getInt("battery.power"),nbt.getInt("battery.maxPower"));
        this.energyStorage.amount = nbt.getLong("battery.power") * 10L;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BatteryBlockEntity b) {
        if (world.isClient){
            return;
        }
        AbstractBatteryBlock self = (AbstractBatteryBlock) world.getBlockState(pos).getBlock();
        long maxEnergy = self.getMaxPower() * 10;
        if (b.energyStorage.amount > maxEnergy){
            b.energyStorage.amount = maxEnergy;
        }
        b.power.setPowerValue(b.energyStorage.amount / 10);
    }
}
