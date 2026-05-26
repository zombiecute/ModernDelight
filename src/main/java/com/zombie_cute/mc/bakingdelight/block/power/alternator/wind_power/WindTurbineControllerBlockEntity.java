package com.zombie_cute.mc.bakingdelight.block.power.alternator.wind_power;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.ACGenerateAble;
import com.zombie_cute.mc.bakingdelight.screen.custom.WindTurbineControllerScreenHandler;
import com.zombie_cute.mc.bakingdelight.util.ModConfig;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WindTurbineControllerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ACGenerateAble {
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
    public long getEfficiency() {
        return efficiency;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient){
            return;
        }
        switch (state.get(WindTurbineControllerBlock.FACING)){
            case EAST -> facingBlock = pos.east();
            case SOUTH -> facingBlock = pos.south();
            case WEST -> facingBlock = pos.west();
            case NORTH -> facingBlock = pos.north();
        }
        if (world.getBlockEntity(facingBlock) instanceof FanBladeBlockEntity){
            if (world.getBlockState(facingBlock).get(FanBladeBlock.FACING) == state.get(WindTurbineControllerBlock.FACING)){
                this.isWorking = 1;
                if (world.isThundering()){
                    this.efficiency = (int) Math.max(getMultiplier()*pos.getY() / 3, 3);
                } else if (world.isRaining()){
                    this.efficiency = (int) Math.max(getMultiplier()*pos.getY() / 4, 2);
                } else {
                    this.efficiency = (int) Math.max(getMultiplier()*pos.getY() / 5, 1);
                }
                return;
            }
        }
        this.isWorking = 0;
        this.efficiency = 0;
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        nbt.putInt("wind_turbine_controller.isWorking",this.isWorking);
        nbt.putInt("wind_turbine_controller.efficiency",this.efficiency);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.isWorking = nbt.getInt("wind_turbine_controller.isWorking");
        this.efficiency = nbt.getInt("wind_turbine_controller.efficiency");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
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
    public static float getMultiplier(){
        try {
            float value = ModConfig.windTurbineMultiplier;
            if (value > 0){
                return value;
            } else return 3.0f;
        } catch (Throwable e){
            return 3.0f;
        }
    }
}
