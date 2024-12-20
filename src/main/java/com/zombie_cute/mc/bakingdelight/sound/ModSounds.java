package com.zombie_cute.mc.bakingdelight.sound;

import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent ENTITY_BUTTER_HIT = registerSound("entity_butter_hit");
    public static final SoundEvent ENTITY_BUTTER_SHOOT = registerSound("entity_butter_shoot");
    public static final SoundEvent ENTITY_CHERRY_BOMB_EXPLOSION = registerSound("entity_cherry_bomb_explosion");
    public static final SoundEvent ENTITY_CHERRY_BOMB_SHOOT = registerSound("entity_cherry_bomb_shoot");
    public static final SoundEvent BLOCK_FREEZER_RUNNING = registerSound("block_freezer_running");
    public static final SoundEvent BLOCK_FREEZER_OPEN = registerSound("block_freezer_open");
    public static final SoundEvent BLOCK_FREEZER_CLOSE = registerSound("block_freezer_close");
    public static final SoundEvent BLOCK_GLASS_BOWL_WHISKING = registerSound("block_glass_bowl_whisking");
    public static final SoundEvent BLOCK_FOOD_FRYING = registerSound("block_food_frying");
    public static final SoundEvent ITEM_CROWBAR_HIT = registerSound("item_crowbar_hit");
    public static final SoundEvent ITEM_CROWBAR_ATTACK = registerSound("item_crowbar_attack");
    public static final SoundEvent BLOCK_GAS_CANISTER_FILLING = registerSound("block_gas_canister_filling");
    public static final SoundEvent BLOCK_GAS_COOKING_STOVE_IGNITE = registerSound("block_gas_cooking_stove_ignite");
    public static final SoundEvent BLOCK_STERLING_ENGINE = registerSound("block_sterling_engine");
    public static final SoundEvent BLOCK_TESLA_COIL = registerSound("block_tesla_coil");
    public static final SoundEvent ITEM_ELECTRIC_WHISK_WORKING = registerSound("item_electric_whisk_working");

    public static SoundEvent registerSound(String name){
        Identifier id = Identifier.of(Bakingdelight.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    public static void registerModSounds(){
        Bakingdelight.LOGGER.info("Registering Mod Sounds for " + Bakingdelight.MOD_ID);
    }
}
