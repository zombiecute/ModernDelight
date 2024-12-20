package com.zombie_cute.mc.bakingdelight.block.custom;

import com.google.common.collect.Lists;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.zombie_cute.mc.bakingdelight.block.entities.PizzaWIPBlockEntity.NEED_CHEESE;

public class WheatDoughBlock extends Block {
    public static final IntProperty CRAFT_STATE = IntProperty.of("craft_state",0,3);
    public WheatDoughBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState()
                .with(CRAFT_STATE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CRAFT_STATE);
    }
    private static final VoxelShape SHAPE_0 = Block.createCuboidShape(3,0,3,13,6,13);
    private static final VoxelShape SHAPE_1 = Block.createCuboidShape(3,0,3,13,4,13);
    private static final VoxelShape SHAPE_2 = Block.createCuboidShape(2,0,2,14,2,14);
    private static final VoxelShape SHAPE_3 = Block.createCuboidShape(1,0,1,15,1,15);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int s = state.get(CRAFT_STATE);
        if (s == 1){
            return SHAPE_1;
        } else if (s == 2) {
            return SHAPE_2;
        } else if (s == 3) {
            return SHAPE_3;
        } else {
            return SHAPE_0;
        }
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int s = state.get(CRAFT_STATE);
        if (s == 1){
            return SHAPE_1;
        } else if (s == 2) {
            return SHAPE_2;
        } else if (s == 3) {
            return SHAPE_3;
        } else {
            return SHAPE_0;
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient){
            return ActionResult.SUCCESS;
        }
        Item item = player.getMainHandStack().getItem();
        int currentState = state.get(CRAFT_STATE);
        if (isKneadingStick(item) && currentState < 3){
            double random = world.random.nextDouble();
            if (random < 0.4){
                world.setBlockState(pos,state.with(CRAFT_STATE,currentState + 1));
            }
            player.getMainHandStack().damage(1, player, EquipmentSlot.MAINHAND);
            world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_HONEY_BLOCK_BREAK, SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.1f,true);
            return ActionResult.SUCCESS;
        } else if (currentState == 3){
            if (item == ModItems.CHEESE){
                player.getMainHandStack().split(1);
                world.playSound(pos.getX(),pos.getY(),pos.getZ(), SoundEvents.BLOCK_HONEY_BLOCK_PLACE, SoundCategory.BLOCKS,1.0f,world.random.nextFloat()+0.1f,true);
                world.setBlockState(pos, ModBlocks.PIZZA_WIP.getDefaultState());
            } else {
                player.sendMessage(Text.translatable(NEED_CHEESE),true);
            }
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }
    private boolean isKneadingStick(@NotNull Item item) {
        ItemStack stack = item.getDefaultStack();
        ArrayList<Item> list = Lists.newArrayList();
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.ROLLING_PINS)) {
            list.add(registryEntry.value());
        }
        return list.contains(stack.getItem());
    }
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
