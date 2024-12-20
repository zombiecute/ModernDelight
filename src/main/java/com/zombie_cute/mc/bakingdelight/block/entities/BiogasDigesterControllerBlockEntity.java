package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.screen.custom.BiogasDigesterControllerScreenHandler;
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

public class BiogasDigesterControllerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos> {
    protected final PropertyDelegate propertyDelegate;
    private int yCounter = 1;
    private int maxXCounter = 0;
    private int maxZCounter = 0;
    private int minXCounter = 0;
    private int minZCounter = 0;
    private int checked = 0;
    private int size = 0;
    private int gasValue = 0;
    private int maxGasValue = 0;
    private int shortGasValue = 0;
    private int isSplit = 0;
    public BiogasDigesterControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIOGAS_DIGESTER_CONTROLLER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> BiogasDigesterControllerBlockEntity.this.checked;
                    case 1 -> BiogasDigesterControllerBlockEntity.this.size;
                    case 2 -> BiogasDigesterControllerBlockEntity.this.shortGasValue;
                    case 3 -> BiogasDigesterControllerBlockEntity.this.isSplit;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("biogas_digester_controller.gasValue",gasValue);
        nbt.putInt("biogas_digester_controller.maxGasValue",maxGasValue);
    }

    public boolean isChecked(){
        return checked != 0;
    }
    public int getGasValue(){
        return gasValue;
    }
    public void addGas(int value){
        gasValue += value;
    }
    public void reduceGas(int value){
        if (gasValue > value){
            gasValue -= value;
        } else {
            gasValue = 0;
        }
    }
    public int getCurrentSize(){
        return size;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        gasValue = nbt.getInt("biogas_digester_controller.gasValue");
        maxGasValue = nbt.getInt("biogas_digester_controller.maxGasValue");
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public Text getDisplayName() {
        return ModBlocks.BIOGAS_DIGESTER_CONTROLLER.getName();
    }
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BiogasDigesterControllerScreenHandler(syncId, playerInventory,this,this.propertyDelegate);
    }
    private int time = 60;
    public static void tick(World world, BlockPos pos, BlockState state, BiogasDigesterControllerBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        blockEntity.time--;
        if (blockEntity.time == 10) {
            if (blockEntity.check(world)){
                blockEntity.checked = 1;
            } else {
                blockEntity.checked = 0;
                blockEntity.size = 0;
            }
            blockEntity.restAll();
        }
        if (blockEntity.checked==1){
            blockEntity.maxGasValue = blockEntity.size * 1000;
            blockEntity.markDirty();
        } else {
            blockEntity.maxGasValue = 0;
            blockEntity.markDirty();
        }
        if (blockEntity.gasValue >= Short.MAX_VALUE){
            blockEntity.shortGasValue = blockEntity.gasValue/19;
            blockEntity.isSplit = 1;
        } else {
            blockEntity.shortGasValue = blockEntity.gasValue;
            blockEntity.isSplit = 0;
        }
        if (blockEntity.gasValue > blockEntity.maxGasValue){
            if (blockEntity.time == 1){
                world.createExplosion(null,pos.getX(),pos.getY(),pos.getZ(),3.5f,true, World.ExplosionSourceType.BLOCK);
                blockEntity.markDirty();
            }
        }
        if (blockEntity.time <= 0){
            blockEntity.time = 60;
        }
    }
    private boolean check(World world){
        BlockPos maxPos;
        BlockPos minPos;
        int length;
        int width;
        if (world.getBlockState(pos.down()).isAir()){
            int maxValue = 5;
            do {
                yCounter++;
                if (!world.getBlockState(pos.down(yCounter)).isAir()){
                    yCounter--;
                    break;
                }
            } while (yCounter <= maxValue);
            if (!world.getBlockState(pos.down(yCounter+1)).isOpaque()){
                return false;
            } else {
                do {
                    maxXCounter++;
                    if (!world.getBlockState(pos.east(maxXCounter).down(yCounter)).isAir()){
                        maxXCounter--;
                        break;
                    }
                } while (maxXCounter <= maxValue);
                if (!world.getBlockState(pos.east(maxXCounter+1).down(yCounter)).isOpaque()){
                    return false;
                } else {
                    do {
                        maxZCounter++;
                        if (!world.getBlockState(pos.south(maxZCounter).down(yCounter)).isAir()){
                            maxZCounter--;
                            break;
                        }
                    } while (maxZCounter <= maxValue);
                    if (!world.getBlockState(pos.south(maxZCounter+1).down(yCounter)).isOpaque()){
                        return false;
                    } else {
                        maxPos = new BlockPos(pos.getX()+maxXCounter,
                                pos.getY()-yCounter,pos.getZ()+maxZCounter);
                        do {
                            minXCounter++;
                            if (!world.getBlockState(pos.west(minXCounter).down()).isAir()){
                                minXCounter--;
                                break;
                            }
                        } while (minXCounter <= maxValue);
                        if (!world.getBlockState(pos.west(minXCounter+1).down()).isOpaque()){
                            return false;
                        } else {
                            do {
                                minZCounter++;
                                if (!world.getBlockState(pos.north(minZCounter).down()).isAir()){
                                    minZCounter--;
                                    break;
                                }
                            } while (minZCounter <= maxValue);
                            if (!world.getBlockState(pos.north(minZCounter+1).down()).isOpaque()){
                                return false;
                            } else {
                                minPos = new BlockPos(pos.getX()-minXCounter,
                                        pos.getY()-1,pos.getZ()-minZCounter);
                                length = maxZCounter + minZCounter+1;
                                width = maxXCounter + minXCounter+1;
                                BlockPos up = minPos.up();
                                for (int s = 0;s < length;s++){
                                    for (int e = 0;e < width;e++){
                                        if (world.getBlockState(new BlockPos(up.getX()+e,up.getY(),up.getZ()+s)).getBlock() == ModBlocks.BIOGAS_DIGESTER_CONTROLLER){
                                            if (!(up.getX()+ e == pos.getX()&&up.getZ() + s==pos.getZ())){
                                                return false;
                                            }
                                        }
                                        if (!world.getBlockState(new BlockPos(up.getX()+e,up.getY(),up.getZ()+s)).isOpaque()){
                                            return false;
                                        }
                                    }
                                }
                                BlockPos north = minPos.north();
                                for (int e = 0;e < length;e++){
                                    for (int d = 0;d < yCounter;d++){
                                        if (!world.getBlockState(new BlockPos(north.getX()+e,north.getY()-d,north.getZ())).isOpaque()){
                                            return false;
                                        }
                                    }
                                }
                                BlockPos west = minPos.west();
                                for (int d = 0;d < yCounter;d++){
                                    for (int s = 0;s < length;s++){
                                        if (!world.getBlockState(new BlockPos(west.getX(),west.getY()-d,west.getZ()+s)).isOpaque()){
                                            return false;
                                        }
                                    }
                                }
                                BlockPos down = maxPos.down();
                                for (int n = 0;n < length;n++){
                                    for (int w = 0;w < width;w++){
                                        if (!world.getBlockState(new BlockPos(down.getX()-w,down.getY(),down.getZ()-n)).isOpaque()){
                                            return false;
                                        }
                                    }
                                }
                                BlockPos east = maxPos.east();
                                for (int n = 0;n < length;n++){
                                    for (int u = 0;u < yCounter;u++){
                                        if (!world.getBlockState(new BlockPos(east.getX(),east.getY()+u,east.getZ()-n)).isOpaque()){
                                            return false;
                                        }
                                    }
                                }
                                BlockPos south = maxPos.south();
                                for (int u = 0;u < yCounter;u++){
                                    for (int w = 0;w <width;w++){
                                        if (!world.getBlockState(new BlockPos(south.getX()-w,south.getY()+u,south.getZ())).isOpaque()){
                                            return false;
                                        }
                                    }
                                }
                                for (int u = 0;u < yCounter;u++){
                                    for (int e = 0;e < width;e++){
                                        for (int n = 0;n <length;n++){
                                            if (!world.getBlockState(new BlockPos(maxPos.getX()-e,maxPos.getY()+u,maxPos.getZ()-n)).isAir()){
                                                return false;
                                            }
                                        }
                                    }
                                }
                                size = length * width * yCounter;
                                return true;
                            }
                        }
                    }
                }
            }
        } else {
            return false;
        }
    }
    private void restAll(){
        yCounter = 1;
        maxXCounter = 0;
        maxZCounter = 0;
        minXCounter = 0;
        minZCounter = 0;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
