package com.zombie_cute.mc.moderndelight.block.food.clay_pot.pork_noodle;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PorkNoodleBlockEntityRender extends GeoBlockRenderer<PorkNoodleBlockEntity> {
    public PorkNoodleBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new PorkNoodleBlockEntityModel());
    }
}
