package com.zombie_cute.mc.bakingdelight.block.power.batteries;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.DCConsumer;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.Power;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class BatteryBlockItem extends BlockItem implements DCConsumer {
    final AbstractBatteryBlock batteryBlock;
    public BatteryBlockItem(AbstractBatteryBlock block) {
        super(block, new Item.Settings().maxCount(1));
        this.batteryBlock = block;
    }
    public static final String TOOLTIP_TEXT = "toolTipText."+ ModernDelightMain.MOD_ID +".battery_name";

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        NbtComponent nbt = stack.getOrDefault(DataComponentTypes.BLOCK_ENTITY_DATA,null);
        if (nbt != null) {
            NbtCompound nbtCompound = nbt.copyNbt();
            if (nbtCompound.contains("battery.power")) {
                long power = nbtCompound.getLong("battery.power");
                long maxPower = nbtCompound.getLong("battery.maxPower");
                tooltip.add(Text.translatable(TOOLTIP_TEXT).formatted(Formatting.DARK_GRAY));
                tooltip.add(Text.literal(power + "/" + maxPower + "EP").formatted(Formatting.GRAY));
            }
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        NbtComponent nbt = stack.getOrDefault(DataComponentTypes.BLOCK_ENTITY_DATA,null);
        if (nbt != null) {
            NbtCompound nbtCompound = nbt.copyNbt();
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
