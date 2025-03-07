package com.zombie_cute.mc.bakingdelight.block.kitchenware;

import com.google.common.collect.Lists;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.food.PackagedItem;
import com.zombie_cute.mc.bakingdelight.item.tools.ElectricWhiskItem;
import com.zombie_cute.mc.bakingdelight.recipe.custom.MixWithWaterRecipe;
import com.zombie_cute.mc.bakingdelight.recipe.custom.WhiskingRecipe;
import com.zombie_cute.mc.bakingdelight.sound.ModSounds;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import com.zombie_cute.mc.bakingdelight.util.MiscUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static com.zombie_cute.mc.bakingdelight.block.kitchenware.GlassBowlBlock.*;

public class GlassBowlBlockEntity extends BlockEntity implements ImplementedInventory {
    public static final String WHISK_FAIL = "bakingdelight.glass_bowl_message.whisk_fail";
    public static final String NEED_PACKAGE = "bakingdelight.glass_bowl_message.need_package";
    public GlassBowlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GLASS_BOWL_ENTITY, pos, state);
    }
    public final DefaultedList<ItemStack> GLASS_BOWL_INV = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public ItemStack outputStack = ItemStack.EMPTY;

    public ItemStack getOutputStack() {
        return outputStack;
    }

    public void setOutputStack(ItemStack outputStack) {
        this.outputStack = outputStack;
    }

    public void onUse(@NotNull PlayerEntity player, BlockState state, World world){
        if (world.isClient()){
            return;
        }
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
            Optional<MixWithWaterRecipe> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                    .getFirstMatch(MixWithWaterRecipe.Type.INSTANCE, inventory,this.getWorld());
            if (match.isPresent()){
                ItemScatterer.spawn(this.getWorld(),this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(),
                        new ItemStack(match.get().getOutput(null).getItem(),1));
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
            if (!outputStack.isEmpty()){
                if (outputStack.getItem() instanceof PackagedItem packagedItem){
                    if (mainHandItem == packagedItem.getPackageItem()){
                        player.getMainHandStack().split(1);
                        getResultItem(world,state,player,false);
                    } else {
                        MutableText text = Text.translatable(NEED_PACKAGE);
                        text.append(Text.translatable(packagedItem.getPackageItem().getTranslationKey()));
                        player.sendMessage(text,true);
                    }
                } else {
                    getResultItem(world,state,player,true);
                }
            } else {
                // Storage Water
                if (getStack(0).isEmpty() &&
                        !world.getBlockState(pos).get(HAS_ITEM) &&
                        mainHandItem == Items.POTION){
                    player.getMainHandStack().split(1);
                    player.setStackInHand(player.getActiveHand(),Items.GLASS_BOTTLE.getDefaultStack());
                    world.setBlockState(pos,state.with(HAS_WATER, true));
                    playSound(SoundEvents.ITEM_BUCKET_EMPTY,1.0f);
                } else if (getStack(0).isEmpty() &&
                        !world.getBlockState(pos).get(HAS_ITEM) &&
                        mainHandItem == Items.WATER_BUCKET) {
                    player.getMainHandStack().split(1);
                    player.setStackInHand(player.getActiveHand(),Items.BUCKET.getDefaultStack());
                    world.setBlockState(pos,state.with(HAS_WATER, true));
                    playSound(SoundEvents.ITEM_BUCKET_EMPTY,1.0f);
                } else {
                    // Storage Items
                    if(getStack(0).isEmpty()){
                        if (offHandItem == Items.AIR){
                            setStack(0, player.getMainHandStack().split(1));
                            if (mainHandItem != Items.AIR){
                                playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.4F);
                            }
                        } else {
                            setStack(0, player.getOffHandStack().split(1));
                            playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.4F);
                        }
                    } else {
                        if (isWhisk(player.getMainHandStack(), world,player)) {
                            if (hasRecipe()){
                                // Spawn Empty Bowl
                                if (getStack(0).getItem() instanceof PackagedItem packagedItem){
                                    ItemScatterer.spawn(Objects.requireNonNull(this.getWorld()),this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(),
                                            packagedItem.getPackageItem().getDefaultStack());
                                }
                                craft(world);
                                player.getMainHandStack().damage(1, (LivingEntity) player, playerEntity -> playerEntity.sendToolBreakStatus(Hand.MAIN_HAND));
                                setStack(0, ItemStack.EMPTY);
                                playSound(ModSounds.BLOCK_GLASS_BOWL_WHISKING, 1.5F);
                                world.setBlockState(pos,state.with(HAS_ITEM,true));
                            }
                        }
                        spawnItem(world);
                    }
                }
            }
        }
        markDirty();
    }
    private void getResultItem(World world, BlockState state, PlayerEntity player, boolean spawn){
        if (spawn){
            ItemScatterer.spawn(world,this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(), outputStack);
        } else {
            player.giveItemStack(outputStack);
        }
        outputStack = ItemStack.EMPTY;
        playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.8F);
        markDirty();
        world.setBlockState(pos,state.with(HAS_ITEM,false));
    }
    private void spawnItem(World world){
        if (world.isClient){
            return;
        }
        ItemScatterer.spawn(world,this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(),
                getStack(0));
        setStack(0, ItemStack.EMPTY);
        playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.8F);
        markDirty();
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
                player.sendMessage(Text.translatable(MiscUtil.ELECTRIC_WHISK_MSG),true);
                return false;
            }
        }
        ArrayList<Item> list = Lists.newArrayList();
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.WHISKS)) {
            list.add(registryEntry.value());
        }
        return list.contains(stack.getItem());
    }
    private void craft(World world){
        SimpleInventory inventory = new SimpleInventory(this.size());
        inventory.setStack(0,this.getStack(0));
        Optional<WhiskingRecipe> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                .getFirstMatch(WhiskingRecipe.Type.INSTANCE, inventory,this.getWorld());
        outputStack = match.get().getOutput(null).getItem().getDefaultStack().copy();
        if (getStack(0).getCount() > 1){
            ItemStack tmp = getStack(0).copy();
            tmp.setCount(getStack(0).getCount() - 1);
            ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),tmp);
        }
        markDirty();
    }
    private boolean hasRecipe() {
        SimpleInventory inventory = new SimpleInventory(this.size());
        inventory.setStack(0,this.getStack(0));
        Optional<WhiskingRecipe> match = Objects.requireNonNull(this.getWorld()).getRecipeManager()
                .getFirstMatch(WhiskingRecipe.Type.INSTANCE, inventory,this.getWorld());
        return match.isPresent();
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, GLASS_BOWL_INV);
        nbt.putString("glass_bowl_output_item",Registries.ITEM.getId(outputStack.getItem()).toString());
        nbt.putInt("glass_bowl_output_count",outputStack.getCount());
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, GLASS_BOWL_INV);
        String s = nbt.getString("glass_bowl_output_item");
        int c = nbt.getInt("glass_bowl_output_count");
        try {
            Item item = Registries.ITEM.get(new Identifier(s));
            outputStack = new ItemStack(item,c);
        } catch (Exception ignored){}
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
            ItemStackSyncS2CPacket.send(pos,getItems(),world);
        }
        super.markDirty();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

}
