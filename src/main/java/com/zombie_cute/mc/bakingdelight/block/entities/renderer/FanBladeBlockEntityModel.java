package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.entities.FanBladeBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FanBladeBlockEntityModel extends GeoModel<FanBladeBlockEntity> {
    @Override
    public Identifier getModelResource(FanBladeBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID,"geo/fan_blade.geo.json");
    }

    @Override
    public Identifier getTextureResource(FanBladeBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID, "textures/block/fan_blade.png");
    }

    @Override
    public Identifier getAnimationResource(FanBladeBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID, "animations/fan_blade.animation.json");
    }
}
