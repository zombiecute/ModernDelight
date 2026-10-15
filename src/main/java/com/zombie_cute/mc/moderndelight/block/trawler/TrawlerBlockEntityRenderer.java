package com.zombie_cute.mc.moderndelight.block.trawler;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class TrawlerBlockEntityRenderer extends GeoBlockRenderer<TrawlerBlockEntity> {
    public TrawlerBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(new TrawlerBlockEntityModel());
    }
}
