package com.zombie_cute.mc.moderndelight.block.food.clay_pot.cream_mushroom_soup;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class CreamMushroomSoupBlockEntityModel extends DefaultedBlockGeoModel<CreamMushroomSoupBlockEntity> {
    public CreamMushroomSoupBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"cream_mushroom_soup"));
    }

    @Override
    public Identifier getModelResource(CreamMushroomSoupBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/cream_mushroom_soup.geo.json");
    }

    @Override
    public Identifier getTextureResource(CreamMushroomSoupBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/cream_mushroom_soup.png");
    }
}
