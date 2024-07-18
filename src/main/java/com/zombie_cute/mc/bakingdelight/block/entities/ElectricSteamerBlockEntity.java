package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.custom.ElectricSteamerBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ACConsumer;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.recipe.custom.SteamingRecipe;
import com.zombie_cute.mc.bakingdelight.screen.custom.ElectricSteamerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
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
    }
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(8,ItemStack.EMPTY);
    private int cachedPower = 0;
    private final int[] progresses = new int[8];
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("electric_steamer.cachedPower",cachedPower);
        nbt.putIntArray("electric_steamer.progresses",progresses);
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
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient){
            return;
        }
        if (cachedPower > 0){
            cachedPower--;
            world.setBlockState(pos,state.with(ElectricSteamerBlock.IS_WORKING,true));
        } else world.setBlockState(pos,state.with(ElectricSteamerBlock.IS_WORKING,false));
        if (world.getTime() %20L == 0L){
            for (int i = 0; i < 8; i++){
                SimpleInventory inventory = new SimpleInventory(this.getStack(i));
                Optional<SteamingRecipe> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                        .getFirstMatch(SteamingRecipe.Type.INSTANCE, inventory,this.getWorld());
                if (match.isPresent()){
                    int maxProgress = match.get().getMaxProgress();
                    int count = this.getStack(i).getCount();
                    if (this.progresses[i] < maxProgress * count){
                        this.progresses[i]++;
                    } else {
                        this.progresses[i] = 0;
                        this.setStack(i,new ItemStack(match.get().getOutput(null).getItem(),count));
                    }
                } else {
                    this.progresses[i] = 0;
                }
            }
            markDirty();
        }
    }

    @Override
    public int getConsumedValue() {
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
        return Text.translatable(BambooGrateBlockEntity.NAME);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ElectricSteamerScreenHandler(syncId,playerInventory,this);
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
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }
}
