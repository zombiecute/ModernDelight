package com.zombie_cute.mc.bakingdelight.util.block_util.power_util;

public class Power {
    private long power = 0;
    private long max_power;

    public Power(long maxPower) {
        max_power = maxPower;
    }
    public long getPowerValue(){
        return this.power;
    }
    public void setPowerValue(long value){
        if (value <= max_power && value >= 0){
            this.power = value;
        } else if (value > max_power) {
            this.power = this.max_power;
        }
    }
    public void addPower(long value){
        if (this.power + value <= max_power){
            this.power += value;
        } else this.power = this.max_power;
    }
    public void reducePower(long value){
        if (this.power - value >= 0){
            this.power -= value;
        } else this.power = 0;
    }
    public long getMaxPower(){
        return this.max_power;
    }
    public void resetPower(long value, long maxPower){
        this.power = value;
        this.max_power = maxPower;
    }
}
