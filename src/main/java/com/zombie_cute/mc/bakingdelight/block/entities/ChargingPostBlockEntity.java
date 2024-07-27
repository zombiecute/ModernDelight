package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.abstracts.AbstractBatteryBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.DCConsumer;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.Power;
import com.zombie_cute.mc.bakingdelight.screen.custom.ChargingPostScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ChargingPostBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory {
    public ChargingPostBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHARGING_POST_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> ChargingPostBlockEntity.this.isWorking;
                    case 1 -> ChargingPostBlockEntity.this.ticker;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3,ItemStack.EMPTY);
    private final PropertyDelegate propertyDelegate;
    private int isWorking = 0;
    private int ticker = 0;
    private boolean dir = false;
    public void tick(World world) {
        if (world.isClient){
            return;
        }
        if (world.getTime() % 20L == 0){
            int b1 = 0;
            int b2 = 0;
            if (getStack(0).getItem() instanceof BlockItem blockItem){
                if (blockItem.getBlock() instanceof AbstractBatteryBlock){
                    b1 = AbstractBatteryBlock.getBatteryPower(getStack(0));
                }
            }
            if (getStack(1).getItem() instanceof BlockItem blockItem){
                if (blockItem.getBlock() instanceof AbstractBatteryBlock){
                    b2 = AbstractBatteryBlock.getBatteryPower(getStack(1));
                }
            }
            if (getStack(2).getItem() instanceof DCConsumer consumer){
                Power p = consumer.getPower(getStack(2));
                int need = p.getMaxPower() - p.getPowerValue();
                if (need >= 30){
                    if (b1 >= 30){
                        AbstractBatteryBlock.changeBatteryPower(getStack(0),30,false);
                        consumer.addPower(getStack(2),25);
                        isWorking = 1;
                    } else if (b2 >= 30) {
                        AbstractBatteryBlock.changeBatteryPower(getStack(1),30,false);
                        consumer.addPower(getStack(2),25);
                        isWorking = 1;
                    } else if (b1 > 0) {
                        AbstractBatteryBlock.changeBatteryPower(getStack(0),1,false);
                        consumer.addPower(getStack(2),1);
                        isWorking = 1;
                    } else if (b2 > 0) {
                        AbstractBatteryBlock.changeBatteryPower(getStack(1),1,false);
                        consumer.addPower(getStack(2),1);
                        isWorking = 1;
                    } else isWorking = 0;
                } else if (need > 0) {
                    if (b1 > 0) {
                        AbstractBatteryBlock.changeBatteryPower(getStack(0),1,false);
                        consumer.addPower(getStack(2),1);
                        isWorking = 1;
                    } else if (b2 > 0) {
                        AbstractBatteryBlock.changeBatteryPower(getStack(1),1,false);
                        consumer.addPower(getStack(2),1);
                        isWorking = 1;
                    } else isWorking = 0;
                } else isWorking = 0;
            } else isWorking = 0;
        }
        if (isWorking != 0){
            if (!dir){
                ticker++;
            } else {
                ticker--;
            }
            if (ticker == 0){
                dir = false;
            }
            if (ticker == 10){
                dir = true;
            }
        } else {
            ticker = 0;
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        createNbt();
        return super.toInitialChunkDataNbt();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,inventory);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public Text getDisplayName() {
        return ModBlocks.CHARGING_POST.getName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ChargingPostScreenHandler(syncId,playerInventory,this,propertyDelegate);
    }
    public ItemStack getRendererStack(){
        return this.getStack(2);
    }
}
