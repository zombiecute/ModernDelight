package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.pepper_beef_rice;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class PepperBeefRiceBlockEntityModel extends DefaultedBlockGeoModel<PepperBeefRiceBlockEntity> {
    public PepperBeefRiceBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"pepper_beef_rice"));
    }

    @Override
    public Identifier getModelResource(PepperBeefRiceBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/pepper_beef_rice.geo.json");
    }

    @Override
    public Identifier getTextureResource(PepperBeefRiceBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/pepper_beef_rice.png");
    }
}
