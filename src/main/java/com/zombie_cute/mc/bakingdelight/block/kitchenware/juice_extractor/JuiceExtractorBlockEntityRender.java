package com.zombie_cute.mc.bakingdelight.block.kitchenware.juice_extractor;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class JuiceExtractorBlockEntityRender extends GeoBlockRenderer<JuiceExtractorBlockEntity> {
    public JuiceExtractorBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new JuiceExtractorBlockEntityModel());
    }
}
