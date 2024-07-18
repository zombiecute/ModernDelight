package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.block.entities.FishAndChipsBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FishAndChipsBlockEntityRender extends GeoBlockRenderer<FishAndChipsBlockEntity> {
    public FishAndChipsBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new FishAndChipsBlockEntityModel());
    }

}
