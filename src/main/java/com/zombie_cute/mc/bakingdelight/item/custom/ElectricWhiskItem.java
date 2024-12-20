package com.zombie_cute.mc.bakingdelight.item.custom;

import com.zombie_cute.mc.bakingdelight.block.custom.abstracts.AbstractBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.DCConsumer;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.Power;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.renderer.ElectricWhiskItemRenderer;
import com.zombie_cute.mc.bakingdelight.recipe.custom.WhiskingRecipe;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import com.zombie_cute.mc.bakingdelight.util.components.ModComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ElectricWhiskItem extends Item implements GeoItem, DCConsumer {
    public ElectricWhiskItem() {
        super(new Item.Settings().maxCount(1).component(ModComponents.POWER,0));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static void addNBTPower(ItemStack itemStack, int value){
        if (itemStack.contains(ModComponents.POWER)){
            int power = itemStack.getOrDefault(ModComponents.POWER,0);
            if (value >= 0){
                power = Math.min(value + power, 500);
            }
            itemStack.set(ModComponents.POWER,power);
        } else {
            itemStack.set(ModComponents.POWER, Math.max(Math.min(value,500),0));
        }
    }
    public static void reduceNBTPower(ItemStack itemStack, int value){
        if (itemStack.contains(ModComponents.POWER)){
            int power = itemStack.getOrDefault(ModComponents.POWER,0);
            if (value >= 0){
                power = Math.max(power - value, 0);
            }
            itemStack.set(ModComponents.POWER,power);
        } else {
            itemStack.set(ModComponents.POWER, 0);
        }
    }

    public static int getNBTPower(ItemStack itemStack){
        return itemStack.getOrDefault(ModComponents.POWER,0);
    }
    public boolean isWorking = false;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WORKING = RawAnimation.begin().thenLoop("working");

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable(AbstractBatteryBlock.TOOLTIP_TEXT).formatted(Formatting.DARK_GRAY));
        tooltip.add(Text.literal(getNBTPower(stack) + "/500 EP").formatted(Formatting.GRAY));
        if(Screen.hasShiftDown()){
            tooltip.add(ModUtil.getShiftText(true));
            tooltip.add(ModUtil.getAltText(false));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.translatable(ModUtil.ELECTRIC_WHISK_1).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ELECTRIC_WHISK_2).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.ELECTRIC_WHISK_3).formatted(Formatting.GOLD));
        } else if (Screen.hasAltDown()) {
            tooltip.add(ModUtil.getShiftText(false));
            tooltip.add(ModUtil.getAltText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(ModUtil.getDCCom("2"));
            tooltip.add(ModUtil.getDCSto("500"));
        } else {
            tooltip.add(ModUtil.getShiftText(false));
            tooltip.add(ModUtil.getAltText(false));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private ElectricWhiskItemRenderer renderer = new ElectricWhiskItemRenderer();
            @Override
            public @NotNull BuiltinModelItemRenderer getGeoItemRenderer() {
                if (this.renderer == null)
                    this.renderer = new ElectricWhiskItemRenderer();
                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            if (state.getAnimatable().isWorking()){
                return state.setAndContinue(WORKING);
            } else return state.setAndContinue(IDLE);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    public void modifyNearbyItemEntities(PlayerEntity player, ItemStack stack, World world) {
        // Get Eye Position
        Vec3d eyePosition = player.getCameraPosVec(1.0F);
        // Get Look Direction
        Vec3d lookDirection = player.getRotationVec(1.0F).normalize();
        // Reach Distance
        double reachDistance = 5.0;
        Vec3d endVec = eyePosition.add(lookDirection.multiply(reachDistance));
        RaycastContext context = new RaycastContext(
                eyePosition, endVec, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player
        );

        HitResult hitResult = world.raycast(context);

        if (hitResult.getType() == HitResult.Type.MISS) {
            return;
        }

        double radius = 5.0;
        List<ItemEntity> itemEntities = world.getEntitiesByClass(
                ItemEntity.class, player.getBoundingBox().expand(radius), entity -> entity != null && entity.isAlive()
        );

        for (ItemEntity itemEntity : itemEntities) {
            Vec3d itemPosition = itemEntity.getPos();

            Vec3d toItem = itemPosition.subtract(eyePosition).normalize();
            if (lookDirection.dotProduct(toItem) > 0.99) {
                tryModifyItemEntity(itemEntity,stack, world,player);
            }
        }
    }

    private void tryModifyItemEntity(ItemEntity itemEntity,ItemStack stack, World world, PlayerEntity player) {
        if (stack.isOf(ModItems.ELECTRIC_WHISK)){
            ItemStack oldStack = itemEntity.getStack();
            boolean spawnBowl = oldStack.getItem() instanceof ModStewItem;
            int power = getNBTPower(stack);
            int count = oldStack.getCount();
            if (count * 2 > power){
                player.sendMessage(Text.translatable(ModUtil.ELECTRIC_WHISK_MSG),true);
                return;
            }
            BlockPos blockPos = itemEntity.getBlockPos();
            Optional<RecipeEntry<WhiskingRecipe>> match = world.getRecipeManager()
                    .getFirstMatch(WhiskingRecipe.Type.INSTANCE, new SingleStackRecipeInput(oldStack),world);
            if (match.isPresent()){
                ItemStack newStack = new ItemStack(match.get().value().getResult(null).getItem(),count);
                if (newStack.getItem() instanceof ModStewItem){
                    player.sendMessage(Text.translatable(ModUtil.ELECTRIC_WHISK_NEED_BOWL),true);
                    return;
                }
                playAnimation(stack);
                world.playSound(null,blockPos.getX(),blockPos.getY(),blockPos.getZ(),
                        ModSounds.ITEM_ELECTRIC_WHISK_WORKING, SoundCategory.PLAYERS,1.0f,1.0f);
                itemEntity.setStack(newStack);
                itemEntity.setCovetedItem();
                Vec3d currentVelocity = itemEntity.getVelocity();
                Vec3d newVelocity = new Vec3d(currentVelocity.x - 0.125 + world.random.nextDouble()/4, 0.2, currentVelocity.z - 0.125 + world.random.nextDouble()/4);
                if (spawnBowl){
                    ItemScatterer.spawn(world,blockPos.getX(),blockPos.getY(),blockPos.getZ(),new ItemStack(Items.BOWL,count));
                }
                itemEntity.setVelocity(newVelocity);
                reduceNBTPower(stack, count * 2);
            }
        }
    }
    public static void playAnimation(ItemStack stack){
        if (stack.getItem() instanceof ElectricWhiskItem item){
            if (!item.isWorking()){
                item.setWorking(true);
            }
            scheduler.schedule(()-> MinecraftClient.getInstance().execute(()-> item.setWorking(false))
                    ,1, TimeUnit.SECONDS);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        modifyNearbyItemEntities(user,user.getStackInHand(hand),world);
        return super.use(world, user, hand);
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    @Override
    public Power getPower(ItemStack stack) {
        Power power = new Power(500);
        power.setPowerValue(getNBTPower(stack));
        return power;
    }

    @Override
    public void addPower(ItemStack stack, int value) {
        addNBTPower(stack,value);
    }

    @Override
    public void reducePower(ItemStack stack, int value) {
        reduceNBTPower(stack,value);
    }
}
