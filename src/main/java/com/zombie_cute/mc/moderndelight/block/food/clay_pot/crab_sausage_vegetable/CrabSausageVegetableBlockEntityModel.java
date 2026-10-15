package com.zombie_cute.mc.moderndelight.block.food.clay_pot.crab_sausage_vegetable;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class CrabSausageVegetableBlockEntityModel extends DefaultedBlockGeoModel<CrabSausageVegetableBlockEntity> {
    public CrabSausageVegetableBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"crab_sausage_vegetable"));
    }

    @Override
    public Identifier getModelResource(CrabSausageVegetableBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/crab_sausage_vegetable.geo.json");
    }

    @Override
    public Identifier getTextureResource(CrabSausageVegetableBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/crab_sausage_vegetable.png");
    }
}
