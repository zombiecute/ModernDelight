package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.vegetable_noodle;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class VegetableNoodleBlockEntityRender extends GeoBlockRenderer<VegetableNoodleBlockEntity> {
    public VegetableNoodleBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new VegetableNoodleBlockEntityModel());
    }
}
