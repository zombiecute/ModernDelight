package com.zombie_cute.mc.bakingdelight.components;

import com.mojang.serialization.Codec;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.components.custom.FlavorComponent;
import com.zombie_cute.mc.bakingdelight.components.custom.FlavorListComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModComponents {
    public static final ComponentType<FlavorComponent> FLAVOR = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ModernDelightMain.MOD_ID, "flavor"),
            ComponentType.<FlavorComponent>builder().codec(FlavorComponent.CODEC).build()
    );
    public static final ComponentType<FlavorListComponent> FLAVOR_LIST = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ModernDelightMain.MOD_ID, "flavor_list"),
            ComponentType.<FlavorListComponent>builder().codec(FlavorListComponent.CODEC).build()
    );
    public static final ComponentType<Long> POWER = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ModernDelightMain.MOD_ID, "power"),
            ComponentType.<Long>builder().codec(Codec.LONG).build()
    );
    public static final ComponentType<List<String>> SEASONING_ITEMS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ModernDelightMain.MOD_ID, "seasoning_items"),
            ComponentType.<List<String>>builder().codec(Codec.list(Codec.STRING)).build()
    );
    public static final ComponentType<List<String>> INSTANT_NOODLES_INGREDIENTS = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ModernDelightMain.MOD_ID, "instant_noodles_ingredients"),
            ComponentType.<List<String>>builder().codec(Codec.list(Codec.STRING)).build()
    );
    public static final ComponentType<ItemStack> STONE_MORTAR_CRAFTING_STACK = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ModernDelightMain.MOD_ID, "stone_mortar_crafting_stack"),
            ComponentType.<ItemStack>builder().codec(ItemStack.CODEC).build()
    );
    public static final ComponentType<ItemStack> HOLDER_HOLDING_STACK = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ModernDelightMain.MOD_ID, "holder_holder_holder_stack"),
            ComponentType.<ItemStack>builder().codec(ItemStack.CODEC).build()
    );
    public static final ComponentType<Boolean> POT_HAS_WATER = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ModernDelightMain.MOD_ID, "pot_has_water"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );
    public static final ComponentType<Boolean> POT_HAS_QUICKLIME = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(ModernDelightMain.MOD_ID, "pot_has_quicklime"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static void registerModComponents(){
        ModernDelightMain.LOGGER.info("Registering Mod Components for " + ModernDelightMain.MOD_ID);
    }
}
