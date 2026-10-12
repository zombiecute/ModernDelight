package com.zombie_cute.mc.bakingdelight.util;

import com.zombie_cute.mc.bakingdelight.block.kitchenware.AdvanceFurnaceBlock;
import com.zombie_cute.mc.bakingdelight.block.kitchenware.OvenBlock;
import com.zombie_cute.mc.bakingdelight.tag.TagKeys;
import net.minecraft.block.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.*;

public class TemperatureUtil {
    private static final Map<TagKey<Block>, Integer> TEMPERATURE_TAGS = new LinkedHashMap<>();
    static {
        TEMPERATURE_TAGS.put(TagKeys.TEMPERATURE_MINUS_20, -20);
        TEMPERATURE_TAGS.put(TagKeys.TEMPERATURE_MINUS_10, -10);
        TEMPERATURE_TAGS.put(TagKeys.TEMPERATURE_0, 0);
        TEMPERATURE_TAGS.put(TagKeys.TEMPERATURE_40, 40);
        TEMPERATURE_TAGS.put(TagKeys.TEMPERATURE_60, 60);
        TEMPERATURE_TAGS.put(TagKeys.TEMPERATURE_80, 80);
        TEMPERATURE_TAGS.put(TagKeys.TEMPERATURE_100, 100);
        TEMPERATURE_TAGS.put(TagKeys.TEMPERATURE_300, 300);
        TEMPERATURE_TAGS.put(TagKeys.TEMPERATURE_600, 600);
        TEMPERATURE_TAGS.put(TagKeys.TEMPERATURE_1000, 1000);
    }
    private static final Map<Block, Integer> TEMP_CACHE = new HashMap<>();
    public static int getBiomeTemperature(BlockPos pos, World world) {
        Biome biome = world.getBiome(pos).value();
        if (biome != null) {
            float baseTemp = biome.getTemperature();
            // 高度修正 (Y>80 时引入噪声并逐格降温)
            if (pos.getY() > 80) {
                // 简易 XZ 噪声偏移，范围约 -0.01 ~ 0.01
                double noise = Math.sin((pos.getX() * 0.5 + pos.getZ() * 0.7) * 0.1) * 0.01;
                baseTemp += (float) noise;
                // 每升高 1 格温度下降 0.00125
                if (pos.getY() > 81) {
                    baseTemp -= (pos.getY() - 81) * 0.00125f;
                }
            }
            // 将原版温度映射为摄氏度
            return Math.round(baseTemp * 20 + 5);
        }
        return 25;
    }
    public static int getTemperature(BlockPos pos, World world) {
        if (world.isClient()) return 25;
        // 记录每个有效热/冷源的温差和衰减权重
        record HeatSource(double delta, double attenuation) {}
        List<HeatSource> sources = new ArrayList<>();
        int baseTemperature = getBiomeTemperature(pos, world);
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                for (int k = -3; k <= 3; k++) {
                    if (i == 0 && j == 0 && k == 0) continue;
                    BlockPos currentPos = pos.add(i, j, k);
                    BlockState state = world.getBlockState(currentPos);
                    int blockTemp = getBlockTemperature(state);
                    if (blockTemp == 25) continue;

                    double delta = blockTemp - baseTemperature; // 温差（正为热，负为冷）
                    double distanceSq = i * i + j * j + k * k;
                    double attenuation = 1.0 / (distanceSq + 1.0); // 衰减因子
                    sources.add(new HeatSource(delta, attenuation));
                }
            }
        }
        if (sources.isEmpty()) return baseTemperature;
        // 按衰减系数从大到小排序（最近的影响最大）
        sources.sort((a, b) -> Double.compare(b.attenuation, a.attenuation));
        // 主导热源（最近的）完全贡献，其余部分打折
        double totalDelta = sources.get(0).delta * sources.get(0).attenuation;
        for (int i = 1; i < sources.size(); i++) {
            totalDelta += sources.get(i).delta * sources.get(i).attenuation * 0.2; // 其余只贡献20%
        }
        int result = baseTemperature + (int) Math.round(totalDelta);
        // 限制最终温度不能超出所有热源/冷源的极端值
        int maxTemp = baseTemperature, minTemp = baseTemperature;
        for (BlockPos currentPos : BlockPos.iterate(pos.add(-3, -3, -3), pos.add(3, 3, 3))) {
            int t = getBlockTemperature(world.getBlockState(currentPos));
            if (t > maxTemp) maxTemp = t;
            if (t < minTemp) minTemp = t;
        }
        result = MathHelper.clamp(result, minTemp, maxTemp);
        return result;
    }
    public static int getBlockTemperature(BlockState state) {
        Block block = state.getBlock();
        // 标签匹配的方块温度与状态无关，可以缓存
        Integer cached = TEMP_CACHE.get(block);
        if (cached != null) return cached;
        // 检查标签
        for (Map.Entry<TagKey<Block>, Integer> entry : TEMPERATURE_TAGS.entrySet()) {
            if (state.isIn(entry.getKey())) {
                TEMP_CACHE.put(block, entry.getValue());
                return entry.getValue();
            }
        }
        // 特殊方块判断
        if (block instanceof FurnaceBlock) {
            return state.get(FurnaceBlock.LIT) ? 60 : 25;
        } else if (block instanceof SmokerBlock) {
            return state.get(SmokerBlock.LIT) ? 50 : 25;
        } else if (block instanceof BlastFurnaceBlock) {
            return state.get(BlastFurnaceBlock.LIT) ? 80 : 25;
        } else if (block instanceof OvenBlock) {
            return state.get(OvenBlock.OVEN_BURNING) ? 40 : 25;
        } else if (block instanceof AdvanceFurnaceBlock) {
            return state.get(AdvanceFurnaceBlock.BURNING) ? 70 : 25;
        } else if (block instanceof CampfireBlock) {
            return state.get(CampfireBlock.LIT) ? 50 : 25;
        }
        return 25;
    }
    public static void clearCache() {
        TEMP_CACHE.clear();
    }
}
