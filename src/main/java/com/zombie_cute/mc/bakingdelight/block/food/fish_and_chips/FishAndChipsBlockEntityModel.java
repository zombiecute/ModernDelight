package com.zombie_cute.mc.bakingdelight.block.food.fish_and_chips;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class FishAndChipsBlockEntityModel extends DefaultedBlockGeoModel<FishAndChipsBlockEntity> {
    public FishAndChipsBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"fish_and_chips"));
    }

    @Override
    public Identifier getModelResource(FishAndChipsBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/fish_and_chips.geo.json");
    }

    @Override
    public Identifier getTextureResource(FishAndChipsBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/fish_and_chips.png");
    }

    @Override
    public Identifier getAnimationResource(FishAndChipsBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "animations/fish_and_chips.animation.json");
    }
}
