package com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;

import java.util.function.Consumer;

public class SterlingEngineBlockItem extends BlockItem implements GeoItem {
    public SterlingEngineBlockItem() {
        super(ModBlocks.STERLING_ENGINE, new Item.Settings().maxCount(1));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private final SterlingEngineBlockItemRenderer renderer = new SterlingEngineBlockItemRenderer();
            @Override
            public @NotNull BuiltinModelItemRenderer getGeoItemRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> state.setAndContinue(RawAnimation.begin())));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
