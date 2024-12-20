package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.GasCanisterBlock;
import com.zombie_cute.mc.bakingdelight.screen.custom.GasCanisterScreenHandler;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GasCanisterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos> {
    private int gasValue = 0;
    private final int maxGasValue = 6000;
    private int cycleInt = 0;
    public static final String GAS_CANISTER_NAME = "display_name.bakingdelight.gas_canister_name";
    protected final PropertyDelegate propertyDelegate;

    public GasCanisterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GAS_CANISTER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> GasCanisterBlockEntity.this.gasValue;
                    case 1 -> GasCanisterBlockEntity.this.cycleInt;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: {
                        GasCanisterBlockEntity.this.gasValue = value;
                        break;
                    }
                    case 1: {
                        GasCanisterBlockEntity.this.cycleInt = value;
                        break;
                    }
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }
    private int tick = 20;
    public static void tick(World world, BlockPos pos, BlockState state, GasCanisterBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        blockEntity.tick--;
        switch (blockEntity.tick){
            case 20, 3: blockEntity.cycleInt = 0;break;
            case 17, 7: blockEntity.cycleInt = 1;break;
            case 15, 10: blockEntity.cycleInt = 2;break;
            case 12: blockEntity.cycleInt = 3;break;
            case 0 : blockEntity.tick = 20;
        }
        if(blockEntity.isDangerBlock(world.getBlockState(pos.down()).getBlock())||
                blockEntity.isDangerBlock(world.getBlockState(pos.up()).getBlock())||
                blockEntity.isDangerBlock(world.getBlockState(pos.north()).getBlock())||
                blockEntity.isDangerBlock(world.getBlockState(pos.south()).getBlock())||
                blockEntity.isDangerBlock(world.getBlockState(pos.west()).getBlock())||
                blockEntity.isDangerBlock(world.getBlockState(pos.east()).getBlock())){
            blockEntity.randomExplode(world);
        } else if (world.getRegistryKey() == World.NETHER) {
            blockEntity.randomExplode(world);
        } else if (blockEntity.gasValue > blockEntity.maxGasValue){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5, true, World.ExplosionSourceType.BLOCK);
        }
        Direction direction = state.get(GasCanisterBlock.FACING);
        BlockPos facingBlock = pos;
        BlockPos underBlock = pos;
        switch (direction){
            case EAST: {
                facingBlock = pos.east(1);
                underBlock = pos.east(1).down(1);
                break;
            }
            case SOUTH: {
                facingBlock = pos.south(1);
                underBlock = pos.south(1).down(1);
                break;
            }
            case WEST: {
                facingBlock = pos.west(1);
                underBlock = pos.west(1).down(1);
                break;
            }
            case NORTH: {
                facingBlock = pos.north(1);
                underBlock = pos.north(1).down(1);
                break;
            }
        }
        if (world.getBlockState(facingBlock).getBlock().equals(ModBlocks.BIOGAS_DIGESTER_IO) &&
                world.getBlockEntity(underBlock) instanceof BiogasDigesterControllerBlockEntity entity){
            if (entity.getGasValue()>=5){
                blockEntity.playSound(ModSounds.BLOCK_GAS_CANISTER_FILLING,0.5f,0.8f);
                entity.reduceGas(5);
                blockEntity.gasValue += 5;
                blockEntity.markDirty();
            } else if (entity.getGasValue() > 0){
                blockEntity.playSound(ModSounds.BLOCK_GAS_CANISTER_FILLING,0.5f,0.8f);
                entity.reduceGas(1);
                blockEntity.gasValue += 1;
                blockEntity.markDirty();
            }
        }
    }
    public boolean isDangerBlock(Block block){
        Set<Block> blockHashSet = new HashSet<>();
        for (RegistryEntry<Block> registryEntry : Registries.BLOCK.iterateEntries(TagKeys.DANGER_BLOCKS)){
            blockHashSet.add(registryEntry.value());
        }
        blockHashSet.remove(Blocks.CAULDRON);
        blockHashSet.remove(ModBlocks.BURNING_GAS_COOKING_STOVE);
        return blockHashSet.contains(block);
    }
    private float explodeTime = 0;
    public void randomExplode(World world){
        if (gasValue > 999){
            explodeTime += world.random.nextFloat();
            if (explodeTime > 60){
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) (gasValue * 5 / maxGasValue), true, World.ExplosionSourceType.BLOCK);
            }
        }
    }
    public void explode(World world){
        if (gasValue > 999){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) (gasValue * 5 / maxGasValue), true, World.ExplosionSourceType.BLOCK);
        }
    }

    public void onUse(PlayerEntity player, World world) {
        if (gasValue > 999){
            if (player.getOffHandStack().getItem().equals(Items.FLINT_AND_STEEL)){
                player.getOffHandStack().damage(1, player, EquipmentSlot.OFFHAND);
                playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE,1.0f,1.0f);
                explode(world);
            } else if (player.getMainHandStack().getItem().equals(Items.FLINT_AND_STEEL)){
                player.getMainHandStack().damage(1, player, EquipmentSlot.MAINHAND);
                playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE,1.0f,1.0f);
                explode(world);
            } else if (player.getOffHandStack().getItem().equals(Items.FIRE_CHARGE)){
                player.getOffHandStack().decrement(1);
                playSound(SoundEvents.ITEM_FIRECHARGE_USE,1.0f,1.0f);
                explode(world);
            } else if (player.getMainHandStack().getItem().equals(Items.FIRE_CHARGE)){
                player.getMainHandStack().decrement(1);
                playSound(SoundEvents.ITEM_FIRECHARGE_USE,1.0f,1.0f);
                explode(world);
            }
        }
    }
    public void playSound(SoundEvent sound, float volume, float pitch) {
        Objects.requireNonNull(world).playSound(null,
                pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f,
                sound, SoundCategory.BLOCKS, volume, pitch);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("gas_canister.gasValue", gasValue);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt,registryLookup);
        gasValue = nbt.getInt("gas_canister.gasValue");
        markDirty();
    }
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
    @Override
    public Text getDisplayName() {
        return Text.translatable(GAS_CANISTER_NAME);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GasCanisterScreenHandler(syncId, playerInventory,this,this.propertyDelegate);
    }
    public int getGasValue() {
        return gasValue;
    }
    public void reduceGas(){
        if (gasValue!=0 && tick %2 == 0){
            gasValue--;
        }
        markDirty();
    }
    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        super.markDirty();
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
