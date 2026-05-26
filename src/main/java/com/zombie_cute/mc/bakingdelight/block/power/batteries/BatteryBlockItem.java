package com.zombie_cute.mc.bakingdelight.block.power.batteries;

import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.DCConsumer;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.Power;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BatteryBlockItem extends BlockItem implements DCConsumer {
    final AbstractBatteryBlock batteryBlock;
    public BatteryBlockItem(AbstractBatteryBlock block) {
        super(block, new FabricItemSettings().maxCount(1));
        this.batteryBlock = block;
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

    @Override
    public Power getPower(ItemStack stack) {
        Power power = new Power(batteryBlock.getMaxPower());
        power.setPowerValue(AbstractBatteryBlock.getBatteryPower(stack));
        return power;
    }

    @Override
    public void addPower(ItemStack stack, long value) {
        AbstractBatteryBlock.changeBatteryPower(stack,value,true);
    }

    @Override
    public void reducePower(ItemStack stack, long value) {
        AbstractBatteryBlock.changeBatteryPower(stack,value,false);
    }

}
