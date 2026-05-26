package com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.screen.custom.FaradayGeneratorScreenHandler;
import com.zombie_cute.mc.bakingdelight.util.ModConfig;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.ACGenerateAble;
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
    public static void tick(World world, BlockPos pos, BlockState state, FaradayGeneratorBlockEntity b) {
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
                        b.isWorking = 0;
                        return;
                    }}
                    case SOUTH -> {if (engineDir != Direction.WEST) {
                        b.isWorking = 0;
                        return;
                    }}
                    case EAST -> {if (engineDir != Direction.SOUTH) {
                        b.isWorking = 0;
                        return;
                    }}
                    case NORTH -> {if (engineDir != Direction.EAST) {
                        b.isWorking = 0;
                        return;
                    }}
                }
                if (engineBlockEntity.getCachedState().get(SterlingEngineBlock.IS_WORKING)){
                    b.isWorking = 1;
                } else b.isWorking = 0;
            } else b.isWorking = 0;
        }

    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
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
    public static long getEnergyEfficiency(){
        try {
            int val = ModConfig.energyGeneratedByFaradayGenerator;
            if (val >= 1){
                return val;
            } else return 200;
        } catch (Throwable e){
            return 200;
        }
    }
    @Override
    public long getEfficiency() {
        if (isWorking != 0){
            return getEnergyEfficiency();
        } else return 0;
    }
}
