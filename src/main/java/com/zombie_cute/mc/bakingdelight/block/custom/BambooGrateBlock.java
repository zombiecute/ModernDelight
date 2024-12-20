package com.zombie_cute.mc.bakingdelight.block.custom;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.BambooGrateBlockEntity;
import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BambooGrateBlock extends BlockWithEntity {
    public BambooGrateBlock() {
        super(AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS).nonOpaque());
        setDefaultState(this.getStateManager().getDefaultState()
                .with(LAYER, 1).with(COVERED,false));
    }
    public static final MapCodec<BambooGrateBlock> CODEC = createCodec(settings -> new BambooGrateBlock());
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    public static final IntProperty LAYER = IntProperty.of("layer",1,4);
    public static final BooleanProperty COVERED = BooleanProperty.of("covered");
    public static final VoxelShape SHAPED_1 = Block.createCuboidShape(1,0,1,15,4,15);
    public static final VoxelShape SHAPED_2 = Block.createCuboidShape(1,0,1,15,8,15);
    public static final VoxelShape SHAPED_3 = Block.createCuboidShape(1,0,1,15,12,15);
    public static final VoxelShape SHAPED_4 = Block.createCuboidShape(1,0,1,15,16,15);
    public static final VoxelShape SHAPED_5 = Block.createCuboidShape(1,0,1,15,20,15);
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAYER,COVERED);
    }
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if(Screen.hasShiftDown()){
            tooltip.add(ModUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.translatable(ModUtil.BAMBOO_STEAMER_1).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.BAMBOO_STEAMER_2).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.BAMBOO_STEAMER_3).formatted(Formatting.GOLD));
        } else {
            tooltip.add(ModUtil.getShiftText(false));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(COVERED)){
            return switch (state.get(LAYER)){
                case 2 -> SHAPED_3;
                case 3 -> SHAPED_4;
                case 4 -> SHAPED_5;
                default -> SHAPED_2;
            };
        } else {
            return switch (state.get(LAYER)){
                case 2 -> SHAPED_2;
                case 3 -> SHAPED_3;
                case 4 -> SHAPED_4;
                default -> SHAPED_1;
            };
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(COVERED)){
            return switch (state.get(LAYER)){
                case 2 -> SHAPED_3;
                case 3 -> SHAPED_4;
                case 4 -> SHAPED_5;
                default -> SHAPED_2;
            };
        } else {
            return switch (state.get(LAYER)){
                case 2 -> SHAPED_2;
                case 3 -> SHAPED_3;
                case 4 -> SHAPED_4;
                default -> SHAPED_1;
            };
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient){
            int layer = state.get(LAYER);
            if (state.get(COVERED)){
                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModBlocks.BAMBOO_COVER));
            }
            if (layer > 1){
                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModBlocks.BAMBOO_GRATE,layer-1));
            }
            if (world.getBlockEntity(pos) instanceof BambooGrateBlockEntity entity){
                ItemScatterer.spawn(world,pos,entity);
            }
        }
        super.onBreak(world,pos,state,player);
        return state;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.scheduleBlockTick(pos,this,1);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(LAYER) == 4 && state.get(COVERED) && !world.getBlockState(pos.up()).isAir()){
            if (world.getBlockState(pos.up()).getBlock().equals(ModBlocks.BAMBOO_COVER)){
                world.setBlockState(pos,state.with(COVERED,true));
                world.setBlockState(pos.up(),Blocks.AIR.getDefaultState());
                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModBlocks.BAMBOO_COVER));
                return;
            }
            world.setBlockState(pos,state.with(COVERED,false));
            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModBlocks.BAMBOO_COVER));
            world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_BAMBOO_WOOD_BREAK, SoundCategory.BLOCKS,1.0f,world.random.nextFloat() + 0.5f);
        } else if (state.get(LAYER) == 4 && !state.get(COVERED) && world.getBlockState(pos.up()).getBlock().equals(ModBlocks.BAMBOO_COVER)) {
            world.setBlockState(pos,state.with(COVERED,true));
            world.setBlockState(pos.up(),Blocks.AIR.getDefaultState());
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (!world.isClient){
            if(world.getBlockEntity(pos) instanceof BambooGrateBlockEntity blockEntity){
                if (ModUtil.isCrowbar(player)){
                    if (state.get(COVERED)){
                        world.setBlockState(pos,state.with(COVERED,false));
                        ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModBlocks.BAMBOO_COVER));
                        world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_BAMBOO_WOOD_BREAK, SoundCategory.BLOCKS,1.0f,world.random.nextFloat() + 0.5f);
                    } else if (state.get(LAYER) > 1) {
                        for (int i = 1;i <= 4;i++){
                            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),blockEntity.getStack(4 * state.get(LAYER) - i));
                            blockEntity.setStack(4 * state.get(LAYER) - i,ItemStack.EMPTY);
                        }
                        world.setBlockState(pos,state.with(LAYER,state.get(LAYER) - 1));
                        ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModBlocks.BAMBOO_GRATE));
                        world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_BAMBOO_WOOD_BREAK, SoundCategory.BLOCKS,1.0f,world.random.nextFloat() + 0.5f);
                    } else {
                        for (int i = 0;i < 4;i++){
                            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),blockEntity.getStack(i));
                        }
                        world.breakBlock(pos,true);
                    }
                } else if (player.getStackInHand(hand).getItem().equals(ModBlocks.BAMBOO_COVER.asItem())){
                    if (!state.get(COVERED)){
                        if (state.get(LAYER) < 4){
                            world.setBlockState(pos,state.with(COVERED,true));
                            player.getStackInHand(hand).decrement(1);
                            world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_BAMBOO_WOOD_PLACE, SoundCategory.BLOCKS,1.0f,world.random.nextFloat() + 0.5f);
                        } else if (world.getBlockState(pos.up()).isAir()){
                            world.setBlockState(pos,state.with(COVERED,true));
                            player.getStackInHand(hand).decrement(1);
                            world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_BAMBOO_WOOD_PLACE, SoundCategory.BLOCKS,1.0f,world.random.nextFloat() + 0.5f);
                        }
                    }
                } else if (player.getStackInHand(hand).getItem().equals(ModBlocks.BAMBOO_GRATE.asItem())) {
                    if (state.get(LAYER) < 4){
                        if (state.get(LAYER) == 3 && !world.getBlockState(pos.up()).isAir() && state.get(COVERED)){
                            return ActionResult.SUCCESS;
                        }
                        world.setBlockState(pos,state.with(LAYER,state.get(LAYER) + 1));
                        player.getStackInHand(hand).decrement(1);
                        world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_BAMBOO_WOOD_PLACE, SoundCategory.BLOCKS,1.0f,world.random.nextFloat() + 0.5f);
                    } else if (world.getBlockState(pos.up()).isAir()){
                        player.getStackInHand(hand).decrement(1);
                        world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_BAMBOO_WOOD_PLACE, SoundCategory.BLOCKS,1.0f,world.random.nextFloat() + 0.5f);
                        if (!state.get(COVERED)){
                            world.setBlockState(pos.up(),ModBlocks.BAMBOO_GRATE.getDefaultState());
                        } else {
                            world.setBlockState(pos,state.with(COVERED,false));
                            world.setBlockState(pos.up(),ModBlocks.BAMBOO_GRATE.getDefaultState().with(COVERED,true));
                        }
                    }
                } else {
                    player.openHandledScreen(blockEntity);
                }
                blockEntity.markDirty();
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BambooGrateBlockEntity(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type,ModBlockEntities.BAMBOO_GRATE_BLOCK_ENTITY, BambooGrateBlockEntity::tick);
    }
}
