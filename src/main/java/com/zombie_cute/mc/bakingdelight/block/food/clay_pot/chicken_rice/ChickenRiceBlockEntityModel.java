package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.chicken_rice;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class ChickenRiceBlockEntityModel extends DefaultedBlockGeoModel<ChickenRiceBlockEntity> {
    public ChickenRiceBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"chicken_rice"));
    }

    @Override
    public Identifier getModelResource(ChickenRiceBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/chicken_rice.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChickenRiceBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/chicken_rice.png");
    }
}
