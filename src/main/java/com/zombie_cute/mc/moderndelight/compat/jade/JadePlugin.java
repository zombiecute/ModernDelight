package com.zombie_cute.mc.moderndelight.compat.jade;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import com.zombie_cute.mc.moderndelight.block.kitchenware.juice_extractor.JuiceExtractorBlock;
import com.zombie_cute.mc.moderndelight.block.kitchenware.juice_extractor.JuiceExtractorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.FaradayGeneratorBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.FaradayGeneratorBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.SterlingEngineBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.thermal_power.SterlingEngineBlockEntity;
import com.zombie_cute.mc.moderndelight.block.power.alternator.wind_power.WindTurbineControllerBlock;
import com.zombie_cute.mc.moderndelight.block.power.alternator.wind_power.WindTurbineControllerBlockEntity;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBlock;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBlockEntity;
import com.zombie_cute.mc.moderndelight.compat.jade.provider.ACGeneratorComponentProvider;
import com.zombie_cute.mc.moderndelight.compat.jade.provider.JuiceExtractorComponentProvider;
import com.zombie_cute.mc.moderndelight.compat.jade.provider.SterlingEngineComponentProvider;
import com.zombie_cute.mc.moderndelight.compat.jade.provider.TrawlerComponentProvider;
import net.minecraft.util.Identifier;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    public static final Identifier TRAWLER = Identifier.of(ModernDelightMain.MOD_ID, "trawler");
    public static final Identifier STERLING_ENGINE = Identifier.of(ModernDelightMain.MOD_ID, "sterling_engine");
    public static final Identifier AC_GENERATOR = Identifier.of(ModernDelightMain.MOD_ID, "ac_generator");
    public static final Identifier JUICE_EXTRACTOR = Identifier.of(ModernDelightMain.MOD_ID, "juice_extractor");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(TrawlerComponentProvider.INSTANCE, TrawlerBlockEntity.class);
        registration.registerBlockDataProvider(SterlingEngineComponentProvider.INSTANCE, SterlingEngineBlockEntity.class);
        registration.registerBlockDataProvider(ACGeneratorComponentProvider.INSTANCE, FaradayGeneratorBlockEntity.class);
        registration.registerBlockDataProvider(ACGeneratorComponentProvider.INSTANCE, WindTurbineControllerBlockEntity.class);
        registration.registerBlockDataProvider(JuiceExtractorComponentProvider.INSTANCE, JuiceExtractorBlockEntity.class);

    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(TrawlerComponentProvider.INSTANCE, TrawlerBlock.class);
        registration.registerBlockComponent(SterlingEngineComponentProvider.INSTANCE, SterlingEngineBlock.class);
        registration.registerBlockComponent(ACGeneratorComponentProvider.INSTANCE, FaradayGeneratorBlock.class);
        registration.registerBlockComponent(ACGeneratorComponentProvider.INSTANCE, WindTurbineControllerBlock.class);
        registration.registerBlockComponent(JuiceExtractorComponentProvider.INSTANCE, JuiceExtractorBlock.class);

    }
}
