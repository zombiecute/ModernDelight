package com.zombie_cute.mc.moderndelight;

import com.zombie_cute.mc.moderndelight.advancement.ModCriteria;
import com.zombie_cute.mc.moderndelight.advancement.util.StoveClickTracker;
import com.zombie_cute.mc.moderndelight.block.ModBlockEntities;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.block.biogas.GasCanisterBlock;
import com.zombie_cute.mc.moderndelight.block.biogas.GasCanisterBlockEntity;
import com.zombie_cute.mc.moderndelight.block.trawler.TrawlerBaitManager;
import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.moderndelight.enchantment.ModEnchantments;
import com.zombie_cute.mc.moderndelight.entity.ModEntities;
import com.zombie_cute.mc.moderndelight.entity.custom.ButterEntity;
import com.zombie_cute.mc.moderndelight.entity.custom.CherryBombEntity;
import com.zombie_cute.mc.moderndelight.fluid.ModFluid;
import com.zombie_cute.mc.moderndelight.item.ModItemGroups;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import com.zombie_cute.mc.moderndelight.networking.NetworkHandler;
import com.zombie_cute.mc.moderndelight.recipe.ModRecipes;
import com.zombie_cute.mc.moderndelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.moderndelight.sound.ModSounds;
import com.zombie_cute.mc.moderndelight.util.ModConfig;
import com.zombie_cute.mc.moderndelight.util.TemperatureUtil;
import com.zombie_cute.mc.moderndelight.util.registry_util.ModBrewingRecipe;
import com.zombie_cute.mc.moderndelight.util.registry_util.ModCompostingChances;
import com.zombie_cute.mc.moderndelight.util.registry_util.ModFuels;
import com.zombie_cute.mc.moderndelight.util.registry_util.ModLootTableModifies;
import com.zombie_cute.mc.moderndelight.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModernDelightMain implements ModInitializer {

	public static final String MOD_ID = "moderndelight";
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
		NetworkHandler.registerC2SPacket();
		ModCriteria.register();
		try {
			ModConfig.INSTANCE.load();
		} catch (Throwable throwable){
			LOGGER.warn("It is recommended to install Yet Another Config Lib to configure {} more easily.", MOD_ID);
		}
		dispenserBlockBehavior();
		events();
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new TrawlerBaitManager());
	}

	private static void events() {
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ItemStack itemStack = player.getStackInHand(hand);
			if (itemStack.getItem().equals(ModItems.CROWBAR)){
				world.playSound(null,entity.getBlockPos(),
						ModSounds.ITEM_CROWBAR_ATTACK, SoundCategory.PLAYERS,
						0.4f,world.random.nextFloat()/2+0.8f);
			}
			return ActionResult.PASS;
		});
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
			if (success) {
				TemperatureUtil.clearCache();
			}
		});
		ServerPlayConnectionEvents.DISCONNECT.register((server, player) -> {
			StoveClickTracker.clear(server.getPlayer().getUuid());
		});
	}

	private static void dispenserBlockBehavior() {
		DispenserBlock.registerBehavior(ModItems.BUTTER, new ProjectileDispenserBehavior() {
			@Override
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				return new ButterEntity(world,position.getX(),position.getY(),position.getZ());
			}
		});
		DispenserBlock.registerBehavior(ModItems.CHERRY_BOMB, new ProjectileDispenserBehavior() {
			@Override
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				return new CherryBombEntity(world,position.getX(),position.getY(),position.getZ());
			}
		});
		DispenserBlock.registerBehavior(ModBlocks.GAS_CANISTER_ITEM, new ItemDispenserBehavior() {
			@Override
			protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				World world = pointer.getWorld();
				Direction dispenserFacing = pointer.getBlockState().get(DispenserBlock.FACING);
				BlockPos pos = pointer.getPos().offset(dispenserFacing);
				if (world.isAir(pos)) {
					if (dispenserFacing == Direction.UP || dispenserFacing == Direction.DOWN){
						world.setBlockState(pos, ModBlocks.GAS_CANISTER.getDefaultState());
					} else {
						world.setBlockState(pos, ModBlocks.GAS_CANISTER.getDefaultState().with(GasCanisterBlock.FACING,dispenserFacing));
					}
					if (world.getBlockEntity(pos) instanceof GasCanisterBlockEntity blockEntity){
						NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
						if (nbtCompound != null) {
							blockEntity.readNbt(nbtCompound);
						}
					}
					stack.decrement(1);
				}
				return stack;
			}
		});
	}
}
