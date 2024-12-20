package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class KitchenUtensilHolderBlockEntity extends BlockEntity implements ImplementedInventory {
    public KitchenUtensilHolderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KITCHEN_UTENSIL_HOLDER_BLOCK_ENTITY,pos, state);
    }
    public final DefaultedList<ItemStack> INV = DefaultedList.ofSize(4, ItemStack.EMPTY);


    public void onUse(@NotNull PlayerEntity player) {
        Item item;
        if (player.getOffHandStack().isEmpty()){
            item = player.getMainHandStack().getItem();
        } else {
            item = player.getOffHandStack().getItem();
        }
        if(item instanceof ToolItem) {
            int item_index = 0;
            while (item_index < 4) {
                if (INV.get(item_index).isEmpty()) {
                    INV.set(item_index, player.getMainHandStack().split(1));
                    playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.4F);
                    break;
                }
                else{
                    item_index++;
                }
            }
        } else{
            int item_index = 3;
            while (item_index > -1) {
                if (!INV.get(item_index).isEmpty()) {
                    spawnItem(item_index);
                    playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.8F);
                    break;
                }
                else{
                    item_index--;
                }
            }
        }
        markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt,registryLookup);
        Inventories.writeNbt(nbt, INV,registryLookup);
    }
    @Override
    public void readNbt(NbtCompound nbt,RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt,registryLookup);
        Inventories.readNbt(nbt, INV,registryLookup);
    }

    public DefaultedList<ItemStack> getItems() {
        return INV;
    }

    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    private void spawnItem(int slot){
        ItemScatterer.spawn(Objects.requireNonNull(this.getWorld()),this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(),
                INV.get(slot));
        INV.set(slot, ItemStack.EMPTY);
    }
    public void playSound(SoundEvent sound, float volume) {
        Objects.requireNonNull(world).playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, sound, SoundCategory.BLOCKS, volume, world.random.nextFloat()+0.1f);
    }

}
