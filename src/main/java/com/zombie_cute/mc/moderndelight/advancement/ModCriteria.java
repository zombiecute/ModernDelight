package com.zombie_cute.mc.moderndelight.advancement;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import com.zombie_cute.mc.moderndelight.advancement.criterion.FastStoveClickCriterion;
import com.zombie_cute.mc.moderndelight.advancement.criterion.FermentCriterion;
import com.zombie_cute.mc.moderndelight.advancement.criterion.ItemProducedCriterion;
import com.zombie_cute.mc.moderndelight.advancement.criterion.SimpleActionCriterion;
import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
    public static final FastStoveClickCriterion FAST_STOVE_CLICK =
            Criteria.register(new FastStoveClickCriterion());

    public static final ItemProducedCriterion WHISK =
            Criteria.register(new ItemProducedCriterion("whisk"));

    public static final ItemProducedCriterion JUICE_EXTRACT =
            Criteria.register(new ItemProducedCriterion("juice_extract"));

    public static final ItemProducedCriterion TRAWLER_CATCH =
            Criteria.register(new ItemProducedCriterion("trawler_catch"));

    public static final FermentCriterion FERMENT =
            Criteria.register(new FermentCriterion());

    public static final SimpleActionCriterion CROWBAR_PRY =
            Criteria.register(new SimpleActionCriterion("crowbar_pry"));

    public static final SimpleActionCriterion CHERRY_BOMB_EXPLODE =
            Criteria.register(new SimpleActionCriterion("cherry_bomb_explode"));

    public static final SimpleActionCriterion NOT_SPICY =
            Criteria.register(new SimpleActionCriterion("not_spicy"));
    public static void register() {
    }
}
