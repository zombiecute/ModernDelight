package com.zombie_cute.mc.bakingdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.TeslaCoilBlockEntity;
import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeslaCoilBlock extends BlockWithEntity implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty SHOW_PARTICLE = BooleanProperty.of("show_particle");
    public static final BooleanProperty IS_OVERLOADED = BooleanProperty.of("is_overloaded");
    private static final VoxelShape UP = Block.createCuboidShape(5,2,5,11,16,11);
    private static final VoxelShape DOWN = Block.createCuboidShape(5,0,5,11,14,11);
    private static final VoxelShape EAST = Block.createCuboidShape(0,5,5,14,11,11);
    private static final VoxelShape WEST = Block.createCuboidShape(2,5,5,16,11,11);
    private static final VoxelShape SOUTH = Block.createCuboidShape(5,5,0,11,11,14);
    private static final VoxelShape NORTH = Block.createCuboidShape(5,5,2,11,11,16);
    public static final MapCodec<TeslaCoilBlock> CODEC = createCodec((settings -> new TeslaCoilBlock()));
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    public TeslaCoilBlock() {
        super(AbstractBlock.Settings.copy(Blocks.STONE).sounds(BlockSoundGroup.NETHERITE).luminance(state -> state.get(IS_OVERLOADED) ? 0 : 7));
        setDefaultState(this.getStateManager().getDefaultState()
                .with(WATERLOGGED,false).with(FACING,Direction.DOWN).with(SHOW_PARTICLE,false).with(IS_OVERLOADED,true));
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if(Screen.hasShiftDown()){
            tooltip.add(ModUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.translatable(ModUtil.TESLA_COIL_1).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.TESLA_COIL_2).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.TESLA_COIL_3).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.TESLA_COIL_4).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.TESLA_COIL_5).formatted(Formatting.GOLD));
        } else {
            tooltip.add(ModUtil.getShiftText(false));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return Boolean.TRUE.equals(state.get(WATERLOGGED)) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case EAST -> {
                return EAST;
            }
            case SOUTH -> {
                return SOUTH;
            }
            case WEST -> {
                return WEST;
            }
            case NORTH -> {
                return NORTH;
            }
            case UP -> {
                return UP;
            }
            default -> {
                return DOWN;
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        // 展示粒子效果
        if (state.get(SHOW_PARTICLE)){
            for(int i = 0; i < 2; ++i) {
                for (int j = 1; j <= 8; j++){
                    world.addParticle(ParticleTypes.END_ROD, x + j, y, z, 0.0, 0.0, 0.0);
                    world.addParticle(ParticleTypes.END_ROD, x, y + j, z, 0.0, 0.0, 0.0);
                    world.addParticle(ParticleTypes.END_ROD, x, y, z + j, 0.0, 0.0, 0.0);
                    world.addParticle(ParticleTypes.END_ROD, x - j, y, z, 0.0, 0.0, 0.0);
                    world.addParticle(ParticleTypes.END_ROD, x, y - j, z, 0.0, 0.0, 0.0);
                    world.addParticle(ParticleTypes.END_ROD, x, y, z - j, 0.0, 0.0, 0.0);
                }
            }
        }
        if (state.get(IS_OVERLOADED)){
            for (int i =0 ; i < 3; ++i){
                world.addParticle(new DustParticleEffect(DustParticleEffect.RED,1.5f), x + world.random.nextDouble()/2, y + 0.2, z + world.random.nextDouble()/2, 0.0, 0.0, 0.0);
            }
        } else {
            Box box = new Box(pos).expand(1.2);
            List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class,box);
            for (LivingEntity entity : entities) {
                if (entity != null){
                    for (int j = 0; j < 3; j++){
                        world.addParticle(ParticleTypes.WAX_OFF,
                                x-0.5+world.random.nextFloat(),y,z-0.5+world.random.nextFloat(),
                                (world.random.nextFloat()-0.5)*3 ,1.0 + world.random.nextFloat(),(world.random.nextFloat()-0.5)*3);
                        world.addParticle(ParticleTypes.WAX_OFF,
                                entity.getX()-0.5+world.random.nextFloat(),entity.getY()-0.5 + world.random.nextFloat(),entity.getZ()-0.5+world.random.nextFloat(),
                                (world.random.nextFloat()-0.5)*3 ,3.5 + world.random.nextFloat(),(world.random.nextFloat()-0.5)*3);
                    }
                }
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case EAST -> {
                return EAST;
            }
            case SOUTH -> {
                return SOUTH;
            }
            case WEST -> {
                return WEST;
            }
            case NORTH -> {
                return NORTH;
            }
            case UP -> {
                return UP;
            }
            default -> {
                return DOWN;
            }
        }
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, SHOW_PARTICLE, IS_OVERLOADED);
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockState blockState = this.getDefaultState();
        WorldView worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction[] directions = ctx.getPlacementDirections();
        for (Direction direction : directions) {
            if (direction.getAxis().isHorizontal()){
                Direction direction2 = direction.getOpposite();
                blockState = blockState.with(FACING, direction2);
                if (blockState.canPlaceAt(worldView, blockPos)) {
                    return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            } else {
                blockState = blockState.with(FACING, direction);
                if (blockState.canPlaceAt(worldView, blockPos)) {
                    return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos,
                                                BlockPos posFrom) {
        if (Boolean.TRUE.equals(state.get(WATERLOGGED))) {
            world.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING,rotation.rotate(state.get(FACING)));
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TeslaCoilBlockEntity(pos,state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        if (world.getBlockEntity(pos) instanceof TeslaCoilBlockEntity entity){
            player.openHandledScreen(entity);
        }
        return ActionResult.SUCCESS;
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type,ModBlockEntities.TESLA_COIL_BLOCK_ENTITY, TeslaCoilBlockEntity::tick);
    }
}
