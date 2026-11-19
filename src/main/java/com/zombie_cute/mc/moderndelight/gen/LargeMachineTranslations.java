package com.zombie_cute.mc.moderndelight.gen;

import com.zombie_cute.mc.moderndelight.block.multiblock.LargeMachineBlock;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

/** Keep invisible cells' names synchronized with the owner's existing translation. */
final class LargeMachineTranslations {
    private LargeMachineTranslations() {}

    static FabricLanguageProvider.TranslationBuilder includeParts(FabricLanguageProvider.TranslationBuilder target) {
        return (key, value) -> {
            target.add(key, value);
            LargeMachineBlock.getRegisteredParts().forEach((owner, part) -> {
                if (owner.getTranslationKey().equals(key)) target.add(part, value);
            });
        };
    }
}
