package com.zombie_cute.mc.bakingdelight;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.*;
import com.zombie_cute.mc.bakingdelight.effects.ModEffects;
import com.zombie_cute.mc.bakingdelight.effects.ModPotions;
import com.zombie_cute.mc.bakingdelight.entity.ModEntities;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.ModItemGroups;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.recipe.ModRecipes;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.util.ModBrewingRecipe;
import com.zombie_cute.mc.bakingdelight.util.ModCompostingChances;
import com.zombie_cute.mc.bakingdelight.util.ModFuels;
import com.zombie_cute.mc.bakingdelight.util.ModLootTableModifies;
import com.zombie_cute.mc.bakingdelight.util.components.ModComponents;
import com.zombie_cute.mc.bakingdelight.util.custom_pay_load.ChangeBlockEntityDataPayLoad;
import com.zombie_cute.mc.bakingdelight.util.custom_pay_load.SpawnXPPayLoad;
import com.zombie_cute.mc.bakingdelight.util.custom_pay_load.UpdateInventoryPayLoad;
import com.zombie_cute.mc.bakingdelight.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bakingdelight implements ModInitializer {

	public static final String MOD_ID = "bakingdelight";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier UPDATE_INVENTORY_PACKET_ID = Identifier.of(MOD_ID,"update_inventory");
	public static final Identifier SPAWN_XP_PACKET_ID = Identifier.of(MOD_ID,"spawn_xp");
	public static final Identifier CHANGE_BLOCK_ENTITY_DATA_PACKET_ID = Identifier.of(MOD_ID,"change_block_entity_data");

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
		ModFuels.registerFuels();
		ModCompostingChances.registerCompostingChances();
		ModBrewingRecipe.registerModBrewingRecipe();
		ModPotions.registerModPotions();
		ModEffects.registerModEffects();
		ModFluid.registerModFluid();
		ModWorldGeneration.generateModWorldGen();
		ModComponents.init();

		DispenserBlock.registerBehavior(ModItems.BUTTER, new ProjectileDispenserBehavior(ModItems.BUTTER));
		DispenserBlock.registerBehavior(ModItems.CHERRY_BOMB, new ProjectileDispenserBehavior(ModItems.CHERRY_BOMB));

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ItemStack itemStack = player.getStackInHand(hand);
			if (itemStack.getItem().equals(ModItems.CROWBAR)){
				world.playSound(entity.getX(),entity.getY(),entity.getZ(),
						ModSounds.ITEM_CROWBAR_ATTACK, SoundCategory.PLAYERS,
						0.4f,world.random.nextFloat()/2+0.8f,true);
			}
			return ActionResult.PASS;
		});

		PayloadTypeRegistry.playC2S().register(UpdateInventoryPayLoad.ID, UpdateInventoryPayLoad.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(UpdateInventoryPayLoad.ID,(payload, context) ->
				handleUpdateInventoryPacket(context.server(),context.player(),payload.pos(),payload.item(),payload.count()));
        LOGGER.info("Registering C2S receiver with id {}", UPDATE_INVENTORY_PACKET_ID);

		PayloadTypeRegistry.playC2S().register(SpawnXPPayLoad.ID, SpawnXPPayLoad.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(SpawnXPPayLoad.ID,(payload, context) ->
				handleSpawnXPPacket(context.server(),context.player(),payload.pos()));
		LOGGER.info("Registering C2S receiver with id {}", SPAWN_XP_PACKET_ID);

		PayloadTypeRegistry.playC2S().register(ChangeBlockEntityDataPayLoad.ID, ChangeBlockEntityDataPayLoad.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(ChangeBlockEntityDataPayLoad.ID,(payload, context) ->
				handleChangeBlockEntityData(context.server(),context.player(),payload.pos(),payload.array()));
		LOGGER.info("Registering C2S receiver with id {}", CHANGE_BLOCK_ENTITY_DATA_PACKET_ID);
	}
	private void handleChangeBlockEntityData(MinecraftServer server, ServerPlayerEntity player, BlockPos pos, byte[] array) {
		server.execute(() -> {
			BlockEntity blockEntity = player.getWorld().getBlockEntity(pos);
			if (blockEntity instanceof TeslaCoilBlockEntity teslaCoilBlockEntity){
				switch (array[0]){
					case 1 -> teslaCoilBlockEntity.setShowParticle(false);
					case 2 -> teslaCoilBlockEntity.setShowParticle(true);
				}
			} else if (blockEntity instanceof ACDCConverterBlockEntity gasPumpBlockEntity) {
				switch (array[0]){
					case 1 -> gasPumpBlockEntity.addWorkSpeed(1);
					case 2 -> gasPumpBlockEntity.reduceWorkSpeed(1);
				}
				switch (array[1]){
					case 1 -> gasPumpBlockEntity.setACMode(false);
					case 2 -> gasPumpBlockEntity.setACMode(true);
				}
				gasPumpBlockEntity.markDirty();
			} else if (blockEntity instanceof ElectriciansDeskBlockEntity electriciansDeskBlockEntity) {
				switch (array[0]){
					case 1 -> electriciansDeskBlockEntity.setCanCraft(true);
					case 2 -> electriciansDeskBlockEntity.setCanCraft(false);
					case 3 -> {
						electriciansDeskBlockEntity.removeStack(6, 1);
						electriciansDeskBlockEntity.removeStack(7, 1);
						electriciansDeskBlockEntity.setOccupied(true);
					}
					case 4 -> electriciansDeskBlockEntity.setOccupied(false);
				}
			} else if (blockEntity instanceof IceCreamMakerBlockEntity iceCreamMakerBlockEntity) {
				switch (array[0]){
					case 1 -> iceCreamMakerBlockEntity.changeIceCream1();
					case 2 -> iceCreamMakerBlockEntity.changeIceCream2();
					case 3 -> iceCreamMakerBlockEntity.changeIceCream3();
				}
			} else if (blockEntity instanceof CuisineTableBlockEntity cuisineTableBlockEntity) {
				switch (array[0]){
					case 1 -> cuisineTableBlockEntity.setCanOpen(true);
				}
			}
		});
	}
	private void handleUpdateInventoryPacket(MinecraftServer server, ServerPlayerEntity player, BlockPos pos, String item, int count) {
		server.execute(() -> {
			BlockEntity blockEntity = player.getWorld().getBlockEntity(pos);
			ItemStack itemStack;
			try {
				itemStack = new ItemStack(Registries.ITEM.get(Identifier.of(item)), count);
			} catch (Exception e){
				itemStack = ItemStack.EMPTY;
			}
			if (blockEntity instanceof CuisineTableBlockEntity cuisineTableBlockEntity) {
				cuisineTableBlockEntity.getItems().set(2, itemStack);
				blockEntity.markDirty();
			} else if (blockEntity instanceof ElectriciansDeskBlockEntity electriciansDeskBlockEntity){
				electriciansDeskBlockEntity.getItems().set(8,itemStack);
			}
		});
	}
	private void handleSpawnXPPacket(MinecraftServer server, ServerPlayerEntity player, BlockPos pos) {
		server.execute(() -> {
			World world = player.getWorld();
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof AdvanceFurnaceBlockEntity entity) {
				if (entity.getExperience() != 0) {
					ExperienceOrbEntity xp = new ExperienceOrbEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), entity.getExperience());
					world.spawnEntity(xp);
					entity.setExperience(0);
				}
			} else if (blockEntity instanceof OvenBlockEntity entity) {
				if (entity.getExperience() != 0) {
					ExperienceOrbEntity xp = new ExperienceOrbEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), entity.getExperience());
					world.spawnEntity(xp);
					entity.setExperience(0);
				}
			} else if (blockEntity instanceof FreezerBlockEntity entity) {
				if (entity.getExperience() != 0) {
					ExperienceOrbEntity xp = new ExperienceOrbEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), entity.getExperience());
					world.spawnEntity(xp);
					entity.setExperience(0);
				}
			}
		});
	}
}