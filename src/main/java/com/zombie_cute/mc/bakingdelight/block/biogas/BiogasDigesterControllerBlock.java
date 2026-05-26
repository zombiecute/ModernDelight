package com.zombie_cute.mc.bakingdelight.block.biogas;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.TextUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BiogasDigesterControllerBlock extends BlockWithEntity{
    public BiogasDigesterControllerBlock() {
        super(AbstractBlock.Settings.copy(Blocks.LAPIS_BLOCK));
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BiogasDigesterControllerBlockEntity(pos,state);
    }
    public static final MapCodec<BiogasDigesterControllerBlock> CODEC = createCodec((settings -> new BiogasDigesterControllerBlock()));
    @Override
    protected MapCodec<? extends BiogasDigesterControllerBlock> getCodec() {
        return CODEC;
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient){
            if (world.getBlockEntity(pos) instanceof BiogasDigesterControllerBlockEntity blockEntity){
                if (blockEntity.getGasValue() > 1000){
                    blockEntity.createExplode(world,pos);
                } else if (blockEntity.getGasValue() > 500){
                    world.setBlockState(pos.down(), ModBlocks.LIQUEFIED_BIOGAS_FLUID_BLOCK.getDefaultState());
                }
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if(Screen.hasShiftDown()){
            tooltip.add(TextUtil.getShiftText(true));
            tooltip.add(Text.literal(" "));
            tooltip.addAll(TextUtil.generateToolTip(Text.translatable(TextUtil.BDC)));
        } else {
            tooltip.add(TextUtil.getShiftText(false));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient){
            NamedScreenHandlerFactory screenHandlerFactory = ((BiogasDigesterControllerBlockEntity) world.getBlockEntity(pos));
        if (screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type, ModBlockEntities.BIOGAS_DIGESTER_CONTROLLER_BLOCK_ENTITY, BiogasDigesterControllerBlockEntity::tick);
    }
}
