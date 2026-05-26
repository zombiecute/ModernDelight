package com.zombie_cute.mc.bakingdelight.block.food;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.block_util.Drinkable;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;

import java.util.Arrays;
import java.util.List;

public class GlassCupOfTeaBlock extends Block implements Drinkable {
    public GlassCupOfTeaBlock(int hunger, float saturationModifier, StatusEffectInstance... effects) {
        super(FabricBlockSettings.copyOf(Blocks.REPEATER).sounds(BlockSoundGroup.GLASS));
        this.hunger = hunger;
        this.saturationModifier = saturationModifier;
        this.effects = Arrays.asList(effects.clone());
    }
    public GlassCupOfTeaBlock(int hunger, float saturationModifier) {
        super(FabricBlockSettings.copyOf(Blocks.REPEATER).sounds(BlockSoundGroup.GLASS));
        this.hunger = hunger;
        this.saturationModifier = saturationModifier;
    }
    protected static final VoxelShape SHAPED = Block.createCuboidShape(5.0, 0.0, 5.0, 11, 10, 11);;
    public List<StatusEffectInstance> effects = null;
    public int hunger;
    public float saturationModifier;

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClient && world.random.nextFloat() < fallDistance - 0.5f && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512f) {
            world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.4f, 1.6f + world.random.nextFloat() * 0.2f);
            world.breakBlock(pos, false);
        }
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()){
            if (player.isSneaking()){
                if (player.getMainHandStack().isEmpty()){
                    player.setStackInHand(Hand.MAIN_HAND,new ItemStack(this));
                } else {
                    player.giveItemStack(new ItemStack(this));
                }
                world.playSound(null,pos,SoundEvents.ENTITY_ITEM_PICKUP,SoundCategory.BLOCKS,1.0f,0.8f+world.random.nextFloat());
                world.removeBlock(pos, false);
                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            } else {
                drink(world,player);
                world.setBlockState(pos,ModBlocks.GLASS_CUP.getDefaultState());
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public int getHunger() {
        return hunger;
    }

    @Override
    public float getSaturationModifier() {
        return saturationModifier;
    }

    @Override
    public List<StatusEffectInstance> getEffects() {
        return effects;
    }
    protected boolean canPlaceOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return !floor.getCollisionShape(world, pos).getFace(Direction.UP).isEmpty() || floor.isSideSolidFullSquare(world, pos, Direction.UP);
    }
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return this.canPlaceOnTop(world.getBlockState(blockPos), world, blockPos);
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos,
                                                BlockPos posFrom) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }
}
