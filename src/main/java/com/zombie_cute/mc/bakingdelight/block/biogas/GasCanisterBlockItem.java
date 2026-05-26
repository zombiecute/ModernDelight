package com.zombie_cute.mc.bakingdelight.block.biogas;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GasCanisterBlockItem extends BlockItem {
    public GasCanisterBlockItem() {
        super(ModBlocks.GAS_CANISTER,new Item.Settings().maxCount(16));
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        NbtComponent nbt = stack.getOrDefault(DataComponentTypes.BLOCK_ENTITY_DATA,null);
        if (nbt != null) {
            NbtCompound nbtCompound = nbt.copyNbt();
            if (nbtCompound.contains("gas_canister.fluid_variant") && nbtCompound.contains("gas_canister.fluid_amount")){
                long fluid_amount = nbtCompound.getLong("gas_canister.fluid_amount");
                return (int) Math.min(12.0 * (float) FluidStack.convertDropletsToMb(fluid_amount) / (float) GasCanisterBlockEntity.getMaxCapacity(), 13);
            }
        }
        return 0;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        NbtComponent nbt = stack.getOrDefault(DataComponentTypes.BLOCK_ENTITY_DATA,null);
        if (nbt != null) {
            NbtCompound nbtCompound = nbt.copyNbt();
            if (nbtCompound.contains("gas_canister.fluid_variant") && nbtCompound.contains("gas_canister.fluid_amount")){
                long fluid_amount = nbtCompound.getLong("gas_canister.fluid_amount");
                float f = (float) FluidStack.convertDropletsToMb(fluid_amount) / (float) GasCanisterBlockEntity.getMaxCapacity();
                return MathHelper.packRgb(f,1-f,0);
            }
        }
        return 0x00ff00;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        NbtComponent nbt = stack.getOrDefault(DataComponentTypes.BLOCK_ENTITY_DATA,null);
        if (nbt != null) {
            NbtCompound nbtCompound = nbt.copyNbt();
            if (nbtCompound.contains("gas_canister.fluid_variant") && nbtCompound.contains("gas_canister.fluid_amount")){
                FluidStack fluidStack = FluidStack.getFluidStack(nbtCompound.getString("gas_canister.fluid_variant"), nbtCompound.getLong("gas_canister.fluid_amount"));
                MutableText mutableText = getFluidCapacity(fluidStack);
                tooltip.add(mutableText);
            }
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    @NotNull
    private static MutableText getFluidCapacity(FluidStack fluidStack) {
        String translation_key = fluidStack.getFluidVariant().getFluid().getDefaultState().getBlockState().getBlock().getTranslationKey();
        MutableText mutableText = Text.translatable(translation_key).formatted(Formatting.GRAY);
        mutableText.append(Text.literal(": ").formatted(Formatting.GRAY));
        long mb = FluidStack.convertDropletsToMb(fluidStack.getAmount());
        if (mb < GasCanisterBlockEntity.getMaxCapacity()/6){
            mutableText.append(Text.literal(String.valueOf(mb)).formatted(Formatting.GREEN));
        } else if (mb < GasCanisterBlockEntity.getMaxCapacity()/2) {
            mutableText.append(Text.literal(String.valueOf(mb)).formatted(Formatting.YELLOW));
        } else if (mb < GasCanisterBlockEntity.getMaxCapacity()* 5L /6) {
            mutableText.append(Text.literal(String.valueOf(mb)).formatted(Formatting.GOLD));
        } else {
            mutableText.append(Text.literal(String.valueOf(mb)).formatted(Formatting.RED));
        }
        mutableText.append(Text.literal("mB").formatted(Formatting.GRAY));
        return mutableText;
    }
}
