package com.zombie_cute.mc.moderndelight.util;

import com.zombie_cute.mc.moderndelight.ModernDelightMain;
import com.zombie_cute.mc.moderndelight.effects.ModEffectsAndPotions;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 状态效果的延迟结算器。
 *
 * <p>1.20.1 的 {@code LivingEntity#tickStatusEffects} 会直接遍历 activeStatusEffects 的
 * HashMap，在 {@code applyUpdateEffect} 里调用 addStatusEffect/removeStatusEffect 会导致
 * ConcurrentModificationException。所有“效果结束时给另一个效果”“受伤打断食困”这类操作
 * 都通过这里排到 END_SERVER_TICK，避免在遍历过程中改 map。</p>
 *
 * <p>同时这里也处理两个每 tick 的状态检查，这样就不需要再为它们各写一个 Mixin。</p>
 */
public final class EffectScheduler {
    private static final List<Consumer<MinecraftServer>> TASKS = new ArrayList<>();
    private static final Set<UUID> COMA_INTERRUPTED = new HashSet<>();

    public static void schedule(Consumer<MinecraftServer> task) {
        TASKS.add(task);
    }

    public static void scheduleForPlayer(ServerPlayerEntity player, Consumer<ServerPlayerEntity> action) {
        UUID uuid = player.getUuid();
        TASKS.add(server -> {
            ServerPlayerEntity current = server.getPlayerManager().getPlayer(uuid);
            if (current != null) {
                action.accept(current);
            }
        });
    }

    /**
     * 受伤时登记“打断食困”。真正移除放到 END_SERVER_TICK，避免在效果 tick 内改 map。
     * 同时标记 interrupted，防止同一 tick 自然到期时又发饱足。
     */
    public static void scheduleComaBreak(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        StatusEffectInstance coma = player.getStatusEffect(ModEffectsAndPotions.FOOD_COMA);
        COMA_INTERRUPTED.add(uuid);
        TASKS.add(server -> {
            ServerPlayerEntity current = server.getPlayerManager().getPlayer(uuid);
            if (current != null && current.getStatusEffect(ModEffectsAndPotions.FOOD_COMA) == coma
                    && current.hurtTime > 0) {
                current.removeStatusEffect(ModEffectsAndPotions.FOOD_COMA);
            } else {
                // 伤害被其他模组取消/格挡，或同一 tick 又获得了新的食困时，不要误删。
                COMA_INTERRUPTED.remove(uuid);
            }
        });
    }

    /**
     * 食困自然结束时发放饱足。若同一 tick 受过伤害，则不发。
     */
    public static void scheduleComaWellFed(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        TASKS.add(server -> {
            if (COMA_INTERRUPTED.contains(uuid)) {
                return;
            }
            ServerPlayerEntity current = server.getPlayerManager().getPlayer(uuid);
            if (current != null && !current.hasStatusEffect(ModEffectsAndPotions.FOOD_COMA)) {
                int level = Math.min(FoodEffectDispatcher.getLevel(player, ModEffectsAndPotions.WELL_FED),4);
                current.addStatusEffect(new StatusEffectInstance(ModEffectsAndPotions.WELL_FED, 120 * 20, level));
            }
        });
    }

    /**
     * 冰头痛自然结束时发放抗火。
     */
    public static void scheduleBrainFreezeFireResistance(ServerPlayerEntity player, int duration) {
        UUID uuid = player.getUuid();
        TASKS.add(server -> {
            ServerPlayerEntity current = server.getPlayerManager().getPlayer(uuid);
            if (current != null && !current.hasStatusEffect(ModEffectsAndPotions.BRAIN_FREEZE)) {
                current.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, duration, 0));
            }
        });
    }

    public static void clearComaInterrupted(UUID uuid) {
        COMA_INTERRUPTED.remove(uuid);
    }

    public static void tick(MinecraftServer server) {
        // 需要每 tick 维持的效果，用事件代替 setSprinting/canFreeze 的 Mixin。
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player.hasStatusEffect(ModEffectsAndPotions.FOOD_COMA) && player.isSprinting()) {
                player.setSprinting(false);
            }
            if (player.hasStatusEffect(ModEffectsAndPotions.WARM_BELLY)) {
                player.setFrozenTicks(0);
            }
        }

        if (TASKS.isEmpty()) {
            COMA_INTERRUPTED.clear();
            return;
        }

        List<Consumer<MinecraftServer>> tasks = new ArrayList<>(TASKS);
        TASKS.clear();
        for (Consumer<MinecraftServer> task : tasks) {
            try {
                task.accept(server);
            } catch (Exception e) {
                ModernDelightMain.LOGGER.error("Failed to run a deferred status effect task", e);
            }
        }
        // 所有任务都跑完后再清理，保证“打断食困”的标记能覆盖同一 tick 的饱足任务。
        COMA_INTERRUPTED.clear();
    }
}
