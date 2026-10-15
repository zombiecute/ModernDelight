package com.zombie_cute.mc.moderndelight.block.food.clay_pot;

import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.util.TextUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ClayPotFoodBlock extends BlockWithEntity {
    private final int hunger;
    private final float saturation;
    public ClayPotFoodBlock(int hunger, float saturation) {
        super(FabricBlockSettings.copyOf(Blocks.REPEATER)
                .mapColor(MapColor.BROWN).nonOpaque());
        this.hunger = hunger;
        this.saturation = saturation;
    }
    private static final VoxelShape SHAPED = Block.createCuboidShape(0,0,0,16,5,16);
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable(TextUtil.CAN_PLACE).formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }
    public int getHunger() {
        return hunger;
    }

    public float getSaturation() {
        return saturation;
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
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
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
            world.setBlockState(pos, ModBlocks.CLAY_POT.getDefaultState(),3);
            return ActionResult.SUCCESS;
        }
    }
}
