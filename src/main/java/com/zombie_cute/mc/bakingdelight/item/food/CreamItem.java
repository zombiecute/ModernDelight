package com.zombie_cute.mc.bakingdelight.item.food;

import com.zombie_cute.mc.bakingdelight.util.enums.CreamFlavor;
import net.minecraft.item.Items;

public class CreamItem extends PackagedItem {
    public CreamItem(CreamFlavor creamFlavor, Settings settings) {
        super(Items.BOWL,settings);
        this.creamFlavor = creamFlavor;
    }

    public CreamFlavor getFlavor() {
        return creamFlavor;
    }

    private final CreamFlavor creamFlavor;
}
