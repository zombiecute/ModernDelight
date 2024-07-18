package com.zombie_cute.mc.bakingdelight.block.entities.utils;

public interface PowerStorageAble {
    Power getPower();
    default void addPower(int value){
        getPower().addPower(value);
    }
    default void reducePower(int value){
        getPower().reducePower(value);
    }
    default int getPowerValue(){
        return getPower().getPowerValue();
    }
    default boolean isPowerEmpty() {
        return getPowerValue() == 0;
    }
    default boolean isPowerFull() {
        return getPowerValue() == getPower().getMaxPower();
    }
    default void setPower(int value){
        getPower().setPowerValue(value);
    }
    default void changeMaxPower(int value){
        getPower().changeMaxPower(value);
    }
}
