package com.zombie_cute.mc.bakingdelight.block.custom;

import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.abstracts.AbstractGasCookingStoveBlock;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GasCookingStoveBlock extends AbstractGasCookingStoveBlock {
    public GasCookingStoveBlock() {
        super(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if(Screen.hasShiftDown()){
            tooltip.add(ModUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.add(Text.translatable(ModUtil.GAS_COOKING_STOVE_1).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.GAS_COOKING_STOVE_2).formatted(Formatting.GOLD));
            tooltip.add(Text.translatable(ModUtil.GAS_COOKING_STOVE_3).formatted(Formatting.GOLD));
        }else {
            tooltip.add(ModUtil.getShiftText(false));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient){
            if (world.random.nextFloat()>0.5){
                world.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f,
                        ModSounds.BLOCK_GAS_COOKING_STOVE_IGNITE, SoundCategory.BLOCKS,
                        2.0f, world.random.nextFloat()+0.5f);
            } else {
                world.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f,
                        SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS,
                        1.0f, world.random.nextFloat()+0.5f);
                if (state.get(HAS_BRACKET)){
                    world.setBlockState(pos, ModBlocks.BURNING_GAS_COOKING_STOVE.getDefaultState().with(BurningGasCookingStoveBlock.HAS_BRACKET,true));
                } else {
                    world.setBlockState(pos, ModBlocks.BURNING_GAS_COOKING_STOVE.getDefaultState());
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
}
