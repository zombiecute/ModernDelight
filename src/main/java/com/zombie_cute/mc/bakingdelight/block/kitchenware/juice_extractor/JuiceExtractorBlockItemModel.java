package com.zombie_cute.mc.bakingdelight.block.kitchenware.juice_extractor;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class JuiceExtractorBlockItemModel extends DefaultedItemGeoModel<JuiceExtractorBlockItem> {
    public JuiceExtractorBlockItemModel() {
        super(new Identifier(ModernDelightMain.MOD_ID,"juice_extractor_item"));

    }

    @Override
    public Identifier getModelResource(JuiceExtractorBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID,"geo/juice_extractor.geo.json");
    }

    @Override
    public Identifier getTextureResource(JuiceExtractorBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "textures/block/juice_extractor.png");
    }

    @Override
    public Identifier getAnimationResource(JuiceExtractorBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "animations/juice_extractor.animation.json");
    }
}
