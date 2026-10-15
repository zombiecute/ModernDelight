package com.zombie_cute.mc.moderndelight.block.kitchenware.ice_cream_maker;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class IceCreamMakerBlockEntityRender extends GeoBlockRenderer<IceCreamMakerBlockEntity> {
    public IceCreamMakerBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new IceCreamMakerBlockEntityModel());
    }

}
