package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.crab_sausage_vegetable;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class CrabSausageVegetableBlockEntityRender extends GeoBlockRenderer<CrabSausageVegetableBlockEntity> {
    public CrabSausageVegetableBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new CrabSausageVegetableBlockEntityModel());
    }
}
