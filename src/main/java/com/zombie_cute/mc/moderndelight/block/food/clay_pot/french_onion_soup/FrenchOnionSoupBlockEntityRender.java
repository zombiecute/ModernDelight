package com.zombie_cute.mc.moderndelight.block.food.clay_pot.french_onion_soup;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FrenchOnionSoupBlockEntityRender extends GeoBlockRenderer<FrenchOnionSoupBlockEntity> {
    public FrenchOnionSoupBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new FrenchOnionSoupBlockEntityModel());
    }
}
