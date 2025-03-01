package com.zombie_cute.mc.bakingdelight.util.block_util.power_util;

public interface ACConsumer {
    long getConsumedValue();
    boolean isWorking();
    void energize();
}
