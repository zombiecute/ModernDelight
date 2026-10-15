package com.zombie_cute.mc.moderndelight.block.food.clay_pot.marshala_cheese_ball;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MarshalaCheeseBallBlockEntityRender extends GeoBlockRenderer<MarshalaCheeseBallBlockEntity> {
    public MarshalaCheeseBallBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new MarshalaCheeseBallBlockEntityModel());
    }
}
