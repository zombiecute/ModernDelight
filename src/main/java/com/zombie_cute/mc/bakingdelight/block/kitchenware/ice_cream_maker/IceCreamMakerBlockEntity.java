package com.zombie_cute.mc.bakingdelight.block.kitchenware.ice_cream_maker;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.util.block_util.power_util.ACConsumer;
import com.zombie_cute.mc.bakingdelight.util.block_util.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.food.CreamItem;
import com.zombie_cute.mc.bakingdelight.screen.custom.IceCreamMakerScreenHandler;
import com.zombie_cute.mc.bakingdelight.util.enums.CreamFlavor;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IceCreamMakerBlockEntity extends BlockEntity implements GeoBlockEntity, ACConsumer, ImplementedInventory, ExtendedScreenHandlerFactory {
    public IceCreamMakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ICE_CREAM_MAKER_BLOCK_ENTITY, pos, state);
        propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> IceCreamMakerBlockEntity.this.isPowered;
                    case 1 -> IceCreamMakerBlockEntity.this.progress;

                    case 2 -> IceCreamMakerBlockEntity.this.iceCream1.getFlavor().getId();
                    case 3 -> IceCreamMakerBlockEntity.this.iceCream1.getAmount();
                    case 4 -> IceCreamMakerBlockEntity.this.iceCream1.getSelected();

                    case 5 -> IceCreamMakerBlockEntity.this.iceCream2.getFlavor().getId();
                    case 6 -> IceCreamMakerBlockEntity.this.iceCream2.getAmount();
                    case 7 -> IceCreamMakerBlockEntity.this.iceCream2.getSelected();

                    case 8 -> IceCreamMakerBlockEntity.this.iceCream3.getFlavor().getId();
                    case 9 -> IceCreamMakerBlockEntity.this.iceCream3.getAmount();
                    case 10 -> IceCreamMakerBlockEntity.this.iceCream3.getSelected();
                    default -> 0;
                };
            }
            @Override
            public void set(int index, int value) {}
            @Override
            public int size() {
                return 11;
            }
        };
    }
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final PropertyDelegate propertyDelegate;
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation START = RawAnimation.begin().thenPlay("start");
    private int isPowered = 0;
    private final IceCream iceCream1 = new IceCream();
    private final IceCream iceCream2 = new IceCream();
    private final IceCream iceCream3 = new IceCream();
    private int progress = 0;

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5,ItemStack.EMPTY);
    private int ticker = 0;
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            if (Objects.requireNonNull(state.getAnimatable().getCachedState().get(IceCreamMakerBlock.START))) {
                return state.setAndContinue(START);
            } else {
                return state.setAndContinue(IDLE);
            }
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient){
            return;
        }
        if (isPowered > 0){
            this.isPowered--;
        } else {
            if (this.progress > 0){
                progress--;
            }
        }
        if (state.get(IceCreamMakerBlock.START)){
            ticker++;
            if (ticker >= 60){
                ItemScatterer.spawn(world,pos.getX(),pos.getY(), pos.getZ(), this.getStack(4));
                this.setStack(4,ItemStack.EMPTY);
                world.setBlockState(pos,state.with(IceCreamMakerBlock.START,false));
            }
        } else ticker = 0;
        if (world.getTime() %20L == 0L){
            if (this.getStack(0).getItem() instanceof CreamItem cream){
                CreamFlavor creamFlavor = cream.getFlavor();
                int findNull = findNull();
                int findSameFlavor = findSameFlavor(creamFlavor);
                if (hasRecipe() && (findNull != 0 || findSameFlavor != 0)){
                    progress++;
                    int maxProgress = 10;
                    if (progress >= maxProgress){
                        if (findSameFlavor != 0){
                            switch (findSameFlavor){
                                case 1 -> this.iceCream1.changeAmount(100);
                                case 2 -> this.iceCream2.changeAmount(100);
                                case 3 -> this.iceCream3.changeAmount(100);
                            }
                        } else {
                            switch (findNull){
                                case 1 -> {
                                    this.iceCream1.setFlavor(creamFlavor);
                                    this.iceCream1.setAmount(100);
                                }
                                case 2 -> {
                                    this.iceCream2.setFlavor(creamFlavor);
                                    this.iceCream2.setAmount(100);
                                }
                                case 3 -> {
                                    this.iceCream3.setFlavor(creamFlavor);
                                    this.iceCream3.setAmount(100);
                                }
                            }
                        }
                        this.removeStack(0,1);
                        this.removeStack(1,1);
                        this.removeStack(2,1);
                        ItemStack stack3 = this.getStack(3);
                        int count = stack3.getCount();
                        if (stack3.isEmpty()){
                            this.setStack(3,Items.BOWL.getDefaultStack());
                        } else {
                            if (stack3.getItem() == Items.BOWL && count < stack3.getMaxCount()){
                                this.setStack(3,new ItemStack(Items.BOWL,count+1));
                            } else ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(Items.BOWL));
                        }
                        progress = 0;
                    }
                } else progress = 0;
            } else progress = 0;
            markDirty();
        }
    }
    private int findSameFlavor(CreamFlavor creamFlavor){
        if (this.iceCream1.getFlavor() == creamFlavor && this.iceCream1.getAmount() != 1000){
            return 1;
        }
        if (this.iceCream2.getFlavor() == creamFlavor && this.iceCream2.getAmount() != 1000) {
            return 2;
        }
        if (this.iceCream3.getFlavor() == creamFlavor && this.iceCream3.getAmount() != 1000) {
            return 3;
        }
        return 0;
    }
    private int findNull(){
        if (this.iceCream1.getFlavor().getId() == -1){
            return 1;
        }
        if (this.iceCream2.getFlavor().getId() == -1) {
            return 2;
        }
        if (this.iceCream3.getFlavor().getId() == -1) {
            return 3;
        }
        return 0;
    }
    private boolean hasRecipe(){
        return this.isPowered != 0 &&
                this.getStack(1).getItem() == Items.SUGAR &&
                this.getStack(2).getItem() == Items.EGG;
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("ice_cream_maker.progress",this.progress);

        nbt.putInt("ice_cream_maker.ice_cream1.flavor",this.iceCream1.getFlavor().getId());
        nbt.putInt("ice_cream_maker.ice_cream1.amount",this.iceCream1.getAmount());
        nbt.putBoolean("ice_cream_maker.ice_cream1.selected",this.iceCream1.isSelected());

        nbt.putInt("ice_cream_maker.ice_cream2.flavor",this.iceCream2.getFlavor().getId());
        nbt.putInt("ice_cream_maker.ice_cream2.amount",this.iceCream2.getAmount());
        nbt.putBoolean("ice_cream_maker.ice_cream2.selected",this.iceCream2.isSelected());

        nbt.putInt("ice_cream_maker.ice_cream3.flavor",this.iceCream3.getFlavor().getId());
        nbt.putInt("ice_cream_maker.ice_cream3.amount",this.iceCream3.getAmount());
        nbt.putBoolean("ice_cream_maker.ice_cream3.selected",this.iceCream3.isSelected());
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        this.progress = nbt.getInt("ice_cream_maker.progress");

        this.iceCream1.setFlavor(CreamFlavor.getFlavorByID(nbt.getInt("ice_cream_maker.ice_cream1.flavor")));
        this.iceCream1.setAmount(nbt.getInt("ice_cream_maker.ice_cream1.amount"));
        this.iceCream1.setSelected(nbt.getBoolean("ice_cream_maker.ice_cream1.selected"));

        this.iceCream2.setFlavor(CreamFlavor.getFlavorByID(nbt.getInt("ice_cream_maker.ice_cream2.flavor")));
        this.iceCream2.setAmount(nbt.getInt("ice_cream_maker.ice_cream2.amount"));
        this.iceCream2.setSelected(nbt.getBoolean("ice_cream_maker.ice_cream2.selected"));

        this.iceCream3.setFlavor(CreamFlavor.getFlavorByID(nbt.getInt("ice_cream_maker.ice_cream3.flavor")));
        this.iceCream3.setAmount(nbt.getInt("ice_cream_maker.ice_cream3.amount"));
        this.iceCream3.setSelected(nbt.getBoolean("ice_cream_maker.ice_cream3.selected"));
    }
    @Override
    public long getConsumedValue() {
        return 20;
    }

    @Override
    public boolean isWorking() {
        return true;
    }

    @Override
    public void energize() {
        this.isPowered = 100;
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
        return ModBlocks.ICE_CREAM_MAKER.getName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new IceCreamMakerScreenHandler(syncId,playerInventory,this,propertyDelegate);
    }
    public static final String NEED_CONE = "bakingdelight.ice_cream_maker.need_cone";
    public static final String NEED_SELECT = "bakingdelight.ice_cream_maker.need_select";

    public void tryStart(BlockState state, World world, PlayerEntity player) {
        if (!state.get(IceCreamMakerBlock.START)){
            if (player.getMainHandStack().getItem() != ModItems.ICE_CREAM_CONE){
                player.sendMessage(Text.translatable(NEED_CONE),true);
                return;
            }
            List<CreamFlavor> creamFlavors = new ArrayList<>();
            if (iceCream1.isSelected() && iceCream1.getFlavor() != CreamFlavor.NULL && iceCream1.getAmount() >= 50){
                creamFlavors.add(iceCream1.getFlavor());
                iceCream1.changeAmount(-50);
            }
            if (iceCream2.isSelected() && iceCream2.getFlavor() != CreamFlavor.NULL && iceCream2.getAmount() >= 50){
                creamFlavors.add(iceCream2.getFlavor());
                iceCream2.changeAmount(-50);
            }
            if (iceCream3.isSelected() && iceCream3.getFlavor() != CreamFlavor.NULL && iceCream3.getAmount() >= 50){
                creamFlavors.add(iceCream3.getFlavor());
                iceCream3.changeAmount(-50);
            }
            if (creamFlavors.isEmpty()){
                player.sendMessage(Text.translatable(NEED_SELECT),true);
                return;
            }
            ItemStack iceCream = new ItemStack(ModItems.ICE_CREAM);
            for (CreamFlavor creamFlavor : creamFlavors){
                CreamFlavor.addFlavorToFood(iceCream, creamFlavor);
            }
            this.setStack(4,iceCream);
            player.getMainHandStack().decrement(1);
            world.setBlockState(pos,state.with(IceCreamMakerBlock.START,true));
        }
    }

    public void changeIceCream1() {
        this.iceCream1.setSelected(!this.iceCream1.isSelected());
    }
    public void changeIceCream2() {
        this.iceCream2.setSelected(!this.iceCream2.isSelected());
    }
    public void changeIceCream3() {
        this.iceCream3.setSelected(!this.iceCream3.isSelected());
    }

    public static class IceCream {
        public IceCream(CreamFlavor creamFlavor, int amount){
            this.creamFlavor = creamFlavor;
            this.amount = amount;
        }
        public IceCream(){
            this.creamFlavor = CreamFlavor.NULL;
            this.amount = 0;
        }
        public CreamFlavor getFlavor() {
            return creamFlavor;
        }
        public int getSelected(){
            return isSelected ? 1 : 0;
        }
        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
        public void setSelected(int value){
            isSelected = value != 0;
        }

        public void setFlavor(CreamFlavor creamFlavor) {
            this.creamFlavor = creamFlavor;
            if (creamFlavor == CreamFlavor.NULL){
                this.amount = 0;
            }
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            if (this.creamFlavor != CreamFlavor.NULL){
                if (amount > MAX_AMOUNT){
                    this.amount = MAX_AMOUNT;
                } else {
                    this.amount = Math.max(amount, 0);
                }
                if (this.amount == 0){
                    this.creamFlavor = CreamFlavor.NULL;
                }
            }
        }
        public void changeAmount(int value){
            if (this.creamFlavor != CreamFlavor.NULL){
                if (this.amount + value <= 0){
                    this.amount = 0;
                    this.creamFlavor = CreamFlavor.NULL;
                } else if (this.amount + value >= MAX_AMOUNT){
                    this.amount = MAX_AMOUNT;
                } else this.amount += value;
            }
        }

        private CreamFlavor creamFlavor;
        private int amount;
        private final int MAX_AMOUNT = 1000;
        private boolean isSelected = false;
    }
}
