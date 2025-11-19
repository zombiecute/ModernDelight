package com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.AdvanceFurnaceBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.OvenBlock;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SterlingEngineBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPED = Block.createCuboidShape(0,0,0,16,3,16);
    public static final BooleanProperty SMALL_SOUND = BooleanProperty.of("small_sound");
    public static final BooleanProperty IS_WORKING = BooleanProperty.of("is_working");

    public SterlingEngineBlock() {
        super(FabricBlockSettings.copyOf(Blocks.IRON_BARS));
        getStateManager().getDefaultState().with(SMALL_SOUND, false).with(IS_WORKING,false);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.STERLING_ENGINE)));

        } else {
            tooltip.add(TextUtil.getShiftText(false));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(SterlingEngineBlock.IS_WORKING)){
            Direction dir = state.get(SterlingEngineBlock.FACING);
            double varX = 0;
            double varZ = 0;
            switch (dir){
                case NORTH -> {
                    varX = 0.5;
                    varZ = -0.5;
                }
                case SOUTH -> {
                    varX = 0.5;
                    varZ = 1.5;
                }
                case EAST -> {
                    varX = 1.5;
                    varZ = 0.5;
                }
                case WEST -> {
                    varX = -0.5;
                    varZ = 0.5;
                }
            }
            double x = pos.getX() + varX;
            double y = pos.getY() + 0.3;
            double z = pos.getZ() + varZ;
            for (int i = 0;i<5;i++){
                world.addParticle(ParticleTypes.POOF,x,y,z,
                        (random.nextFloat()-0.5)/3,
                        (random.nextFloat()-0.5)/5,
                        (random.nextFloat()-0.5)/3);
            }
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(SMALL_SOUND,false).with(IS_WORKING,false);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Block block = world.getBlockState(pos.down()).getBlock();
        return block instanceof AbstractFurnaceBlock ||
                block instanceof OvenBlock ||
                block instanceof AdvanceFurnaceBlock;
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (MiscUtil.isPlayerHoldingCrowbar(player)){
            if (world.isClient){
                return ActionResult.SUCCESS;
            }
            if (state.get(SMALL_SOUND)){
                world.setBlockState(pos,state.with(SMALL_SOUND,false));
                world.playSound(null,pos, BlockSoundGroup.GRASS.getBreakSound(), SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.8f);
                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),Items.SPONGE.getDefaultStack());
            } else {
                Direction dir = state.get(FACING);
                switch (dir){
                    case EAST -> world.setBlockState(pos,state.with(FACING,Direction.SOUTH));
                    case SOUTH -> world.setBlockState(pos,state.with(FACING,Direction.WEST));
                    case WEST -> world.setBlockState(pos,state.with(FACING,Direction.NORTH));
                    case NORTH -> world.setBlockState(pos,state.with(FACING,Direction.EAST));
                }
            }
            world.playSound(null,pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.8f);
            return ActionResult.CONSUME;
        } else if (player.getStackInHand(hand).getItem() == Items.SPONGE && !state.get(SMALL_SOUND)) {
            if (world.isClient){
                return ActionResult.SUCCESS;
            } else {
                player.getStackInHand(hand).decrement(1);
                world.setBlockState(pos,state.with(SMALL_SOUND,true));
                world.playSound(null,pos, BlockSoundGroup.GRASS.getPlaceSound(), SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.8f);
                return ActionResult.CONSUME;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, SMALL_SOUND, IS_WORKING);
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING,rotation.rotate(state.get(FACING)));
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SterlingEngineBlockEntity(pos,state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.STERLING_ENGINE_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
