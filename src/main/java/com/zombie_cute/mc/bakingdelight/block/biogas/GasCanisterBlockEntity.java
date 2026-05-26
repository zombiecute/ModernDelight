package com.zombie_cute.mc.bakingdelight.block.biogas;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.screen.custom.GasCanisterScreenHandler;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
import com.zombie_cute.mc.bakingdelight.util.ModConfig;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
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
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GasCanisterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>{
    private int gasValue = 0;
    private int cycleInt = 0;
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }
        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidStack.convertMbToDroplets(getMaxCapacity());
        }
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
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
        blockEntity.gasValue = (int) FluidStack.convertDropletsToMb(blockEntity.fluidStorage.amount);
        blockEntity.tick--;
        switch (blockEntity.tick){
            case 20, 3: blockEntity.cycleInt = 0;break;
            case 17, 7: blockEntity.cycleInt = 1;break;
            case 15, 10: blockEntity.cycleInt = 2;break;
            case 12: blockEntity.cycleInt = 3;break;
            case 0 : blockEntity.tick = 20;
        }
        if (allowExplode() && blockEntity.fluidIsGas()) {
            if(isDangerBlock(world.getBlockState(pos.down()).getBlock())||
                    isDangerBlock(world.getBlockState(pos.up()).getBlock())||
                    isDangerBlock(world.getBlockState(pos.north()).getBlock())||
                    isDangerBlock(world.getBlockState(pos.south()).getBlock())||
                    isDangerBlock(world.getBlockState(pos.west()).getBlock())||
                    isDangerBlock(world.getBlockState(pos.east()).getBlock())){
                blockEntity.randomExplode(world);
            } else if (world.getDimension().ultrawarm() && !allowNether()) {
                blockEntity.randomExplode(world);
            } else if (blockEntity.fluidStorage.amount >= FluidStack.convertMbToDroplets(getMaxCapacity())){
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), blockEntity.getGasValue() / 1000f, true, World.ExplosionSourceType.BLOCK);
            }
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
        if ( (blockEntity.fluidIsGas() || blockEntity.fluidStorage.variant.isBlank()) &&
                world.getBlockState(facingBlock).getBlock().equals(ModBlocks.BIOGAS_DIGESTER_IO) &&
                world.getBlockEntity(underBlock) instanceof BiogasDigesterControllerBlockEntity entity){
            if (blockEntity.gasValue < getMaxCapacity()){
                if (entity.getGasValue()>=5){
                    blockEntity.playSound(ModSounds.BLOCK_GAS_CANISTER_FILLING,0.5f,0.8f);
                    entity.reduceGas(5);
                    try(Transaction transaction = Transaction.openOuter()){
                        blockEntity.fluidStorage.insert(FluidVariant.of(ModFluid.STILL_LIQUEFIED_BIOGAS),
                                FluidStack.convertMbToDroplets(5),transaction);
                        transaction.commit();
                    }
                    blockEntity.markDirty();
                } else if (entity.getGasValue() > 0){
                    blockEntity.playSound(ModSounds.BLOCK_GAS_CANISTER_FILLING,0.5f,0.8f);
                    entity.reduceGas(1);
                    try(Transaction transaction = Transaction.openOuter()){
                        blockEntity.fluidStorage.insert(FluidVariant.of(ModFluid.STILL_LIQUEFIED_BIOGAS),
                                FluidStack.convertMbToDroplets(1),transaction);
                        transaction.commit();
                    }
                    blockEntity.markDirty();
                }
            }
        }
    }

    public boolean fluidIsGas() {
        return fluidIsGas(fluidStorage.variant.getFluid());
    }
    public static boolean fluidIsGas(Fluid fluid){
        for (RegistryEntry<Fluid> registryEntry: Registries.FLUID.iterateEntries(TagKeys.GAS)){
            if (registryEntry.value() == fluid){
                return true;
            }
        }
        return false;
    }

    public static boolean isDangerBlock(Block block){
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
        if (gasValue > 999 && fluidIsGas()){
            explodeTime += world.random.nextFloat();
            if (explodeTime > 60){
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) (gasValue * 5 / getMaxCapacity()), true, World.ExplosionSourceType.BLOCK);
            }
        }
    }
    public void instantExplode(World world){
        if (gasValue > 999 && fluidIsGas()){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) (gasValue * 5 / getMaxCapacity()), true, World.ExplosionSourceType.BLOCK);
        }
    }

    public void onUse(PlayerEntity player, World world) {
        if (gasValue > 999 && fluidIsGas()){
            if (player.getOffHandStack().getItem().equals(Items.FLINT_AND_STEEL)){
                player.getOffHandStack().damage(1, player,player.getActiveHand()== Hand.MAIN_HAND? EquipmentSlot.MAINHAND:EquipmentSlot.OFFHAND);
                playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE,1.0f,1.0f);
                instantExplode(world);
            } else if (player.getMainHandStack().getItem().equals(Items.FLINT_AND_STEEL)){
                player.getMainHandStack().damage(1, player,player.getActiveHand()== Hand.MAIN_HAND? EquipmentSlot.MAINHAND:EquipmentSlot.OFFHAND);
                playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE,1.0f,1.0f);
                instantExplode(world);
            } else if (player.getOffHandStack().getItem().equals(Items.FIRE_CHARGE)){
                player.getOffHandStack().decrement(1);
                playSound(SoundEvents.ITEM_FIRECHARGE_USE,1.0f,1.0f);
                instantExplode(world);
            } else if (player.getMainHandStack().getItem().equals(Items.FIRE_CHARGE)){
                player.getMainHandStack().decrement(1);
                playSound(SoundEvents.ITEM_FIRECHARGE_USE,1.0f,1.0f);
                instantExplode(world);
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
        nbt.putLong("gas_canister.fluid_amount",fluidStorage.amount);
        nbt.putString("gas_canister.fluid_variant",fluidStorage.variant.getRegistryEntry().getIdAsString());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        FluidStack stack;
        try {
            stack = FluidStack.getFluidStack(nbt.getString("gas_canister.fluid_variant"), nbt.getLong("gas_canister.fluid_amount"));
        } catch (Exception e) {
            stack = new FluidStack(FluidVariant.of(Fluids.WATER), nbt.getLong("gas_canister.fluid_amount"));
        }
        fluidStorage.variant = stack.fluidVariant;
        fluidStorage.amount = stack.amount_droplets;
        markDirty();
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
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

    public boolean reduceGas(){
        if (gasValue!=0 && fluidIsGas()){
            fluidStorage.amount-=27;
            markDirty();
            return true;
        } else return false;
    }
    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        super.markDirty();
    }
    public static int getMaxCapacity(){
        try {
            int value = ModConfig.gasCanisterVolume;
            if (value > 0){
                return value;
            } else return 6000;
        } catch (Throwable e){
            return 6000;
        }
    }
    public static boolean allowExplode(){
        try {
            return ModConfig.allowGasCanisterExplode;
        } catch (Throwable e){
            return true;
        }
    }
    public static boolean allowNether(){
        try {
            return ModConfig.allowGasCanisterInNether;
        } catch (Throwable e){
            return false;
        }
    }
}
