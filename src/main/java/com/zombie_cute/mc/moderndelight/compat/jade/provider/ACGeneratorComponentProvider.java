package com.zombie_cute.mc.moderndelight.compat.jade.provider;

import com.zombie_cute.mc.moderndelight.compat.jade.JadePlugin;
import com.zombie_cute.mc.moderndelight.util.block_util.power_util.ACGenerateAble;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum ACGeneratorComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("Power")){
            iTooltip.add(Text.translatable("moderndelight.jade.tooltip.ac_generator.power",
                    blockAccessor.getServerData().getInt("Power")));
        }
    }

    @Override
    public Identifier getUid() {
        return JadePlugin.AC_GENERATOR;
    }

    @Override
    public void appendServerData(NbtCompound nbtCompound, BlockAccessor blockAccessor) {
        ACGenerateAble acGenerator = (ACGenerateAble) blockAccessor.getBlockEntity();
        nbtCompound.putLong("Power", acGenerator.getEfficiency());
    }
}
