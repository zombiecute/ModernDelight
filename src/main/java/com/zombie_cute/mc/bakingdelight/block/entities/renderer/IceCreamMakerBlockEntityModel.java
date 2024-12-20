package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.entities.IceCreamMakerBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class IceCreamMakerBlockEntityModel extends GeoModel<IceCreamMakerBlockEntity> {
    @Override
    public Identifier getModelResource(IceCreamMakerBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID,"geo/ice_cream_maker.geo.json");
    }

    @Override
    public Identifier getTextureResource(IceCreamMakerBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID, "textures/block/ice_cream_maker.png");
    }

    @Override
    public Identifier getAnimationResource(IceCreamMakerBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID, "animations/ice_cream_maker.animation.json");
    }
}
