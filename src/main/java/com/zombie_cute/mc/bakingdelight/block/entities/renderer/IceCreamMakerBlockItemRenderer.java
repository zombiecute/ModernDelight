package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.block.custom.IceCreamMakerBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class IceCreamMakerBlockItemRenderer extends GeoItemRenderer<IceCreamMakerBlockItem> {
    public IceCreamMakerBlockItemRenderer() {
        super(new IceCreamMakerBlockItemModel());
    }
}
