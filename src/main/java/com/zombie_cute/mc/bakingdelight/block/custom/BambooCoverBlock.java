package com.zombie_cute.mc.bakingdelight.block.custom;

import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class BambooCoverBlock extends Block {
    public BambooCoverBlock() {
        super(AbstractBlock.Settings.copy(Blocks.BAMBOO_PLANKS).nonOpaque());
    }
    public static final VoxelShape SHAPED = Block.createCuboidShape(1,0,1,15,4,15);

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
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPED;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (ModUtil.isCrowbar(player)){
            if (!world.isClient){
                world.breakBlock(pos,true);
            }
            return ActionResult.SUCCESS;
        } else return ActionResult.FAIL;
    }
}
