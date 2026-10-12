package com.zombie_cute.mc.bakingdelight.block.food.clay_pot.marshala_cheese_ball;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class MarshalaCheeseBallBlockEntityModel extends DefaultedBlockGeoModel<MarshalaCheeseBallBlockEntity> {
    public MarshalaCheeseBallBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"marshala_cheese_ball"));
    }

    @Override
    public Identifier getModelResource(MarshalaCheeseBallBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/marshala_cheese_ball.geo.json");
    }

    @Override
    public Identifier getTextureResource(MarshalaCheeseBallBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/marshala_cheese_ball.png");
    }
}
