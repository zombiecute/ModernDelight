package com.zombie_cute.mc.bakingdelight.block.entities;

import com.google.common.collect.Maps;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ACConsumer;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.custom.JarItem;
import com.zombie_cute.mc.bakingdelight.recipe.custom.FreezingRecipe;
import com.zombie_cute.mc.bakingdelight.screen.custom.FreezerScreenHandler;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.tag.ForgeTagKeys;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FreezerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory, ACConsumer {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(20,ItemStack.EMPTY);
    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;
    private static final int ICE_SLOT = 3;
    private static final int OUTPUT_SLOT = 4;
    public static final String FREEZER_NAME = "display_name.bakingdelight.freezer_name";
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 400;
    private int coolTime = 0;
    private int maxCoolTime = 1;
    private int experiences = 0;
    public FreezerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FREEZER_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FreezerBlockEntity.this.progress;
                    case 1 -> FreezerBlockEntity.this.maxProgress;
                    case 2 -> FreezerBlockEntity.this.coolTime;
                    case 3 -> FreezerBlockEntity.this.maxCoolTime;
                    case 4 -> FreezerBlockEntity.this.experiences;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 :{
                        FreezerBlockEntity.this.progress = value;break;
                    }
                    case 1 :{
                        FreezerBlockEntity.this.maxProgress = value;break;
                    }
                    case 2 :{
                        FreezerBlockEntity.this.coolTime = value;break;
                    }
                    case 3 :{
                        FreezerBlockEntity.this.maxCoolTime = value;break;
                    }
                    case 4 :{
                        FreezerBlockEntity.this.experiences = value;break;
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
    public void playSound(SoundEvent sound, float volume, float pitch) {
        Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, pitch);
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("freezer.progress",progress);
        nbt.putInt("freezer.fuelTime", coolTime);
        nbt.putInt("freezer.maxCoolTime", maxCoolTime);
        nbt.putInt("freezer.experiences", experiences);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("freezer.progress");
        coolTime = nbt.getInt("freezer.coolTime");
        maxCoolTime = nbt.getInt("freezer.maxCoolTime");
        experiences = nbt.getInt("freezer.experiences");
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(FREEZER_NAME);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FreezerScreenHandler(syncId, playerInventory,this,this.propertyDelegate);
    }
    public void tick(World world, BlockPos pos, BlockState state, FreezerBlockEntity entity){
        if (world.isClient){
            return;
        }
        if (isCool()){
            --coolTime;
            if (world.getTime() % 100L == 0L){
                playSound(ModSounds.BLOCK_FREEZER_RUNNING,0.3f,0.8f);
            }
            if (isOutputSlotEmptyOrReceivable()){
                if (hasRecipe(entity)){
                    increaseCraftProgress();
                    markDirty(world, pos, state);
                    if (hasCraftingFinished()){
                        craftItem(entity);
                        for (int i = 0;i < 3;i++){
                            if (getStack(i).getItem() instanceof JarItem){
                                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(ModItems.JAR));
                            }
                            if (getStack(i).getItem() instanceof BucketItem){
                                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(Items.BUCKET));
                            }
                        }
                        removeStack(INPUT_SLOT_1,1);
                        removeStack(INPUT_SLOT_2,1);
                        removeStack(INPUT_SLOT_3,1);
                        resetProgress();
                    }
                } else {
                    if (this.progress != 0){
                        resetProgress();
                    }
                }
            }
        } else {
            if (hasRecipe(entity)){
                progress--;
            } else {
                resetProgress();
            }
            maxCoolTime = 60;
            markDirty(world, pos, state);
        }
        if (canUseAsIce(this.getStack(3))&&this.coolTime == 0){
            ItemStack ice = this.getStack(3);
            this.coolTime = this.getCoolTime(ice);
            maxCoolTime = coolTime;
            if (this.getStack(ICE_SLOT).getItem() == Items.POWDER_SNOW_BUCKET){
                this.setStack(ICE_SLOT, Items.BUCKET.getDefaultStack());
            } else {
                this.removeStack(ICE_SLOT,1);
            }
        }
    }


    public static Map<Item, Integer> createCoolTimeMap() {
        LinkedHashMap<Item, Integer> map = Maps.newLinkedHashMap();
        FreezerBlockEntity.addIce(map, Items.ICE, 20);
        FreezerBlockEntity.addIce(map, Items.PACKED_ICE, 190);
        FreezerBlockEntity.addIce(map, Items.BLUE_ICE, 1830);
        FreezerBlockEntity.addIce(map, Items.SNOW_BLOCK, 15);
        FreezerBlockEntity.addIce(map, Items.SNOW, 10);
        FreezerBlockEntity.addIce(map, Items.POWDER_SNOW_BUCKET, 400);
        FreezerBlockEntity.addIce(map, ForgeTagKeys.COLD_ITEMS, 3);
        return map;
    }
    private static void addIce(Map<Item, Integer> coolTimes, TagKey<Item> tag, int coolTime) {
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(tag)) {
            coolTimes.put(registryEntry.value(), coolTime);
        }
    }
    private static void addIce(Map<Item, Integer> coolTimes, ItemConvertible item, int coolTime) {
        Item item2 = item.asItem();
        coolTimes.put(item2, coolTime);
    }
    public static boolean canUseAsIce(ItemStack stack){
        return FreezerBlockEntity.createCoolTimeMap().containsKey(stack.getItem());
    }
    private int getCoolTime(ItemStack ice){
        if (ice.isEmpty()){
            return 0;
        }
        Item item = ice.getItem();
        return FreezerBlockEntity.createCoolTimeMap().getOrDefault(item,0);
    }
    private boolean isCool(){
        return this.coolTime > 0;
    }
    private void resetProgress(){
        this.progress = 0;
    }
    private void craftItem(FreezerBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for(int i = 0; i< entity.size();i++){
            inventory.setStack(i,entity.getStack(i));
        }
        Optional<FreezingRecipe> match = Objects.requireNonNull(entity.getWorld()).getRecipeManager()
                .getFirstMatch(FreezingRecipe.Type.INSTANCE, inventory,entity.getWorld());
        experiences += 3;
        this.setStack(OUTPUT_SLOT, new ItemStack(match.get().getOutput(null).getItem(),
                getStack(OUTPUT_SLOT).getCount() + match.get().getOutput(null).getCount()));
    }
    private boolean hasCraftingFinished(){
        return progress >= maxProgress;
    }
    private void increaseCraftProgress(){
        progress++;
    }
    private boolean hasRecipe(FreezerBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for(int i = 0; i< entity.size();i++){
            inventory.setStack(i,entity.getStack(i));
        }
        Optional<FreezingRecipe> match = Objects.requireNonNull(entity.getWorld()).getRecipeManager()
                .getFirstMatch(FreezingRecipe.Type.INSTANCE, inventory,entity.getWorld());

        if (entity.world != null) {
            return match.isPresent() &&
                    canInsertAmountIntoOutputSlot(match.get().getOutput(null)) &&
                    canInsertItemIntoOutputSlot(match.get().getOutput(entity.world.getRegistryManager()).getItem());
        } else return false;
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
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
        if (dir == Direction.UP && slot >= 5){
            return true;
        } else if (dir == Direction.NORTH && slot == 3) {
            return true;
        } else if (dir == Direction.WEST && slot == 0) {
            return true;
        } else if (dir == Direction.SOUTH && slot == 1) {
            return true;
        } else return dir == Direction.EAST && slot == 2;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return dir == Direction.DOWN && slot >= 4;
    }
    public ItemStack getIceSlot(){
        if (world != null) {
            return this.getStack(ICE_SLOT);
        } else return ItemStack.EMPTY;
    }
    public ItemStack getCraftSlot(){
        if (world != null) {
            if (this.getStack(OUTPUT_SLOT).isEmpty()){
                return this.getStack(INPUT_SLOT_2);
            } else {
                return this.getStack(OUTPUT_SLOT);
            }
        } else return ItemStack.EMPTY;
    }
    public ItemStack getSlot1(){
        if (world != null) {
            return this.getStack(5);
        } else return ItemStack.EMPTY;
    }
    public ItemStack getSlot2(){
        if (world != null) {
            return this.getStack(6);
        } else return ItemStack.EMPTY;
    }
    public void setExperience(int experiences){
        this.experiences = experiences;
        markDirty();
    }
    public int getExperience(){
        return experiences;
    }

    @Override
    public int getConsumedValue() {
        return 15;
    }

    @Override
    public boolean isWorking() {
        return !this.isCool();
    }

    @Override
    public void energize() {
        if (!this.isCool()){
            this.coolTime = 60;
        }
    }
}
