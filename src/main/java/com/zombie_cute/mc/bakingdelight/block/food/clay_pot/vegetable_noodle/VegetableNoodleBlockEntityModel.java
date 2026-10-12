package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.vegetable_noodle;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class VegetableNoodleBlockEntityModel extends DefaultedBlockGeoModel<VegetableNoodleBlockEntity> {
    public VegetableNoodleBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"vegetable_noodle"));
    }

    @Override
    public Identifier getModelResource(VegetableNoodleBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/vegetable_noodle.geo.json");
    }

    @Override
    public Identifier getTextureResource(VegetableNoodleBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/vegetable_noodle.png");
    }
}
