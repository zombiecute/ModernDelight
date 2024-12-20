package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.custom.blockItem.IceCreamMakerBlockItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class IceCreamMakerBlockItemModel extends GeoModel<IceCreamMakerBlockItem> {
    @Override
    public Identifier getModelResource(IceCreamMakerBlockItem animatable) {
        return Identifier.of(Bakingdelight.MOD_ID,"geo/ice_cream_maker.geo.json");
    }

    @Override
    public Identifier getTextureResource(IceCreamMakerBlockItem animatable) {
        return Identifier.of(Bakingdelight.MOD_ID, "textures/block/ice_cream_maker.png");
    }

    @Override
    public Identifier getAnimationResource(IceCreamMakerBlockItem animatable) {
        return Identifier.of(Bakingdelight.MOD_ID, "animations/ice_cream_maker.animation.json");
    }
}
