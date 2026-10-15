package com.zombie_cute.mc.moderndelight.block.trawler;

import com.zombie_cute.mc.moderndelight.block.ModBlockEntities;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.screen.custom.TrawlerScreenHandler;
import com.zombie_cute.mc.moderndelight.util.block_util.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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

public class TrawlerBlockEntity extends BlockEntity implements GeoBlockEntity, ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory {
    public TrawlerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAWLER_BLOCK_ENTITY, pos, state);
    }

    public static final int BAIT_SLOT = 0;

    private final DefaultedList<ItemStack> itemStorage = DefaultedList.ofSize(19,ItemStack.EMPTY);
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final RawAnimation STATIC = RawAnimation.begin().thenLoop("static");
    private static final RawAnimation IDLE_UP = RawAnimation.begin().thenLoop("idle_up");
    private static final RawAnimation IDLE_DOWN = RawAnimation.begin().thenLoop("idle_down");
    private static final RawAnimation DOWN = RawAnimation.begin().thenPlayAndHold("down");
    private static final RawAnimation UP = RawAnimation.begin().thenPlayAndHold("up");
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,state -> {
            BlockState cached = state.getAnimatable().getCachedState();
            World world = state.getAnimatable().getWorld();
            BlockPos pos = state.getAnimatable().getPos();
            if (world != null
                    && cached.getBlock() instanceof TrawlerBlock
                    && world.getBlockState(pos.down()).getFluidState().isOf(Fluids.WATER)) {
                if (cached.get(TrawlerBlock.RISING)){
                    return state.setAndContinue(UP);
                }
                if (cached.get(TrawlerBlock.FALLING)){
                    return state.setAndContinue(DOWN);
                }
                return cached.get(TrawlerBlock.UP)
                        ? state.setAndContinue(IDLE_UP)
                        : state.setAndContinue(IDLE_DOWN);
            }
            return state.setAndContinue(STATIC);
        }));
    }
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,itemStorage);
        nbt.putInt("trawler.fishingTicker", fishingTicker);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,itemStorage);
        fishingTicker = nbt.getInt("trawler.fishingTicker");
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return itemStorage;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.getPos());
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] slots = new int[19];
        for (int i = 0; i < 19; i++) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        if (slot != BAIT_SLOT || stack.isEmpty()) {
            return false;
        }
        return TrawlerBaitManager.getConfig(stack.getItem()) != null;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot != BAIT_SLOT;
    }

    @Override
    public Text getDisplayName() {
        return ModBlocks.TRAWLER.getName();
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TrawlerScreenHandler(syncId, playerInventory, this);
    }
    private int fallingTicker = 0;
    private int risingTicker = 0;
    private int fishingTicker = 0;
    private int intervalTick = 0;

    public static void tick(World world, BlockPos pos, BlockState state, TrawlerBlockEntity be) {
        tickAnimation(world, pos, state, be);
        if (world instanceof ServerWorld serverWorld){
            tickFishing(serverWorld, pos, state, be);
        }
    }

    private static void tickFishing(ServerWorld serverWorld, BlockPos pos, BlockState state, TrawlerBlockEntity be) {
        // 拖网收起、正在下降或正在上升时不捕鱼
        if (state.get(TrawlerBlock.UP) || state.get(TrawlerBlock.FALLING) || state.get(TrawlerBlock.RISING)) {
            return;
        }
        TrawlerBaitConfig config = be.getCurrentBaitConfig();

        be.intervalTick = config != null ? config.intervalTicks()
                : TrawlerFishingLoot.getDefaultIntervalTicks();
        be.intervalTick = Math.max(1, be.intervalTick);
        // 满仓时暂停
        if (TrawlerFishingLoot.doStorageSpaceRunOut(be)) {
            return;
        }
        if (be.fishingTicker < be.intervalTick) {
            be.fishingTicker++;
            return;
        }
        boolean openWater = TrawlerFishingLoot.isOpenWater(serverWorld, pos);
        TrawlerFishingLoot.tryCatch(serverWorld, pos, be, openWater, config);
        be.fishingTicker = 0;
    }
    private TrawlerBaitConfig getCurrentBaitConfig() {
        ItemStack bait = itemStorage.get(BAIT_SLOT);
        if (bait.isEmpty()) {
            return null;
        }
        return TrawlerBaitManager.getConfig(bait.getItem());
    }
    private static void tickAnimation(World world, BlockPos pos, BlockState state, TrawlerBlockEntity be) {
        if (state.get(TrawlerBlock.FALLING)) {
            be.fallingTicker++;
            if (be.fallingTicker >= 40) {
                world.setBlockState(pos, state.with(TrawlerBlock.FALLING, false));
                be.fallingTicker = 0;
            }
        }
        if (state.get(TrawlerBlock.RISING)) {
            be.risingTicker++;
            if (be.risingTicker >= 60) {
                world.setBlockState(pos, state.with(TrawlerBlock.RISING, false));
                be.risingTicker = 0;
            }
        }
    }

    public void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        boolean flag = player.isSneaking();
        boolean is_up = state.get(TrawlerBlock.UP);
        if (flag && is_up) {
            player.openHandledScreen(this);
            return;
        }
        if (fallingTicker != 0 || risingTicker != 0) {
            return;
        }
        if (is_up) {
            world.playSound(null, pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS,1.0f,0.6f);
            world.setBlockState(pos, state.with(TrawlerBlock.FALLING, true).with(TrawlerBlock.UP, false));
        } else {
            world.setBlockState(pos, state.with(TrawlerBlock.RISING, true).with(TrawlerBlock.UP, true));
            world.playSound(null, pos.getX(),pos.getY(),pos.getZ(), SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS,1.0f,0.9f);
        }
    }

    public int getFishingTicker() {
        return fishingTicker;
    }

    public int getIntervalTick() {
        return intervalTick;
    }
}
