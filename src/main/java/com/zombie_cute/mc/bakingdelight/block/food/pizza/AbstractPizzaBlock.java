package com.zombie_cute.mc.bakingdelight.block.food.pizza;

import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractPizzaBlock extends BlockWithEntity {
    public AbstractPizzaBlock() {
        super(FabricBlockSettings.copyOf(Blocks.REPEATER).burnable().sounds(BlockSoundGroup.HONEY)
                .jumpVelocityMultiplier(0.5f).mapColor(MapColor.YELLOW).nonOpaque());
    }
    private static final VoxelShape SHAPED = Block.createCuboidShape(1,0,1,15,1,15);
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
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable(MiscUtil.CAN_PLACE).formatted(Formatting.GRAY));
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            if (nbtCompound.contains("Items", 9)) {
                DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(5, ItemStack.EMPTY);
                Inventories.readNbt(nbtCompound, defaultedList);
                tooltip.add(Text.translatable(MiscUtil.PIZZA_INGREDIENTS).formatted(Formatting.DARK_GRAY));
                for (ItemStack itemStack : defaultedList) {
                    if (!itemStack.isEmpty()) {
                        tooltip.add(
                                Text.translatable(
                                        String.valueOf(itemStack.getTranslationKey())
                                ).formatted(Formatting.GRAY)
                        );
                    }
                }
            }
        }
        super.appendTooltip(stack, world, tooltip, options);
    }

}
