package com.zombie_cute.mc.bakingdelight.item.custom;

import com.zombie_cute.mc.bakingdelight.util.Flavor;

public class CreamItem extends ModStewItem{
    public CreamItem(Flavor flavor, Settings settings) {
        super(settings);
        this.flavor = flavor;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    private final Flavor flavor;
}
