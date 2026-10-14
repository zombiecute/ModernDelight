package com.zombie_cute.mc.bakingdelight.compat.jade;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.block.trawler.TrawlerBlock;
import com.zombie_cute.mc.bakingdelight.block.trawler.TrawlerBlockEntity;
import com.zombie_cute.mc.bakingdelight.compat.jade.provider.TrawlerComponentProvider;
import net.minecraft.util.Identifier;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {
    public static final Identifier TRAWLER_INTERVAL = Identifier.of(ModernDelightMain.MOD_ID, "trawler_interval");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(TrawlerComponentProvider.INSTANCE, TrawlerBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(TrawlerComponentProvider.INSTANCE, TrawlerBlock.class);
    }
}
