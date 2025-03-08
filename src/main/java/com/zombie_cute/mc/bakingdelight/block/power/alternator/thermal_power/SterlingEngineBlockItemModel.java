package com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class SterlingEngineBlockItemModel extends DefaultedItemGeoModel<SterlingEngineBlockItem> {
    public SterlingEngineBlockItemModel() {
        super(new Identifier(ModernDelightMain.MOD_ID,"sterling_engine_item"));

    }

    @Override
    public Identifier getModelResource(SterlingEngineBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID,"geo/sterling_engine.geo.json");
    }

    @Override
    public Identifier getTextureResource(SterlingEngineBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "textures/block/sterling_engine.png");
    }

    @Override
    public Identifier getAnimationResource(SterlingEngineBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "animations/sterling_engine.animation.json");
    }
}
