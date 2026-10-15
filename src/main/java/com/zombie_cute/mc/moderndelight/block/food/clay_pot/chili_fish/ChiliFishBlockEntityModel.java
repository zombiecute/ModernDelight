package com.zombie_cute.mc.moderndelight.block.food.clay_pot.chili_fish;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class ChiliFishBlockEntityModel extends DefaultedBlockGeoModel<ChiliFishBlockEntity> {
    public ChiliFishBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"chili_fish"));
    }

    @Override
    public Identifier getModelResource(ChiliFishBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/chili_fish.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChiliFishBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/chili_fish.png");
    }
}
