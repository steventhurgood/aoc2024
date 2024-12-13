package org.nihilistic.aoc.day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record BlinkCache(Map<Long, List<BlinkCacheResult>> cache) {

    public BlinkCache() {
        this(new HashMap<Long, List<BlinkCacheResult>>());
    }

    public void put(Long key, int blinks, List<Long> values) {
        if (!cache.containsKey(key)) {
            cache.put(key, new ArrayList<BlinkCacheResult>());
        }
        cache.get(key).add(new BlinkCacheResult(blinks, values));
    }

    public Optional<BlinkCacheResult> getMaxCachedValueUpTo(Long key, int maxBlinks) {
        if (cache.containsKey(key)) {
            List<BlinkCacheResult> cachedValues = cache.get(key);
            BlinkCacheResult result = null;
            for (BlinkCacheResult cachedValue : cachedValues) {
                if (cachedValue.blinks() == maxBlinks) {
                    return Optional.of(cachedValue);
                }
                if (cachedValue.blinks() > maxBlinks) {
                    continue;
                }
                if (result == null) {
                    result = cachedValue;
                    continue;
                }
                if (cachedValue.blinks() > result.blinks()) {
                    result = cachedValue;
                }
            }
            if (result == null) {
                return Optional.empty();
            }
            return Optional.of(result);
        }
        return Optional.empty();
    }
}
