package com.zombie_cute.mc.moderndelight.block.trawler;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class TrawlerBlockItemModel extends DefaultedItemGeoModel<TrawlerBlockItem> {
    public TrawlerBlockItemModel() {
        super(new Identifier(ModernDelightMain.MOD_ID,"trawler_item"));
    }

    @Override
    public Identifier getModelResource(TrawlerBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID,"geo/trawler.geo.json");
    }

    @Override
    public Identifier getTextureResource(TrawlerBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "textures/block/trawler.png");
    }

    @Override
    public Identifier getAnimationResource(TrawlerBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "animations/trawler.animation.json");
    }
}
