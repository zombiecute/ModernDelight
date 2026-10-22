package com.zombie_cute.mc.moderndelight.advancement.criterion;

import com.google.gson.JsonObject;
import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
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
 * 当某个机器或动作产出一件物品时触发。
 *
 * <p>可选条件 {@code item} 用于限定产出的物品；不写则任意产出都能触发。</p>
 */
public class ItemProducedCriterion extends AbstractCriterion<ItemProducedCriterion.Conditions> {

    private final Identifier id;

    public ItemProducedCriterion(String path) {
        this.id = Identifier.of(ModernDelightMain.MOD_ID, path);
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack) {
        this.trigger(player, conditions -> conditions.matches(stack));
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject json, LootContextPredicate playerPredicate,
                                            AdvancementEntityPredicateDeserializer predicateDeserializer) {
        Item item = json.has("item")
                ? Registries.ITEM.get(new Identifier(JsonHelper.getString(json, "item")))
                : null;
        return new Conditions(this.id, playerPredicate, item);
    }

    public Conditions conditions(ItemConvertible item) {
        return new Conditions(this.id, LootContextPredicate.EMPTY, item.asItem());
    }

    public static class Conditions extends AbstractCriterionConditions {
        @Nullable
        private final Item item;

        public Conditions(Identifier id, LootContextPredicate player, @Nullable Item item) {
            super(id, player);
            this.item = item;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject json = super.toJson(predicateSerializer);
            if (this.item != null) {
                json.addProperty("item", Registries.ITEM.getId(this.item).toString());
            }
            return json;
        }

        public boolean matches(ItemStack stack) {
            return this.item == null || stack.isOf(this.item);
        }
    }
}
