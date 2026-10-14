package com.zombie_cute.mc.bakingdelight.block.trawler;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TrawlerBlockItem extends BlockItem implements GeoItem {
    public TrawlerBlockItem() {
        super(ModBlocks.TRAWLER, new FabricItemSettings());
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }
    private static final RawAnimation STATIC = RawAnimation.begin().thenLoop("static");
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final TrawlerBlockItemRenderer renderer = new TrawlerBlockItemRenderer();
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> state.setAndContinue(STATIC)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        // 主动检测视线中的水源方块
        BlockHitResult hit = Item.raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (hit.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(stack);
        }
        BlockPos waterPos = hit.getBlockPos();
        // 只接受水源
        if (!world.getFluidState(waterPos).isStill()
                || !world.getFluidState(waterPos)
                .isIn(net.minecraft.registry.tag.FluidTags.WATER)) {
            return TypedActionResult.pass(stack);
        }
        BlockPos targetPos = waterPos.up();
        BlockState targetState = world.getBlockState(targetPos);
        if (!targetState.isReplaceable()) {
            return TypedActionResult.fail(stack);
        }
        BlockHitResult placementHit = new BlockHitResult(Vec3d.ofCenter(targetPos), Direction.UP, targetPos, false);
        ItemPlacementContext placementContext = new ItemPlacementContext(world, player, hand, stack, placementHit);
        BlockState placementState = getBlock().getPlacementState(placementContext);
        if (placementState == null || !placementState.canPlaceAt(world, targetPos)) {
            return TypedActionResult.fail(stack);
        }
        if (!world.isClient) {
            // 使用方块自身的放置状态，保证朝向等属性正常
            world.setBlockState(targetPos, placementState);
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return TypedActionResult.success(stack, world.isClient);
    }
    @Nullable
    @Override
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        World world = context.getWorld();
        BlockPos waterPos = context.getBlockPos();
        FluidState fluid = world.getFluidState(waterPos);
        if (!fluid.isOf(Fluids.WATER) || !fluid.isStill()) {
            return null;
        }
        BlockPos targetPos = waterPos.up();
        // 目标位置必须可替换
        if (!world.getBlockState(targetPos).canReplace(context)) {
            return null;
        }
        // 将放置目标改成水源方块正上方
        BlockHitResult hit = new BlockHitResult(Vec3d.ofCenter(targetPos), Direction.UP, targetPos, false);
        return new ItemPlacementContext(world, context.getPlayer(), context.getHand(), context.getStack(), hit);
    }
}
