package com.zombie_cute.mc.bakingdelight.block.entities.utils;

public class Power {
    private int power = 0;
    private int max_power;

    public Power(int maxPower) {
        max_power = maxPower;
    }
    public int getPowerValue(){
        return this.power;
    }
    public void setPowerValue(int value){
        if (value <= max_power && value >= 0){
            this.power = value;
        } else if (value > max_power) {
            this.power = this.max_power;
        }
    }
    public void addPower(int value){
        if (this.power + value <= max_power){
            this.power += value;
        } else this.power = this.max_power;
    }
    public void reducePower(int value){
        if (this.power - value >= 0){
            this.power -= value;
        } else this.power = 0;
    }
    public int getMaxPower(){
        return this.max_power;
    }
    public void changeMaxPower(int value){
        this.max_power = value;
    }
}
