package com.zombie_cute.mc.bakingdelight.block.biogas;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GasCanisterBlockItem extends BlockItem {
    public GasCanisterBlockItem() {
        super(ModBlocks.GAS_CANISTER,new FabricItemSettings().maxCount(16));
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            if (nbtCompound.contains("gas_canister.fluid_variant") && nbtCompound.contains("gas_canister.fluid_amount")){
                long fluid_amount = nbtCompound.getLong("gas_canister.fluid_amount");
                return (int) Math.min(12.0 * (float) FluidStack.convertDropletsToMb(fluid_amount) / (float) GasCanisterBlockEntity.getMaxCapacity(), 13);
            }
        }
        return 0;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            if (nbtCompound.contains("gas_canister.fluid_variant") && nbtCompound.contains("gas_canister.fluid_amount")){
                long fluid_amount = nbtCompound.getLong("gas_canister.fluid_amount");
                float f = (float) FluidStack.convertDropletsToMb(fluid_amount) / (float) GasCanisterBlockEntity.getMaxCapacity();
                return MathHelper.packRgb(f,1-f,0);
            }
        }
        return 0x00ff00;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            if (nbtCompound.contains("gas_canister.fluid_variant") && nbtCompound.contains("gas_canister.fluid_amount")){
                FluidVariant fluidVariant = FluidVariant.fromNbt((NbtCompound) nbtCompound.get("gas_canister.fluid_variant"));
                long fluid_amount = nbtCompound.getLong("gas_canister.fluid_amount");
                FluidStack fluidStack = new FluidStack(fluidVariant,fluid_amount);
                MutableText mutableText = getFluidCapacity(fluidStack);
                tooltip.add(mutableText);
            }
        }
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
