package com.zombie_cute.mc.bakingdelight.block.power.alternator.wind_power;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class FanBladeBlockEntityModel extends DefaultedBlockGeoModel<FanBladeBlockEntity> {
    public FanBladeBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"fan_blade"));
    }
    @Override
    public Identifier getModelResource(FanBladeBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/fan_blade.geo.json");
    }

    @Override
    public Identifier getTextureResource(FanBladeBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/fan_blade.png");
    }

    @Override
    public Identifier getAnimationResource(FanBladeBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "animations/fan_blade.animation.json");
    }
}
