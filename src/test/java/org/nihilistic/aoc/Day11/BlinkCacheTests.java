package org.nihilistic.aoc.Day11;

import org.junit.Before;
import org.junit.Test;
import org.nihilistic.aoc.day11.BlinkCache;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.Lists;

public class BlinkCacheTests {
    public BlinkCache cache;
    @Before
    public void SetupCache() {
        cache = new BlinkCache();
        cache.put(10L, 5, Lists.newArrayList(1L, 2L));
        cache.put(10L, 3, Lists.newArrayList(3L));
        cache.put(20L, 10, Lists.newArrayList(4L));
    }
    @Test
    public void testBlinkCache_whenGetMaxCachedValueEquals_isCorrect() {
        // act
        var actual = cache.getMaxCachedValueUpTo(10L, 5);

        // assert

        assertThat(actual).isPresent();
        assertThat(actual.get().blinks()).isEqualTo(5);
        assertThat(actual.get().values()).containsExactly(1L, 2L);
    }

    @Test
    public void testBlinkCache_whenGetMaxCachedValueLess_isCorrect() {
        // act
        var actual = cache.getMaxCachedValueUpTo(10L, 4);

        // assert

        assertThat(actual).isPresent();
        assertThat(actual.get().blinks()).isEqualTo(3);
        assertThat(actual.get().values()).containsExactly(3L);
    }

    @Test
    public void testBlinkCache_whenGetMaxCachedValueNoOldEnough_isCorrect() {
        // act
        var actual = cache.getMaxCachedValueUpTo(20L, 5);

        // assert

        assertThat(actual).isEmpty();
    }

    @Test
    public void testBlinkCache_whenGetMaxCachedValueMissing_isCorrect() {
        // act
        var actual = cache.getMaxCachedValueUpTo(30L, 0);

        // assert

        assertThat(actual).isEmpty();
    }
}
