package com.zombie_cute.mc.bakingdelight.block.custom;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.abstracts.AbstractGasCookingStoveBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.BurningGasCookingStoveBlockEntity;
import com.zombie_cute.mc.bakingdelight.util.ModDamageTypes;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BurningGasCookingStoveBlock extends AbstractGasCookingStoveBlock {
    public BurningGasCookingStoveBlock() {
        super(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).luminance(12).nonOpaque());
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for(int i = 0; i < 3; ++i) {
            double d = (double)pos.getX() + world.random.nextDouble();
            double e = (double)pos.getY() +  world.random.nextDouble() * 0.5 + 1;
            double f = (double)pos.getZ() +  world.random.nextDouble();
            world.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.FLAME, d, e, f, 0.0, 0.0, 0.0);
        }
        if (world.getBlockState(pos.up()).getBlock() instanceof LeveledCauldronBlock){
            for (int i = 0;i < 3; ++i){
                double d = (double)pos.getX() + world.random.nextDouble();
                double e = (double)pos.getY() +  world.random.nextDouble() * 0.5 + 2;
                double f = (double)pos.getZ() +  world.random.nextDouble();
                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, d, e, f, -0.0625 + world.random.nextDouble()/8, world.random.nextDouble()/4, -0.0625 + world.random.nextDouble()/8);
            }
        }
    }
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isFireImmune()) {
            entity.setFireTicks(entity.getFireTicks() + 2);
            if (entity.getFireTicks() == 0) {
                entity.setOnFireFor(10);
            }
        }
        entity.damage(ModDamageTypes.of(entity.getWorld(),ModDamageTypes.TURNED_TO_ASHES), 2);
        super.onSteppedOn(world, pos, state, entity);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient){
            world.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f,
                    SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS,
                    1.2f, world.random.nextFloat()+0.5f);
            if (state.get(HAS_BRACKET)){
                world.setBlockState(pos, ModBlocks.GAS_COOKING_STOVE.getDefaultState().with(GasCookingStoveBlock.HAS_BRACKET,true));
            } else {
                world.setBlockState(pos, ModBlocks.GAS_COOKING_STOVE.getDefaultState());
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BurningGasCookingStoveBlockEntity(pos, state);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.BURNING_GAS_COOKING_STOVE_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
