package com.zombie_cute.mc.moderndelight.advancement.criterion;

import com.google.gson.JsonObject;
import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

/**
 * 当工业发酵机完成一次发酵时触发。
 *
 * <p>可以按产出物品或产出流体限定。</p>
 */
public class FermentCriterion extends AbstractCriterion<FermentCriterion.Conditions> {

    public static final Identifier ID = Identifier.of(ModernDelightMain.MOD_ID, "ferment");

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, @Nullable Fluid outputFluid, ItemStack outputStack) {
        this.trigger(player, conditions -> conditions.matches(outputFluid, outputStack));
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject json, LootContextPredicate playerPredicate,
                                            AdvancementEntityPredicateDeserializer predicateDeserializer) {
        Item item = json.has("item")
                ? Registries.ITEM.get(new Identifier(JsonHelper.getString(json, "item")))
                : null;
        Identifier fluidId = json.has("fluid")
                ? new Identifier(JsonHelper.getString(json, "fluid"))
                : null;
        return new Conditions(playerPredicate, item, fluidId);
    }

    public static class Conditions extends AbstractCriterionConditions {
        @Nullable
        private final Item item;
        @Nullable
        private final Identifier fluidId;

        public Conditions(LootContextPredicate player, @Nullable Item item, @Nullable Identifier fluidId) {
            super(ID, player);
            this.item = item;
            this.fluidId = fluidId;
        }

        public static Conditions item(ItemConvertible item) {
            return new Conditions(LootContextPredicate.EMPTY, item.asItem(), null);
        }

        public static Conditions fluid(Fluid fluid) {
            return new Conditions(LootContextPredicate.EMPTY, null, Registries.FLUID.getId(fluid));
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject json = super.toJson(predicateSerializer);
            if (this.item != null) {
                json.addProperty("item", Registries.ITEM.getId(this.item).toString());
            }
            if (this.fluidId != null) {
                json.addProperty("fluid", this.fluidId.toString());
            }
            return json;
        }

        public boolean matches(@Nullable Fluid outputFluid, ItemStack outputStack) {
            if (this.item != null && !outputStack.isOf(this.item)) {
                return false;
            }
            if (this.fluidId != null) {
                return outputFluid != null && this.fluidId.equals(Registries.FLUID.getId(outputFluid));
            }
            return true;
        }
    }
}
