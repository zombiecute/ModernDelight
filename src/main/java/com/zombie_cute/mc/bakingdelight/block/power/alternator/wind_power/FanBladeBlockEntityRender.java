package com.zombie_cute.mc.bakingdelight.block.power.alternator.wind_power;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FanBladeBlockEntityRender extends GeoBlockRenderer<FanBladeBlockEntity> {
    public FanBladeBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new FanBladeBlockEntityModel());
    }

}
