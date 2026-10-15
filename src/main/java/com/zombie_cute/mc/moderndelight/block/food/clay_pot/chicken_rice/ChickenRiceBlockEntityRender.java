package com.zombie_cute.mc.moderndelight.block.food.clay_pot.chicken_rice;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ChickenRiceBlockEntityRender extends GeoBlockRenderer<ChickenRiceBlockEntity> {
    public ChickenRiceBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new ChickenRiceBlockEntityModel());
    }
}
