package com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SterlingEngineBlockEntityRenderer extends GeoBlockRenderer<SterlingEngineBlockEntity> {
    public SterlingEngineBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(new SterlingEngineBlockEntityModel());
    }

}
