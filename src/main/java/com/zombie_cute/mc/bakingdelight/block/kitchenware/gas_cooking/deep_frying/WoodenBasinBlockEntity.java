package com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.recipe.custom.SqueezeRecipe;
import com.zombie_cute.mc.bakingdelight.screen.custom.WoodenBasinScreenHandler;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.FluidStack;
import com.zombie_cute.mc.bakingdelight.util.block_util.FluidStorageAble;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class WoodenBasinBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos>, SidedInventory, FluidStorageAble {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5,ItemStack.EMPTY);
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int FILTER_SLOT = 2;
    private static final int INGREDIENT_SLOT = 3;
    private static final int IMPURITIES_SLOT = 4;
    public static final int MAX_FLUID_LEVEL = 81000;
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
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

    @Override
    public SingleVariantStorage<FluidVariant> getFluidStorage() {
        return fluidStorage;
    }

    public static final String WOODEN_BASIN_NAME = "display_name.bakingdelight.wooden_basin_name";
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    public WoodenBasinBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WOODEN_BASIN_BLOCK_ENTITY, pos, state);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt,inventory,registryLookup);
        nbt.putLong("wooden_basin.fluid_amount", fluidStorage.amount);
        nbt.putString("wooden_basin.fluid_variant",fluidStorage.variant.getRegistryEntry().getIdAsString());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt,inventory,registryLookup);
        FluidStack fluidStack;
        try {
            fluidStack = FluidStack.getFluidStack(nbt.getString("wooden_basin.fluid_variant"),nbt.getLong("wooden_basin.fluid_amount"));
        } catch (Exception ignored) {
            fluidStack = new FluidStack(FluidVariant.of(Fluids.WATER),nbt.getLong("wooden_basin.fluid_amount"));
        }
        fluidStorage.variant = fluidStack.fluidVariant;
        fluidStorage.amount = fluidStack.getAmount();
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
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
        if (stack.getItem().equals(Items.GLASS_BOTTLE)||stack.getItem().equals(Items.BUCKET))return slot == INPUT_SLOT;
        else if (isFilter(stack.getItem())) return slot == FILTER_SLOT;
        else return slot == INGREDIENT_SLOT;
    }
    public void playSound(SoundEvent sound, float volume, boolean isRandom) {
        if (isRandom){
            Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, world.random.nextFloat()+0.8f);
        } else {
            Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, 1.0f);
        }
    }
    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == OUTPUT_SLOT || slot == IMPURITIES_SLOT;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(WOODEN_BASIN_NAME);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new WoodenBasinScreenHandler(syncId, playerInventory,this);
    }
    public static boolean isFilter(Item item){
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.FILTERS)){
            if (item == registryEntry.value()){
                return true;
            }
        }
        return false;
    }
    private boolean isOil(Fluid fluid){
        for (RegistryEntry<Fluid> registryEntry : Registries.FLUID.iterateEntries(TagKeys.OIL)){
            if (fluid == registryEntry.value()){
                return true;
            }
        }
        return false;
    }
    public void onLandedUpon(World world, LivingEntity entity) {
        if (world.isClient){
            return;
        }
        Optional<RecipeEntry<SqueezeRecipe>> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                .getFirstMatch(SqueezeRecipe.Type.INSTANCE, new SingleStackRecipeInput(getStack(INGREDIENT_SLOT)),this.getWorld());
        if (match.isPresent()){
            FluidStack outputFluid = match.get().value().getOutputFluid();
            if (
                    (fluidStorage.variant.isOf(outputFluid.getFluidVariant().getFluid()) ||
                            fluidStorage.variant.isBlank())
                            && fluidStorage.amount != MAX_FLUID_LEVEL
                            && isFilter(getStack(FILTER_SLOT).getItem())
            ){
                fluidStorage.variant = outputFluid.getFluidVariant();
                if ((fluidStorage.amount + outputFluid.getAmount()) <= MAX_FLUID_LEVEL){
                    fluidStorage.amount += outputFluid.getAmount();
                } else {
                    fluidStorage.amount = MAX_FLUID_LEVEL;
                }
                ItemStack outputStack = match.get().value().getResult(null);
                int damage = getStack(FILTER_SLOT).getDamage();
                if (damage < getStack(FILTER_SLOT).getMaxDamage()){
                    getStack(FILTER_SLOT).setDamage(damage+1);
                } else {
                    playSound(SoundEvents.ENTITY_ITEM_BREAK,1.2f,true);
                    setStack(FILTER_SLOT,ItemStack.EMPTY);
                }
                playSound(SoundEvents.BLOCK_HONEY_BLOCK_BREAK,1.8f,true);
                removeStack(INGREDIENT_SLOT,1);
                if (getStack(IMPURITIES_SLOT).isEmpty()){
                    setStack(IMPURITIES_SLOT,outputStack);
                } else if (getStack(IMPURITIES_SLOT).getItem().equals(outputStack.getItem()) &&
                        getStack(IMPURITIES_SLOT).getCount()+outputStack.getCount()<=getStack(IMPURITIES_SLOT).getMaxCount()) {
                    int count = getStack(IMPURITIES_SLOT).getCount() + outputStack.getCount();
                    setStack(IMPURITIES_SLOT, new ItemStack(outputStack.getItem(),count));
                } else {
                    ItemScatterer.spawn(world,pos.getX(), pos.getY(), pos.getZ(),outputStack.copy());
                }
                if (match.get().value().isDanger()){
                    entity.damage(world.getDamageSources().cactus(),1.5f);
                }
                if (match.get().value().doCreateFire()){
                    entity.setOnFireFor(5);
                    createFire(world);
                }
            }
        }
        markDirty();
    }
    private boolean canLightFire(World world, BlockPos pos) {
        Direction[] dirs = Direction.values();
        for (Direction direction : dirs) {
            if (this.hasBlock(world, pos.offset(direction))) {
                return true;
            }
        }
        return false;
    }
    private boolean hasBlock(World world, BlockPos pos) {
        return (pos.getY() < world.getBottomY() || pos.getY() >= world.getTopY() || world.isChunkLoaded(ChunkSectionPos.getSectionCoord(pos.getX()), ChunkSectionPos.getSectionCoord(pos.getZ()))) && !world.getBlockState(pos).isAir();
    }
    private void createFire(World world){
        if (world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            BlockPos blockPos = pos;
            for(int j = 0; j < 5; ++j) {
                blockPos = blockPos.add(world.random.nextInt(3) - 1, 1, world.random.nextInt(3) - 1);
                if (!world.canSetBlock(blockPos)) {
                    return;
                }
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isAir()) {
                    if (this.canLightFire(world, blockPos)) {
                        world.setBlockState(blockPos, AbstractFireBlock.getState(world, blockPos));
                        return;
                    }
                } else if (blockState.blocksMovement()) {
                    return;
                }
            }
            for(int k = 0; k < 5; ++k) {
                BlockPos blockPos2 = pos.add(world.random.nextInt(3) - 1, 0, world.random.nextInt(3) - 1);
                if (!world.canSetBlock(blockPos2)) {
                    return;
                }
                if (world.isAir(blockPos2.up()) && this.hasBlock(world, blockPos2)) {
                    world.setBlockState(blockPos2.up(), AbstractFireBlock.getState(world, blockPos2));
                }
            }
        }
    }
    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
            ItemStackSyncS2CPacket.send(pos,getItems(),world);
            sendFluidPacket(world,pos);
        }
        super.markDirty();
    }
    public static void tick(World world, BlockPos pos, BlockState state, WoodenBasinBlockEntity b) {
        if(world.isClient){
            return;
        }
        if (b.getStack(INPUT_SLOT).getItem().equals(Items.BUCKET) &&
                b.fluidStorage.amount== MAX_FLUID_LEVEL &&
                b.getStack(OUTPUT_SLOT).isEmpty()){
            b.removeStack(INPUT_SLOT,1);
            b.setStack(OUTPUT_SLOT,b.fluidStorage.variant.getFluid().getBucketItem().getDefaultStack());
            b.fluidStorage.amount = 0;
            b.fluidStorage.variant = FluidVariant.blank();
            b.markDirty();
        } else if (b.isVegetableOil()){
            b.fluidStorage.amount -= 27000;
            if (b.fluidStorage.amount == 0){
                b.fluidStorage.variant = FluidVariant.blank();
            }
            b.removeStack(INPUT_SLOT,1);
            int count = b.getStack(OUTPUT_SLOT).getCount();
            b.setStack(OUTPUT_SLOT,new ItemStack(ModItems.VEGETABLE_OIL_BOTTLE,count+1));
            b.markDirty();
        } else if (b.isWater()){
            b.fluidStorage.amount -= 27000;
            if (b.fluidStorage.amount == 0){
                b.fluidStorage.variant = FluidVariant.blank();
            }
            b.removeStack(INPUT_SLOT,1);
            ItemStack waterBottle = new ItemStack(Items.POTION);
            waterBottle.set(DataComponentTypes.POTION_CONTENTS,new PotionContentsComponent(Potions.WATER));
            b.setStack(OUTPUT_SLOT, waterBottle);
            b.markDirty();
        }
    }

    private boolean isVegetableOil() {
        return getStack(INPUT_SLOT).getItem().equals(Items.GLASS_BOTTLE)
                && fluidStorage.amount >= 27000
                && fluidStorage.variant.isOf(ModFluid.STILL_VEGETABLE_OIL)
                && (getStack(OUTPUT_SLOT).isEmpty()
                    || (getStack(OUTPUT_SLOT).getItem().equals(ModItems.VEGETABLE_OIL_BOTTLE)
                        && getStack(OUTPUT_SLOT).getCount() < ModItems.VEGETABLE_OIL_BOTTLE.getMaxCount()
                    )
                );
    }
    private boolean isWater() {
        return getStack(INPUT_SLOT).getItem().equals(Items.GLASS_BOTTLE)
                && fluidStorage.amount >= 27000
                && fluidStorage.variant.isOf(Fluids.WATER)
                && getStack(OUTPUT_SLOT).isEmpty();
    }

    public ItemStack getRendererStack() {
        return getStack(FILTER_SLOT);
    }

    public ItemStack getRendererStack2() {
        return getStack(INGREDIENT_SLOT);
    }
    public FluidStack getFluidStackCopy(){
        return new FluidStack(fluidStorage.variant,fluidStorage.amount);
    }
}
