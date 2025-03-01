package com.zombie_cute.mc.bakingdelight.item.food.instant_noodles;

import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.enums.SpecialIngredient;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CookedPortablePotItem extends Item {
    public CookedPortablePotItem() {
        super(new FabricItemSettings().maxCount(1).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1f).build()));
    }
    public static boolean isUnhealthy(ItemStack potItem){
        NbtCompound nbt = potItem.getNbt();
        if (nbt != null && nbt.contains("noodles_data")){
            NbtCompound noodleNBT = nbt.getCompound("noodles_data");
            ItemStack noodles = ItemStack.fromNbt(noodleNBT);
            return PackagedInstantNoodlesItem.isUnhealthy(noodles);
        }
        return false;
    }
    @Override
    public Text getName(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains("noodles_data")){
            NbtCompound noodleNBT = nbt.getCompound("noodles_data");
            ItemStack noodles = ItemStack.fromNbt(noodleNBT);
            SpecialIngredient special = PackagedInstantNoodlesItem.getSpecialIngredient(noodles);
            if (special != null){
                return Text.translatable(special.toTranslationKey());
            } else if (isUnhealthy(stack)) {
                return Text.translatable(MiscUtil.NOODLE_UNHEALTHY);
            }
        }
        return super.getName(stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains("noodles_data")){
            NbtCompound noodleNBT = nbt.getCompound("noodles_data");
            ItemStack noodles = ItemStack.fromNbt(noodleNBT);
            PackagedInstantNoodlesItem.setToolTipFromNoodles(noodles,tooltip);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        MiscUtil.applyFoodEffects(stack,user);
        if (user instanceof PlayerEntity player){
            if (!world.isClient()) {
                NbtCompound nbt = stack.getNbt();
                if (nbt != null && nbt.contains("noodles_data")){
                    NbtCompound noodleNBT = nbt.getCompound("noodles_data");
                    ItemStack noodles = ItemStack.fromNbt(noodleNBT);
                    SpecialIngredient specialIngredient = PackagedInstantNoodlesItem.getSpecialIngredient(noodles);
                    if (specialIngredient != null){
                        player.getHungerManager().add(8, 1.5F);
                    } else {
                        List<Item> items = PackagedInstantNoodlesItem.getStacksFromNbt(noodles);
                        if (items != null){
                            for (Item item : items){
                                if (item.isFood()){
                                    player.eatFood(world, item.getDefaultStack());
                                } else {
                                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,200,0));
                                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER,300,0));
                                }
                            }
                        }
                    }
                }
                player.eatFood(world, ModItems.FRIED_NOODLES.getDefaultStack());
                player.setStackInHand(player.getActiveHand(),ModItems.PORTABLE_POT.getDefaultStack());
            }
        }
        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.canConsume(false)){
            user.setCurrentHand(hand);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }
    public static ItemStack createCookedPot(List<Ingredient> ingredients){
        ItemStack noodles = new ItemStack(ModItems.PACKAGED_INSTANT_NOODLES);
        NbtCompound noodleNbt = noodles.getOrCreateSubNbt("instant_noodles_ingredients");
        for (int i = 1; i <= ingredients.size();i++){
            try {
                String name = Registries.ITEM.getId(ingredients.get(i-1).getMatchingStacks()[0].getItem()).toString();
                noodleNbt.putString("ingredients_"+i,name);
            } catch (Exception ignored){}
        }
        ItemStack cookedPot = new ItemStack(ModItems.COOKED_PORTABLE_POT);
        NbtCompound potNbt = cookedPot.getOrCreateNbt();
        NbtCompound temp = new NbtCompound();
        noodles.writeNbt(temp);
        potNbt.put("noodles_data",temp);
        return cookedPot;
    }
}
