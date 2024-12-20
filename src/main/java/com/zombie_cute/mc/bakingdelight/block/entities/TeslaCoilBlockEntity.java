package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.TeslaCoilBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ACConsumer;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ACGenerateAble;
import com.zombie_cute.mc.bakingdelight.screen.custom.TeslaCoilScreenHandler;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.util.ModDamageTypes;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TeslaCoilBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos> {
    protected final PropertyDelegate propertyDelegate;
    private static final Direction[] DIRECTIONS = {Direction.UP,Direction.DOWN,Direction.WEST,Direction.EAST,Direction.NORTH,Direction.SOUTH};
    public TeslaCoilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TESLA_COIL_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> TeslaCoilBlockEntity.this.efficiency;
                    case 1 -> TeslaCoilBlockEntity.this.showPractical;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> TeslaCoilBlockEntity.this.efficiency = value;
                    case 1 -> TeslaCoilBlockEntity.this.showPractical = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }
    @Override
    public Text getDisplayName() {
        return ModBlocks.TESLA_COIL.getName();
    }
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TeslaCoilScreenHandler(syncId,playerInventory,this,propertyDelegate);
    }
    private int showPractical = 0;
    private int efficiency = 0;
    private final List<BlockEntity> confinedBlockEntities = new ArrayList<>();
    private BlockEntity powerSupply = null;
    private int ticker = 60;
    public static void tick(World world, BlockPos pos, BlockState state, TeslaCoilBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        blockEntity.ticker--;
        if (blockEntity.ticker <= 0){
            blockEntity.ticker = 60;
        }
        if (blockEntity.showPractical != 0){
            world.setBlockState(pos,state.with(TeslaCoilBlock.SHOW_PARTICLE,true));
        } else world.setBlockState(pos,state.with(TeslaCoilBlock.SHOW_PARTICLE,false));
        // Attack
        if (blockEntity.ticker % 20 == 0){
            if (blockEntity.efficiency > 0){
                Box box = new Box(pos).expand(1.2);
                List<LivingEntity> entities = world.getNonSpectatingEntities(LivingEntity.class,box);
                for (LivingEntity entity : entities) {
                    if (entity != null){
                        entity.damage(ModDamageTypes.of(world, ModDamageTypes.ELECTROSHOCK),
                                (float) ((float) blockEntity.efficiency / 100.0) * 2); // 造成伤害
                        for (int j = 0; j < 4; j++){
                            world.addImportantParticle(ParticleTypes.WAX_OFF,
                                    pos.getX() + 0.3 + world.random.nextFloat()/3,
                                    pos.getY() + 0.5,pos.getZ()  + 0.3 + world.random.nextFloat()/3,
                                    0,0,0);
                        }
                        world.playSound(null,pos.getX(),pos.getY(),pos.getZ(),
                                ModSounds.BLOCK_TESLA_COIL, SoundCategory.BLOCKS,
                                1.0f,0.8f + world.random.nextFloat());
                    }
                }
            }
        }
        // 每3秒更新一次
        if (blockEntity.ticker % 60 == 0){
            // 电源判定
            switch (state.get(TeslaCoilBlock.FACING)){
                case UP -> blockEntity.powerSupply = blockEntity.getPowerSupply(world,pos.up());
                case DOWN -> blockEntity.powerSupply = blockEntity.getPowerSupply(world,pos.down());
                case EAST -> blockEntity.powerSupply = blockEntity.getPowerSupply(world,pos.west());
                case SOUTH -> blockEntity.powerSupply = blockEntity.getPowerSupply(world,pos.north());
                case WEST -> blockEntity.powerSupply = blockEntity.getPowerSupply(world,pos.east());
                case NORTH -> blockEntity.powerSupply = blockEntity.getPowerSupply(world,pos.south());
            }
            int max = 0;
            int cachedPower = 0;
            blockEntity.confinedBlockEntities.clear();
            // 遍历范围内所有的方块实体
            for (Direction direction : DIRECTIONS){
                for (int i = 1; i <= 8;i++){
                    switch (direction){
                        case NORTH -> blockEntity.confinedBlockEntities.add(world.getBlockEntity(pos.north(i)));
                        case SOUTH -> blockEntity.confinedBlockEntities.add(world.getBlockEntity(pos.south(i)));
                        case EAST -> blockEntity.confinedBlockEntities.add(world.getBlockEntity(pos.east(i)));
                        case WEST -> blockEntity.confinedBlockEntities.add(world.getBlockEntity(pos.west(i)));
                        case UP -> blockEntity.confinedBlockEntities.add(world.getBlockEntity(pos.up(i)));
                        case DOWN -> blockEntity.confinedBlockEntities.add(world.getBlockEntity(pos.down(i)));
                    }
                }
            }
            if (!blockEntity.isOnPowerSupply()){
                // 找自己并获取最大的电量值
                for (BlockEntity entity : blockEntity.confinedBlockEntities){
                    if (entity instanceof TeslaCoilBlockEntity teslaCoilBlock){
                        if (max < teslaCoilBlock.getEfficiency()){
                            max = teslaCoilBlock.getEfficiency();
                        }
                    }
                }
                blockEntity.efficiency = (int) (max * 0.7);
            }
            // 找要电的
            for (BlockEntity entity : blockEntity.confinedBlockEntities){
                if (entity instanceof ACConsumer consumer){
                    if (consumer.isWorking()){
                        cachedPower += consumer.getConsumedValue();
                    }
                }
            }
            blockEntity.efficiency -= cachedPower;
            // 用电器工作
            if (blockEntity.efficiency > 0){
                for (BlockEntity entity : blockEntity.confinedBlockEntities){
                    if (entity instanceof ACConsumer consumer){
                        if (consumer.isWorking()){
                            consumer.energize();
                        }
                    }
                }
                world.setBlockState(pos,state.with(TeslaCoilBlock.IS_OVERLOADED,false));
            } else world.setBlockState(pos,state.with(TeslaCoilBlock.IS_OVERLOADED,true));
            blockEntity.markDirty();
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("tesla_coil.showParticle",this.showPractical);
        nbt.putInt("tesla_coil.ticker",this.ticker);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.showPractical = nbt.getInt("tesla_coil.showParticle");
        this.ticker = nbt.getInt("tesla_coil.ticker");
    }

    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        super.markDirty();
    }
    public void setShowParticle(boolean value){
        if (value){
            this.showPractical = 1;
        } else this.showPractical = 0;
        markDirty();
    }
    public int getEfficiency() {
        return this.efficiency;
    }
    private boolean isOnPowerSupply(){
        return this.powerSupply != null;
    }
    private BlockEntity getPowerSupply(World world,BlockPos pos){
        if (world.getBlockEntity(pos) instanceof ACGenerateAble able && able.getEfficiency() > 0){
            this.efficiency = (int) (able.getEfficiency() * 0.9);
            return world.getBlockEntity(pos);
        } else return null;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
