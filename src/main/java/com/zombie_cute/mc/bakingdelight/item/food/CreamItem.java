package com.zombie_cute.mc.bakingdelight.item.food;

import com.zombie_cute.mc.bakingdelight.components.custom.FlavorComponent;
import net.minecraft.item.Items;

public class CreamItem extends PackagedItem {
    public CreamItem(FlavorComponent creamFlavor, Settings settings) {
        super(Items.BOWL,settings);
        this.creamFlavor = creamFlavor;
    }

    public FlavorComponent getFlavor() {
        return creamFlavor;
    }

    private final FlavorComponent creamFlavor;
}
