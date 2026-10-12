package com.zombie_cute.mc.bakingdelight.advancement;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.advancement.criterion.FastStoveClickCriterion;
import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
    public static final FastStoveClickCriterion FAST_STOVE_CLICK =
            Criteria.register(new FastStoveClickCriterion());

    public static void register() {
        ModernDelightMain.LOGGER.info("Registering Mod Criteria for " + ModernDelightMain.MOD_ID);
    }
}