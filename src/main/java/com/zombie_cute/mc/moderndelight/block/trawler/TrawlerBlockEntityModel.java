package com.zombie_cute.mc.moderndelight.block.trawler;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class TrawlerBlockEntityModel extends DefaultedBlockGeoModel<TrawlerBlockEntity> {
    public TrawlerBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"trawler"));
    }

    @Override
    public Identifier getModelResource(TrawlerBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/trawler.geo.json");
    }

    @Override
    public Identifier getTextureResource(TrawlerBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/trawler.png");
    }

    @Override
    public Identifier getAnimationResource(TrawlerBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "animations/trawler.animation.json");
    }
}
