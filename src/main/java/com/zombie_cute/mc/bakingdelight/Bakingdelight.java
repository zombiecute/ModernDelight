package com.zombie_cute.mc.bakingdelight;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.*;
import com.zombie_cute.mc.bakingdelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.bakingdelight.entity.ModEntities;
import com.zombie_cute.mc.bakingdelight.entity.custom.ButterEntity;
import com.zombie_cute.mc.bakingdelight.entity.custom.CherryBombEntity;
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
import com.zombie_cute.mc.bakingdelight.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bakingdelight implements ModInitializer {

	public static final String MOD_ID = "bakingdelight";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier UPDATE_INVENTORY_PACKET_ID = new Identifier(MOD_ID,"update_inventory");
	public static final Identifier SPAWN_XP_PACKET_ID = new Identifier(MOD_ID,"spawn_xp");
	public static final Identifier CHANGE_BLOCK_ENTITY_DATA_PACKET_ID = new Identifier(MOD_ID,"change_block_entity_data");

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
		ModEffectsAndPotions.registerModEffectsAndPotions();
		ModFluid.registerModFluid();
		ModWorldGeneration.generateModWorldGen();


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
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			ItemStack itemStack = player.getStackInHand(hand);
			if (itemStack.getItem().equals(ModItems.CROWBAR)){
				world.playSound(entity.getX(),entity.getY(),entity.getZ(),
						ModSounds.ITEM_CROWBAR_ATTACK, SoundCategory.PLAYERS,
						0.4f,world.random.nextFloat()/2+0.8f,true);
			}
			return ActionResult.PASS;
		});

		ServerPlayNetworking.registerGlobalReceiver(UPDATE_INVENTORY_PACKET_ID, this::handleUpdateInventoryPacket);
        LOGGER.info("Registering C2S receiver with id {}", UPDATE_INVENTORY_PACKET_ID);
		ServerPlayNetworking.registerGlobalReceiver(SPAWN_XP_PACKET_ID, this::handleSpawnXPPacket);
		LOGGER.info("Registering C2S receiver with id {}", SPAWN_XP_PACKET_ID);
		ServerPlayNetworking.registerGlobalReceiver(CHANGE_BLOCK_ENTITY_DATA_PACKET_ID, this::handleSetGasPumpStatePacket);
		LOGGER.info("Registering C2S receiver with id {}", CHANGE_BLOCK_ENTITY_DATA_PACKET_ID);
	}
	private void handleSetGasPumpStatePacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		BlockPos pos = buf.readBlockPos();
		int[] array = buf.readIntArray();
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
	private void handleUpdateInventoryPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		BlockPos pos = buf.readBlockPos();
		ItemStack itemStack = buf.readItemStack();
		server.execute(() -> {
			BlockEntity blockEntity = player.getWorld().getBlockEntity(pos);
			if (blockEntity instanceof CuisineTableBlockEntity cuisineTableBlockEntity) {
				cuisineTableBlockEntity.getItems().set(2, itemStack);
				blockEntity.markDirty();
			} else if (blockEntity instanceof ElectriciansDeskBlockEntity electriciansDeskBlockEntity){
				electriciansDeskBlockEntity.getItems().set(8,itemStack);
			}
		});
	}
	private void handleSpawnXPPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		BlockPos pos = buf.readBlockPos();
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