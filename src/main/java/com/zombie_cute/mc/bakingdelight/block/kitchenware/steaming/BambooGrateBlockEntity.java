package com.zombie_cute.mc.bakingdelight.block.kitchenware.steaming;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.gas_cooking_stove.BurningGasCookingStoveBlockEntity;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.recipe.custom.SteamingRecipe;
import com.zombie_cute.mc.bakingdelight.screen.custom.BambooSteamerScreenHandler;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.screen.PropertyDelegate;
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

public class BambooGrateBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos>, SidedInventory {
    public BambooGrateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BAMBOO_GRATE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                if (index >= 0 && index <= 15){
                    return BambooGrateBlockEntity.this.progresses[index];
                } else if (index >= 16 && index <=31){
                    return BambooGrateBlockEntity.this.maxProgresses[index-16];
                } else {
                    return switch (index){
                        case 32 -> BambooGrateBlockEntity.this.isCovered;
                        case 33 -> BambooGrateBlockEntity.this.isHeated;
                        case 34 -> BambooGrateBlockEntity.this.currentLayer;
                        default -> 0;
                    };
                }
            }

            @Override
            public void set(int index, int value) {}

            @Override
            public int size() {
                return 35;
            }
        };
    }
    public static final String NAME = "display_name.bakingdelight.steamer_name";
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(16,ItemStack.EMPTY);
    private final PropertyDelegate propertyDelegate;
    private int isCovered = 0;
    private int isHeated = 0;
    private int currentLayer = 0;
    private final int[] progresses = new int[16];
    private final int[] maxProgresses = new int[16];
    private int availableSlots = 4;
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory,registryLookup);
        nbt.putInt("bamboo_grate.currentLayer",currentLayer);
        nbt.putInt("bamboo_grate.isHeated",isHeated);
        nbt.putInt("bamboo_grate.isCovered",isCovered);
        nbt.putIntArray("bamboo_grate.progresses",progresses);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory,registryLookup);
        this.currentLayer = nbt.getInt("bamboo_grate.currentLayer");
        this.isHeated = nbt.getInt("bamboo_grate.isHeated");
        this.isCovered = nbt.getInt("bamboo_grate.isCovered");
        int[] temp = nbt.getIntArray("bamboo_grate.progresses");
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

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(NAME);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        if (world != null){
            return new BambooSteamerScreenHandler(syncId,playerInventory,this,world.getBlockState(pos).get(BambooGrateBlock.LAYER),propertyDelegate);
        } else return null;
    }
    public static void tick(World world, BlockPos pos, BlockState state, BambooGrateBlockEntity b) {
        if (world.isClient){
            return;
        }
        if (world.getTime() % 20L == 0L){
            b.availableSlots = state.get(BambooGrateBlock.LAYER) * 4;
            if (b.isHeated !=0 && b.isCovered !=0){
                for (int i = 0;i < b.availableSlots;i++){
                    Optional<RecipeEntry<SteamingRecipe>> match = Objects.requireNonNull(b.getWorld()).getRecipeManager()
                            .getFirstMatch(SteamingRecipe.Type.INSTANCE, new SingleStackRecipeInput(b.getStack(i)),b.getWorld());
                    if (match.isPresent()){
                        int maxProgress = match.get().value().getMaxProgress();
                        int count = b.getStack(i).getCount();
                        if (count <= 4){
                            b.maxProgresses[i] = maxProgress;
                        } else {
                            b.maxProgresses[i] = maxProgress * count / 4;
                        }
                        if (b.progresses[i] < b.maxProgresses[i]){
                            b.progresses[i]++;
                        } else {
                            b.progresses[i] = 0;
                            b.setStack(i,new ItemStack(match.get().value().getResult(null).getItem(),count));
                        }
                    } else {
                        b.progresses[i] = 0;
                    }
                }
            }
            if (!(world.getBlockEntity(pos.down()) instanceof BambooGrateBlockEntity) &&
                    world.getBlockEntity(pos.down(2)) instanceof BurningGasCookingStoveBlockEntity &&
                    world.getBlockState(pos.down()).getBlock().equals(Blocks.WATER_CAULDRON)){
                    b.isHeated = 1;
            } else {
                if (world.getBlockEntity(pos.down()) instanceof BambooGrateBlockEntity blockEntity){
                    if (blockEntity.isHeated == 1 && blockEntity.currentLayer != 0 &&
                    world.getBlockState(pos.down()).get(BambooGrateBlock.LAYER) == 4){
                        b.isHeated = 1;
                    } else b.isHeated = 0;
                } else b.isHeated = 0;
            }
            if(state.get(BambooGrateBlock.COVERED)){
                b.isCovered = 1;
                b.currentLayer = 1;
            } else {
                if (world.getBlockState(pos.up()).getBlock().equals(ModBlocks.BAMBOO_GRATE)
                        && state.get(BambooGrateBlock.LAYER) == 4){
                    if (world.getBlockState(pos.up()).get(BambooGrateBlock.COVERED)){
                        b.isCovered = 1;
                        b.currentLayer = 2;
                    } else {
                        if (world.getBlockState(pos.up(2)).getBlock().equals(ModBlocks.BAMBOO_GRATE) &&
                                world.getBlockState(pos.up()).get(BambooGrateBlock.LAYER) == 4){
                            if (world.getBlockState(pos.up(2)).get(BambooGrateBlock.COVERED)){
                                b.isCovered = 1;
                                b.currentLayer = 3;
                            } else {
                                b.isCovered = 0;
                                if (world.getBlockState(pos.up(3)).getBlock().equals(ModBlocks.BAMBOO_GRATE)){
                                    b.currentLayer = 0;
                                } else b.currentLayer = 3;
                            }
                        } else {
                            b.isCovered = 0;
                            b.currentLayer = 2;
                        }
                    }
                } else {
                    b.isCovered = 0;
                    b.currentLayer = 1;
                }
            }
            b.markDirty();
        }
    }

    @Override
    public void markDirty() {
        ItemStackSyncS2CPacket.send(pos,getItems(),world);
        super.markDirty();
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[availableSlots];
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
