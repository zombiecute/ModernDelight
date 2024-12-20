package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.AdvanceFurnaceBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.custom.JarItem;
import com.zombie_cute.mc.bakingdelight.recipe.custom.BakingRecipe;
import com.zombie_cute.mc.bakingdelight.recipe.recipeInput.MultiStackRecipeInput;
import com.zombie_cute.mc.bakingdelight.screen.custom.OvenScreenHandler;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.zombie_cute.mc.bakingdelight.block.custom.OvenBlock.FACING;
import static com.zombie_cute.mc.bakingdelight.block.custom.OvenBlock.OVEN_BURNING;

public class OvenBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory, SidedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6,ItemStack.EMPTY);
    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;
    private static final int INPUT_SLOT_4 = 3;
    private static final int FUEL_SLOT = 4;
    private static final int OUTPUT_SLOT = 5;
    public static final String OVEN_NAME = "display_name.bakingdelight.oven_name";
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 200;
    private int burnTime = 0;
    private int maxBurnTime = 1;
    private int experiences = 0;
    private int cachedBurnTime = 0;
    private int cachedMaxBurnTime = 0;
    public OvenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.OVEN_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> OvenBlockEntity.this.progress;
                    case 1 -> OvenBlockEntity.this.maxProgress;
                    case 2 -> OvenBlockEntity.this.burnTime;
                    case 3 -> OvenBlockEntity.this.maxBurnTime;
                    case 4 -> OvenBlockEntity.this.experiences;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 :{
                        OvenBlockEntity.this.progress = value;break;
                    }
                    case 1 :{
                        OvenBlockEntity.this.maxProgress = value;break;
                    }
                    case 2 :{
                        OvenBlockEntity.this.burnTime = value;break;
                    }
                    case 3 :{
                        OvenBlockEntity.this.maxBurnTime = value;break;
                    }
                    case 4 :{
                        OvenBlockEntity.this.experiences = value;break;
                    }
                }
            }

            @Override
            public int size() {
                return 5;
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
        nbt.putInt("oven.progress",progress);
        nbt.putInt("oven.fuelTime", burnTime);
        nbt.putInt("oven.maxFuelTime", maxBurnTime);
        nbt.putInt("oven.experiences", experiences);
        nbt.putInt("oven.cachedBurnTime", cachedBurnTime);
        nbt.putInt("oven.cachedMaxBurnTime", cachedMaxBurnTime);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory,registryLookup);
        progress = nbt.getInt("oven.progress");
        burnTime = nbt.getInt("oven.fuelTime");
        maxBurnTime = nbt.getInt("oven.maxFuelTime");
        experiences = nbt.getInt("oven.experiences");
        cachedBurnTime = nbt.getInt("oven.cachedBurnTime");
        cachedMaxBurnTime = nbt.getInt("oven.cachedMaxBurnTime");
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
        return Text.translatable(OVEN_NAME);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new OvenScreenHandler(syncId, playerInventory, this,this.propertyDelegate);
    }
    public void playSound(SoundEvent sound, float volume, float pitch) {
        Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, pitch);
    }
    private int tick = 20;
    public static void tick(World world, BlockPos pos, BlockState state, OvenBlockEntity entity) {
        entity.tick--;
        if (entity.tick == 0) entity.tick = 20;
        boolean alwaysBurning = world.getBlockEntity(pos.down()) instanceof BurningGasCookingStoveBlockEntity;
        if (alwaysBurning){
            if (entity.burnTime != 0 && entity.cachedBurnTime == 0){
                entity.cachedBurnTime = entity.burnTime;
                entity.cachedMaxBurnTime = entity.maxBurnTime;
            }
            entity.burnTime = 1;
            entity.maxBurnTime = 1;
            world.setBlockState(pos, state.with(OVEN_BURNING,true));
            markDirty(world, pos, state);
            entity.checkAndCraft(world, pos, state, entity);
        } else {
            if (entity.cachedBurnTime != 0){
                entity.burnTime = entity.cachedBurnTime;
                entity.maxBurnTime = entity.cachedMaxBurnTime;
                entity.cachedBurnTime = 0;
                entity.cachedMaxBurnTime = 0;
            }
            if (entity.isFuelBurning()){
                --entity.burnTime;
                world.setBlockState(pos, state.with(OVEN_BURNING,true));
                entity.checkAndCraft(world, pos, state, entity);
            } else {
                if (entity.hasRecipe(entity)){
                    entity.progress--;
                } else {
                    entity.resetProgress();
                }
                entity.maxBurnTime = 1;
                world.setBlockState(pos, state.with(OVEN_BURNING,false));
                markDirty(world, pos, state);
            }
            if (canUseAsFuel(entity.getStack(4))&&(entity.burnTime == 0) && entity.hasRecipe(entity)){
                ItemStack fuel = entity.getStack(4);
                entity.burnTime = entity.getFuelTime(fuel);
                entity.maxBurnTime = entity.burnTime;
                if (entity.getStack(FUEL_SLOT).getItem() == Items.LAVA_BUCKET){
                    entity.setStack(FUEL_SLOT, Items.BUCKET.getDefaultStack());
                } else {
                    entity.removeStack(FUEL_SLOT,1);
                }
            }
        }
    }

    private void checkAndCraft(World world, BlockPos pos, BlockState state, OvenBlockEntity entity) {
        if (isOutputSlotEmptyOrReceivable()){
            if (this.hasRecipe(entity)){
                this.increaseCraftProgress();
                markDirty(world, pos, state);
                if (hasCraftingFinished()){
                    this.craftItem(entity);
                    for (int i = 0; i < 4; i++){
                        if (this.getStack(i).getItem() instanceof BucketItem){
                            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(Items.BUCKET));
                        }
                        if (this.getStack(i).getItem() instanceof JarItem){
                            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModItems.JAR));
                        }
                        if (this.getStack(i).getItem().getRecipeRemainder() == Items.GLASS_BOTTLE){
                            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(Items.GLASS_BOTTLE));
                        }
                    }
                    this.removeStack(INPUT_SLOT_1,1);
                    this.removeStack(INPUT_SLOT_2,1);
                    this.removeStack(INPUT_SLOT_3,1);
                    this.removeStack(INPUT_SLOT_4,1);
                    this.resetProgress();
                }
            }
            else {
                resetProgress();
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
        return  time == null ? 0 : time;
    }
    private boolean isFuelBurning() {
        return this.burnTime > 0;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem(OvenBlockEntity entity) {
        if (this.getStack(INPUT_SLOT_1).getItem().equals(ModItems.BLACK_PEPPER_DUST) &&
                this.getStack(INPUT_SLOT_2).getItem().equals(Items.SUGAR) &&
                this.getStack(INPUT_SLOT_3).getItem().equals(ModItems.BLACK_PEPPER_DUST) &&
                this.getStack(INPUT_SLOT_4).getItem().equals(ModBlocks.RAW_PIZZA_ITEM)){
            ItemStack rawPizzaStack = this.getStack(INPUT_SLOT_4);
            ContainerComponent component = rawPizzaStack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT);
            ItemStack pizzaStack = new ItemStack(ModBlocks.PIZZA_ITEM);
            pizzaStack.set(DataComponentTypes.CONTAINER,component);
            this.setStack(OUTPUT_SLOT,pizzaStack);
        } else {
            List<ItemStack> inventory = new ArrayList<>(entity.size());
            for(int i = 0; i< 4;i++){
                inventory.add(i,entity.getStack(i));
            }
            Optional<RecipeEntry<BakingRecipe>> match = getMatch(entity,inventory);
            this.setStack(OUTPUT_SLOT, new ItemStack(match.get().value().getResult(null).getItem(),
                    getStack(OUTPUT_SLOT).getCount() + match.get().value().getResult(null).getCount()));
        }
        experiences += 4;
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe(OvenBlockEntity entity) {
        if (this.getStack(INPUT_SLOT_1).getItem().equals(ModItems.BLACK_PEPPER_DUST) &&
        this.getStack(INPUT_SLOT_2).getItem().equals(Items.SUGAR) &&
        this.getStack(INPUT_SLOT_3).getItem().equals(ModItems.BLACK_PEPPER_DUST) &&
        this.getStack(INPUT_SLOT_4).getItem().equals(ModBlocks.RAW_PIZZA_ITEM) &&
        this.getStack(OUTPUT_SLOT).isEmpty()){
            return true;
        }
        List<ItemStack> inventory = new ArrayList<>(entity.size());
        for(int i = 0; i< 4;i++){
            inventory.add(i,entity.getStack(i));
        }
        Optional<RecipeEntry<BakingRecipe>> match = getMatch(entity, inventory);
        if (match.isPresent()){
            ItemStack output = match.get().value().getResult(null);
            int count = output.getCount();
            return this.getStack(OUTPUT_SLOT).getCount() + count <= this.getStack(OUTPUT_SLOT).getMaxCount() ||
                    this.getStack(OUTPUT_SLOT).isEmpty() && this.getStack(OUTPUT_SLOT).getItem() != output.getItem();
        }
        return false;
    }

    private Optional<RecipeEntry<BakingRecipe>> getMatch(OvenBlockEntity entity, List<ItemStack> inventory) {
        return Objects.requireNonNull(world).getRecipeManager()
                .getFirstMatch(BakingRecipe.Type.INSTANCE, new MultiStackRecipeInput(inventory,4), entity.getWorld());
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
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
        if (dir == Direction.UP && slot == 4){
            return true;
        } else if (dir == Direction.EAST && slot == 0) {
            return true;
        } else if (dir == Direction.SOUTH && slot == 1) {
            return true;
        } else if (dir == Direction.WEST && slot == 2) {
            return true;
        } else return dir == Direction.NORTH && slot == 3;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (dir == Direction.DOWN && slot == 4){
            return stack.isOf(Items.BUCKET);
        } else return dir == Direction.DOWN && slot == 5;
    }

    public void onUse(BlockState state, World world) {
        playSound(SoundEvents.BLOCK_STONE_BREAK,2.3f,world.random.nextFloat()/2 +0.5f);
        ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),ModBlocks.BAKING_TRAY.asItem().getDefaultStack());
        world.setBlockState(pos, ModBlocks.ADVANCE_FURNACE.getDefaultState().with(AdvanceFurnaceBlock.FACING,state.get(FACING)).with(AdvanceFurnaceBlock.BURNING,false));
    }
    public void setExperience(int experiences){
        this.experiences = experiences;
        markDirty();
    }
    public int getExperience(){
        return experiences;
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }
}