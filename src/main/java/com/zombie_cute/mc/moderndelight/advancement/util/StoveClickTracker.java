package com.zombie_cute.mc.moderndelight.advancement.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StoveClickTracker {

    private static final int MAX_SAMPLES = 20;
    private static final long HISTORY_RETENTION_MILLIS = 10_000;
    private static final Map<UUID, Deque<Long>> CLICK_HISTORY = new HashMap<>();

    /** Records a click and returns an ordered snapshot of the recent click timestamps. */
    public static List<Long> recordClick(UUID playerId) {
        long now = System.currentTimeMillis();
        Deque<Long> history = CLICK_HISTORY.computeIfAbsent(playerId, k -> new ArrayDeque<>());
        history.addLast(now);
        while (!history.isEmpty() && now - history.peekFirst() > HISTORY_RETENTION_MILLIS) {
            history.pollFirst();
        }
        while (history.size() > MAX_SAMPLES) {
            history.pollFirst();
        }
        return List.copyOf(history);
    }

    /** Clears the history when a player disconnects. */
    public static void clear(UUID playerId) {
        CLICK_HISTORY.remove(playerId);
    }
}
