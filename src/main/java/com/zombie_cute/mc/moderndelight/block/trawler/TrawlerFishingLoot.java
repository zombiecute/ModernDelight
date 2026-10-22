package com.zombie_cute.mc.moderndelight.block.trawler;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import com.zombie_cute.mc.moderndelight.advancement.ModCriteria;
import com.zombie_cute.mc.moderndelight.util.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.loot.LootTables;
import net.minecraft.world.LightType;

import java.util.ArrayList;
import java.util.List;

public final class TrawlerFishingLoot {
    public static int getDefaultIntervalTicks() {
        try {
            int value = ModConfig.baseFishingIntervals;
            if (value > 0){
                return value;
            } else return 1200;
        } catch (Throwable e){
            return 1200;
        }
    }

    private static final int INVENTORY_START = 1;
    private static final int INVENTORY_END = 18;

    private static final Identifier FISH_LOOT = LootTables.FISHING_FISH_GAMEPLAY;

    private static final Identifier JUNK_LOOT = LootTables.FISHING_JUNK_GAMEPLAY;

    /**
     * 尝试生成并存储一次战利品。
     */
    public static void tryCatch(ServerWorld world, BlockPos pos, TrawlerBlockEntity be,
                                boolean openWater, TrawlerBaitConfig baitConfig) {
        ItemStack baitStack = be.getStack(TrawlerBlockEntity.BAIT_SLOT);
        // 有鱼饵配置时优先尝试专属战利品表
        List<ItemStack> loot = null;
        if (baitConfig != null) {
            Identifier customTable = openWater ? baitConfig.lootTable() : JUNK_LOOT;
            if (customTable != null) {
                loot = generateFromTable(world, pos, baitStack, customTable);
            }
        }
        // 没有配置、配置表不存在或生成失败时回退。
        if (loot == null) {
            loot = generateDefaultLoot(
                    world, pos, baitStack, openWater
            );
        }
        if (loot == null || loot.isEmpty()) {
            return;
        }
        // 先在副本上模拟插入，确认全部物品都能容纳。
        List<ItemStack> simulated = copyStorage(be);

        for (ItemStack stack : loot) {
            if (stack.isEmpty()) {
                continue;
            }

            if (!insertStack(simulated, stack.copy())) {
                return;
            }
        }
        // 至少需要一件实际生成的物品。
        boolean hasLoot = loot.stream().anyMatch(stack -> !stack.isEmpty());
        if (!hasLoot) {
            return;
        }
        // 模拟成功，才写回真实库存。
        for (int slot = INVENTORY_START; slot <= INVENTORY_END; slot++) {
            be.setStack(slot, simulated.get(slot));
        }
        be.markDirty();
        // 只在收获物成功存入后消耗鱼饵。
        float probabilityOfConsume = 0;
        if (baitConfig != null) {
            probabilityOfConsume = baitConfig.probabilityOfConsume();
        }
        if (world.random.nextFloat() < probabilityOfConsume) {
            ItemStack currentBait = be.getStack(TrawlerBlockEntity.BAIT_SLOT);
            if (!currentBait.isEmpty()) {
                currentBait.decrement(1);
                be.markDirty();
            }
        }

        // 让拖网附近的玩家获得“亲眼见证收获”的进度。
        PlayerEntity nearbyPlayer = world.getClosestPlayer(
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16.0, false);
        if (nearbyPlayer instanceof ServerPlayerEntity serverPlayer) {
            for (ItemStack stack : loot) {
                if (!stack.isEmpty()) {
                    ModCriteria.TRAWLER_CATCH.trigger(serverPlayer, stack);
                }
            }
        }

    }

    /**
     * 默认模式：
     * 开阔水域按原版鱼类与垃圾权重 85:10 抽取；
     * 非开阔水域仅抽取垃圾。
     * <p>
     * 宝藏表在任何情况下都不会被调用。
     */
    private static List<ItemStack> generateDefaultLoot(ServerWorld world, BlockPos pos,
            ItemStack tool, boolean openWater) {
        Identifier tableId;
        if (!openWater) {
            tableId = JUNK_LOOT;
        } else {
            tableId = world.random.nextInt(95) < 85 ? FISH_LOOT : JUNK_LOOT;
        }
        return generateFromTable(world, pos, tool, tableId);
    }

    /**
     * 从当前服务器已经加载的战利品表生成物品。
     * <p>
     * 返回 null 表示战利品表不存在或生成失败；
     * 返回空列表表示表成功执行，但本次没有产生物品。
     */
    private static List<ItemStack> generateFromTable(ServerWorld world, BlockPos pos,
            ItemStack tool, Identifier tableId) {
        try {
            var lootManager = world.getServer().getLootManager();
            if (!lootManager.getIds(LootDataType.LOOT_TABLES).contains(tableId)) {
                return null;
            }

            LootTable table = lootManager.getLootTable(tableId);
            FishingBobberEntity bobber = new FishingBobberEntity(EntityType.FISHING_BOBBER, world);
            bobber.setPosition(Vec3d.ofCenter(pos));
            LootContextParameterSet parameters =
                    new LootContextParameterSet.Builder(world)
                            .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
                            .add(LootContextParameters.TOOL, tool.isEmpty() ? ItemStack.EMPTY : tool.copy())
                            .add(LootContextParameters.THIS_ENTITY, bobber)
                            .luck(0.0F)
                            .build(LootContextTypes.FISHING);
            LootContext context = new LootContext.Builder(parameters).random(world.random.nextLong()).build(null);
            List<ItemStack> generated = new ArrayList<>();
            table.generateLoot(context, stack -> {
                if (!stack.isEmpty()) {
                    generated.add(stack.copy());
                }
            });
            return generated;
        } catch (Exception e) {
            ModernDelightMain.LOGGER.error("[{}] Failed to generate loot from {}: {}", ModernDelightMain.MOD_ID, tableId, e.getMessage());
            return null;
        }
    }

    public static boolean doStorageSpaceRunOut(TrawlerBlockEntity be) {
        for (int slot = INVENTORY_START; slot <= INVENTORY_END; slot++) {
            if (be.getStack(slot).isEmpty()) {
                return false;
            }
            ItemStack current = be.getStack(slot);
            if (current.getCount() < current.getMaxCount()) {
                return false;
            }
        }
        return true;
    }

    private static List<ItemStack> copyStorage(TrawlerBlockEntity be) {
        List<ItemStack> copy = new ArrayList<>(19);
        for (int slot = 0; slot < 19; slot++) {
            copy.add(be.getStack(slot).copy());
        }
        return copy;
    }
    /**
     * 将一组物品插入库存副本。
     * 只允许使用第 1～18 格。
     */
    private static boolean insertStack(List<ItemStack> storage, ItemStack input) {
        if (input.isEmpty()) {
            return true;
        }
        // 先堆叠到已有的相同物品上。
        for (int slot = INVENTORY_START; slot <= INVENTORY_END && !input.isEmpty(); slot++) {
            ItemStack existing = storage.get(slot);
            if (!existing.isEmpty() && ItemStack.canCombine(existing, input)) {
                int space = existing.getMaxCount() - existing.getCount();
                if (space > 0) {
                    int moved = Math.min(space, input.getCount());
                    existing.increment(moved);
                    input.decrement(moved);
                }
            }
        }
        // 再放入空格；如果一个格子装不下，就拆分堆叠。
        for (int slot = INVENTORY_START; slot <= INVENTORY_END && !input.isEmpty(); slot++) {
            if (!storage.get(slot).isEmpty()) {
                continue;
            }
            int moved = Math.min(input.getCount(), input.getMaxCount());

            ItemStack part = input.copy();
            part.setCount(moved);

            storage.set(slot, part);
            input.decrement(moved);
        }

        return input.isEmpty();
    }

    /**
     * 近似开阔水域判定。
     * <p>
     * 以拖网正下方的水体为中心检查 5×5×3 区域，同时要求天空光照 >= 12。
     */
    public static boolean isOpenWater(ServerWorld world, BlockPos trawlerPos) {
        BlockPos center = trawlerPos.down();
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = -2; y <= 0; y++) {
                    BlockPos check = center.add(x, y, z);
                    BlockState state = world.getBlockState(check);
                    if (!state.getFluidState().isIn(FluidTags.WATER)) {
                        return false;
                    }
                }
            }
        }
        return world.getLightLevel(LightType.SKY,trawlerPos.up()) >= 12;
    }
}