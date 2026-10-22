package com.zombie_cute.mc.moderndelight.advancement.criterion;

import com.google.gson.JsonObject;
import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * 不需要额外条件的动作型触发器。
 */
public class SimpleActionCriterion extends AbstractCriterion<SimpleActionCriterion.Conditions> {

    private final Identifier id;

    public SimpleActionCriterion(String path) {
        this.id = Identifier.of(ModernDelightMain.MOD_ID, path);
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> true);
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject json, LootContextPredicate playerPredicate,
                                            AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(this.id, playerPredicate);
    }

    public Conditions conditions() {
        return new Conditions(this.id, LootContextPredicate.EMPTY);
    }

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions(Identifier id, LootContextPredicate player) {
            super(id, player);
        }
    }
}
