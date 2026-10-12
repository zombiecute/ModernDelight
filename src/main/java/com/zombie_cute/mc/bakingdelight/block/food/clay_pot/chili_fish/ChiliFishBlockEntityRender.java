package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.chili_fish;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ChiliFishBlockEntityRender extends GeoBlockRenderer<ChiliFishBlockEntity> {
    public ChiliFishBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new ChiliFishBlockEntityModel());
    }
}
