package com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying;

import com.google.common.collect.Maps;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.screen.custom.WoodenBasinScreenHandler;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.FluidUtil;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
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

import java.util.*;

public class WoodenBasinBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory, SidedInventory {
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
            return FluidVariant.of(ModFluid.STILL_VEGETABLE_OIL);
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
    public static final String WOODEN_BASIN_NAME = "display_name.bakingdelight.wooden_basin_name";
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    public WoodenBasinBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WOODEN_BASIN_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return (int) FluidUtil.convertDropletsToMb(WoodenBasinBlockEntity.this.fluidStorage.amount);
            }

            @Override
            public void set(int index, int value) {}

            @Override
            public int size() {
                return 1;
            }
        };
    }
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,inventory);
        nbt.putLong("wooden_basin.fluid_amount", fluidStorage.amount);
        nbt.put("wooden_basin.fluid_variant",fluidStorage.variant.toNbt());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,inventory);
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("wooden_basin.fluid_variant"));
        fluidStorage.amount = nbt.getLong("wooden_basin.fluid_amount");
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
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
        return new WoodenBasinScreenHandler(syncId, playerInventory,this,this.propertyDelegate);
    }
    private boolean isFilter(Item item){
        List<Item> filterItems = new ArrayList<>();
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.FILTERS)){
            filterItems.add(registryEntry.value());
        }
        return filterItems.contains(item);
    }
    public void onLandedUpon(World world) {
        if (world.isClient){
            return;
        }
        if (fluidStorage.amount != MAX_FLUID_LEVEL && canCreateOil(getStack(INGREDIENT_SLOT))
                &&isFilter(getStack(FILTER_SLOT).getItem())){
            if (fluidStorage.amount + getOil(getStack(INGREDIENT_SLOT)) <= MAX_FLUID_LEVEL){
                fluidStorage.amount += getOil(getStack(INGREDIENT_SLOT));
            } else {
                fluidStorage.amount = MAX_FLUID_LEVEL;
            }
            ItemStack stack = getStack(FILTER_SLOT);
            int damage = stack.getDamage();
            if (damage < stack.getMaxDamage()){
                stack.setDamage(damage+1);
                setStack(FILTER_SLOT,stack);
            } else {
                playSound(SoundEvents.ENTITY_ITEM_BREAK,1.2f,true);
                setStack(FILTER_SLOT,ItemStack.EMPTY);
            }
            playSound(SoundEvents.BLOCK_HONEY_BLOCK_BREAK,1.8f,true);
            removeStack(INGREDIENT_SLOT,1);
            if (getStack(IMPURITIES_SLOT).isEmpty()){
                setStack(IMPURITIES_SLOT,ModItems.OIL_IMPURITY.getDefaultStack());
            } else if (getStack(IMPURITIES_SLOT).getItem().equals(ModItems.OIL_IMPURITY) &&
                    getStack(IMPURITIES_SLOT).getCount()!=getStack(IMPURITIES_SLOT).getMaxCount()) {
                int count = getStack(IMPURITIES_SLOT).getCount();
                setStack(IMPURITIES_SLOT, new ItemStack(ModItems.OIL_IMPURITY,count+1));
            } else {
                spawnImpurity(world,pos);
            }
        }
        markDirty();
    }

    private void spawnImpurity(World world, BlockPos pos) {
        ItemScatterer.spawn(world,pos.getX(), pos.getY(), pos.getZ(),new ItemStack(ModItems.OIL_IMPURITY));
    }
    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
            ItemStackSyncS2CPacket.send(pos,getItems(),world);
        }
        super.markDirty();
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient){
            return;
        }
        if (fluidStorage.amount==0){
            world.setBlockState(pos,state.with(WoodenBasinBlock.HAS_OIL,false));
        } else {
            world.setBlockState(pos,state.with(WoodenBasinBlock.HAS_OIL,true));
        }
        if (getStack(INPUT_SLOT).getItem().equals(Items.BUCKET) &&
                fluidStorage.amount== MAX_FLUID_LEVEL &&
                getStack(OUTPUT_SLOT).isEmpty()){
            fluidStorage.amount = 0;
            removeStack(INPUT_SLOT,1);
            setStack(OUTPUT_SLOT,ModItems.VEGETABLE_OIL_BUCKET.getDefaultStack());
            markDirty();
        } else if (
                getStack(INPUT_SLOT).getItem().equals(Items.GLASS_BOTTLE) && fluidStorage.amount >= 27000 &&
                (
                        getStack(OUTPUT_SLOT).isEmpty()||
                        (
                                getStack(OUTPUT_SLOT).getItem().equals(ModItems.VEGETABLE_OIL_BOTTLE) &&
                                        getStack(OUTPUT_SLOT).getCount()<16
                        )
                )
        ){
            fluidStorage.amount-=27000;
            removeStack(INPUT_SLOT,1);
            int count = getStack(OUTPUT_SLOT).getCount();
            setStack(OUTPUT_SLOT,new ItemStack(ModItems.VEGETABLE_OIL_BOTTLE,count+1));
            markDirty();
        }
    }
    public static Map<Item, Integer> createOilMap() {
        LinkedHashMap<Item, Integer> map = Maps.newLinkedHashMap();
        WoodenBasinBlockEntity.addOil(map, TagKeys.OIL_PLANTS, 9000);
        return map;
    }
    private static void addOil(Map<Item, Integer> coolTimes, TagKey<Item> tag, int oil) {
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(tag)) {
            coolTimes.put(registryEntry.value(), oil);
        }
    }
    private static void addOil(Map<Item, Integer> coolTimes, ItemConvertible item, int oil) {
        Item item2 = item.asItem();
        coolTimes.put(item2, oil);
    }
    public static boolean canCreateOil(ItemStack stack){
        return WoodenBasinBlockEntity.createOilMap().containsKey(stack.getItem());
    }
    private int getOil(ItemStack oil){
        if (oil.isEmpty()){
            return 0;
        }
        Item item = oil.getItem();
        return WoodenBasinBlockEntity.createOilMap().getOrDefault(item,0);
    }

    public ItemStack getRendererStack() {
        return getStack(FILTER_SLOT);
    }

    public ItemStack getRendererStack2() {
        return getStack(INGREDIENT_SLOT);
    }
}
