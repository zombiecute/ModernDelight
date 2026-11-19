package com.zombie_cute.mc.moderndelight.block.kitchenware.decor;

import com.zombie_cute.mc.moderndelight.util.block_util.ModelShapes;
import com.zombie_cute.mc.moderndelight.util.block_util.ShapeProfile;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.util.TextUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.tick.OrderedTick;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class GlassCupBlock extends Block implements Waterloggable {
    private static final ShapeProfile COLLISION_PROFILE = ShapeProfile.vanilla()
            // Each stem locates one cup; simplify that cup without filling the space between cups.
            .filterElements(element -> element.width() == 1 && element.height() == 2
                    && element.depth() == 1 && element.y() == 1)
            .simplifyElements(GlassCupBlock::solidCups)
            .build();

    private static VoxelShape solidCups(List<ShapeProfile.Element> stems) {
        VoxelShape result = VoxelShapes.empty();
        for (var stem : stems) {
            float x = stem.x() + .5f, z = stem.z() + .5f;
            result = VoxelShapes.union(result,
                    createCuboidShape(x - 2.5, 0, z - 2.5, x + 2.5, 1, z + 2.5),
                    createCuboidShape(x - .5, 1, z - .5, x + .5, 3, z + .5),
                    createCuboidShape(x - 3, 3, z - 3, x + 3, 10, z + 3));
        }
        return result.simplify();
    }

    public static VoxelShape singleCupShape() {
        return ModelShapes.get(ModBlocks.GLASS_CUP.getDefaultState(), COLLISION_PROFILE);
    }

    public GlassCupBlock() {
        super(FabricBlockSettings.copyOf(ModBlocks.GLASS_BOWL));
        this.setDefaultState(getStateManager().getDefaultState().with(CUPS, 1).with(WATERLOGGED, false));
    }
    public static final IntProperty CUPS = IntProperty.of("cups", 1, 4);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()){
            int cups = state.get(CUPS);
            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModBlocks.GLASS_CUP,cups));
        }
        super.onBreak(world, pos, state, player);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable(TextUtil.CAN_PLACE).formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.with(CUPS, Math.min(4, blockState.get(CUPS) + 1));
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
            return Objects.requireNonNull(super.getPlacementState(ctx)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
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
        if (Boolean.TRUE.equals(state.get(WATERLOGGED))) {
            world.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER, pos));
        }
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(CUPS) < 4 || super.canReplace(state, context);
    }
    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!world.isClient && world.random.nextFloat() < fallDistance - 0.5f && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512f) {
            world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.4f, 1.6f + world.random.nextFloat() * 0.2f);
            world.breakBlock(pos, true);
        }
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ModelShapes.get(state, COLLISION_PROFILE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ModelShapes.get(state, COLLISION_PROFILE);
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CUPS, WATERLOGGED);
    }

}
