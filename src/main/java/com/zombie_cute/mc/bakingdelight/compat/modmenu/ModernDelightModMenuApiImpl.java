package com.zombie_cute.mc.bakingdelight.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.zombie_cute.mc.bakingdelight.util.ModConfig;

public class ModernDelightModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfig::makeScreen;
    }
}
