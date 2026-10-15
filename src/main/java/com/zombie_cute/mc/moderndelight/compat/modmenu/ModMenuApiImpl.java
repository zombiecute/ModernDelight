package com.zombie_cute.mc.moderndelight.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.zombie_cute.mc.moderndelight.util.ModConfig;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        try {
            return ModConfig::makeScreen;
        } catch (Exception e){
            return null;
        }
    }
}
