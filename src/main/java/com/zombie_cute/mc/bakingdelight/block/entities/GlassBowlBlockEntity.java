package com.zombie_cute.mc.bakingdelight.block.entities;

import com.google.common.collect.Lists;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.custom.ElectricWhiskItem;
import com.zombie_cute.mc.bakingdelight.item.custom.ModStewItem;
import com.zombie_cute.mc.bakingdelight.recipe.custom.MixWithWaterRecipe;
import com.zombie_cute.mc.bakingdelight.recipe.custom.WhiskingRecipe;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.ModUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static com.zombie_cute.mc.bakingdelight.block.custom.GlassBowlBlock.*;

public class GlassBowlBlockEntity extends BlockEntity implements ImplementedInventory {
    public static final String WHISK_FAIL = "bakingdelight.glass_bowl_message.whisk_fail";
    public static final String NEED_BOWL = "bakingdelight.glass_bowl_message.need_bowl";
    public GlassBowlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GLASS_BOWL_ENTITY, pos, state);
    }
    public final DefaultedList<ItemStack> GLASS_BOWL_INV = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public void onUse(@NotNull PlayerEntity player, BlockState state, World world){
        Item offHandItem = player.getOffHandStack().getItem();
        Item mainHandItem = player.getMainHandStack().getItem();
        // Check Water
        if (world.getBlockState(pos).get(HAS_WATER)) {
            SimpleInventory inventory = new SimpleInventory(this.size());
            boolean isMainHand;
            if (offHandItem == Items.AIR){
                inventory.setStack(0, mainHandItem.getDefaultStack());
                isMainHand = true;
            } else {
                inventory.setStack(0, offHandItem.getDefaultStack());
                isMainHand = false;
            }
            // Mix
            Optional<RecipeEntry<MixWithWaterRecipe>> match = Objects.requireNonNull(getWorld()).getRecipeManager().getFirstMatch(MixWithWaterRecipe.Type.INSTANCE,
                    new SingleStackRecipeInput(inventory.getStack(0)),getWorld());

            if (match.isPresent()){
                ItemScatterer.spawn(world,this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(),
                        new ItemStack(match.get().value().getResult(null).getItem(),1));
                if (isMainHand){
                    player.getMainHandStack().split(1);
                } else {
                    player.getOffHandStack().split(1);
                }
                world.setBlockState(pos,state.with(HAS_WATER, false));
                playSound(SoundEvents.ITEM_BUCKET_FILL,1.0f);
            } else if (mainHandItem == Items.GLASS_BOTTLE){
                player.getMainHandStack().split(1);
                player.giveItemStack(Items.POTION.getDefaultStack());
                world.setBlockState(pos,state.with(HAS_WATER, false));
                playSound(SoundEvents.ITEM_BUCKET_FILL,1.0f);
            } else
            if (mainHandItem == Items.BUCKET) {
                player.getMainHandStack().split(1);
                player.giveItemStack(Items.WATER_BUCKET.getDefaultStack());
                world.setBlockState(pos,state.with(HAS_WATER, false));
                playSound(SoundEvents.ITEM_BUCKET_FILL,1.0f);
            }
        } else {
            // Take the Output
            if (!GLASS_BOWL_INV.get(1).isEmpty()){
                if (GLASS_BOWL_INV.get(1).getItem() instanceof ModStewItem){
                    if (mainHandItem == Items.BOWL){
                        player.getMainHandStack().split(1);
                        getResultItem(world,state,player,false);
                    } else {
                        player.sendMessage(Text.translatable(NEED_BOWL),true);
                    }
                } else {
                    getResultItem(world,state,player,true);
                }
            } else {
                // Storage Water
                if (GLASS_BOWL_INV.getFirst().isEmpty() &&
                        !world.getBlockState(pos).get(HAS_ITEM) &&
                        mainHandItem == Items.POTION){
                    player.getMainHandStack().split(1);
                    player.setStackInHand(player.getActiveHand(),Items.GLASS_BOTTLE.getDefaultStack());
                    world.setBlockState(pos,state.with(HAS_WATER, true));
                    playSound(SoundEvents.ITEM_BUCKET_EMPTY,1.0f);
                } else if (GLASS_BOWL_INV.getFirst().isEmpty() &&
                        !world.getBlockState(pos).get(HAS_ITEM) &&
                        mainHandItem == Items.WATER_BUCKET) {
                    player.getMainHandStack().split(1);
                    player.setStackInHand(player.getActiveHand(),Items.BUCKET.getDefaultStack());
                    world.setBlockState(pos,state.with(HAS_WATER, true));
                    playSound(SoundEvents.ITEM_BUCKET_EMPTY,1.0f);
                } else {
                    // Storage Items
                    if(GLASS_BOWL_INV.getFirst().isEmpty()){
                        if (offHandItem == Items.AIR){
                            GLASS_BOWL_INV.set(0, player.getMainHandStack().split(1));
                            if (mainHandItem != Items.AIR){
                                playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.4F);
                            }
                        } else {
                            GLASS_BOWL_INV.set(0, player.getOffHandStack().split(1));
                            playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.4F);
                        }
                    } else {
                        if (isWhisk(player.getMainHandStack(), world,player)) {
                            if (hasRecipe()){
                                // Spawn Empty Bowl
                                if (GLASS_BOWL_INV.getFirst().getItem() instanceof ModStewItem){
                                    ItemScatterer.spawn(Objects.requireNonNull(this.getWorld()),this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(),
                                            Items.BOWL.getDefaultStack());
                                }
                                craft();
                                player.getMainHandStack().damage(1, player, EquipmentSlot.MAINHAND);
                                GLASS_BOWL_INV.set(0, ItemStack.EMPTY);
                                playSound(ModSounds.BLOCK_GLASS_BOWL_WHISKING, 1.5F);
                                world.setBlockState(pos,state.with(HAS_ITEM,true));
                            }
                        }
                        spawnItem(0,world);
                    }
                }
            }
        }
        markDirty();
    }

    private Optional<RecipeEntry<WhiskingRecipe>> getCurrentMixRecipe() {
        SimpleInventory inventory = new SimpleInventory(this.size());
        inventory.setStack(0,this.getStack(0));
        return Objects.requireNonNull(getWorld()).getRecipeManager().getFirstMatch(WhiskingRecipe.Type.INSTANCE,
                new SingleStackRecipeInput(inventory.getStack(0)),getWorld());
    }

    private void getResultItem(World world, BlockState state, PlayerEntity player, boolean spawn){
        if (spawn){
            spawnItem(1,world);
        } else {
            player.giveItemStack(GLASS_BOWL_INV.get(1));
            GLASS_BOWL_INV.set(1, ItemStack.EMPTY);
            playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.8F);
        }
        world.setBlockState(pos,state.with(HAS_ITEM,false));
    }
    private void spawnItem(int slot,World world){
        if (world.isClient){
            setStack(slot, ItemStack.EMPTY);
            return;
        }
        ItemScatterer.spawn(world,this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(),
                GLASS_BOWL_INV.get(slot));
        GLASS_BOWL_INV.set(slot, ItemStack.EMPTY);
        playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.8F);
    }
    public void playSound(SoundEvent sound, float volume) {
        Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, world.random.nextFloat()+0.1f);
    }
    private boolean isWhisk(@NotNull ItemStack stack, World world, PlayerEntity player) {
        if (stack.isOf(ModItems.ELECTRIC_WHISK)){
            if (ElectricWhiskItem.getNBTPower(stack) >= 2){
                ElectricWhiskItem.reduceNBTPower(stack,2);
                ElectricWhiskItem.playAnimation(stack);
                world.playSound(null,pos.getX(),pos.getY(),pos.getZ(),
                        ModSounds.ITEM_ELECTRIC_WHISK_WORKING, SoundCategory.PLAYERS,1.0f,1.0f);
                return true;
            } else {
                player.sendMessage(Text.translatable(ModUtil.ELECTRIC_WHISK_MSG),true);
                return false;
            }
        }
        ArrayList<Item> list = Lists.newArrayList();
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.WHISKS)) {
            list.add(registryEntry.value());
        }
        return list.contains(stack.getItem());
    }
    private void craft(){
        Optional<RecipeEntry<WhiskingRecipe>> match = getCurrentMixRecipe();
        this.setStack(1, new ItemStack(match.get().value().getResult(null).getItem(),1));
    }
    private boolean hasRecipe() {
        Optional<RecipeEntry<WhiskingRecipe>> match = getCurrentMixRecipe();
        return match.isPresent();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, GLASS_BOWL_INV, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, GLASS_BOWL_INV, registryLookup);
    }
    public void playSound(SoundEvent sound, float volume, float pitch) {
        Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, pitch);
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return GLASS_BOWL_INV;
    }
    public ItemStack getRendererStack(){
        return this.getStack(0);
    }
    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        super.markDirty();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GlassBowlBlockEntity blockEntity) {
        if (state.get(WATERLOGGED)){
            world.setBlockState(pos,state.with(HAS_WATER,true));
            if (!blockEntity.GLASS_BOWL_INV.get(0).isEmpty()){
                blockEntity.spawnItem(0,world);
                blockEntity.markDirty();
            }
            if (!blockEntity.GLASS_BOWL_INV.get(1).isEmpty()){
                if (!(blockEntity.getStack(1).getItem() instanceof ModStewItem)) {
                    blockEntity.spawnItem(1,world);
                }
                blockEntity.markDirty();
            }
        }
        if (state.get(HAS_WATER)){
            if (!blockEntity.GLASS_BOWL_INV.get(1).isEmpty()){
                if (!(blockEntity.getStack(1).getItem() instanceof ModStewItem)) {
                    blockEntity.spawnItem(1,world);
                }
                blockEntity.markDirty();
            }
            world.setBlockState(pos, state.with(HAS_ITEM,false));
        }
    }
}
