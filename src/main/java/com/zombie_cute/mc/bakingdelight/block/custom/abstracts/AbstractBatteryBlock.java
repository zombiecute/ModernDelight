package com.zombie_cute.mc.bakingdelight.block.custom.abstracts;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.custom.AdvanceBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.DimensionBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.IntermediateBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.SimpleBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.BatteryBlockEntity;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.Power;
import com.zombie_cute.mc.bakingdelight.util.components.ModComponents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.List;

public abstract class AbstractBatteryBlock extends BlockWithEntity {
    public AbstractBatteryBlock(Settings settings) {
        super(settings);
    }

    public static int getBatteryPower(ItemStack batteryItem){
        return batteryItem.getOrDefault(ModComponents.POWER,0);
    }
    private static int getMaxPower(BlockItem blockItem) {
        int maxPower;
        if (blockItem.getBlock() instanceof SimpleBatteryBlock){
            maxPower = SimpleBatteryBlock.getMaxPower();
        } else if (blockItem.getBlock() instanceof IntermediateBatteryBlock){
            maxPower = IntermediateBatteryBlock.getMaxPower();
        } else if (blockItem.getBlock() instanceof AdvanceBatteryBlock){
            maxPower = AdvanceBatteryBlock.getMaxPower();
        } else if (blockItem.getBlock() instanceof DimensionBatteryBlock){
            maxPower = DimensionBatteryBlock.getMaxPower();
        } else maxPower = 0;
        return maxPower;
    }
    public static void changeBatteryPower(ItemStack batteryItem,int value,boolean isAdd){
        if (batteryItem.getItem() instanceof BlockItem blockItem){
            int maxPower = getMaxPower(blockItem);
            int power = batteryItem.getOrDefault(ModComponents.POWER,0);
            if (isAdd){
                if (power + value > maxPower){
                    batteryItem.set(ModComponents.POWER, maxPower);
                } else {
                    batteryItem.set(ModComponents.POWER, power + value);
                }
            } else {
                if (power - value < 0){
                    batteryItem.set(ModComponents.POWER, 0);
                } else {
                    batteryItem.set(ModComponents.POWER, power - value);
                }
            }
        }
    }

    public static ItemStack changeBatteryPower(ItemStack batteryItemStack, Power thisPower, int value, boolean isAddBatteryPower) {
        if (value < 0){
            Bakingdelight.LOGGER.error("Exception battery power value: \"{}\" is not a positive number!", value);
            return batteryItemStack;
        }
        ItemStack newStack = batteryItemStack.copy();
        if (batteryItemStack.getItem() instanceof BlockItem blockItem){
            int batteryPower = batteryItemStack.getOrDefault(ModComponents.POWER,0);
            int maxBatteryPower = getMaxPower(blockItem);
            if (isAddBatteryPower){
                if (batteryPower + value < maxBatteryPower && thisPower.getPowerValue() >= value){
                    batteryPower += value;
                    thisPower.reducePower(value);
                    newStack.set(ModComponents.POWER,batteryPower);
                } else if (batteryPower < maxBatteryPower && thisPower.getPowerValue() >= 1){
                    batteryPower ++;
                    thisPower.reducePower(1);
                    newStack.set(ModComponents.POWER,batteryPower);
                }
            } else {
                if (batteryPower - value > 0 && thisPower.getPowerValue() + value <= thisPower.getMaxPower()){
                    batteryPower -= value;
                    thisPower.addPower(value);
                    newStack.set(ModComponents.POWER,batteryPower);
                } else if (batteryPower > 0 && thisPower.getPowerValue() != thisPower.getMaxPower()){
                    batteryPower --;
                    thisPower.addPower(1);
                    newStack.set(ModComponents.POWER,batteryPower);
                }
            }
        }
        return newStack;
    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        super.onDestroyedByExplosion(world, pos, explosion);
        if (!world.isClient){
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2.0f, true, World.ExplosionSourceType.BLOCK);
        }
    }
    public static final String TOOLTIP_TEXT = "toolTipText.bakingdelight.battery_name";
    protected abstract Block getBlock();
    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof BatteryBlockEntity blockEntity) {
            if (!world.isClient) {
                ItemStack itemStack = new ItemStack(getBlock());
                int power = blockEntity.getPowerValue();
                itemStack.set(ModComponents.POWER,power);
                ItemScatterer.spawn(world,pos.getX(),pos.getY(), pos.getZ(),itemStack);
            }
        }
        super.onBreak(world, pos, state, player);
        return state;
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack,context,tooltip,options);
        tooltip.add(Text.translatable(TOOLTIP_TEXT).formatted(Formatting.DARK_GRAY));
        int power = stack.getOrDefault(ModComponents.POWER,0);
        int maxPower;
        if (stack.getItem() instanceof BlockItem blockItem){
            maxPower =  getMaxPower(blockItem);
        } else maxPower = 5000;
        tooltip.add(Text.literal(power + "/" + maxPower + "EP").formatted(Formatting.GRAY));
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof BatteryBlockEntity blockEntity){
            return blockEntity.getPowerValue() *15/ blockEntity.getPower().getMaxPower();
        } else {
            return 0;
        }
    }
}
