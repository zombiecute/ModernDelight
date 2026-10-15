package com.zombie_cute.mc.moderndelight.block.fermenter;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class IndustrialFermenterBlockEntityRenderer extends GeoBlockRenderer<IndustrialFermenterBlockEntity> {
    public IndustrialFermenterBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(new IndustrialFermenterBlockEntityModel());
    }
}
