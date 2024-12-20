package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.custom.abstracts.AbstractBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.Power;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.PowerStorageAble;
import com.zombie_cute.mc.bakingdelight.screen.custom.PhotovoltaicGeneratorScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PhotovoltaicGeneratorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory, PowerStorageAble {
    public PhotovoltaicGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PHOTOVOLTAIC_GENERATOR_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> PhotovoltaicGeneratorBlockEntity.this.isWorking;
                    case 1 -> PhotovoltaicGeneratorBlockEntity.this.getPowerValue();
                    case 2 -> PhotovoltaicGeneratorBlockEntity.this.getPower().getMaxPower();
                    case 3 -> PhotovoltaicGeneratorBlockEntity.this.slowMode;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> PhotovoltaicGeneratorBlockEntity.this.isWorking = value;
                    case 1 -> PhotovoltaicGeneratorBlockEntity.this.setPower(value);
                    case 3 -> PhotovoltaicGeneratorBlockEntity.this.slowMode = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1,ItemStack.EMPTY);
    private final Power power = new Power(3000);
    private final PropertyDelegate propertyDelegate;
    private int isWorking = 0;
    private int slowMode = 0;
    public static final String PHOTOVOLTAIC_GENERATOR_NAME = "display_name.bakingdelight.photovoltaic_generator_name";
    public static void tick(World world,BlockPos pos,BlockState state, PhotovoltaicGeneratorBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        if (world.getTime() % 20L == 0L){
            ItemStack itemStack = blockEntity.getStack(0);
            blockEntity.setStack(0,
                    AbstractBatteryBlock.changeBatteryPower(itemStack,blockEntity.getPower(), 20, true));
            int light = world.getLightLevel(LightType.BLOCK,pos) / 3 - 1;
            if (light > 0){
                blockEntity.addPower(light);
                blockEntity.slowMode = 1;
            } else blockEntity.slowMode = 0;
            if (blockEntity.isInOpenAir(world)){
                if (isEarlyMorningOrTwilight(world)){
                    blockEntity.addPowerAndCheck(1,world);
                    if (world.isThundering() || world.isRaining()) blockEntity.isWorking = 0;
                    else blockEntity.isWorking = 1;
                } else if (isMorningOrAfternoon(world)) {
                    blockEntity.addPowerAndCheck(2,world);
                    if (world.isThundering()) blockEntity.isWorking = 0;
                    else blockEntity.isWorking = 1;
                } else if (isNoon(world)) {
                    blockEntity.addPowerAndCheck(3,world);
                    blockEntity.isWorking = 1;
                } else blockEntity.isWorking = 0;
            } else blockEntity.isWorking = 0;
            blockEntity.markDirty();
        }
    }

    private void addPowerAndCheck(int multiplier, World world){
        int y = pos.getY() / 2;
        int var = multiplier;
        if (var <= 0){
            return;
        }
        if (world.isThundering()){
            if (var - 2 >= 0){
                var -= 2;
            } else {
                return;
            }
        } else if (world.isRaining()) {
            var--;
        }
        if (y / 10 > 0){
            this.addPower(var * y / 10);
        } else {
            this.addPower(var);
        }
    }
    private boolean isInOpenAir(World world){
        return world.getLightLevel(LightType.SKY, pos) >= 13 && !world.getDimension().hasFixedTime() &&
                !world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos.up(), Direction.DOWN);
    }
    public static boolean isEarlyMorningOrTwilight(World world){
        long timeOfDay = world.getTimeOfDay() % 24000L;
        return (timeOfDay >= 0 && timeOfDay < 167) || (timeOfDay >= 11617 && timeOfDay < 13702) || (timeOfDay >= 23000);
    }
    public static boolean isMorningOrAfternoon(World world){
        long timeOfDay = world.getTimeOfDay() % 24000L;
        return (timeOfDay >= 167 && timeOfDay < 4283) || (timeOfDay >= 7700 && timeOfDay < 11617);
    }
    public static boolean isNoon(World world){
        long timeOfDay = world.getTimeOfDay() % 24000L;
        return timeOfDay >= 4283 && timeOfDay < 7700;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt,inventory,registryLookup);
        nbt.putInt("photovoltaic_generator.power", this.getPower().getPowerValue());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt,inventory,registryLookup);
        this.setPower(nbt.getInt("photovoltaic_generator.power"));
        markDirty();
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(PHOTOVOLTAIC_GENERATOR_NAME);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PhotovoltaicGeneratorScreenHandler(syncId,playerInventory,this,propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Power getPower() {
        return power;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
