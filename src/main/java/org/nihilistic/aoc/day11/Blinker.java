package org.nihilistic.aoc.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Blinker(List<Long> numbers, Map<BlinkCacheKey, Long> cache) {
    public static Blinker fromString(String input) {
        var numbers = Arrays.stream(input.split(" ")).mapToLong(Long::valueOf).boxed().toList();
        var cache = new HashMap<BlinkCacheKey, Long>();
        return new Blinker(numbers, cache);
    }

    public Long countStones() {
        return countStones(25);
    }
    public Long countStones(int blinksLeft) {
        long total=0;
        for (var number: numbers) {
            total += countStones(number, blinksLeft);
        }
        return total;
    }

    public Long countStones(Long number, int blinksLeft) {
        if (blinksLeft == 0) {
            return 1L;
        }

        var cacheKey = new BlinkCacheKey(number, blinksLeft);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        var numbers = blink(number);
        long total=0;

        for (var splitNumber: numbers) {
            total += countStones(splitNumber, blinksLeft-1);
        } 
        cache.put(cacheKey, total);
        return total;
    }

    public List<Long> blink(Long number) {
        if (number == 0) {
            return List.of(1L);
        }

        var digits = String.valueOf(number);
        if (digits.length() % 2 == 0) {
            var midPoint = digits.length() / 2;
            var left = Long.valueOf(digits.substring(0, midPoint));
            var right = Long.valueOf(digits.substring(midPoint, digits.length()));
            return List.of(left, right);
        }
        return List.of(number * 2024);
    }
}
/*
 * the pattern always goes
 * 0
 * 1
 * 2024
 * 20 24
 * 2 0 2 4
 * 4048 ...
 * 40 48
 * 4 0 4 8
 * 8096 ...
 * 80 96
 * 8 0 9 6
 * 16192 ...
 * 32772608 ...
 * 3277 2608 ...
 * 32 77 26 08 ...
 * 3 2 7 7 2 6 0 8
 * 6072 ...
 * 60 72 ...
 * 6 0 7 2 ...
 */