package com.zombie_cute.mc.bakingdelight.block.food.pizza;

import com.mojang.serialization.MapCodec;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RawPizzaBlock extends AbstractPizzaBlock {
    public static final MapCodec<RawPizzaBlock> CODEC = createCodec((s)->new RawPizzaBlock());
    protected MapCodec<? extends RawPizzaBlock> getCodec() {
        return CODEC;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RawPizzaBlockEntity(pos, state);
    }
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (world.getBlockEntity(pos) instanceof RawPizzaBlockEntity blockEntity) {
            if (!world.isClient) {
                ItemStack itemStack = new ItemStack(ModBlocks.RAW_PIZZA_ITEM);
                itemStack.applyComponentsFrom(blockEntity.createComponentMap());
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
