package com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeepFryBasketBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public DeepFryBasketBlock() {
        super(FabricBlockSettings.copyOf(Blocks.DIRT)
                .nonOpaque().breakInstantly().sounds(BlockSoundGroup.LANTERN));
    }
    private static final VoxelShape TYPE_WEST = Block.createCuboidShape(1,0,1,16,8,15);
    private static final VoxelShape TYPE_EAST = Block.createCuboidShape(0,0,1,15,8,15);
    private static final VoxelShape TYPE_SOUTH = Block.createCuboidShape(1,0,0,15,8,15);
    private static final VoxelShape TYPE_NORTH = Block.createCuboidShape(1,0,1,15,8,16);
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.WEST){
            return TYPE_WEST;
        } else if (direction == Direction.EAST) {
            return TYPE_EAST;
        } else if (direction == Direction.NORTH){
            return TYPE_NORTH;
        } else {
            return TYPE_SOUTH;
        }
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction == Direction.WEST){
            return TYPE_WEST;
        } else if (direction == Direction.EAST) {
            return TYPE_EAST;
        } else if (direction == Direction.NORTH){
            return TYPE_NORTH;
        } else {
            return TYPE_SOUTH;
        }
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            if (nbtCompound.contains("Items", 9)) {
                DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(4, ItemStack.EMPTY);
                Inventories.readNbt(nbtCompound, defaultedList);
                for (ItemStack itemStack : defaultedList) {
                    if (!itemStack.isEmpty()) {
                        MutableText mutableText = itemStack.getName().copy();
                        mutableText.append(" x").append(String.valueOf(itemStack.getCount()));
                        tooltip.add(mutableText);
                    }
                }
            }
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()){
            if (world.getBlockEntity(pos) instanceof DeepFryBasketBlockEntity blockEntity){
                ItemStack itemStack = new ItemStack(ModBlocks.DEEP_FRY_BASKET_ITEM);
                blockEntity.setStackNbt(itemStack);
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING,rotation.rotate(state.get(FACING)));
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        if (MiscUtil.isCrowbar(player)){
            Direction dir = state.get(FACING);
            switch (dir){
                case EAST -> world.setBlockState(pos,state.with(FACING,Direction.SOUTH));
                case SOUTH -> world.setBlockState(pos,state.with(FACING,Direction.WEST));
                case WEST -> world.setBlockState(pos,state.with(FACING,Direction.NORTH));
                case NORTH -> world.setBlockState(pos,state.with(FACING,Direction.EAST));
            }
            world.playSound(null,pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.8f);
        } else {
            if (world.getBlockEntity(pos) instanceof DeepFryBasketBlockEntity blockEntity){
                blockEntity.onUse(world,player,hand);
            }
        }
        return ActionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DeepFryBasketBlockEntity(pos,state);
    }
}
