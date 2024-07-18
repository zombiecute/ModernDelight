package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.DeepFryerBlock;
import com.zombie_cute.mc.bakingdelight.block.custom.GasCanisterBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.item.custom.ModStewItem;
import com.zombie_cute.mc.bakingdelight.recipe.custom.DeepFryingRecipe;
import com.zombie_cute.mc.bakingdelight.screen.custom.DeepFryerScreenHandler;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.tag.ForgeTagKeys;
import com.zombie_cute.mc.bakingdelight.util.ModDamageTypes;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
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

import java.util.Objects;
import java.util.Optional;

public class DeepFryerBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory, SidedInventory {
    public DeepFryerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEEP_FRYER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DeepFryerBlockEntity.this.progress1;
                    case 1 -> DeepFryerBlockEntity.this.progress2;
                    case 2 -> DeepFryerBlockEntity.this.progress3;
                    case 3 -> DeepFryerBlockEntity.this.progress4;
                    case 4 -> DeepFryerBlockEntity.this.isHeated;
                    case 5 -> DeepFryerBlockEntity.this.oilLevel;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 :{
                        DeepFryerBlockEntity.this.progress1 = value;break;
                    }
                    case 1 :{
                        DeepFryerBlockEntity.this.progress2 = value;break;
                    }
                    case 2 :{
                        DeepFryerBlockEntity.this.progress3 = value;break;
                    }
                    case 3 :{
                        DeepFryerBlockEntity.this.progress4 = value;break;
                    }
                    case 4 :{
                        DeepFryerBlockEntity.this.isHeated = value;break;
                    }
                    case 5 :{
                        DeepFryerBlockEntity.this.oilLevel = value;break;
                    }
                }
            }

            @Override
            public int size() {
                return 6;
            }
        };
    }
    private int progress1 = 0;
    private int progress2 = 0;
    private int progress3 = 0;
    private int progress4 = 0;
    private int oilLevel = 0;
    private int isHeated = 0;
    protected final PropertyDelegate propertyDelegate;
    public static final String DEEP_FRYER_NAME = "display_name.bakingdelight.deep_fryer_name";
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4,ItemStack.EMPTY);
    public static final String ADD_OIL = "bakingdelight.deep_fryer_message.need_oil";
    public static final String TOO_HOT = "bakingdelight.deep_fryer_message.too_hot";
    public void useOnButton(BlockState state, World world){
        if (world.isClient){
            return;
        }
        if (!isHeated(state)){
            playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON,1.0f,1.0f);
            world.setBlockState(pos,state.with(DeepFryerBlock.RUNNING,true));
        } else {
            playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,1.0f,1.0f);
            world.setBlockState(pos,state.with(DeepFryerBlock.RUNNING,false));
        }
        markDirty();
        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
    }
    public boolean isBottleOil(Item item){
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(ForgeTagKeys.BOTTLE_OIL)){
            if (item == registryEntry.value()){
                return true;
            }
        }
        return false;
    }
    public boolean isBucketOil(Item item){
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(ForgeTagKeys.BUCKET_OIL)){
            if (item == registryEntry.value()){
                return true;
            }
        }
        return false;
    }
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient){
            ItemStack mainHandItem = player.getMainHandStack();
            ItemStack offHandItem = player.getOffHandStack();
            if (!state.get(DeepFryerBlock.HAS_OIL)){
                if ( !isBottleOil(offHandItem.getItem()) &&
                        !isBottleOil(mainHandItem.getItem()) &&
                        !isBucketOil(offHandItem.getItem()) &&
                        !isBucketOil(mainHandItem.getItem()) &&
                        (
                                !getStack(0).isEmpty() ||
                                !getStack(1).isEmpty() ||
                                !getStack(2).isEmpty() ||
                                !getStack(3).isEmpty()
                        )
                ) {
                    spawnItem(world);
                }
            } else if (mainHandItem.getItem() == ModBlocks.DEEP_FRY_BASKET.asItem() ||
                    offHandItem.getItem() == ModBlocks.DEEP_FRY_BASKET.asItem()) {
                spawnItem(world);
            } else {
                if (player.getOffHandStack().isEmpty()){
                    if (player.getMainHandStack().isEmpty()){
                        spawnItem(world);
                    }
                }
            }
            return;
        }
        ItemStack mainHandItem = player.getMainHandStack();
        ItemStack offHandItem = player.getOffHandStack();
        if (!state.get(DeepFryerBlock.HAS_OIL)){
            if (isBottleOil(offHandItem.getItem())){
                splitOilItem(state, world, pos, player,false, Items.GLASS_BOTTLE);
            } else if (isBottleOil(mainHandItem.getItem())){
                splitOilItem(state, world, pos, player,true,Items.GLASS_BOTTLE);
            } else if (isBucketOil(offHandItem.getItem())){
                splitOilItem(state, world, pos, player,false,Items.BUCKET);
            } else if (isBucketOil(mainHandItem.getItem())){
                splitOilItem(state, world, pos, player,true,Items.BUCKET);
            } else {
                if (getStack(0).isEmpty() && getStack(1).isEmpty() && getStack(2).isEmpty() && getStack(3).isEmpty()){
                    player.sendMessage(Text.translatable(ADD_OIL),true);
                } else {
                    if (mainHandItem.getItem() == ModBlocks.DEEP_FRY_BASKET.asItem() ||
                            offHandItem.getItem() == ModBlocks.DEEP_FRY_BASKET.asItem()){
                        spawnItem(world);
                    } else {
                        spawnItemAndTryDamage(world,player,state);
                    }
                }
            }
        } else if (mainHandItem.getItem() == ModBlocks.DEEP_FRY_BASKET.asItem() ||
                offHandItem.getItem() == ModBlocks.DEEP_FRY_BASKET.asItem()) {
            spawnItem(world);
        } else {
            if (player.getOffHandStack().isEmpty()){
                if (player.getMainHandStack().isEmpty()){
                    spawnItemAndTryDamage(world,player,state);
                } else {
                    checkAndPut(true,player,world);
                }
            } else {
                checkAndPut(false,player,world);
            }
        }
        markDirty();
        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    private void spawnItemAndTryDamage(World world, PlayerEntity player, BlockState state){
        if (isHeated(state)){
            player.damage(ModDamageTypes.of(world,ModDamageTypes.SCALDED),2.0f);
            player.sendMessage(Text.translatable(TOO_HOT),true);
        } else if (state.get(DeepFryerBlock.HAS_OIL)) {
            player.damage(ModDamageTypes.of(world,ModDamageTypes.SCALDED),1.0f);
            player.sendMessage(Text.translatable(TOO_HOT),true);
        }
        spawnItem(world);
    }
    private void spawnItem(World world){
        for(int i=3;i>=0;i--){
            if (!getStack(i).isEmpty()){
                spawnItem(i,world);
                break;
            }
        }
    }
    private void spawnItem(int slot,World world){
        if (world.isClient){
            this.setStack(slot,ItemStack.EMPTY);
            return;
        }
        ItemScatterer.spawn(world,pos.getX()+0.5,pos.getY()+0.8,pos.getZ()+0.5,
                getStack(slot));
        this.setStack(slot,ItemStack.EMPTY);
        playSound(SoundEvents.ENTITY_ITEM_PICKUP,1.3f,world.random.nextFloat()+0.4f);
    }
    private void checkAndPut(boolean isMainHand, PlayerEntity player, World world){
        for (int i=0;i<4;i++){
            if (getStack(i).isEmpty()){
                checkHandAndSplit(isMainHand,player,i);
                playSound(SoundEvents.ENTITY_ITEM_PICKUP,1.0f,world.random.nextFloat()+0.3f);
                break;
            }
        }
    }
    private void checkHandAndDecrement(boolean isMainHand,PlayerEntity player){
        if (isMainHand){
            player.getMainHandStack().decrement(1);
        } else {
            player.getOffHandStack().decrement(1);
        }
    }
    private void checkHandAndSplit(boolean isMainHand,PlayerEntity player,int slot){
        if (isMainHand){
            setStack(slot,player.getMainHandStack().split(1));
        } else {
            setStack(slot,player.getOffHandStack().split(1));
        }
    }
    private void splitOilItem(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean isMainHand, Item item) {
        checkHandAndDecrement(isMainHand,player);
        player.giveItemStack(item.getDefaultStack());
        playSound(SoundEvents.ITEM_BUCKET_EMPTY,1.0f, world.random.nextFloat()+0.3f);
        world.setBlockState(pos, state.with(DeepFryerBlock.HAS_OIL,true));
        oilLevel = 10;
    }
    int maxProgress = 300;
    public void tick(World world, BlockState state, DeepFryerBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        if (progress1 != 0 || progress2 !=0 || progress3 != 0 || progress4 != 0){
            playSound(ModSounds.BLOCK_FOOD_FRYING,0.4f,1.0f);
        }
        if (isHeated(state)){
            isHeated = 1;
            playSound(SoundEvents.BLOCK_FIRE_AMBIENT,0.3f,1.0f);
            Direction dir = state.get(DeepFryerBlock.FACING);
            BlockState neighborState = Blocks.AIR.getDefaultState();
            BlockPos neighborPos = pos;
            switch (dir){
                case EAST -> {
                    neighborPos = pos.offset(Direction.WEST);
                    neighborState = world.getBlockState(neighborPos);
                }
                case SOUTH -> {
                    neighborPos = pos.offset(Direction.NORTH);
                    neighborState = world.getBlockState(neighborPos);
                }
                case WEST -> {
                    neighborPos = pos.offset(Direction.EAST);
                    neighborState = world.getBlockState(neighborPos);
                }
                case NORTH -> {
                    neighborPos = pos.offset(Direction.SOUTH);
                    neighborState = world.getBlockState(neighborPos);
                }
            }
            if (neighborState.getBlock() instanceof GasCanisterBlock) {
                if (neighborState.get(GasCanisterBlock.FACING) == dir) {
                    BlockEntity neighborBlockEntity = world.getBlockEntity(neighborPos);
                    if (neighborBlockEntity instanceof GasCanisterBlockEntity entity && entity.getGasValue() != 0) {
                        entity.reduceGas();
                    } else {
                        playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,1.0f,1.0f);
                        world.setBlockState(pos,state.with(DeepFryerBlock.RUNNING,false));
                    }
                } else {
                    playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,1.0f,1.0f);
                    world.setBlockState(pos,state.with(DeepFryerBlock.RUNNING,false));
                }
            } else {
                playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,1.0f,1.0f);
                world.setBlockState(pos,state.with(DeepFryerBlock.RUNNING,false));
            }
            if (getOilLevel() != 0){
                if (hasRecipe(0)){
                    blockEntity.progress1++;
                    if (blockEntity.progress1 == maxProgress){
                        craft(0, world);
                        decreaseOilLevel(world,state);
                        blockEntity.progress1 = 0;
                        playSound(ModSounds.BLOCK_FOOD_FRYING,1.0f,2.0f);
                        markDirty();
                    }
                } else {
                    blockEntity.progress1 = 0;
                }
                if (hasRecipe(1)){
                    blockEntity.progress2++;
                    if (blockEntity.progress2 == maxProgress){
                        craft(1, world);
                        decreaseOilLevel(world,state);
                        blockEntity.progress2 = 0;
                        playSound(ModSounds.BLOCK_FOOD_FRYING,1.0f,2.0f);
                        markDirty();
                    }
                } else {
                    blockEntity.progress2 = 0;
                }
                if (hasRecipe(2)){
                    blockEntity.progress3++;
                    if (blockEntity.progress3 == maxProgress){
                        craft(2, world);
                        decreaseOilLevel(world,state);
                        blockEntity.progress3 = 0;
                        playSound(ModSounds.BLOCK_FOOD_FRYING,1.0f,2.0f);
                        markDirty();
                    }
                } else {
                    blockEntity.progress3 = 0;
                }
                if (hasRecipe(3)) {
                    blockEntity.progress4++;
                    if (blockEntity.progress4 == maxProgress) {
                        craft(3, world);
                        decreaseOilLevel(world, state);
                        blockEntity.progress4 = 0;
                        playSound(ModSounds.BLOCK_FOOD_FRYING, 1.0f, 2.0f);
                        markDirty();
                    }
                } else {
                    blockEntity.progress4 = 0;
                }
            } else {
                resetAllProgress();
            }
        } else {
            resetAllProgress();
            isHeated = 0;
        }
    }
    private void resetAllProgress(){
        progress1 = 0;
        progress2 = 0;
        progress3 = 0;
        progress4 = 0;
        markDirty();
    }
    public void playSound(SoundEvent sound, float volume, float pitch) {
        Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, pitch);
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    private boolean isHeated(BlockState state){
        return state.get(DeepFryerBlock.RUNNING);
    }
    private int getOilLevel(){
        return oilLevel;
    }
    private void decreaseOilLevel(World world,BlockState state){
        if (oilLevel - 1 != 0){
            oilLevel--;
        } else {
            world.setBlockState(pos,state.with(DeepFryerBlock.HAS_OIL,false));
            oilLevel = 0;
        }
    }
    private void craft(int slot, World world){
        SimpleInventory inventory = new SimpleInventory(1);
        inventory.setStack(0,this.getStack(slot));
        Optional<DeepFryingRecipe> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                .getFirstMatch(DeepFryingRecipe.Type.INSTANCE, inventory,this.getWorld());
        if (this.getStack(slot).getItem() instanceof ModStewItem){
            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(Items.BOWL));
        }
        this.setStack(slot, new ItemStack(match.get().getOutput(null).getItem(),
                match.get().getOutput(null).getCount()));
    }
    private boolean hasRecipe(int slot) {
        SimpleInventory inventory = new SimpleInventory(1);
        inventory.setStack(0,this.getStack(slot));
        Optional<DeepFryingRecipe> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                .getFirstMatch(DeepFryingRecipe.Type.INSTANCE, inventory,this.getWorld());
        return match.isPresent();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("deep_fryer.progress1",progress1);
        nbt.putInt("deep_fryer.progress2",progress2);
        nbt.putInt("deep_fryer.progress3",progress3);
        nbt.putInt("deep_fryer.progress4",progress4);
        nbt.putInt("deep_fryer.oilLevel",oilLevel);
        nbt.putInt("deep_fryer.isHeated",isHeated);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress1 = nbt.getInt("deep_fryer.progress1");
        progress2 = nbt.getInt("deep_fryer.progress2");
        progress3 = nbt.getInt("deep_fryer.progress3");
        progress4 = nbt.getInt("deep_fryer.progress4");
        oilLevel = nbt.getInt("deep_fryer.oilLevel");
        isHeated = nbt.getInt("deep_fryer.isHeated");
    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        super.markDirty();
    }
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(DEEP_FRYER_NAME);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new DeepFryerScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

}
