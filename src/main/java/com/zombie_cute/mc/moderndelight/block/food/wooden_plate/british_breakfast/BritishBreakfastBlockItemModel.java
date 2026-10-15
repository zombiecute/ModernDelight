package com.zombie_cute.mc.moderndelight.block.food.wooden_plate.british_breakfast;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class BritishBreakfastBlockItemModel extends DefaultedItemGeoModel<BritishBreakfastBlockItem> {
    public BritishBreakfastBlockItemModel() {
        super(new Identifier(ModernDelightMain.MOD_ID,"british_breakfast"));

    }

    @Override
    public Identifier getModelResource(BritishBreakfastBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID,"geo/british_breakfast.geo.json");
    }

    @Override
    public Identifier getTextureResource(BritishBreakfastBlockItem animatable) {
        return new Identifier(ModernDelightMain.MOD_ID, "textures/block/british_breakfast.png");
    }
}
