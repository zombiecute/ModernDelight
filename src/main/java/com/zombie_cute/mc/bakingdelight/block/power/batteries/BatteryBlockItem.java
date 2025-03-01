package com.zombie_cute.mc.bakingdelight.block.power.batteries;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BatteryBlockItem extends BlockItem {
    public BatteryBlockItem(Block block) {
        super(block, new FabricItemSettings().maxCount(1));
    }
    public static final String TOOLTIP_TEXT = "toolTipText.bakingdelight.battery_name";
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext options) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            if (nbtCompound.contains("battery.power")) {
                long power = nbtCompound.getLong("battery.power");
                long maxPower = nbtCompound.getLong("battery.maxPower");
                tooltip.add(Text.translatable(TOOLTIP_TEXT).formatted(Formatting.DARK_GRAY));
                tooltip.add(Text.literal(power + "/" + maxPower + "EP").formatted(Formatting.GRAY));
            }
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            if (nbtCompound.contains("battery.power")) {
                long power = nbtCompound.getLong("battery.power");
                long maxPower = nbtCompound.getLong("battery.maxPower");
                return (int) Math.min(12 * power / maxPower, 13);
            }
        }
        return 0;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xffffff;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }
}
