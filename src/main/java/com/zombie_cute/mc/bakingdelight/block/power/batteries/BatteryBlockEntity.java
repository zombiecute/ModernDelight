package com.zombie_cute.mc.bakingdelight.block.power.batteries;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.Power;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.PowerStorageAble;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
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
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong("battery.maxPower",this.getPower().getMaxPower());
        nbt.putLong("battery.power",this.getPowerValue());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.resetPower(nbt.getInt("battery.power"),nbt.getInt("battery.maxPower"));
        this.energyStorage.amount = nbt.getLong("battery.power") * 10L;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public void tick(World world, BlockPos pos) {
        if (world.isClient){
            return;
        }
        AbstractBatteryBlock self = (AbstractBatteryBlock) world.getBlockState(pos).getBlock();
        long maxEnergy = self.getMaxPower() * 10;
        if (energyStorage.amount > maxEnergy){
            energyStorage.amount = maxEnergy;
        }
        this.power.setPowerValue(energyStorage.amount / 10);
    }
}
