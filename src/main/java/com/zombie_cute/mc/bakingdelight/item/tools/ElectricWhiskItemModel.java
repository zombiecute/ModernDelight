package com.zombie_cute.mc.bakingdelight.item.tools;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class ElectricWhiskItemModel extends DefaultedItemGeoModel<ElectricWhiskItem> {
    public ElectricWhiskItemModel() {
        super(new Identifier(ModernDelightMain.MOD_ID,"electric_whisk"));
    }

    @Override
    public Identifier getModelResource(ElectricWhiskItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID,"geo/electric_whisk.geo.json");
    }

    @Override
    public Identifier getTextureResource(ElectricWhiskItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "textures/item/electric_whisk.png");
    }

    @Override
    public Identifier getAnimationResource(ElectricWhiskItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "animations/electric_whisk.animation.json");
    }
}
