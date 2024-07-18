package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.block.entities.IceCreamMakerBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class IceCreamMakerBlockEntityRender extends GeoBlockRenderer<IceCreamMakerBlockEntity> {
    public IceCreamMakerBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new IceCreamMakerBlockEntityModel());
    }

}
