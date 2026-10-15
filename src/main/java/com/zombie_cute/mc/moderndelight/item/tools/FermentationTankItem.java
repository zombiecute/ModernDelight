package com.zombie_cute.mc.moderndelight.item.tools;

import com.zombie_cute.mc.moderndelight.item.ModItems;
import com.zombie_cute.mc.moderndelight.util.FluidStack;
import com.zombie_cute.mc.moderndelight.util.MiscUtil;
import com.zombie_cute.mc.moderndelight.util.TextUtil;
import com.zombie_cute.mc.moderndelight.util.enums.BacteriaType;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FermentationTankItem extends Item {
    public FermentationTankItem() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        setFluidStack(stack, new FluidStack(FluidVariant.blank(),0));
        resetBacteria(stack);
        super.onCraft(stack, world, player);
    }

    public static FluidStack getFluidStack(ItemStack stack) {
        if (stack.isOf(ModItems.FERMENTATION_TANK)){
            NbtCompound nbt = stack.getNbt();
            if (nbt != null && nbt.contains("fermentation_fluid") && nbt.contains("fermentation_fluid_amount")){
                return new FluidStack(
                        FluidVariant.of(MiscUtil.getFluidFromString(nbt.getString("fermentation_fluid"))),
                        nbt.getLong("fermentation_fluid_amount"));
            }
        }
        return new FluidStack(FluidVariant.blank(),0);
    }
    public static long getFluidAmount(ItemStack stack) {
        if (stack.isOf(ModItems.FERMENTATION_TANK)){
            NbtCompound nbt = stack.getNbt();
            if (nbt != null && nbt.contains("fermentation_fluid_amount")){
                return nbt.getLong("fermentation_fluid_amount");
            }
        }
        return 0;
    }
    public static void setFluidStack(ItemStack stack, FluidStack fluidStack) {
        if (stack.isOf(ModItems.FERMENTATION_TANK)){
            NbtCompound nbt = stack.getOrCreateNbt();
            nbt.putString("fermentation_fluid", Registries.FLUID.getId(fluidStack.fluidVariant.getFluid()).toString());
            nbt.putLong("fermentation_fluid_amount", fluidStack.getAmount());
        }
    }

    public static Map<BacteriaType,Integer> getBacteriaMap(ItemStack stack) {
        if (stack.isOf(ModItems.FERMENTATION_TANK)){
            NbtCompound nbt = stack.getNbt();
            if (nbt != null && nbt.contains("bacteria_cfu")){
                int[] cfu = nbt.getIntArray("bacteria_cfu");
                Map<BacteriaType,Integer> map = new HashMap<>();
                for (int i = 0; i < BacteriaType.values().length; i++){
                    BacteriaType b = BacteriaType.getBacteriaFromRawID(i);
                    try {
                        map.put(b, cfu[i]);
                    } catch (IndexOutOfBoundsException e) {
                        map.put(b, 0);
                    }
                }
                return map;
            }
        }
        return null;
    }
    public static void setBacteria(ItemStack stack, int[] bacteriaCFU) {
        if (stack.isOf(ModItems.FERMENTATION_TANK)){
            NbtCompound nbt = stack.getOrCreateNbt();
            nbt.putIntArray("bacteria_cfu", bacteriaCFU);
        }
    }
    public static void setBacteria(ItemStack stack, BacteriaType bacteriaType, int cfu) {
        if (stack.isOf(ModItems.FERMENTATION_TANK)){
            NbtCompound nbt = stack.getOrCreateNbt();
            int rawID = bacteriaType.getRawID();
            if (nbt.contains("bacteria_cfu")){
                int[] cfus1 = nbt.getIntArray("bacteria_cfu");
                if (cfus1.length != BacteriaType.values().length){
                    int[] cfus2 = new int[BacteriaType.values().length];
                    System.arraycopy(cfus1, 0, cfus2, 0, BacteriaType.values().length);
                    cfus2[rawID] = cfu;
                    nbt.putIntArray("bacteria_cfu", cfus2);
                } else  {
                    cfus1[rawID] = cfu;
                    nbt.putIntArray("bacteria_cfu", cfus1);
                }
            } else {
                int[] cfus = new int[BacteriaType.values().length];
                cfus[rawID] = cfu;
                nbt.putIntArray("bacteria_cfu", cfus);
            }
        }
    }
    public static void resetBacteria(ItemStack stack) {
        if (stack.isOf(ModItems.FERMENTATION_TANK)){
            NbtCompound nbt = stack.getOrCreateNbt();
            int[] cfu = new int[BacteriaType.values().length];
            nbt.putIntArray("bacteria_cfu", cfu);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(TextUtil.getCtrlText(false));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.FERMENTATION_TANK)));
        } else if (Screen.hasControlDown()) {
            tooltip.add(TextUtil.getShiftText(false));
            tooltip.add(TextUtil.getCtrlText(true));
            tooltip.add(Text.literal(" "));
            FluidStack fluidStack = getFluidStack(stack);
            Map<BacteriaType,Integer> bacteria = getBacteriaMap(stack);
            tooltip.add(TextUtil.getFluidInfoText(fluidStack, 5000));
            if (bacteria != null) {
                for (Map.Entry<BacteriaType,Integer> entry : bacteria.entrySet()) {
                    BacteriaType b = entry.getKey();
                    int cfu = entry.getValue();
                    MutableText text = Text.translatable(b.getTranslationKey()).formatted(Formatting.DARK_GRAY);
                    text.append(Text.literal(": ").formatted(Formatting.DARK_GRAY));
                    text.append(Text.literal(cfu + " CFU").formatted(Formatting.GRAY));
                    tooltip.add(text);
                }
            }
        } else {
            tooltip.add(TextUtil.getShiftText(false));
            tooltip.add(TextUtil.getCtrlText(false));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (getFluidAmount(stack) == 0){
            return 0;
        } else {
            return (int) Math.min(1 + 12.0 * getFluidAmount(stack) / 81000L * 5L, 13);
        }
    }
    @Override
    public int getItemBarColor(ItemStack stack) {
        return MathHelper.packRgb(0,0,0);
    }
}
