package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.Power;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.PowerStorageAble;
import com.zombie_cute.mc.bakingdelight.util.components.ModComponents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class BatteryBlockEntity extends BlockEntity implements PowerStorageAble {

    public BatteryBlockEntity(BlockPos pos, BlockState state, int maxPower) {
        super(ModBlockEntities.BATTERY_BLOCK_ENTITY, pos, state);
        this.power = new Power(maxPower);
    }
    public BatteryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BATTERY_BLOCK_ENTITY, pos, state);
        this.power = new Power(Integer.MAX_VALUE);
    }
    private final Power power;
    @Override
    public Power getPower() {
        return power;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.power.setPowerValue(nbt.getInt("power"));
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("power",this.power.getPowerValue());
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(ModComponents.POWER,this.getPowerValue());
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);
        this.power.setPowerValue(components.getOrDefault(ModComponents.POWER,0));
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
