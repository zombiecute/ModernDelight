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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
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

public class ElectricSteamerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory, ACConsumer, SidedInventory {
    public ElectricSteamerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ELECTRIC_STEAMER_BLOCK_ENTITY, pos, state);
    }
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(8,ItemStack.EMPTY);
    private int cachedPower = 0;
    private final int[] progresses = new int[8];

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory,registryLookup);
        nbt.putInt("electric_steamer.cachedPower",cachedPower);
        nbt.putIntArray("electric_steamer.progresses",progresses);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
        cachedPower = nbt.getInt("electric_steamer.cachedPower");
        int[] temp = nbt.getIntArray("electric_steamer.progresses");
        int max = progresses.length;
        if (temp.length < max){
            max = temp.length;
        }
        System.arraycopy(temp, 0, progresses, 0, max);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
    public static void tick(World world, BlockPos pos, BlockState state, ElectricSteamerBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        if (blockEntity.cachedPower > 0){
            blockEntity.cachedPower--;
            world.setBlockState(pos,state.with(ElectricSteamerBlock.IS_WORKING,true));
        } else world.setBlockState(pos,state.with(ElectricSteamerBlock.IS_WORKING,false));
        if (world.getTime() %20L == 0L){
            for (int i = 0; i < 8; i++){
                Optional<RecipeEntry<SteamingRecipe>> match = Objects.requireNonNull(blockEntity.getWorld()).getRecipeManager()
                        .getFirstMatch(SteamingRecipe.Type.INSTANCE, new SingleStackRecipeInput(blockEntity.getStack(i)),blockEntity.getWorld());
                if (match.isPresent()){
                    int maxProgress = match.get().value().getMaxProgress();
                    int count = blockEntity.getStack(i).getCount();
                    if (blockEntity.progresses[i] < maxProgress * count){
                        blockEntity.progresses[i]++;
                    } else {
                        blockEntity.progresses[i] = 0;
                        blockEntity.setStack(i,new ItemStack(match.get().value().getResult(null).getItem(),count));
                    }
                } else {
                    blockEntity.progresses[i] = 0;
                }
            }
            blockEntity.markDirty();
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

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
