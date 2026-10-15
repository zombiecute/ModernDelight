package com.zombie_cute.mc.moderndelight.compat.jade.provider;

import com.zombie_cute.mc.moderndelight.block.kitchenware.juice_extractor.JuiceExtractorBlockEntity;
import com.zombie_cute.mc.moderndelight.compat.jade.JadePlugin;
import com.zombie_cute.mc.moderndelight.util.block_util.power_util.ACGenerateAble;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum JuiceExtractorComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("IsPowered") &&  blockAccessor.getServerData().getBoolean("IsPowered")) {
            iTooltip.add(Text.translatable("moderndelight.jade.tooltip.juice_extractor.powered").formatted(Formatting.GREEN));
        } else {
            iTooltip.add(Text.translatable("moderndelight.jade.tooltip.juice_extractor.not_powered").formatted(Formatting.RED));
        }
        if (blockAccessor.getServerData().contains("Time")) {
            int time = blockAccessor.getServerData().getInt("Time");
            if (time > 0) {
                iTooltip.add(Text.translatable("moderndelight.jade.tooltip.juice_extractor.time",time));
            }
        }
    }

    @Override
    public Identifier getUid() {
        return JadePlugin.JUICE_EXTRACTOR;
    }

    @Override
    public void appendServerData(NbtCompound nbtCompound, BlockAccessor blockAccessor) {
        JuiceExtractorBlockEntity juicer = (JuiceExtractorBlockEntity) blockAccessor.getBlockEntity();
        nbtCompound.putInt("Time", juicer.getProgress() / 20);
        nbtCompound.putBoolean("IsPowered", juicer.getCachedPower() > 0);
    }
}
