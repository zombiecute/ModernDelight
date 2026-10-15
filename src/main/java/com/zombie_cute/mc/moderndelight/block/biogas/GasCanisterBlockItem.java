package com.zombie_cute.mc.moderndelight.block.biogas;

import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.util.FluidStack;
import com.zombie_cute.mc.moderndelight.util.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
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
                tooltip.add(TextUtil.getFluidInfoText(fluidStack, GasCanisterBlockEntity.getMaxCapacity()));
            }
        }
    }
}
