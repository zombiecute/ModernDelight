package com.zombie_cute.mc.bakingdelight.block.entities.renderer;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.block.entities.FishAndChipsBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FishAndChipsBlockEntityModel extends GeoModel<FishAndChipsBlockEntity> {
    @Override
    public Identifier getModelResource(FishAndChipsBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID,"geo/fish_and_chips.geo.json");
    }

    @Override
    public Identifier getTextureResource(FishAndChipsBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID, "textures/block/fish_and_chips.png");
    }

    @Override
    public Identifier getAnimationResource(FishAndChipsBlockEntity animatable) {
        return Identifier.of(Bakingdelight.MOD_ID, "animations/fish_and_chips.animation.json");
    }
}
