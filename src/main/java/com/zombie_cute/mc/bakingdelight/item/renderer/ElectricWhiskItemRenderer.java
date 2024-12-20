package com.zombie_cute.mc.bakingdelight.item.renderer;

import com.zombie_cute.mc.bakingdelight.item.custom.ElectricWhiskItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ElectricWhiskItemRenderer extends GeoItemRenderer<ElectricWhiskItem> {
    public ElectricWhiskItemRenderer() {
        super(new ElectricWhiskItemModel());
    }
}
