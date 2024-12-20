package com.zombie_cute.mc.bakingdelight.block.entities;

import com.google.common.collect.Lists;
import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.entities.abstracts.AbstractPizzaBlockEntity;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.zombie_cute.mc.bakingdelight.block.custom.PizzaWIPBlock.CRAFT_STATE;

public class PizzaWIPBlockEntity extends AbstractPizzaBlockEntity {
    public PizzaWIPBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PIZZA_WIP_ENTITY, pos, state);
    }
    public static final String NEED_INGREDIENT = "bakingdelight.pizza_message.need_ingredient";
    public static final String NEED_CHEESE = "bakingdelight.pizza_message.need_cheese";

    public void onUse(PlayerEntity player, BlockState state, World world) {
        if (world.isClient){
            return;
        }
        Item item;
        boolean isMainHand;
        if (player.getOffHandStack().isEmpty()){
            item = player.getMainHandStack().getItem();
            isMainHand = true;
        } else {
            item = player.getOffHandStack().getItem();
            isMainHand = false;
        }
        int currentState = state.get(CRAFT_STATE);
        if (currentState < 5){
            if (isPizzaIngredients(item)){
                int next = currentState + 1;
                world.setBlockState(pos, state.with(CRAFT_STATE, next));
                if (isMainHand){
                    this.setStack(currentState, player.getMainHandStack().split(1));
                } else {
                    this.setStack(currentState, player.getOffHandStack().split(1));
                }
                playSound(SoundEvents.ENTITY_ITEM_PICKUP,1.0f, world.random.nextFloat() + 0.1f);
            } else {
                player.sendMessage(Text.translatable(NEED_INGREDIENT),true);
            }
        } else if (currentState == 5){
            if (item == ModItems.CHEESE){
                SimpleInventory inventory = new SimpleInventory(PIZZA_INV.size());
                for (int i = 0;i < 5;i++){
                    inventory.setStack(i,this.getStack(i));
                    this.setStack(i,ItemStack.EMPTY);
                }
                player.getMainHandStack().decrement(1);
                ItemStack rawPizza = new ItemStack(ModBlocks.RAW_PIZZA_ITEM);
                List<ItemStack> itemStackList = new ArrayList<>();
                for(int i = 0; i < inventory.size(); ++i) {
                    ItemStack itemStack = inventory.getStack(i);
                    itemStackList.add(itemStack);
                }
                ContainerComponent component = ContainerComponent.fromStacks(itemStackList);
                rawPizza.set(DataComponentTypes.CONTAINER,component);
                playSound(SoundEvents.BLOCK_HONEY_BLOCK_PLACE,1.0f, world.random.nextFloat() + 0.1f);
                ItemScatterer.spawn(world,pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5,rawPizza);
                world.breakBlock(pos, false);
            } else {
                player.sendMessage(Text.translatable(NEED_CHEESE), true);
            }
        }
        markDirty();
    }
    private boolean isPizzaIngredients(@NotNull Item item) {
        ItemStack stack = item.getDefaultStack();
        ArrayList<Item> list = Lists.newArrayList();
        for (RegistryEntry<Item> registryEntry : Registries.ITEM.iterateEntries(TagKeys.PIZZA_INGREDIENTS)) {
            list.add(registryEntry.value());
        }
        return list.contains(stack.getItem());
    }
}
