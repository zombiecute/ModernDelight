package com.zombie_cute.mc.bakingdelight.item.custom;

import com.zombie_cute.mc.bakingdelight.util.components.cumstom.FlavorComponent;

public class CreamItem extends ModStewItem{
    public CreamItem(FlavorComponent flavorComponent, Settings settings) {
        super(settings);
        this.flavorComponent = flavorComponent;
    }

    public FlavorComponent getFlavor() {
        return flavorComponent;
    }

    private final FlavorComponent flavorComponent;
}
