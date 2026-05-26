package com.zombie_cute.mc.bakingdelight.block.kitchenware.juice_extractor;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.recipe.custom.JuiceExtractingRecipe;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.ACConsumer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.HashSet;
import java.util.Optional;

public class JuiceExtractorBlockEntity extends BlockEntity implements GeoBlockEntity, ImplementedInventory, ACConsumer, SidedInventory {
    public JuiceExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.JUICE_EXTRACTOR_BLOCK_ENTITY, pos, state);
    }
    public static final String NO_POWER = "bakingdelight.juice_extractor_message.no_power";
    public static final String WRONG_RECIPE = "bakingdelight.juice_extractor_message.wrong_recipe";
    public static final String IS_FULL = "bakingdelight.juice_extractor_message.is_full";

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WORK = RawAnimation.begin().thenLoop("work");
    private static final RawAnimation FULL = RawAnimation.begin().thenPlay("full");
    private static final RawAnimation HAS_ITEM = RawAnimation.begin().thenPlay("has_item");

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private int cachedPower = 0;
    private int progress = 0;
    private boolean scream = false;
    private boolean hasRecipe = false;
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    public ItemStack tempOutput = ItemStack.EMPTY;
    public Item tempContainer = Items.AIR;
    public static final int SLOT_1 = 0;
    public static final int SLOT_2 = 1;
    public static final int SLOT_3 = 2;
    public static final int SLOT_4 = 3;
    public static final int OUTPUT = 4;

    @Override
    public long getConsumedValue() {
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            if (state.getAnimatable().getCachedState().get(JuiceExtractorBlock.IS_WORKING)) {
                return state.setAndContinue(WORK);
            } else if (state.getAnimatable().getCachedState().get(JuiceExtractorBlock.IS_FULL)){
                return state.setAndContinue(FULL);
            } else {
                return state.setAndContinue(IDLE);
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("juice_extractor.progress",progress);
        NbtCompound tempOutputNBT = new NbtCompound();
        tempOutput.writeNbt(tempOutputNBT);
        nbt.put("juice_extractor.tempOutput",tempOutputNBT);
        NbtCompound tempContainerNBT = new NbtCompound();
        tempContainer.getDefaultStack().writeNbt(tempContainerNBT);
        nbt.put("juice_extractor.tempContainer",tempContainerNBT);
        nbt.putBoolean("juice_extractor.hasRecipe", hasRecipe);
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("juice_extractor.progress");
        NbtCompound output = nbt.getCompound("juice_extractor.tempOutput");
        tempOutput = ItemStack.fromNbt(output);
        NbtCompound container = nbt.getCompound("juice_extractor.tempContainer");
        tempContainer = ItemStack.fromNbt(container).getItem();
        hasRecipe = nbt.getBoolean("juice_extractor.hasRecipe");
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    public void use(World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient || world.getBlockState(pos).get(JuiceExtractorBlock.IS_WORKING)){
            return;
        }
        if (player.isSneaking()) {
            // 从后往前检查槽位并弹出物品
            for (int slot = SLOT_4; slot >= SLOT_1; slot--) {
                if (!getStack(slot).isEmpty()) {
                    ItemStack tmp = getStack(slot).copy();
                    tmp.setCount(1);
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), tmp);
                    world.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 0.6f);
                    getStack(slot).decrement(1);
                    markDirty();
                    return; // 弹出一次后直接返回
                }
            }
        } else {
            boolean isMainHand = !player.getMainHandStack().isEmpty();
            Hand hand = isMainHand ? Hand.MAIN_HAND : Hand.OFF_HAND;
            // isFull 检测
            if (world.getBlockState(pos).get(JuiceExtractorBlock.IS_FULL)){
                if (player.getStackInHand(hand).getItem() == tempContainer){
                    if (player.getStackInHand(hand).getCount() == 1){
                        player.setStackInHand(Hand.MAIN_HAND,getStack(OUTPUT).copyWithCount(1));
                    } else {
                        player.getStackInHand(hand).decrement(1);
                        player.giveItemStack(getStack(OUTPUT).copyWithCount(1));
                    }
                    world.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    getStack(OUTPUT).decrement(1);
                    if(getStack(OUTPUT).isEmpty()){
                        setState(world,pos,JuiceExtractorBlock.IS_FULL,false);
                        tempOutput = ItemStack.EMPTY;
                        tempContainer = Items.AIR;
                    }
                } else {
                    MutableText text = Text.translatable(IS_FULL);
                    text.append(Text.literal(" "));
                    text.append(Text.translatable(tempContainer.getTranslationKey()));
                    player.sendMessage(text,true);
                }
                markDirty();
                return;
            }
            // 空手检测
            if (!isMainHand && player.getOffHandStack().isEmpty()) {
                for(int i = 0;i < 4; i++){
                    if (getStack(i).isEmpty()){
                        return;
                    }
                }
                tryStart(player,world);
                return;
            }
            // 放入物品
            for (int slot = SLOT_1; slot <= SLOT_4; slot++) {
                if (getStack(slot).isEmpty()) {
                    setStack(slot, player.getStackInHand(hand).split(1));
                    world.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    markDirty();
                    return;
                }
            }
            tryStart(player, world);
        }
    }

    private void tryStart(PlayerEntity player,World world) {
        if (cachedPower > 0){
            SimpleInventory inv = new SimpleInventory(4);
            for (int i = 0;i < 4;i++){
                inv.setStack(i,getStack(i));
            }
            Optional<JuiceExtractingRecipe> match = world.getRecipeManager()
                    .getFirstMatch(JuiceExtractingRecipe.Type.INSTANCE, inv, world);
            if (match.isPresent()){
                this.tempOutput = match.get().getOutput(null).copy();
                this.tempContainer = match.get().getContainer();
                this.progress = match.get().getProgress();
                clear(world);
                if (hasHardThings()){
                    scream = true;
                }
                hasRecipe = true;
                setWorking(world);
            } else if (hasHardThings()){
                scream = true;
                this.tempOutput = ItemStack.EMPTY.copy();
                this.tempContainer = Items.AIR;
                this.progress = 200;
                clear(world);
                hasRecipe = false;
                setWorking(world);
            } else {
                player.sendMessage(Text.translatable(WRONG_RECIPE),true);
            }
            markDirty();
        } else {
            player.sendMessage(Text.translatable(NO_POWER),true);
        }
    }

    private void setWorking(World world) {
        if (world.getBlockState(pos).getBlock() instanceof JuiceExtractorBlock) {
            world.setBlockState(pos, world.getBlockState(pos).with(JuiceExtractorBlock.IS_WORKING,true));
        }
    }

    private void clear(World world) {
        for(int i = 0;i < 4;i++){
            if (!getStack(i).getRecipeRemainder().isEmpty()){
                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),this.getStack(i).getRecipeRemainder().copy());
            }
            setStack(i,ItemStack.EMPTY);
            markDirty();
        }
    }

    private boolean hasHardThings() {
        HashSet<Block> blocks = new HashSet<>();
        for (RegistryEntry<Block> registryEntry: Registries.BLOCK.iterateEntries(BlockTags.PICKAXE_MINEABLE)){
            blocks.add(registryEntry.value());
        }
        for(int i = 0;i < 4;i++){
            if (getStack(i).getItem() instanceof BlockItem blockItem){
                if (blocks.contains(blockItem.getBlock())){
                    return true;
                }
            }
        }
        return false;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient){
            return;
        }
        if (cachedPower != 0){
            cachedPower--;
        }
        if (state.get(JuiceExtractorBlock.IS_WORKING)){
            if (progress > 0){
                if (world.getTime() % 5 == 0){
                    if (scream){
                        world.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, ModSounds.BLOCK_JUICE_EXTRACTOR_SCREAM, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    } else {
                        world.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, ModSounds.BLOCK_JUICE_EXTRACTOR_WORKING, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    }
                }
                progress--;
            } else {
                world.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, ModSounds.BLOCK_JUICE_EXTRACTOR_STOP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                if (hasRecipe){
                    setStack(OUTPUT,tempOutput.copy());
                    setState(world, pos, JuiceExtractorBlock.IS_FULL, true);
                } else {
                    world.createExplosion(null,pos.getX(),pos.getY(),pos.getZ(),1.5f,false, World.ExplosionSourceType.BLOCK);
                }
                hasRecipe = false;
                scream = false;
                setState(world, pos, JuiceExtractorBlock.IS_WORKING, false);
                markDirty();
            }
        }
    }

    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
            ItemStackSyncS2CPacket.send(pos,getItems(),world);
        }
        super.markDirty();
    }

    private static void setState(World world, BlockPos pos, BooleanProperty booleanProperty, boolean value) {
        if (world.getBlockState(pos).getBlock() instanceof JuiceExtractorBlock) {
            world.setBlockState(pos, world.getBlockState(pos).with(booleanProperty, value), Block.NOTIFY_ALL);
        }
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[4];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot < 4;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot < 4;
    }
}
