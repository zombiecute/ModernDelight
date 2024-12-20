package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.FaradayGeneratorBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.SterlingEngineBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ACGenerateAble;
import com.zombie_cute.mc.bakingdelight.screen.custom.FaradayGeneratorScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FaradayGeneratorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ACGenerateAble {
    public FaradayGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FARADAY_GENERATOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return FaradayGeneratorBlockEntity.this.isWorking;
            }

            @Override
            public void set(int index, int value) {
                FaradayGeneratorBlockEntity.this.isWorking = value;
            }

            @Override
            public int size() {
                return 1;
            }
        };
    }
    protected final PropertyDelegate propertyDelegate;
    private int isWorking = 0;
    public static void tick(World world, BlockPos pos, BlockState state, FaradayGeneratorBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        if (world.getTime() %20L == 0L){
            BlockPos blockPos = pos;
            Direction thisDir = state.get(FaradayGeneratorBlock.FACING);
            switch (thisDir){
                case WEST -> blockPos = pos.south(3).up().east();
                case SOUTH -> blockPos = pos.east(3).up().north();
                case EAST -> blockPos = pos.north(3).up().west();
                case NORTH -> blockPos = pos.west(3).up().south();
            }
            if (world.getBlockEntity(blockPos) instanceof SterlingEngineBlockEntity engineBlockEntity){
                Direction engineDir = world.getBlockState(blockPos).get(SterlingEngineBlock.FACING);
                switch (thisDir){
                    case WEST -> {if (engineDir != Direction.NORTH) {
                        blockEntity.isWorking = 0;
                        return;
                    }}
                    case SOUTH -> {if (engineDir != Direction.WEST) {
                        blockEntity.isWorking = 0;
                        return;
                    }}
                    case EAST -> {if (engineDir != Direction.SOUTH) {
                        blockEntity.isWorking = 0;
                        return;
                    }}
                    case NORTH -> {if (engineDir != Direction.EAST) {
                        blockEntity.isWorking = 0;
                        return;
                    }}
                }
                if (engineBlockEntity.isWorking()){
                    blockEntity.isWorking = 1;
                } else blockEntity.isWorking = 0;
            } else blockEntity.isWorking = 0;
        }

    }
    @Override
    public Text getDisplayName() {
        return ModBlocks.FARADAY_GENERATOR.getName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FaradayGeneratorScreenHandler(syncId,playerInventory,this, this.propertyDelegate);
    }

    @Override
    public int getEfficiency() {
        if (this.isWorking != 0){
            return 100;
        } else return 0;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
