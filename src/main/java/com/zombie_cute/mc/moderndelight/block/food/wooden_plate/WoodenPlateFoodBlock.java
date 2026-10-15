package com.zombie_cute.mc.moderndelight.block.food.wooden_plate;

import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.util.TextUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class WoodenPlateFoodBlock extends BlockWithEntity {
    public final int hunger;
    public final float saturation;
    public WoodenPlateFoodBlock(int hunger, float saturation) {
        super(FabricBlockSettings.copyOf(Blocks.REPEATER).sounds(BlockSoundGroup.WOOD));
        this.hunger = hunger;
        this.saturation = saturation;
    }

    public int getHunger() {
        return hunger;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable(TextUtil.CAN_PLACE).formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }
    public float getSaturation() {
        return saturation;
    }

    private static final VoxelShape SHAPED = Block.createCuboidShape(1,0,1,15,3,15);
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        return tryEat(world, pos, player, hunger, saturation);
    }
    private static ActionResult tryEat(WorldAccess world, BlockPos pos, PlayerEntity player, int hunger, float saturation) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        } else {
            player.incrementStat(Stats.EAT_CAKE_SLICE);
            player.getHungerManager().add(hunger, saturation);
            world.emitGameEvent(player, GameEvent.EAT, pos);
            world.playSound(player,pos, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS,2.3f,world.getRandom().nextFloat()+0.6f);
            world.setBlockState(pos,ModBlocks.WOODEN_PLATE.getDefaultState(),3);
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

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
