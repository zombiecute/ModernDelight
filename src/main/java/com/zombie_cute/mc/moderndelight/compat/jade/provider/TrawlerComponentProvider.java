package com.zombie_cute.mc.moderndelight.compat.jade.provider;

import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBlockEntity;
import com.zombie_cute.mc.moderndelight.compat.jade.JadePlugin;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum TrawlerComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("Time")){
            iTooltip.add(Text.translatable("moderndelight.jade.tooltip.trawler.trawler_interval",
                    blockAccessor.getServerData().getInt("Time")));
        }
    }

    @Override
    public Identifier getUid() {
        return JadePlugin.TRAWLER;
    }

    @Override
    public void appendServerData(NbtCompound nbtCompound, BlockAccessor blockAccessor) {
        TrawlerBlockEntity trawler = (TrawlerBlockEntity) blockAccessor.getBlockEntity();
        nbtCompound.putInt("Time", (trawler.getIntervalTick() - trawler.getFishingTicker()) / 20);
    }
}
