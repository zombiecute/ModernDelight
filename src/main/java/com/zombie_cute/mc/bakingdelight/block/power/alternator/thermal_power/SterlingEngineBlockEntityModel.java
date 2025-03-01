package com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class SterlingEngineBlockEntityModel extends DefaultedBlockGeoModel<SterlingEngineBlockEntity> {
    public SterlingEngineBlockEntityModel() {
        super(new Identifier(ModernDelightMain.MOD_ID,"sterling_engine"));

    }

    @Override
    public Identifier getModelResource(SterlingEngineBlockEntity animatable) {
        return new Identifier(ModernDelightMain.MOD_ID,"geo/sterling_engine.geo.json");
    }

    @Override
    public Identifier getTextureResource(SterlingEngineBlockEntity animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "textures/block/sterling_engine.png");
    }

    @Override
    public Identifier getAnimationResource(SterlingEngineBlockEntity animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "animations/sterling_engine.animation.json");
    }
}
