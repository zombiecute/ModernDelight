package com.zombie_cute.mc.bakingdelight.block.entities;

import com.zombie_cute.mc.bakingdelight.block.ModBlockEntities;
import com.zombie_cute.mc.bakingdelight.block.ModBlocks;
import com.zombie_cute.mc.bakingdelight.block.custom.IceCreamMakerBlock;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ACConsumer;
import com.zombie_cute.mc.bakingdelight.block.entities.utils.ImplementedInventory;
import com.zombie_cute.mc.bakingdelight.item.ModItems;
import com.zombie_cute.mc.bakingdelight.item.custom.CreamItem;
import com.zombie_cute.mc.bakingdelight.screen.custom.IceCreamMakerScreenHandler;
import com.zombie_cute.mc.bakingdelight.util.components.ModComponents;
import com.zombie_cute.mc.bakingdelight.util.components.cumstom.FlavorComponent;
import com.zombie_cute.mc.bakingdelight.util.components.cumstom.FlavorListComponent;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
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
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IceCreamMakerBlockEntity extends BlockEntity implements GeoBlockEntity, ACConsumer, ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {
    public IceCreamMakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ICE_CREAM_MAKER_BLOCK_ENTITY, pos, state);
        propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> IceCreamMakerBlockEntity.this.isPowered;
                    case 1 -> IceCreamMakerBlockEntity.this.progress;

                    case 2 -> IceCreamMakerBlockEntity.this.iceCream1.getFlavor().getID();
                    case 3 -> IceCreamMakerBlockEntity.this.iceCream1.getAmount();
                    case 4 -> IceCreamMakerBlockEntity.this.iceCream1.getSelected();

                    case 5 -> IceCreamMakerBlockEntity.this.iceCream2.getFlavor().getID();
                    case 6 -> IceCreamMakerBlockEntity.this.iceCream2.getAmount();
                    case 7 -> IceCreamMakerBlockEntity.this.iceCream2.getSelected();

                    case 8 -> IceCreamMakerBlockEntity.this.iceCream3.getFlavor().getID();
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

    public static void tick(World world, BlockPos pos, BlockState state, IceCreamMakerBlockEntity blockEntity) {
        if (world.isClient){
            return;
        }
        if (blockEntity.isPowered > 0){
            blockEntity.isPowered--;
        } else {
            if (blockEntity.progress > 0){
                blockEntity.progress--;
            }
        }
        if (state.get(IceCreamMakerBlock.START)){
            blockEntity.ticker++;
            if (blockEntity.ticker >= 60){
                ItemScatterer.spawn(world,pos.getX(),pos.getY(), pos.getZ(), blockEntity.getStack(4));
                blockEntity.setStack(4,ItemStack.EMPTY);
                world.setBlockState(pos,state.with(IceCreamMakerBlock.START,false));
            }
        } else blockEntity.ticker = 0;
        if (world.getTime() %20L == 0L){
            if (blockEntity.getStack(0).getItem() instanceof CreamItem cream){
                FlavorComponent flavorComponent = cream.getFlavor();
                int findNull = blockEntity.findNull();
                int findSameFlavor = blockEntity.findSameFlavor(flavorComponent);
                if (blockEntity.hasRecipe() && (findNull != 0 || findSameFlavor != 0)){
                    blockEntity.progress++;
                    int maxProgress = 10;
                    if (blockEntity.progress >= maxProgress){
                        if (findSameFlavor != 0){
                            switch (findSameFlavor){
                                case 1 -> blockEntity.iceCream1.changeAmount(100);
                                case 2 -> blockEntity.iceCream2.changeAmount(100);
                                case 3 -> blockEntity.iceCream3.changeAmount(100);
                            }
                        } else {
                            switch (findNull){
                                case 1 -> {
                                    blockEntity.iceCream1.setFlavor(flavorComponent);
                                    blockEntity.iceCream1.setAmount(100);
                                }
                                case 2 -> {
                                    blockEntity.iceCream2.setFlavor(flavorComponent);
                                    blockEntity.iceCream2.setAmount(100);
                                }
                                case 3 -> {
                                    blockEntity.iceCream3.setFlavor(flavorComponent);
                                    blockEntity.iceCream3.setAmount(100);
                                }
                            }
                        }
                        blockEntity.removeStack(0,1);
                        blockEntity.removeStack(1,1);
                        blockEntity.removeStack(2,1);
                        ItemStack stack3 = blockEntity.getStack(3);
                        int count = stack3.getCount();
                        if (stack3.isEmpty()){
                            blockEntity.setStack(3,Items.BOWL.getDefaultStack());
                        } else {
                            if (stack3.getItem() == Items.BOWL && count < stack3.getMaxCount()){
                                blockEntity.setStack(3,new ItemStack(Items.BOWL,count+1));
                            } else ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),new ItemStack(Items.BOWL));
                        }
                        blockEntity.progress = 0;
                    }
                } else blockEntity.progress = 0;
            } else blockEntity.progress = 0;
            blockEntity.markDirty();
        }
    }
    private int findSameFlavor(FlavorComponent flavorComponent){
        if (this.iceCream1.getFlavor() == flavorComponent && this.iceCream1.getAmount() != 1000){
            return 1;
        }
        if (this.iceCream2.getFlavor() == flavorComponent && this.iceCream2.getAmount() != 1000) {
            return 2;
        }
        if (this.iceCream3.getFlavor() == flavorComponent && this.iceCream3.getAmount() != 1000) {
            return 3;
        }
        return 0;
    }
    private int findNull(){
        if (this.iceCream1.getFlavor().getID() < 0){
            return 1;
        }
        if (this.iceCream2.getFlavor().getID() < 0) {
            return 2;
        }
        if (this.iceCream3.getFlavor().getID() < 0) {
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
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("ice_cream_maker.progress",this.progress);

        nbt.putInt("ice_cream_maker.ice_cream1.flavor",this.iceCream1.getFlavor().getID());
        nbt.putInt("ice_cream_maker.ice_cream1.amount",this.iceCream1.getAmount());
        nbt.putBoolean("ice_cream_maker.ice_cream1.selected",this.iceCream1.isSelected());

        nbt.putInt("ice_cream_maker.ice_cream2.flavor",this.iceCream2.getFlavor().getID());
        nbt.putInt("ice_cream_maker.ice_cream2.amount",this.iceCream2.getAmount());
        nbt.putBoolean("ice_cream_maker.ice_cream2.selected",this.iceCream2.isSelected());

        nbt.putInt("ice_cream_maker.ice_cream3.flavor",this.iceCream3.getFlavor().getID());
        nbt.putInt("ice_cream_maker.ice_cream3.amount",this.iceCream3.getAmount());
        nbt.putBoolean("ice_cream_maker.ice_cream3.selected",this.iceCream3.isSelected());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory,registryLookup);
        this.progress = nbt.getInt("ice_cream_maker.progress");

        this.iceCream1.setFlavor(FlavorComponent.getFlavorByID(nbt.getInt("ice_cream_maker.ice_cream1.flavor")));
        this.iceCream1.setAmount(nbt.getInt("ice_cream_maker.ice_cream1.amount"));
        this.iceCream1.setSelected(nbt.getBoolean("ice_cream_maker.ice_cream1.selected"));

        this.iceCream2.setFlavor(FlavorComponent.getFlavorByID(nbt.getInt("ice_cream_maker.ice_cream2.flavor")));
        this.iceCream2.setAmount(nbt.getInt("ice_cream_maker.ice_cream2.amount"));
        this.iceCream2.setSelected(nbt.getBoolean("ice_cream_maker.ice_cream2.selected"));

        this.iceCream3.setFlavor(FlavorComponent.getFlavorByID(nbt.getInt("ice_cream_maker.ice_cream3.flavor")));
        this.iceCream3.setAmount(nbt.getInt("ice_cream_maker.ice_cream3.amount"));
        this.iceCream3.setSelected(nbt.getBoolean("ice_cream_maker.ice_cream3.selected"));
        markDirty();
    }
    @Override
    public int getConsumedValue() {
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
            List<FlavorComponent> flavorComponents = new ArrayList<>();
            if (iceCream1.isSelected() && iceCream1.getFlavor() != FlavorComponent.NULL && iceCream1.getAmount() >= 50){
                iceCream1.changeAmount(-50);
                flavorComponents.add(iceCream1.getFlavor());
            }
            if (iceCream2.isSelected() && iceCream2.getFlavor() != FlavorComponent.NULL && iceCream2.getAmount() >= 50){
                iceCream2.changeAmount(-50);
                flavorComponents.add(iceCream2.getFlavor());
            }
            if (iceCream3.isSelected() && iceCream3.getFlavor() != FlavorComponent.NULL && iceCream3.getAmount() >= 50){
                iceCream3.changeAmount(-50);
                flavorComponents.add(iceCream3.getFlavor());
            }
            if (flavorComponents.isEmpty()){
                player.sendMessage(Text.translatable(NEED_SELECT),true);
                return;
            }
            ItemStack iceCream = new ItemStack(ModItems.ICE_CREAM);
            removeNullFlavor(flavorComponents);
            iceCream.set(ModComponents.FLAVOR_LIST,new FlavorListComponent(flavorComponents));
            this.setStack(4,iceCream);
            player.getMainHandStack().decrement(1);
            world.setBlockState(pos,state.with(IceCreamMakerBlock.START,true));
        }
    }

    private static void removeNullFlavor(List<FlavorComponent> flavorComponents) {
        for(int i = 0; i < flavorComponents.size(); i++){
            if (flavorComponents.get(i).isNull()){
                flavorComponents.remove(i);
                removeNullFlavor(flavorComponents);
                break;
            }
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

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return pos;
    }

    public static class IceCream {
        public IceCream(int flavorID, int amount){
            this.flavorID = flavorID;
            this.amount = amount;
        }
        public IceCream(){
            this.flavorID = -1;
            this.amount = 0;
        }
        public FlavorComponent getFlavor() {
            return FlavorComponent.getFlavorByID(flavorID);
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

        public void setFlavor(FlavorComponent flavorComponent) {
            this.flavorID = flavorComponent.getID();
            if (flavorComponent == FlavorComponent.NULL){
                this.amount = 0;
            }
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            if (this.flavorID >= 0){
                if (amount > MAX_AMOUNT){
                    this.amount = MAX_AMOUNT;
                } else {
                    this.amount = Math.max(amount, 0);
                }
                if (this.amount == 0){
                    this.flavorID = -1;
                }
            }
        }
        public void changeAmount(int value){
            if (this.flavorID >= 0){
                if (this.amount + value <= 0){
                    this.amount = 0;
                    this.flavorID = -1;
                } else if (this.amount + value >= MAX_AMOUNT){
                    this.amount = MAX_AMOUNT;
                } else this.amount += value;
            }
        }

        private int flavorID;
        private int amount;
        private final int MAX_AMOUNT = 1000;
        private boolean isSelected = false;
    }
}
