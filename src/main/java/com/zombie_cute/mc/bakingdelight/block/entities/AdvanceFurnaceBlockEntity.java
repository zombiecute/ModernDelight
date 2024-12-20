package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.AdvanceFurnaceBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.OvenBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.screen.custom.AdvanceFurnaceScreenHandler;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class AdvanceFurnaceBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory, SidedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9,ItemStack.EMPTY);
    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;
    private static final int INPUT_SLOT_4 = 3;
    private static final int OUTPUT_SLOT_1 = 4;
    private static final int OUTPUT_SLOT_2 = 5;
    private static final int OUTPUT_SLOT_3 = 6;
    private static final int OUTPUT_SLOT_4 = 7;
    private static final int FUEL_SLOT = 8;
    public static final String ADVANCE_FURNACE_NAME = "display_name.bakingdelight.advance_furnace_name";
    protected final PropertyDelegate propertyDelegate;
    private int progress_1 = 0;
    private int progress_2 = 0;
    private int progress_3 = 0;
    private int progress_4 = 0;
    private int maxProgress = 200;
    private int burnTime = 0;
    private int maxBurnTime = 1;
    private int experience = 0;
    private int cachedBurnTime = 0;
    private int cachedMaxBurnTime = 0;
    public AdvanceFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ADVANCE_FURNACE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> AdvanceFurnaceBlockEntity.this.progress_1;
                    case 1 -> AdvanceFurnaceBlockEntity.this.progress_2;
                    case 2 -> AdvanceFurnaceBlockEntity.this.progress_3;
                    case 3 -> AdvanceFurnaceBlockEntity.this.progress_4;
                    case 4 -> AdvanceFurnaceBlockEntity.this.maxProgress;
                    case 5 -> AdvanceFurnaceBlockEntity.this.burnTime;
                    case 6 -> AdvanceFurnaceBlockEntity.this.maxBurnTime;
                    case 7 -> AdvanceFurnaceBlockEntity.this.experience;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 :{
                        AdvanceFurnaceBlockEntity.this.progress_1 = value;break;
                    }
                    case 1 :{
                        AdvanceFurnaceBlockEntity.this.progress_2 = value;break;
                    }
                    case 2 :{
                        AdvanceFurnaceBlockEntity.this.progress_3 = value;break;
                    }
                    case 3 :{
                        AdvanceFurnaceBlockEntity.this.progress_4 = value;break;
                    }
                    case 4 :{
                        AdvanceFurnaceBlockEntity.this.maxProgress = value;break;
                    }
                    case 5 :{
                        AdvanceFurnaceBlockEntity.this.burnTime = value;break;
                    }
                    case 6 :{
                        AdvanceFurnaceBlockEntity.this.maxBurnTime = value;break;
                    }
                    case 7 :{
                        AdvanceFurnaceBlockEntity.this.experience = value;break;
                    }
                }
            }

            @Override
            public int size() {
                return 8;
            }
        };
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory,registryLookup);
        nbt.putInt("advance_furnace.progress_1", progress_1);
        nbt.putInt("advance_furnace.progress_2", progress_2);
        nbt.putInt("advance_furnace.progress_3", progress_3);
        nbt.putInt("advance_furnace.progress_4", progress_4);
        nbt.putInt("advance_furnace.burnTime", burnTime);
        nbt.putInt("advance_furnace.maxBurnTime", maxBurnTime);
        nbt.putInt("advance_furnace.experience", experience);
        nbt.putInt("advance_furnace.cachedBurnTime", cachedBurnTime);
        nbt.putInt("advance_furnace.cachedMaxBurnTime", cachedMaxBurnTime);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory,registryLookup);
        progress_1 = nbt.getInt("advance_furnace.progress_1");
        progress_2 = nbt.getInt("advance_furnace.progress_2");
        progress_3 = nbt.getInt("advance_furnace.progress_3");
        progress_4 = nbt.getInt("advance_furnace.progress_4");
        burnTime = nbt.getInt("advance_furnace.burnTime");
        maxBurnTime = nbt.getInt("advance_furnace.maxBurnTime");
        experience = nbt.getInt("advance_furnace.experience");
        cachedBurnTime = nbt.getInt("advance_furnace.cachedBurnTime");
        cachedMaxBurnTime = nbt.getInt("advance_furnace.cachedMaxBurnTime");
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(ADVANCE_FURNACE_NAME);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AdvanceFurnaceScreenHandler(syncId, playerInventory,
                this,this.propertyDelegate);
    }
    public void playSound(SoundEvent sound, float volume, float pitch) {
        Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, pitch);
    }
    int tick = 20;
    private boolean alwaysBurning = false;
    public static void tick(World world, BlockPos pos, BlockState state, AdvanceFurnaceBlockEntity entity) {
        if (world.isClient){
            return;
        }
        entity.tick--;
        if (entity.tick==0) entity.tick = 20;
        entity.alwaysBurning(world.getBlockEntity(pos.down()) instanceof BurningGasCookingStoveBlockEntity);
        if (entity.alwaysBurning){
            if (entity.burnTime != 0 && entity.cachedBurnTime == 0){
                entity.cachedBurnTime = entity.burnTime;
                entity.cachedMaxBurnTime = entity.maxBurnTime;
            }
            entity.maxBurnTime = 1;
            entity.burnTime = 1;
            world.setBlockState(pos, state.with(AdvanceFurnaceBlock.BURNING,true));
            markDirty(world, pos, state);
            entity.checkStack(entity,state,INPUT_SLOT_1,OUTPUT_SLOT_1);
            entity.checkStack(entity,state,INPUT_SLOT_2,OUTPUT_SLOT_2);
            entity.checkStack(entity,state,INPUT_SLOT_3,OUTPUT_SLOT_3);
            entity.checkStack(entity,state,INPUT_SLOT_4,OUTPUT_SLOT_4);
        } else {
            if (entity.cachedBurnTime != 0){
                entity.burnTime = entity.cachedBurnTime;
                entity.maxBurnTime = entity.cachedMaxBurnTime;
                entity.cachedBurnTime = 0;
                entity.cachedMaxBurnTime = 0;
            }
            if (entity.isFuelBurning()){
                --entity.burnTime;
                world.setBlockState(pos, state.with(AdvanceFurnaceBlock.BURNING,true));
                markDirty(world, pos, state);
                entity.checkStack(entity,state,INPUT_SLOT_1,OUTPUT_SLOT_1);
                entity.checkStack(entity,state,INPUT_SLOT_2,OUTPUT_SLOT_2);
                entity.checkStack(entity,state,INPUT_SLOT_3,OUTPUT_SLOT_3);
                entity.checkStack(entity,state,INPUT_SLOT_4,OUTPUT_SLOT_4);
            } else {
                entity.decreaseCraftProgress();
                entity.maxBurnTime = 1;
                world.setBlockState(pos, state.with(AdvanceFurnaceBlock.BURNING,false));
            }
            if (canUseAsFuel(entity.getStack(FUEL_SLOT))&&(entity.burnTime == 0)&&
                    (entity.hasRecipe(entity,INPUT_SLOT_1,OUTPUT_SLOT_1)||entity.hasRecipe(entity,INPUT_SLOT_2,OUTPUT_SLOT_3)
                            ||entity.hasRecipe(entity,INPUT_SLOT_3,OUTPUT_SLOT_3)||entity.hasRecipe(entity,INPUT_SLOT_4,OUTPUT_SLOT_4))
            ){
                ItemStack fuel = entity.getStack(FUEL_SLOT);
                entity.burnTime = entity.getFuelTime(fuel);
                entity.maxBurnTime = entity.burnTime;
                if (entity.getStack(FUEL_SLOT).getItem() == Items.LAVA_BUCKET){
                    entity.setStack(FUEL_SLOT, Items.BUCKET.getDefaultStack());
                } else {
                    entity.removeStack(FUEL_SLOT,1);
                }
                markDirty(world, pos, state);
            }
        }
    }

    private void checkStack(AdvanceFurnaceBlockEntity entity,BlockState state,int inputSlot1, int outputSlot1) {
        if (isOutputSlotEmptyOrReceivable(outputSlot1)){
            if (hasRecipe(entity,inputSlot1,outputSlot1)){
                this.increaseCraftProgress(inputSlot1+1);
                if (world != null) {
                    markDirty(world, pos, state);
                }
                if (hasCraftingFinished(inputSlot1+1)){
                    this.craftItem(entity,inputSlot1,outputSlot1);
                    this.removeStack(inputSlot1,1);
                    this.resetProgress(inputSlot1+1);
                }
            }
            else {
                switch (inputSlot1+1){
                    case 1:{
                        if (this.progress_1 != 0){
                            resetProgress(1);
                        }break;
                    }
                    case 2:{
                        if (this.progress_2 != 0){
                            resetProgress(2);
                        }break;
                    }
                    case 3:{
                        if (this.progress_3 != 0){
                            resetProgress(3);
                        }break;
                    }
                    case 4:{
                        if (this.progress_4 != 0){
                            resetProgress(4);
                        }break;
                    }
                }
                if (world != null) {
                    markDirty(world, pos, state);
                }
            }
        }
    }

    public static boolean canUseAsFuel(ItemStack stack) {
        return FuelRegistry.INSTANCE.get(stack.getItem()) != null;
    }
    private int getFuelTime(ItemStack fuel){
        if (fuel.isEmpty()) {
            return 0;
        }
        Integer time = FuelRegistry.INSTANCE.get(fuel.getItem());
        return  time == null ? 0 : time / 4;
    }

    private boolean isFuelBurning() {
        return this.burnTime > 0;
    }

    private void resetProgress(int slot) {
        switch (slot){
            case 1: this.progress_1 = 0;break;
            case 2: this.progress_2 = 0;break;
            case 3: this.progress_3 = 0;break;
            case 4: this.progress_4 = 0;break;
        }
    }

    private void craftItem(AdvanceFurnaceBlockEntity entity,int inputSlot,int outputSlot) {
        Optional<RecipeEntry<SmeltingRecipe>> match = Objects.requireNonNull(entity.getWorld()).getRecipeManager()
                .getFirstMatch(RecipeType.SMELTING, new SingleStackRecipeInput(entity.getStack(inputSlot)),entity.getWorld());
        experience += (int) (match.get().value().getExperience() * 10);
        this.setStack(outputSlot, new ItemStack(match.get().value().getResult(null).getItem(),
                getStack(outputSlot).getCount() + match.get().value().getResult(null).getCount()));
    }
    public void setExperience(int value){
        this.experience = value;
        markDirty();
    }
    public int getExperience(){
        return experience/10;
    }
    private boolean hasCraftingFinished(int slot) {
        return switch (slot) {
            case 1 -> progress_1 >= maxProgress;
            case 2 -> progress_2 >= maxProgress;
            case 3 -> progress_3 >= maxProgress;
            case 4 -> progress_4 >= maxProgress;
            default -> false;
        };
    }

    private void increaseCraftProgress(int slot) {
        switch (slot){
            case 1: progress_1++;break;
            case 2: progress_2++;break;
            case 3: progress_3++;break;
            case 4: progress_4++;break;
        }
    }
    private void decreaseCraftProgress() {
        if (progress_1 > 0)progress_1--;
        if (progress_2 > 0)progress_2--;
        if (progress_3 > 0)progress_3--;
        if (progress_4 > 0)progress_4--;
    }

    private boolean hasRecipe(AdvanceFurnaceBlockEntity entity, int inputSlot, int outputSlot) {
        Optional<RecipeEntry<SmeltingRecipe>> match = Objects.requireNonNull(entity.getWorld()).getRecipeManager()
                .getFirstMatch(RecipeType.SMELTING, new SingleStackRecipeInput(entity.getStack(inputSlot)),entity.getWorld());

        if (entity.world != null) {
            return match.isPresent() &&
                    canInsertAmountIntoOutputSlot(match.get().value().getResult(null),outputSlot) &&
                    canInsertItemIntoOutputSlot(match.get().value().getResult(entity.world.getRegistryManager()).getItem(),outputSlot);
        }
        return false;
    }

    private boolean canInsertItemIntoOutputSlot(Item item, int slot) {
        return this.getStack(slot).getItem() == item || this.getStack(slot).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result, int slot) {
        return this.getStack(slot).getCount() + result.getCount() <= getStack(slot).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable(int slot) {
        return this.getStack(slot).isEmpty() || this.getStack(slot).getCount() < this.getStack(slot).getMaxCount();
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
        if (dir == Direction.UP && slot >= 0 && slot <= 3){
            return true;
        } else return (
                dir == Direction.EAST ||
                dir == Direction.SOUTH ||
                dir == Direction.WEST ||
                dir == Direction.NORTH
        ) && slot == 8;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (dir == Direction.DOWN && slot == 8){
            return stack.isOf(Items.BUCKET);
        } else return dir == Direction.DOWN && slot >= 4 && slot <=7 ;
    }
    public void onUse(World world,BlockState state){
        playSound(SoundEvents.BLOCK_STONE_PLACE,2.3f,world.random.nextFloat()+0.5f);
        world.setBlockState(pos, ModBlocks.OVEN.getDefaultState().with(OvenBlock.FACING,state.get(AdvanceFurnaceBlock.FACING)).with(OvenBlock.OVEN_BURNING,false));
    }

    public void alwaysBurning(boolean b) {
        this.alwaysBurning = b;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}
