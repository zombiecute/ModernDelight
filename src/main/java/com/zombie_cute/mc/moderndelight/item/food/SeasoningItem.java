package com.zombie_cute.mc.moderndelight.item.food;

import com.zombie_cute.mc.moderndelight.util.TextUtil;
import com.zombie_cute.mc.moderndelight.util.ModConfig;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeasoningItem extends Item {
    public static final String SEASONING_NBT = "modern_delight_seasoning";
    public static final String SEASONING_PREFIX = "seasoning_";
    private static final int MAX_STORED_SEASONINGS = 15;

    private final List<StatusEffectInstance> effects;

    public SeasoningItem(Settings settings, StatusEffectInstance... effects) {
        super(settings);
        this.effects = List.copyOf(Arrays.asList(effects));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.SEASONING_TIP)));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack seasoningStack = user.getStackInHand(hand);
        Hand otherHand = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
        ItemStack foodStack = user.getStackInHand(otherHand);

        if (foodStack.isEmpty()) {
            return tryEat(world, user, hand, seasoningStack);
        }

        if (world.isClient()) {
            return foodStack.isFood()
                    ? TypedActionResult.success(seasoningStack)
                    : TypedActionResult.pass(seasoningStack);
        }

        return applySeasoning(world, user, hand, seasoningStack, foodStack);
    }

    private TypedActionResult<ItemStack> tryEat(World world, PlayerEntity user, Hand hand, ItemStack stack) {
        if (!this.isFood()) {
            if (!world.isClient()) {
                user.sendMessage(Text.translatable(TextUtil.NEED_FOOD), true);
            }
            return TypedActionResult.pass(stack);
        }
        if (this.getFoodComponent() != null && user.canConsume(this.getFoodComponent().isAlwaysEdible())) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
        return TypedActionResult.pass(stack);
    }

    private TypedActionResult<ItemStack> applySeasoning(World world, PlayerEntity user, Hand hand, ItemStack seasoningStack, ItemStack foodStack) {
        if (!foodStack.isFood()) {
            user.sendMessage(Text.translatable(TextUtil.NEED_FOOD), true);
            return TypedActionResult.pass(seasoningStack);
        }

        Item seasoningItem = seasoningStack.getItem();
        List<Item> currentSeasonings = getSeasonings(foodStack);

        if (foodStack.getItem() instanceof SeasoningItem
                || currentSeasonings.size() >= getMaxSeasoning()
                || currentSeasonings.contains(seasoningItem)) {
            user.sendMessage(Text.translatable(TextUtil.FAILED_SEASONING), true);
            return TypedActionResult.fail(seasoningStack);
        }

        int slot = findFreeSlot(foodStack);
        if (slot < 1) {
            user.sendMessage(Text.translatable(TextUtil.FAILED_SEASONING), true);
            return TypedActionResult.fail(seasoningStack);
        }

        int amount = Math.min(foodStack.getCount(), seasoningStack.getCount());
        if (amount <= 0) {
            return TypedActionResult.fail(seasoningStack);
        }
        ItemStack seasonedStack = foodStack.copy();
        seasonedStack.setCount(amount);
        NbtCompound nbt = seasonedStack.getOrCreateSubNbt(SEASONING_NBT);

        nbt.putString(SEASONING_PREFIX + slot, Registries.ITEM.getId(seasoningItem).toString());

        giveOrDrop(user, seasonedStack);
        if (!user.getAbilities().creativeMode) {
            Item remainder = seasoningItem.getRecipeRemainder();
            if (remainder != null && remainder != Items.AIR) {
                giveOrDrop(user,new ItemStack(remainder,amount));
            }
            foodStack.decrement(amount);
            seasoningStack.decrement(amount);
        }

        user.getItemCooldownManager().set(this, 20);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ITEM_PICKUP,
                SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() + 0.8F);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
    private static void giveOrDrop(PlayerEntity user, ItemStack stack) {
        if (!user.giveItemStack(stack.copy())) {
            user.dropItem(stack, false);
        }
    }
    @Nullable
    private static Item readSeasoning(NbtCompound nbt, int slot) {
        String key = SEASONING_PREFIX + slot;
        if (!nbt.contains(key, NbtElement.STRING_TYPE)) {
            return null;
        }
        Identifier id = Identifier.tryParse(nbt.getString(key));
        if (id == null) {
            return null;
        }
        Item item = Registries.ITEM.getOrEmpty(id).orElse(null);
        return item == Items.AIR ? null : item;
    }

    public static List<Item> getSeasonings(ItemStack stack) {
        NbtCompound nbt = stack.getSubNbt(SEASONING_NBT);
        if (nbt == null) {
            return List.of();
        }
        List<Item> seasonings = new ArrayList<>();
        for (int i = 1; i <= MAX_STORED_SEASONINGS; i++) {
            Item item = readSeasoning(nbt, i);
            if (item != null) {
                seasonings.add(item);
            }
        }
        return seasonings;
    }

    private static int findFreeSlot(ItemStack foodStack) {
        NbtCompound nbt = foodStack.getSubNbt(SEASONING_NBT);
        for (int i = 1; i <= MAX_STORED_SEASONINGS; i++) {
            if (nbt == null || !nbt.contains(SEASONING_PREFIX + i, NbtElement.STRING_TYPE)) {
                return i;
            }
        }
        return -1;
    }

    public static int getMaxSeasoning() {
        try {
            int m = ModConfig.maxSeasonings;
            if (m <= 0) {
                return 5;
            }
            return Math.min(m, MAX_STORED_SEASONINGS);
        } catch (Throwable e) {
            return 5;
        }
    }

    public List<StatusEffectInstance> getEffects() {
        return effects;
    }
}
