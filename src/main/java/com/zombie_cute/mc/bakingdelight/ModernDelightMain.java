package com.zombie_cute.mc.bakingdelight;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.biogas.GasCanisterBlock;
import com.zombie_cute.mc.bakingdelight.block.biogas.GasCanisterBlockEntity;
import com.zombie_cute.mc.bakingdelight.effects.ModEffectsAndPotions;
import com.zombie_cute.mc.bakingdelight.enchantment.ModEnchantments;
import com.zombie_cute.mc.bakingdelight.entity.ModEntities;
import com.zombie_cute.mc.bakingdelight.entity.custom.ButterEntity;
import com.zombie_cute.mc.bakingdelight.entity.custom.CherryBombEntity;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.ModItemGroups;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.networking.NetworkHandler;
import com.zombie_cute.mc.bakingdelight.recipe.ModRecipes;
import com.zombie_cute.mc.bakingdelight.screen.ModScreenHandlers;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.util.ModConfig;
import com.zombie_cute.mc.bakingdelight.util.registry_util.ModBrewingRecipe;
import com.zombie_cute.mc.bakingdelight.util.registry_util.ModCompostingChances;
import com.zombie_cute.mc.bakingdelight.util.registry_util.ModFuels;
import com.zombie_cute.mc.bakingdelight.util.registry_util.ModLootTableModifies;
import com.zombie_cute.mc.bakingdelight.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
		NetworkHandler.registerC2SPacket();
		try {
			ModConfig.INSTANCE.load();
		} catch (Throwable throwable){
			LOGGER.warn("It is recommended to install Yet Another Config Lib to configure {} more easily.", MOD_ID);
		}
		// DispenserBlock
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