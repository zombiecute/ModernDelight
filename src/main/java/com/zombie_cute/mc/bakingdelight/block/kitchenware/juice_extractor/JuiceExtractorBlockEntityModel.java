package com.zombie_cute.mc.bakingdelight.block.kitchenware.juice_extractor;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class JuiceExtractorBlockEntityModel extends DefaultedBlockGeoModel<JuiceExtractorBlockEntity> {
    public JuiceExtractorBlockEntityModel() {
        super(new Identifier(ModernDelightMain.MOD_ID,"juice_extractor"));

    }

    @Override
    public Identifier getModelResource(JuiceExtractorBlockEntity animatable) {
        return new Identifier(ModernDelightMain.MOD_ID,"geo/juice_extractor.geo.json");
    }

    @Override
    public Identifier getTextureResource(JuiceExtractorBlockEntity animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "textures/block/juice_extractor.png");
    }

    @Override
    public Identifier getAnimationResource(JuiceExtractorBlockEntity animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "animations/juice_extractor.animation.json");
    }
    @Override
    public RenderLayer getRenderType(JuiceExtractorBlockEntity animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
    }
}
