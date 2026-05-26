package com.zombie_cute.mc.bakingdelight.enchantment;

import com.zombie_cute.mc.bakingdelight.ModernDelightMain;
import com.zombie_cute.mc.bakingdelight.enchantment.custom.FineGrindingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final Enchantment FINE_GRINDING = registerEnchantment("fine_grinding",new FineGrindingEnchantment());
    private static Enchantment registerEnchantment(String name,Enchantment enchant){
        return Registry.register(Registries.ENCHANTMENT,new Identifier(ModernDelightMain.MOD_ID,name),enchant);
    }
    public static void registerModEnchantments(){
        ModernDelightMain.LOGGER.info("Registering Mod Enchantments for " + ModernDelightMain.MOD_ID);
    }
}
