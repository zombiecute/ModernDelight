package com.zombie_cute.mc.moderndelight.block.food.clay_pot.french_onion_soup;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class FrenchOnionSoupBlockEntityModel extends DefaultedBlockGeoModel<FrenchOnionSoupBlockEntity> {
    public FrenchOnionSoupBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"french_onion_soup"));
    }

    @Override
    public Identifier getModelResource(FrenchOnionSoupBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/french_onion_soup.geo.json");
    }

    @Override
    public Identifier getTextureResource(FrenchOnionSoupBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/french_onion_soup.png");
    }
}
