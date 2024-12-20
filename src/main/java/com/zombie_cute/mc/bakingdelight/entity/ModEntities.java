package com.zombie_cute.mc.bakingdelight.entity;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.entity.custom.ButterEntity;
import com.zombie_cute.mc.bakingdelight.entity.custom.CherryBombEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<ButterEntity> BUTTER = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(Bakingdelight.MOD_ID,"butter"),
            EntityType.Builder.<ButterEntity>create(SpawnGroup.MISC)
                    .dimensions(0.25f,0.25f)
                    .maxTrackingRange(4).trackingTickInterval(10).build("entity.bakingdelight.butter"));
    public static EntityType<CherryBombEntity> CHERRY_BOMB = Registry.register(
            Registries.ENTITY_TYPE, Identifier.of(Bakingdelight.MOD_ID,"cherry_bomb"),
            EntityType.Builder.<CherryBombEntity>create(SpawnGroup.MISC)
                    .dimensions(0.25f,0.25f)
                    .maxTrackingRange(4).trackingTickInterval(10).build("entity.bakingdelight.cherry_bomb"));

    public static void registerModEntities(){
        Bakingdelight.LOGGER.info("Registering Mod Entities for " + Bakingdelight.MOD_ID);
    }
}
