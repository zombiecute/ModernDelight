package com.zombie_cute.mc.bakingdelight.util.block_util.power_util;

import team.reborn.energy.api.base.SimpleEnergyStorage;

public interface PowerStorageAble {
    Power getPower();
    SimpleEnergyStorage getEnergyStorage();
    default void addPower(long value){
        getPower().addPower(value);
    }
    default void reducePower(long value){
        getPower().reducePower(value);
    }
    default long getPowerValue(){
        return getPower().getPowerValue();
    }
    default boolean isPowerEmpty() {
        return getPowerValue() == 0;
    }
    default boolean isPowerFull() {
        return getPowerValue() == getPower().getMaxPower();
    }
    default void setPower(long value){
        getPower().setPowerValue(value);
    }
    default void resetPower(long value, long maxValue){
        getPower().resetPower(value,maxValue);
    }
    default void addEnergy(long value){
        if (getEnergyStorage().amount + value < getEnergyStorage().capacity){
            getEnergyStorage().amount += value;
        } else {
            getEnergyStorage().amount = getEnergyStorage().capacity;
        }
    }
    default void reduceEnergy(long value){
        if (getEnergyStorage().amount - value > 0){
            getEnergyStorage().amount -= value;
        } else {
            getEnergyStorage().amount = 0;
        }
    }
}
