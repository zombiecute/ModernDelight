package com.zombie_cute.mc.bakingdelight.block.biogas;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.screen.custom.GasCanisterScreenHandler;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.FluidUtil;
import com.zombie_cute.mc.bakingdelight.util.ModConfig;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
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

public class GasCanisterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory{
    private int gasValue = 0;
    private int cycleInt = 0;
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }
        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidUtil.convertMbToDroplets(getMaxCapacity());
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
    public void tick(World world, BlockPos pos, BlockState state, GasCanisterBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        gasValue = (int)FluidUtil.convertDropletsToMb(fluidStorage.amount);
        blockEntity.tick--;
        switch (tick){
            case 20, 3: cycleInt = 0;break;
            case 17, 7: cycleInt = 1;break;
            case 15, 10: cycleInt = 2;break;
            case 12: cycleInt = 3;break;
            case 0 : tick = 20;
        }
        if (allowExplode() && fluidIsGas()) {
            if(isDangerBlock(world.getBlockState(pos.down()).getBlock())||
                    isDangerBlock(world.getBlockState(pos.up()).getBlock())||
                    isDangerBlock(world.getBlockState(pos.north()).getBlock())||
                    isDangerBlock(world.getBlockState(pos.south()).getBlock())||
                    isDangerBlock(world.getBlockState(pos.west()).getBlock())||
                    isDangerBlock(world.getBlockState(pos.east()).getBlock())){
                randomExplode(world);
            } else if (world.getDimension().ultrawarm() && !allowNether()) {
                randomExplode(world);
            } else if (fluidStorage.amount >= FluidUtil.convertMbToDroplets(getMaxCapacity())){
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), getGasValue() / 1000f, true, World.ExplosionSourceType.BLOCK);
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
        if ( (fluidIsGas() || fluidStorage.variant.isBlank()) &&
                world.getBlockState(facingBlock).getBlock().equals(ModBlocks.BIOGAS_DIGESTER_IO) &&
                world.getBlockEntity(underBlock) instanceof BiogasDigesterControllerBlockEntity entity){
            if (gasValue < getMaxCapacity()){
                if (entity.getGasValue()>=5){
                    playSound(ModSounds.BLOCK_GAS_CANISTER_FILLING,0.5f,0.8f);
                    entity.reduceGas(5);
                    try(Transaction transaction = Transaction.openOuter()){
                        fluidStorage.insert(FluidVariant.of(ModFluid.STILL_LIQUEFIED_BIOGAS),
                                FluidUtil.convertMbToDroplets(5),transaction);
                        transaction.commit();
                    }
                    markDirty();
                } else if (entity.getGasValue() > 0){
                    playSound(ModSounds.BLOCK_GAS_CANISTER_FILLING,0.5f,0.8f);
                    entity.reduceGas(1);
                    try(Transaction transaction = Transaction.openOuter()){
                        fluidStorage.insert(FluidVariant.of(ModFluid.STILL_LIQUEFIED_BIOGAS),
                                FluidUtil.convertMbToDroplets(1),transaction);
                        transaction.commit();
                    }
                    markDirty();
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
                player.getOffHandStack().damage(1, (LivingEntity) player, playerEntity -> playerEntity.sendToolBreakStatus(Hand.MAIN_HAND));
                playSound(SoundEvents.ITEM_FLINTANDSTEEL_USE,1.0f,1.0f);
                instantExplode(world);
            } else if (player.getMainHandStack().getItem().equals(Items.FLINT_AND_STEEL)){
                player.getMainHandStack().damage(1, (LivingEntity) player, playerEntity -> playerEntity.sendToolBreakStatus(Hand.OFF_HAND));
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
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong("gas_canister.fluid_amount",fluidStorage.amount);
        nbt.put("gas_canister.fluid_variant",fluidStorage.variant.toNbt());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("gas_canister.fluid_variant"));
        fluidStorage.amount = nbt.getLong("gas_canister.fluid_amount");
        markDirty();
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
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
        int value = ModConfig.gasCanisterVolume;
        if (value > 0){
            return value;
        } else return 6000;
    }
    public static boolean allowExplode(){
        return ModConfig.allowGasCanisterExplode;
    }
    public static boolean allowNether(){
        return ModConfig.allowGasCanisterInNether;
    }
}
