package com.zombie_cute.mc.bakingdelight.advancement.criterion;

import com.google.gson.JsonObject;
import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.List;

public class FastStoveClickCriterion extends AbstractCriterion<FastStoveClickCriterion.Conditions> {

    public static final Identifier ID =
            Identifier.of(ModernDelightMain.MOD_ID, "fast_stove_click");

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, List<Long> clickTimestamps) {
        this.trigger(player, conditions -> conditions.matches(clickTimestamps));
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject json, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        int requiredClicks = JsonHelper.getInt(json, "required_clicks", 10);
        double windowSeconds = JsonHelper.getFloat(json, "window_seconds", 3.0f);
        long windowMillis = (long) (windowSeconds * 1000);
        return new Conditions(playerPredicate, requiredClicks, windowMillis);
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final int requiredClicks;
        private final long windowMillis;

        public Conditions(LootContextPredicate player, int requiredClicks, long windowMillis) {
            super(ID, player);
            this.requiredClicks = requiredClicks;
            this.windowMillis = windowMillis;
        }
        public static Conditions create(int requiredClicks, double windowSeconds) {
            return new Conditions(LootContextPredicate.EMPTY, requiredClicks, (long) (windowSeconds * 1000));
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject json = super.toJson(predicateSerializer);
            json.addProperty("required_clicks", this.requiredClicks);
            json.addProperty("window_seconds", this.windowMillis / 1000.0);
            return json;
        }

        public boolean matches(List<Long> clickTimestamps) {
            if (clickTimestamps.size() < this.requiredClicks) {
                return false;
            }

            int newestClickIndex = clickTimestamps.size() - 1;
            int firstRequiredClickIndex = clickTimestamps.size() - this.requiredClicks;
            long elapsedMillis = clickTimestamps.get(newestClickIndex) - clickTimestamps.get(firstRequiredClickIndex);
            return elapsedMillis >= 0 && elapsedMillis <= this.windowMillis;
        }
    }
}
