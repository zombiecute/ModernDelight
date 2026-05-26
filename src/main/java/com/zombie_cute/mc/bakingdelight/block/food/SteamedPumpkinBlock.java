package com.zombie_cute.mc.bakingdelight.block.food;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class SteamedPumpkinBlock extends Block {
    public SteamedPumpkinBlock() {
        super(AbstractBlock.Settings.copy(Blocks.CAKE));
        setDefaultState(this.getStateManager().getDefaultState()
                .with(BITES, 0));
    }
    public static final MapCodec<SteamedPumpkinBlock> CODEC = createCodec((s) -> new SteamedPumpkinBlock());
    protected MapCodec<? extends SteamedPumpkinBlock> getCodec() {
        return CODEC;
    }
    public static final IntProperty BITES = IntProperty.of("bites",0,3);
    private static final VoxelShape SHAPED = Block.createCuboidShape(1,0,1,15,8,15);
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable(TextUtil.CAN_PLACE).formatted(Formatting.GRAY));
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (world.isClient) {
            if (player.getStackInHand(hand).getItem() == Items.BOWL) {
                world.playSound(player,pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS,1.2f,world.getRandom().nextFloat()+0.6f);
                return ActionResult.SUCCESS;
            } else if (tryEat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }
        }
        if (player.getStackInHand(hand).getItem() == Items.BOWL){
            int i = state.get(BITES);
            player.getStackInHand(hand).decrement(1);
            player.giveItemStack(ModItems.STEAMED_PUMPKIN_IN_BOWL.getDefaultStack());
            if (i < 3) {
                world.setBlockState(pos, state.with(BITES, i + 1), 3);
            } else {
                world.setBlockState(pos,ModBlocks.WOODEN_PLATE.getDefaultState(),3);
            }
            return ActionResult.SUCCESS;
        }
        return tryEat(world, pos, state, player);
    }
    private static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        } else {
            player.incrementStat(Stats.EAT_CAKE_SLICE);
            player.getHungerManager().add(6, 0.5F);
            int i = state.get(BITES);
            world.emitGameEvent(player, GameEvent.EAT, pos);
            world.playSound(player,pos, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS,2.3f,world.getRandom().nextFloat()+0.6f);
            if (i < 3) {
                world.setBlockState(pos, state.with(BITES, i + 1), 3);
            } else {
                world.setBlockState(pos,ModBlocks.WOODEN_PLATE.getDefaultState(),3);
            }
            return ActionResult.SUCCESS;
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
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }
}
