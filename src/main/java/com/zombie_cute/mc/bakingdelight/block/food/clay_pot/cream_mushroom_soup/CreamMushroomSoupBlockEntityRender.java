package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.cream_mushroom_soup;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class CreamMushroomSoupBlockEntityRender extends GeoBlockRenderer<CreamMushroomSoupBlockEntity> {
    public CreamMushroomSoupBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new CreamMushroomSoupBlockEntityModel());
    }
}
