package com.zombie_cute.mc.moderndelight.util;

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

    public static final String HOLD_A_KEY = "moderndelight.tooltips.hold_a_key";
    public static final String SHIFT_END = "moderndelight.tooltips.shift_end";
    public static final String ALT_END = "moderndelight.tooltips.alt_end";
    public static final String CTRL_END = "moderndelight.tooltips.ctrl_end";
    public static final String WHISK = "moderndelight.tooltips.whisk";
    public static final String BUTTER = "moderndelight.tooltips.butter";
    public static final String CUTTLEBONE = "moderndelight.tooltips.cuttlebone";
    public static final String TRUFFLE = "moderndelight.tooltips.truffle";
    public static final String FILTER = "moderndelight.tooltips.filter";
    public static final String KNEADING_STICK = "moderndelight.tooltips.kneading_stick";
    public static final String SPATULA = "moderndelight.tooltips.spatula";
    public static final String BDC = "moderndelight.tooltips.bdc";
    public static final String BDI = "moderndelight.tooltips.bdi";
    public static final String GAS_COOKING_STOVE = "moderndelight.tooltips.gas_cooking_stove";
    public static final String CROWBAR = "moderndelight.tooltips.crowbar";
    public static final String INGREDIENTS = "moderndelight.tooltips.ingredients";
    public static final String BAKING_TRAY = "moderndelight.tooltips.baking_tray";
    public static final String DEEP_FRYER = "moderndelight.tooltips.deep_fryer";
    public static final String WOODEN_BASIN = "moderndelight.tooltips.wooden_basin";
    public static final String HOLDER = "moderndelight.tooltips.holder";
    public static final String ACDCC = "moderndelight.tooltips.acdcc";
    public static final String ALT_ACGen = "moderndelight.tooltips.ac_gen";
    public static final String ALT_ACCom = "moderndelight.tooltips.ac_com";
    public static final String ALT_DCSto = "moderndelight.tooltips.dc_sto";
    public static final String ALT_DCGen = "moderndelight.tooltips.dc_gen";
    public static final String ALT_DCCom = "moderndelight.tooltips.dc_com";
    public static final String BAMBOO_STEAMER = "moderndelight.tooltips.bamboo_steamer";
    public static final String CUISINE_TABLE = "moderndelight.tooltips.cuisine_table";
    public static final String ELECTRICIANS_DESK = "moderndelight.tooltips.electricians_desk";
    public static final String ELECTRIC_STEAMER = "moderndelight.tooltips.electric_steamer";
    public static final String FAN_BLADE = "moderndelight.tooltips.fan_blade";
    public static final String FARADAY_GENERATOR = "moderndelight.tooltips.faraday_generator";
    public static final String FREEZER = "moderndelight.tooltips.freezer";
    public static final String GLASS_BOWL = "moderndelight.tooltips.glass_bowl";
    public static final String PGEN = "moderndelight.tooltips.p_gen";
    public static final String STERLING_ENGINE = "moderndelight.tooltips.sterling_engine";
    public static final String TESLA_COIL = "moderndelight.tooltips.tesla_coil";
    public static final String WTC = "moderndelight.tooltips.wtc";
    public static final String ICE_CREAM_MAKER = "moderndelight.tooltips.ice_cream_maker";
    public static final String TURNIP = "moderndelight.tooltips.turnip";
    public static final String ELECTRIC_WHISK_MSG = "moderndelight.msg.electric_whisk";
    public static final String ELECTRIC_WHISK_NEED_BOWL = "moderndelight.msg.electric_whisk.need_bowl";
    public static final String ELECTRIC_WHISK = "moderndelight.tooltips.electric_whisk";
    public static final String CHARGING_POST = "moderndelight.tooltips.charging_post";
    public static final String JUICE_EXTRACTOR = "moderndelight.tooltips.juice_extractor";
    public static final String POT_HAS_QUICKLIME = "moderndelight.tooltips.pot_has_quicklime";
    public static final String POT_MISS_QUICKLIME = "moderndelight.tooltips.pot_miss_quicklime";
    public static final String POT_HAS_WATER = "moderndelight.tooltips.pot_has_water";
    public static final String POT_MISS_WATER = "moderndelight.tooltips.pot_miss_water";
    public static final String NOODLE_UNHEALTHY = "moderndelight.tooltips.noodle_unhealthy";
    public static final String ANYTHING = "moderndelight.tooltips.anything";
    public static final String NEED_FOOD = "moderndelight.tooltips.need_food";
    public static final String SEASONING_TIP = "moderndelight.tooltips.condiment_tip";
    public static final String FAILED_SEASONING = "moderndelight.tooltips.failed_seasoning";
    public static final String SEASONING_ADDED = "moderndelight.tooltips.seasoning_added";
    public static final String CAN_PLACE = "moderndelight.tooltips.can_place";
    public static final String PUN = "moderndelight.tooltips.prohibit_unlimited_nesting";
    public static final String WOODEN_PLATE = "moderndelight.tooltips.wooden_plate";
    public static final String INDUSTRIAL_FERMENTER = "moderndelight.tooltips.industrial_fermenter";
    public static final String FERMENTING_QUALITY = "moderndelight.tooltips.fermenting_quality";
    public static final String QUALITY_NEG_1 = "moderndelight.tooltips.quality_negative_1";
    public static final String QUALITY_0 = "moderndelight.tooltips.quality_0";
    public static final String QUALITY_1 = "moderndelight.tooltips.quality_1";
    public static final String QUALITY_2 = "moderndelight.tooltips.quality_2";
    public static final String FERMENTATION_SHIFT = "moderndelight.tooltip.fermentation.shift";
    public static final String FERMENTATION_TIME = "moderndelight.tooltip.fermentation.time";
    public static final String FERMENTATION_LIGHT = "moderndelight.tooltip.fermentation.light";
    public static final String FERMENTATION_TEMP = "moderndelight.tooltip.fermentation.temperature";
    public static final String NO_BACTRIA = "moderndelight.tooltip.fermentation.no_bactria";
    public static final String FERMENTATION_TANK = "moderndelight.tooltip.fermentation_tank";
    public static final String TRAWLER = "moderndelight.tooltips.trawler";
    public static final String TRAWLER_BAIT = "moderndelight.tooltips.trawler_bait";
    public static final String GET_EXP = "tooltips.moderndelight.tooltip.get_exp";

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
        MutableText mutableText = Text.translatable(HOLD_A_KEY).formatted(Formatting.DARK_GRAY);
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
        MutableText mutableText = Text.translatable(HOLD_A_KEY).formatted(Formatting.DARK_GRAY);
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
    public static MutableText getCtrlText(boolean hasDown){
        MutableText mutableText = Text.translatable(HOLD_A_KEY).formatted(Formatting.DARK_GRAY);
        mutableText.append(Text.literal("[").formatted(Formatting.DARK_GRAY));
        if (hasDown){
            mutableText.append(Text.literal("Ctrl").formatted(Formatting.WHITE));
        } else {
            mutableText.append(Text.literal("Ctrl").formatted(Formatting.GRAY));
        }
        mutableText.append(Text.literal("]").formatted(Formatting.DARK_GRAY));
        mutableText.append(Text.translatable(CTRL_END).formatted(Formatting.DARK_GRAY));
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

    public static MutableText getFluidInfoText(FluidStack fluidStack, int maxCapacityMB) {
        String translation_key = fluidStack.getFluidVariant().getFluid().getDefaultState().getBlockState().getBlock().getTranslationKey();
        MutableText mutableText = Text.translatable(translation_key).formatted(Formatting.GRAY);
        mutableText.append(Text.literal(": ").formatted(Formatting.GRAY));
        long mb = FluidStack.convertDropletsToMb(fluidStack.getAmount());
        if (mb < maxCapacityMB / 4){
            mutableText.append(Text.literal(String.valueOf(mb)).formatted(Formatting.GREEN));
        } else if (mb < maxCapacityMB / 2) {
            mutableText.append(Text.literal(String.valueOf(mb)).formatted(Formatting.YELLOW));
        } else if (mb < maxCapacityMB* 3L / 4) {
            mutableText.append(Text.literal(String.valueOf(mb)).formatted(Formatting.GOLD));
        } else {
            mutableText.append(Text.literal(String.valueOf(mb)).formatted(Formatting.RED));
        }
        mutableText.append(Text.literal(" mB").formatted(Formatting.GRAY));
        return mutableText;
    }
}
