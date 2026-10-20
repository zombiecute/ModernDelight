package com.zombie_cute.mc.moderndelight.block.kitchenware.decor.clay_pot;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class ClayPotBlockEntityModel extends DefaultedBlockGeoModel<ClayPotBlockEntity> {
    public ClayPotBlockEntityModel() {
        super(Identifier.of(ModernDelightMain.MOD_ID,"clay_pot"));
    }

    @Override
    public Identifier getModelResource(ClayPotBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID,"geo/clay_pot.geo.json");
    }

    @Override
    public Identifier getTextureResource(ClayPotBlockEntity animatable) {
        return Identifier.of(ModernDelightMain.MOD_ID, "textures/block/clay_pot.png");
    }
}
