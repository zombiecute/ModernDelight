package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.entities.SterlingEngineBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SterlingEngineBlockEntityModel extends GeoModel<SterlingEngineBlockEntity> {
    @Override
    public Identifier getModelResource(SterlingEngineBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID,"geo/sterling_engine.geo.json");
    }

    @Override
    public Identifier getTextureResource(SterlingEngineBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID, "textures/block/sterling_engine.png");
    }

    @Override
    public Identifier getAnimationResource(SterlingEngineBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID, "animations/sterling_engine.animation.json");
    }
}
