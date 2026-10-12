package com.zombie_cute.mc.bakingdelight.block.fermenter;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class IndustrialFermenterBlockItemModel extends DefaultedItemGeoModel<IndustrialFermenterBlockItem> {
    public IndustrialFermenterBlockItemModel() {
        super(new Identifier(ModernDelightMain.MOD_ID,"industrial_fermenter_item"));
    }

    @Override
    public Identifier getModelResource(IndustrialFermenterBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID,"geo/industrial_fermenter.geo.json");
    }

    @Override
    public Identifier getTextureResource(IndustrialFermenterBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "textures/block/industrial_fermenter.png");
    }

    @Override
    public Identifier getAnimationResource(IndustrialFermenterBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "animations/industrial_fermenter.animation.json");
    }
}
