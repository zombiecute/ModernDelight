package com.zombie_cute.mc.bakingdelight.util.components;

import com.mojang.serialization.Codec;
import com.zombie_cute.mc.bakingdelight.Bakingdelight;
import com.zombie_cute.mc.bakingdelight.util.components.cumstom.FlavorComponent;
import com.zombie_cute.mc.bakingdelight.util.components.cumstom.FlavorListComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    public static final ComponentType<FlavorComponent> FLAVOR = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Bakingdelight.MOD_ID, "flavor"),
            ComponentType.<FlavorComponent>builder().codec(FlavorComponent.CODEC).build()
    );
    public static final ComponentType<FlavorListComponent> FLAVOR_LIST = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Bakingdelight.MOD_ID, "flavor_list"),
            ComponentType.<FlavorListComponent>builder().codec(FlavorListComponent.CODEC).build()
    );
    public static final ComponentType<Integer> POWER = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Bakingdelight.MOD_ID, "power"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static void init(){
        Bakingdelight.LOGGER.info("Registering Mod Components for " + Bakingdelight.MOD_ID);
    }
}
