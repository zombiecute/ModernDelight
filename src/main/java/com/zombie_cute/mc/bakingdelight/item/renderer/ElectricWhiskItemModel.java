package com.zombie_cute.mc.bakingdelight.item.renderer;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.item.custom.ElectricWhiskItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ElectricWhiskItemModel extends GeoModel<ElectricWhiskItem> {
    @Override
    public Identifier getModelResource(ElectricWhiskItem animatable) {
        return new Identifier(Bakingdelight.MOD_ID,"geo/electric_whisk.geo.json");
    }

    @Override
    public Identifier getTextureResource(ElectricWhiskItem animatable) {
        return new Identifier(Bakingdelight.MOD_ID, "textures/item/electric_whisk.png");
    }

    @Override
    public Identifier getAnimationResource(ElectricWhiskItem animatable) {
        return new Identifier(Bakingdelight.MOD_ID, "animations/electric_whisk.animation.json");
    }
}
