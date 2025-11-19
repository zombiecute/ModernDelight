package com.zombie_cute.mc.bakingdelight.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {
    public static final int MAX_TOOLTIP_WIDTH = 250;

    public static final String SHIFT_FRONT = "bakingdelight.tooltips.shift_front";
    public static final String SHIFT_END = "bakingdelight.tooltips.shift_end";
    public static final String ALT_END = "bakingdelight.tooltips.alt_end";
    public static final String WHISK = "bakingdelight.tooltips.whisk";
    public static final String BUTTER = "bakingdelight.tooltips.butter";
    public static final String CUTTLEBONE = "bakingdelight.tooltips.cuttlebone";
    public static final String TRUFFLE = "bakingdelight.tooltips.truffle";
    public static final String FILTER = "bakingdelight.tooltips.filter";
    public static final String KNEADING_STICK = "bakingdelight.tooltips.kneading_stick";
    public static final String SPATULA = "bakingdelight.tooltips.spatula";
    public static final String BDC = "bakingdelight.tooltips.bdc";
    public static final String BDI = "bakingdelight.tooltips.bdi";
    public static final String GAS_COOKING_STOVE = "bakingdelight.tooltips.gas_cooking_stove";
    public static final String CROWBAR = "bakingdelight.tooltips.crowbar";
    public static final String INGREDIENTS = "bakingdelight.tooltips.ingredients";
    public static final String BAKING_TRAY = "bakingdelight.tooltips.baking_tray";
    public static final String DEEP_FRYER = "bakingdelight.tooltips.deep_fryer";
    public static final String WOODEN_BASIN = "bakingdelight.tooltips.wooden_basin";
    public static final String HOLDER = "bakingdelight.tooltips.holder";
    public static final String ACDCC = "bakingdelight.tooltips.acdcc";
    public static final String ALT_ACGen = "bakingdelight.tooltips.ac_gen";
    public static final String ALT_ACCom = "bakingdelight.tooltips.ac_com";
    public static final String ALT_DCSto = "bakingdelight.tooltips.dc_sto";
    public static final String ALT_DCGen = "bakingdelight.tooltips.dc_gen";
    public static final String ALT_DCCom = "bakingdelight.tooltips.dc_com";
    public static final String BAMBOO_STEAMER = "bakingdelight.tooltips.bamboo_steamer";
    public static final String CUISINE_TABLE = "bakingdelight.tooltips.cuisine_table";
    public static final String ELECTRICIANS_DESK = "bakingdelight.tooltips.electricians_desk";
    public static final String ELECTRIC_STEAMER = "bakingdelight.tooltips.electric_steamer";
    public static final String FAN_BLADE = "bakingdelight.tooltips.fan_blade";
    public static final String FARADAY_GENERATOR = "bakingdelight.tooltips.faraday_generator";
    public static final String FREEZER = "bakingdelight.tooltips.freezer";
    public static final String GLASS_BOWL = "bakingdelight.tooltips.glass_bowl";
    public static final String PGEN = "bakingdelight.tooltips.p_gen";
    public static final String STERLING_ENGINE = "bakingdelight.tooltips.sterling_engine";
    public static final String TESLA_COIL = "bakingdelight.tooltips.tesla_coil";
    public static final String WTC = "bakingdelight.tooltips.wtc";
    public static final String ICE_CREAM_MAKER = "bakingdelight.tooltips.ice_cream_maker";
    public static final String TURNIP = "bakingdelight.tooltips.turnip";
    public static final String ELECTRIC_WHISK_MSG = "bakingdelight.msg.electric_whisk";
    public static final String ELECTRIC_WHISK_NEED_BOWL = "bakingdelight.msg.electric_whisk.need_bowl";
    public static final String ELECTRIC_WHISK = "bakingdelight.tooltips.electric_whisk";
    public static final String CHARGING_POST = "bakingdelight.tooltips.charging_post";
    public static final String JUICE_EXTRACTOR = "bakingdelight.tooltips.juice_extractor";
    public static final String POT_HAS_QUICKLIME = "bakingdelight.tooltips.pot_has_quicklime";
    public static final String POT_MISS_QUICKLIME = "bakingdelight.tooltips.pot_miss_quicklime";
    public static final String POT_HAS_WATER = "bakingdelight.tooltips.pot_has_water";
    public static final String POT_MISS_WATER = "bakingdelight.tooltips.pot_miss_water";
    public static final String NOODLE_UNHEALTHY = "bakingdelight.tooltips.noodle_unhealthy";
    public static final String ANYTHING = "bakingdelight.tooltips.anything";
    public static final String NEED_FOOD = "bakingdelight.tooltips.need_food";
    public static final String SEASONING_TIP = "bakingdelight.tooltips.condiment_tip";
    public static final String FAILED_SEASONING = "bakingdelight.tooltips.failed_seasoning";
    public static final String SEASONING_ADDED = "bakingdelight.tooltips.seasoning_added";
    public static final String CAN_PLACE = "bakingdelight.tooltips.can_place";
    public static final String PUN = "bakingdelight.tooltips.prohibit_unlimited_nesting";
    public static final String WOODEN_PLATE = "bakingdelight.tooltips.wooden_plate";
    public static List<Text> generateToolTip(Text text, Style style){
        List<Text> result = new ArrayList<>();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return result;
        TextRenderer textRenderer = client.textRenderer;
        List<OrderedText> wrappedLines = textRenderer.wrapLines(text, MAX_TOOLTIP_WIDTH);
        for (OrderedText wrappedLine : wrappedLines) {
            MutableText lineText = Text.empty();
            wrappedLine.accept((index, charStyle, codePoint) -> {
                MutableText charText = Text.literal(new String(Character.toChars(codePoint)));
                charText.setStyle(charStyle.withParent(style));
                lineText.append(charText);
                return true;
            });
            result.add(lineText);
        }
        return result;
    }
    public static List<Text> generateToolTip(Text text, int rgb){
        return generateToolTip(text,Style.EMPTY.withColor(rgb));
    }
    public static List<Text> generateToolTip(Text text){
        return generateToolTip(text,16755200);
    }
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
}
