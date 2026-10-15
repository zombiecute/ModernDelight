package com.zombie_cute.mc.moderndelight.block.food.clay_pot.pepper_beef_rice;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PepperBeefRiceBlockEntityRender extends GeoBlockRenderer<PepperBeefRiceBlockEntity> {
    public PepperBeefRiceBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new PepperBeefRiceBlockEntityModel());
    }
}
