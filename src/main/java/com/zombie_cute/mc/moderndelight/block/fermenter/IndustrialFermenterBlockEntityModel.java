package com.zombie_cute.mc.moderndelight.block.fermenter;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class IndustrialFermenterBlockEntityModel extends DefaultedBlockGeoModel<IndustrialFermenterBlockEntity> {
    public IndustrialFermenterBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"industrial_fermenter"));
    }

    @Override
    public Identifier getModelResource(IndustrialFermenterBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/industrial_fermenter.geo.json");
    }

    @Override
    public Identifier getTextureResource(IndustrialFermenterBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/industrial_fermenter.png");
    }

    @Override
    public Identifier getAnimationResource(IndustrialFermenterBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "animations/industrial_fermenter.animation.json");
    }
}
