package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BakingTrayBlockEntity extends BlockEntity implements ImplementedInventory {
    public BakingTrayBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BAKING_TRAY_BLOCK_ENTITY, pos, state);
    }
    public final DefaultedList<ItemStack> INV = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private int coolTime = 0;
    private int stir_fry_times = 0;
    private final int max_stir_fry_times = 5;

    @Override
    public DefaultedList<ItemStack> getItems() {
        return INV;
    }
    public void playSound(SoundEvent sound, float volume, boolean isRandom) {
        if (isRandom){
            Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, world.random.nextFloat()+0.8f);
        } else {
            Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, 1.0f);
        }
    }
    public void onUse(PlayerEntity player, World world) {
        boolean isMainHand = player.getOffHandStack().getItem().getDefaultStack().isEmpty();
        if (isHeated(world,pos) && isSpatulaItem(player) &&
                (!this.getStack(0).isEmpty() ||
                !this.getStack(1).isEmpty() ||
                !this.getStack(2).isEmpty() ||
                !this.getStack(3).isEmpty())){
            if (stir_fry_times!=max_stir_fry_times && coolTime==0){
                stir_fry_times++;
                coolTime = 5;
                SimpleInventory inventory = new SimpleInventory(4);
                for (int i=0;i<4;i++){
                    if (i!=3){
                        inventory.setStack(i,this.getStack(i+1));
                    } else {
                        inventory.setStack(i,this.getStack(0));
                    }
                }
                for (int i=0;i<4;i++){
                    this.setStack(i,inventory.getStack(i));
                }
                playSound(ModSounds.BLOCK_FOOD_FRYING,1.0f,true);

                player.getMainHandStack().damage(1, player, EquipmentSlot.MAINHAND);
            }
        } else {
            if (player.isSneaking()){
                if (!this.getStack(3).isEmpty()){
                    spawnItem(world,3);
                } else if (!this.getStack(2).isEmpty()) {
                    spawnItem(world,2);
                } else if (!this.getStack(1).isEmpty()) {
                    spawnItem(world,1);
                } else if (!this.getStack(0).isEmpty()) {
                    spawnItem(world,0);
                }
            } else {
                if ((player.getMainHandStack().isEmpty()&&
                        player.getOffHandStack().isEmpty()) ||
                isSpatulaItem(player)){
                    markDirty();
                    return;
                }
                if (this.getStack(0).isEmpty()){
                    splitItem(player,isMainHand,0);
                } else if(this.getStack(1).isEmpty()){
                    splitItem(player,isMainHand,1);
                } else if(this.getStack(2).isEmpty()){
                    splitItem(player,isMainHand,2);
                } else if(this.getStack(3).isEmpty()){
                    splitItem(player,isMainHand,3);
                }
            }
        }
        markDirty();
    }

    private boolean isSpatulaItem(PlayerEntity player) {
        List<Item> items = new ArrayList<>();
        for (RegistryEntry<Item> registryEntry :  Registries.ITEM.iterateEntries(TagKeys.SPATULAS)){
            items.add(registryEntry.value());
        }
        return items.contains(player.getMainHandStack().getItem());
    }

    private void spawnItem(World world,int slot){
        if (world.isClient){
            this.setStack(slot,ItemStack.EMPTY);
            return;
        }
        ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),this.getStack(slot));
        this.setStack(slot,ItemStack.EMPTY);
        playSound(SoundEvents.ENTITY_ITEM_PICKUP,1.0f,true);
        markDirty();
    }

    private void splitItem(PlayerEntity player, boolean isMainHand,int inv) {
        if (isMainHand){
            INV.set(inv, player.getMainHandStack().split(1));
        } else {
            INV.set(inv, player.getOffHandStack().split(1));
        }
        if (isHeated(Objects.requireNonNull(world),pos)){
            playSound(ModSounds.BLOCK_FOOD_FRYING,1.0f,true);
        } else {
            playSound(SoundEvents.ENTITY_ITEM_PICKUP,1.0f,true);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, INV,registryLookup);
        nbt.putInt("baking_tray.stir_fry_times", stir_fry_times);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, INV, registryLookup);
        stir_fry_times = nbt.getInt("baking_tray.stir_fry_times");
    }

    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
        super.markDirty();
    }
    private boolean isHeated(World world, BlockPos pos){
        return world.getBlockState(pos.down()).getBlock() == ModBlocks.BURNING_GAS_COOKING_STOVE;
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


    public ItemStack getRendererStack1() {
        return this.getStack(0);
    }
    public ItemStack getRendererStack2() {
        return this.getStack(1);
    }
    public ItemStack getRendererStack3() {
        return this.getStack(2);
    }
    public ItemStack getRendererStack4() {
        return this.getStack(3);
    }
    private boolean isFlat(Item item) {
        List<Item> items = new ArrayList<>();
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.FLAT_ON_BAKING_TRAY)){
            items.add(registryEntry.value());
        }
        return items.contains(item);
    }
    public static void tick(World world, BlockPos pos, BlockState state, BakingTrayBlockEntity blockEntity) {
        if (blockEntity.coolTime!=0){
            blockEntity.coolTime--;
        }
        if (blockEntity.isHeated(world,pos)){
            if (!blockEntity.isFlat(blockEntity.getStack(0).getItem())){
                if (blockEntity.stir_fry_times == blockEntity.max_stir_fry_times){
                    if (blockEntity.hasCampfireRecipe(blockEntity.getStack(0))){
                        blockEntity.craftCampfireItem(blockEntity.getStack(0),0,world);
                    }
                    if (blockEntity.hasCampfireRecipe(blockEntity.getStack(1))){
                        blockEntity.craftCampfireItem(blockEntity.getStack(1),1,world);
                    }
                    if (blockEntity.hasCampfireRecipe(blockEntity.getStack(2))){
                        blockEntity.craftCampfireItem(blockEntity.getStack(2),2,world);
                    }
                    if (blockEntity.hasCampfireRecipe(blockEntity.getStack(3))){
                        blockEntity.craftCampfireItem(blockEntity.getStack(3),3,world);
                    }
                    blockEntity.stir_fry_times = 0;
                }
            }
        }
    }

    private void craftCampfireItem(ItemStack stack, int slot, World world) {
        if (world.isClient){
            this.setStack(slot,ItemStack.EMPTY);
            return;
        }
        Optional<RecipeEntry<CampfireCookingRecipe>> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                .getFirstMatch(RecipeType.CAMPFIRE_COOKING,new SingleStackRecipeInput(stack),world);
        if (match.isPresent()){
            ItemScatterer.spawn(world,pos.getX(), pos.getY(), pos.getZ(),
                    new ItemStack(match.get().value().getResult(null).getItem()));
            this.setStack(slot,ItemStack.EMPTY);
            int exp = (int)match.get().value().getExperience();
            if (exp == 0) exp=1;
            ExperienceOrbEntity xp = new ExperienceOrbEntity(world,pos.getX(),pos.getY(),pos.getZ(),exp);
            world.spawnEntity(xp);
            markDirty();
        }
    }
    private boolean hasCampfireRecipe(ItemStack stack) {
        Optional<RecipeEntry<CampfireCookingRecipe>> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                .getFirstMatch(RecipeType.CAMPFIRE_COOKING,new SingleStackRecipeInput(stack),world);
        return match.isPresent();
    }
}
