package com.zombie_cute.mc.bakingdelight.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.PlantBlock;

public class WildCropBlock extends PlantBlock {
    public WildCropBlock(Settings settings) {
        super(settings);
    }
    public static final MapCodec<WildCropBlock> CODEC = createCodec((WildCropBlock::new));

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }
}
