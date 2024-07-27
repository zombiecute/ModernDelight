package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.Power;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.PowerStorageAble;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class BatteryBlockEntity extends BlockEntity implements PowerStorageAble {

    public BatteryBlockEntity(BlockPos pos, BlockState state, int maxPower) {
        super(ModBlockEntities.BATTERY_BLOCK_ENTITY, pos, state);
        this.power = new Power(maxPower);
    }
    public BatteryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BATTERY_BLOCK_ENTITY, pos, state);
        this.power = new Power(5000);
    }
    private final Power power;
    @Override
    public Power getPower() {
        return power;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("battery.maxPower",this.getPower().getMaxPower());
        nbt.putInt("battery.power",this.getPowerValue());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.setCachedPower(nbt.getInt("battery.power"),nbt.getInt("battery.maxPower"));
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
