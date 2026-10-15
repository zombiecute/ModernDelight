package com.zombie_cute.mc.moderndelight.compat.jade.provider;

import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.SterlingEngineBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.SterlingEngineBlockEntity;
import com.zombie_cute.mc.moderndelight.compat.jade.JadePlugin;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum SterlingEngineComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("HasSponge") && blockAccessor.getServerData().getBoolean("HasSponge")){
            iTooltip.add(Text.translatable("moderndelight.jade.tooltip.sterling_engine.has_sponge").formatted(Formatting.GREEN));
        } else {
            iTooltip.add(Text.translatable("moderndelight.jade.tooltip.sterling_engine.no_sponge").formatted(Formatting.RED));
        }
        if (blockAccessor.getServerData().contains("IsWorking") && blockAccessor.getServerData().getBoolean("IsWorking")){
            iTooltip.add(Text.translatable("moderndelight.jade.tooltip.sterling_engine.working").formatted(Formatting.GREEN));
        } else {
            iTooltip.add(Text.translatable("moderndelight.jade.tooltip.sterling_engine.not_working").formatted(Formatting.RED));
        }
    }

    @Override
    public Identifier getUid() {
        return JadePlugin.STERLING_ENGINE;
    }

    @Override
    public void appendServerData(NbtCompound nbtCompound, BlockAccessor blockAccessor) {
        SterlingEngineBlockEntity engine = (SterlingEngineBlockEntity) blockAccessor.getBlockEntity();
        BlockState state = engine.getCachedState();
        nbtCompound.putBoolean("HasSponge", state.get(SterlingEngineBlock.SMALL_SOUND));
        nbtCompound.putBoolean("IsWorking", state.get(SterlingEngineBlock.IS_WORKING));
    }
}
