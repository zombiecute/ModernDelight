package com.zombie_cute.mc.moderndelight.block.food.clay_pot.pork_noodle;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class PorkNoodleBlockEntityModel extends DefaultedBlockGeoModel<PorkNoodleBlockEntity> {
    public PorkNoodleBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"pork_noodle"));
    }

    @Override
    public Identifier getModelResource(PorkNoodleBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/pork_noodle.geo.json");
    }

    @Override
    public Identifier getTextureResource(PorkNoodleBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/pork_noodle.png");
    }
}
