package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.FanBladeBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.WindTurbineControllerBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ACGenerateAble;
import com.zombie_cute.mc.bakingdelight.screen.custom.WindTurbineControllerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WindTurbineControllerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ACGenerateAble {
    public WindTurbineControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WIND_TURBINE_CONTROLLER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> WindTurbineControllerBlockEntity.this.isWorking;
                    case 1 -> WindTurbineControllerBlockEntity.this.pos.getY();
                    case 2 -> WindTurbineControllerBlockEntity.this.efficiency;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> WindTurbineControllerBlockEntity.this.isWorking = value;
                    case 2 -> WindTurbineControllerBlockEntity.this.efficiency = value;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }
    protected final PropertyDelegate propertyDelegate;
    private int isWorking = 0;
    private int efficiency = 0;
    private BlockPos facingBlock = pos;
    @Override
    public int getEfficiency() {
        return efficiency;
    }

    public static void tick(World world, BlockPos pos, BlockState state, WindTurbineControllerBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        switch (state.get(WindTurbineControllerBlock.FACING)){
            case EAST -> blockEntity.facingBlock = pos.east();
            case SOUTH -> blockEntity.facingBlock = pos.south();
            case WEST -> blockEntity.facingBlock = pos.west();
            case NORTH -> blockEntity.facingBlock = pos.north();
        }
        if (world.getBlockEntity(blockEntity.facingBlock) instanceof FanBladeBlockEntity){
            if (world.getBlockState(blockEntity.facingBlock).get(FanBladeBlock.FACING) == state.get(WindTurbineControllerBlock.FACING)){
                blockEntity.isWorking = 1;
                if (world.isThundering()){
                    blockEntity.efficiency = Math.max(pos.getY() / 3, 3);
                } else if (world.isRaining()){
                    blockEntity.efficiency = Math.max(pos.getY() / 4, 2);
                } else {
                    blockEntity.efficiency = Math.max(pos.getY() / 5, 1);
                }
                return;
            }
        }
        blockEntity.isWorking = 0;
        blockEntity.efficiency = 0;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        nbt.putInt("wind_turbine_controller.isWorking",this.isWorking);
        nbt.putInt("wind_turbine_controller.efficiency",this.efficiency);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        this.isWorking = nbt.getInt("wind_turbine_controller.isWorking");
        this.efficiency = nbt.getInt("wind_turbine_controller.efficiency");
    }
    @Override
    public Text getDisplayName() {
        return ModBlocks.WIND_TURBINE_CONTROLLER.getName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new WindTurbineControllerScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
