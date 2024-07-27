package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.tag.ModTagKeys;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashSet;

public class ModUtil {
    public static final String SHIFT_FRONT = "bakingdelight.tooltips.shift_front";
    public static final String SHIFT_END = "bakingdelight.tooltips.shift_end";
    public static final String ALT_END = "bakingdelight.tooltips.alt_end";
    public static final String WHISK_1 = "bakingdelight.tooltips.whisk_1";
    public static final String WHISK_2 = "bakingdelight.tooltips.whisk_2";
    public static final String BUTTER_1 = "bakingdelight.tooltips.butter_1";
    public static final String BUTTER_2 = "bakingdelight.tooltips.butter_2";
    public static final String CUTTLEBONE = "bakingdelight.tooltips.cuttlebone";
    public static final String TRUFFLE = "bakingdelight.tooltips.truffle";
    public static final String FILTER_1 = "bakingdelight.tooltips.filter_1";
    public static final String FILTER_2 = "bakingdelight.tooltips.filter_2";
    public static final String KNEADING_STICK = "bakingdelight.tooltips.kneading_stick";
    public static final String SPATULA = "bakingdelight.tooltips.spatula";
    public static final String BDC_1 = "bakingdelight.tooltips.bdc_1";
    public static final String BDC_2 = "bakingdelight.tooltips.bdc_2";
    public static final String BDC_3 = "bakingdelight.tooltips.bdc_3";
    public static final String BDI_1 = "bakingdelight.tooltips.bdi_1";
    public static final String BDI_2 = "bakingdelight.tooltips.bdi_2";
    public static final String BDI_3 = "bakingdelight.tooltips.bdi_3";
    public static final String BDI_4 = "bakingdelight.tooltips.bdi_4";
    public static final String GAS_COOKING_STOVE_1 = "bakingdelight.tooltips.gas_cooking_stove_1";
    public static final String GAS_COOKING_STOVE_2 = "bakingdelight.tooltips.gas_cooking_stove_2";
    public static final String GAS_COOKING_STOVE_3 = "bakingdelight.tooltips.gas_cooking_stove_3";
    public static final String CROWBAR = "bakingdelight.tooltips.crowbar";
    public static final String PIZZA_INGREDIENTS = "bakingdelight.tooltips.pizza_ingredients";
    public static final String BAKING_TRAY_1 = "bakingdelight.tooltips.baking_tray_1";
    public static final String BAKING_TRAY_2 = "bakingdelight.tooltips.baking_tray_2";
    public static final String BAKING_TRAY_3 = "bakingdelight.tooltips.baking_tray_3";
    public static final String DEEP_FRYER_1 = "bakingdelight.tooltips.deep_fryer_1";
    public static final String DEEP_FRYER_2 = "bakingdelight.tooltips.deep_fryer_2";
    public static final String DEEP_FRYER_3 = "bakingdelight.tooltips.deep_fryer_3";
    public static final String DEEP_FRYER_4 = "bakingdelight.tooltips.deep_fryer_4";
    public static final String WOODEN_BASIN_1 = "bakingdelight.tooltips.wooden_basin_1";
    public static final String WOODEN_BASIN_2 = "bakingdelight.tooltips.wooden_basin_2";
    public static final String WOODEN_BASIN_3 = "bakingdelight.tooltips.wooden_basin_3";
    public static final String DFB = "bakingdelight.tooltips.dfb";
    public static final String ACDCC_1 = "bakingdelight.tooltips.acdcc_1";
    public static final String ACDCC_2 = "bakingdelight.tooltips.acdcc_2";
    public static final String ACDCC_3 = "bakingdelight.tooltips.acdcc_3";
    public static final String ACDCC_4 = "bakingdelight.tooltips.acdcc_4";
    public static final String ALT_ACGen = "bakingdelight.tooltips.ac_gen";
    public static final String ALT_ACCom = "bakingdelight.tooltips.ac_com";
    public static final String ALT_DCSto = "bakingdelight.tooltips.dc_sto";
    public static final String ALT_DCGen = "bakingdelight.tooltips.dc_gen";
    public static final String ALT_DCCom = "bakingdelight.tooltips.dc_com";
    public static final String BAMBOO_STEAMER_1 = "bakingdelight.tooltips.bamboo_steamer_1";
    public static final String BAMBOO_STEAMER_2 = "bakingdelight.tooltips.bamboo_steamer_2";
    public static final String BAMBOO_STEAMER_3 = "bakingdelight.tooltips.bamboo_steamer_3";
    public static final String CUISINE_TABLE_1 = "bakingdelight.tooltips.cuisine_table_1";
    public static final String CUISINE_TABLE_2 = "bakingdelight.tooltips.cuisine_table_2";
    public static final String ELECTRICIANS_DESK_1 = "bakingdelight.tooltips.electricians_desk_1";
    public static final String ELECTRICIANS_DESK_2 = "bakingdelight.tooltips.electricians_desk_2";
    public static final String ELECTRICIANS_DESK_3 = "bakingdelight.tooltips.electricians_desk_3";
    public static final String ELECTRICIANS_DESK_4 = "bakingdelight.tooltips.electricians_desk_4";
    public static final String ELECTRICIANS_DESK_5 = "bakingdelight.tooltips.electricians_desk_5";
    public static final String ELECTRICIANS_DESK_6 = "bakingdelight.tooltips.electricians_desk_6";
    public static final String ELECTRICIANS_DESK_7 = "bakingdelight.tooltips.electricians_desk_7";
    public static final String ELECTRICIANS_DESK_8 = "bakingdelight.tooltips.electricians_desk_8";
    public static final String ELECTRIC_STEAMER_1 = "bakingdelight.tooltips.electric_steamer_1";
    public static final String ELECTRIC_STEAMER_2 = "bakingdelight.tooltips.electric_steamer_2";
    public static final String FAN_BLADE_1 = "bakingdelight.tooltips.fan_blade_1";
    public static final String FAN_BLADE_2 = "bakingdelight.tooltips.fan_blade_2";
    public static final String FAN_BLADE_3 = "bakingdelight.tooltips.fan_blade_3";
    public static final String FARADAY_GENERATOR_1 = "bakingdelight.tooltips.faraday_generator_1";
    public static final String FARADAY_GENERATOR_2 = "bakingdelight.tooltips.faraday_generator_2";
    public static final String FARADAY_GENERATOR_3 = "bakingdelight.tooltips.faraday_generator_3";
    public static final String FREEZER_1 = "bakingdelight.tooltips.freezer_1";
    public static final String FREEZER_2 = "bakingdelight.tooltips.freezer_2";
    public static final String FREEZER_3 = "bakingdelight.tooltips.freezer_3";
    public static final String GLASS_BOWL_1 = "bakingdelight.tooltips.glass_bowl_1";
    public static final String GLASS_BOWL_2 = "bakingdelight.tooltips.glass_bowl_2";
    public static final String PGen_1 = "bakingdelight.tooltips.p_gen_1";
    public static final String PGen_2 = "bakingdelight.tooltips.p_gen_2";
    public static final String PGen_3 = "bakingdelight.tooltips.p_gen_3";
    public static final String STERLING_ENGINE_1 = "bakingdelight.tooltips.sterling_engine_1";
    public static final String STERLING_ENGINE_2 = "bakingdelight.tooltips.sterling_engine_2";
    public static final String STERLING_ENGINE_3 = "bakingdelight.tooltips.sterling_engine_3";
    public static final String TESLA_COIL_1 = "bakingdelight.tooltips.tesla_coil_1";
    public static final String TESLA_COIL_2 = "bakingdelight.tooltips.tesla_coil_2";
    public static final String TESLA_COIL_3 = "bakingdelight.tooltips.tesla_coil_3";
    public static final String TESLA_COIL_4 = "bakingdelight.tooltips.tesla_coil_4";
    public static final String TESLA_COIL_5 = "bakingdelight.tooltips.tesla_coil_5";
    public static final String WTC_1 = "bakingdelight.tooltips.wtc_1";
    public static final String WTC_2 = "bakingdelight.tooltips.wtc_2";
    public static final String WTC_3 = "bakingdelight.tooltips.wtc_3";
    public static final String ICE_CREAM_MAKER_1 = "bakingdelight.tooltips.ice_cream_maker_1";
    public static final String ICE_CREAM_MAKER_2 = "bakingdelight.tooltips.ice_cream_maker_2";
    public static final String ICE_CREAM_MAKER_3 = "bakingdelight.tooltips.ice_cream_maker_3";
    public static final String ICE_CREAM_MAKER_4 = "bakingdelight.tooltips.ice_cream_maker_4";
    public static final String TURNIP = "bakingdelight.tooltips.turnip";
    public static final String ELECTRIC_WHISK_MSG = "bakingdelight.msg.electric_whisk";
    public static final String ELECTRIC_WHISK_NEED_BOWL = "bakingdelight.msg.electric_whisk.need_bowl";
    public static final String ELECTRIC_WHISK_1 = "bakingdelight.tooltips.electric_whisk_1";
    public static final String ELECTRIC_WHISK_2 = "bakingdelight.tooltips.electric_whisk_2";
    public static final String ELECTRIC_WHISK_3 = "bakingdelight.tooltips.electric_whisk_3";
    public static final String CHARGING_POST_1 = "bakingdelight.tooltips.charging_post_1";
    public static final String CHARGING_POST_2 = "bakingdelight.tooltips.charging_post_2";
    public static final String CHARGING_POST_3 = "bakingdelight.tooltips.charging_post_3";
    public static final String CHARGING_POST_4 = "bakingdelight.tooltips.charging_post_4";


    public static MutableText getShiftText(boolean hasDown){
        MutableText mutableText = Text.translatable(SHIFT_FRONT).formatted(Formatting.DARK_GRAY);
        mutableText.append(Text.literal("[").formatted(Formatting.DARK_GRAY));
        if (hasDown){
            mutableText.append(Text.literal("Shift").formatted(Formatting.WHITE));
        } else {
            mutableText.append(Text.literal("Shift").formatted(Formatting.GRAY));
        }
        mutableText.append(Text.literal("]").formatted(Formatting.DARK_GRAY));
        mutableText.append(Text.translatable(SHIFT_END).formatted(Formatting.DARK_GRAY));
        return mutableText;
    }
    public static MutableText getAltText(boolean hasDown){
        MutableText mutableText = Text.translatable(SHIFT_FRONT).formatted(Formatting.DARK_GRAY);
        mutableText.append(Text.literal("[").formatted(Formatting.DARK_GRAY));
        if (hasDown){
            mutableText.append(Text.literal("Alt").formatted(Formatting.WHITE));
        } else {
            mutableText.append(Text.literal("Alt").formatted(Formatting.GRAY));
        }
        mutableText.append(Text.literal("]").formatted(Formatting.DARK_GRAY));
        mutableText.append(Text.translatable(ALT_END).formatted(Formatting.DARK_GRAY));
        return mutableText;
    }
    public static MutableText getACGen(String count){
        MutableText mutableText = Text.translatable(ALT_ACGen).formatted(Formatting.DARK_GREEN);
        mutableText.append(Text.literal(" " + count + " ").formatted(Formatting.WHITE));
        mutableText.append(Text.literal("EP/s").formatted(Formatting.GRAY));
        return mutableText;
    }
    public static MutableText getACCom(String count){
        MutableText mutableText = Text.translatable(ALT_ACCom).formatted(Formatting.DARK_GREEN);
        mutableText.append(Text.literal(" " + count + " ").formatted(Formatting.WHITE));
        mutableText.append(Text.literal("EP/s").formatted(Formatting.GRAY));
        return mutableText;
    }
    public static MutableText getDCSto(String count){
        MutableText mutableText = Text.translatable(ALT_DCSto).formatted(Formatting.DARK_GREEN);
        mutableText.append(Text.literal(" " + count + " ").formatted(Formatting.WHITE));
        mutableText.append(Text.literal("EP").formatted(Formatting.GRAY));
        return mutableText;
    }
    public static MutableText getDCGen(String count){
        MutableText mutableText = Text.translatable(ALT_DCGen).formatted(Formatting.DARK_GREEN);
        mutableText.append(Text.literal(" " + count + " ").formatted(Formatting.WHITE));
        mutableText.append(Text.literal("EP/s").formatted(Formatting.GRAY));
        return mutableText;
    }
    public static MutableText getDCCom(String count){
        MutableText mutableText = Text.translatable(ALT_DCCom).formatted(Formatting.DARK_GREEN);
        mutableText.append(Text.literal(" " + count + " ").formatted(Formatting.WHITE));
        mutableText.append(Text.literal("EP/s").formatted(Formatting.GRAY));
        return mutableText;
    }
    public static boolean isCrowbar(PlayerEntity player) {
        Item item = player.getMainHandStack().getItem();
        HashSet<Item> items = new HashSet<>();
        for (RegistryEntry<Item> registryEntry: Registries.ITEM.iterateEntries(ModTagKeys.CROWBARS)){
            items.add(registryEntry.value());
        }
        return items.contains(item);
    }
}
