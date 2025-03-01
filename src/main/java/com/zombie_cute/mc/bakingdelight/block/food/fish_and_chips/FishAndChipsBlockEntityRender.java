package com.zombie_cute.mc.bakingdelight.block.food.fish_and_chips;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FishAndChipsBlockEntityRender extends GeoBlockRenderer<FishAndChipsBlockEntity> {
    public FishAndChipsBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new FishAndChipsBlockEntityModel());
    }

}
