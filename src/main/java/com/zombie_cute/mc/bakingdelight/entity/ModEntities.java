package com.zombie_cute.mc.bakingdelight.entity;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.entity.custom.ButterEntity;
import com.zombie_cute.mc.bakingdelight.entity.custom.CherryBombEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<ButterEntity> BUTTER = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ModernDelightMain.MOD_ID,"butter"),
            FabricEntityTypeBuilder.<ButterEntity>create(SpawnGroup.MISC, ButterEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f,0.25f))
                    .trackRangeBlocks(4).trackedUpdateRate(10).build());
    public static EntityType<CherryBombEntity> CHERRY_BOMB = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ModernDelightMain.MOD_ID,"cherry_bomb"),
            FabricEntityTypeBuilder.<CherryBombEntity>create(SpawnGroup.MISC, CherryBombEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f,0.25f))
                    .trackRangeBlocks(4).trackedUpdateRate(10).build());

    public static void registerModEntities(){
        ModernDelightMain.LOGGER.info("Registering Mod Entities for " + ModernDelightMain.MOD_ID);
    }
}
