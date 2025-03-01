package com.zombie_cute.mc.bakingdelight.block.kitchenware.ice_cream_maker;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class IceCreamMakerBlockItemModel extends DefaultedItemGeoModel<IceCreamMakerBlockItem> {
    public IceCreamMakerBlockItemModel() {
        super(new Identifier(ModernDelightMain.MOD_ID,"ice_cream_maker_item"));
    }

    @Override
    public Identifier getModelResource(IceCreamMakerBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID,"geo/ice_cream_maker.geo.json");
    }

    @Override
    public Identifier getTextureResource(IceCreamMakerBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "textures/block/ice_cream_maker.png");
    }

    @Override
    public Identifier getAnimationResource(IceCreamMakerBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "animations/ice_cream_maker.animation.json");
    }
}
