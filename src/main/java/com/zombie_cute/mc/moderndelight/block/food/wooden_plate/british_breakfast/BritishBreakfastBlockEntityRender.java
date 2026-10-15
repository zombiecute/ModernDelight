package com.zombie_cute.mc.moderndelight.block.food.wooden_plate.british_breakfast;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BritishBreakfastBlockEntityRender extends GeoBlockRenderer<BritishBreakfastBlockEntity> {
    public BritishBreakfastBlockEntityRender(BlockEntityRendererFactory.Context context) {
        super(new BritishBreakfastBlockEntityModel());
    }
}
