package com.zombie_cute.mc.bakingdelight.block.power.alternator.thermal_power;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.ACGenerateAble;
import com.zombie_cute.mc.bakingdelight.screen.custom.FaradayGeneratorScreenHandler;
import com.zombie_cute.mc.bakingdelight.util.ModConfig;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FaradayGeneratorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ACGenerateAble {
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
    public void tick(World world, BlockPos pos, BlockState state) {
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
                        this.isWorking = 0;
                        return;
                    }}
                    case SOUTH -> {if (engineDir != Direction.WEST) {
                        this.isWorking = 0;
                        return;
                    }}
                    case EAST -> {if (engineDir != Direction.SOUTH) {
                        this.isWorking = 0;
                        return;
                    }}
                    case NORTH -> {if (engineDir != Direction.EAST) {
                        this.isWorking = 0;
                        return;
                    }}
                }
                if (engineBlockEntity.isWorking()){
                    this.isWorking = 1;
                } else this.isWorking = 0;
            } else this.isWorking = 0;
        }

    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
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
    public long getEfficiency() {
        if (this.isWorking != 0){
            int value = ModConfig.energyGeneratedByFaradayGenerator;
            if (value < 1){
                return 200;
            } else return value;
        } else return 0;
    }
}
