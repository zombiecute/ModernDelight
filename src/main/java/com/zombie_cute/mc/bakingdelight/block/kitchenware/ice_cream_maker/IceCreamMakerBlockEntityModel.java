package com.zombie_cute.mc.bakingdelight.block.kitchenware.ice_cream_maker;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class IceCreamMakerBlockEntityModel extends DefaultedBlockGeoModel<IceCreamMakerBlockEntity> {
    public IceCreamMakerBlockEntityModel() {
        super(new Identifier(ModernDelightMain.MOD_ID,"ice_cream_maker"));
    }

    @Override
    public Identifier getModelResource(IceCreamMakerBlockEntity animatable) {
        return new Identifier(ModernDelightMain.MOD_ID,"geo/ice_cream_maker.geo.json");
    }

    @Override
    public Identifier getTextureResource(IceCreamMakerBlockEntity animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "textures/block/ice_cream_maker.png");
    }

    @Override
    public Identifier getAnimationResource(IceCreamMakerBlockEntity animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "animations/ice_cream_maker.animation.json");
    }
}
