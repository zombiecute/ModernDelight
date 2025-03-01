package com.zombie_cute.mc.bakingdelight.fluid.custom;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.biogas.GasCanisterBlockEntity;
import com.zombie_cute.mc.bakingdelight.fluid.ModAbstractFluid;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.List;

public abstract class LiquefiedBiogasFluid extends ModAbstractFluid {
    @Override
    public Fluid getFlowing() {
        return ModFluid.FLOWING_LIQUEFIED_BIOGAS;
    }

    @Override
    public Fluid getStill() {
        return ModFluid.STILL_LIQUEFIED_BIOGAS;
    }

    @Override
    public Item getBucketItem() {
        return ModItems.LIQUEFIED_BIOGAS_BUCKET;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 3;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModBlocks.LIQUEFIED_BIOGAS_FLUID_BLOCK.getDefaultState().with(Properties.LEVEL_15,getBlockStateLevel(state));
    }

    @Override
    protected boolean hasRandomTicks() {
        return true;
    }

    @Override
    protected float getBlastResistance() {
        return 2.0F;
    }

    @Override
    public void onScheduledTick(World world, BlockPos pos, FluidState state) {
        Box box = new Box(pos).expand(1.2);
        List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class,box);
        for (LivingEntity entity : entities) {
            if (entity != null){
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,40 * 20,0));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON,30 * 20,0));
            }
        }
        if (world.getDimension().ultrawarm()) {
            createExplode(world, pos);
        }
        super.onScheduledTick(world, pos, state);
    }

    @Override
    protected void onRandomTick(World world, BlockPos pos, FluidState state, Random random) {
        if (!world.isClient()){
            for (Direction dir : Direction.values()){
                if (GasCanisterBlockEntity.isDangerBlock(world.getBlockState(pos.offset(dir)).getBlock())){
                    createExplode(world, pos.offset(dir));
                }
            }
            this.onScheduledTick(world, pos, state);
        }
        super.onRandomTick(world, pos, state, random);
    }

    private static void createExplode(World world, BlockPos pos) {
        world.createExplosion(null, pos.getX()+0.5, pos.getY()+1.0, pos.getZ()+0.5,4.0f,true, World.ExplosionSourceType.BLOCK);
        world.breakBlock(pos,false);
    }

    @Override
    protected void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        if(random.nextDouble() < 0.5 && world.getBlockState(pos.up()).isAir()){
            double d = (double)pos.getX() + world.random.nextDouble();
            double e = (double)pos.getY() +  world.random.nextDouble() * 0.5;
            double f = (double)pos.getZ() +  world.random.nextDouble();
            world.addParticle(ParticleTypes.LARGE_SMOKE, d, e, f, 0.0, 0.3 * random.nextDouble(), 0.0);
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0, 0.5 * random.nextDouble(), 0.0);
        }
        super.randomDisplayTick(world, pos, state, random);
    }

    public static class Flowing extends LiquefiedBiogasFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }
        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }
    public static class Still extends LiquefiedBiogasFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 3;
        }
        @Override
        protected void onRandomTick(World world, BlockPos pos, FluidState state, Random random) {
            if (!world.isClient()){
                world.playSound(null,pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS,1.0f,0.5f+random.nextFloat());
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            super.onRandomTick(world, pos, state, random);
        }
        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
