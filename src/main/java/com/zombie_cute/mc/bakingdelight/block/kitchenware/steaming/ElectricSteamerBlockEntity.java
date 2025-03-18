package com.zombie_cute.mc.bakingdelight.block.kitchenware.steaming;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.recipe.custom.SteamingRecipe;
import com.zombie_cute.mc.bakingdelight.screen.custom.ElectricSteamerScreenHandler;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.ACConsumer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class ElectricSteamerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, ACConsumer, SidedInventory {
    public ElectricSteamerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ELECTRIC_STEAMER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                if (index>=0 && index <= 11){
                    return ElectricSteamerBlockEntity.this.progresses[index];
                } else if (index>=12 && index <=23) {
                    return ElectricSteamerBlockEntity.this.maxProgresses[index-12];
                } else {
                    return switch (index){
                        case 24 -> ElectricSteamerBlockEntity.this.water;
                        case 25 -> ElectricSteamerBlockEntity.this.steam;
                        case 26 -> ElectricSteamerBlockEntity.this.steamProgress;
                        default -> 0;
                    };
                }
            }

            @Override
            public void set(int index, int value) {}

            @Override
            public int size() {
                return 27;
            }
        };
    }
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(13,ItemStack.EMPTY);
    private int cachedPower = 0;
    private final int[] progresses = new int[12];
    private final int[] maxProgresses = new int[12];
    private int water = 0;
    private int steam = 0;
    private int steamProgress = 0;
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.of(Fluids.WATER);
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidConstants.BUCKET;
        }
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
    protected final PropertyDelegate propertyDelegate;
    public static final int MAX_STEAM_PROGRESS = 60;
    public static final int MAX_WATER_OR_STEAM = 1000;
    public static final int WATER_SLOT = 12;
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("electric_steamer.cachedPower",cachedPower);
        nbt.putIntArray("electric_steamer.progresses",progresses);
        nbt.put("electric_steamer.fluid_variant",fluidStorage.variant.toNbt());
        nbt.putLong("electric_steamer.fluid_amount",fluidStorage.amount);
        nbt.putInt("electric_steamer.steam",steam);
        nbt.putInt("electric_steamer.steamProgress",steamProgress);
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        cachedPower = nbt.getInt("electric_steamer.cachedPower");
        int[] temp = nbt.getIntArray("electric_steamer.progresses");
        int max = progresses.length;
        if (temp.length < max){
            max = temp.length;
        }
        System.arraycopy(temp, 0, progresses, 0, max);
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("electric_steamer.fluid_variant"));
        fluidStorage.amount = nbt.getLong("electric_steamer.fluid_amount");
        steam = nbt.getInt("electric_steamer.steam");
        steamProgress = nbt.getInt("electric_steamer.steamProgress");
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient){
            return;
        }
        if (fluidStorage.variant.getFluid() == Fluids.WATER){
            water = (int) FluidStack.convertDropletsToMb(fluidStorage.amount);
        } else water = 0;
        if (cachedPower > 0){
            cachedPower--;
            world.setBlockState(pos,state.with(ElectricSteamerBlock.IS_WORKING,true));
            if (fluidStorage.amount > FluidStack.convertMbToDroplets(50) &&
                    steam < MAX_WATER_OR_STEAM &&
                    fluidStorage.variant.getFluid() == Fluids.WATER){
                steamProgress++;
                if (steamProgress >= MAX_STEAM_PROGRESS){
                    steamProgress = 0;
                    fluidStorage.amount-= FluidStack.convertMbToDroplets(50);
                    if (steam + 80 < MAX_WATER_OR_STEAM){
                        steam+=80;
                    } else {
                        steam = MAX_WATER_OR_STEAM;
                    }
                }
            } else {
                steamProgress = 0;
            }
        } else world.setBlockState(pos,state.with(ElectricSteamerBlock.IS_WORKING,false));
        if (getStack(WATER_SLOT).getItem().equals(Items.WATER_BUCKET)){
            if (fillWater(world,pos)){
                setStack(WATER_SLOT,Items.BUCKET.getDefaultStack());
            }
        }
        if (world.getTime() %20L == 0L){
            if (steam > 5){
                for (int i = 0; i < 12; i++){
                    SimpleInventory inventory = new SimpleInventory(this.getStack(i));
                    Optional<SteamingRecipe> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                            .getFirstMatch(SteamingRecipe.Type.INSTANCE, inventory,this.getWorld());
                    if (match.isPresent()){
                        int maxProgress = match.get().getMaxProgress();
                        int count = this.getStack(i).getCount();
                        if (count <= 16){
                            this.maxProgresses[i] = maxProgress;
                        } else {
                            this.maxProgresses[i] = maxProgress * count / 16;
                        }
                        if (this.progresses[i] < this.maxProgresses[i]){
                            this.progresses[i]++;
                            this.steam -=5;
                        } else {
                            this.progresses[i] = 0;
                            this.maxProgresses[i] = 0;
                            this.setStack(i,new ItemStack(match.get().getOutput(null).getItem(),count));
                        }
                    } else {
                        this.progresses[i] = 0;
                        this.maxProgresses[i] = 0;
                    }
                }
                markDirty();
            }
        }
    }

    @Override
    public void markDirty() {
        ItemStackSyncS2CPacket.send(pos,getItems(),world);
        super.markDirty();
    }

    public boolean fillWater(World world, BlockPos pos) {
        if (fluidStorage.variant.getFluid() == FluidVariant.blank() ||
                (fluidStorage.amount < fluidStorage.getCapacity() &&
                        fluidStorage.variant.getFluid() == Fluids.WATER)){
            world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS,1.0f,1.0f);
            fluidStorage.variant = FluidVariant.of(Fluids.WATER);
            fluidStorage.amount = fluidStorage.getCapacity();
            return true;
        } else return false;
    }

    @Override
    public long getConsumedValue() {
        return 10;
    }

    @Override
    public boolean isWorking() {
        return true;
    }

    @Override
    public void energize() {
        cachedPower = 60;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal(" ");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ElectricSteamerScreenHandler(syncId,playerInventory,this,propertyDelegate);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (dir == Direction.UP && slot >= 0 && slot <= 11){
            return true;
        } else return (
                        dir == Direction.EAST ||
                        dir == Direction.SOUTH ||
                        dir == Direction.WEST ||
                        dir == Direction.NORTH
                ) && slot == WATER_SLOT;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }
}
