package com.zombie_cute.mc.bakingdelight.block.kitchenware;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AdvanceFurnaceBlock extends BlockWithEntity implements BlockEntityProvider {
    public AdvanceFurnaceBlock() {
        super(FabricBlockSettings.copyOf(Blocks.BRICKS).luminance(state -> state.get(BURNING) ? 15 : 0));
    }
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty BURNING = BooleanProperty.of("burning");

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(BURNING, false);
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, BURNING);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING,rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AdvanceFurnaceBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AdvanceFurnaceBlockEntity){
                ItemScatterer.spawn(world ,pos, (AdvanceFurnaceBlockEntity)blockEntity);
                if (((AdvanceFurnaceBlockEntity) blockEntity).getExperience() != 0){
                    ExperienceOrbEntity xp =
                            new ExperienceOrbEntity(world,pos.getX(),pos.getY(),pos.getZ(),
                                    ((AdvanceFurnaceBlockEntity) blockEntity).getExperience());
                    world.spawnEntity(xp);
                    world.updateComparators(pos,this);
                }
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(BURNING)){
            double d = (double)pos.getX() + 0.5;
            double e = pos.getY();
            double f = (double)pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.get(FACING);
            Direction.Axis axis = direction.getAxis();
            double h = random.nextDouble() * 0.6 - 0.3;
            double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52 : h;
            double j = random.nextDouble() * 6.0 / 16.0;
            double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0);
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient){
            NamedScreenHandlerFactory screenHandlerFactory = ((AdvanceFurnaceBlockEntity) world.getBlockEntity(pos));
            if (isBakingTrayBlock(player)){
                ((AdvanceFurnaceBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))).onUse(world,state);
            } else if (MiscUtil.isCrowbar(player)){
                Direction dir = state.get(FACING);
                switch (dir){
                    case EAST -> world.setBlockState(pos,state.with(FACING,Direction.SOUTH));
                    case SOUTH -> world.setBlockState(pos,state.with(FACING,Direction.WEST));
                    case WEST -> world.setBlockState(pos,state.with(FACING,Direction.NORTH));
                    case NORTH -> world.setBlockState(pos,state.with(FACING,Direction.EAST));
                }
                world.playSound(null,pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.8f);
            } else if (screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }
    private boolean isBakingTrayBlock(PlayerEntity player) {
        if (player.getMainHandStack().getItem().equals(ModBlocks.BAKING_TRAY.asItem())){
            player.getMainHandStack().decrement(1);
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.ADVANCE_FURNACE_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1, blockEntity));
    }
}
