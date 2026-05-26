package com.zombie_cute.mc.bakingdelight;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.bakingdelight.enchantment.ModEnchantments;
import com.zombie_cute.mc.bakingdelight.entity.ModEntities;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.ModItemGroups;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import com.zombie_cute.mc.bakingdelight.recipe.ModRecipes;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.util.ModConfig;
import com.zombie_cute.mc.bakingdelight.components.ModComponents;
import com.zombie_cute.mc.bakingdelight.util.registry_util.ModBrewingRecipe;
import com.zombie_cute.mc.bakingdelight.util.registry_util.ModCompostingChances;
import com.zombie_cute.mc.bakingdelight.util.registry_util.ModFuels;
import com.zombie_cute.mc.bakingdelight.util.registry_util.ModLootTableModifies;
import com.zombie_cute.mc.bakingdelight.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.BlockPlacementDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModernDelightMain implements ModInitializer {

	public static final String MOD_ID = "bakingdelight";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerItemGroup();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModLootTableModifies.modifyLootTables();
		ModEntities.registerModEntities();
		ModSounds.registerModSounds();
		ModScreenHandlers.registerScreenHandlers();
		ModRecipes.registerRecipes();
		ModEnchantments.registerModEnchantments();
		ModFuels.registerFuels();
		ModCompostingChances.registerCompostingChances();
		ModBrewingRecipe.registerModBrewingRecipe();
		ModEffectsAndPotions.registerModEffectsAndPotions();
		ModFluid.registerModFluid();
		ModWorldGeneration.generateModWorldGen();
		NetworkHandler.registerPayloadTypes();
		NetworkHandler.registerC2SPacket();
		ModComponents.registerModComponents();

		try {
			ModConfig.INSTANCE.load();
		} catch (Throwable throwable){
			LOGGER.warn("It is recommended to install Yet Another Config Lib to configure {} more easily.", MOD_ID);
		}
		// DispenserBlock
		DispenserBlock.registerBehavior(ModItems.BUTTER, new ProjectileDispenserBehavior(ModItems.BUTTER));
		DispenserBlock.registerBehavior(ModItems.CHERRY_BOMB, new ProjectileDispenserBehavior(ModItems.CHERRY_BOMB));
		DispenserBlock.registerBehavior(ModBlocks.GAS_CANISTER_ITEM, new BlockPlacementDispenserBehavior());
		// Events
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ItemStack itemStack = player.getStackInHand(hand);
			if (itemStack.getItem().equals(ModItems.CROWBAR)){
				world.playSound(null,entity.getBlockPos(),
						ModSounds.ITEM_CROWBAR_ATTACK, SoundCategory.PLAYERS,
						0.4f,world.random.nextFloat()/2+0.8f);
			}
			return ActionResult.PASS;
		});

	}
}