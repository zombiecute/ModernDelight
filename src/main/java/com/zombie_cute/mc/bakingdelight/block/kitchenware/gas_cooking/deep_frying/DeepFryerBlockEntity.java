package com.zombie_cute.mc.bakingdelight.block.kitchenware.gas_cooking.deep_frying;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.biogas.GasCanisterBlock;
import com.zombie_cute.mc.bakingdelight.block.biogas.GasCanisterBlockEntity;
import com.zombie_cute.mc.bakingdelight.fluid.ModFluid;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.tools.HolderItem;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.recipe.custom.DeepFryingRecipe;
import com.zombie_cute.mc.bakingdelight.screen.custom.DeepFryerScreenHandler;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.FluidUtil;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.util.registry_util.ModDamageTypes;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
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
    public static final int MAX_OIL = 1000;
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
            stopRunning(world, state);
        }
        markDirty();
        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
    }
    public boolean isBottleVegetableOil(Item item){
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.BOTTLE_VEGETABLE_OIL)){
            if (item == registryEntry.value()){
                return true;
            }
        }
        return false;
    }
    public boolean isBucketVegetableOil(Item item){
        return ModFluid.STILL_VEGETABLE_OIL.getBucketItem() == item;
    }
    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient){
            return;
        }
        ItemStack mainHandStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();
        if (!state.get(DeepFryerBlock.HAS_OIL)){
            if (isBottleVegetableOil(offHandStack.getItem())){
                splitOilItem(world, player,false, Items.GLASS_BOTTLE);
            } else if (isBottleVegetableOil(mainHandStack.getItem())){
                splitOilItem(world, player,true,Items.GLASS_BOTTLE);
            } else if (isBucketVegetableOil(offHandStack.getItem())){
                splitOilItem(world, player,false,Items.BUCKET);
            } else if (isBucketVegetableOil(mainHandStack.getItem())){
                splitOilItem(world, player,true,Items.BUCKET);
            } else {
                if (getStack(0).isEmpty() && getStack(1).isEmpty() && getStack(2).isEmpty() && getStack(3).isEmpty()){
                    player.sendMessage(Text.translatable(ADD_OIL),true);
                } else {
                    if (mainHandStack.getItem() == ModItems.HOLDER){
                        setItemOnHolder(mainHandStack);
                    } else if (offHandStack.getItem() == ModItems.HOLDER){
                        setItemOnHolder(offHandStack);
                    } else {
                        spawnItemAndTryDamage(world,player,state);
                    }
                }
            }
        } else if (mainHandStack.getItem() == ModItems.HOLDER){
            setItemOnHolder(mainHandStack);
        } else if (offHandStack.getItem() == ModItems.HOLDER){
            setItemOnHolder(offHandStack);
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
    private void setItemOnHolder(ItemStack holder){
        if (HolderItem.getHoldingStack(holder).isEmpty()){
            for(int i=3;i>=0;i--){
                if (!getStack(i).isEmpty()){
                    ItemStack stack = getStack(i).copy();
                    setStack(i,ItemStack.EMPTY);
                    playSound(SoundEvents.ENTITY_ITEM_PICKUP,1.0f,1.0f);
                    HolderItem.setHoldingStack(stack,holder);
                    break;
                }
            }
        }
        markDirty();
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
        ItemScatterer.spawn(world,pos.getX()+0.5,pos.getY()+0.8,pos.getZ()+0.5,
                getStack(slot));
        this.setStack(slot,ItemStack.EMPTY);
        playSound(SoundEvents.ENTITY_ITEM_PICKUP,1.3f,world.random.nextFloat()+0.4f);
        markDirty();
    }
    private void checkAndPut(boolean isMainHand, PlayerEntity player, World world){
        for (int i=0;i<4;i++){
            if (getStack(i).isEmpty()){
                checkHandAndSplit(isMainHand,player,i,world);
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
    private void checkHandAndSplit(boolean isMainHand,PlayerEntity player,int slot, World world){
        if (isMainHand){
            setStack(slot,player.getMainHandStack().split(1));
        } else {
            setStack(slot,player.getOffHandStack().split(1));
        }
        markDirty();
    }
    private void splitOilItem(World world, PlayerEntity player, boolean isMainHand, Item item) {
        checkHandAndDecrement(isMainHand,player);
        playSound(SoundEvents.ITEM_BUCKET_EMPTY,1.0f, world.random.nextFloat()+0.3f);
        player.giveItemStack(item.getDefaultStack());
        if (item.equals(Items.GLASS_BOTTLE)){
            try(Transaction transaction = Transaction.openOuter()){
                fluidStorage.insert(FluidVariant.of(ModFluid.STILL_VEGETABLE_OIL),
                        fluidStorage.getCapacity() / 3,transaction);
                transaction.commit();
            }
        } else if (item.equals(Items.BUCKET)){
            fluidStorage.variant = FluidVariant.of(ModFluid.STILL_VEGETABLE_OIL);
            fluidStorage.amount = fluidStorage.getCapacity();
        }
    }
    int maxProgress = 300;
    public void tick(World world, BlockState state, DeepFryerBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        if (hasOil()){
            world.setBlockState(pos, state.with(DeepFryerBlock.HAS_OIL,true));
            this.oilLevel = (int) FluidUtil.convertDropletsToMb(fluidStorage.amount);
        } else {
            this.oilLevel = 0;
            world.setBlockState(pos, state.with(DeepFryerBlock.HAS_OIL,false));
        }
        if (world.getTime()%5==0&&(progress1 != 0 || progress2 !=0 || progress3 != 0 || progress4 != 0)){
            playSound(ModSounds.BLOCK_FOOD_FRYING,0.4f,1.0f);
        }
        if (isHeated(state)){
            isHeated = 1;
            if (world.getTime()%5==0){
                playSound(SoundEvents.BLOCK_FIRE_AMBIENT,0.3f,1.0f);
            }
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
                    if (!(neighborBlockEntity instanceof GasCanisterBlockEntity entity) || !entity.reduceGas()) {
                        stopRunning(world, state);
                    }
                } else {
                    stopRunning(world, state);
                }
            } else {
                stopRunning(world, state);
            }
            if (state.get(DeepFryerBlock.HAS_OIL)){
                if (hasRecipe(0)){
                    blockEntity.progress1++;
                    if (blockEntity.progress1 == maxProgress){
                        craft(0, world);
                    }
                } else {
                    blockEntity.progress1 = 0;
                }
                if (hasRecipe(1)){
                    blockEntity.progress2++;
                    if (blockEntity.progress2 == maxProgress){
                        craft(1, world);
                    }
                } else {
                    blockEntity.progress2 = 0;
                }
                if (hasRecipe(2)){
                    blockEntity.progress3++;
                    if (blockEntity.progress3 == maxProgress){
                        craft(2, world);
                    }
                } else {
                    blockEntity.progress3 = 0;
                }
                if (hasRecipe(3)) {
                    blockEntity.progress4++;
                    if (blockEntity.progress4 == maxProgress) {
                        craft(3, world);
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

    private boolean hasOil() {
        Fluid fluid = fluidStorage.variant.getFluid();
        for(RegistryEntry<Fluid> f:Registries.FLUID.iterateEntries(TagKeys.OIL)){
            if (fluid == f.value()){
                return true;
            }
        }
        return false;
    }

    private void craft(int slot, World world) {
        SimpleInventory inventory = new SimpleInventory(1);
        inventory.setStack(0,this.getStack(slot));
        Optional<DeepFryingRecipe> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                .getFirstMatch(DeepFryingRecipe.Type.INSTANCE, inventory,this.getWorld());
        if (!this.getStack(slot).getRecipeRemainder().isEmpty()){
            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),this.getStack(slot).getRecipeRemainder().copy());
        }
        this.setStack(slot, new ItemStack(match.get().getOutput(null).getItem(),
                match.get().getOutput(null).getCount()));
        decreaseOilLevel();
        if (world.getTime()%5==0){
            playSound(ModSounds.BLOCK_FOOD_FRYING, 1.0f, 2.0f);
        }
        markDirty();
    }

    private void stopRunning(World world, BlockState state) {
        playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,1.0f,1.0f);
        world.setBlockState(pos, state.with(DeepFryerBlock.RUNNING,false));
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
    private void decreaseOilLevel(){
        if (fluidStorage.amount - FluidUtil.convertMbToDroplets(50) > 0){
            fluidStorage.amount-=FluidUtil.convertMbToDroplets(50);
        } else {
            fluidStorage.amount = 0;
            fluidStorage.variant = FluidVariant.blank();
        }
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
        nbt.putLong("deep_fryer.fluid_amount",fluidStorage.amount);
        nbt.put("deep_fryer.fluid_variant",fluidStorage.variant.toNbt());
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
        fluidStorage.variant = FluidVariant.fromNbt((NbtCompound) nbt.get("deep_fryer.fluid_variant"));
        fluidStorage.amount = nbt.getLong("deep_fryer.fluid_amount");
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
            ItemStackSyncS2CPacket.send(pos,getItems(),world);
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
