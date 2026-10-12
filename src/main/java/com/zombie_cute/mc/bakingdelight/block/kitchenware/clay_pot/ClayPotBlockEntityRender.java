package com.zombie_cute.mc.bakingdelight.block.kitchenware.clay_pot;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ClayPotBlockEntityRender extends GeoBlockRenderer<ClayPotBlockEntity> {
    public ClayPotBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new ClayPotBlockEntityModel());
    }
}
