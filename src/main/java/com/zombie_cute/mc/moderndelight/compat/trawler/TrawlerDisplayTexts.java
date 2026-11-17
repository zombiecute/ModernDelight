package com.zombie_cute.mc.moderndelight.compat.trawler;

import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBaitPreview;
import net.minecraft.text.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class TrawlerDisplayTexts {
    public static final String TITLE = "emi.category.moderndelight.trawler";
    public static final String PREFIX = "moderndelight.compat.trawler.";

    public static Text condition(TrawlerBaitPreview preview) {
        return Text.translatable(PREFIX + (preview.openWater() ? "open_water" : "restricted_water"));
    }

    public static Text interval(TrawlerBaitPreview preview) {
        return Text.translatable(PREFIX + "interval", number(preview.intervalTicks() / 20.0));
    }

    public static Text consumption(TrawlerBaitPreview preview) {
        return Text.translatable(PREFIX + "consumption", number(preview.consumeChance() * 100.0));
    }

    public static Text outputs(TrawlerBaitPreview preview) {
        return Text.translatable(PREFIX + "outputs", preview.page(), preview.pages());
    }

    public static List<Text> tooltip(TrawlerBaitPreview preview) {
        List<Text> lines = new ArrayList<>();
        lines.add(Text.translatable(PREFIX + "water_requirement"));
        lines.add(Text.translatable(PREFIX + "possible_loot"));
        lines.add(Text.translatable(PREFIX + "modifiers"));
        lines.add(Text.translatable(PREFIX + "consume_on_catch"));
        if (preview.incomplete()) {
            lines.add(Text.translatable(PREFIX + "incomplete"));
        }
        return lines;
    }

    private static String number(double value) {
        // Consumption chances originate in JSON floats; avoid displaying their binary rounding noise.
        return BigDecimal.valueOf(value).setScale(2, java.math.RoundingMode.HALF_UP)
                .stripTrailingZeros().toPlainString();
    }
}
