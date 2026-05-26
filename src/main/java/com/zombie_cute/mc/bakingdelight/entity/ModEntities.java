package com.zombie_cute.mc.bakingdelight.entity;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.entity.custom.ButterEntity;
import com.zombie_cute.mc.bakingdelight.entity.custom.CherryBombEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<ButterEntity> BUTTER = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(ModernDelightMain.MOD_ID,"butter"),
            EntityType.Builder.<ButterEntity>create(ButterEntity::new,SpawnGroup.MISC)
                    .dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10).build());
    public static EntityType<CherryBombEntity> CHERRY_BOMB = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(ModernDelightMain.MOD_ID,"cherry_bomb"),
            EntityType.Builder.<CherryBombEntity>create(CherryBombEntity::new,SpawnGroup.MISC)
                    .dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10).build());

    public static void registerModEntities(){
        ModernDelightMain.LOGGER.info("Registering Mod Entities for " + ModernDelightMain.MOD_ID);
    }
}
