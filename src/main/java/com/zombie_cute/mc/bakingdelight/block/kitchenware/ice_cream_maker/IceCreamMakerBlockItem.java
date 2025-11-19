package com.zombie_cute.mc.bakingdelight.block.kitchenware.ice_cream_maker;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
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
import software.bernie.geckolib.util.RenderUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class IceCreamMakerBlockItem extends BlockItem implements GeoItem {
    public IceCreamMakerBlockItem() {
        super(ModBlocks.ICE_CREAM_MAKER, new FabricItemSettings());
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private static final RawAnimation START = RawAnimation.begin().thenLoop("idle");

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(TextUtil.getAltText(false));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.ICE_CREAM_MAKER)));

        } else if (Screen.hasAltDown()) {
            tooltip.add(TextUtil.getShiftText(false));
            tooltip.add(TextUtil.getAltText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(TextUtil.getACCom("20"));
        } else {
            tooltip.add(TextUtil.getShiftText(false));
            tooltip.add(TextUtil.getAltText(false));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final IceCreamMakerBlockItemRenderer renderer = new IceCreamMakerBlockItemRenderer();
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
        controllers.add(new AnimationController<>(this, state -> state.setAndContinue(START)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }
}
