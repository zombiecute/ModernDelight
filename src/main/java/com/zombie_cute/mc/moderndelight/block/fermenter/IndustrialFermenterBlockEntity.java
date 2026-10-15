package com.zombie_cute.mc.moderndelight.block.fermenter;

import com.zombie_cute.mc.moderndelight.block.ModBlockEntities;
import com.zombie_cute.mc.moderndelight.block.ModBlocks;
import com.zombie_cute.mc.moderndelight.fluid.ModFluid;
import com.zombie_cute.mc.moderndelight.fluid.custom.AlcoholFluid;
import com.zombie_cute.mc.moderndelight.fluid.custom.VinegarFluid;
import com.zombie_cute.mc.moderndelight.item.ModItems;
import com.zombie_cute.mc.moderndelight.item.tools.FermentationTankItem;
import com.zombie_cute.mc.moderndelight.networking.packet.ItemStackSyncS2CPacket;
import com.zombie_cute.mc.moderndelight.recipe.custom.FermentingRecipe;
import com.zombie_cute.mc.moderndelight.screen.custom.IndustrialFermenterScreenHandler;
import com.zombie_cute.mc.moderndelight.util.FluidStack;
import com.zombie_cute.mc.moderndelight.util.TemperatureUtil;
import com.zombie_cute.mc.moderndelight.util.block_util.FluidStorageAble;
import com.zombie_cute.mc.moderndelight.util.block_util.ImplementedInventory;
import com.zombie_cute.mc.moderndelight.util.block_util.power_util.ACConsumer;
import com.zombie_cute.mc.moderndelight.util.enums.BacteriaType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.mixin.transfer.BucketItemAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
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

import java.util.List;
import java.util.Map;

public class IndustrialFermenterBlockEntity extends BlockEntity implements GeoBlockEntity, ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory, ACConsumer, FluidStorageAble {

    public static final int MAX_CFU = Short.MAX_VALUE * 5;
    public static final int CFU_SATURATION_THRESHOLD = 50000;
    public static final int MIN_DOMINANT_CFU = 1000;
    public static final int MIN_AUXILIARY_CFU = 100;

    public IndustrialFermenterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INDUSTRIAL_FERMENTER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> IndustrialFermenterBlockEntity.this.progress;
                    case 1 -> IndustrialFermenterBlockEntity.this.maxProgress;
                    case 2 -> IndustrialFermenterBlockEntity.this.temperature;
                    case 3 -> IndustrialFermenterBlockEntity.this.temperatureFix;
                    case 4 -> IndustrialFermenterBlockEntity.this.noPowerTicker == 0 ? 0 : 1;
                    case 5 -> (int)(IndustrialFermenterBlockEntity.this.efficiency * 100.0);
                    case 6 -> {
                        if (getWorld() != null) {
                            yield IndustrialFermenterBlockEntity.this.getLightLevel(getWorld());
                        } else yield 15;
                    }
                    case 7 -> IndustrialFermenterBlockEntity.this.bacteriaCFUs[0] / 10;
                    case 8 -> IndustrialFermenterBlockEntity.this.bacteriaCFUs[1] / 10;
                    case 9 -> IndustrialFermenterBlockEntity.this.bacteriaCFUs[2] / 10;
                    case 10 -> IndustrialFermenterBlockEntity.this.bacteriaCFUs[3] / 10;
                    case 11 -> IndustrialFermenterBlockEntity.this.bacteriaCFUs[4] / 10;
                    default -> 0;
                };
            }
            @Override
            public void set(int index, int value) {}
            @Override
            public int size() {
                return 12;
            }
        };
    }
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation RUNNING = RawAnimation.begin().thenLoop("running");
    private static final RawAnimation OPEN = RawAnimation.begin().thenPlayAndHold("open");
    private static final RawAnimation CLOSE = RawAnimation.begin().thenPlayAndHold("close");
    public static final long MAX_FLUID_LEVEL = 5 * FluidConstants.BUCKET;

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private int cachedPower = 0;
    private int closingAnimationTick = 0;
    private int temperature = 25;
    private double efficiency = 1;
    private double spawnMiscBacteriaRate = 0.01;

    protected final PropertyDelegate propertyDelegate;
    public final SingleVariantStorage<FluidVariant> fluidStorage = new  SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }
        @Override
        protected long getCapacity(FluidVariant variant) {
            return MAX_FLUID_LEVEL;
        }
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
    private final DefaultedList<ItemStack> itemStorage = DefaultedList.ofSize(7,ItemStack.EMPTY);
    public static final int INPUT_SLOT_1 = 0;
    public static final int INPUT_SLOT_2 = 1;
    public static final int INPUT_SLOT_3 = 2;
    public static final int INPUT_SLOT_4 = 3;
    public static final int OUTPUT_SLOT = 4;
    public static final int BUCKET_INPUT_SLOT = 5;
    public static final int BUCKET_OUTPUT_SLOT = 6;
    private int progress = 0;
    private int maxProgress = 0;
    private int temperatureFix = 0;
    private int[] bacteriaCFUs = new int[BacteriaType.values().length];
    private int cachedQuality = 0;
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            World world = state.getAnimatable().getWorld();
            BlockState cached = state.getAnimatable().getCachedState();
            if (world != null){
                if (cached.getBlock() instanceof IndustrialFermenterBlock) {
                    if (cached.get(IndustrialFermenterBlock.IS_OPENING)){
                        return state.setAndContinue(OPEN);
                    }
                    if (cached.get(IndustrialFermenterBlock.IS_CLOSING)){
                        return state.setAndContinue(CLOSE);
                    }
                    if (cached.get(IndustrialFermenterBlock.IS_RUNNING)){
                        return state.setAndContinue(RUNNING);
                    }
                }
            }
            return state.setAndContinue(IDLE);
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private int ticker = 100;
    private int noPowerTicker = 10;
    public static void tick(World world, BlockPos pos, BlockState state, IndustrialFermenterBlockEntity be) {
        if (world.isClient) return;
        // 动画更新
        tickAnimation(world, pos, state, be);
        // 电力更新
        tickPower(be);
        // 温度更新
        tickTemperature(world, pos, be);
        // 装桶
        tickFluidIO(world, pos, state, be);
        // 发酵
        if (be.ticker % 20 == 0){
            tickFermentation(world, pos, state, be);
        }
    }

    private static void tickFermentation(World world, BlockPos pos, BlockState state, IndustrialFermenterBlockEntity be) {
        // 菌种自然涨落
        be.updateBacteriaGrowth(world);
        // 配方检测
        List<FermentingRecipe> matches = world.getRecipeManager().getAllMatches(FermentingRecipe.Type.INSTANCE, be.recipeInput, world);
        if (!matches.isEmpty()) {
            FermentingRecipe bestRecipe = null;
            double bestScore = -1;
            BacteriaType currentDominant = be.getDominantBacteria();
            for (FermentingRecipe recipe : matches) {
                // 流体检验
                if (recipe.getMinFluidAmount() > be.fluidStorage.amount) continue;
                if (!(recipe.getInputFluid() instanceof EmptyFluid) &&
                        !recipe.getInputFluid().equals(be.fluidStorage.variant.getFluid())) continue;
                // 菌种检验
                BacteriaType required = recipe.getBacteria_input();
                BacteriaType outputBacteria = recipe.getBacteria_output();
                if ((required != null && currentDominant != required) || currentDominant == outputBacteria) continue; // 严格不匹配则跳过
                // 评分
                double score = 0.0;
                double tempDiff = Math.abs(be.getRealTemperature() - recipe.getTemperature());
                double lightDiff = Math.abs(world.getLightLevel(pos) - recipe.getLuminosity());
                score -= tempDiff * 0.1 + lightDiff * 0.5;
                // 偏好时间短的配方
                score += 50.0 / (recipe.getTime() + 1);
                if (score > bestScore) {
                    bestScore = score;
                    bestRecipe = recipe;
                }
            }
            if (bestRecipe != null) {
                // 计算效率、品质
                double efficiency = be.calculateEfficiency(world, bestRecipe, currentDominant);
                if (be.noPowerTicker > 0) {
                    efficiency *= 3.0;
                    if (!state.get(IndustrialFermenterBlock.IS_RUNNING)) {
                        world.setBlockState(pos, state.with(IndustrialFermenterBlock.IS_RUNNING, true));
                    }
                } else {
                    world.setBlockState(pos, state.with(IndustrialFermenterBlock.IS_RUNNING, false));
                }
                int maxProgress = bestRecipe.getTime();
                be.efficiency = efficiency;
                be.maxProgress = (int) (maxProgress / efficiency);
                be.progress += 1;
                int quality = be.determineQuality(world, currentDominant);
                if (be.progress >= be.maxProgress) {
                    be.completeFermentation(world, pos, bestRecipe, quality, currentDominant, state);
                    be.reset(world, state);
                }
            } else {
                be.reset(world, state);
            }
        } else {
            be.reset(world, state);
        }
    }

    private static void tickFluidIO(World world, BlockPos pos, BlockState state, IndustrialFermenterBlockEntity be) {
        Fluid fluid = null;
        Item bucketInput = be.getStack(BUCKET_INPUT_SLOT).getItem();
        if (bucketInput == Items.BUCKET &&
                be.fluidStorage.amount >= FluidConstants.BUCKET &&
                be.getStack(BUCKET_OUTPUT_SLOT).isEmpty()) {
            be.getStack(BUCKET_INPUT_SLOT).decrement(1);
            ItemStack bucket = be.fluidStorage.variant.getFluid().getBucketItem().getDefaultStack();
            NbtCompound nbt = bucket.getOrCreateNbt();
            nbt.putInt("ferment_quality", be.cachedQuality);
            bucket.setNbt(nbt);
            be.setStack(BUCKET_OUTPUT_SLOT, bucket);
            be.fluidStorage.amount -= FluidConstants.BUCKET;
            if (be.fluidStorage.amount == 0) {
                be.fluidStorage.variant = FluidVariant.blank();
            }
            be.markDirty();
        } else if (bucketInput == ModItems.FERMENTATION_TANK) {
            ItemStack tank = be.getStack(BUCKET_INPUT_SLOT).copy();
            FluidStack tankFluid = FermentationTankItem.getFluidStack(tank);
            Map<BacteriaType,Integer> bacteriaMap = FermentationTankItem.getBacteriaMap(tank);
            if (!tankFluid.getFluidVariant().isBlank() && be.fluidStorage.variant.isBlank()) {
                be.getStack(BUCKET_INPUT_SLOT).decrement(1);
                be.fluidStorage.variant = tankFluid.getFluidVariant();
                be.fluidStorage.amount = tankFluid.getAmount();
                if (bacteriaMap != null) {
                    for (Map.Entry<BacteriaType,Integer> entry : bacteriaMap.entrySet()) {
                        int oldCFU = be.getCFU(entry.getKey());
                        be.setCFU(entry.getKey(), oldCFU + entry.getValue());
                    }
                }
                FermentationTankItem.resetBacteria(tank);
                FermentationTankItem.setFluidStack(tank, new FluidStack(FluidVariant.blank(),0));
                be.spawnTankItem(world, tank);
                be.markDirty();
            } else if (tankFluid.getFluidVariant().isBlank() && !be.fluidStorage.variant.isBlank()) {
                be.getStack(BUCKET_INPUT_SLOT).decrement(1);
                FluidStack currentFluid = new FluidStack(be.fluidStorage.variant, be.fluidStorage.amount);
                int[] bacteriaCFUs = new int[be.bacteriaCFUs.length];
                System.arraycopy(be.bacteriaCFUs, 0, bacteriaCFUs, 0, be.bacteriaCFUs.length);
                FermentationTankItem.setFluidStack(tank, currentFluid);
                FermentationTankItem.setBacteria(tank, bacteriaCFUs);
                be.fluidStorage.variant = FluidVariant.blank();
                be.fluidStorage.amount = 0;
                be.bacteriaCFUs = new int[be.bacteriaCFUs.length];
                be.reset(world, state);
                be.spawnTankItem(world, tank);
                be.markDirty();
            }
        } else if (bucketInput == Items.MILK_BUCKET){
            fluid = ModFluid.STILL_MILK;
        } else if (bucketInput instanceof BucketItemAccessor bucketItemAccessor){
            fluid = bucketItemAccessor.fabric_getFluid();
        }
        if (fluid != null && !(fluid instanceof EmptyFluid)){
            if ((fluid == be.fluidStorage.variant.getFluid() && be.fluidStorage.amount <= MAX_FLUID_LEVEL - FluidConstants.BUCKET)
                            || be.fluidStorage.variant.isBlank()) {
                NbtCompound nbt = be.getStack(BUCKET_INPUT_SLOT).getOrCreateNbt();
                int quality = nbt.contains("ferment_quality") ? nbt.getInt("ferment_quality") : 0;
                be.cachedQuality = (quality + be.cachedQuality) / 2;
                be.fluidStorage.variant = FluidVariant.of(fluid);
                be.fluidStorage.amount += FluidConstants.BUCKET;
                be.getStack(BUCKET_INPUT_SLOT).decrement(1);
                if (be.getStack(BUCKET_OUTPUT_SLOT).isEmpty()) {
                    be.setStack(BUCKET_OUTPUT_SLOT, Items.BUCKET.getDefaultStack());
                } else if (be.getStack(BUCKET_OUTPUT_SLOT).getItem() == Items.BUCKET
                        && be.getStack(BUCKET_OUTPUT_SLOT).getCount() < be.getStack(BUCKET_OUTPUT_SLOT).getMaxCount()) {
                    be.getStack(BUCKET_OUTPUT_SLOT).increment(1);
                } else {
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), Items.BUCKET.getDefaultStack());
                }
                be.markDirty();
            }
        }
    }

    private static void tickTemperature(World world, BlockPos pos, IndustrialFermenterBlockEntity be) {
        if (be.ticker > 0){
            be.ticker--;
        } else {
            be.temperature = TemperatureUtil.getTemperature(pos, world);
            be.ticker = 100;
        }
    }

    private static void tickPower(IndustrialFermenterBlockEntity be) {
        if (be.cachedPower > 0) {
            be.cachedPower--;
            be.noPowerTicker = 10;
        } else {
            if (be.noPowerTicker > 0) {
                be.noPowerTicker--;
            } else if (be.temperatureFix != 0 && be.ticker % 20 == 0) {
                be.temperatureFix += (be.temperatureFix > 0) ? -1 : 1;
            }
        }
    }

    private static void tickAnimation(World world, BlockPos pos, BlockState state, IndustrialFermenterBlockEntity be) {
        if (be.closingAnimationTick > 0) {
            be.closingAnimationTick--;
            if (be.closingAnimationTick == 0) {
                world.setBlockState(pos, state.with(IndustrialFermenterBlock.IS_CLOSING, false));
            }
        }
    }

    private void spawnTankItem(World world, ItemStack tank) {
        if (getStack(BUCKET_OUTPUT_SLOT).isEmpty()) {
            setStack(BUCKET_OUTPUT_SLOT, tank);
        } else {
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), tank);
        }
    }

    private void reset(World world, BlockState state) {
        maxProgress = 0;
        progress = 0;
        spawnMiscBacteriaRate = 0.01;
        world.setBlockState(pos, state.with(IndustrialFermenterBlock.IS_RUNNING, false));
        markDirty();
    }
    private void completeFermentation(World world, BlockPos pos, FermentingRecipe recipe, int quality, BacteriaType dominant, BlockState state) {
        Fluid result = recipe.getOutputFluid();
        if (result instanceof EmptyFluid) {
            if (!(recipe.getInputFluid() instanceof EmptyFluid)) {
                if (recipe.getMinFluidAmount() != 0){
                    fluidStorage.amount = fluidStorage.amount - recipe.getMinFluidAmount();
                    if (fluidStorage.amount <= 0){
                        fluidStorage.variant = FluidVariant.blank();
                    }
                } else  {
                    fluidStorage.variant = FluidVariant.blank();
                    fluidStorage.amount = 0;
                }
            }
        } else {
            fluidStorage.variant = FluidVariant.of(result);
        }
        world.setBlockState(pos, state.with(IndustrialFermenterBlock.IS_RUNNING, false));
        // 消耗输入物品
        for (int i = 0; i < 4; i++) {
            ItemStack input = getStack(i);
            if (input.isEmpty() || input.isOf(Items.BARRIER)) continue;
            ItemStack recipeRemainder = input.getRecipeRemainder().copy();
            input.decrement(1);
            if (!recipeRemainder.isEmpty()) ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),recipeRemainder);
        }
        // 产出输出物品
        ItemStack resultItem = recipe.getOutput(null).copy();
        if (resultItem.getItem() != Items.BARRIER) {
            NbtCompound nbt = resultItem.getOrCreateNbt();
            nbt.putInt("ferment_quality", quality);
            resultItem.setNbt(nbt);
            if (getStack(OUTPUT_SLOT).getItem() == resultItem.getItem() &&
                    getStack(OUTPUT_SLOT).getCount() + resultItem.getCount() <= resultItem.getMaxCount()){
                getStack(OUTPUT_SLOT).setCount(getStack(OUTPUT_SLOT).getCount() + resultItem.getCount());
            } else if (getStack(OUTPUT_SLOT).isEmpty()) {
                setStack(OUTPUT_SLOT,resultItem);
            } else {
                ItemScatterer.spawn(world,pos.getX(),pos.getY(),pos.getZ(),resultItem);
            }
        }
        // 处理菌种产出
        BacteriaType bacteriaOut = recipe.getBacteria_output();
        if (bacteriaOut != null) {
            setCFU(bacteriaOut, getCFU(bacteriaOut) + recipe.getBacteria_output_amount());
        }
        // 发酵完成，减少部分CFU
        if (dominant != null) {
            setCFU(dominant, (int)(getCFU(dominant) * 0.8));
        }
        markDirty();
    }
    private int determineQuality(World world, BacteriaType dominant) {
        int light = getLightLevel(world);
        int realTemp = getRealTemperature();
        int quality = 1; // 正常
        // 大肠杆菌降低品质效果
        if (dominant != BacteriaType.ESCHERICHIA && getCFU(BacteriaType.ESCHERICHIA) >= 300) quality -= 2;
        // 毛霉降低品质效果
        if (dominant != BacteriaType.MUCOR && getCFU(BacteriaType.MUCOR) >= 500) quality -= 1;
        // 醋酸菌降低品质效果
        if (dominant != BacteriaType.ACETOBACTER && getCFU(BacteriaType.ACETOBACTER) >= MIN_DOMINANT_CFU) quality -= 1;
        if (dominant != null) {
            int cfu = getCFU(dominant);
            int acetobacterCFU = getCFU(BacteriaType.ACETOBACTER);
            int yeastCFU = getCFU(BacteriaType.YEAST);
            switch (dominant) {
                case YEAST -> {
                    if (acetobacterCFU >= MIN_AUXILIARY_CFU && acetobacterCFU < MIN_DOMINANT_CFU) quality += 1; // 醋酸菌辅助酵母菌
                    if (cfu > 30000) quality -= 1; // 酵母过多降低品质
                    if (realTemp > 35 || light > 7) {
                        quality -= 1;
                        spawnMiscBacteriaRate = Math.min(1.0,spawnMiscBacteriaRate + 0.01); // 杂菌影响
                    }
                }
                case LACTOBACILLUS -> {
                    if (yeastCFU >= MIN_AUXILIARY_CFU) quality += 1; // 酵母菌辅助乳酸菌
                    if (cfu > 20000) quality -= 1; //乳酸菌过多降低品质
                    if (realTemp >= 40 || light >= 7) {
                        quality -= 1;
                        spawnMiscBacteriaRate = Math.min(1.0,spawnMiscBacteriaRate + 0.01); // 杂菌影响
                    }
                }
                case ESCHERICHIA -> {
                    if (dominant.isInBestCFU(cfu)){
                        quality += 2; // 制取维生素K
                    } else {
                        quality -= 2;
                    }
                }
                case MUCOR -> {
                    if (cfu >
                            10000) quality -= 1; // 毛霉过多降低品质
                }
            }
            // CFU不在完美范围
            if (!dominant.isInBestCFU(cfu)){
                quality -= 1;
            }
        }
        // 品质映射
        if (quality < 0) {
            quality = -1;
        } else if (quality > 2) {
            quality = 2;
        }
        cachedQuality = quality;
        return quality;
    }
    private int getCFU(BacteriaType type){
        return bacteriaCFUs[type.getRawID()];
    }
    private void setCFU(BacteriaType type, int value){
        bacteriaCFUs[type.getRawID()] = value;
    }
    private BacteriaType getSecondaryBacteria(){
        int max = 0;
        BacteriaType maxResult = null;
        BacteriaType secondResult = null;
        for (int i = 0; i < bacteriaCFUs.length; i++) {
            int cfu = bacteriaCFUs[i];
            if (cfu > max && cfu >= MIN_DOMINANT_CFU){
                max = cfu;
                secondResult = maxResult;
                maxResult = BacteriaType.getBacteriaFromRawID(i);
            }
        }
        return secondResult;
    }
    private BacteriaType getDominantBacteria(){
        int max = 0;
        BacteriaType maxResult = null;
        for (int i = 0; i < bacteriaCFUs.length; i++) {
            int cfu = bacteriaCFUs[i];
            if (cfu > max && cfu >= MIN_DOMINANT_CFU){
                max = cfu;
                maxResult = BacteriaType.getBacteriaFromRawID(i);
            }
        }
        return maxResult;
    }
    private double calculateEfficiency(World world, FermentingRecipe recipe, BacteriaType dominant) {
        double base = 1.0;
        Fluid outputFluid = recipe.getOutputFluid();
        // 温度影响
        int actualTemp = getRealTemperature();
        int optimalTemp = recipe.getTemperature();
        int deltaTemp = Math.abs(actualTemp - optimalTemp);
        base *= (30.0 / (30.0 + deltaTemp));
        // 亮度影响
        int optimalLight = recipe.getLuminosity();
        int light = getLightLevel(world);
        int deltaLight = Math.abs(light - optimalLight);
        base *= (9.0 / (9.0 + deltaLight));
        // CFU增益
        if (dominant != null) {
            int cfu = getCFU(dominant);
            if (dominant.isInBestCFU(cfu)) {
                switch (dominant) {
                    case ACETOBACTER -> {
                        if (outputFluid instanceof VinegarFluid) base *= 2.0;
                    }
                    case YEAST -> {
                        if (outputFluid instanceof AlcoholFluid) base *= 1.5;
                    }
                    case MUCOR -> base *= 1.3;
                    case LACTOBACILLUS -> {
                        int yeastCFU = getCFU(BacteriaType.YEAST);
                        if (yeastCFU >= MIN_AUXILIARY_CFU && yeastCFU <= cfu / 2) {
                            base *= 1.3;
                        }
                    }
                }
            }
            // 拮抗惩罚
            BacteriaType secondaryBacteria = getSecondaryBacteria();
            if (secondaryBacteria != null) {
                int secondaryCFU = getCFU(secondaryBacteria);
                if (secondaryCFU > cfu / 2) {
                    base *= 0.5;
                }
            }
        }
        // 大肠埃希菌侵袭惩罚
        int ecoliCFU = getCFU(BacteriaType.ESCHERICHIA);
        if (dominant != null && dominant != BacteriaType.ESCHERICHIA && ecoliCFU >= 300 && ecoliCFU >= getCFU(dominant) * 0.3) {
            base *= 0.5;
        }
        return base;
    }

    private int getLightLevel(World world) {
        return world.getLightLevel(pos);
    }

    private void updateBacteriaGrowth(World world) {
        for (BacteriaType type : BacteriaType.values()) {
            int cfu = getCFU(type);
            if (cfu == 0) continue;
            // 环境条件
            int actualTemp = getRealTemperature();
            int deltaTemperature = Math.abs(actualTemp - type.getOptimalTemp());
            boolean suitableTemp = deltaTemperature <= type.getTolerance();
            boolean suitableLight = BacteriaType.checkLight(world, pos, type);
            if (suitableTemp && suitableLight && !fluidStorage.variant.isBlank()) {
                double growthFactor = getGrowthFactor(type, actualTemp, deltaTemperature);
                if (cfu > CFU_SATURATION_THRESHOLD){
                    // 增长率随种群逼近最大值而下降
                    double growthRate = 0.02;
                    double carryingCapacityFactor = 1.0 - (double) cfu / MAX_CFU;
                    double delta = growthRate * cfu * carryingCapacityFactor * growthFactor;
                    // 随机波动，模拟微生物群落自然涨落
                    double fluctuation = (world.random.nextDouble() - 0.5) * 0.1;
                    delta += delta * fluctuation;
                    int newCFU = (int) (cfu + delta);
                    newCFU = Math.min(newCFU, MAX_CFU);
                    newCFU = Math.max(newCFU, 0);
                    setCFU(type, newCFU);
                } else {
                    setCFU(type, (int)(cfu + cfu * 0.02 * growthFactor));
                }
            } else {
                // 衰减
                setCFU(type, Math.max((int)(cfu - cfu * 0.01), 0));
            }
        }
        if (world.random.nextDouble() < spawnMiscBacteriaRate && getRealTemperature() >= 30 && getLightLevel(world) >= 7 && getCFU(BacteriaType.ACETOBACTER) < MIN_DOMINANT_CFU) {
            setCFU(BacteriaType.ACETOBACTER, getCFU(BacteriaType.ACETOBACTER) + MIN_AUXILIARY_CFU);
        }
        if (world.random.nextDouble() < spawnMiscBacteriaRate && getCFU(BacteriaType.ESCHERICHIA) < MIN_DOMINANT_CFU) {
            setCFU(BacteriaType.ESCHERICHIA, getCFU(BacteriaType.ESCHERICHIA) + MIN_AUXILIARY_CFU);
        }
    }

    private double getGrowthFactor(BacteriaType type, int actualTemp, int deltaTemperature) {
        double growthFactor;
        if (actualTemp == type.getOptimalTemp()) growthFactor = 2.0;
        else if (deltaTemperature <= type.getTolerance() / 2) growthFactor = 1.5;
        else growthFactor = 0.3; // 勉强可生长
        // 乳酸菌抑制
        BacteriaType dominant = getDominantBacteria();
        if (dominant == BacteriaType.LACTOBACILLUS && dominant != type) {
            growthFactor *= 0.7;
        }
        // 竞争修正
        if (dominant != null && dominant != type) {
            if (type == BacteriaType.ESCHERICHIA) growthFactor *= 0.6;
            else growthFactor *= 0.2;
        }
        return growthFactor;
    }

    private final SimpleInventory recipeInput = new SimpleInventory() {
        private static final ItemStack BARRIER = Items.BARRIER.getDefaultStack();

        @Override
        public ItemStack getStack(int slot) {
            return itemStorage.get(slot).isEmpty() ? BARRIER : itemStorage.get(slot);
        }
        @Override
        public int size() {
            return 4;
        }
    };
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,itemStorage);
        nbt.putLong("industrial_fermenter.fluid_amount", fluidStorage.amount);
        nbt.put("industrial_fermenter.fluid_variant", fluidStorage.variant.toNbt());
        nbt.putInt("industrial_fermenter.temperatureFix", temperatureFix);
        nbt.putInt("industrial_fermenter.progress", progress);
        nbt.putInt("industrial_fermenter.max_progress", maxProgress);
        nbt.putInt("industrial_fermenter.closingAnimationTick", closingAnimationTick);
        nbt.putIntArray("industrial_fermenter.bacteria_cfus",bacteriaCFUs);
        nbt.putInt("industrial_fermenter.cached_quality", cachedQuality);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,itemStorage);
        NbtCompound fluidNbt = nbt.getCompound("industrial_fermenter.fluid_variant");
        if (!fluidNbt.isEmpty()) {
            fluidStorage.variant = FluidVariant.fromNbt(fluidNbt);
            fluidStorage.amount = nbt.getLong("industrial_fermenter.fluid_amount");
        } else {
            fluidStorage.variant = FluidVariant.blank();
            fluidStorage.amount = 0;
        }
        temperatureFix = nbt.getInt("industrial_fermenter.temperatureFix");
        progress = nbt.getInt("industrial_fermenter.progress");
        maxProgress = nbt.getInt("industrial_fermenter.max_progress");
        closingAnimationTick = nbt.getInt("industrial_fermenter.closingAnimationTick");
        int [] tempCFU = nbt.getIntArray("industrial_fermenter.bacteria_cfus");
        if (tempCFU.length != BacteriaType.values().length) {
            bacteriaCFUs = new int[BacteriaType.values().length];
        } else bacteriaCFUs = tempCFU.clone();
        cachedQuality = nbt.getInt("industrial_fermenter.cached_quality");
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    @Override
    public void markDirty() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
            ItemStackSyncS2CPacket.send(pos,getItems(),world);
            sendFluidPacket(world,pos);
        }
        super.markDirty();
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return itemStorage;
    }

    @Override
    public long getConsumedValue() {
        return 30 + Math.abs(temperatureFix);
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
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return new int[]{OUTPUT_SLOT, BUCKET_OUTPUT_SLOT};
        }
        return new int[]{INPUT_SLOT_1, INPUT_SLOT_2, INPUT_SLOT_3, INPUT_SLOT_4, BUCKET_INPUT_SLOT};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (slot == BUCKET_INPUT_SLOT && stack.getItem() instanceof BucketItem && dir != Direction.DOWN) {
            return true;
        }
        return (slot >= INPUT_SLOT_1 && slot <= INPUT_SLOT_4) && dir != Direction.DOWN;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return (slot == OUTPUT_SLOT || slot == BUCKET_OUTPUT_SLOT) && dir == Direction.DOWN;
    }

    @Override
    public Text getDisplayName() {
        return ModBlocks.INDUSTRIAL_FERMENTER.getName();
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new IndustrialFermenterScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public SingleVariantStorage<FluidVariant> getFluidStorage() {
        return fluidStorage;
    }

    public FluidStack getFluidStackCopy(){
        return new FluidStack(fluidStorage.variant,fluidStorage.amount);
    }

    public void reduceFix() {
        if (temperatureFix <= -30) {
            return;
        }
        temperatureFix = temperatureFix - 1;
    }

    public void addFix() {
        if (temperatureFix >= 30) {
            return;
        }
        temperatureFix = temperatureFix + 1;
    }

    public void setOpening() {
        if (world == null || world.isClient) return;
        world.playSound(null, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(IndustrialFermenterBlock.IS_CLOSING, false)
                .with(IndustrialFermenterBlock.IS_OPENING, true));
        markDirty();
    }

    public void setClosing() {
        if (world == null || world.isClient) return;
        world.playSound(null, pos, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(IndustrialFermenterBlock.IS_OPENING, false)
                .with(IndustrialFermenterBlock.IS_CLOSING, true));
        closingAnimationTick = 40;
        markDirty();
    }
    public int getRealTemperature() {
        return temperature + temperatureFix;
    }

    public void clear() {
        fluidStorage.amount = 0;
        fluidStorage.variant = FluidVariant.blank();
        markDirty();
    }
}
